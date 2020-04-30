package com.xrosstools.xdecision.idea.editor.parts;

import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.EditPolicy;
import com.xrosstools.xdecision.idea.editor.figures.DecisionTreeNodeFigure;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.policies.DecisionTreeNodeEditPolicy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeNodePart extends EditPart {
	protected Figure createFigure() {
        return new DecisionTreeNodeFigure();
    }

	@Override
	protected EditPolicy createEditPolicy() {
		return new DecisionTreeNodeEditPolicy();
	}

	public List<DecisionTreeNodeConnection> getModelSourceConnections() {
    	return ((DecisionTreeNode)getModel()).getOutputs();
    }

    public List<DecisionTreeNodeConnection> getModelTargetConnections() {
    	ArrayList<DecisionTreeNodeConnection> inputs = new ArrayList<DecisionTreeNodeConnection>();
    	if(((DecisionTreeNode)getModel()).getInput() != null)
    		inputs.add(((DecisionTreeNode)getModel()).getInput());
    	return inputs;
    }

    protected void refreshVisuals() {
    	DecisionTreeNode node = (DecisionTreeNode) getModel();
    	DecisionTreeNodeFigure figure = (DecisionTreeNodeFigure)getFigure();

		figure.setLocation(node.getLocation());
		figure.setSize(node.getSize());

        String factor;
    	if(node.getFactorId() == -1)
    		factor = "";
    	else
    		factor = ((DecisionTreeDiagram)getParent().getModel()).getFactors().get(node.getFactorId()).getFactorName();
    	figure.setFactor(factor);
    	
        String decision;
    	if(node.getDecisionId() == -1)
    		decision = "";
    	else
    		decision = ((DecisionTreeDiagram)getParent().getModel()).getDecisions().get(node.getDecisionId());

    	figure.setDecision(decision);
    }
}
