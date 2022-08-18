package com.xrosstools.idea.gef.routers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointList {
    private int delta = 2;

    private List<Point> points = new ArrayList();
    public void addPoint(Point p) {
        points.add(p);
    }

    public int size() {
        return points.size();
    }

    public int[] getXPoints() {
        int[] x = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
        }
        return x;
    }
    public int[] getYPoints() {
        int[] y = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            y[i] = points.get(i).y;
        }
        return y;
    }

    public int getPoints() {
        return points.size();
    }

    public Point getFirst() {
        return points.get(0);
    }

    public boolean containsPoint(int x, int y) {
        Rectangle r = new Rectangle(x - delta / 2, y - delta / 2, delta, delta);

        int count = getPoints();
        for(int i = 1; i < count; i++) {
            Point start = get(i-1);
            Point end = get(i);

            if(r.intersectsLine(start.x, start.y, end.x, end.y)) {
                return true;
            }
        }
        return false;
    }

    public Point get(int index) {
        return points.get(index);
    }

    public Point getLast() {
        return points.get(points.size() -1);
    }

    public void removeAllPoints() {
        points.clear();
    }
}