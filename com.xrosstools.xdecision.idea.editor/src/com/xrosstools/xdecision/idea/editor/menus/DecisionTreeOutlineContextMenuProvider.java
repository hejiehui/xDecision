package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.TreeEditPart;
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

    public JPopupMenu buildContextMenu(Object selected) {
        TreeEditPart editPart = (TreeEditPart )selected;
        JPopupMenu menu = new JPopupMenu();
        if(editPart instanceof NamedElementContainerTreePart) {
            namedElementContainerProvider.buildContextMenu(menu, (NamedElementContainerTreePart)editPart);
        }else if(editPart instanceof NamedElementTreePart) {
            namedElementContextMenuProvider.buildContextMenu(menu, (NamedElementTreePart)editPart);
        }
        return menu;
    }
}
