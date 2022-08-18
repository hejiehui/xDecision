package com.xrosstools.idea.gef.figures;

import java.awt.*;

public class ChopboxAnchor extends AbstractAnchor {
    public ChopboxAnchor(Figure owner) {
        setOwner(owner);
    }

    /**
     * Return null if the point is inside figure
     */
    @Override
    public Point getLocation(Point refernce) {
        Figure owner = getOwner();
        //Return null if the point is inside figure
        if(owner.getBound().contains(refernce))
            return null;

        Point start = owner.getCenter();
        Point end = refernce;

        if(start.x == end.x)
            return start.y < end.y ? owner.getBottom() : owner.getTop();

        if(start.y == end.y)
            return start.x < end.x ? owner.getRight() : owner.getLeft();

        float deltaY = end.y - start.y;
        float deltaX = end.x - start.x;

        float kr = deltaY/deltaX;

        int w = owner.getWidth()/2;
        int h = owner.getHeight()/2;
        float ko = (float) h/(float) w;

        Point p = owner.getCenter().getLocation();

        float absKr = Math.abs(kr);

        int x = absKr > ko ? (int) (h / kr) : w;
        int y = absKr > ko ? h : (int) (w * kr);

        if(deltaX > 0 && deltaY > 0) {
            x = kr > ko ? (int) (h / kr) : w;
            y = kr > ko ? h : (int) (w * kr);
        }else if(deltaX > 0 && deltaY < 0) {
            x = absKr > ko ? -(int) (h / kr) : w;
            y = absKr > ko ? -h : (int) (w * kr);
        }else if(deltaX < 0 && deltaY > 0) {
            x = absKr > ko ? (int) (h / kr) : -w;
            y = absKr > ko ? h : -(int) (w * kr);
        }else if(deltaX < 0 && deltaY < 0) {
            x = absKr > ko ? -(int) (h / kr) : -w;
            y = absKr > ko ? -h : -(int) (w * kr);
        }

        p.translate(x, y);

        return p;
    }
}
