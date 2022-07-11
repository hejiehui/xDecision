package com.xrosstools.xdecision.idea.editor.parts.expression;

import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.parts.GraphicalEditPart;
import com.xrosstools.xdecision.idea.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.idea.editor.figures.MethodExpressionFigure;
import com.xrosstools.xdecision.idea.editor.model.expression.MethodExpression;

import java.util.List;

public class MethodExpressionPart extends ExtensibleExpressionPart {
    private MethodExpressionFigure methodFigure;

    @Override
    protected void postCreateFigure(ExpandableExpressionFigure figure) {
        methodFigure = new MethodExpressionFigure();
        figure.setBaseFigure(methodFigure);
    }
    
    @Override
    protected void postGetModelChildren(List children) {
        children.add(((MethodExpression)getModel()).getParameters());
    }
    
    @Override
    protected void postAddChildVisual(GraphicalEditPart childEditPart, int index) {
        MethodExpression exp = (MethodExpression)getModel();
        
        Figure childFigure = childEditPart.getFigure();
        
        if(childEditPart.getModel() == exp.getParameters())
            methodFigure.setParameterFigure(childFigure);
    }
    
    protected void postRefreshVisuals() {
        MethodExpression exp = (MethodExpression)getModel();
        methodFigure.setMethodName(exp.getName());
        methodFigure.setMethodValidation(exp.isValid());
    }
}
