package com.xrosstools.xdecision.ext;

import java.io.BufferedWriter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.xrosstools.xdecision.Facts;
import com.xrosstools.xdecision.PathEvaluator;
import com.xrosstools.xdecision.XDecisionTreeParser;

public class XrossEvaluator implements XDecisionTreeParser, PathEvaluator {
    private static final String EQUAL = "==";
    private static final String NOT_EQUAL = "<>";
    private static final String GREATER_THAN = ">";
    private static final String GREATER_THAN_EQUAL = ">=";
    private static final String LESS_THAN = "<";
    private static final String LESS_THAN_EQUAL = "<=";
    private static final String STARTS_WITH = "STARTS WITH";
    private static final String ENDS_WITH = "ENDS WITH";
    private static final String CONTAINS = "CONTAINS";
    
    private static final String IS_NULL = "IS NULL";
    private static final String IS_NOT_NULL = "IS NOT NULL";
    
    private static final String BETWEEN = "BETWEEN";
    private static final String NOT_BETWEEN = "NOT BETWEEN";
    
    private static final String IN = "IN";
    private static final String NOT_IN = "NOT IN";
    
    private static final String STRING_DELIMITER = "'";
    private static final Pattern VARIABLE = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
    
    
    private static final String[] SINGLE_OPERAND_OPERATOR = new String[] {
            IS_NULL, IS_NOT_NULL
    };

    private static final String[] DOUBLE_OPERAND_OPERATOR = new String[] {
            EQUAL, NOT_EQUAL, GREATER_THAN_EQUAL, GREATER_THAN, LESS_THAN_EQUAL, LESS_THAN, STARTS_WITH, ENDS_WITH, CONTAINS
    };

    private static final String[] BETWEEN_OPERATOR = new String[] {
            BETWEEN, NOT_BETWEEN
    };

    private static final String[] IN_OPERATOR = new String[] {
            IN, NOT_IN
    };

    private Map<String, Expression> cache = new ConcurrentHashMap<>();
    
    @Override
    public Object evaluate(Facts facts, String factorName, Object[] paths) {
        for(Object path: paths) {
            Expression exp= (Expression)path;
            if(exp.evaluate(facts).equals(Boolean.TRUE))
                return path;
        }
        return null;
    }
    
    private static interface Expression {
        Object evaluate(Facts facts);
    }
    
    private static interface FactParser {
        String[] operators();
        Object parse(String name, String operator, String expStr);
    }
    
    private static class SingleOperandParser implements FactParser {
        public String[] operators() {return SINGLE_OPERAND_OPERATOR;}
        
        public Object parse(String name, String operator, String expStr) {
            if(expStr.length() != 0)
                throw new IllegalArgumentException(String.format("s% does not require extra parameter s%", operator, expStr));
            
            return new SingleOperandExpression(factorValue(name), operator);
        }
    }
    
    private static class DoubleOperandParser implements FactParser {
        public String[] operators() {return DOUBLE_OPERAND_OPERATOR;}
        
        public Object parse(String name, String operator, String expStr) {
            return new DoubleOperandExpression(factorValue(name), operator, parseExpression(expStr));
        }
    }

    private static class BetweenParser implements FactParser {
        public String[] operators() {return BETWEEN_OPERATOR;}
        
        public Object parse(String name, String operator, String expStr) {
            String[] params = expStr.split(",");
            if(params.length != 2)
                throw new IllegalArgumentException(String.format("BETWEEN/NOT BETWEEN requires 2 parameters separated by ',', please check the format of : ", expStr));
            
            return new BetweenExpression(factorValue(name), operator, parseExpression(params[0]), parseExpression(params[1]));
        }
    };
    
    private static class InParser implements FactParser {
        public String[] operators() {return IN_OPERATOR;}
        
        public Object parse(String name, String operator, String expStr) {
            if(expStr.length() == 0)
                throw new IllegalArgumentException("IN/NOT IN requires at least one parameter");
            
            String[] params = expStr.split(",");
            Expression[] exps = new Expression[params.length];
            int i = 0;
            for(String exp: params)
                exps[i++] = parseExpression(exp);
            return new InExpression(factorValue(name), operator, exps);
        }
    };
    
    private FactParser[] parsers = new FactParser[] {
            new SingleOperandParser(), new DoubleOperandParser(), new BetweenParser(), new InParser(),
    };
    
    @Override
    public Object parseFact(String name, String value) {
        for(FactParser parser: parsers) {
            String operator = identifyOperator(parser.operators(), value);
            if(operator == null)
                continue;
            
            String expStr = value.replaceFirst(operator, "").trim();
            return parser.parse(name, operator, expStr);
        }
        
        throw new IllegalArgumentException("Can not parse expression: " + value);
    }

    /**
     * Subclass can override this to customize decision
     */
    @Override
    public Object parseDecision(String name) {
        return name;
    }
    
    private static String identifyOperator(String[] operators, String path) {
        for(String operand: operators)
            if(path.startsWith(operand))
                return operand;

        return null;
    }
    
    private static Expression parseExpression(String expStr) { 
        expStr = expStr.trim();
        //Parse String
        if(expStr.startsWith(STRING_DELIMITER) && expStr.endsWith(STRING_DELIMITER))
            return new RawValue(expStr.subSequence(1, expStr.length()-1));
        
        //Parse Number
        try {
            return new RawValue(Double.parseDouble(expStr));
        } catch (NumberFormatException e) {
        }
        
        if(VARIABLE.matcher(expStr).matches())
            return new FactorValue(expStr);
        
        throw new IllegalArgumentException("Can not parse expression: " + expStr);
    }
    
    private RawValue rawValue(Object value) {
        return new RawValue(value);
    }
    
    private static class RawValue implements Expression {
        private Object value;
        
        public RawValue(Object value) {
            this.value = value;
        }

        @Override
        public Object evaluate(Facts facts) {
            return value;
        }
        
        @Override
        public String toString() {
            return String.valueOf(value);
        }        
    }
    
    private static class FactorValue implements Expression {
        private String factorName;
        
        public FactorValue(String factorName) {
            this.factorName = factorName;
        }

        @Override
        public Object evaluate(Facts facts) {
            return facts.get(factorName);
        }
        
        @Override
        public String toString() {
            return factorName;
        }
    }
    
    private static Expression factorValue(String factorName) {
        return new FactorValue(factorName);
    }

    private static class SingleOperandExpression implements Expression {
        public Expression leftExp;
        public String operator;
        
        SingleOperandExpression(Expression leftExp, String operator) {
            this.leftExp = leftExp;
            this.operator = operator;
        }

        @Override
        public Boolean evaluate(Facts facts) {
            switch (operator) {
            case IS_NULL:
                return leftExp.evaluate(facts) == null; 
            case IS_NOT_NULL:
                return leftExp.evaluate(facts) != null; 
            default:
                throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
            }
        }        

        @Override
        public String toString() {
            return String.format("s% s%", String.valueOf(leftExp), operator);
        }
    }
    
    private static class DoubleOperandExpression extends SingleOperandExpression {
        public Expression rightExp;
        
        public DoubleOperandExpression(Expression leftExp, String operator, Expression rightExp) {
            super(leftExp, operator);
            this.rightExp = rightExp;
        }

        @Override
        public Boolean evaluate(Facts facts) {
            Object v1 = leftExp.evaluate(facts);
            Object v2 = rightExp.evaluate(facts);
            
            if(v1 == null && v2 == null)
                return operator.contains("=");//==, <=, <=
            
            if(v1 == null || v2 == null)
                return false;
            
            switch (operator) {
            case EQUAL:
                return compare(v1, v2) == 0; 
            case NOT_EQUAL:
                return compare(v1, v2) != 0;
            case GREATER_THAN:
                return compare(v1, v2) > 0;
            case GREATER_THAN_EQUAL:
                return compare(v1, v2) >= 0;
            case LESS_THAN:
                return compare(v1, v2) < 0;
            case LESS_THAN_EQUAL:
                return compare(v1, v2) <= 0;
            case STARTS_WITH:
                return string(v1).startsWith(string(v2));
            case ENDS_WITH:
                return string(v1).endsWith(string(v2));
            case CONTAINS:
                return string(v1).contains(string(v2));
            default:
                throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
            }
        }

        @Override
        public String toString() {
            return String.format("s% s% s%", String.valueOf(leftExp), operator, String.valueOf(rightExp));
        }
        
        private static String string(Object value) {
            if(value instanceof String)
                return (String)value;
            
            throw new IllegalArgumentException(String.format("s% is not a string type", String.valueOf(value)));
        }        
    }
    
    private static class BetweenExpression extends SingleOperandExpression {
        public Expression lowerExp;
        public Expression upperExp;
        
        public BetweenExpression(Expression leftExp, String operator, Expression lowerExp, Expression upperExp) {
            super(leftExp, operator);
            this.lowerExp = lowerExp;
            this.upperExp = upperExp;
        }

        @Override
        public Boolean evaluate(Facts facts) {
            Object value = leftExp.evaluate(facts);
            Object lower = lowerExp.evaluate(facts);
            Object upper = upperExp.evaluate(facts);
            
            if(value == null || lower == null || upper == null)
                return false;
            
            switch (operator) {
            case BETWEEN:
                return compare(value, lower) >= 0 && compare(value, upper) <= 0; 
            case NOT_BETWEEN:
                return compare(value, lower) < 0 || compare(value, upper) > 0; 
            default:
                throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
            }
        }

        @Override
        public String toString() {
            return String.format("s% s% s%, s%", String.valueOf(leftExp), operator, String.valueOf(lowerExp), String.valueOf(upperExp));
        }
    }
    
    private static class InExpression extends SingleOperandExpression {
        public Expression[] valueRange;
        
        public InExpression(Expression leftExp, String operator, Expression[] valueRange) {
            super(leftExp, operator);
            this.valueRange = valueRange;
        }

        @Override
        public Boolean evaluate(Facts facts) {
            Object fact = leftExp.evaluate(facts);
            
            if(fact == null)
                return false;
            
            switch (operator) {
            case IN:
                return inRange(facts, fact); 
            case NOT_IN:
                return !inRange(facts, fact); 
            default:
                throw new IllegalArgumentException("Operator: " + operator + " is not supported!");
            }
        }
        
        private boolean inRange(Facts facts, Object fact) {
            for(Expression exp: valueRange) {
                Object candidateValue = exp.evaluate(facts);
                if(candidateValue == null)
                    continue;
                if(candidateValue instanceof Collection<?>) {
                    Collection<?> col = (Collection<?>)candidateValue;
                    if(col.contains(fact))
                        return true;
                }else {
                    if(compare(fact, candidateValue) == 0)
                        return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(Expression exp: valueRange)
                sb.append(exp.toString()).append(",");
            
            return String.format("s% s% s%", String.valueOf(leftExp), operator, sb.substring(0,  sb.length()-2));
        }
    }
    
    private static int compare(Object v1, Object v2) {
        if(v1 instanceof Number && v2 instanceof Number)
            return Double.compare(((Number)v1).doubleValue(), ((Number)v2).doubleValue());
        
        if(v1 instanceof String && v2 instanceof String)
            return ((String)v1).compareTo((String)v2);
        
        throw new IllegalArgumentException(String.format("s% can not be compared to s%", v1.toString(), v2.toString()));
    }
}

