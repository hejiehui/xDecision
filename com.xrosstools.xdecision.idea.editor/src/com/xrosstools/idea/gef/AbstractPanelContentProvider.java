package com.xrosstools.idea.gef;

import com.intellij.openapi.util.IconLoader;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.xdecision.idea.editor.Activator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

public abstract class AbstractPanelContentProvider<T extends IPropertySource> implements PanelContentProvider<T> {
    private EditorPanel editorPanel;

    public void setEditorPanel(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    public EditorPanel getEditorPanel() {
        return editorPanel;
    }

    public void preBuildRoot(){}
    public void postBuildRoot(){}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        editorPanel.refresh();
    }

    public void createConnection(Object connModel){
        editorPanel.createConnection(connModel);
    }

    public void createModel(Object model){
        editorPanel.createModel(model);
    }

    public JButton createToolbarButton(Action action, String iconName, String tooltip) {
        JButton btn = new JButton(IconLoader.findIcon(Activator.getIconPath(iconName)));
        btn.setToolTipText(tooltip);
        btn.setContentAreaFilled(false);
        btn.addActionListener(action);
        btn.setSize(new Dimension(32, 32));
        btn.setPreferredSize(new Dimension(32, 32));
        return btn;
    }

    public JButton createPaletteButton(ActionListener action, String iconName, String tooltip) {
        JButton btn = new JButton(tooltip, IconLoader.findIcon(Activator.getIconPath(iconName)));
        btn.setContentAreaFilled(false);
        btn.addActionListener(action);
        return btn;
    }
}
