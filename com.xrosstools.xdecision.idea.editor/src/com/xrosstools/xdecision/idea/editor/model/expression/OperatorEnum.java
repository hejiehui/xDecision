package com.xrosstools.xdecision.idea.editor.model.expression;

public enum OperatorEnum {
    //TODO "%"

    PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/");
    
    public String getOperator() {
        return operator;
    }

    private String operator;

    OperatorEnum(String operator) {
        this.operator = operator;
    }
}
