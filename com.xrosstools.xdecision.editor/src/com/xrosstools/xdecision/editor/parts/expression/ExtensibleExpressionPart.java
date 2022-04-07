package com.xrosstools.xdecision.editor.parts.expression;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.ElementExpression;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;

public class ExtensibleExpressionPart extends BaseExpressionPart {
    public ExtensibleExpression getModel() {
        return (ExtensibleExpression)super.getModel();
    }

    @Override
    protected IFigure createFigure() {
        ExpandableExpressionFigure figure = new ExpandableExpressionFigure();
        figure.setJointFigure(new Label());
        postCreateFigure(figure);
        return figure;
    }
    
    protected void postCreateFigure(ExpandableExpressionFigure figure) {}
    
    protected void postGetModelChildren(List children) {}
    
    protected void postAddChildVisual(EditPart childEditPart, int index) {}
    
    protected void postRefreshVisuals() {}
    
    @Override
    public ExpandableExpressionFigure getFigure() {
        return (ExpandableExpressionFigure)super.getFigure();
    }
    
    @Override
    protected List getModelChildren() {
        List children = new ArrayList();
        ExtensibleExpression exp = getModel();
        
        if(exp.getChildExpression() != null)
            children.add(exp.getChildExpression());

        postGetModelChildren(children);

        return children;
    }
    
    @Override
    protected void addChildVisual(EditPart childEditPart, int index) {
        ExtensibleExpression exp = getModel();
        
        IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
        Object childModel = childEditPart.getModel();
        
        if(childModel == exp.getChildExpression()) {
            getFigure().setExpandedFigure(childFigure);
            return;
        }
        
        postAddChildVisual(childEditPart, index);
    }

    protected void refreshVisuals() {
        ExtensibleExpression exp = getModel();
        Label jointLabel = (Label)getFigure().getJointFigure();
        if(exp.getChildExpression() == null || exp.getChildExpression() instanceof ElementExpression)
            jointLabel.setText("");
        else
            jointLabel.setText(".");
        
        postRefreshVisuals();
    }    
}
