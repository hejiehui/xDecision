package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.UserDefinedType;

public class DeleteMethodCommand extends Command{
    private UserDefinedType parent;
    private MethodDefinition method;
    
    public DeleteMethodCommand(
            UserDefinedType parent, 
            MethodDefinition field){
        this.parent = parent;
        this.method = field;
    }
    
    public void execute() {
        parent.getMethods().remove(method);
    }

    public String getLabel() {
        return "Delete methos";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        parent.getMethods().add(method);
    }
}
