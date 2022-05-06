package com.xrosstools.xdecision.idea.editor.parts;

import com.xrosstools.gef.figures.*;
import com.xrosstools.gef.parts.ConnectionEditPart;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.EditPolicy;
import com.xrosstools.gef.routers.BendpointConnectionRouter;
import com.xrosstools.xdecision.idea.editor.figures.BranchConditionFigure;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.idea.editor.policies.DecisionTreeNodeConnectionEditPolicy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DecisionTreeNodeConnectionPart extends ConnectionEditPart implements PropertyChangeListener{
    private BranchConditionFigure condition;

    protected List getModelChildren() {
        List children = new ArrayList();
        DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();
        if(nodeConn.getExpression() != null)
            children.add(nodeConn.getExpression());

        return children;
    }

    public void addChildVisual(EditPart childEditPart, int index) {
        Figure childFigure = childEditPart.getFigure();
        if(childEditPart.getModel() instanceof ExpressionDefinition)
            condition.setExpressionFigure(childFigure);
    }


    protected Figure createFigure() {
        Connection conn = new Connection();
        conn.setTargetDecoration(new ArrowDecoration());
        conn.setRouter(new BendpointConnectionRouter());
        conn.setForegroundColor(ColorConstants.black);
        
        DecisionTreeNodeConnection nodeConn = (DecisionTreeNodeConnection)getModel();

        condition = new BranchConditionFigure();
        conn.add(condition, new DecisionTreeConditionLocator(nodeConn));

        return conn;
    }

    public Figure getContentPane() {
        return condition;
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
        refresh();
    }

    protected void refreshVisuals() {
        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection) getModel();
        condition.setOperator(conn.getOperator());
        boolean opaque = conn.getOperator() != null || !(conn.getExpression() == null || conn.getExpression().toString().equals(""));
        condition.setOpaque(opaque);

        conn.setActualWidth(opaque ? condition.getPreferredSize().width : 0);
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
