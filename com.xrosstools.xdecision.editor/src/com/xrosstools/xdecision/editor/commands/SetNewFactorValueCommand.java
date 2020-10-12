package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class SetNewFactorValueCommand extends Command{
    private DecisionTreeNodeConnection connection;
    private int oldValueId;
    private int newValueId;
    
    public SetNewFactorValueCommand(DecisionTreeNodeConnection connection, int newValueId){
        this.connection = connection;
        this.newValueId = newValueId;
        oldValueId = connection.getValueId();
    }
    
    public void execute() {
        connection.setValueId(newValueId);
    }

    public String getLabel() {
        return "Set factor value";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        connection.setValueId(oldValueId);
    }
}
