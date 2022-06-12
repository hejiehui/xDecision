package com.xrosstools.gef.parts;

import com.xrosstools.gef.EditorPanel;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.figures.AbstractAnchor;
import com.xrosstools.gef.figures.ChopboxAnchor;
import com.xrosstools.gef.figures.Connection;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.util.IPropertySource;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

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
    protected EditPolicy createEditPolicy() {
        return new EditPolicy();
    }

    protected void refreshVisuals() {}

    public void execute(Command cmd) {
        cmd.run();
        getRoot().build();
    }

    /**
     * Being double clicked
     */
    public void performAction() {}

    protected List getModelChildren() {
        return Collections.EMPTY_LIST;
    }

    public List<EditPart> getChildren() {
        return childEditParts;
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

    protected void removeChildVisual(EditPart childEditPart) {
        getFigure().remove(childEditPart.getFigure());
    }

    public final void addChildModel(Object child, int index) {
        EditPart childEditPart = factory.createEditPart(this, child);
        childEditParts.add(index, childEditPart);
        childEditPart.build();
        addChildVisual(childEditPart, index);
        childEditPart.activate();
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

    protected void activate(){}

    protected void deactivate(){}

    public void removeChild(EditPart childEditPart) {
        childEditPart.deactivate();
        removeChildVisual(childEditPart);
        childEditPart.setParent(null);
        childEditParts.remove(childEditPart);
        getFigure().remove(childEditPart.getFigure());
    }

    public void removeSourceConnection(ConnectionEditPart connectionEditPart) {
        getFigure().getConnection().remove(connectionEditPart.getFigure());
    }

    public void removeTargetConnection(ConnectionEditPart connectionEditPart) {
        getFigure().getConnection().remove(connectionEditPart.getFigure());
    }

    public final void setEditPartFactory(EditPartFactory factory) {
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
        return getFigure() != null;
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
        refreshVisuals();
        refreshChildren();
        ((EditorPanel<IPropertySource>)context.getContentPane()).refresh();
    }

    /**
     * The following is copy from GEF AbstractEditPart.refreshChildren
     */
    private void refreshChildren() {
        List children = getChildren();
        int size = children.size();
        Map modelToEditPart = Collections.emptyMap();
        int i;
        if(size > 0) {
            modelToEditPart = new HashMap(size);
            for(i = 0; i < size; i++) {
                EditPart editPart = (EditPart)children.get(i);
                modelToEditPart.put(editPart.getModel(), editPart);
            }
        }

        List modelObjects = getModelChildren();
        for(i = 0; i < modelObjects.size(); i++) {
            Object model = modelObjects.get(i);
            if(i >= children.size() || ((EditPart)children.get(i)).getModel() != model) {
                EditPart editPart = (EditPart)modelToEditPart.get(model);
                if(editPart != null) {
                    reorderChild(editPart, i);
                } else {
                    addChildModel(model, i);
                }
            }
        }

        size = children.size();
        if(i < size) {
            List trash = new ArrayList(size - i);
            for(; i < size; i++)
                trash.add(children.get(i));
            for(i = 0; i < trash.size(); i++)            {
                EditPart ep = (EditPart)trash.get(i);
                removeChild(ep);
            }
        }
    }

    protected void reorderChild(EditPart editpart, int index) {
        removeChildVisual(editpart);
        List children = getChildren();
        children.remove(editpart);
        children.add(index, editpart);
        addChildVisual(editpart, index);
    }
}
