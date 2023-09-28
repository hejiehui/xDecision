package com.xrosstools.xdecision.sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class ObjectValidationTest {
    private XDecisionTree<String> tree;
    private MapFacts test;

    @Before
    public void setUp(){
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("object_validation.xdecision");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_0(){
        /*
            IS TRUE

            A IS TRUE 
        */
        test = new MapFacts();
        test.set("A", true);
        test.set("B", "");
        assertEquals("IS TRUE", tree.get(test));
    }

    @Test
    public void test_1(){
        /*
            IS FALSE

            A IS FALSE 
        */
        test = new MapFacts();
        test.set("A", false);
        test.set("B", null);
        assertEquals("IS FALSE", tree.get(test));
    }

    @Test
    public void test_2(){
        /*
            IS NULL

            A IS TRUE 
            B IS NULL 
        */
        test = new MapFacts();
        test.set("A", true);
        test.set("B", null);
        assertEquals("IS NULL", tree.get(test));
    }

    @Test
    public void test_3(){
        /*
            IS NOT NULL

            A IS FALSE 
            B IS NOT NULL 
        */
        test = new MapFacts();
        test.set("A", false);
        test.set("B", "");
        assertEquals("IS NOT NULL", tree.get(test));
    }
}

