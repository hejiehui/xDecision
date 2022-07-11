package com.xrosstools.gef.parts;

import com.xrosstools.gef.EditorPanel;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.figures.AbstractAnchor;
import com.xrosstools.gef.figures.ChopboxAnchor;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.util.IPropertySource;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class GraphicalEditPart extends AbstractEditPart {
    public static final int SELECTED_NONE = 0;

    public static final int SELECTED = 1;

    private Figure figure;
    private int flags;
    private List<GraphicalEditPart> childEditParts = new ArrayList<>();
    private List<ConnectionEditPart> sourceConnEditParts = new ArrayList<>();
    private List<ConnectionEditPart> targteConnEditParts = new ArrayList<>();
    private int selected;

    private EditPolicy editPolicy;

    protected abstract Figure createFigure();
    protected EditPolicy createEditPolicy() {
        return new EditPolicy();
    }

    protected void refreshVisuals() {}

    /**
     * Being double clicked
     */
    public void performAction() {}

    public List<GraphicalEditPart> getChildren() {
        return childEditParts;
    }

    public List<ConnectionEditPart> getSourceConnections() {
        return sourceConnEditParts;
    }

    public List getModelSourceConnections() {
        return Collections.EMPTY_LIST;
    }

    public List<ConnectionEditPart> getTargetConnections() {
        return targteConnEditParts;
    }

    public List getModelTargetConnections() {
        return Collections.EMPTY_LIST;
    }

    public void addConnectionVisual(ConnectionEditPart childEditPart, int index) {
        getFigure().add(childEditPart.getFigure(), index);
    }

    public void addChildVisual(EditPart childEditPart, int index) {
        getFigure().add(((GraphicalEditPart)childEditPart).getFigure(), index);
    }

    protected void addChildPartVisual(EditPart childEditPart, int index) {
        if(childEditPart instanceof ConnectionEditPart) {
            addConnectionVisual((ConnectionEditPart)childEditPart, index);
        }else {
            addChildVisual(childEditPart, index);
        }
    }

    protected void removeChildVisual(EditPart childEditPart) {
        getFigure().remove(((GraphicalEditPart)childEditPart).getFigure());
    }

    public void addNotify() {
        getFigure();
        refresh();
    }

//    public void remove() {
//        for(Object conn : getModelSourceConnections())
//            findEditPart(conn).remove();
//
//        for(Object conn : getModelTargetConnections())
//            findEditPart(conn).remove();
//
//        getParent().removeChild(childEditParts, this);
//    }

    public void removeSourceConnection(ConnectionEditPart connectionEditPart) {
        getFigure().getConnection().remove(connectionEditPart.getFigure());
    }

    public void removeTargetConnection(ConnectionEditPart connectionEditPart) {
        getFigure().getConnection().remove(connectionEditPart.getFigure());
    }

    public final void build() {
        getFigure();
        List children = getModelChildren();
        for (int i = 0; i < children.size(); i++) {
            addChildModel(childEditParts, children.get(i), i);
        }

        children = getModelSourceConnections();
        for (int i = 0; i < children.size(); i++) {
            addChildModel(sourceConnEditParts, children.get(i), i);
        }

        refreshVisuals();
    }

    public final Figure getFigure() {
        if (figure == null) {
            figure = createFigure();
            figure.setPart(this);
            figure.setRootPane(getContext().getContentPane());
        }
        return figure;
    }

    public  Figure getContentPane() {
        return getFigure();
    }

    public final EditPolicy getEditPolicy() {
        if (editPolicy == null) {
            editPolicy = createEditPolicy();
            editPolicy.setHost(this);
        }
        return editPolicy;
    }

    public boolean isSelectable() {
        return getFigure() != null;
    }

    public final GraphicalEditPart findEditPart(Object model) {
        return getContext().findEditPart(model);
    }

    public final int getSelected() {
        return selected;
    }

    public void showSourceFeedback() {}

    public void eraseSourceFeedback() {}

    public void showTargetFeedback() {}

    public void eraseTargetFeedback() {}

    public AbstractAnchor getSourceConnectionAnchor(ConnectionEditPart connectionEditPart) {
        return new ChopboxAnchor(getFigure());
    }

    public AbstractAnchor getTargetConnectionAnchor(ConnectionEditPart connectionEditPart) {
        return new ChopboxAnchor(getFigure());
    }

    public void refresh() {
        refreshVisuals();
        refreshChildren();
        refreshSourceConnections();
        refreshTargetConnections();
//        ((EditorPanel<IPropertySource>)getContext().getContentPane()).refresh();
    }

    /**
     * The following is copy from GEF AbstractEditPart.refreshChildren
     */
    private void refreshChildren() {
        refreshModelPart(getChildren(), getModelChildren());
    }

    protected void refreshSourceConnections() {
        refreshModelPart(getSourceConnections(), getModelSourceConnections());
    }

    protected void refreshTargetConnections() {
        refreshModelPart(getTargetConnections(), getModelTargetConnections());
    }
}
