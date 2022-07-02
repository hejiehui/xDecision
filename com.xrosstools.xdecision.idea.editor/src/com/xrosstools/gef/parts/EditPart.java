package com.xrosstools.gef.parts;

import com.xrosstools.gef.EditorPanel;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.figures.AbstractAnchor;
import com.xrosstools.gef.figures.ChopboxAnchor;
import com.xrosstools.gef.figures.Figure;
import com.xrosstools.gef.util.IPropertySource;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    private List<ConnectionEditPart> sourceConnEditParts = new ArrayList<>();
    private List<ConnectionEditPart> targteConnEditParts = new ArrayList<>();
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
        getRoot().refresh();
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
        getFigure().add(childEditPart.getFigure(), index);
    }

    private void addChildPartVisual(EditPart childEditPart, int index) {
        if(childEditPart instanceof ConnectionEditPart) {
            addConnectionVisual((ConnectionEditPart)childEditPart, index);
        }else {
            addChildVisual(childEditPart, index);
        }
    }

    protected void removeChildVisual(EditPart childEditPart) {
        getFigure().remove(childEditPart.getFigure());
    }

    public final void addChildModel(List parts, Object child, int index) {
        EditPart childEditPart = factory.createEditPart(this, child);
        parts.add(index, childEditPart);
        addChildPartVisual(childEditPart, index);
        childEditPart.addNotify();
        childEditPart.activate();
    }

    public void addNotify() {
        getFigure();
        refresh();

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
            PropertyChangeSupport support = ((IPropertySource)model).getListeners();
            support.removePropertyChangeListener(this);
            support.addPropertyChangeListener(this);
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
        refreshSourceConnections();
        refreshTargetConnections();
        ((EditorPanel<IPropertySource>)context.getContentPane()).refresh();
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

    private void refreshModelPart(List parts, List models) {
        int size = parts.size();
        Map modelToEditPart = Collections.emptyMap();
        int i;
        if(size > 0) {
            modelToEditPart = new HashMap(size);
            for(i = 0; i < size; i++) {
                EditPart editPart = (EditPart)parts.get(i);
                modelToEditPart.put(editPart.getModel(), editPart);
            }
        }

        for(i = 0; i < models.size(); i++) {
            Object model = models.get(i);
            if(i >= parts.size() || ((EditPart)parts.get(i)).getModel() != model) {
                EditPart editPart = (EditPart)modelToEditPart.get(model);
                if(editPart != null) {
                    reorderChild(parts, editPart, i);
                } else {
                    addChildModel(parts, model, i);
                }
            }
        }

        size = parts.size();
        if(i < size) {
            List trash = new ArrayList(size - i);
            for(; i < size; i++)
                trash.add(parts.get(i));
            for(i = 0; i < trash.size(); i++)            {
                EditPart ep = (EditPart)trash.get(i);
                removeChild(ep);
            }
        }
    }

    protected void reorderChild(List parts, EditPart editpart, int index) {
        removeChildVisual(editpart);
        parts.remove(editpart);
        parts.add(index, editpart);
        addChildPartVisual(editpart, index);
    }
}
