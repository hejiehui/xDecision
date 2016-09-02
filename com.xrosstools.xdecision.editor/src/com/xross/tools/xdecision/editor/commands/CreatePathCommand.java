package com.xross.tools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xdecision.editor.model.DecisionTreeNode;
import com.xross.tools.xdecision.editor.model.DecisionTreeNodeConnection;

public class CreatePathCommand extends Command {
	private DecisionTreeNodeConnection path;
	private DecisionTreeNode parent;
	private DecisionTreeNode child;

	public boolean canExecute() {
		if(child != null && child.getInput() != null)
			return false;
		
		if (parent.equals(child))
			return false;
		
		for (DecisionTreeNodeConnection path : parent.getOutputs()) {
			if (path.getChild().equals(child))
				return false;
		}
		return true;
	}

	public void execute() {
		path = new DecisionTreeNodeConnection(parent, child);
	}

	public void redo() {
		parent.addOutput(path);
		child.setInput(path);
	}

	public void setParent(DecisionTreeNode parent) {
		this.parent = parent;
	}

	public void setChild(DecisionTreeNode child) {
		this.child = child;
	}

	public void undo() {
		parent.removeOutput(path);
		child.setInput(null);
	}
}