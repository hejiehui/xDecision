package com.xrosstools.gef.routers;

import com.xrosstools.gef.figures.Connection;

import java.awt.*;

public class RightAngleRouter implements ConnectionRouter {
    public static final int HEIGHT_FIRST = 0;
    public static final int WIDTH_FIRST = 1;

    private int style;

    public RightAngleRouter(int style) {
        if(style != 0 && style != 1)
            throw new IllegalArgumentException("Style is not a legal value");

        this.style = style;
    }

    @Override
    public void route(Connection conn) {
        PointList pl = conn.getPoints();
        Point start = pl.getStartPoint();
        Point end = pl.getLast();

        Point middle;
        pl.addPoint(start);
        if (style == HEIGHT_FIRST) {
            middle = new Point(start.x, end.y);
            pl.addPoint(middle);
            if(middle.x == end.x)
                return;
        } else {
            middle = new Point(end.x, start.y);
            pl.addPoint(middle);
            if(middle.y == end.y)
                return;
        }

        pl.addPoint(end);
    }
}