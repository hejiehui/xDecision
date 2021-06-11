package com.xrosstools.xdecision.editor.parts.expression;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.Identifier;

public class IdentifierExpressionPart extends ExtensibleExpressionPart {
    private Label identifierLabel;
    private Label jointLabel;
    @Override
    protected IFigure createFigure() {
        identifierLabel = new Label();
        jointLabel = new Label();
        
        ExpandableExpressionFigure figure = new ExpandableExpressionFigure();
        figure.setBaseFigure(identifierLabel);
        figure.setJointFigure(jointLabel);
        return figure;
    }
    
    protected void refreshVisuals() {
        ExtensibleExpression exp = (ExtensibleExpression)getModel();
        identifierLabel.setText(((Identifier)exp).getIdentifier());
        jointLabel.setText(exp.hasChild() ? "." : "");
        
//        if(exp.isValid())
//            identifierLabel.getNameLabel().setForegroundColor(ColorConstants.black);
//        else
//            identifierLabel.setForegroundColor(ColorConstants.red);
    }
}
