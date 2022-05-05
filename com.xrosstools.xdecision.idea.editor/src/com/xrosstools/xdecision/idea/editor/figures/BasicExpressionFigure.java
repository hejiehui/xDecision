package com.xrosstools.xdecision.idea.editor.figures;

import com.xrosstools.gef.figures.Label;

import java.awt.*;

public class BasicExpressionFigure extends Label {
    private static final String INITIAL_TEXT = "...";
    //TODO support customizatiuon of cloor etc
    public BasicExpressionFigure() {
        setForegroundColor(Color.black);
        setText(INITIAL_TEXT);
    }
}
