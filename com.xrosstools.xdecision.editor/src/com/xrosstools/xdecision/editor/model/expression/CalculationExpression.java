package com.xrosstools.xdecision.editor.model.expression;

/**
 * A + B - C
 */
public class CalculationExpression extends CompositeExpression {
    //TODO remove this
    public CalculationExpression compute(OperatorEnum operator, ExpressionDefinition operand) {
        add(new OperatorExpression(operator));
        add(operand);
        return this;
    }
    
    public static CalculationExpression compile(OperatorEnum operator, ExpressionDefinition operant) {
        CalculationExpression calExp = operant instanceof CalculationExpression ? (CalculationExpression)operant: new CalculationExpression();
        calExp.addFirst(operant);
        calExp.addFirst(new OperatorExpression(operator));
        return calExp;
    }
}
