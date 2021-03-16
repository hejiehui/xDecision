package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.UserDefinedType;

public class DeleteFieldCommand extends Command{
    private UserDefinedType parent;
    private FieldDefinition field;
    
    public DeleteFieldCommand(
            UserDefinedType parent, 
            FieldDefinition field){
        this.parent = parent;
        this.field = field;
    }
    
    public void execute() {
        parent.getFields().remove(field);
    }

    public String getLabel() {
        return "Delete field";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        parent.getFields().add(field);
    }
}
