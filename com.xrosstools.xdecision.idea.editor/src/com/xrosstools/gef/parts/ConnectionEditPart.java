package com.xrosstools.gef.parts;

import com.xrosstools.gef.figures.AbstractAnchor;
import com.xrosstools.gef.figures.Figure;

public abstract class ConnectionEditPart extends GraphicalEditPart {
    private GraphicalEditPart source;
    private GraphicalEditPart target;

//    @Override
//    public void remove() {
//        getSource().removeSourceConnection(this);
//        getTarget().removeTargetConnection(this);
//    }

    public GraphicalEditPart getSource() {
        return source;
    }

    public GraphicalEditPart getTarget() {
        return target;
    }

    public void setSource(GraphicalEditPart source) {
        this.source = source;
    }

    public void setTarget(GraphicalEditPart target) {
        this.target = target;
    }

    public Figure getSourceFigure() {
        return getSource().getFigure();
    }

    public Figure getTargetFigure() {
        return getTarget().getFigure();
    }

    public AbstractAnchor getSourceAnchor() {
        return getSource().getSourceConnectionAnchor(this);
    }

    public AbstractAnchor getTargetAnchor() {
        return getTarget().getTargetConnectionAnchor(this);
    }
}
