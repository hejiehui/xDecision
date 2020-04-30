package com.xrosstools.gef;

import com.xrosstools.gef.parts.EditPart;

import javax.swing.*;

public interface ContextMenuProvider {

    JPopupMenu buildContextMenu(EditPart selecte);

    default JMenuItem createItem(String text, Action action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }

}
