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
            if(words.isEmpty())
                continue;

            if(g.tokens.contains(END)) {
                break;
            }

            List<Object> segment = new ArrayList<>();
            
            for(Object token: g.tokens) {
                if(token instanceof ExpressionType) {
                    segment.add(compile((ExpressionType)token, words));
                }else {
                    if(words.isEmpty())
                        throw new IllegalArgumentException(String.format("Unexpected end of expression \"%s\",  next token of %s is: %s", bakWords, g.tokens, token.toString()));
                    
                    if(words.getFirst().getType() == token ) {
                        segment.add(words.removeFirst());
                    }else {
                        if(segment.size() > 0)
                            throw new IllegalArgumentException("Expect " + token.toString() + " instead of " + words.getFirst().getType().toString());
                        continue L;
                    }
                }
            }

            return type.compile(g, segment);
        }

        return new EndExpression();
    }
}
