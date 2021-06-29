package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class ChangeNodeFactorCommand extends Command{
    private DecisionTreeNode node;
    private ExpressionDefinition oldExp;
    private ExpressionDefinition newExp;
    
    public ChangeNodeFactorCommand(DecisionTreeNode node, String factorName){
    	this.node = node;
    	oldExp = node.getNodeExpression();
    	newExp = new VariableExpression(factorName);
    }
    
    public void execute() {
        node.setNodeExpression(newExp);
    }

    public String getLabel() {
        return "Change factor id";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	node.setNodeExpression(oldExp);
    }
}
