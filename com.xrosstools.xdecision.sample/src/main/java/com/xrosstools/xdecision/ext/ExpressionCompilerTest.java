package com.xrosstools.xdecision.ext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.xrosstools.xdecision.MapFacts;

public class ExpressionCompilerTest {
    private TokenParser p = new TokenParser();
    private ExpressionCompiler test = new ExpressionCompiler();
//    @Test
    public void testCompileID() {
        Expression e = test.compile(p.parseToken("A"));
        
        MapFacts f = new MapFacts();
        f.set("A", 1);
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
    public void testCompileComputation() {
        Expression e = test.compile(p.parseToken("A+B"));
        
        MapFacts f = new MapFacts();
        f.set("A", 1);
        f.set("B", 1);
        f.set("C", 2);
        f.set("D", 2);
        
//        assertNotNull(e);
//        assertEquals(2.0,  e.evaluate(f));
//        
//        e = test.compile(p.parseToken("A-B"));
//        assertEquals(0.0,  e.evaluate(f));
//
//        e = test.compile(p.parseToken("A*B"));
//        assertEquals(1.0,  e.evaluate(f));
//        
//        e = test.compile(p.parseToken("A/B"));
//        assertEquals(1.0,  e.evaluate(f));
//        
//        e = test.compile(p.parseToken("A+B*C "));
//        f.set("C", 2);
//        assertEquals(3.0,  e.evaluate(f));
//        
        e = test.compile(p.parseToken("A+B*C-D "));
        assertEquals(1.0,  e.evaluate(f));
    }

//    @Test
    public void testBracketComputation() {
        Expression e = test.compile(p.parseToken("A+B"));
        
        MapFacts f = new MapFacts();
        f.set("A", 1);
        f.set("B", 1);
        assertNotNull(e);
        assertEquals(2.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("A-B"));
        assertEquals(0.0,  e.evaluate(f));

        e = test.compile(p.parseToken("A*B"));
        assertEquals(1.0,  e.evaluate(f));
        
        e = test.compile(p.parseToken("A/B"));
        assertEquals(1.0,  e.evaluate(f));
    }
}
