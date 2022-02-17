package com.xrosstools.xdecision.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.CreateElementCommand;
import com.xrosstools.xdecision.editor.commands.CreateNamedTypeCommand;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedType;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.NamedElementContainerTreePart;

public class NamedElementContainerContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    private DecisionTreeDiagramEditor editor;

    public NamedElementContainerContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, NamedElementContainerTreePart containerPart) {
        buildContextMenu(menu, (NamedElementContainer<NamedElement>)containerPart.getModel());
    }
    
    public void buildContextMenu(IMenuManager menu, NamedElementContainer container) {
        String createMsg = String.format(CREATE_NEW_TEMPLATE_MSG, container.getElementType().getTypeName());
        
        String[] qualifiedTypes = container.getElementType().getQualifiedDataTypes();
        if(qualifiedTypes.length == 0)
            menu.add(createElementMenu(createMsg, container));
        else
            menu.add(createNamedTypeMenu(container, qualifiedTypes));
    }

    public InputTextCommandAction createElementMenu(String createMsg, NamedElementContainer<NamedElement> container) {
        String typeName = container.getElementType().getTypeName();
        
        return new InputTextCommandAction(editor, createMsg, typeName, "", new CreateElementCommand(container)); 
    }

    public MenuManager createNamedTypeMenu(NamedElementContainer<NamedElement> container, String[] typeNames) {
        String category = container.getElementType().getTypeName();
        MenuManager createElementMenu = new MenuManager(String.format(CREATE_NEW_TEMPLATE_MSG, category));
        
        // create element
        for(String typeName: typeNames) {
            createElementMenu.add(new InputTextCommandAction(editor, typeName, String.format("Set %s name", category), "", new CreateNamedTypeCommand(container, typeName)));
        }

        return createElementMenu;
    }
}
