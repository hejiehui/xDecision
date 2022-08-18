package com.xrosstools.idea.gef.figures;

import java.awt.*;

public class RoundedRectangle extends Figure {

    private int arcWidth = 5;
    private int arcHeight = 5;

    public int getArcWidth() {
        return arcWidth;
    }

    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
    }

    public int getArcHeight() {
        return arcHeight;
    }

    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawRoundRect(getX(), getY(), getWidth(),getHeight(), arcWidth, arcHeight);
    }
}
