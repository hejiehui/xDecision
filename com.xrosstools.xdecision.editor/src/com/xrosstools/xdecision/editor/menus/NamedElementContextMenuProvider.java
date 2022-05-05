package com.xrosstools.xdecision.editor.menus;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.DecisionTreeDiagramEditor;
import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.definition.ChangeElementNameCommand;
import com.xrosstools.xdecision.editor.commands.definition.ChangeElementTypeCommand;
import com.xrosstools.xdecision.editor.model.definition.NamedElement;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.definition.NamedElementTypeEnum;
import com.xrosstools.xdecision.editor.model.definition.NamedType;
import com.xrosstools.xdecision.editor.treeparts.definition.NamedElementTreePart;

public class NamedElementContextMenuProvider implements DecisionTreeMessages {
    protected DecisionTreeDiagramEditor editor;
    
    public NamedElementContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, NamedElementTreePart namedElementPart) {
        NamedElement element = (NamedElement)namedElementPart.getModel();
        
        NamedElementContainer container = (NamedElementContainer<?>)namedElementPart.getParent().getModel();
        NamedElementTypeEnum type = container.getElementType();
        String typeName = type.getTypeName();
        
        menu.add(new InputTextCommandAction(editor, String.format(CHANGE_NAME_MSG, typeName), "New Name", element.getName(), new ChangeElementNameCommand(editor.getModel(), container, element)));
        
        changeTypeMenu(menu, element);
        
        menu.add(new Separator());
        menu.add(new CommandAction(editor, String.format(REMOVE_MSG, element.getName()), false, type.createDeleteCommand(editor.getModel(), (NamedElementContainer<NamedElement>)namedElementPart.getParent().getModel(), element)));
    }
    
    public void changeTypeMenu(IMenuManager menu, NamedElement element) {
        String[] qualifiedTypes = element.getElementType().getQualifiedDataTypes(editor.getModel());
        if(qualifiedTypes.length == 0)
            return;
        
        String category = element.getElementType().getTypeName();
        MenuManager changeTypeMenu = new MenuManager(String.format(CHANGE_TYPE_MSG, category));
        
        NamedType nameType = (NamedType)element;
        for(String typeName: qualifiedTypes) {
            changeTypeMenu.add(new CommandAction(editor, typeName, nameType.getType().getName().equals(typeName), new ChangeElementTypeCommand(editor.getModel(), nameType, typeName)));
        }
        
        menu.add(changeTypeMenu);
    }

}
