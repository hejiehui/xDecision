package com.xrosstools.xdecision.idea.editor.parts.expression;


import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.figures.Label;
import com.xrosstools.gef.parts.GraphicalEditPart;
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

    protected void removeChildVisual(GraphicalEditPart childEditPart) {
        Figure child = childEditPart.getFigure();
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
            return Color.yellow;
        
        if(exp.getReferenceElement() instanceof DecisionTreeFactor)
            return Color.lightGray;

        return Color.gray;
    }
}
