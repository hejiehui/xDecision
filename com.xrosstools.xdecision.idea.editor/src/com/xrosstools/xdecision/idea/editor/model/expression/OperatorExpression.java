package com.xrosstools.xdecision.idea.editor.model.expression;

public class OperatorExpression extends BasicExpression {
    private OperatorEnum operator;
    
    public OperatorExpression(OperatorEnum operator) {
        this.operator = operator;
    }
    
    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }

    public OperatorEnum getOperator() {
        return operator;
    }

    @Override
    public String getDisplayText() {
        return operator.getOperator();
    }
}
