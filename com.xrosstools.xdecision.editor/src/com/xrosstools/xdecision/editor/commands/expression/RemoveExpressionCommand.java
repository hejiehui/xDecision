package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.expression.BracktExpression;
import com.xrosstools.xdecision.editor.model.expression.CalculationExpression;
import com.xrosstools.xdecision.editor.model.expression.CompositeExpression;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.OperatorExpression;
import com.xrosstools.xdecision.editor.model.expression.ParameterExpression;
import com.xrosstools.xdecision.editor.model.expression.ParameterListExpression;

public class RemoveExpressionCommand extends Command{
    private Object parentModel;
    private ExpressionDefinition oldExp;
    
    private int oldExpIndex;
    private OperatorExpression oldOpr;

    public RemoveExpressionCommand(EditPart expPart) {
        if(expPart.getParent().getModel() instanceof ParameterExpression) {
            this.parentModel = (ParameterListExpression)expPart.getParent().getParent().getModel();
            this.oldExp = (ExpressionDefinition)expPart.getParent().getModel();
        } else {
            this.parentModel = expPart.getParent().getModel();
            this.oldExp = (ExpressionDefinition)expPart.getModel();
        }
    }
    
    public void removeChild() {
        if(parentModel instanceof ParameterListExpression) {
            ParameterListExpression parent = (ParameterListExpression)parentModel;
            oldExpIndex = parent.indexOf(oldExp);
            parent.remove(oldExp);
            return;
        }

        if(parentModel instanceof CalculationExpression) {
            CalculationExpression parent = (CalculationExpression)parentModel;
            oldExpIndex = parent.indexOf(oldExp);

            parent.remove(oldExp);
            //First, we need to check following operator
            if(oldExpIndex == 0 && parent.size() > 0 && parent.getExpression(oldExpIndex) instanceof OperatorExpression) {
                oldOpr = (OperatorExpression) parent.getExpression(oldExpIndex);
            } //Last, we need to check previours operator
            else if(oldExpIndex > 0 && parent.size() > 0 && parent.getExpression(oldExpIndex - 1) instanceof OperatorExpression) {
                oldOpr = (OperatorExpression)parent.getExpression(oldExpIndex - 1);
            }

            if(oldOpr != null)
                parent.remove(oldOpr);
            return;
        }
        
        if(parentModel instanceof ElementExpression) {
            ElementExpression parent = (ElementExpression)parentModel;
            parent.setIndexExpression(null);
            return;
        }
        
        if(parentModel instanceof ExtensibleExpression) {
            ((ExtensibleExpression)parentModel).setChildExpression(null);
            return;
        }
        
        if(parentModel instanceof DecisionTreeNode) {
            ((DecisionTreeNode)parentModel).setNodeExpression(null);
            return;
        }
        
        if(parentModel instanceof DecisionTreeNodeConnection) {
            ((DecisionTreeNodeConnection)parentModel).setExpression(null);
            return;
        }
        
        if(parentModel instanceof BracktExpression) {
            ((BracktExpression)parentModel).setInnerExpression(null);
            return;
        }

        throw new IllegalArgumentException("type not supported: " + parentModel.getClass());
    }

    public void setChild() {
        if(parentModel instanceof CompositeExpression) {
            CompositeExpression parent = (CompositeExpression)parentModel;
            if(oldExpIndex == 0 && oldOpr != null) {
                parent.add(oldExpIndex, oldExp);
                parent.add(1, oldOpr);
            }
            else if(oldExpIndex > 0 && oldOpr != null) {
                parent.add(oldExpIndex - 1, oldOpr);
                parent.add(oldExpIndex, oldExp);
            }
            return;
        }
    }

    public void execute() {
        removeChild();
    }

    public String getLabel() {
        return "Change child";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        setChild();
    }
}
