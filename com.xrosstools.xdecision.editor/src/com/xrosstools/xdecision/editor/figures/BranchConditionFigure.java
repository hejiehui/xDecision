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
    private Figure widthLine;
    private BorderLayout layout= new BorderLayout();

    public BranchConditionFigure() {
        layout= new BorderLayout();
        setLayoutManager(layout);
        this.setBorder(new MarginBorder(5));
        
        widthLine = new Figure();
        widthLine.setPreferredSize(new Dimension(10, 10));
        widthLine.setForegroundColor(ColorConstants.black);
        add(widthLine);
        layout.setConstraint(widthLine, PositionConstants.CENTER);
    }

    //first add then remove will be called
    public void addExpressionFigure(IFigure expressionFigure) {
        if(this.expressionFigure != null)
            remove(this.expressionFigure);

        this.expressionFigure = expressionFigure;

        if(expressionFigure == null)
            return;

        add(expressionFigure);
        setConstraint(expressionFigure, PositionConstants.RIGHT);
//        repaint();
    }

    public void setConstraint(IFigure child, Object constraint) {
        if(child == operatorFigure)
            layout.setConstraint(child, PositionConstants.LEFT);
        else if(child == expressionFigure)
            layout.setConstraint(child, PositionConstants.RIGHT);
        else
            layout.setConstraint(child, PositionConstants.CENTER);
    }

    //first add then remove will be called
    public void addOperatorFigure(IFigure operatorFigure) {
        if(this.operatorFigure != null)
            remove(this.operatorFigure);
        
        this.operatorFigure = operatorFigure;

        if(operatorFigure == null)
            return;

        add(operatorFigure);
        setConstraint(operatorFigure, PositionConstants.LEFT);
//        repaint();
    }
}
