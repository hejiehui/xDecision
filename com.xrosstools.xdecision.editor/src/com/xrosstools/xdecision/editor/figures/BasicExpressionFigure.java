package com.xrosstools.xdecision.editor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;

public class BasicExpressionFigure extends Label {
    private static final String INITIAL_TEXT = "...";
    //TODO support customizatiuon of cloor etc
    public BasicExpressionFigure() {
        setForegroundColor(ColorConstants.black);
        setText(INITIAL_TEXT);
    }
}
