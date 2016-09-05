package com.xrosstools.xdecision.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.xrosstools.xdecision.MapFacts;
import com.xrosstools.xdecision.XDecisionTree;
import com.xrosstools.xdecision.XDecisionTreeFactory;

public class SampleTest {
	@Test
	public void testGetDecision(){
		XDecisionTree<String> tree;
		try {
			// Please revise the path to correct value 
			tree = XDecisionTreeFactory.create("Sample.xdecision");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return;
		}

		//Verify tree
		MapFacts test;
		
		
		test = new MapFacts();
		test.set("Age", "below 20");
		assertEquals("decision1", tree.get(test));

		test = new MapFacts();
		test.set("Age", "above 20");
		assertEquals("decision2", tree.get(test));

		test = new MapFacts();
		test.set("Age", "below 20");
		test.set("Rank", "Level 1");
		assertEquals("decision0", tree.get(test));

		test = new MapFacts();
		test.set("Age", "below 20");
		test.set("Rank", "Level 2");
		assertEquals("decision4", tree.get(test));

		test = new MapFacts();
		test.set("Age", "above 20");
		test.set("Insurance Participation", "Yes");
		assertEquals("decision5", tree.get(test));

		test = new MapFacts();
		test.set("Age", "above 20");
		test.set("Insurance Participation", "No");
		assertEquals("decision6", tree.get(test));

		test = new MapFacts();
		test.set("Age", "below 20");
		test.set("Rank", "Level 3");
		assertEquals("decision5", tree.get(test));


	}
}

