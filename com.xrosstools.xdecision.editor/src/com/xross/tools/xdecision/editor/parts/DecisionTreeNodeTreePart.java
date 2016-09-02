package com.xross.tools.xdecision.editor.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xross.tools.xdecision.editor.Activator;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.model.DecisionTreeNode;
import com.xross.tools.xdecision.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeNodeTreePart extends AbstractTreeEditPart {
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

    protected String getText() {
    	DecisionTreeNode node = (DecisionTreeNode)getModel();
    	
    	DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getContents().getModel();
    	
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
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(com.xross.tools.xdecision.editor.Activator.NODE);
    }
}
