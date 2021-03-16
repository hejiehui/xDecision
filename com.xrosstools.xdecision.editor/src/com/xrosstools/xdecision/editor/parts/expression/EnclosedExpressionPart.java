package com.xrosstools.xdecision.editor.parts.expression;

import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.EnclosedExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.EnclosedExpression;

public class EnclosedExpressionPart extends BaseExpressionPart {
    @Override
    protected IFigure createFigure() {
        if(getModel() instanceof ElementExpression)
            return EnclosedExpressionFigure.createElementFigure();
        return new EnclosedExpressionFigure();
    }
    
    @Override
    protected List getModelChildren() {
        EnclosedExpression exp = (EnclosedExpression)getModel();
        
        return Arrays.asList(exp.getEnclosedExpression());
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        EnclosedExpression exp = (EnclosedExpression)getModel();
        EnclosedExpressionFigure figure = (EnclosedExpressionFigure)getFigure();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        figure.setEnclosedFigure(childFigure);
    }
}
