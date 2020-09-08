package com.xrosstools.gef;

import com.xrosstools.gef.parts.EditPart;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface ContextMenuProvider {

    JPopupMenu buildContextMenu(EditPart selecte);

    default JMenuItem createItem(String text, ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }

}
