package com.xross.tools.xdecision;

import java.util.HashMap;
import java.util.Map;

/**
 * A more user readable decision tree implementation
 * @author Jerry He
 */
public class XDecisionTree<T> {
    private String factorName;
    private T decision;
    private Map<Object, XDecisionTree<T>> nodes;

    public XDecisionTree(){}
    
    /**
     * Initialize root node with a default decision
     * @param decision
     */
    public XDecisionTree(T decision) {
        this.decision = decision;
    }
    
    /**
     * Path is a two dimension array, the first column is factor name, the 2nd is factor value
     * @param path
     * @param decision
     */
    public void add(XDecisionPath<T> path) {
        add(0, path);
    }
    
    private void add(int depth, XDecisionPath<T> path) {
        setKeyIndex(path.getPathEntry(depth).getFactorName());
        Object key = path.getPathEntry(depth).getValue();
        XDecisionTree<T> node = getNode(key);
        
        if ((depth + 1) == path.length()) {
            node.decision = path.getDecision();
        }
        else {
            node.add(depth + 1, path);
        }
    }
    
    public T get(Facts facts) {
        if (nodes == null)
            return decision;
        
        Object fact = facts.get(factorName);
        XDecisionTree<T> node = nodes.get(fact);
        return node == null ? decision : node.get(facts);
    }
    
    private XDecisionTree<T> getNode(Object key) {
    	XDecisionTree<T> node = null;
        if (nodes == null){
            nodes = new HashMap<Object, XDecisionTree<T>>();
        }
        if(nodes.containsKey(key)) {
            node = nodes.get(key);
        }
        else {
            node = new XDecisionTree<T>();
            nodes.put(key, node);
        }
        return node;
    }
    
    private void setKeyIndex(String factorName) {
        if (factorName == null)
            throw new NullPointerException("Factor name can not be null");

        // Not initialized yet
        if (this.factorName == null) {
            nodes = new HashMap<Object, XDecisionTree<T>>();
            this.factorName = factorName;
        }
        else {
        	// Already initialized and factor name is not the same with the incoming name
            if (!factorName.equals(this.factorName)) 
            	throw new IllegalStateException(factorName + " is already initialized at current node");
        }
    }
}