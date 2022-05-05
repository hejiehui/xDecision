package com.xrosstools.gef;

import com.xrosstools.gef.actions.Action;
import com.xrosstools.gef.parts.EditPart;

import javax.swing.*;

public class ContextMenuProvider {

    public void addSeparator(JPopupMenu menu) {
        menu.addSeparator();
    }

    public JMenuItem createItem(Action action) {
        JMenuItem item = new JMenuItem(action.getText());
        item.addActionListener(action);
        item.setSelected(action.isChecked());
        return item;
    }
}
