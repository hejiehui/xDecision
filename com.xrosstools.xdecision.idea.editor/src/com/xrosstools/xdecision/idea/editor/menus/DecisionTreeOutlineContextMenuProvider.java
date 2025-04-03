package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.NamedElementContainerTreePart;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.NamedElementTreePart;

import javax.swing.*;

public class DecisionTreeOutlineContextMenuProvider extends ContextMenuProvider implements DecisionTreeMessages {
    private Project project;
    private DecisionTreeDiagram diagram;
    private NamedElementContainerContextMenuProvider namedElementContainerProvider;
    private NamedElementContextMenuProvider namedElementContextMenuProvider;

    public DecisionTreeOutlineContextMenuProvider(Project project) {
        this.project = project;
        namedElementContextMenuProvider = new NamedElementContextMenuProvider(project);
        namedElementContainerProvider = new NamedElementContainerContextMenuProvider(project);
    }

    public void setDiagram(DecisionTreeDiagram diagram) {
        this.diagram = diagram;
        namedElementContextMenuProvider.setDiagram(diagram);
        namedElementContainerProvider.setDiagram(diagram);
    }

    public JPopupMenu buildContextMenu(Object selected) {
        AbstractTreeEditPart editPart = (AbstractTreeEditPart )selected;
        JPopupMenu menu = new JPopupMenu();
        if(editPart instanceof NamedElementContainerTreePart) {
            namedElementContainerProvider.buildContextMenu(menu, (NamedElementContainerTreePart)editPart);
        }else if(editPart instanceof NamedElementTreePart) {
            namedElementContextMenuProvider.buildContextMenu(menu, (NamedElementTreePart)editPart);
        }
        return menu;
    }
}
