package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeNodeTreePart extends TreeEditPart implements PropertyChangeListener {
	public DecisionTreeNodeTreePart(Object model) {
		super(model);
	}

	protected List<DecisionTreeNode> getModelChildren() {
		DecisionTreeNode node = (DecisionTreeNode)getModel();
		List<DecisionTreeNode> chidren = new ArrayList<DecisionTreeNode>();
		for(DecisionTreeNodeConnection path : node.getOutputs())
			chidren.add(path.getChild());
		return chidren;
	}

	public void activate() {
//		super.activate();
		((DecisionTreeNode) getModel()).getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
//		super.deactivate();
		((DecisionTreeNode) getModel()).getListeners().removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}

	public String getText() {
		DecisionTreeNode node = (DecisionTreeNode)getModel();
		return node.getOutlineText();
	}
}
