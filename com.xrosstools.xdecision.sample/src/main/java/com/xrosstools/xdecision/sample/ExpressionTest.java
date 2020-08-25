package com.xrosstools.xdecision.sample;

import org.junit.Test;

import static org.junit.Assert.*;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class ExpressionTest {
    @Test
    public void testGetDecision(){
        XDecisionTree<String> tree;
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("expression.xdecision");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        //Verify tree
        MapFacts test;
        
        
        test = new MapFacts();
        test.set("A", 18);//"< 20"
        assertEquals("decision1", tree.get(test));

        test = new MapFacts();
        test.set("A", 22);//"> 20"
        assertEquals("decision2", tree.get(test));

        test = new MapFacts();
        test.set("A", 10);//"< 20"
        test.set("B", true);//"= true"
        assertEquals("decision3", tree.get(test));

        test = new MapFacts();
        test.set("A", 10);//"< 20"
        test.set("B", false);//"= false"
        assertEquals("decision4", tree.get(test));

        test = new MapFacts();
        test.set("A", 30);//"> 20"
        test.set("C", "abc");//"= 'abc'"
        assertEquals("decision5", tree.get(test));

        test = new MapFacts();
        test.set("A", 30);//"> 20"
        test.set("C", "def");//"= 'def'"
        assertEquals("decision6", tree.get(test));


    }
}
