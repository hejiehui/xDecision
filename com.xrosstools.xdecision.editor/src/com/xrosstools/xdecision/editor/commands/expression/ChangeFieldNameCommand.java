package com.xrosstools.xdecision.editor.commands.expression;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;

public class ChangeFieldNameCommand extends Command{
    private ExtensibleExpression expression;
    private ExpressionDefinition oldExp;
    private ExpressionDefinition childExp;
    
    
    public ChangeFieldNameCommand(ExtensibleExpression expression, ExpressionDefinition childExp) {
        this.expression = expression;
        this.childExp = childExp;
        oldExp = expression.getChild();
    }

    public void execute() {
        expression.setChild(childExp);
    }

    public String getLabel() {
        return "Change child";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        expression.setChild(oldExp);
    }
}
