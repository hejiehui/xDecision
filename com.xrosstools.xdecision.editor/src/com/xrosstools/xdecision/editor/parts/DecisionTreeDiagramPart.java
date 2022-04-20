package com.xrosstools.xdecision.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xdecision.editor.layout.LayoutAlgorithm;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeManager;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.policies.DecisionTreeDiagramLayoutPolicy;

public class DecisionTreeDiagramPart extends AbstractGraphicalEditPart implements PropertyChangeListener{
//	private boolean isLayoutUpdated;
    protected List<DecisionTreeNode> getModelChildren() {
    	DecisionTreeDiagram diagram = (DecisionTreeDiagram)getModel();
//    	if(!isLayoutUpdated){
//    		new LayoutAlgorithm().layout(diagram);
//    		isLayoutUpdated = true;
//    	}
    	
    	DecisionTreeManager manager = new DecisionTreeManager(diagram);
    	for(DecisionTreeNode node: diagram.getNodes())
    		if(!node.isDecisionTreeManagerSet())
    			node.setDecisionTreeManager(manager);
        return diagram.getNodes();
    }

	protected IFigure createFigure() {
        Figure figure = new FreeformLayer();
        figure.setLayoutManager(new FreeformLayout());
        return figure;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if (DecisionTreeDiagram.LAYOUT.equals(prop)){
		    new LayoutAlgorithm().layout((DecisionTreeDiagram)getModel());
		}

		refreshChildren();
	}
	
	public void activate() {
		super.activate();
		((DecisionTreeDiagram)getModel()).getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((DecisionTreeDiagram)getModel()).getListeners().removePropertyChangeListener(this);
	}

    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new DecisionTreeDiagramLayoutPolicy());
    }
}
