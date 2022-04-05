package com.xrosstools.xdecision.editor.parts.expression;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.EnclosedExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;

public class ElementExpressionPart extends BaseExpressionPart {
    public ElementExpression getModel() {
        return (ElementExpression)super.getModel();
    }

    @Override
    protected IFigure createFigure() {
        return EnclosedExpressionFigure.createElementFigure();
    }
    
    @Override
    protected List getModelChildren() {
        List children = new ArrayList();

        ElementExpression exp = getModel();
        if(exp.getIndexExpression() != null)
            children.add(exp.getIndexExpression());

        return children;
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        ElementExpression exp = getModel();
        EnclosedExpressionFigure figure = (EnclosedExpressionFigure)getFigure();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        Object childModel = childEditPart.getModel();
        
        figure.setEnclosedFigure(childFigure);
    }
}