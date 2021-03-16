package com.xrosstools.xdecision.editor.model.expression;

public class NumberExpression extends BasicExpression {
    private Number value;

    public NumberExpression(Number value) {
        this.value = value;
    }

    public NumberExpression(String valueText) {
        this.value = parse(valueText);
    }

    public void setValueText(String valueText) {
        value = parse(valueText);
    }
    
    public Number parse(String valueText) {
        if(valueText.contains("."))
            return new Double(valueText);
        else
            return new Integer(valueText);
    }

    @Override
    public String getDisplayText() {
        return value.toString();
    }
}
