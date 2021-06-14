package com.xrosstools.xdecision.editor.commands.expression;

import com.xrosstools.xdecision.editor.commands.InputTextCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.expression.CompositeExpression;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.NumberExpression;
import com.xrosstools.xdecision.editor.model.expression.StringExpression;

public class CreateExpressionCommand extends InputTextCommand {
    private Object parentModel;
    private ExpressionDefinition placeholderExp;
    private ExpressionDefinition newExp;
    private String type;
    
    public CreateExpressionCommand(Object parentModel, ExpressionDefinition placeholderExp, String type) {
        this.parentModel = parentModel;
        this.placeholderExp = placeholderExp;
        this.type = type;
    }
    
    private ExpressionDefinition createExpression() {
        if(type == DataType.NUMBER)
           return new NumberExpression(getInputText()); 
        
        if(type == DataType.STRING)
            return new StringExpression(getInputText());
        
        throw new IllegalArgumentException("type not supported: " + parentModel.getClass());
    }
    
    private void replace(ExpressionDefinition oldExp, ExpressionDefinition newExp) {
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
        
        throw new IllegalArgumentException("type not supported: " + parentModel.getClass());
    }

    public void execute() {
        if(newExp == null)
            newExp = createExpression();
        replace(placeholderExp, newExp);
    }

    public String getLabel() {
        return "Change child";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        replace(newExp, placeholderExp);
    }
}
