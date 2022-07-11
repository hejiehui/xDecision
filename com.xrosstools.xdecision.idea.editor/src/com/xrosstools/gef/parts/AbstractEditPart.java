package com.xrosstools.gef.parts;

import com.xrosstools.gef.EditorPanel;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.util.IPropertySource;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.*;

public abstract class AbstractEditPart implements EditPart {
    private EditPartFactory factory;
    private EditPart parent;
    private Object model;
    private EditContext editContext;

    //TODO fix the name
    abstract protected void addChildPartVisual(EditPart childEditPart, int index);

    abstract protected void removeChildVisual(EditPart childEditPart);

    public void addNotify(){}

    public void activate(){
        if(getModel() instanceof IPropertySource)
            ((IPropertySource)getModel()).getListeners().addPropertyChangeListener(this);
    }

    public void deactivate(){
        if(getModel() instanceof IPropertySource)
            ((IPropertySource)getModel()).getListeners().removePropertyChangeListener(this);
    }

    @Override
    public final Object getModel() {
        return model;
    }

    @Override
    public final void setModel(Object model) {
        this.model = model;
        if(model instanceof IPropertySource) {
            PropertyChangeSupport support = ((IPropertySource)model).getListeners();
            support.removePropertyChangeListener(this);
            support.addPropertyChangeListener(this);
        }
    }

    public List getModelChildren() {
        return Collections.emptyList();
    }

    public List getChildren() {
        return Collections.emptyList();
    }

    public final void setParent(EditPart parent) {
        this.parent = parent;
    }

    public final EditPart getParent() {
        return parent;
    }

    public final void setEditPartFactory(EditPartFactory factory) {
        this.factory = factory;
    }

    public final EditPartFactory getEditPartFactory() {
        return factory;
    }

    public final void setContext(EditContext editContext) {
        this.editContext = editContext;
    }

    public EditContext getContext() {
        return editContext;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
        repaint();
    }

    public void execute(Command cmd) {
        getContext().getContentPane().execute(cmd);
    }

    public void repaint() {
        ((EditorPanel<IPropertySource>)getContext().getContentPane()).refresh();
    }

    public final void refreshModelPart(List parts, List models) {
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
                removeChild(parts, ep);
            }
        }
    }
    protected void reorderChild(List parts, EditPart editPart, int index) {
        removeChildVisual(editPart);
        parts.remove(editPart);
        parts.add(index, editPart);
        addChildPartVisual(editPart, index);
    }

    public final void addChildModel(List parts, Object child, int index) {
        EditPart childEditPart = factory.createEditPart(this, child);
        parts.add(index, childEditPart);
        addChildPartVisual(childEditPart, index);
        childEditPart.addNotify();
        childEditPart.activate();
    }

    public void removeChild(List parts, EditPart childEditPart) {
        childEditPart.deactivate();
        removeChildVisual(childEditPart);
        childEditPart.setParent(null);
        parts.remove(childEditPart);
    }
}
