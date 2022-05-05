package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.gef.figures.BorderLayout;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.figures.Label;
import com.xrosstools.gef.figures.PositionConstants;

public class EnclosedExpressionFigure extends Figure {
    private BorderLayout layout = new BorderLayout();
    private Label leftDelimeter;
    private Label rightDelimeter;
    private Figure enclosedFigure;
    public static EnclosedExpressionFigure createBracketFigure() {
        EnclosedExpressionFigure bracket = new EnclosedExpressionFigure();
        bracket.setLeftDelimeter("(");
        bracket.setRightDelimeter(")");
        return bracket;
    }

    public static EnclosedExpressionFigure createElementFigure() {
        EnclosedExpressionFigure element = new EnclosedExpressionFigure();
        element.setLeftDelimeter("[");
        element.setRightDelimeter("]");
        return element;
    }
    
    public static EnclosedExpressionFigure createNegativeFigure() {
        EnclosedExpressionFigure element = new EnclosedExpressionFigure();
        element.setLeftDelimeter("-");
        element.setRightDelimeter("");
        return element;
    }
    
    public EnclosedExpressionFigure() {
        setLayoutManager(layout);
//        this.setBorder(new MarginBorder(0, 2, 0, 2));
        leftDelimeter = new Label();
        add(leftDelimeter);
        layout.setConstraint(leftDelimeter, PositionConstants.LEFT);

        rightDelimeter = new Label();
        add(rightDelimeter);
        layout.setConstraint(rightDelimeter, PositionConstants.RIGHT);
    }
    
    public void setLeftDelimeter(String text) {
        leftDelimeter.setText(text);
    }
    
    public void setRightDelimeter(String text) {
        rightDelimeter.setText(text);
    }

    public void setEnclosedFigure(Figure newEnclosedFigure) {
        add(newEnclosedFigure);
        layout.setConstraint(newEnclosedFigure, PositionConstants.CENTER);
    }
}
