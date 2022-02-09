package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.UserDefinedEnum;

public class CreateEnumCommand extends InputTextCommand{
    private DecisionTreeDiagram diagram;
    private UserDefinedEnum udfEnum;
    
    public CreateEnumCommand(DecisionTreeDiagram diagram){
        this.diagram= diagram;
    }
    
    public void execute() {
        udfEnum = new UserDefinedEnum();
        udfEnum.setName(getInputText());
        redo();
    }

    public String getLabel() {
        return "Create new enum";
    }

    public void redo() {
        diagram.getUserDefinedEnums().add(udfEnum);
    }

    public void undo() {
        diagram.getUserDefinedEnums().remove(udfEnum);
    }
}
