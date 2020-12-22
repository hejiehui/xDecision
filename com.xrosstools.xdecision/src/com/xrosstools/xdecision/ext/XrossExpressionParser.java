package com.xrosstools.xdecision.ext;

import java.util.ArrayList;
import java.util.List;

public class XrossExpressionParser {
    public Object parseExpression(String expressionStr) {
        List<Token> tokens = parseToken(expressionStr);
        
        return null;
    }
    
    private List<Token> parseToken(String expressionStr) {
        List<Token> tokens = new ArrayList<>();
        
        int pos = 0;
        char[] chars = expressionStr.toCharArray();
        
        while(pos < chars.length) {
            char next = chars[pos];
            
            //Filter blank
            if(next == ' ') {
                pos++;
                continue;
            }
            
            StringBuilder buf = new StringBuilder(next);
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
            }
            
        }

        return tokens;
    }
    
}
