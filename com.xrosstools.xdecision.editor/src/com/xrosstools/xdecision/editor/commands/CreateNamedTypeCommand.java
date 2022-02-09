package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedType;

public class CreateNamedTypeCommand extends CreateElementCommand{
    private DataType fieldType;
    
    public CreateNamedTypeCommand(NamedElementContainer container, DataType fieldType){
        super(container);
        this.fieldType = fieldType;
    }
    
    public void execute() {
        super.execute();
        ((NamedType)newElement).setType(fieldType);
    }
}