package com.xrosstools.gef.parts;

import com.xrosstools.gef.Command;
import com.xrosstools.gef.figures.AbstractAnchor;
import com.xrosstools.gef.figures.ChopboxAnchor;
import com.xrosstools.gef.figures.Connection;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.util.IPropertySource;
import com.xrosstools.xdecision.idea.editor.DecisionTreeDiagramPanel;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreePartFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class EditPart implements PropertyChangeListener {
    public static final int SELECTED_NONE = 0;

    public static final int SELECTED = 1;

    private EditContext context;
    private Figure figure;
    private Object model;
    private int flags;
    private EditPart parent;
    private List<EditPart> childEditParts = new ArrayList<>();
    private int selected;

    private EditPartFactory factory;
    private EditPolicy editPolicy;

    protected abstract Figure createFigure();
    protected abstract EditPolicy createEditPolicy();

    protected void refreshVisuals() {}

    public void execute(Command cmd) {
        cmd.execute();
        getRoot().build();
    }

    /**
     * Being double clicked
     */
    public void performAction() {}

    protected List getModelChildren() {
        return Collections.EMPTY_LIST;
    }

    public List getModelSourceConnections() {
        return Collections.EMPTY_LIST;
    }

    public List getModelTargetConnections() {
        return Collections.EMPTY_LIST;
    }

    public void addChildVisual(EditPart childEditPart, int index) {
        getFigure().add(childEditPart.getFigure(), index);
    }

    public final void addChildModel(Object child, int index) {
        EditPart childEditPart = factory.createEditPart(this, child);
        childEditParts.add(childEditPart);
        childEditPart.build();
        addChildVisual(childEditPart, index);
    }

    public final void addConnection(Object conn) {
        ConnectionEditPart connPart = (ConnectionEditPart)factory.createEditPart(this, conn);
        Connection connFigure = (Connection)connPart.getFigure();
        connFigure.setParent(getFigure());
        getFigure().getConnection().add(connFigure);
    }

    public void remove() {
        for(Object conn : getModelSourceConnections())
            findEditPart(conn).remove();

        for(Object conn : getModelTargetConnections())
            findEditPart(conn).remove();

        parent.removeChild(this);
    }

    public void removeChild(EditPart childEditPart) {
        getFigure().remove(childEditPart.getFigure());
    }

    public void removeSourceConnection(ConnectionEditPart connectionEditPart) {
        getFigure().getConnection().remove(connectionEditPart.getFigure());
    }

    public void removeTargetConnection(ConnectionEditPart connectionEditPart) {
        getFigure().getConnection().remove(connectionEditPart.getFigure());
    }

    public final void setEditPartFactory(DecisionTreePartFactory factory) {
        this.factory = factory;
    }

    public final void build() {
        getFigure();
        List children = getModelChildren();
        for (int i = 0; i < children.size(); i++) {
            addChildModel(children.get(i), i);
        }

        for(Object conn: getModelSourceConnections()) {
            addConnection(conn);
        }

        refreshVisuals();
    }

    public final Figure getFigure() {
        if (figure == null) {
            figure = createFigure();
            figure.setPart(this);
            figure.setRootPane(context.getContentPane());
            refreshVisuals();
        }
        return figure;
    }

    public final EditPolicy getEditPolicy() {
        if (editPolicy == null) {
            editPolicy = createEditPolicy();
            editPolicy.setHost(this);
        }
        return editPolicy;
    }

    public final Object getModel() {
        return model;
    }

    public final void setModel(Object model) {
        this.model = model;
        if(model instanceof IPropertySource) {
            ((IPropertySource)model).getListeners().addPropertyChangeListener(this);
        }
    }

    public final EditPart getRoot() {
        EditPart part = this;
        while(part.parent != null) {
            part = part.parent;
        }

        return part;
    }

    public boolean isSelectable() {
        if(model == null || !(model instanceof IPropertySource))
            return false;

        return ((IPropertySource)model).getPropertyDescriptors().length != 0;
    }

    public final EditPart findEditPart(Object model) {
        return context.findEditPart(model);
    }

    public final Figure findFigure(Object model) {
        return context.findFigure(model);
    }

    public final void setParent(EditPart parent) {
        this.parent = parent;
    }

    public final EditPart getParent() {
        return parent;
    }

    public final int getSelected() {
        return selected;
    }

    public EditContext getContext() {
        return context;
    }

    public void setContext(EditContext context) {
        this.context = context;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        refreshVisuals();
        refresh();
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
        ((DecisionTreeDiagramPanel)context.getContentPane()).refresh();
    }
}
