package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.gef.figures.BorderLayout;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.figures.Label;
import com.xrosstools.gef.figures.PositionConstants;


public class ExpandableExpressionFigure extends Figure {
    private Label jointFigure;

    private BorderLayout layout = new BorderLayout();

    public ExpandableExpressionFigure() {
        setLayoutManager(layout);
//      this.setBorder(new MarginBorder(0, 2, 0, 2));

        jointFigure = new Label();
        add(jointFigure);
        layout.setConstraint(jointFigure, PositionConstants.CENTER);
    }

    public void setBaseFigure(Figure baseFigure) {
        add(baseFigure);
        layout.setConstraint(baseFigure, PositionConstants.LEFT);
    }

    public void setJointText(String jointText) {
        jointFigure.setText(jointText);
    }
    
    public void setExpandedFigure(Figure expandedFigure) {
        add(expandedFigure);
        layout.setConstraint(expandedFigure, PositionConstants.RIGHT);
    }
}