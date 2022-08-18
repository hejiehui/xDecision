package com.xrosstools.idea.gef.util;

import javax.swing.*;

public interface IPropertyDescriptor {
    void setId(Object id);
    Object getId();

    void setCategory(String category);
    String getCategory();

    JComponent getEditor(Object value);

    String getValue(int index);
}
