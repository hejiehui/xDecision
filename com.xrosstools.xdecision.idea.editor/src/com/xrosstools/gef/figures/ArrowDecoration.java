package com.xrosstools.gef.figures;

import com.xrosstools.gef.routers.PointList;

import java.awt.*;

public class ArrowDecoration extends RotatableDecoration {
    private static int SIZE = 10;
    private int aWidth;
    private int aHeight;

    public ArrowDecoration() {
        this(SIZE, SIZE);
    }

    public ArrowDecoration(int aWidth, int aHeight) {
        this.aWidth = aWidth;
        this.aHeight = aHeight;

    }

    @Override
    public void layout() {
        Connection parent = (Connection) getParent();
        PointList pl = parent.getPoints();
        setLocation(pl.getLast());
        setReferencePoint(pl.get(pl.size() - 2));
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics.create();

        Polygon poly = new Polygon(new int[] { 0, aWidth, aWidth }, new int[] { 0, aHeight/2, -aHeight/2 }, 3);

        poly.translate(getX(), getY());

        g2d.setColor(getForegroundColor());
        g2d.rotate(getAngle(), getX(), getY());

        g2d.fill(poly);
    }
}
