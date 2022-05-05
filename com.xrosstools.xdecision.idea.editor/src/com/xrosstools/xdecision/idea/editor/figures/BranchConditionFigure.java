package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.gef.figures.*;
import com.xrosstools.gef.figures.BorderLayout;
import com.xrosstools.gef.figures.Label;
import com.xrosstools.xdecision.idea.editor.model.ConditionOperator;

import java.awt.*;

public class BranchConditionFigure extends Figure {
    private Label operatorFigure;
    private Figure widthLine;
    private BorderLayout layout= new BorderLayout();

    public BranchConditionFigure() {
        layout= new BorderLayout();
        setLayoutManager(layout);
        this.getInsets().set(5, 5, 5, 5);

        operatorFigure = new Label();
        operatorFigure.setLabelAlignment(PositionConstants.RIGHT);
        operatorFigure.setForegroundColor(ColorConstants.black);
        add(operatorFigure);
        setConstraint(operatorFigure, PositionConstants.LEFT);

        widthLine = new Figure();
        widthLine.setPreferredSize(new Dimension(10, 10));
        widthLine.setForegroundColor(ColorConstants.black);
        add(widthLine);
        layout.setConstraint(widthLine, PositionConstants.CENTER);
    }

    //first add then remove will be called
    public void setExpressionFigure(Figure expressionFigure) {
        add(expressionFigure);
        setConstraint(expressionFigure, PositionConstants.RIGHT);
    }

    public void setOperator(ConditionOperator operator) {
        operatorFigure.setText(operator == null ? "" : operator.getText());        
    }
}
