package com.xrosstools.gef.parts;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface EditPart extends PropertyChangeListener {
    Object getModel();

    void setModel(Object model);

    void setParent(EditPart parent);

    EditPart getParent();

    List getModelChildren();

    //EditPart children
    List getChildren();

    void setEditPartFactory(EditPartFactory factory);

    EditPartFactory getEditPartFactory();

    void setContext(EditContext editContext);

    EditContext getContext();

    void addNotify();

    void activate();

    void deactivate();

    void refresh();
}
