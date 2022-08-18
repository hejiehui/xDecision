package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

public class ReconnectParentCommand extends Command {

	private DecisionTreeNodeConnection path;
	private DecisionTreeNode oldParent;
	private DecisionTreeNode newParent;

	public ReconnectParentCommand(DecisionTreeNodeConnection path, DecisionTreeNode newParent){
		this.path = path;
		this.newParent = newParent;
		oldParent = path.getParent();
	}

	public String getLabel() {
		return "Reconnect Parent";
	}

	public boolean canExecute() {
		if (path.getChild().equals(newParent))
			return false;
			
		if (path.getParent().equals(newParent))
			return false;
		
		return true;
	}

	public void execute() {
		oldParent.removeOutput(path);
		path.setParent(newParent);
		newParent.addOutput(path);
	}

	public void undo() {
		newParent.removeOutput(path);
		path.setParent(oldParent);
		oldParent.addOutput(path);
	}
}