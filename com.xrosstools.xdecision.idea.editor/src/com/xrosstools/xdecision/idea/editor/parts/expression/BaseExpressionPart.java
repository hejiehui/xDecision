package com.xrosstools.xdecision.idea.editor.parts.expression;

import java.beans.PropertyChangeListener;

import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionDefinition;

public abstract class BaseExpressionPart extends AbstractGraphicalEditPart implements PropertyChangeListener {
    public void activate() {
        super.activate();
        ((ExpressionDefinition) getModel()).getListeners().addPropertyChangeListener(this);
    }

    public void deactivate() {
        super.deactivate();
        ((ExpressionDefinition) getModel()).getListeners().removePropertyChangeListener(this);
    }
}
