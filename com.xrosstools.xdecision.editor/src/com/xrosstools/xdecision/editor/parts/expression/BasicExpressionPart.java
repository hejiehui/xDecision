package com.xrosstools.xdecision.editor.parts.expression;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import com.xrosstools.xdecision.editor.figures.BasicExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.BasicExpression;
import com.xrosstools.xdecision.editor.model.expression.PlaceholderExpression;

public class BasicExpressionPart extends BaseExpressionPart {
    protected IFigure createFigure() {
        BasicExpressionFigure figure = new BasicExpressionFigure();
        
        if(getModel() instanceof PlaceholderExpression)
            figure.setForegroundColor(ColorConstants.gray);

        return figure;
    }

    public void performRequest(Request req) {
        if (req.getType() == RequestConstants.REQ_OPEN){
        }
    }
    
    protected void refreshVisuals() {
        BasicExpression node = (BasicExpression) getModel();
        BasicExpressionFigure figure = (BasicExpressionFigure)getFigure();
        figure.setText(node.getDisplayText());
    }
}
