package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.expression.BracktExpression;
import com.xrosstools.xdecision.editor.model.expression.CompositeExpression;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;

public class ChangeChildCommand extends Command{
    private Object parentModel;
    private ExpressionDefinition oldExp;
    private ExpressionDefinition newExp;
    
    
    public ChangeChildCommand(Object parentModel, ExpressionDefinition oldExp, ExpressionDefinition newExp) {
        this.parentModel = parentModel;
        this.newExp = newExp;
        this.oldExp = oldExp;
    }
    
    public static void setChild(Object parentModel, ExpressionDefinition oldExp, ExpressionDefinition newExp) {
        if(parentModel instanceof CompositeExpression) {
            CompositeExpression parent = (CompositeExpression)parentModel;
            parent.set(parent.indexOf(oldExp), newExp);
            return;
        }
        
        if(parentModel instanceof ElementExpression) {
            ElementExpression parent = (ElementExpression)parentModel;
            parent.setIndexExpression(newExp);
            return;
        }
        
        if(parentModel instanceof ExtensibleExpression) {
            ((ExtensibleExpression)parentModel).setChild(newExp);
            return;
        }
        
        if(parentModel instanceof DecisionTreeNode) {
            ((DecisionTreeNode)parentModel).setNodeExpression(newExp);
            return;
        }
        
        if(parentModel instanceof DecisionTreeNodeConnection) {
            ((DecisionTreeNodeConnection)parentModel).setExpression(newExp);
            return;
        }

        if(parentModel instanceof BracktExpression) {
            ((BracktExpression)parentModel).setEnclosedExpression(newExp);
            return;
        }

        throw new IllegalArgumentException("type not supported: " + parentModel.getClass());
    }

    public void execute() {
        setChild(parentModel, oldExp, newExp);
    }

    public String getLabel() {
        return "Change child";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        setChild(parentModel, newExp, oldExp);
    }
}
