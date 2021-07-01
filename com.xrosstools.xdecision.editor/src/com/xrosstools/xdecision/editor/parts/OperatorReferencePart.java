package com.xrosstools.xdecision.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xdecision.editor.model.OperatorReference;

public class OperatorReferencePart extends AbstractGraphicalEditPart implements PropertyChangeListener {
    private Label operatorFigure;
    protected IFigure createFigure() {
        operatorFigure = new Label();
        operatorFigure.setLabelAlignment(PositionConstants.RIGHT);
        operatorFigure.setForegroundColor(ColorConstants.black);
        return operatorFigure;
    }

    protected void createEditPolicies() {
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }
    
    public void activate() {
        super.activate();
        ((OperatorReference) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
        super.deactivate();
        ((OperatorReference) getModel()).getListeners().removePropertyChangeListener(this);
    }

    protected void refreshVisuals() {
        OperatorReference ref = (OperatorReference) getModel();
        
        operatorFigure.setText(ref.getOperator() == null? "-" : ref.getOperator().getText());
    }
}