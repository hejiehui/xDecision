package com.xrosstools.xdecision.idea.editor.treeparts;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

import java.util.ArrayList;
import java.util.List;

public class DecisionTreeNodeTreePart extends TreeEditPart {
    protected List<DecisionTreeNode> getModelChildren() {
    	DecisionTreeNode node = (DecisionTreeNode)getModel();
    	List<DecisionTreeNode> children = new ArrayList<DecisionTreeNode>();
    	for(DecisionTreeNodeConnection path : node.getOutputs())
    		children.add(path.getChild());
    	return children;
    }

    public String getText() {
    	DecisionTreeNode node = (DecisionTreeNode)getModel();
    	DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getModel();
    	
        String factor;
    	if(node.getFactorId() == -1)
    		factor = "Not specified";
    	else
    		factor = diagram.getFactors().get(node.getFactorId()).getFactorName();

        String decision;
    	if(node.getDecisionId() == -1)
    		decision = "No decision";
    	else
    		decision = diagram.getDecisions().get(node.getDecisionId());

        return "[" + decision + "] " + factor;
    }
}
