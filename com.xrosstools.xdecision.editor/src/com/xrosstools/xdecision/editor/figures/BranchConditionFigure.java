package com.xrosstools.xdecision.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;

public class BranchConditionFigure extends Figure {
    private IFigure operatorFigure;
    private IFigure expressionFigure;
    private BorderLayout layout= new BorderLayout();

    public BranchConditionFigure() {
        layout= new BorderLayout();
        setLayoutManager(layout);
        this.setBorder(new MarginBorder(5));
        
        Figure widthLine = new Figure();
        widthLine.setPreferredSize(new Dimension(10, 10));
        widthLine.setForegroundColor(ColorConstants.black);
        add(widthLine);
        layout.setConstraint(widthLine, PositionConstants.CENTER);
    }

    public void setExpressionFigure(IFigure expressionFigure) {
        if(this.expressionFigure != null)
            remove(this.expressionFigure);
        this.expressionFigure = expressionFigure;
        add(expressionFigure);
        layout.setConstraint(expressionFigure, PositionConstants.RIGHT);
        repaint();
    }

    public void setOperatorFigure(IFigure operatorFigure) {
        if(this.operatorFigure != null)
            remove(this.operatorFigure);
        this.operatorFigure = operatorFigure;
        add(operatorFigure);
        layout.setConstraint(operatorFigure, PositionConstants.LEFT);
        repaint();
    }
}
