package com.xrosstools.xdecision.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.Before;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class SampleV2Test {

    private XDecisionTree<String> tree;
    private MapFacts test;

    @Before
    public void setUp(){
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("SampleV2.xdecision");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_0(){
        /*
            decision1

            Age == 'below 20'
        */
        test = new MapFacts();
        test.set("Age", "below 20");
        test.set("Rank", "");
        test.set("Insurance", "");
        test.set("title", "");
        assertEquals("decision1", tree.get(test));
    }

    @Test
    public void test_1(){
        /*
            decision2

            Age == Above20
        */
        test = new MapFacts();
        test.set("Age", "above 20");
        test.set("Rank", "");
        test.set("Insurance", "");
        test.set("title", "");
        assertEquals("decision2", tree.get(test));
    }

    @Test
    public void test_2(){
        /*
            decision5

            Age == Above20
            Insurance == 'Yes'
        */
        test = new MapFacts();
        test.set("Age", "above 20");
        test.set("Rank", "");
        test.set("Insurance", PermitEnum.Yes);
        test.set("title", "");
        assertEquals("decision5", tree.get(test));
    }

    @Test
    public void test_3(){
        /*
            decision6

            Age == Above20
            Insurance == 'No'
        */
        test = new MapFacts();
        test.set("Age", "above 20");
        test.set("Rank", "");
        test.set("Insurance", PermitEnum.No);
        test.set("title", "");
        assertEquals("decision6", tree.get(test));
    }

    @Test
    public void test_4(){
        /*
            decision5

            Age == 'below 20'
            Rank == 'Level 1'
        */
        test = new MapFacts();
        test.set("Age", "below 20");
        test.set("Rank", LevelEnum.level1);
        test.set("Insurance", "");
        test.set("title", "");
        assertEquals("decision5", tree.get(test));
    }

    @Test
    public void test_5(){
        /*
            decision0

            Age == 'below 20'
            Rank == 'Level 2'
        */
        test = new MapFacts();
        test.set("Age", "below 20");
        test.set("Rank", LevelEnum.level2);
        test.set("Insurance", "");
        test.set("title", "");
        assertEquals("decision0", tree.get(test));
    }

    @Test
    public void test_6(){
        /*
            decision4

            Age == 'below 20'
            Rank == 'Level 3'
        */
        test = new MapFacts();
        test.set("Age", "below 20");
        test.set("Rank", LevelEnum.level3);
        test.set("Insurance", "");
        test.set("title", "");
        assertEquals("decision4", tree.get(test));
    }
}

