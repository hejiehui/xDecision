package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.ContextMenuProvider;
import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.NamedElementContainerTreePart;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.NamedElementTreePart;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class DecisionTreeOutlineContextMenuProvider extends ContextMenuProvider implements DecisionTreeMessages {
    private Project project;
    private DecisionTreeDiagram diagram;
    private NamedElementContainerContextMenuProvider namedElementContainerProvider;
    private NamedElementContextMenuProvider namedElementContextMenuProvider;

    public DecisionTreeOutlineContextMenuProvider(Project project, DecisionTreeDiagram diagram, PropertyChangeListener listener) {
        super(listener);
        this.project = project;
        this.diagram = diagram;
        namedElementContextMenuProvider = new NamedElementContextMenuProvider(project, diagram, listener);
        namedElementContainerProvider = new NamedElementContainerContextMenuProvider(project, diagram, listener);
    }

    public JPopupMenu buildContextMenu(TreeEditPart selected) {
        JPopupMenu menu = new JPopupMenu();
        if(selected instanceof NamedElementContainerTreePart) {
            namedElementContainerProvider.buildContextMenu(menu, (NamedElementContainerTreePart)selected);
        }else if(selected instanceof NamedElementTreePart) {
            namedElementContextMenuProvider.buildContextMenu(menu, (NamedElementTreePart)selected);
        }
        return menu;
    }
}
