package com.xrosstools.xdecision.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;

import com.xrosstools.xdecision.editor.model.ConditionOperator;

public class BranchConditionFigure extends Figure {
    private Label operatorFigure;
    private Figure widthLine;
    private BorderLayout layout= new BorderLayout();

    public BranchConditionFigure() {
        layout= new BorderLayout();
        setLayoutManager(layout);
        this.setBorder(new MarginBorder(5));
        
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
    public void setExpressionFigure(IFigure expressionFigure) {
//        if(this.expressionFigure != null)
//            remove(this.expressionFigure);
//
//        this.expressionFigure = expressionFigure;
//
//        if(expressionFigure == null)
//            return;
//
        add(expressionFigure);
        setConstraint(expressionFigure, PositionConstants.RIGHT);
    }

    public void setOperator(ConditionOperator operator) {
        operatorFigure.setText(operator == null ? "" : operator.getText());        
    }
}
