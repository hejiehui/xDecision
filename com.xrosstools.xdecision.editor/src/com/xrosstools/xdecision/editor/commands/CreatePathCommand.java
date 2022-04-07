package com.xrosstools.xdecision.editor.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeManager;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class CreatePathCommand extends Command {
	private DecisionTreeNodeConnection path;
	private DecisionTreeNode parent;
	private DecisionTreeNode child;

	@Override
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
		DecisionTreeManager mgr = parent.getDecisionTreeManager();
		
//		if(parent.getFactorId() == -1)
//		    return;
//		
//		List<String> values = new ArrayList<String>(Arrays.asList(mgr.getFactorValues(parent.getFactorId())));
//		for (DecisionTreeNodeConnection path : parent.getOutputs()) {
//		    if(path.getValueId() != -1)
//		        values.remove(mgr.getFactorValue(parent.getFactorId(), path.getValueId()));
//		}
//
//		// Assign first unused factor value
//		if(values.size() > 0)
//		    path.setValueId(mgr.getFactorValueId(parent.getFactorId(), values.get(0)));
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