package com.xrosstools.xdecision.sample;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class RangeValidationTest {
    private XDecisionTree<String> tree;
    private MapFacts test;

    @Before
    public void setUp(){
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("range_validation.xdecision");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_0(){
        /*
            BETWEEN

            A BETWEEN 1,10
        */
        test = new MapFacts();
        test.set("A", 3);
        assertEquals("BETWEEN", tree.get(test));
    }

    @Test
    public void test_1(){
        /*
            NOT BETWEEN

            A NOT BETWEEN B,C
        */
        test = new MapFacts();
        test.set("A", 13);
        test.set("B", 4);
        test.set("C", 10);
        test.set("E", 10);
        test.set("F", "");
        test.set("G", "");
        assertEquals("NOT BETWEEN", tree.get(test));
    }

    @Test
    public void test_2(){
        /*
            IN

            A BETWEEN 1,10
            B IN 1,2,3,4
        */
        test = new MapFacts();
        test.set("A", 3);
        test.set("B", 4);
        test.set("C", "");
        test.set("E", "");
        test.set("F", "");
        test.set("G", "");
        assertEquals("IN", tree.get(test));
    }

    @Test
    public void test_3(){
        /*
            NOT IN

            A NOT BETWEEN B,C
            C NOT IN E,F,G
        */
        test = new MapFacts();
        test.set("A", 13);
        test.set("B", 4);
        test.set("C", 10);
        test.set("E", 11);
        test.set("F", 12);
        test.set("G", 13);
        assertEquals("NOT IN", tree.get(test));
    }
}

