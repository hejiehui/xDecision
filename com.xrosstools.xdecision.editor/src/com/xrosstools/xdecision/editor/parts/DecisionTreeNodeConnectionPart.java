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
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import com.xrosstools.xdecision.editor.figures.BranchConditionFigure;
import com.xrosstools.xdecision.editor.figures.DecisionTreeNodeFigure;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.policies.DecisionTreeNodeConnectionEditPolicy;

public class DecisionTreeNodeConnectionPart extends AbstractConnectionEditPart implements PropertyChangeListener{
    private PolylineConnection conn = new PolylineConnection();
	private BranchConditionFigure condition;
	
    protected List getModelChildren() {
        List children = new ArrayList();
        DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
        if(nodeConn.getExpressionReference().getExpression() != null)
            children.add(nodeConn.getExpressionReference().getExpression());
        children.add(nodeConn.getOperatorReference());
        return children;
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        if(childEditPart instanceof OperatorReferencePart)
            condition.setOperatorFigure(childFigure);
        else //if(childEditPart instanceof ExpressionReferencePart)
            condition.setExpressionFigure(childFigure);
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
    
    public void performRequest(Request req) {
        if (req.getType() == RequestConstants.REQ_OPEN){
//            DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
//            int factorId = nodeConn.getParent().getFactorId();
//            if(factorId == -1)
//                return;
//            
//            DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getContents().getModel();
//            
//            DecisionTreeFactor factor = diagram.getFactors().get(factorId);
//            
//            getViewer().getEditDomain().getCommandStack().execute(DecisionTreeCreateValueAction.createAndSetValueCommand(factor, nodeConn));
        }
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
        refresh();
//    	DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
//        int valueId = nodeConn.getValueId();
//        int factorId = nodeConn.getParent().getFactorId();
//        
//        DecisionTreeDiagram diagram = (DecisionTreeDiagram)getRoot().getContents().getModel();
//        setLabelText(valueId, factorId, diagram);
    }
    
//    protected void refreshVisuals() {
//        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection) getModel();
//        condition.setCondition(conn.getOperator());
//    }
}
