package com.xrosstools.xdecision.idea.editor.parts.expression;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.EditPolicy;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.idea.editor.policies.DecisionTreeNodeEditPolicy;

public abstract class BaseExpressionPart extends EditPart implements PropertyChangeListener {
    protected EditPolicy createEditPolicy() {
        return new DecisionTreeNodeEditPolicy();
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }

    public void activate() {
//        super.activate();
        ((ExpressionDefinition) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
//        super.deactivate();
        ((ExpressionDefinition) getModel()).getListeners().removePropertyChangeListener(this);
    }
}
