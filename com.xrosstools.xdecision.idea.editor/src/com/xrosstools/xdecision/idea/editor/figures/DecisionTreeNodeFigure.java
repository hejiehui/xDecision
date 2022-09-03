package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.idea.gef.figures.*;
import com.xrosstools.idea.gef.figures.BorderLayout;
import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.idea.gef.figures.LayoutManager;

import java.awt.*;


public class DecisionTreeNodeFigure extends RoundedRectangle {
    private Label decisionLabel;
    private Figure nodeExpression;
    private LayoutManager layout;

    public DecisionTreeNodeFigure() {
        setMinSize(new Dimension(100, 50));
        getInsets().set(5, 5, 5, 5);
        layout= new BorderLayout();
        setLayoutManager(layout);

        //To make sure the minimal width of the node figure
        Figure  widthLine = new Figure();
        widthLine.setPreferredSize(new Dimension(90, 1));
        widthLine.getInsets().set(5, 5, 5, 5);
        add(widthLine);
        layout.setConstraint(widthLine, PositionConstants.TOP);

        decisionLabel = new Label();
        decisionLabel.setLabelAlignment(PositionConstants.CENTER);
        decisionLabel.setForegroundColor(ColorConstants.gray);
        decisionLabel.getInsets().set(5, 5, 5, 5);
        add(decisionLabel);
        layout.setConstraint(decisionLabel, PositionConstants.CENTER);
    }

    public void setDecision(String decision) {
        decisionLabel.setText(decision);
        repaint();
    }

    public void setExpressionFigure(Figure nodeExpression) {
        this.nodeExpression = nodeExpression;
        add(nodeExpression);
        layout.setConstraint(nodeExpression, PositionConstants.BOTTOM);
        repaint();
    }
}
