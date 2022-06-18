package com.xrosstools.gef.figures;

import java.awt.*;

public class Label extends Figure {
    private IconFigure icon;
    private Text text;
    private Color foreground;

    public Label(String text) {
        this();
        setText(text);
    }

    public Label() {
        setLayoutManager(new ToolbarLayout(true, ToolbarLayout.ALIGN_CENTER, 5));
    }

    public void setForegroundColor(Color foreground) {
        this.foreground = foreground;
    }

    public void setText(String textStr) {
        if (text == null) {
            text = new Text();
            add(text);
        }
        text.setText(textStr);
        if(foreground != null)
            text.setForegroundColor(foreground);

        repaint();
    }

    public void setIcon(String iconLoc) {
        if (icon == null) {
            icon = new IconFigure();
            add(icon);
        }
        icon.setSource(iconLoc);
    }

    public void setLabelAlignment(int position){
        //TODO
    }
}