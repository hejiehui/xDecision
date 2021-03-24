package com.xrosstools.xdecision.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.ChangeFieldNameCommand;
import com.xrosstools.xdecision.editor.commands.ChangeMethodNameCommand;
import com.xrosstools.xdecision.editor.commands.ChangeMethodTypeCommand;
import com.xrosstools.xdecision.editor.commands.ChangeTypeCommand;
import com.xrosstools.xdecision.editor.commands.CreateElementCommand;
import com.xrosstools.xdecision.editor.commands.CreateFieldCommand;
import com.xrosstools.xdecision.editor.commands.CreateMethodCommand;
import com.xrosstools.xdecision.editor.commands.CreateUserDefineidTypeCommand;
import com.xrosstools.xdecision.editor.commands.DeleteElementCommand;
import com.xrosstools.xdecision.editor.commands.DeleteMethodCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;

public class UserDefinedTypeContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    private DecisionTreeDiagramEditor editor;
    
    public UserDefinedTypeContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu) {
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        
        // create type
        menu.add(new InputTextCommandAction(editor, CREATE_NEW_USER_DEFINED_TYPE_MSG, FACTOR_TYPE_NAME_MSG, "", new CreateUserDefineidTypeCommand(diagram)));
        
        List<String> types = diagram.getAllTypeNames();
        for(DataType udfType: diagram.getUserDefinedTypes()) {
            MenuManager typeSub = new MenuManager(udfType.getName());
            
            createFieldMenu(typeSub, types, udfType);
            
            typeSub.add(new Separator());
            
            createMethodMenu(typeSub, types, udfType);
            
            menu.add(typeSub);
        }
    }

    private MenuManager createNamedTypeMenu(MenuManager typeSub, String category, List<String> types, List<FieldDefinition> elements, FieldDefinition newElement) {
        // create element
        MenuManager createElementMenu = new MenuManager(String.format(CREATE_MSG, category));
        createElementMenu(createElementMenu, types, elements, newElement, "%s");
        
        MenuManager createListMenu = new MenuManager("List");
        createElementMenu(createListMenu, types, elements, newElement, "List<%s>");
        createElementMenu.add(createListMenu);
        
        MenuManager createSetMenu = new MenuManager("Set");
        createElementMenu(createSetMenu, types, elements, newElement, "Set<%s>");
        createElementMenu.add(createSetMenu);
        
        typeSub.add(createElementMenu);

        //Remove element
        MenuManager removeElementMenu = new MenuManager(String.format(REMOVE_MSG, category));
        for(FieldDefinition element: elements) {
            removeElementMenu.add(new CommandAction(editor, element.getName(), false, new DeleteElementCommand(elements, element)));
        }
        typeSub.add(removeElementMenu);
        
        typeSub.add(new Separator());
        
        for(FieldDefinition element: elements) {
            MenuManager fieldMenu = new MenuManager(element.getName());
            //Change Field Name
            fieldMenu.add(new InputTextCommandAction(editor, String.format(CHANGE_NAME_MSG, category), "New Name", element.getName(), new ChangeFieldNameCommand(element)));
            
            // change field type
            changeElementTypeMenu(fieldMenu, types, element, "%s");
            
            MenuManager changeListTypeMenu = new MenuManager("List");
            changeElementTypeMenu(changeListTypeMenu, types, element, "List<%s>");
            fieldMenu.add(changeListTypeMenu);
            
            MenuManager changeSetTypeMenu = new MenuManager("Set");
            changeElementTypeMenu(changeSetTypeMenu, types, element, "Set<%s>");
            fieldMenu.add(changeSetTypeMenu);
            
            typeSub.add(fieldMenu);
        }
        return typeSub;

    }

    private void createElementMenu(MenuManager createElementMenu, List<String> types, List<FieldDefinition> elements, FieldDefinition newElement, String typeTemplate) {
        for(String type: types) {
            String typeName = String.format(typeTemplate, type);
            createElementMenu.add(new InputTextCommandAction(editor, typeName, "Set name", "", new CreateElementCommand(elements, newElement, typeName)));
        }
    }

    private void changeElementTypeMenu(MenuManager changeTypeMenu, List<String> types, FieldDefinition element, String typeTemplate) {
        for(String type: types) {
            String typeName = String.format(typeTemplate, type);
            changeTypeMenu.add(new CommandAction(editor, typeName, element.getTypeName().equals(typeName), new ChangeTypeCommand(element, typeName)));
        }
    }

    private void createFieldMenu(MenuManager typeSub, List<String> types, DataType udfType) {
        createNamedTypeMenu(typeSub, FIELD_MSG, types, udfType.getFields(), new FieldDefinition());
    }

    private void createMethodMenu(MenuManager typeSub, List<String> types, DataType udfType) {
        List<FieldDefinition> elements = new ArrayList<FieldDefinition>();
        for(FieldDefinition f: udfType.getMethods()){
            elements.add(f);
        }
        createNamedTypeMenu(typeSub, METHOD_MSG, types, elements, new MethodDefinition());
    }
}
