package com.xrosstools.xdecision.editor.parts.expression;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.EnclosedExpressionFigure;
import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;

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
    
    protected void postAddChildVisual(EditPart childEditPart, int index) {
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();

        if(getElementExpression().getIndexExpression() == childEditPart.getModel()) 
            indexPanel.setEnclosedFigure(childFigure);
    }

    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        if(indexPanel.getChildren().contains(childFigure)) {
            indexPanel.remove(childFigure);
        }else
            super.removeChildVisual(childEditPart);
    }
}