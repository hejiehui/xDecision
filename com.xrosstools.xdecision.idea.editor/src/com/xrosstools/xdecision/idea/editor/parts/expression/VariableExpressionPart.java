package com.xrosstools.xdecision.idea.editor.parts.expression;


import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.GraphicalEditPart;
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
        Figure child = ((GraphicalEditPart)childEditPart).getFigure();
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
