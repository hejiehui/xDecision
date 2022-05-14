package com.xrosstools.xdecision.idea.editor.parts;

import java.beans.PropertyChangeEvent;
import java.util.List;

import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.figures.FreeformLayout;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.EditPolicy;
import com.xrosstools.xdecision.idea.editor.layout.LayoutAlgorithm;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeManager;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.policies.DecisionTreeDiagramLayoutPolicy;

public class DecisionTreeDiagramPart extends EditPart {
    private LayoutAlgorithm layout = new LayoutAlgorithm();
    protected List<DecisionTreeNode> getModelChildren() {
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)getModel();

        DecisionTreeManager manager = new DecisionTreeManager(diagram);
        for(DecisionTreeNode node: diagram.getNodes())
            if(!node.isDecisionTreeManagerSet())
                node.setDecisionTreeManager(manager);
        return diagram.getNodes();
    }

	protected Figure createFigure() {
        Figure figure = new Figure();
        figure.setLayoutManager(new FreeformLayout());
        return figure;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
        if (DecisionTreeDiagram.LAYOUT.equals(prop)){
            layout.layout((DecisionTreeDiagram)getModel());
        }
		super.propertyChange(evt);
	}

    protected EditPolicy createEditPolicy() {
        return new DecisionTreeDiagramLayoutPolicy();
    }
}
