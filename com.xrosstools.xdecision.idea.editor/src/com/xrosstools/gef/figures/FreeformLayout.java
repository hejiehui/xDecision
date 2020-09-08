package com.xrosstools.gef.figures;

import java.awt.*;

public class FreeformLayout implements LayoutManager {
    private int margin = 200;
    @Override
    public Dimension preferredLayoutSize(Figure parent) {
        int width=0;
        int height=0;

        for (Figure c: parent.getComponents()) {
            Dimension size = c.getPreferredSize();
            Point location = c.getLocation();

            int cWidth = c.getX() + (int)size.getWidth();
            if(cWidth > width)
                width = cWidth;

            int cHeight = c.getY() + (int)size.getHeight();
            if(cHeight > height)
                height = cHeight;

        }
        return new Dimension(width + margin, height + margin);
    }

    @Override
    public void layoutContainer(Figure parent) {
        for (Figure c : parent.getComponents()) {
            Dimension size = c.getPreferredSize();
            c.setSize(size);
        }
    }

    @Override
    public int getInsertionIndex(Figure parent, Point insertionPoint) {
        return 0;
    }

    @Override
    public void paintInsertionFeedback(Figure parent, Point insertionPoint, Graphics gef) {}
}
