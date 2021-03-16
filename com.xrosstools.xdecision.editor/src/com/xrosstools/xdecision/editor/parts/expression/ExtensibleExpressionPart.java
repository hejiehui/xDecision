package com.xrosstools.xdecision.editor.parts.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;

public abstract class ExtensibleExpressionPart extends BaseExpressionPart {
    @Override
    protected List getModelChildren() {
        ExtensibleExpression exp = (ExtensibleExpression)getModel();
        return exp.hasChild() ? Arrays.asList(exp.getChild()) : Collections.emptyList();
    }
    
    protected void addChildVisual(EditPart childEditPart, int index) {
        ExtensibleExpression exp = (ExtensibleExpression)getModel();
        ExpandableExpressionFigure figure = (ExpandableExpressionFigure)getFigure();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        Object childModel = childEditPart.getModel();
        
        if(childModel == exp.getChild())
            figure.setExpandedFigure(childFigure);
    }
}
