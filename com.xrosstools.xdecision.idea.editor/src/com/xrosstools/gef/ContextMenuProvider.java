package com.xrosstools.gef;

import com.xrosstools.gef.actions.Action;
import com.xrosstools.gef.actions.CommandAction;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.parts.EditPart;

import javax.swing.*;
import java.util.List;

public class ContextMenuProvider {
    private static final JMenuItem SEPARATOR = new JMenuItem();
    public void addSeparator(JPopupMenu menu) {
        menu.addSeparator();
    }

    public JMenuItem separator() {
        return SEPARATOR;
    }

    public JMenuItem createItem(Action action) {
        JMenuItem item = new JMenuItem(action.getText());
        item.addActionListener(action);
        item.setSelected(action.isChecked());
        return item;
    }

    public JMenuItem createItem(String text, boolean checked, Command command) {
        return createItem(new CommandAction(text, checked, command));
    }

    public JMenuItem createItem(String text, List<JMenuItem> items) {
        JMenu menu = new JMenu(text);
        for(JMenuItem item: items)
            menu.add(item);
        return menu;
    }

    public void addAll(JPopupMenu menu, List<JMenuItem> items) {
        for(JMenuItem item: items)
            if(item == SEPARATOR)
                menu.addSeparator();
            else
                menu.add(item);
    }
}
