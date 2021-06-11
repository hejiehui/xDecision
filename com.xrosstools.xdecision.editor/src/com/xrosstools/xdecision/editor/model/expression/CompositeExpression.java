package com.xrosstools.xdecision.editor.model.expression;

import java.util.ArrayList;
import java.util.List;

public class CompositeExpression extends ExpressionDefinition {
    private List<ExpressionDefinition> expressionChildren = new ArrayList<ExpressionDefinition>();

    public int size() {
        return expressionChildren.size();
    }
    public List<ExpressionDefinition> getAllExpression() {
        return expressionChildren;
    }
    
    public ExpressionDefinition getExpression(int index) {
        return expressionChildren.get(index);
    }
    
    public int indexOf(ExpressionDefinition exp) {
        return expressionChildren.indexOf(exp);
    }
    
    public void remove(ExpressionDefinition exp) {
        expressionChildren.remove(exp);
    }
    
    public CompositeExpression add(int index, ExpressionDefinition exp) {
        expressionChildren.add(index, exp);
        return this;
    }
    
    public CompositeExpression addFirst(ExpressionDefinition exp) {
        expressionChildren.add(0, exp);
        return this;
    }
    
    public boolean isEmpty() {
        return expressionChildren.isEmpty();
    }
    
    public ExpressionDefinition getFirst() {
        return expressionChildren.get(0);
    }
    
    public CompositeExpression add(ExpressionDefinition exp) {
        expressionChildren.add(exp);
        return this;
    }

    public void set(int index, ExpressionDefinition exp) {
        expressionChildren.set(index, exp);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ExpressionDefinition child: expressionChildren)
            sb.append(child.toString());
        
        return sb.toString();
    }
}
