package com.xrosstools.xdecision.ext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.xrosstools.xdecision.MapFacts;

public class ExpressionCompilerTest {
    private TokenParser p = new TokenParser();
    private ExpressionCompiler test = new ExpressionCompiler();
    @Test
    public void testID() {
        MapFacts f = new MapFacts();
        f.set("A", 1);

        Expression e = test.compile(p.parseToken("A"));
        
        assertNotNull(e);
        assertEquals(1,  e.evaluate(f));
        
        f.set("A", 1.1);
        assertEquals(1.1,  e.evaluate(f));
        
        f.set("A", "A");
        assertEquals("A",  e.evaluate(f));
        
        Object v = new Object();
        f.set("A", v);
        assertEquals(v,  e.evaluate(f));

    }

    @Test
    public void testNumber() {
        MapFacts f = new MapFacts();

        Expression e = test.compile(p.parseToken("123"));
        
        assertNotNull(e);
        assertEquals(123,  e.evaluate(f));
      
        e = test.compile(p.parseToken("123.456"));
        assertEquals(123.456,  e.evaluate(f));
        
//        e = test.compile(p.parseToken("-123.456"));
//        assertEquals(-123.456,  e.evaluate(f));
    }

    @Test
    public void testString() {
        MapFacts f = new MapFacts();

        Expression e = test.compile(p.parseToken("'123'"));
        
        assertNotNull(e);
        assertEquals("123",  e.evaluate(f));
    }

    @Test
    public void testCompileString() {
        MapFacts f = new MapFacts();

        Expression e = test.compile(p.parseToken("'123'"));
        
        assertNotNull(e);
        assertEquals("123",  e.evaluate(f));
    }

    @Test
    public void testElementOf() {
        MapFacts f = new MapFacts();
      
        Integer[] intArray = new Integer[] {1, 2, 3};
      
        f.set("intArray", intArray);
        f.set("A", 1);
      
        Expression e = test.compile(p.parseToken("intArray[0]"));

        assertNotNull(e);
        assertEquals(1,  e.evaluate(f));
      
        e = test.compile(p.parseToken("intArray[1]"));
        assertEquals(2,  e.evaluate(f));
      
        e = test.compile(p.parseToken("intArray[2]"));
        assertEquals(3,  e.evaluate(f));
      
        e = test.compile(p.parseToken("intArray[A]"));
        assertEquals(2,  e.evaluate(f));

        e = test.compile(p.parseToken("intArray[1+1]"));
        assertEquals(3,  e.evaluate(f));

    }
  
    @Test
    public void testMethodOf() {
        MapFacts f = new MapFacts();
      
        f.set("A", "abc");
      
        Expression e = test.compile(p.parseToken("A.length()"));

        assertNotNull(e);
        assertEquals(3,  e.evaluate(f));
        
        e = test.compile(p.parseToken("A.substring(1,2)"));
        assertEquals("b",  e.evaluate(f));
    }
    
    @Test
    public void testCompileComputation() {
        MapFacts f = new MapFacts();
        f.set("A", 1);
        f.set("B", 1);
        f.set("C", 2);
        f.set("D", 2);
        
        Expression e = test.compile(p.parseToken("A+B"));
        
        assertNotNull(e);
        assertEquals(2.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("A-B"));
        assertEquals(0.0,  e.evaluate(f));

        e = test.compile(p.parseToken("A*B"));
        assertEquals(1.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("A/B"));
        assertEquals(1.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("A+B*C "));
        assertEquals(3.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("A+B*C-D "));
        assertEquals(1.0,  e.evaluate(f));
    }

//    @Test
    public void testBracketComputation() {
        MapFacts f = new MapFacts();
        f.set("A", 1);
        f.set("B", 1);
        f.set("C", 2);
        f.set("D", 2);
        
        Expression e = test.compile(p.parseToken("(A+B)"));

        assertNotNull(e);
        assertEquals(2.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("(A-B)"));
        assertEquals(0.0,  e.evaluate(f));

        e = test.compile(p.parseToken("(A*B)"));
        assertEquals(1.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("(A/B)"));
        assertEquals(1.0,  e.evaluate(f));

        e = test.compile(p.parseToken("A+(B*C) "));
        assertEquals(3.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("(A+B)*C "));
        assertEquals(4.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("(A+B)*(C-D) "));
        assertEquals(0.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("(A+B*(C-D)) "));
        assertEquals(1.0,  e.evaluate(f));

        e = test.compile(p.parseToken("(A+(B*C)-D) "));
        assertEquals(1.0,  e.evaluate(f));
    }
}