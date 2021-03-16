package com.xrosstools.xdecision.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;

public class ExpandableExpressionFigure extends Figure {
    private BorderLayout layout = new BorderLayout();

    public ExpandableExpressionFigure() {
        setLayoutManager(layout);
//        this.setBorder(new MarginBorder(0, 2, 0, 2));
    }

    public void setBaseFigure(IFigure baseFigure) {
        add(baseFigure);
        layout.setConstraint(baseFigure, PositionConstants.LEFT);
    }

    public void setJointFigure(IFigure jointFigure) {
        add(jointFigure);
        layout.setConstraint(jointFigure, PositionConstants.CENTER);
    }

    public void setExpandedFigure(IFigure expandedFigure) {
        add(expandedFigure);
        layout.setConstraint(expandedFigure, PositionConstants.RIGHT);
    }
}