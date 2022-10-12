package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.idea.gef.figures.Label;

public class BasicExpressionFigure extends Label {
    private static final String INITIAL_TEXT = "...";
    //TODO support customizatiuon of cloor etc
    public BasicExpressionFigure() {
        setForegroundColor(ElementColors.TEXT_NORMAL);
        setText(INITIAL_TEXT);
    }
}
