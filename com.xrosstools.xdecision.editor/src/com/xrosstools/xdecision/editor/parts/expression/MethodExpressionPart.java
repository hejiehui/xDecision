package com.xrosstools.xdecision.editor.parts.expression;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.figures.MethodExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;

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
    protected void postAddChildVisual(EditPart childEditPart, int index) {
        MethodExpression exp = (MethodExpression)getModel();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        
        if(childEditPart.getModel() == exp.getParameters())
            methodFigure.setParameterFigure(childFigure);
    }
    
    protected void postRefreshVisuals() {
        MethodExpression exp = (MethodExpression)getModel();
        methodFigure.setMethodName(exp.getName());

        if(exp.isValid())
            methodFigure.getNameLabel().setForegroundColor(ColorConstants.black);
        else
            methodFigure.setForegroundColor(ColorConstants.red);
    }
}
