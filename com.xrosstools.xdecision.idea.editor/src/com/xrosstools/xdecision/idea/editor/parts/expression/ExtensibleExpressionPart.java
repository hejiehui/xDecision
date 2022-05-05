package com.xrosstools.xdecision.idea.editor.parts.expression;

import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.xdecision.idea.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.idea.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.idea.editor.model.expression.ExtensibleExpression;

import java.util.ArrayList;
import java.util.List;



public class ExtensibleExpressionPart extends BaseExpressionPart {
    public ExtensibleExpression getExtensibleExpression() {
        return (ExtensibleExpression)super.getModel();
    }

    @Override
    protected Figure createFigure() {
        ExpandableExpressionFigure figure = new ExpandableExpressionFigure();
        postCreateFigure(figure);
        return figure;
    }
    
    protected void postCreateFigure(ExpandableExpressionFigure figure) {}
    
    protected void postGetModelChildren(List children) {}
    
    protected void postAddChildVisual(EditPart childEditPart, int index) {}
    
    protected void postRefreshVisuals() {}
    
    private ExpandableExpressionFigure getExpandableExpressionFigure() {
        return (ExpandableExpressionFigure)super.getFigure();
    }
    
    @Override
    protected List getModelChildren() {
        List children = new ArrayList();
        ExtensibleExpression exp = getExtensibleExpression();
        
        if(exp.getChildExpression() != null)
            children.add(exp.getChildExpression());

        postGetModelChildren(children);

        return children;
    }
    
    @Override
    public void addChildVisual(EditPart childEditPart, int index) {
        ExtensibleExpression exp = getExtensibleExpression();
        
        Figure childFigure = childEditPart.getFigure();
        Object childModel = childEditPart.getModel();
        
        if(childModel == exp.getChildExpression()) {
            getExpandableExpressionFigure().setExpandedFigure(childFigure);
            return;
        }
        
        postAddChildVisual(childEditPart, index);
    }

    protected void refreshVisuals() {
        ExtensibleExpression exp = getExtensibleExpression();
        ExpandableExpressionFigure figure = getExpandableExpressionFigure();
        if(exp.getChildExpression() == null || exp.getChildExpression() instanceof ElementExpression)
            figure.setJointText("");
        else
            figure.setJointText(".");
        
        postRefreshVisuals();
    }    
}
