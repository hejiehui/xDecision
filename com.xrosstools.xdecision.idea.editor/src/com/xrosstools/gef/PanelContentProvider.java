package com.xrosstools.gef;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.IconLoader;
import com.xrosstools.gef.actions.Action;
import com.xrosstools.gef.parts.EditContext;
import com.xrosstools.gef.parts.EditPartFactory;
import com.xrosstools.gef.parts.TreeEditPartFactory;
import com.xrosstools.gef.util.IPropertySource;
import com.xrosstools.xdecision.idea.editor.Activator;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class PanelContentProvider<T extends IPropertySource> implements PropertyChangeListener {
    private EditorPanel editorPanel;
    public abstract T getContent() throws Exception;
    public abstract void saveContent() throws Exception;
    public abstract ContextMenuProvider getContextMenuProvider();
    public abstract ContextMenuProvider getOutlineContextMenuProvider();
    public abstract void buildPalette(JPanel palette);
    public abstract void buildToolbar(JToolBar toolbar);
    public abstract EditPartFactory createEditPartFactory(EditContext context);
    public abstract TreeEditPartFactory createTreePartFactory(EditContext context);

    public void preBuildRoot(){}
    public void postBuildRoot(){}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        editorPanel.rebuild();
        save();
    }

    public void createConnection(Object connModel){
        editorPanel.createConnection(new DecisionTreeNodeConnection());
    }

    public void createModel(Object model){
        editorPanel.createModel(new DecisionTreeNode());
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

    public void setEditorPanel(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    public void save() {
        ApplicationManager.getApplication().runWriteAction(() -> {
            try {
                saveContent();
            } catch (Throwable e) {
                throw new IllegalStateException("Can not save change", e);
            }
        });
    }


    public EditorPanel getEditorPanel() {
        return editorPanel;
    }
}