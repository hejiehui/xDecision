package com.xrosstools.xdecision.editor.model.expression;

import static com.xrosstools.xdecision.editor.model.expression.ExpressionType.A;
import static com.xrosstools.xdecision.editor.model.expression.ExpressionType.END;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExpressionCompiler {
    public ExpressionDefinition compile(List<Token> tokens) {
        return compileType(A, new LinkedList<Token>(tokens));
    }
    
    public ExpressionDefinition compile(ExpressionType type, List<Token> tokens) {
        return compileType(type, new LinkedList<Token>(tokens));
    }
    
    // Assume there is no common prefix
    private ExpressionDefinition compileType(ExpressionType type, LinkedList<Token> words) {
        List<Token> bakWords = new ArrayList<Token>(words);

        List<Grammar> grammars = type.getGrammars();
        L: for(Grammar g: grammars) {
            List<Object> segment = new ArrayList<Object>();
            if(g.tokens.contains(END))
                return type.compile(g, segment);

            if(words.isEmpty())
                continue;

            for(Object token: g.tokens) {
                Object exp = null;
                if(token instanceof ExpressionType) {
                    exp = compileType((ExpressionType)token, words);
                }else {
                    if(words.isEmpty())
                        throw new IllegalArgumentException(String.format("Unexpected end of expression \"%s\",  next token of %s is: %s", bakWords, g.tokens, token.toString()));
                    
                    exp = words.getFirst().getType() == token ? words.removeFirst() : NOT_MATCH;
                }

                if(exp == NOT_MATCH) {
                    if(segment.isEmpty())
                        continue L;

                    String msg = words.isEmpty() ? EMPTY : words.getFirst().getType().toString();
                    throw new IllegalArgumentException("Expect " + token.toString() + " instead of " + msg);
                }
                 
                segment.add(exp);
            }

            return type.compile(g, segment);
        }

        return NOT_MATCH;
    }
    
    private static final PlaceholderExpression NOT_MATCH = new PlaceholderExpression("NOT MATCHED!!!");
    private static final String EMPTY = "empty";
}
