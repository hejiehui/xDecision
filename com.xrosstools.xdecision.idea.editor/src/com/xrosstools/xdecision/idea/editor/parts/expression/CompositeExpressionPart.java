package com.xrosstools.xdecision.idea.editor.parts.expression;

import java.util.List;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.xdecision.idea.editor.figures.CompositeExpressionFigure;
import com.xrosstools.xdecision.idea.editor.model.expression.CompositeExpression;

public class CompositeExpressionPart extends BaseExpressionPart {
    public List getModelChildren() {
        return ((CompositeExpression)getModel()).getAllExpression();
    }
    
    protected Figure createFigure() {
        return new CompositeExpressionFigure();
    }
}
