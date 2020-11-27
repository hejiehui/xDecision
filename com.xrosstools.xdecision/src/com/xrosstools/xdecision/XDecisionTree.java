package com.xrosstools.xdecision;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A more user readable decision tree implementation
 * @author Jerry He
 */
public class XDecisionTree<T> {
    private String factorName;
    private T decision;
    private Map<Object, XDecisionTree<T>> nodes;
    private PathEvaluator evaluator;

    public XDecisionTree(PathEvaluator evaluator) {
        this(null, evaluator );
    }
    
    public XDecisionTree(T decision, PathEvaluator evaluator) {
        this.decision = decision;
        this.evaluator = evaluator;
    }

    /**
     * Path is a two dimension array, the first column is factor name, the 2nd is factor value
     * @param path the value list of factors that can reach to a decision
     */
    public void add(XDecisionPath<T> path) {
        add(0, path, evaluator);
    }
    
    private void add(int depth, XDecisionPath<T> path, PathEvaluator evaluator) {
        setKeyIndex(path.getPathEntry(depth).getFactorExpression());
        Object key = path.getPathEntry(depth).getValue();
        XDecisionTree<T> node = getNode(key, evaluator);
        
        if ((depth + 1) == path.length()) {
            node.decision = path.getDecision();
        }
        else {
            node.add(depth + 1, path, evaluator);
        }
    }
    
    public T get(Facts facts) {
        if (nodes == null)
            return decision;
        
        Object fact = facts.get(factorName);
        
        XDecisionTree<T> node = null;
        
        Object path = evaluator == null ? fact : evaluator.evaluate(facts, factorName, nodes.keySet().toArray());
        
        if(path == null)
            return decision;
        
        node = nodes.get(path);
        
        return node == null ? decision : node.get(facts);
    }
    
    private XDecisionTree<T> getNode(Object key, PathEvaluator evaluator) {
    	XDecisionTree<T> node = null;
        if (nodes == null){
            nodes = new ConcurrentHashMap<Object, XDecisionTree<T>>();
        }
        if(nodes.containsKey(key)) {
            node = nodes.get(key);
        }
        else {
            node = new XDecisionTree<T>(evaluator);
            nodes.put(key, node);
        }
        return node;
    }
    
    private void setKeyIndex(String factorName) {
        if (factorName == null)
            throw new NullPointerException("Factor name can not be null");

        // Not initialized yet
        if (this.factorName == null) {
            nodes = new ConcurrentHashMap<Object, XDecisionTree<T>>();
            this.factorName = factorName;
        }
        else {
        	// Already initialized and factor name is not the same with the incoming name
            if (!factorName.equals(this.factorName)) 
            	throw new IllegalStateException(factorName + " is already initialized at current node");
        }
    }
}