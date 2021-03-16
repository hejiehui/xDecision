package com.xrosstools.xdecision.editor.model.expression;

import static com.xrosstools.xdecision.editor.model.expression.ExpressionType.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.xrosstools.xdecision.editor.model.DecisionTreeManager;

public class ExpressionCompiler {
    public ExpressionDefinition compile(List<Token> tokens) {
        return (ExpressionDefinition)compile(A, new LinkedList<Token>(tokens));
    }
    
    // Assume there is no common prefix
    public Object compile(ExpressionType type, LinkedList<Token> words) {
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
