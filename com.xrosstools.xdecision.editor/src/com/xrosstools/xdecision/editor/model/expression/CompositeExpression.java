package com.xrosstools.xdecision.editor.model.expression;

import java.util.ArrayList;
import java.util.List;

public class CompositeExpression extends ExpressionDefinition {
    private List<ExpressionDefinition> elements = new ArrayList<ExpressionDefinition>();

    public int size() {
        return elements.size();
    }
    public List<ExpressionDefinition> getAllExpression() {
        return elements;
    }
    
    public ExpressionDefinition getExpression(int index) {
        return elements.get(index);
    }
    
    public int indexOf(ExpressionDefinition exp) {
        return elements.indexOf(exp);
    }
    
    public void remove(ExpressionDefinition exp) {
        elements.remove(exp);
        propertyChanged();
    }
    
    public CompositeExpression add(int index, ExpressionDefinition exp) {
        elements.add(index, exp);
        propertyChanged();
        return this;
    }
    
    public CompositeExpression addFirst(ExpressionDefinition exp) {
        elements.add(0, exp);
        propertyChanged();
        return this;
    }
    
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    
    public ExpressionDefinition getFirst() {
        return elements.get(0);
    }
    
    public CompositeExpression add(ExpressionDefinition exp) {
        elements.add(exp);
        propertyChanged();
        return this;
    }

    public void set(int index, ExpressionDefinition exp) {
        elements.set(index, exp);
        propertyChanged();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ExpressionDefinition child: elements)
            sb.append(child.toString());
        
        return sb.toString();
    }
}
