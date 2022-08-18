package com.xrosstools.xdecision.idea.editor.parts.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.GraphicalEditPart;
import com.xrosstools.xdecision.idea.editor.figures.EnclosedExpressionFigure;
import com.xrosstools.xdecision.idea.editor.model.expression.BracktExpression;
import com.xrosstools.xdecision.idea.editor.model.expression.EnclosedExpression;
import com.xrosstools.xdecision.idea.editor.model.expression.NegativeExpression;


public class EnclosedExpressionPart extends BaseExpressionPart {
    @Override
    protected Figure createFigure() {
        if(getModel() instanceof BracktExpression)
            return EnclosedExpressionFigure.createBracketFigure();
        
        if(getModel() instanceof NegativeExpression)
            return EnclosedExpressionFigure.createNegativeFigure();

        return new EnclosedExpressionFigure();
    }
    
    @Override
    public List getModelChildren() {
        EnclosedExpression exp = (EnclosedExpression)getModel();
        if(exp.getInnerExpression() != null)
            return Arrays.asList(exp.getInnerExpression());
        else
            return Collections.emptyList();
    }
    
    public void addChildVisual(EditPart childEditPart, int index) {
        EnclosedExpression exp = (EnclosedExpression)getModel();
        EnclosedExpressionFigure figure = (EnclosedExpressionFigure)getFigure();
        
        Figure childFigure = ((GraphicalEditPart)childEditPart).getFigure();
        figure.setEnclosedFigure(childFigure);
    }
}
