package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

public class DeletePathCommand extends Command {
	private DecisionTreeNodeConnection path;
	public DeletePathCommand(DecisionTreeNodeConnection path){
		this.path = path;
	}
	
    public void execute() {
    	path.getParent().removeOutput(path);
        path.getChild().setInput(null);
    }

    public String getLabel() {
        return "Delete path";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	path.getParent().addOutput(path);
        path.getChild().setInput(path);
    }
}
