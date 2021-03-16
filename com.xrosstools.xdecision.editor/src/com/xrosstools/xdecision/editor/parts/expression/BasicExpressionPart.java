package com.xrosstools.xdecision.editor.parts.expression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import com.xrosstools.xdecision.editor.figures.BasicExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.BasicExpression;

public class BasicExpressionPart extends BaseExpressionPart {
    protected IFigure createFigure() {
        return new BasicExpressionFigure();
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
