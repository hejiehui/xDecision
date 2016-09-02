package com.xrosstools.xdecision.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.policies.DecisionTreeNodeConnectionEditPolicy;

public class DecisionTreeNodeConnectionPart extends AbstractConnectionEditPart implements PropertyChangeListener{
	private Label label;
    protected IFigure createFigure() {
        PolylineConnection conn = new PolylineConnection();
        conn.setTargetDecoration(new PolygonDecoration());
        conn.setConnectionRouter(new BendpointConnectionRouter());
        conn.setForegroundColor(ColorConstants.black);
        
        DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
        int valueId = nodeConn.getValueId();
        int factorId = nodeConn.getParent().getFactorId();
        
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getContents().getModel();
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
        conn.add(label, new MidpointLocator(conn, 0));
        return conn;
    }

    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new DecisionTreeNodeConnectionEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
    }

    public void setSelected(int value) {
        super.setSelected(value);
        if (value != EditPart.SELECTED_NONE)
            ((PolylineConnection) getFigure()).setLineWidth(2);
        else
            ((PolylineConnection) getFigure()).setLineWidth(1);
    }
    
    public void activate() {
    	super.activate();
    	((DecisionTreeDiagram)getRoot().getContents().getModel()).getListeners().addPropertyChangeListener(this);
    	((DecisionTreeNodeConnection) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	((DecisionTreeDiagram)getRoot().getContents().getModel()).getListeners().removePropertyChangeListener(this);
    	((DecisionTreeNodeConnection) getModel()).getListeners().removePropertyChangeListener(this);
    }
    
    public void propertyChange(PropertyChangeEvent event){
    	DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
        int valueId = nodeConn.getValueId();
        int factorId = nodeConn.getParent().getFactorId();
        
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getContents().getModel();
    	label.setText(diagram.getFactors().get(factorId).getFactorValues()[valueId]);
    }
}
