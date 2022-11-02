package com.xrosstools.xdecision.idea.editor.model.expression;

import java.util.ArrayList;
import java.util.List;

public class TokenParser {
    public List<Token> parseToken(String expressionStr) {
        List<Token> tokens = new ArrayList<Token>();
        
        int pos = 0;
        char[] chars = expressionStr.toCharArray();
        
        while(pos < chars.length) {
            char next = chars[pos];
            
            //Filter blank
            if(next == ' ') {
                pos++;
                continue;
            }

            boolean matches = false;
            StringBuilder buf = new StringBuilder(String.valueOf(next));
            for(TokenType type: TokenType.values()) {
                if(!type.matchStart(next))
                    continue;
                    
                pos++;
                while(pos < chars.length) {
                    next = chars[pos];
                    if(type.matchBody(buf, next)) {
                        pos++;
                        buf.append(next);
                    } else
                        break;
                }
                
                tokens.add(type.parse(buf));
                matches = true;
                break;
            }

            if(!matches)
                throw new IllegalArgumentException("Can not identify expression: " + expressionStr.substring(pos));
        }

        return tokens;
    }
}
