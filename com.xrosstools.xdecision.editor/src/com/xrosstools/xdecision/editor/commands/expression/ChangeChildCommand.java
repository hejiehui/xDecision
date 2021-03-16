package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;

public class ChangeChildCommand extends Command{
    private Object parentModel;
    private ExpressionDefinition oldExp;
    private ExpressionDefinition childExp;
    
    
    public ChangeChildCommand(Object parentModel, ExpressionDefinition childExp) {
        this.parentModel = parentModel;
        this.childExp = childExp;
        
        oldExp = getChild(parentModel);
    }
    
    public static ExpressionDefinition getChild(Object parentModel) {
        if(parentModel instanceof ExtensibleExpression)
            return ((ExtensibleExpression)parentModel).getChild();
        else
            return ((DecisionTreeNode)parentModel).getNodeExpression();
    }
    
    public static void setChild(Object parentModel, ExpressionDefinition childExp) {
        if(parentModel instanceof ExtensibleExpression)
            ((ExtensibleExpression)parentModel).setChild(childExp);
        else
            ((DecisionTreeNode)parentModel).setNodeExpression(childExp);
    }

    public void execute() {
        setChild(parentModel, childExp);
    }

    public String getLabel() {
        return "Change child";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        if(parentModel instanceof ExtensibleExpression)
            ((ExtensibleExpression)parentModel).setChild(oldExp);
        else
            ((DecisionTreeNode)parentModel).setNodeExpression(oldExp);
    }
}
