package com.xrosstools.gef.figures;

import java.awt.*;

public class RectangleFigure extends Figure {

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawRect(getX(), getY(), getWidth(),getHeight());
    }
}
