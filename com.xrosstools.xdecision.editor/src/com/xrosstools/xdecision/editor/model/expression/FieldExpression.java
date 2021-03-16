package com.xrosstools.xdecision.editor.model.expression;

public class FieldExpression extends ExtensibleExpression implements Identifier {
    private String fieldName;

    public FieldExpression(String fieldName) {
        this.fieldName = fieldName;
    }
    
    public String getDisplayText() {
        return getFieldName();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
        propertyChanged();
    }
    
    public String getMainExpDisplayText() {
        return getDisplayText();
    }
}
