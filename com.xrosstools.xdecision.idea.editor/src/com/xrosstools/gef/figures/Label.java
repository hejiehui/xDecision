package com.xrosstools.gef.figures;

import java.awt.*;

public class Label extends Figure {
    private IconFigure icon = new IconFigure();
    private Text text = new Text();

    public Label(String text) {
        this();
        this.text.setText(text);
    }

    public Label() {
        setLayoutManager(new ToolbarLayout(true, ToolbarLayout.ALIGN_CENTER, 5));
        add(icon);
        add(text);
    }

    public void setForegroundColor(Color foreground) {
        this.text.setForegroundColor(foreground);
    }

    public void setText(String text) {
        this.text.setText(text);
        repaint();
    }

    public void setIcon(String icon) {
        this.icon.setSource(icon);
    }

    public void setLabelAlignment(int position){
        //TODO
    }
}