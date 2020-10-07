package com.xrosstools.xdecision.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

/**
 * This is the unit test for XrossEvaluator
 * @author hejiehui
 *
 */
public class XrossEvaluatorTest {
    @Test
    public void testGetDecision(){
        XDecisionTree<String> tree;
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("xross_evaluator.xdecision");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            return;
        }

        //Verify tree
        MapFacts test;
        
        test = new MapFacts();
        test.set("A", 3);//==3
        assertEquals("decision2", tree.get(test));

        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "1234");//start with 123
        assertEquals("decision5", tree.get(test));
        
        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "1234");//start with 123
        test.set("D", 10);//BETWEEN E, F
        test.set("E", 4);//>3 && >=4
        test.set("F", 14);//>3 && >=4
        assertEquals("decision3", tree.get(test));
        

        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "1234");//start with 123
        test.set("D", 24);//NOT BETWEEN E, F
        test.set("E", 4);//>3 && >=4
        test.set("F", 14);//>3 && >=4
        assertEquals("decision4", tree.get(test));
        
        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "xxabc");//ends with abc
        assertEquals("decision6", tree.get(test));
        
        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "xxabc");//ends with abc
        test.set("F", 1);//IN 1,2, 3,4,5
        assertEquals("decision1", tree.get(test));
        
        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "xxabc");//ends with abc
        test.set("F", 1.5);//NOT IN 1,2, 3,4,5
        assertEquals("decision2", tree.get(test));
        
        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "xy789xx");//contains 789
        assertEquals("decision0", tree.get(test));

        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "xy789xx");//contains 789
        test.set("F", 3);//IN G, 10, 11
        test.set("G", Arrays.asList(1, 2 ,3));
        assertEquals("decision3", tree.get(test));

        test = new MapFacts();
        test.set("A", 3);
        test.set("C", "xy789xx");//contains 789
        test.set("F", 13);//NOT IN G, 10, 11
        test.set("G", Arrays.asList(1, 2 ,3));
        assertEquals("decision4", tree.get(test));

        test = new MapFacts();
        test.set("A", 2.5);//< 3 && !<=2
        assertEquals("decision1", tree.get(test));

        test = new MapFacts();
        test.set("A", 2);//< 3 && <=2
        test.set("B", null);//IS NULL
        assertEquals("decision3", tree.get(test));

        test = new MapFacts();
        test.set("A", 1);//< 3
        test.set("B", "IS NOT NULL");
        assertEquals("decision4", tree.get(test));

        test = new MapFacts();
        test.set("A", 3.5);//> 3
        assertEquals("decision3", tree.get(test));

        test = new MapFacts();
        test.set("A", 4);//>3 && >=4
        assertEquals("decision4", tree.get(test));
        
        test = new MapFacts();
        test.set("A", 4);//>3 && >=4
        test.set("D", 4);//BETWEEN 1, 10
        assertEquals("decision1", tree.get(test));

        test = new MapFacts();
        test.set("A", 4);//>3 && >=4
        test.set("D", 1);//BETWEEN 1, 10
        assertEquals("decision1", tree.get(test));

        test = new MapFacts();
        test.set("A", 4);//>3 && >=4
        test.set("D", 10);//BETWEEN 1, 10
        assertEquals("decision1", tree.get(test));

        test = new MapFacts();
        test.set("A", 4);//>3 && >=4
        test.set("D", 14);//NOT BETWEEN 1, 10
        assertEquals("decision2", tree.get(test));

    }
}

