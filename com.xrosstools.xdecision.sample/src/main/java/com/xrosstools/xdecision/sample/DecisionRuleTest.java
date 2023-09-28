package com.xrosstools.xdecision.sample;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class DecisionRuleTest {
    private XDecisionTree<String> tree;
    private MapFacts test;

    @Before
    public void setUp(){
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("DecisionRule.xdecision");
            tree.setDebug(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_0(){
        /*
            decision1

            Age == 20
        */
        test = new MapFacts();
        test.set("Age", 20);
        test.set("Name", "");
        test.set("Gender", "");
        assertEquals("decision1", tree.get(test));
    }

    @Test
    public void test_1(){
        /*
            decision0

            Age == 20
            Name == 'Jerry'
            Gender == true
        */
        test = new MapFacts();
        test.set("Age", 20);
        test.set("Name", "Jerry");
        test.set("Gender", true);
        assertEquals("decision0", tree.get(test));
    }

    @Test
    public void test_2(){
        /*
            no decision

            Age == 20
            Name == 'Jerry'
            Gender == false
        */
        test = new MapFacts();
        test.set("Age", 20);
        test.set("Name", "Jerry");
        test.set("Gender", false);
        assertEquals(null, tree.get(test));
    }
}