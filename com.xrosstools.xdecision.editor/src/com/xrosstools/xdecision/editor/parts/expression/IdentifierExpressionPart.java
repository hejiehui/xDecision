package com.xrosstools.xdecision.editor.parts.expression;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Color;

import com.xrosstools.xdecision.editor.figures.ExpandableExpressionFigure;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.expression.ExtensibleExpression;
import com.xrosstools.xdecision.editor.model.expression.Identifier;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

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
        identifierLabel.setForegroundColor(getColor(exp));
        jointLabel.setText(exp.hasChild() ? "." : "");        
    }
    
    private Color getColor(ExtensibleExpression exp) {
        if(exp instanceof VariableExpression) {
            NamedElement element = ((VariableExpression)exp).getReferenceElement();
            if(element instanceof DecisionTreeFactor)
                return ColorConstants.blue;
        }

        return ColorConstants.black;
    }
}
