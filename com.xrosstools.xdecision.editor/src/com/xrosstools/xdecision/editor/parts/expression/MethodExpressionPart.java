package com.xrosstools.xdecision.editor.parts.expression;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.figures.MethodExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.MethodExpression;

public class MethodExpressionPart extends ExtensibleExpressionPart {
    private MethodExpressionFigure methodFigure;
    private Label jointLabel;
    @Override
    protected IFigure createFigure() {
        methodFigure = new MethodExpressionFigure();
        jointLabel = new Label();
        
        ExpandableExpressionFigure figure = new ExpandableExpressionFigure();
        figure.setBaseFigure(methodFigure);
        figure.setJointFigure(jointLabel);
        return figure;
    }
    
    @Override
    protected List getModelChildren() {
        MethodExpression exp = (MethodExpression)getModel();
        List children = new ArrayList();
        children.add(exp.getParameters());
        if(exp.hasChild())
            children.add(exp.getChild());
        return children;
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        MethodExpression exp = (MethodExpression)getModel();
        ExpandableExpressionFigure figure = (ExpandableExpressionFigure)getFigure();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        Object childModel = childEditPart.getModel();
        
        if(childModel == exp.getChild())
            figure.setExpandedFigure(childFigure);
        
        if(childModel == exp.getParameters())
            methodFigure.setParameterFigure(childFigure);
    }
    
    protected void refreshVisuals() {
        MethodExpression exp = (MethodExpression)getModel();
        methodFigure.setMethodName(exp.getName());
        jointLabel.setText(exp.hasChild() ? "." : "");
    }
}
