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
    private IFigure indexFigure;
    public ElementExpression getModel() {
        return (ElementExpression)super.getModel();
    }

    @Override
    protected void postCreateFigure(ExpandableExpressionFigure figure) {
        indexPanel = EnclosedExpressionFigure.createElementFigure();
        figure.setBaseFigure(indexPanel);
    }
    
    @Override
    protected void postGetModelChildren(List children) {
        if(getModel().getIndexExpression() != null)
            children.add(getModel().getIndexExpression());
    }
    
    protected void postAddChildVisual(EditPart childEditPart, int index) {
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();

        if(getModel().getIndexExpression() == childEditPart.getModel()) 
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