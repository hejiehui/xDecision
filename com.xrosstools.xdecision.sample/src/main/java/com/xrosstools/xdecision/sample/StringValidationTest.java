package com.xrosstools.xdecision.sample;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class StringValidationTest {
    private XDecisionTree<String> tree;
    private MapFacts test;

    @Before
    public void setUp(){
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("string_validation.xdecision");
            tree.setDebug(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_0(){
        /*
            STARTS WITH

            A STARTS WITH 'abc'
        */
        test = new MapFacts();
        test.set("A", "abc");
        assertEquals("STARTS WITH", tree.get(test));
    }


    @Test
    public void test_1(){
        /*
            NOT STARTS WITH

            A NOT STARTS WITH 'abc'
        */
        test = new MapFacts();
        test.set("A", "123");
        assertEquals("NOT STARTS WITH", tree.get(test));
    }

    @Test
    public void test_2(){
        /*
            ENDS WITH

            A STARTS WITH 'abc'
            B ENDS WITH 'def'
        */
        test = new MapFacts();
        test.set("A", "abc");
        test.set("B", "def");
        test.set("C", "");
        assertEquals("ENDS WITH", tree.get(test));
    }

    @Test
    public void test_3(){
        /*
            NOT ENDS WITH

            A STARTS WITH 'abc'
            B NOT ENDS WITH 'def'
        */
        test = new MapFacts();
        test.set("A", "abc");
        test.set("B", "123");
        assertEquals("NOT ENDS WITH", tree.get(test));
    }

    @Test
    public void test_4(){
        /*
            CONTAINS

            A NOT STARTS WITH 'abc'
            B CONTAINS 'def'
        */
        test = new MapFacts();
        test.set("A", "123");
        test.set("B", "cdefg");
        test.set("C", "");
        assertEquals("CONTAINS", tree.get(test));
    }

    @Test
    public void test_5(){
        /*
            NOT CONTAINS

            A NOT STARTS WITH 'abc'
            B NOT CONTAINS 'def'
        */
        test = new MapFacts();
        test.set("A", "123");
        test.set("B", "abc");
        test.set("C", "");
        assertEquals("NOT CONTAINS", tree.get(test));
    }

    @Test
    public void test_6(){
        /*
            MATCHES

            A STARTS WITH 'abc'
            B NOT ENDS WITH 'def'
            C MATCHES 'a*'
        */
        test = new MapFacts();
        test.set("A", "abc");
        test.set("B", "123");
        test.set("C", "aaa");
        assertEquals("MATCHES", tree.get(test));
    }

    @Test
    public void test_7(){
        /*
            NOT MATCHES

            A NOT STARTS WITH 'abc'
            B CONTAINS 'def'
            C NOT MATCHES 'a*'
        */
        test = new MapFacts();
        test.set("A", "123");
        test.set("B", "cdefg");
        test.set("C", "bbb");
        assertEquals("NOT MATCHES", tree.get(test));
    }
}

