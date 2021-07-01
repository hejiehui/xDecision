package com.xrosstools.xdecision.editor.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xrosstools.xdecision.editor.Activator;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.DecisionTreeRoot;

public class DecisionTreeRootTreePart extends AbstractTreeEditPart {
    public DecisionTreeRootTreePart(Object model) {
        super(model);
    }
    
    protected List<DecisionTreeNode> getModelChildren() {
    	DecisionTreeRoot root = (DecisionTreeRoot)getModel();
    	
    	DecisionTreeNode node = root.getRootNode();
    	List<DecisionTreeNode> chidren = new ArrayList<DecisionTreeNode>();
    	for(DecisionTreeNodeConnection path : node.getOutputs())
    		chidren.add(path.getChild());
    	return chidren;
    }

    protected String getText() {
    	DecisionTreeNode node = ((DecisionTreeRoot)getModel()).getRootNode();
        return node.getOutlineText();
    }
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(com.xrosstools.xdecision.editor.Activator.TREE);
    }
}
