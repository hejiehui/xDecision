package com.xrosstools.xdecision.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;

public class EnclosedExpressionFigure extends Figure {
    private BorderLayout layout = new BorderLayout();

    public static EnclosedExpressionFigure createBracketFigure() {
        EnclosedExpressionFigure bracket = new EnclosedExpressionFigure();
        bracket.setLeftDelimeter(new Label("("));
        bracket.setRightDelimeter(new Label(")"));
        return bracket;
    }

    public static EnclosedExpressionFigure createElementFigure() {
        EnclosedExpressionFigure element = new EnclosedExpressionFigure();
        element.setLeftDelimeter(new Label("["));
        element.setRightDelimeter(new Label("]"));
        return element;
    }
    
    public EnclosedExpressionFigure() {
        setLayoutManager(layout);
//        this.setBorder(new MarginBorder(0, 2, 0, 2));
    }
    
    public void setLeftDelimeter(IFigure leftDelimeter) {
        add(leftDelimeter);
        layout.setConstraint(leftDelimeter, PositionConstants.LEFT);
    }
    
    public void setRightDelimeter(IFigure rightDelimeter) {
        add(rightDelimeter);
        layout.setConstraint(rightDelimeter, PositionConstants.RIGHT);
    }

    public void setEnclosedFigure(IFigure enclosedFigure) {
        add(enclosedFigure);
        layout.setConstraint(enclosedFigure, PositionConstants.CENTER);
    }
}
