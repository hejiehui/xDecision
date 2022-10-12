package com.xrosstools.xdecision.idea.editor.parts.expression;


import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.xdecision.idea.editor.figures.ElementColors;
import com.xrosstools.xdecision.idea.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.expression.VariableExpression;

import java.awt.*;

public class VariableExpressionPart extends ExtensibleExpressionPart {
    private Label identifierLabel;

    @Override
    protected void postCreateFigure(ExpandableExpressionFigure figure) {
        identifierLabel = new Label();
        figure.setBaseFigure(identifierLabel);
    }

    protected void removeChildVisual(EditPart childEditPart) {
        Figure child = ((AbstractGraphicalEditPart)childEditPart).getFigure();
        if(getContentPane().getChildren().contains(child))
            getContentPane().remove(child);
    }


    @Override
    protected void postRefreshVisuals() {
        VariableExpression exp = (VariableExpression)getModel();
        identifierLabel.setText(exp.getName());
        identifierLabel.setForegroundColor(getColor(exp));
    }
    
    private Color getColor(VariableExpression exp) {
        if(!exp.isValid())
            return ElementColors.TEXT_ERROR;
        
        if(exp.getReferenceElement() instanceof DecisionTreeFactor)
            return ElementColors.TEXT_HIGHLIGHT;

        return ElementColors.TEXT_NORMAL;
    }
}
