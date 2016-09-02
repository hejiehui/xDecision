package com.xrosstools.xdecision;

import java.util.HashMap;
import java.util.Map;

/*
 * For those don't want to bloat code with complex and mess if-else branches,
 * here is your solution. It use a tree to store decision value and an object array 
 * to represent path. It support any depth of decision making by using default value.
 * E.g. if path (1,2,3) represent a value and at the same time path(1,2) also represent
 * another value. you can get each of them by passing each path. If a path is not defined
 * by add, you still can get the most near value of the path. E.g, if path (1,2,4) is not 
 * specified in the previous example, you still can get the value of path (1,2) since this
 * is the nearest one.
 * 
 * @author jiehe
 * 
 * Note: This class is the early version of decision tree. The problem is it is not so easy to 
 * explain and understand. So begin from 2015, you can use XDecisionTree, which is more user friendly and 
 * easy to understand.
 */

public class DecisionTree<T> {
    private int keyIndex = -1;
    private T decision;
    private Map<Object, DecisionTree<T>> nodes;

    public DecisionTree(){}
    
    /**
     * Initialize root node with a default decision
     * @param decision
     */
    public DecisionTree(T decision) {
        this.decision = decision;
    }
    public void add(Object[][] path, T decision) {
        add(0, path, decision);
    }
    
    private void add(int depth, Object[][] path, T aDecision) {
        setKeyIndex((Integer)path[depth][0]);
        Object key = path[depth][1];
        DecisionTree  <T> node = getNode(key);
        
        if ((depth + 1) == path.length) {
            node.decision = aDecision;
        }
        else {
            node.add(depth + 1, path, aDecision);
        }
    }
    
    public T get(Object[] test) {
        if (nodes == null)
            return decision;
        
        Object key = test[keyIndex];
          DecisionTree  <T> node = nodes.get(key);
        return node == null ? decision : node.get(test);
    }
    
    private DecisionTree<T> getNode(Object key) {
          DecisionTree  <T> node = null;
        if (nodes == null){
            nodes = new HashMap<Object, DecisionTree<T>>();
        }
        if(nodes.containsKey(key)) {
            node = nodes.get(key);
        }
        else {
            node =  new DecisionTree<T>();
            nodes.put(key, node);
        }
        return node;
    }
    
    private void setKeyIndex(Integer indexInteger) {
        int index = indexInteger.intValue();
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        
        if (keyIndex == -1) {
            nodes = new HashMap<Object, DecisionTree<T>>();
            keyIndex = index;
        }
        else {
            if (keyIndex != index) {
                // If already initialized, we need to clear the original nodes.
                nodes.clear();
                keyIndex = index;
            }
        }
    }
}