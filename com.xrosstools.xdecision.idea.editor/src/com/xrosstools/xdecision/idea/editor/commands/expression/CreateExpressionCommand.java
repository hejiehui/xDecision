package com.xrosstools.xdecision.idea.editor.commands.expression;


import com.xrosstools.gef.commands.InputTextCommand;
import com.xrosstools.gef.parts.GraphicalEditPart;
import com.xrosstools.xdecision.idea.editor.model.definition.DataType;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.idea.editor.model.expression.NumberExpression;
import com.xrosstools.xdecision.idea.editor.model.expression.StringExpression;

public class CreateExpressionCommand extends InputTextCommand {
    private Object parentModel;
    private ExpressionDefinition oldExp;
    private ExpressionDefinition newExp;
    private DataType type;
    
    public CreateExpressionCommand(GraphicalEditPart expPart, DataType type) {
        GraphicalEditPart topExp = AddOperatorCommand.findTopExpressionPart(expPart);
        this.parentModel = topExp.getParent().getModel();
        this.oldExp = (ExpressionDefinition)topExp.getModel();
        this.type = type;
    }
    
    private ExpressionDefinition createExpression() {
        if(type == DataType.NUMBER_TYPE)
           return new NumberExpression(getInputText());
        
        if(type == DataType.STRING_TYPE)
            return new StringExpression(getInputText());
        
        throw new IllegalArgumentException("type not supported: " + parentModel.getClass());
    }
    
    public void execute() {
        if(newExp == null)
            newExp = createExpression();
        ChangeChildCommand.setChild(parentModel, oldExp, newExp);
    }

    public String getLabel() {
        return "Change child";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        ChangeChildCommand.setChild(parentModel, newExp, oldExp);
    }
}
