package com.xrosstools.gef.figures;

import com.xrosstools.gef.parts.ConnectionEditPart;
import com.xrosstools.gef.routers.*;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Connection extends Figure {
    private Endpoint sourceEndpoint;
    private Endpoint targetEndpoint;
    private PointList points = new PointList();
    private ConnectionRouter router;
    private RotatableDecoration sourceDecoration;
    private RotatableDecoration targetDecoration;
    private Map<Figure, ConnectionLocator> children = new LinkedHashMap<>();

    public Connection() {
        sourceEndpoint = new Endpoint();
        targetEndpoint = new Endpoint();
        add(sourceEndpoint, new ConnectionEndpointLocator(true));
        add(targetEndpoint, new ConnectionEndpointLocator(false));

        targetDecoration = new ArrowDecoration();
        add(targetDecoration, new ConnectionEndpointLocator(false));
    }

    public ConnectionEditPart getConnectionPart() {
        return (ConnectionEditPart)getPart();
    }

    public void add(Figure child, ConnectionLocator locator) {
        add(child);
        children.put(child, locator);
    }

    @Override
    public void layout() {
        points.removeAllPoints();
        layoutEndpoints();

        if(router != null)
            router.route(this);

        for(Map.Entry<Figure, ConnectionLocator> childEntry: children.entrySet()) {
            childEntry.getKey().setLocation(childEntry.getValue().getLocation(points));
            childEntry.getKey().setSize(childEntry.getKey().getPreferredSize());
            childEntry.getKey().layout();

        }
    }

    /**
     * Layout start and end point of the connection
     */
    public void layoutEndpoints() {
        Figure sourceFigure = getConnectionPart().getSourceFigure();
        Figure targetFigure = getConnectionPart().getTargetFigure();

        Point start = sourceFigure.getCenter();
        Point end = targetFigure.getCenter();

        points.addPoint(getConnectionPart().getSourceAnchor().getLocation(end));
        points.addPoint(getConnectionPart().getTargetAnchor().getLocation(start));
    }

    public Figure findFigureAt(int x, int y) {
        for(Figure child: children.keySet()) {
            if(child.containsPoint(x, y))
                return child;
        }

        if(points.containsPoint(x, y))
            return this;

        return null;
    }
    @Override
    public boolean containsPoint(int x, int y) {
        if(points.containsPoint(x, y))
            return true;

        for(Figure child: children.keySet()) {
            if(child.containsPoint(x, y))
                return true;
        }

        return false;
    }

    @Override
    public final void painLink(Graphics graphics) {}

    @Override
    public void paintComponent(Graphics graphics) {
        Stroke s = setLineWidth(graphics, getLineWidth());

        graphics.drawPolyline(points.getXPoints(), points.getYPoints(), points.getPoints());

        restore(graphics, s);
    }

    @Override
    public void paintSelection(Graphics graphics) {}

    public void setRouter(ConnectionRouter router) {
        this.router = router;
    }

    public PointList getPoints() {
        return points;
    }

    public void setSourceDecoration(RotatableDecoration sourceDecoration) {
        this.sourceDecoration = sourceDecoration;
    }

    public void setTargetDecoration(RotatableDecoration targetDecoration) {
        this.targetDecoration = targetDecoration;
    }

    public Endpoint getSourceEndpoint() {
        return sourceEndpoint;
    }

    public Endpoint getTargetEndpoint() {
        return targetEndpoint;
    }

    private class ConnectionEndpointLocator implements ConnectionLocator {
        private boolean source;
        public ConnectionEndpointLocator(boolean source) {
            this.source = source;
        }

        @Override
        public Point getLocation(PointList points) {
            Point p = source ? points.getFirst() : points.getLast();
            p = new Point(p);
            p.translate(-Endpoint.SIZE/2, -Endpoint.SIZE/2);
            return p;
        }
    }
}