package com.xrosstools.xdecision.editor.parts;

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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xdecision.editor.actions.CommandChain;
import com.xrosstools.xdecision.editor.commands.CreateNodeCommand;
import com.xrosstools.xdecision.editor.commands.CreatePathCommand;
import com.xrosstools.xdecision.editor.commands.LayoutTreeCommand;
import com.xrosstools.xdecision.editor.figures.DecisionTreeNodeFigure;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.policies.DecisionTreeGraphicNodeEditPolicy;
import com.xrosstools.xdecision.editor.policies.DecisionTreeNodeEditPolicy;

public class DecisionTreeNodePart extends AbstractGraphicalEditPart implements PropertyChangeListener, NodeEditPart {
    private DecisionTreeNode node;
    
    @Override
    public void setModel(Object model) {
        super.setModel(model);
        node = (DecisionTreeNode)model;
    }
    protected List getModelChildren() {
        List children = new ArrayList();
        
        if(node.getNodeExpression() != null)
            children.add(node.getNodeExpression());

        return children;
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        DecisionTreeNodeFigure figure = (DecisionTreeNodeFigure)getFigure();
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();

        figure.setExpressionFigure(childFigure);
    }
    
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

	//TODO revise this behavior
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN){
		    CommandChain cc = new CommandChain();
            DecisionTreeDiagram diagram = getDiagram();
            DecisionTreeNode child = new DecisionTreeNode();
		    
		    cc.add(new LayoutTreeCommand(diagram, diagram.isHorizantal(), diagram.getAlignment()));
            cc.add(new CreateNodeCommand(diagram, child, new Point(400, 400)));
            
            CreatePathCommand linkNode = new CreatePathCommand();
            linkNode.setParent((DecisionTreeNode)getModel());
            linkNode.setChild(child);
            cc.add(linkNode);

            cc.add(new LayoutTreeCommand(diagram, diagram.isHorizantal(), diagram.getAlignment()));
            
            CommandStack statck = getViewer().getEditDomain().getCommandStack();
            statck.execute(cc);
		}
	}
	
	protected void createEditPolicies() {
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
        if (evt.getPropertyName().equals(DecisionTreeNode.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(DecisionTreeNode.PROP_OUTPUTS))
            refreshSourceConnections();
        else
            refreshVisuals();
    }
    
    public void activate() {
    	super.activate();
    	node.getListeners().addPropertyChangeListener(this);
    	node.getDecisionTreeManager().getDecisions().getListeners().addPropertyChangeListener(this);
    	if(node.getDecision() != null)
    	    node.getDecision().getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	node.getListeners().removePropertyChangeListener(this);
    	node.getDecisionTreeManager().getDecisions().getListeners().removePropertyChangeListener(this);
        if(node.getDecision() != null)
            node.getDecision().getListeners().removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
    	DecisionTreeNodeFigure figure = (DecisionTreeNodeFigure)getFigure();

		Point loc = node.getLocation();
		Dimension size = new Dimension(-1, -1);
        Rectangle rectangle = new Rectangle(loc, size);
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
        
        figure.setDecision(node.getDecision() == null ? "": node.getDecision().getName());
    }

    private DecisionTreeDiagram getDiagram() {
        return (DecisionTreeDiagram)getParent().getModel();
    }
}
