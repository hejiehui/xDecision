package com.xrosstools.xdecision.editor.parts.expression;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.MethodExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;

public class MethodExpressionPart extends BaseExpressionPart {
    private MethodExpressionFigure methodFigure;

    @Override
    protected IFigure createFigure() {
        methodFigure = new MethodExpressionFigure();
        return methodFigure;
    }
    
    @Override
    protected List getModelChildren() {
        MethodExpression exp = (MethodExpression)getModel();
        List children = new ArrayList();
        children.add(exp.getParameters());
        return children;
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        MethodExpression exp = (MethodExpression)getModel();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();

        methodFigure.setParameterFigure(childFigure);
    }
    
    protected void refreshVisuals() {
        MethodExpression exp = (MethodExpression)getModel();
        methodFigure.setMethodName(exp.getName());
        if(exp.isValid())
            methodFigure.getNameLabel().setForegroundColor(ColorConstants.black);
        else
            methodFigure.setForegroundColor(ColorConstants.red);
    }
}
