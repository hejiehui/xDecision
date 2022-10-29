package com.xrosstools.xdecision.ext;

import static com.xrosstools.xdecision.ext.Grammar.of;
import static com.xrosstools.xdecision.ext.TokenType.*;
import static com.xrosstools.xdecision.ext.EndExpression.end;

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
        public Object compile(Grammar grammar, List<Object> segment) {
            return withLeft(exp0(segment), exp1(segment));
        }
    },
    
    B(){
        protected List<Grammar> createGrammars() {
            return asList(PLUS_T_B, MINUS_T_B, FIN);
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();

            Expression exp = withLeft(exp1(segment), exp2(segment));
            return new CalculateExpression(exp, grammar == PLUS_T_B ? PLUS: MINUS);
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
        public Object compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();
            
            Expression exp = withLeft(exp1(segment), exp2(segment));
            return new CalculateExpression(exp, grammar == TIMES_F_U ? TIMES: DIVIDE);
        }
    },
    
    F(){
        protected List<Grammar> createGrammars() {
            return asList(MINUS_H, of(H));
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            if(grammar == MINUS_H)
                return new NegtiveExpression(exp1(segment));

            return exp0(segment);
        }
    },

    H(){
        protected List<Grammar> createGrammars() {
            return asList(LBRKT_A_RBRKT, of(M), of(STRING), of(NUMBER));
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            if(grammar == LBRKT_A_RBRKT)
                return exp(segment, 1);
            
            if(isMatch(grammar, M))
                return exp0(segment);

            return RawValue.tokenOf((Token)segment.get(0));
        }
    },
    
    M(){
        protected List<Grammar> createGrammars() {
            return asList(ID_G_I);
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            Expression expID = new ReferenceExpression(((Token)segment.get(0)).getValueStr());
            segment.set(0, expID);
            return super.compile(grammar, segment);
        }
    },
    
    G(){
        protected List<Grammar> createGrammars() {
            return asList(LBRKT_P_RBRKT, FIN);
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();
            
            return new MethodExpression((ParametersExpression)exp(segment, 1));
        }
    },
    
    P(){
        protected List<Grammar> createGrammars() {
            return asList(A_L, FIN);
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            return compileParameters(grammar, segment);
        }
    },
    
    L(){
        protected List<Grammar> createGrammars() {
            return asList(COMMA_A_L, FIN);
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            return compileParameters(grammar, segment);
        }
    },
    
    I(){
        protected List<Grammar> createGrammars() {
            return asList(LSBRKT_A_RSBRKT_I, DOT_M, FIN);
        }
        public Object compile(Grammar grammar, List<Object> segment) {
            if(grammar == FIN)
                return end();

            if(grammar == LSBRKT_A_RSBRKT_I)
                return withLeft(new ElementOfExpression(exp(segment, 1)), exp(segment, 3));
            
            return segment.get(1);
        }
    },
    
    END(){
        protected List<Grammar> createGrammars() {
            return Collections.emptyList();
        }
        public Expression compile(Grammar grammar, List<Object> segment) {
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

    private AtomicReference<List<Grammar>> grammars = new AtomicReference<>();
    public List<Grammar> getGrammars() {
        if(grammars.get() == null)
            grammars.set(createGrammars());
        return grammars.get();
    }

    protected abstract List<Grammar> createGrammars();

    public Object compile(Grammar grammar, List<Object> segment) {
        Expression exp = end();
        for(int i = segment.size() - 1; i >= 0; i--) {
            exp = withLeft(exp(segment, i), exp);
        }
        return exp;
    }
    
    private static Expression exp(List<Object> segment, int index) {
        return (Expression)segment.get(index);
    }
    
    private static Expression exp0(List<Object> segment) {
        return exp(segment, 0);
    }
    
    private static Expression exp1(List<Object> segment) {
        return exp(segment, 1);
    }

    private static Expression exp2(List<Object> segment) {
        return exp(segment, 2);
    }

    private static boolean isMatch(Grammar g, ExpressionType token) {
        return g.tokens.size() == 1 &&  g.tokens.get(0) == token;
    }

    private static Expression withLeft(Expression leftExp, Expression basic) {
        return ((LeftExpression)basic).setLeftExp(leftExp);
    }

    private static Object compileParameters(Grammar grammar, List<Object> segment) {
        if(grammar == FIN)
            return new ParametersExpression();

        if(grammar == COMMA_A_L)
            segment.remove(0);
            
        ParametersExpression params = (ParametersExpression)exp1(segment);
        params.addParameter(exp0(segment));
        return params;
    }
    
}