package com.xrosstools.xdecision.idea.editor.treeparts;

import java.util.ArrayList;
import java.util.List;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeRoot;

public class DecisionTreeRootTreePart extends TreeEditPart {
    protected List<Object> getModelChildren() {
    	DecisionTreeRoot root = (DecisionTreeRoot)getModel();
    	
    	DecisionTreeNode node = root.getRootNode();
    	List<Object> chidren = new ArrayList<>();
    	for(DecisionTreeNodeConnection path : node.getOutputs())
    		chidren.add(path.getChild());
    	return chidren;
    }

    public String getText() {
    	DecisionTreeNode node = ((DecisionTreeRoot)getModel()).getRootNode();
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
