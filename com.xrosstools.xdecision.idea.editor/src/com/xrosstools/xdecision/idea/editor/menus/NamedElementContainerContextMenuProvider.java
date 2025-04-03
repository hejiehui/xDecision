package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.InputTextCommandAction;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.commands.definition.CreateElementCommand;
import com.xrosstools.xdecision.idea.editor.commands.definition.CreateNamedTypeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.NamedElementContainerTreePart;

import javax.swing.*;

import static com.xrosstools.idea.gef.ContextMenuProvider.createItem;

public class NamedElementContainerContextMenuProvider implements DecisionTreeMessages {
    private Project project;
    private DecisionTreeDiagram diagram;

    public NamedElementContainerContextMenuProvider(Project project) {
        this.project = project;
    }

    public void setDiagram(DecisionTreeDiagram diagram) {
        this.diagram = diagram;
    }

    public void buildContextMenu(JPopupMenu menu, NamedElementContainerTreePart containerPart) {
        buildContextMenu(menu, (NamedElementContainer<NamedElement>)containerPart.getModel());
    }
    
    public void buildContextMenu(JPopupMenu menu, NamedElementContainer container) {
        String createMsg = String.format(CREATE_NEW_TEMPLATE_MSG, container.getElementType().getTypeName());
        
        String[] qualifiedTypes = container.getElementType().getQualifiedDataTypes(diagram);
        if(qualifiedTypes.length == 0)
            menu.add(createItem(createElementMenu(createMsg, container)));
        else
            menu.add(createNamedTypeMenu(container, qualifiedTypes));
    }

    public InputTextCommandAction createElementMenu(String createMsg, NamedElementContainer<NamedElement> container) {
        String typeName = container.getElementType().getTypeName();
        
        return new InputTextCommandAction(project, createMsg, typeName, "", new CreateElementCommand(diagram,container));
    }

    public JMenu createNamedTypeMenu(NamedElementContainer<NamedElement> container, String[] typeNames) {
        String category = container.getElementType().getTypeName();
        JMenu createElementMenu = new JMenu(String.format(CREATE_NEW_TEMPLATE_MSG, category));
        
        // create element
        for(String typeName: typeNames) {
            createElementMenu.add(createItem(new InputTextCommandAction(project, typeName, String.format("Set %s name", category), "", new CreateNamedTypeCommand(diagram, container, typeName))));
        }

        return createElementMenu;
    }
}
