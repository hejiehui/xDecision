package com.xrosstools.xdecision.sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class NumberComparationTest {
    private XDecisionTree<String> tree;
    private MapFacts test;

    @Before
    public void setUp(){
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("number_comparation.xdecision");
            tree.setDebug(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_0(){
        /*
            Greater than 0

            A <> 0
            A > 0
        */
        test = new MapFacts();
        test.set("A", 0.5f);
        assertEquals("Greater than 0", tree.get(test));
    }

    @Test
    public void test_1(){
        /*
            Less than 0

            A <> 0
            A < 0
        */
        test = new MapFacts();
        test.set("A", -0.5f);
        assertEquals("Less than 0", tree.get(test));
    }

    @Test
    public void test_2(){
        /*
            Equals 0

            A == 0
        */
        test = new MapFacts();
        test.set("A", 0);
        assertEquals("Equals 0", tree.get(test));
    }

    @Test
    public void test_3(){
        /*
            Less than or equals to -1

            A <> 0
            A < 0
            A <= -1
        */
        test = new MapFacts();
        test.set("A", -2);
        assertEquals("Less than or equals to -1", tree.get(test));
    }

    @Test
    public void test_4(){
        /*
            Greater than or equals to 1

            A <> 0
            A > 0
            A >= 1
        */
        test = new MapFacts();
        test.set("A", 2);
        assertEquals("Greater than or equals to 1", tree.get(test));
    }
}

