package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.CommandAction;
import com.xrosstools.idea.gef.actions.InputTextCommandAction;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.commands.definition.ChangeElementNameCommand;
import com.xrosstools.xdecision.idea.editor.commands.definition.ChangeElementTypeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementTypeEnum;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedType;
import com.xrosstools.xdecision.idea.editor.treeparts.definition.NamedElementTreePart;

import javax.swing.*;

import static com.xrosstools.idea.gef.ContextMenuProvider.addSeparator;
import static com.xrosstools.idea.gef.ContextMenuProvider.createItem;

public class NamedElementContextMenuProvider implements DecisionTreeMessages {
    private Project project;
    private DecisionTreeDiagram diagram;

    public NamedElementContextMenuProvider(Project project) {
        this.project = project;
    }

    public void setDiagram(DecisionTreeDiagram diagram) {
        this.diagram = diagram;
    }

    public void buildContextMenu(JPopupMenu menu, NamedElementTreePart namedElementPart) {
        NamedElement element = (NamedElement)namedElementPart.getModel();
        
        NamedElementContainer container = (NamedElementContainer<?>)namedElementPart.getParent().getModel();
        NamedElementTypeEnum type = container.getElementType();
        String typeName = type.getTypeName();
        
        menu.add(createItem(new InputTextCommandAction(project, String.format(CHANGE_NAME_MSG, typeName), "New Name", element.getName(), new ChangeElementNameCommand(diagram, container, element))));
        
        changeTypeMenu(menu, element);
        
        addSeparator(menu);
        menu.add(createItem(new CommandAction(String.format(REMOVE_MSG, element.getName()), false, type.createDeleteCommand(diagram, (NamedElementContainer)namedElementPart.getParent().getModel(), element))));
    }
    
    public void changeTypeMenu(JPopupMenu menu, NamedElement element) {
        String[] qualifiedTypes = element.getElementType().getQualifiedDataTypes(diagram);
        if(qualifiedTypes.length == 0)
            return;
        
        String category = element.getElementType().getTypeName();
        JMenu changeTypeMenu = new JMenu(String.format(CHANGE_TYPE_MSG, category));
        
        NamedType nameType = (NamedType)element;
        for(String typeName: qualifiedTypes) {
            changeTypeMenu.add(createItem(new CommandAction(typeName, nameType.getType().getName().equals(typeName), new ChangeElementTypeCommand(diagram, nameType, typeName))));
        }
        
        menu.add(changeTypeMenu);
    }

}
