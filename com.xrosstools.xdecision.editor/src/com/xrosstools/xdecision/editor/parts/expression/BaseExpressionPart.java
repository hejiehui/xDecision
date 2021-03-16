package com.xrosstools.xdecision.editor.parts.expression;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;

public abstract class BaseExpressionPart extends AbstractGraphicalEditPart implements PropertyChangeListener {
    public void performRequest(Request req) {
        if (req.getType() == RequestConstants.REQ_OPEN){
        }
    }
    
    protected void createEditPolicies() {
//      installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new DecisionTreeNodeDirectEditPolicy(getDiagram().getFactors()));
//        installEditPolicy(EditPolicy.COMPONENT_ROLE, new DecisionTreeNodeEditPolicy());
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }
    
    public void activate() {
        super.activate();
        ((ExpressionDefinition) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
        super.deactivate();
        ((ExpressionDefinition) getModel()).getListeners().removePropertyChangeListener(this);
    }
}
