package com.xrosstools.xdecision.idea.editor.parts.expression;

import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionDefinition;

import java.beans.PropertyChangeListener;

public abstract class BaseExpressionPart extends AbstractGraphicalEditPart implements PropertyChangeListener {
    //Do not remove the following. It allows refresh when property change
    public void activate() {
        super.activate();
        ((ExpressionDefinition) getModel()).getListeners().addPropertyChangeListener(this);
    }

    //Do not remove the following. It allows refresh when property change
    public void deactivate() {
        super.deactivate();
        ((ExpressionDefinition) getModel()).getListeners().removePropertyChangeListener(this);
    }
}
