package com.xrosstools.xdecision.sample;

import static org.junit.Assert.*;

import org.junit.Test;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.ext.*;

public class XrossEvaluatorUnitTest implements XrossEvaluatorConstants {
    private static final String abc1 = "abc1";
    private static final String def1 = "def1";
    private XrossEvaluator test = new XrossEvaluator();
    
    private void testSingleOprPass(Object value, String op) {
        MapFacts facts = new MapFacts();

        Object[] paths = new Object[1];
        paths[0] = test.parseFact(abc1, op);

        facts.set(abc1, value);
        assertNotNull(test.evaluate(facts, abc1, paths));
    }

    private void testSingleOprFail(Object value, String op) {
        MapFacts facts = new MapFacts();

        Object[] paths = new Object[1];
        paths[0] = test.parseFact(abc1, op);

        facts.set(abc1, value);
        assertNull(test.evaluate(facts, abc1, paths));
    }

    @Test
    public void testIsTrue() {
        testSingleOprPass(true, IS_TRUE);
        
        testSingleOprFail(false, IS_TRUE);
        testSingleOprFail(123, IS_TRUE);
        testSingleOprFail(123.4, IS_TRUE);
        testSingleOprFail("123", IS_TRUE);
    }

    @Test
    public void testIsFalse() {
        testSingleOprPass(false, IS_FALSE);
        
        testSingleOprFail(true, IS_FALSE);
        testSingleOprFail(123, IS_FALSE);
        testSingleOprFail(123.4, IS_FALSE);
        testSingleOprFail("123", IS_FALSE);
    }

    @Test
    public void testIsNull() {
        testSingleOprPass(null, IS_NULL);
        
        testSingleOprFail(false, IS_NULL);
        testSingleOprFail(true, IS_NULL);
        testSingleOprFail(123, IS_NULL);
        testSingleOprFail(123.4, IS_NULL);
        testSingleOprFail("123", IS_NULL);
    }

    @Test
    public void testIsNotNull() {
        testSingleOprFail(null, IS_NOT_NULL);
        
        testSingleOprPass(false, IS_NOT_NULL);
        testSingleOprPass(true, IS_NOT_NULL);
        testSingleOprPass(123, IS_NOT_NULL);
        testSingleOprPass(123.4, IS_NOT_NULL);
        testSingleOprPass("123", IS_NOT_NULL);
    }
    
    private void testDoubleOprPass(Object value, String op, Object valueStr) {
        MapFacts facts = new MapFacts();

        Object[] paths = new Object[1];
        paths[0] = test.parseFact(abc1, String.format("%s %s", op, valueStr));

        facts.set(abc1, value);
        facts.set(def1, null);
        assertNotNull(test.evaluate(facts, abc1, paths));
    }

    private void testDoubleOprFail(Object value, String op, Object valueStr) {
        MapFacts facts = new MapFacts();

        Object[] paths = new Object[1];
        paths[0] = test.parseFact(abc1, String.format("%s %s", op, valueStr));

        facts.set(abc1, value);
        facts.set(def1, null);
        assertNull(test.evaluate(facts, abc1, paths));
    }

    @Test
    public void testEqual() {
        String op = EQUAL;
        testDoubleOprPass("abc", op, "'abc'");
        testDoubleOprPass(123, op, "123");
        testDoubleOprPass(123.4, op, "123.4");
        testDoubleOprPass(null, op, "def1");
        testDoubleOprPass(null, op, null);
        
        testDoubleOprFail("abc", op, "'abcd'");
        testDoubleOprFail(123, op, "1234");
        testDoubleOprFail(123.4, op, "123.45");
        testDoubleOprFail(null, op, "123.4");
    }

    @Test
    public void testNotEqual() {
        String op = NOT_EQUAL;
        testDoubleOprPass("abc", op, "'abcd'");
        testDoubleOprPass(123, op, "1234");
        testDoubleOprPass(123.4, op, "123.45");
        testDoubleOprPass(null, op, "123.4");

        testDoubleOprFail("abc", op, "'abc'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testGreaterThan() {
        String op = GREATER_THAN;
        testDoubleOprPass("abc", op, "'ab'");
        testDoubleOprPass(123, op, "12");
        testDoubleOprPass(-1, op, "-2");
        testDoubleOprPass(123.4, op, "123.01");

        testDoubleOprFail("abc", op, "'abc'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "def1");

        testDoubleOprFail("abc", op, "'abcd'");
        testDoubleOprFail(123, op, "1234");
        testDoubleOprFail(-123, op, "-12");
        testDoubleOprFail(123.4, op, "123.45");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testGreaterThanEqual() {
        String op = GREATER_THAN_EQUAL;
        testDoubleOprPass("abc", op, "'ab'");
        testDoubleOprPass(123, op, "12");
        testDoubleOprPass(-1, op, "-2");
        testDoubleOprPass(123.4, op, "123.01");

        testDoubleOprPass("abc", op, "'abc'");
        testDoubleOprPass(123, op, "123");
        testDoubleOprPass(-1, op, "-1");
        testDoubleOprPass(123.4, op, "123.4");
        testDoubleOprPass(null, op, null);
        testDoubleOprPass(null, op, "def1");

        testDoubleOprFail("abc", op, "'abcd'");
        testDoubleOprFail(123, op, "1234");
        testDoubleOprFail(-123, op, "-12");
        testDoubleOprFail(123.4, op, "123.45");
    }

    @Test
    public void testLessThan() {
        String op = LESS_THAN;
        testDoubleOprPass("ab", op, "'abc'");
        testDoubleOprPass(12, op, "123");
        testDoubleOprPass(-2, op, "-1");
        testDoubleOprPass(123.01, op, "123.4");
        
        testDoubleOprFail("abc", op, "'abc'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "def1");
        testDoubleOprFail(null, op, null);

        testDoubleOprFail("abc", op, "'ab'");
        testDoubleOprFail(123, op, "12");
        testDoubleOprFail(-1, op, "-2");
        testDoubleOprFail(123.4, op, "123.01");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testLessThanEqual() {
        String op = LESS_THAN_EQUAL;
        testDoubleOprPass("ab", op, "'abc'");
        testDoubleOprPass(12, op, "123");
        testDoubleOprPass(-2, op, "-1");
        testDoubleOprPass(123.01, op, "123.4");
        
        testDoubleOprPass("abc", op, "'abc'");
        testDoubleOprPass(123, op, "123");
        testDoubleOprPass(123.4, op, "123.4");
        testDoubleOprPass(null, op, "def1");
        testDoubleOprPass(null, op, null);

        testDoubleOprFail("abc", op, "'ab'");
        testDoubleOprFail(123, op, "12");
        testDoubleOprFail(-1, op, "-2");
        testDoubleOprFail(123.4, op, "123.01");
        testDoubleOprFail(null, op, "'abc'");
        testDoubleOprFail("abc", op, null);
    }

    @Test
    public void testStartWith() {
        String op = STARTS_WITH;
        testDoubleOprPass("abc", op, "'ab'");
        
        testDoubleOprFail("ab", op, "'abc'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "'abc'");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testEndWith() {
        String op = ENDS_WITH;
        testDoubleOprPass("aab", op, "'ab'");
        
        testDoubleOprFail("abc", op, "'ab'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "'abc'");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testContains() {
        String op = CONTAINS;
        testDoubleOprPass("abc", op, "'ab'");
        testDoubleOprPass("abc", op, "'bc'");
        testDoubleOprPass("abc", op, "'b'");
        
        testDoubleOprFail("ab", op, "'abc'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "'abc'");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testNotStartWith() {
        String op = NOT_STARTS_WITH;
        testDoubleOprPass("ab", op, "'abc'");
        
        testDoubleOprFail("abc", op, "'ab'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "'abc'");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testNotEndWith() {
        String op = NOT_ENDS_WITH;
        testDoubleOprPass("abc", op, "'ab'");
        
        testDoubleOprFail("aab", op, "'ab'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "'abc'");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testNotContains() {
        String op = NOT_CONTAINS;
        testDoubleOprPass("ab", op, "'abc'");

        testDoubleOprFail("abc", op, "'ab'");
        testDoubleOprFail("abc", op, "'bc'");
        testDoubleOprFail("abc", op, "'b'");
        testDoubleOprFail(123, op, "123");
        testDoubleOprFail(123.4, op, "123.4");
        testDoubleOprFail(null, op, "'abc'");
        testDoubleOprFail(null, op, null);
        testDoubleOprFail(null, op, "def1");
    }

    @Test
    public void testMatches() {
        String op = MATCHES;
        testDoubleOprPass("A", op, "'A*'");
        testDoubleOprPass("AAA", op, "'A*'");

        testDoubleOprFail("B", op, "'A*'");
        testDoubleOprFail("BBB", op, "'A*'");
        testDoubleOprFail(123, op, "'A*'");
        testDoubleOprFail(123.4, op, "'A*'");
        testDoubleOprFail(null, op, "'A*'");
        testDoubleOprFail(null, op, "'A*'");
        testDoubleOprFail(null, op, null);
    }

    @Test
    public void testNotMatches() {
        String op = NOT_MATCHES;
        testDoubleOprPass("B", op, "'A*'");
        testDoubleOprPass("BBB", op, "'A*'");

        testDoubleOprFail("A", op, "'A*'");
        testDoubleOprFail("AAA", op, "'A*'");
        testDoubleOprFail(123.4, op, "'A*'");
        testDoubleOprFail(null, op, "'A*'");
        testDoubleOprFail(null, op, "'A*'");
        testDoubleOprFail(null, op, null);
    }
}
