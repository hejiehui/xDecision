package com.xrosstools.gef;

import com.xrosstools.gef.parts.EditPart;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface ContextMenuProvider {

    JPopupMenu buildContextMenu(EditPart selecte);

    default JMenuItem createItem(Action action) {
        JMenuItem item = new JMenuItem(action.getText());
        item.addActionListener(action);
        item.setSelected(action.isChecked());
        return item;
    }

}
