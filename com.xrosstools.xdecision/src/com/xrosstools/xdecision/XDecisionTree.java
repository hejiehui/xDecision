package com.xrosstools.xdecision;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A more user readable decision tree implementation
 * @author Jerry He
 */
public class XDecisionTree<T> {
    private String nodeExpression;
    private T decision;
    private Map<Object, XDecisionTree<T>> nodes;
    private PathEvaluator evaluator;
    private boolean debug;

    public XDecisionTree(PathEvaluator evaluator) {
        this(null, evaluator );
    }
    
    public XDecisionTree(T decision, PathEvaluator evaluator) {
        this.decision = decision;
        this.evaluator = evaluator;
    }
    
    public void setDebug(boolean debug) {
        this.debug = debug;
        if(nodes == null)
            return;
        
        for(XDecisionTree<T> childNode: nodes.values()) {
            childNode.setDebug(debug);
        }
    }

    /**
     * Path is a two dimension array, the first column is factor name, the 2nd is factor value
     * @param path the value list of factors that can reach to a decision
     */
    public void add(XDecisionPath<T> path) {
        add(0, path, evaluator);
    }
    
    private void add(int depth, XDecisionPath<T> path, PathEvaluator evaluator) {
        setKeyIndex(path.getPathEntry(depth).getNodeExpression());
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
        if (nodes == null) {
            return debugDecision("Leaf decision: ", decision);
        }
        
        debugNodeExpression(nodeExpression);

        Object fact = facts.get(nodeExpression);
        
        Object path = evaluator == null ? fact : evaluator.evaluate(facts, nodeExpression, nodes.keySet().toArray());
        
        debugPath(path);
        
        if(path == null)
            return debugDecision("Default decision: ", decision);
        
        XDecisionTree<T> node = nodes.get(path);
        
        return node == null ? decision : node.get(facts);
    }
    
    private T debugDecision(String message, T value) {
        if(debug)
            System.out.println(message + value);

        return value;
    }
    
    private void debugNodeExpression(Object value) {
        if(debug)
            System.out.print("Node: "+ value);
    }

    private void debugPath(Object value) {
        if(debug)
            System.out.println(" Path: " + value);
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
    
    private void setKeyIndex(String nodeExpression) {
        if (nodeExpression == null)
            throw new NullPointerException("Node expression can not be null");

        // Not initialized yet
        if (this.nodeExpression == null) {
            nodes = new ConcurrentHashMap<Object, XDecisionTree<T>>();
            this.nodeExpression = nodeExpression;
        }
        else {
        	// Already initialized and factor name is not the same with the incoming name
            if (!nodeExpression.equals(this.nodeExpression)) 
            	throw new IllegalStateException(nodeExpression + " is already initialized at current node");
        }
    }
}