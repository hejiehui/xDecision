package com.xrosstools.xdecision.editor.parts.expression;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Color;

import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class VariableExpressionPart extends BaseExpressionPart {
    private Label identifierLabel;

    @Override
    protected IFigure createFigure() {
        identifierLabel = new Label();
        return identifierLabel;
    }
    
    protected void refreshVisuals() {
        VariableExpression exp = (VariableExpression)getModel();
        identifierLabel.setText(exp.getName());
        identifierLabel.setForegroundColor(getColor(exp));
    }
    
    private Color getColor(ExpressionDefinition exp) {
        if(exp instanceof VariableExpression) {
            NamedElement element = ((VariableExpression)exp).getReferenceElement();
            if(element instanceof DecisionTreeFactor)
                return ColorConstants.blue;
        }

        return ColorConstants.black;
    }
}
