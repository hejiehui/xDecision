package com.xrosstools.xdecision.editor;

import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.CreateElementCommand;
import com.xrosstools.xdecision.editor.commands.CreateNamedTypeCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.NamedElement;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedType;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.NamedElementContainerTreePart;

public class NamedElementContainerContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    private DecisionTreeDiagramEditor editor;
    private DecisionTreeDiagram diagram;

    public NamedElementContainerContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
        diagram = editor.getModel();
    }
    
    public void buildContextMenu(IMenuManager menu, NamedElementContainerTreePart containerPart) {
        NamedElementContainer<NamedElement> container = (NamedElementContainer<NamedElement>)containerPart.getModel();
        
        String createMsg = String.format(CREATE_NEW_TEMPLATE_MSG, container.getElementType().getTypeName());
        
        List<DataType> qualifiedTypes = container.getElementType().getQualifiedDataTypes(diagram);
        if(qualifiedTypes.size() == 0)
            menu.add(createElementMenu(createMsg, container));
        else
            menu.add(createNamedTypeMenu(container, qualifiedTypes));
    }

    public InputTextCommandAction createElementMenu(String createMsg, NamedElementContainer<NamedElement> container) {
        String typeName = container.getElementType().getTypeName();
        
        return new InputTextCommandAction(editor, createMsg, typeName, "", new CreateElementCommand(container)); 
    }

    public MenuManager createNamedTypeMenu(NamedElementContainer<NamedElement> container, List<DataType> types) {
        String category = container.getElementType().getTypeName();
        MenuManager createElementMenu = new MenuManager(String.format(CREATE_NEW_TEMPLATE_MSG, category));
        
        // create element
        NamedType newElement = (NamedType)container.getElementType().newInstance();
        createElementMenu(createElementMenu, category, types, container, newElement, "%s");
        
        if(container.getElementType().allowCollections() == false)
            return createElementMenu;
        
        MenuManager createListMenu = new MenuManager("List");
        createElementMenu(createListMenu, category, types, container, newElement, "List<%s>");
        createElementMenu.add(createListMenu);
        
        MenuManager createSetMenu = new MenuManager("Set");
        createElementMenu(createSetMenu, category, types, container, newElement, "Set<%s>");
        createElementMenu.add(createSetMenu);
        /*
        MenuManager createIntMapMenu = new MenuManager("Map<Integer>");
        createElementMenu(createIntMapMenu, category, types, elements, newElement, "Map<Integer, %s>");
        createElementMenu.add(createIntMapMenu);
        
        MenuManager createStringMapMenu = new MenuManager("Map<Integer>");
        createElementMenu(createStringMapMenu, category, types, elements, newElement, "Map<Integer, %s>");
        createElementMenu.add(createStringMapMenu);
        */        
        return createElementMenu;
    }
    
    private void createElementMenu(MenuManager createElementMenu, String category, List<DataType> types, NamedElementContainer container, NamedType newElement, String typeTemplate) {
        for(DataType type: types) {
            String typeName = String.format(typeTemplate, type.getName());
            createElementMenu.add(new InputTextCommandAction(editor, typeName, String.format("Set %s name", category), "", new CreateNamedTypeCommand(container, type)));
        }
    }
}
