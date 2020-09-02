package com.xrosstools.xdecision.idea.editor.parts;

import com.xrosstools.gef.figures.*;
import com.xrosstools.gef.figures.Label;
import com.xrosstools.gef.parts.ConnectionEditPart;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.EditPolicy;
import com.xrosstools.gef.routers.BendpointConnectionRouter;
import com.xrosstools.gef.routers.MidpointLocator;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.policies.DecisionTreeNodeConnectionEditPolicy;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DecisionTreeNodeConnectionPart extends ConnectionEditPart implements PropertyChangeListener{
	private Label label;
    protected Figure createFigure() {
        Connection conn = new Connection();
        conn.setTargetDecoration(new ArrowDecoration());
        conn.setRouter(new BendpointConnectionRouter());
        conn.setForeground(Color.black);
        
        DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
        int valueId = nodeConn.getValueId();
        int factorId = nodeConn.getParent().getFactorId();
        
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getModel();
        label = new Label();
        if(factorId == -1 || valueId == -1)
        	label.setText("Not specified");
        else{
        	if(diagram.getFactors().get(factorId).getFactorValues().length <= valueId)
        		label.setText("Incorrect value" + valueId);
        	else
        		label.setText(diagram.getFactors().get(factorId).getFactorValues()[valueId]);
        }
        	
        label.setOpaque(true);
        conn.add(label, new MidpointLocator());
        return conn;
    }

    protected EditPolicy createEditPolicy() {
        return new DecisionTreeNodeConnectionEditPolicy();
    }

    public void setSelected(int value) {
        if (value == EditPart.SELECTED)
            getFigure().setLineWidth(2);
        else
            getFigure().setLineWidth(1);
    }

    public void propertyChange(PropertyChangeEvent event){
    	DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
        int valueId = nodeConn.getValueId();
        int factorId = nodeConn.getParent().getFactorId();
        
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getModel();
    	label.setText(diagram.getFactors().get(factorId).getFactorValues()[valueId]);
    }

    @Override
    public Object getSourceModel() {
        return ((DecisionTreeNodeConnection)getModel()).getParent();
    }

    @Override
    public Object getTargetModel() {
        return ((DecisionTreeNodeConnection)getModel()).getChild();
    }
}
