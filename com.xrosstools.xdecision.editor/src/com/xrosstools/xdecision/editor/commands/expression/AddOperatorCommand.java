package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.expression.CalculationExpression;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;
import com.xrosstools.xdecision.editor.model.expression.NumberExpression;
import com.xrosstools.xdecision.editor.model.expression.OperatorEnum;
import com.xrosstools.xdecision.editor.model.expression.OperatorExpression;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class AddOperatorCommand extends Command{
    private ExpressionDefinition operant;
    private OperatorExpression operatorExp;
    private NumberExpression zeroExp;
    private Object parentModel;
    private ExpressionDefinition newExp;
        
    public AddOperatorCommand(EditPart expPart, OperatorEnum operator){
        EditPart topExp = findTopExpressionPart(expPart);
        this.operant = (ExpressionDefinition)topExp.getModel();
        this.operatorExp = new OperatorExpression(operator);
        zeroExp = new NumberExpression(0);
        this.parentModel = topExp.getParent().getModel();
    }
    
    public static EditPart findTopExpressionPart(EditPart expPart) {
        EditPart parentPart = expPart.getParent();
        Object parantExp = parentPart.getModel();
        if(parantExp instanceof VariableExpression || parantExp instanceof MethodExpression)
            return findTopExpressionPart(parentPart);
        else
            return expPart;
    }
 
    public void execute() {
        if(parentModel instanceof CalculationExpression) {
            CalculationExpression calExp = (CalculationExpression)parentModel;
            int index = calExp.indexOf(operant);
            if(index == calExp.size() - 1) {
                calExp.add(operatorExp);
                calExp.add(zeroExp);
            }else {
                calExp.add(index + 1, operatorExp);
                calExp.add(index + 2, zeroExp);
            }
        } else {
            CalculationExpression calExp = new CalculationExpression();
            calExp.add(operant);
            calExp.add(operatorExp);
            calExp.add(zeroExp);
            ChangeChildCommand.setChild(parentModel, operant, calExp);
            newExp = calExp;
        }
    }

    public String getLabel() {
        return "add operator";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        if(parentModel instanceof CalculationExpression) {
            CalculationExpression calExp = (CalculationExpression)parentModel;
            calExp.remove(operatorExp);
            calExp.remove(zeroExp);
        } else {
            ChangeChildCommand.setChild(parentModel, newExp, operant);
        }
    }
}
