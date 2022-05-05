package com.xrosstools.xdecision.idea.editor.parts.expression;


import com.xrosstools.gef.figures.Figure;
import com.xrosstools.xdecision.idea.editor.figures.BasicExpressionFigure;
import com.xrosstools.xdecision.idea.editor.model.expression.BasicExpression;
import com.xrosstools.xdecision.idea.editor.model.expression.PlaceholderExpression;

import java.awt.*;

public class BasicExpressionPart extends BaseExpressionPart {
    protected Figure createFigure() {
        BasicExpressionFigure figure = new BasicExpressionFigure();
        
        if(getModel() instanceof PlaceholderExpression)
            figure.setForegroundColor(Color.gray);

        return figure;
    }

    protected void refreshVisuals() {
        BasicExpression node = (BasicExpression) getModel();
        BasicExpressionFigure figure = (BasicExpressionFigure)getFigure();
        figure.setText(node.getDisplayText());
    }
}
