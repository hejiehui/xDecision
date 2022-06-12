package com.xrosstools.xdecision.idea.editor.parts.expression;

import java.beans.PropertyChangeListener;

import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionDefinition;

public abstract class BaseExpressionPart extends EditPart implements PropertyChangeListener {
    public void activate() {
        super.activate();
        ((ExpressionDefinition) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
        super.deactivate();
        ((ExpressionDefinition) getModel()).getListeners().removePropertyChangeListener(this);
    }
}
