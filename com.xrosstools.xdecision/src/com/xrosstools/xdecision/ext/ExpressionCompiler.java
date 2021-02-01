package com.xrosstools.xdecision.ext;

import java.util.*;
import static com.xrosstools.xdecision.ext.ExpressionType.*;

public class ExpressionCompiler {
    public Expression compile(List<Token> tokens) {
        return (Expression)compile(A, new LinkedList<>(tokens));
    }
    
    // Assume there is no common prefix
    public Object compile(ExpressionType type, LinkedList<Token> words) {
        List<Token> bakWords = new ArrayList<Token>(words);

        List<Grammar> grammars = type.getGrammars();
        L: for(Grammar g: grammars) {
            List<Object> segment = new ArrayList<>();
            if(g.tokens.contains(END))
                return type.compile(g, segment);

            if(words.isEmpty())
                continue;

            for(Object token: g.tokens) {
                Object exp = null;
                if(token instanceof ExpressionType) {
                    exp = compile((ExpressionType)token, words);
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
    
    private static final Object NOT_MATCH = new Object();
    private static final String EMPTY = "empty";
}
