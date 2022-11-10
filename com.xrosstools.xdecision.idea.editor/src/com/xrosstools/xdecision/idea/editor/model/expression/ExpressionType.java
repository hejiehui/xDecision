package com.xrosstools.xdecision.idea.editor.model.expression;

import static com.xrosstools.xdecision.idea.editor.model.expression.EndExpression.end;
import static com.xrosstools.xdecision.idea.editor.model.expression.Grammar.*;
import static com.xrosstools.xdecision.idea.editor.model.expression.TokenType.*;
import static java.util.Arrays.asList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

//TODO support negative expression
public enum ExpressionType {
    /*
     * Raw grammar
     * A -> A + T|A - T|T
     * T -> T * F|T / F|F
     * F -> (A)
     * F -> ID M|ID (P) M|STRING|NUMBER
     * P -> P , A|A|e
     * M -> [A]M|.F|e
     * -----
     * Resolve left regression
     * A -> TA'
     * A' -> + TA'|- TA'|e
     * 
     * T -> FT'
     * T' -> * FT'|/ FT'|e
     * 
     * F -> (A)
     * 
     * F -> ID F'|ID(P)F'
     * F' -> [A]F'|.F|e
     * P -> AP'
     * P' -> , AP'|e
     * 
     * F -> STRING
     * F -> NUMBER
     * -----
     * Extract left common factor
     * A -> TB
     * B -> + TB|- TB|e
     * 
     * T -> FU
     * U -> * FU|/ FU|e
     * 
     * F -> -H|H
     * 
     * H -> (A)|M|STRING|NUMBER
     * 
     * M -> ID G I
     * G -> (P)|e
     * P -> AL|e
     * L -> , AL|e
     * 
     * I -> [A]I|.M|e
     * 
     */

    A(){
        protected List<Grammar> createGrammars() {
            return asList(T_B);
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            return withLeft(exp0(segment), exp1(segment));
        }
    },
    
    B(){
        protected List<Grammar> createGrammars() {
            return asList(PLUS_T_B, MINUS_T_B, FIN);
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();

            ExpressionDefinition exp = withLeft(exp1(segment), exp2(segment));
            return CalculationExpression.compile(grammar == PLUS_T_B ? OperatorEnum.PLUS: OperatorEnum.MINUS, exp);
        }
    },
    
    T(){
        protected List<Grammar> createGrammars() {
            return asList(F_U);
        }
    },
    
    U(){
        protected List<Grammar> createGrammars() {
            return asList(TIMES_F_U, DIVIDE_F_U, FIN);
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();
            
            ExpressionDefinition exp = withLeft(exp1(segment), exp2(segment));
            return CalculationExpression.compile(grammar == TIMES_F_U ? OperatorEnum.TIMES: OperatorEnum.DIVIDE, exp);
        }
    },
    
    F(){
        protected List<Grammar> createGrammars() {
            return asList(MINUS_H, of(H));
        }

        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            if(grammar == MINUS_H)
                return new NegativeExpression().setInnerExpression(exp1(segment));

            return exp0(segment);
        }
    },

    H(){
        protected List<Grammar> createGrammars() {
            return asList(LBRKT_A_RBRKT, of(M), of(STRING), of(NUMBER));
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            if(grammar == LBRKT_A_RBRKT)
                return new BracktExpression().setInnerExpression(exp1(segment));
            
            if(isMatch(grammar, M))
                return exp0(segment);

            String rawValue = ((Token)segment.get(0)).getValueStr();
            
            if(isMatch(grammar, STRING))
                return new StringExpression(rawValue);
            
            return new NumberExpression(rawValue);
        }
    },
    
    M(){
        protected List<Grammar> createGrammars() {
            return asList(ID_G_I);
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            String name = ((Token)segment.get(0)).getValueStr();
            ExpressionDefinition exp = exp1(segment);
            
            if(exp instanceof ParameterListExpression)
                exp = new MethodExpression(name, (ParameterListExpression)exp);
            else
                exp = new VariableExpression(name);

            segment.set(0, exp);
            segment.remove(1);
            return super.compile(grammar, segment);
        }
    },
    
    G(){
        protected List<Grammar> createGrammars() {
            return asList(LBRKT_P_RBRKT, FIN);
        }

        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();
            
            return exp1(segment);
        }
    },
    
    P(){
        protected List<Grammar> createGrammars() {
            return asList(A_L, FIN);
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            return compileParameters(grammar, segment);
        }
    },
    
    L(){
        protected List<Grammar> createGrammars() {
            return asList(COMMA_A_L, FIN);
        }

        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            return compileParameters(grammar, segment);
        }
    },
    
    I(){
        protected List<Grammar> createGrammars() {
            return asList(LSBRKT_A_RSBRKT_I, DOT_M, FIN);
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();

            if(grammar == LSBRKT_A_RSBRKT_I)
                return withLeft(new ElementExpression(exp1(segment)), exp(segment, 3));
            
            return exp1(segment);
        }
    },
    
    END(){
        protected List<Grammar> createGrammars() {
            return Collections.emptyList();
        }
        
        @Override
        public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
            return end();
        }
    },;

    private static Grammar T_B = of(T, B);
    private static Grammar PLUS_T_B = of(PLUS, T, B);
    private static Grammar MINUS_T_B = of(MINUS, T, B);
    private static Grammar F_U = of(F, U);
    private static Grammar TIMES_F_U = of(TIMES, F, U);
    private static Grammar DIVIDE_F_U = of(DIVIDE, F, U);
    private static Grammar MINUS_H = of(MINUS, H);
    private static Grammar LBRKT_A_RBRKT= of(LBRKT, A, RBRKT);
    private static Grammar ID_G_I = of(ID, G, I);
    private static Grammar LBRKT_P_RBRKT = of(LBRKT, P, RBRKT);
    private static Grammar A_L = of(A, L);
    private static Grammar COMMA_A_L = of(COMMA, A, L);
    private static Grammar LSBRKT_A_RSBRKT_I = of(LSBRKT, A, RSBRKT, I);
    private static Grammar DOT_M = of(DOT, M);
    private static Grammar FIN = of(END);

    private AtomicReference<List<Grammar>> grammars = new AtomicReference<List<Grammar>>();
    public List<Grammar> getGrammars() {
        if(grammars.get() == null)
            grammars.set(createGrammars());
        return grammars.get();
    }

    protected abstract List<Grammar> createGrammars();

    public ExpressionDefinition compile(Grammar grammar, List<Object> segment) {
        ExpressionDefinition exp = end();
        for(int i = segment.size() - 1; i >= 0; i--) {
            exp = withLeft(exp(segment, i), exp);
        }
        return exp;
    }
    
    private static ExpressionDefinition exp(List<Object> segment, int index) {
        return (ExpressionDefinition)segment.get(index);
    }
    
    private static ExpressionDefinition exp0(List<Object> segment) {
        return exp(segment, 0);
    }
    
    private static ExpressionDefinition exp1(List<Object> segment) {
        return exp(segment, 1);
    }

    private static ExpressionDefinition exp2(List<Object> segment) {
        return exp(segment, 2);
    }

    private static boolean isMatch(Grammar g, Object token) {
        return g.tokens.size() == 1 &&  g.tokens.get(0) == token;
    }

    private static ExpressionDefinition withLeft(ExpressionDefinition leftExp, ExpressionDefinition basic) {
        if(basic instanceof EndExpression)
            return leftExp;

        if(leftExp instanceof EndExpression)
            return basic;

        if(basic instanceof CalculationExpression)
            return ((CalculationExpression)basic).addFirst(leftExp);
        
        if(basic instanceof NegativeExpression) {
            ExpressionDefinition realBasic = ((NegativeExpression)basic).getInnerExpression();
            CalculationExpression calExp = new CalculationExpression();
            return calExp.add(leftExp).add(new OperatorExpression(OperatorEnum.MINUS)).add(realBasic);
        }
            
        if(leftExp instanceof ExtensibleExpression && basic instanceof ExtensibleExpression)
            ((ExtensibleExpression)leftExp).setChildExpression(basic);
        
        return leftExp;
    }

    private static ExpressionDefinition compileParameters(Grammar grammar, List<Object> segment) {
        if(grammar == FIN)
            return new ParameterListExpression();

        if(grammar == COMMA_A_L)
            segment.remove(0);
            
        ParameterListExpression params = (ParameterListExpression)exp1(segment);
        params.addFirst(exp0(segment));


        return params;
    }    
}