package com.xrosstools.xdecision.editor.model.expression;

public class StringExpression extends BasicExpression {
    private String text;

    public StringExpression(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getDisplayText() {
        return "'" + text + "'";
    }
    
}
