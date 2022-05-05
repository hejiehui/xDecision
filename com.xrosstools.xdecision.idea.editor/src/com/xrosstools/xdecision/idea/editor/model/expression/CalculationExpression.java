package com.xrosstools.xdecision.idea.editor.model.expression;

/**
 * A + B - C
 */
public class CalculationExpression extends CompositeExpression {
    public static CalculationExpression compile(OperatorEnum operator, ExpressionDefinition operant) {
        CalculationExpression calExp;
        if(operant instanceof CalculationExpression) {
            calExp = ((CalculationExpression)operant);
        }else{
            calExp = new CalculationExpression();
            calExp.addFirst(operant);
        }
        calExp.addFirst(new OperatorExpression(operator));
        return calExp;
    }
}
