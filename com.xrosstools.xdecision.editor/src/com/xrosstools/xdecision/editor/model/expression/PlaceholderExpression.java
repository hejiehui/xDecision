package com.xrosstools.xdecision.editor.model.expression;

import com.xrosstools.xdecision.editor.model.ParameterDefinition;

public class PlaceholderExpression extends BasicExpression {
    private String text = "...";
    public PlaceholderExpression() {}
    public PlaceholderExpression(String name) {
        this.text = name;
    }
    public PlaceholderExpression(ParameterDefinition definition) {
        this(definition.getName());
    }
    @Override
    public String getDisplayText() {
        return text;
    }

}
