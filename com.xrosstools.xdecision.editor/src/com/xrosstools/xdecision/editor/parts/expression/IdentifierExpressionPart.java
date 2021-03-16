package com.xrosstools.xdecision.editor.parts.expression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.Identifier;

public class IdentifierExpressionPart extends ExtensibleExpressionPart {
    private Label factorLabel;
    private Label jointLabel;
    @Override
    protected IFigure createFigure() {
        factorLabel = new Label();
        jointLabel = new Label();
        
        ExpandableExpressionFigure figure = new ExpandableExpressionFigure();
        figure.setBaseFigure(factorLabel);
        figure.setJointFigure(jointLabel);
        return figure;
    }
    
    protected void refreshVisuals() {
        ExtensibleExpression exp = (ExtensibleExpression)getModel();
        factorLabel.setText(((Identifier)exp).getDisplayText());
        jointLabel.setText(exp.hasChild() ? "." : "");
    }
}
