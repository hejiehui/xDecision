package com.xrosstools.xdecision.idea.editor.parts.expression;

import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.gef.parts.GraphicalEditPart;
import com.xrosstools.xdecision.idea.editor.figures.EnclosedExpressionFigure;
import com.xrosstools.xdecision.idea.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.idea.editor.model.expression.ElementExpression;

import java.util.List;

public class ElementExpressionPart extends ExtensibleExpressionPart {
    private EnclosedExpressionFigure indexPanel;

    public ElementExpression getElementExpression() {
        return (ElementExpression)super.getModel();
    }

    @Override
    protected void postCreateFigure(ExpandableExpressionFigure figure) {
        indexPanel = EnclosedExpressionFigure.createElementFigure();
        figure.setBaseFigure(indexPanel);
    }
    
    @Override
    protected void postGetModelChildren(List children) {
        if(getElementExpression().getIndexExpression() != null)
            children.add(getElementExpression().getIndexExpression());
    }
    
    protected void postAddChildVisual(GraphicalEditPart childEditPart, int index) {
        Figure childFigure = childEditPart.getFigure();

        if(getElementExpression().getIndexExpression() == childEditPart.getModel())
            indexPanel.setEnclosedFigure(childFigure);
    }

    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        Figure childFigure = ((GraphicalEditPart)childEditPart).getFigure();
        if(indexPanel.getChildren().contains(childFigure)) {
            indexPanel.remove(childFigure);
        }else
            super.removeChildVisual(childEditPart);
    }
}