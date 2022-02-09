package com.xrosstools.xdecision.editor;

import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.commands.ChangeElementTypeCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.NamedElementTypeEnum;
import com.xrosstools.xdecision.editor.model.NamedType;
import com.xrosstools.xdecision.editor.parts.NamedTypeTreePart;

public class NamedTypeContextMenuProvider extends NamedElementContextMenuProvider {
    
    public NamedTypeContextMenuProvider(DecisionTreeDiagramEditor editor) {
        super(editor);
    }
    
    public void buildContextMenu(IMenuManager menu, NamedTypeTreePart namedTypePart) {
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();

        super.buildContextMenu(menu, namedTypePart);
        menu.add(new Separator());

        NamedType element = (NamedType)namedTypePart.getModel();
        NamedElementTypeEnum type = ((NamedElementContainer<?>)namedTypePart.getParent().getModel()).getElementType();
        String typeName = type.getTypeName();
        List<DataType> types = type.getQualifiedDataTypes(diagram);
        
        MenuManager changeTypeMenu = new MenuManager(String.format(CHANGE_TYPE_MSG, typeName));
        changeElementTypeMenu(changeTypeMenu, types, element, "%s");
        
        MenuManager changeListTypeMenu = new MenuManager("List");
        changeElementTypeMenu(changeListTypeMenu, types, element, "List<%s>");
        changeTypeMenu.add(changeListTypeMenu);
        
        MenuManager changeSetTypeMenu = new MenuManager("Set");
        changeElementTypeMenu(changeSetTypeMenu, types, element, "Set<%s>");
        changeTypeMenu.add(changeSetTypeMenu);
        
        menu.add(changeTypeMenu);
    }

    private void changeElementTypeMenu(MenuManager changeTypeMenu, List<DataType> types, NamedType element, String typeTemplate) {
        for(DataType type: types) {
            String dataTypeName = String.format(typeTemplate, type.getName());
            changeTypeMenu.add(new CommandAction(editor, dataTypeName, element.getTypeName().equals(dataTypeName), new ChangeElementTypeCommand(element, type)));
        }
    }
}
