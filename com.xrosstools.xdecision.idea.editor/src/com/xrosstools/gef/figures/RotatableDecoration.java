package com.xrosstools.gef.figures;

import java.awt.*;

public class RotatableDecoration extends Figure{
    private Point referencePoint;
    public void setReferencePoint(Point referencePoint) {
        this.referencePoint = referencePoint;
    }

    public Point getReferencePoint() {
        return referencePoint;
    }

    public double getAngle() {
        return Math.atan2(referencePoint.getY() - getY(), referencePoint.getX() - getX());
    }
}
