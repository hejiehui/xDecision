package com.xross.tools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xdecision.editor.model.DecisionTreeNodeConnection;

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
