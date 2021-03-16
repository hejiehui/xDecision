package com.xrosstools.xdecision.editor.parts.expression;

import java.util.List;

import org.eclipse.draw2d.IFigure;

import com.xrosstools.xdecision.editor.figures.CompositeExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.CompositeExpression;

public class CompositeExpressionPart extends BaseExpressionPart {
    protected List getModelChildren() {
        return ((CompositeExpression)getModel()).getAllExpression();
    }
    
    protected IFigure createFigure() {
        return new CompositeExpressionFigure();
    }
}
