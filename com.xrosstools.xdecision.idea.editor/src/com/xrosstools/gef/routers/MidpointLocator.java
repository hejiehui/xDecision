package com.xrosstools.gef.routers;

import java.awt.*;

public class MidpointLocator implements ConnectionLocator {
    @Override
    public Point getLocation(PointList points) {
        Point start = points.get(0);
        Point end = points.getLast();
        return new Point((start.x + end.x)/2, (start.y + end.y)/2);
    }
}
