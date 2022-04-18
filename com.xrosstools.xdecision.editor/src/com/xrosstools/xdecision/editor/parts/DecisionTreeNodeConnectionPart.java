package com.xrosstools.xdecision.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import com.xrosstools.xdecision.editor.figures.BranchConditionFigure;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.policies.DecisionTreeNodeConnectionEditPolicy;

public class DecisionTreeNodeConnectionPart extends AbstractConnectionEditPart implements PropertyChangeListener{
    private PolylineConnection conn;
	private BranchConditionFigure condition;
	
    protected List getModelChildren() {
        List children = new ArrayList();
        DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
        if(nodeConn.getExpression() != null)
            children.add(nodeConn.getExpression());
        
        return children;
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        if(childEditPart.getModel() instanceof ExpressionDefinition)
            condition.setExpressionFigure(childFigure);
    }
    
    public IFigure getContentPane() {
        return condition;
    }

    protected IFigure createFigure() {
        conn = new PolylineConnection();
        conn.setTargetDecoration(new PolygonDecoration());
        conn.setConnectionRouter(new BendpointConnectionRouter());
        conn.setForegroundColor(ColorConstants.black);
        
        condition = new BranchConditionFigure();
        conn.add(condition, new MidpointLocator(conn, 0));
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
    	((DecisionTreeNodeConnection) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	((DecisionTreeNodeConnection) getModel()).getListeners().removePropertyChangeListener(this);
    }
    
    public void propertyChange(PropertyChangeEvent event){
        refresh();
    }
    
    protected void refreshVisuals() {
        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection) getModel();
        condition.setOperator(conn.getOperator());
        boolean opaque = conn.getOperator() != null || !(conn.getExpression() == null || conn.getExpression().toString().equals(""));
        condition.setOpaque(opaque);

        conn.setActualWidth(opaque ? condition.getPreferredSize().width : 0);
    }
}
