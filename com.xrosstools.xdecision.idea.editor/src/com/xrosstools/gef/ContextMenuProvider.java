package com.xrosstools.gef;

import com.xrosstools.gef.actions.Action;
import com.xrosstools.gef.actions.CommandAction;
import com.xrosstools.gef.commands.Command;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class ContextMenuProvider {
    private static final JMenuItem SEPARATOR = new JMenuItem();

    private PropertyChangeListener listener;

    public abstract JPopupMenu buildContextMenu(Object selected);

    protected JPopupMenu buildDisplayMenu(Object selected) {
        JPopupMenu menu = buildContextMenu(selected);
        attachListener(menu);
        return menu;
    }
    public ContextMenuProvider(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public static void addSeparator(JPopupMenu menu) {
        menu.addSeparator();
    }

    public static JMenuItem separator() {
        return SEPARATOR;
    }

    public static JMenuItem createItem(Action action) {
        JMenuItem item = new JMenuItem(action.getText());
        item.addActionListener(action);
        item.setSelected(action.isChecked());
        return item;
    }

    public static JMenuItem createItem(String text, boolean checked, Command command) {
        return createItem(new CommandAction(text, checked, command));
    }

    public static JMenuItem createItem(String text, List<JMenuItem> items) {
        JMenu menu = new JMenu(text);
        for(JMenuItem item: items)
            menu.add(item);
        return menu;
    }

    public static void addAll(JPopupMenu menu, List<JMenuItem> items) {
        for(JMenuItem item: items)
            if(item == SEPARATOR)
                menu.addSeparator();
            else
                menu.add(item);
    }

    public void attachListener(MenuElement menuElement) {
        if(menuElement.getSubElements() != null && menuElement.getSubElements().length > 0) {
            for(MenuElement item: menuElement.getSubElements()){
                attachListener(item);
            }
        }else {
            if(((JMenuItem) menuElement).getActionListeners().length != 0)
                ((Action) ((JMenuItem) menuElement).getActionListeners()[0]).setListener(listener);
        }
    }
}
