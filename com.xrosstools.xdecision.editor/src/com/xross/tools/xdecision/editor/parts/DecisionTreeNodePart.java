package com.xross.tools.xdecision.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;

import com.xross.tools.xdecision.editor.figures.DecisionTreeNodeFigure;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.model.DecisionTreeNode;
import com.xross.tools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xross.tools.xdecision.editor.policies.DecisionTreeGraphicNodeEditPolicy;
import com.xross.tools.xdecision.editor.policies.DecisionTreeNodeDirectEditPolicy;
import com.xross.tools.xdecision.editor.policies.DecisionTreeNodeEditPolicy;

public class DecisionTreeNodePart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {
    private DirectEditManager manager;
	protected IFigure createFigure() {
        return new DecisionTreeNodeFigure();
    }
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new ChopboxAnchor(getFigure());
	}

	public void performRequest(Request req) {
//		if (req.getType() == RequestConstants.REQ_DIRECT_EDIT){
//            if (manager == null) {
//                DecisionTreeNodeFigure figure = (DecisionTreeNodeFigure) getFigure();
//                manager = new DecisionTreeNodeDirectEditManager(this, ((DecisionTreeDiagram)getParent().getModel()).getFactors(), TextCellEditor.class, new DecisionTreeNodeCellEditorLocator(figure));
//            }
//            manager.show();
//		}
	}
	
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DecisionTreeNodeDirectEditPolicy(((DecisionTreeDiagram)getParent().getModel()).getFactors()));
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DecisionTreeNodeEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new DecisionTreeGraphicNodeEditPolicy());
	}
	
    protected List<DecisionTreeNodeConnection> getModelSourceConnections() {
    	return ((DecisionTreeNode)getModel()).getOutputs();
    }

    protected List<DecisionTreeNodeConnection> getModelTargetConnections() {
    	ArrayList<DecisionTreeNodeConnection> inputs = new ArrayList<DecisionTreeNodeConnection>();
    	if(((DecisionTreeNode)getModel()).getInput() != null)
    		inputs.add(((DecisionTreeNode)getModel()).getInput());
    	return inputs;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(DecisionTreeNode.PROP_LOCATION))
            refreshVisuals();
        else if (evt.getPropertyName().equals(DecisionTreeNode.PROP_FACTOR_ID))
            refreshVisuals();
        else if (evt.getPropertyName().equals(DecisionTreeNode.PROP_DECISION_ID))
            refreshVisuals();
        else if (evt.getPropertyName().equals(DecisionTreeNode.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(DecisionTreeNode.PROP_OUTPUTS))
            refreshSourceConnections();

    }
    
    public void activate() {
    	super.activate();
    	((DecisionTreeNode) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	((DecisionTreeNode) getModel()).getListeners().removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
    	DecisionTreeNode node = (DecisionTreeNode) getModel();
    	DecisionTreeNodeFigure figure = (DecisionTreeNodeFigure)getFigure();

		Point loc = node.getLocation();
		Dimension size = node.getSize();
        Rectangle rectangle = new Rectangle(loc, size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
    	
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
