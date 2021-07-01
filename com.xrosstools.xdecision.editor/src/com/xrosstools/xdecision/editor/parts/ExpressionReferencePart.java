package com.xrosstools.xdecision.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xdecision.editor.model.ExpressionReference;

public class ExpressionReferencePart extends AbstractGraphicalEditPart implements PropertyChangeListener {
    private Figure expressionFigure;
    private IFigure childFigure;
    protected List getModelChildren() {
        List children = new ArrayList();
        ExpressionReference ref = (ExpressionReference) getModel();
        if(ref.getExpression() != null)
            children.add(ref.getExpression());
        return children;
    }

    protected IFigure createFigure() {
        expressionFigure = new Figure();
        expressionFigure.setLayoutManager(new BorderLayout());
        return expressionFigure;
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        if(this.childFigure != null)
            expressionFigure.remove(this.childFigure);
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        expressionFigure.add(childFigure);
        expressionFigure.getLayoutManager().setConstraint(expressionFigure, PositionConstants.CENTER);
        this.childFigure = childFigure;
    }


    protected void createEditPolicies() {
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }
    
    public void activate() {
        super.activate();
        ((ExpressionReference) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
        super.deactivate();
        ((ExpressionReference) getModel()).getListeners().removePropertyChangeListener(this);
    }
}