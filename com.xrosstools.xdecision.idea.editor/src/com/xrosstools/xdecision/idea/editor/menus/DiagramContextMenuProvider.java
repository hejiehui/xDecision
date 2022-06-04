package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.ContextMenuProvider;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeDiagramPart;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class DiagramContextMenuProvider implements DecisionTreeMessages {
    private Project project;
    private DecisionTreeDiagram diagram;
    private NamedElementContainerContextMenuProvider elementContainerMenuProvider;

    public DiagramContextMenuProvider(Project project, DecisionTreeDiagram diagram, PropertyChangeListener listener) {
        this.project = project;
        this.diagram = diagram;

        elementContainerMenuProvider = new NamedElementContainerContextMenuProvider(project, diagram, listener);
    }

    public void buildContextMenu(JPopupMenu menu, DecisionTreeDiagramPart part) {
        elementContainerMenuProvider.buildContextMenu(menu, diagram.getDecisions());
        elementContainerMenuProvider.buildContextMenu(menu, diagram.getFactors());
        elementContainerMenuProvider.buildContextMenu(menu, diagram.getUserDefinedConstants());
        menu.addSeparator();

        elementContainerMenuProvider.buildContextMenu(menu, diagram.getUserDefinedTypes());

    }
}
