package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.expression.BracktExpression;
import com.xrosstools.xdecision.editor.model.expression.CompositeExpression;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.OperatorExpression;

public class RemoveExpressionCommand extends Command{
    private Object parentModel;
    private ExpressionDefinition oldExp;
    
    private int oldIndex;
    private OperatorExpression oldOpr;
    
    public RemoveExpressionCommand(EditPart expPart) {
        this.parentModel = expPart.getParent().getModel();
        this.oldExp = (ExpressionDefinition)expPart.getModel();
    }
    
    public void removeChild() {
        if(parentModel instanceof CompositeExpression) {
            CompositeExpression parent = (CompositeExpression)parentModel;
            oldIndex = parent.indexOf(oldExp);
            parent.remove(oldExp);
            if(oldIndex != 0 && parent.getExpression(oldIndex - 1) instanceof OperatorExpression) {
                oldOpr = (OperatorExpression)parent.getExpression(oldIndex - 1);
                parent.remove(oldOpr);
            }
            return;
        }
        
        if(parentModel instanceof ElementExpression) {
            ElementExpression parent = (ElementExpression)parentModel;
            parent.setIndexExpression(null);
            return;
        }
        
        if(parentModel instanceof ExtensibleExpression) {
            ((ExtensibleExpression)parentModel).setChild(null);
            return;
        }
        
        if(parentModel instanceof DecisionTreeNode) {
            ((DecisionTreeNode)parentModel).setNodeExpression(null);
            return;
        }
        
        if(parentModel instanceof BracktExpression) {
            ((BracktExpression)parentModel).setEnclosedExpression(null);
            return;
        }

        throw new IllegalArgumentException("type not supported: " + parentModel.getClass());
    }

    public void setChild() {
        if(parentModel instanceof CompositeExpression) {
            CompositeExpression parent = (CompositeExpression)parentModel;
            if(oldOpr != null)
                parent.add(oldIndex - 1, oldOpr);
            parent.add(oldIndex, oldExp);
            return;
        }
        
        ChangeChildCommand.setChild(parentModel, null, oldExp);
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
