package com.xrosstools.gef.parts;

import com.xrosstools.gef.figures.AbstractAnchor;
import com.xrosstools.gef.figures.Figure;

public abstract class ConnectionEditPart extends EditPart {
    public abstract Object getSourceModel();
    public abstract Object getTargetModel();

    public EditPart getSource() {
        return findEditPart(getSourceModel());
    }

    public EditPart getTarget() {
        return findEditPart(getTargetModel());
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
