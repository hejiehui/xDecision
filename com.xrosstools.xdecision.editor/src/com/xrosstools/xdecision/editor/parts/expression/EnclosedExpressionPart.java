package com.xrosstools.xdecision.editor.parts.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.EnclosedExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.BracktExpression;
import com.xrosstools.xdecision.editor.model.expression.EnclosedExpression;
import com.xrosstools.xdecision.editor.model.expression.NegativeExpression;

public class EnclosedExpressionPart extends BaseExpressionPart {
    @Override
    protected IFigure createFigure() {
        if(getModel() instanceof BracktExpression)
            return EnclosedExpressionFigure.createBracketFigure();
        
        if(getModel() instanceof NegativeExpression)
            return EnclosedExpressionFigure.createNegativeFigure();

        return new EnclosedExpressionFigure();
    }
    
    @Override
    protected List getModelChildren() {
        EnclosedExpression exp = (EnclosedExpression)getModel();
        if(exp.getInnerExpression() != null)
            return Arrays.asList(exp.getInnerExpression());
        else
            return Collections.emptyList();
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        EnclosedExpression exp = (EnclosedExpression)getModel();
        EnclosedExpressionFigure figure = (EnclosedExpressionFigure)getFigure();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        figure.setEnclosedFigure(childFigure);
    }
}
