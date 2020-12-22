package com.xrosstools.xdecision.ext;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TokenParserTest {
    @Test
    public void testParseID() {
        TokenParser parser = new TokenParser();
        String[] cases = new String[] {
                "abc",
                "_abc",
                "ab0c",
                "abc0",
                "abc0_",
                "abc_0",
        };
        
        for(String aCase: cases) {
            List<Token> tokens = parser.parseToken(aCase);
            assertEquals(1, tokens.size());
            assertEquals(TokenType.ID, tokens.get(0).getType());
            assertEquals(aCase, tokens.get(0).getValueStr());
        }
    }

    @Test
    public void testParseString() {
        TokenParser parser = new TokenParser();
        Map<String, String> cases = new HashMap<>();
        cases.put("''", "");
        cases.put("'   '", "   ");
        cases.put("'abc'", "abc");
        cases.put("'123'", "123");
        cases.put("' abc'", " abc");
        cases.put("'123 '", "123 ");
        cases.put("' abc'", " abc");
        cases.put("'1 23 '", "1 23 ");
        
        for(String aCase: cases.keySet()) {
            List<Token> tokens = parser.parseToken(aCase);
            assertEquals(1, tokens.size());
            assertEquals(TokenType.STRING, tokens.get(0).getType());
            assertEquals(cases.get(aCase), tokens.get(0).getValueStr());
        }
    }

    @Test
    public void testParseNumber() {
        TokenParser parser = new TokenParser();
        Map<String, Double> cases = new HashMap<>();
        cases.put("0", 0.0);
        cases.put("1", 1.0);
        cases.put("0.1", 0.1);
        cases.put("1.0", 1.0);
        
        for(String aCase: cases.keySet()) {
            List<Token> tokens = parser.parseToken(aCase);
            assertEquals(1, tokens.size());
            assertEquals(TokenType.NUMBER, tokens.get(0).getType());
            assertEquals(aCase, tokens.get(0).getValueStr());
        }
    }
    
    @Test
    public void testParseSingleToken() {
        TokenParser parser = new TokenParser();
        Map<String, TokenType> cases = new HashMap<>();
        cases.put(".", TokenType.DOT);
        cases.put(",", TokenType.COMMA);
        cases.put("+", TokenType.PLUS);
        cases.put("-", TokenType.MINUS);
        cases.put("*", TokenType.TIMES);
        cases.put("/", TokenType.DIVIDE);
        cases.put("(", TokenType.LBRKT);
        cases.put(")", TokenType.RBRKT);
        cases.put("[", TokenType.LSBRKT);
        cases.put("]", TokenType.RSBRKT);

        for(String aCase: cases.keySet()) {
            List<Token> tokens = parser.parseToken(aCase);
            assertEquals(1, tokens.size());
            assertEquals(cases.get(aCase), tokens.get(0).getType());
            assertEquals(aCase, tokens.get(0).getValueStr());
        }
    }

    @Test
    public void testParseSentence() {
        TokenParser parser = new TokenParser();
        Map<String, List<TokenType>> cases = new HashMap<>();
        cases.put("...", tokens(TokenType.DOT, TokenType.DOT, TokenType.DOT));
        cases.put(",,,", tokens(TokenType.COMMA, TokenType.COMMA, TokenType.COMMA));
        cases.put("+++", tokens(TokenType.PLUS, TokenType.PLUS, TokenType.PLUS));
        cases.put("---", tokens(TokenType.MINUS, TokenType.MINUS, TokenType.MINUS));
        cases.put("***", tokens(TokenType.TIMES, TokenType.TIMES, TokenType.TIMES));
        cases.put("///", tokens(TokenType.DIVIDE, TokenType.DIVIDE, TokenType.DIVIDE));
        cases.put("(((", tokens(TokenType.LBRKT, TokenType.LBRKT, TokenType.LBRKT));
        cases.put(")))", tokens(TokenType.RBRKT, TokenType.RBRKT, TokenType.RBRKT));
        cases.put("[[[", tokens(TokenType.LSBRKT, TokenType.LSBRKT, TokenType.LSBRKT));
        cases.put("]]]", tokens(TokenType.RSBRKT, TokenType.RSBRKT, TokenType.RSBRKT));
        
        cases.put("12 23 45", tokens(TokenType.NUMBER, TokenType.NUMBER, TokenType.NUMBER));
        cases.put("'12' ' 23' '45 ", tokens(TokenType.STRING, TokenType.STRING, TokenType.STRING));
        cases.put("a12 A23 a45_", tokens(TokenType.ID, TokenType.ID, TokenType.ID));
        
        cases.put("a12 'A23' 45 .,+-*/()[]", tokens(
                TokenType.ID, TokenType.STRING, TokenType.NUMBER, 
                TokenType.DOT, TokenType.COMMA, TokenType.PLUS, TokenType.MINUS, TokenType.TIMES, TokenType.DIVIDE,
                TokenType.LBRKT, TokenType.RBRKT, TokenType.LSBRKT, TokenType.RSBRKT));
        
        cases.put("a12.b[123].test('A23', 45)[7]", tokens(
                TokenType.ID, TokenType.DOT, TokenType.ID, TokenType.LSBRKT, TokenType.NUMBER, TokenType.RSBRKT, TokenType.DOT,
                TokenType.ID, TokenType.LBRKT, TokenType.STRING, TokenType.COMMA, TokenType.NUMBER, TokenType.RBRKT, 
                TokenType.LSBRKT, TokenType.NUMBER, TokenType.RSBRKT));

        for(String aCase: cases.keySet()) {
            List<Token> tokens = parser.parseToken(aCase);
            List<TokenType> expList = cases.get(aCase);
            assertEquals(expList.size(), tokens.size());
            
            for(int i = 0; i < tokens.size(); i++) {
                assertEquals(expList.get(i), tokens.get(i).getType());
            }
        }
    }
    
    private List<TokenType> tokens(TokenType...tokens) {
        return Arrays.asList(tokens);
    }
}
