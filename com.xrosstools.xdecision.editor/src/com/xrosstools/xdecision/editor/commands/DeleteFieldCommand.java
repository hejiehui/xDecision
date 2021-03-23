package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.FieldDefinition;

public class DeleteFieldCommand extends Command{
    private DataType parent;
    private FieldDefinition field;
    
    public DeleteFieldCommand(
            DataType parent, 
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
