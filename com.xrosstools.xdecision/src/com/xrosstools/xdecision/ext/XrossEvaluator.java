package com.xrosstools.xdecision.ext;

import java.util.HashMap;
import java.util.Map;

import com.xrosstools.xdecision.Facts;
import com.xrosstools.xdecision.PathEvaluator;
import com.xrosstools.xdecision.XDecisionTreeParser;

public class XrossEvaluator implements XrossEvaluatorConstants, XDecisionTreeParser<String>, PathEvaluator {
    private Map<String, OperandParser> parserMap = new HashMap<>();

    public XrossEvaluator() {
        initParsers(SINGLE_OPERAND_OPERATOR, new SingleOperandParser());
        initParsers(DOUBLE_OPERAND_OPERATOR, new DoubleOperandParser());
        initParsers(BETWEEN_OPERATOR, new BetweenParser());
        initParsers(IN_OPERATOR, new InParser());
    }
    
    private void initParsers(String[] operators, OperandParser parser) {
        for(String opr: operators) {
            parserMap.put(opr, parser);
        }
    }
    
    @Override
    public Object evaluate(Facts facts, String factorName, Object[] paths) {
        for(Object path: paths) {
            Expression exp= (Expression)path;
            if(exp.evaluate(facts).equals(Boolean.TRUE))
                return path;
        }
        return null;
    }
    
    @Override
    public Object parseDecisionPath(String nodeExpression, String operator, String pathExpression) {
        OperandParser parser = parserMap.get(operator);
        if(parser == null)
            throw new IllegalArgumentException("Unidentified operator: " + operator);
            
        return parser.parse(nodeExpression, operator, pathExpression);
    }

    /**
     * Subclass can override this to customize decision
     */
    @Override
    public String parseDecision(String name) {
        return name;
    }
}

