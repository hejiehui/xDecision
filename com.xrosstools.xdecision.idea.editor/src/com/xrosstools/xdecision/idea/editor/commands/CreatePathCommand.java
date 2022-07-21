package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeManager;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePathCommand extends Command {
	private DecisionTreeNodeConnection path;
	private DecisionTreeNode parent;
	private DecisionTreeNode child;

    public CreatePathCommand(DecisionTreeNodeConnection path, DecisionTreeNode parent, DecisionTreeNode child) {
        this.path = path;
        this.parent = parent;
        this.child = child;

    }
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
//        path.setParent(parent);
//        path.setChild(child);
	}

	public String getLabel() {
		return "Create Path";
	}

	public void undo() {
		parent.removeOutput(path);
		child.setInput(null);
//		parent.removeOutput(path);
//		child.setInput(null);
	}
}