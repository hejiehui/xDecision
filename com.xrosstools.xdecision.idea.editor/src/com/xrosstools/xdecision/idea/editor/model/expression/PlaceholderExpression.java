package com.xrosstools.xdecision.idea.editor.model.expression;

import com.xrosstools.xdecision.idea.editor.model.definition.ParameterDefinition;

public class PlaceholderExpression extends BasicExpression {
    public static final PlaceholderExpression EMPTY = new PlaceholderExpression("");
    private String text = "";

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
