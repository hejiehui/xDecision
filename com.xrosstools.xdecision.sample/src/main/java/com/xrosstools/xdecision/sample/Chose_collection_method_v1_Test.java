package com.xrosstools.xdecision.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class Chose_collection_method_v1_Test {
    private XDecisionTree<String> tree;
    private MapFacts test;
    
    @Before
    public void setUp(){
        try {
            // Please revise the path to correct value 
            tree = XDecisionTreeFactory.create("chose_collection_method_v1.xdecision");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void test_0(){
        /*
          overdueTime BETWEEN 1, 29
          riskLevel IN 'low', 'middle'
        */
        test = new MapFacts();
        test.set("overdueTime", 2);
        test.set("riskLevel", "low");
        assertEquals("msg_1", tree.get(test));
    }

    @Test
    public void test_1(){
        /*
          overdueTime BETWEEN 1, 29
          riskLevel == 'high'
        */
        test = new MapFacts();
        test.set("overdueTime", 4);
        test.set("riskLevel", "high");
        assertEquals("msg_2", tree.get(test));
    }

    @Test
    public void test_2(){
        /*
          overdueTime BETWEEN 30, 98
          riskLevel =='low'
        */
        test = new MapFacts();
        test.set("overdueTime", 40);
        test.set("riskLevel", "low");
        assertEquals("msg_2, call_2", tree.get(test));
    }

    @Test
    public void test_3(){
        /*
          overdueTime BETWEEN 30, 98
          riskLevel IN 'middle', 'high'
        */
        test = new MapFacts();
        test.set("overdueTime", 50);
        test.set("riskLevel", "middle");
        assertEquals("call_3", tree.get(test));
    }

    @Test
    public void test_4(){
        /*
          overdueTime BETWEEN 90, 180
          riskLevel =='low'
          repaymentWilling == 'A'
        */
        test = new MapFacts();
        test.set("overdueTime", 120);
        test.set("riskLevel", "low");
        test.set("repaymentWilling", "A");
        assertEquals("call_6", tree.get(test));
    }

    @Test
    public void test_5(){
        /*
          overdueTime BETWEEN 90, 180
          riskLevel =='low'
          repaymentWilling IN 'B', 'C'
        */
        test = new MapFacts();
        test.set("overdueTime", 130);
        test.set("riskLevel", "low");
        test.set("repaymentWilling", "B");
        assertEquals("call_5", tree.get(test));
    }

    @Test
    public void test_6(){
        /*
          overdueTime BETWEEN 90, 180
          riskLevel =='low'
          repaymentWilling == 'others'
        */
        test = new MapFacts();
        test.set("overdueTime", 140);
        test.set("riskLevel", "low");
        test.set("repaymentWilling", "others");
        assertEquals("call_4", tree.get(test));
    }

    @Test
    public void test_7(){
        /*
          overdueTime BETWEEN 90, 180
          riskLevel IN 'middle', 'high'
          amount <= 3000
        */
        test = new MapFacts();
        test.set("overdueTime", 150);
        test.set("riskLevel", "high");
        test.set("amount", 2000);
        assertEquals("call_2", tree.get(test));
    }

    @Test
    public void test_8(){
        /*
          overdueTime BETWEEN 90, 180
          riskLevel IN 'middle', 'high'
          amount > 3000
        */
        test = new MapFacts();
        test.set("overdueTime", 160);
        test.set("riskLevel", "middle");
        test.set("amount", 4000);
        assertEquals("call_6", tree.get(test));
    }

    @Test
    public void test_9(){
        /*
          overdueTime >180
          riskLevel IN 'low', 'middle'
          sex == 'male'
        */
        test = new MapFacts();
        test.set("overdueTime", 190);
        test.set("riskLevel", "middle");
        test.set("sex", "male");
        assertEquals("call_7", tree.get(test));
    }

    @Test
    public void test_10(){
        /*
          overdueTime >180
          riskLevel IN 'low', 'middle'
          sex == 'female'
        */
        test = new MapFacts();
        test.set("overdueTime", 200);
        test.set("riskLevel", "low");
        test.set("sex", "female");
        assertEquals("call_8", tree.get(test));
    }

    @Test
    public void test_11(){
        /*
          overdueTime >180
          riskLevel == 'high'
          homeVisited IS TRUE
        */
        test = new MapFacts();
        test.set("overdueTime", 2000);
        test.set("riskLevel", "high");
        test.set("homeVisited", true);
        assertEquals("mail_1, msg_2", tree.get(test));
    }

    @Test
    public void test_12(){
        /*
          overdueTime >180
          riskLevel == 'high'
          homeVisited IS FALSE
          address == 'hefei'
        */
        test = new MapFacts();
        test.set("overdueTime", 3000);
        test.set("riskLevel", "high");
        test.set("homeVisited", false);
        test.set("address", "hefei");
        assertEquals("hom_visit", tree.get(test));
    }

    @Test
    public void test_13(){
        /*
          overdueTime >180
          riskLevel == 'high'
          homeVisited IS FALSE
          address == 'other'
        */
        test = new MapFacts();
        test.set("overdueTime", 4000);
        test.set("riskLevel", "high");
        test.set("homeVisited", false);
        test.set("address", "other");
        assertEquals("mail_2, msg_3", tree.get(test));
    }

}

