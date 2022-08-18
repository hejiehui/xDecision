package com.xrosstools.xdecision.idea.editor.commands.definition;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.definition.DataType;
import com.xrosstools.xdecision.idea.editor.model.definition.MethodDefinition;

public class DeleteMethodCommand extends Command {
    private DataType parent;
    private MethodDefinition method;
    
    public DeleteMethodCommand(
            DataType parent, 
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
