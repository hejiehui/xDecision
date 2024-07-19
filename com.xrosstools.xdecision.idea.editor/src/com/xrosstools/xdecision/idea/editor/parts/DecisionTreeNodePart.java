package com.xrosstools.xdecision.idea.editor.parts;

import com.xrosstools.idea.gef.commands.CommandChain;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xdecision.idea.editor.commands.CreateNodeCommand;
import com.xrosstools.xdecision.idea.editor.commands.CreatePathCommand;
import com.xrosstools.xdecision.idea.editor.commands.LayoutTreeCommand;
import com.xrosstools.xdecision.idea.editor.figures.DecisionTreeNodeFigure;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.policies.DecisionTreeNodeEditPolicy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeNodePart extends AbstractGraphicalEditPart {
    public DecisionTreeNode getDecisionTreeNode() {
        return (DecisionTreeNode)getModel();
    }
    public List getModelChildren() {
        List children = new ArrayList();
        DecisionTreeNode node = getDecisionTreeNode();

        if(node.getNodeExpression() != null)
            children.add(node.getNodeExpression());

        return children;
    }

    public void addChildVisual(EditPart childEditPart, int index) {
        DecisionTreeNodeFigure figure = (DecisionTreeNodeFigure)getFigure();
        Figure childFigure = ((AbstractGraphicalEditPart)childEditPart).getFigure();

        figure.setExpressionFigure(childFigure);
    }

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

    public void performAction() {
        CommandChain cc = new CommandChain();
        DecisionTreeDiagram diagram = getDiagram();
        DecisionTreeNode child = new DecisionTreeNode();

        cc.add(new LayoutTreeCommand(diagram, diagram.isHorizantal(), diagram.getAlignment()));
        cc.add(new CreateNodeCommand(diagram, child, new Point(400, 400)));

        CreatePathCommand linkNode = new CreatePathCommand(new DecisionTreeNodeConnection(), (DecisionTreeNode)getModel(), child);
        cc.add(linkNode);

        cc.add(new LayoutTreeCommand(diagram, diagram.isHorizantal(), diagram.getAlignment()));

        execute(cc);
    }

    public void activate() {
        super.activate();
        DecisionTreeNode node = (DecisionTreeNode) getModel();
        node.getListeners().addPropertyChangeListener(this);
        node.getDecisionTreeManager().getDecisions().getListeners().addPropertyChangeListener(this);
        if(node.getDecision() != null)
            node.getDecision().getListeners().addPropertyChangeListener(this);
    }

    public void deactivate() {
        super.deactivate();
        DecisionTreeNode node = (DecisionTreeNode) getModel();
        node.getListeners().removePropertyChangeListener(this);
        node.getDecisionTreeManager().getDecisions().getListeners().removePropertyChangeListener(this);
        if(node.getDecision() != null)
            node.getDecision().getListeners().removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
        DecisionTreeNode node = getDecisionTreeNode();
    	DecisionTreeNodeFigure figure = (DecisionTreeNodeFigure)getFigure();

        figure.setDecision(node.getDisplayText());

        Point loc = node.getLocation();
        Dimension size = new Dimension(-1, node.getDecisionTreeManager().getDiagram().getNodeHeight());
        Rectangle rectangle = new Rectangle(loc, size);
        ((AbstractGraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), rectangle);

        node.setSize(new Dimension(figure.getPreferredSize()));
    }

    private DecisionTreeDiagram getDiagram() {
        return (DecisionTreeDiagram)getParent().getModel();
    }
}
