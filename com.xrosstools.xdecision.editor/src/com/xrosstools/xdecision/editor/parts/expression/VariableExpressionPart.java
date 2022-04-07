package com.xrosstools.xdecision.editor.parts.expression;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Color;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class VariableExpressionPart extends ExtensibleExpressionPart {
    private Label identifierLabel;

    @Override
    protected void postCreateFigure(ExpandableExpressionFigure figure) {
        identifierLabel = new Label();
        figure.setBaseFigure(identifierLabel);
    }
    
    @Override
    protected void postRefreshVisuals() {
        VariableExpression exp = (VariableExpression)getModel();
        identifierLabel.setText(exp.getName());
        identifierLabel.setForegroundColor(getColor(exp));
    }
    
    private Color getColor(VariableExpression exp) {
        if(!exp.isValid())
            return ColorConstants.red;
        
        if(exp.getReferenceElement() instanceof DecisionTreeFactor)
            return ColorConstants.blue;

        return ColorConstants.black;
    }
}
