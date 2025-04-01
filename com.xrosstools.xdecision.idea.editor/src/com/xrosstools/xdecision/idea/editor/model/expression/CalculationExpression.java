package com.xrosstools.xdecision.idea.editor.model.expression;

/**
 * A + B - C
 */
public class CalculationExpression extends CompositeExpression {
    public static CalculationExpression compile(OperatorEnum operator, ExpressionDefinition operand) {
        CalculationExpression calExp;
        if(operand instanceof CalculationExpression) {
            calExp = ((CalculationExpression)operand);
        }else{
            calExp = new CalculationExpression();
            calExp.addFirst(operand);
        }
        calExp.addFirst(new OperatorExpression(operator));
        return calExp;
    }
}
