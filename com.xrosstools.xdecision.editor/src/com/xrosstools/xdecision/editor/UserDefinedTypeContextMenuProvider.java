package com.xrosstools.xdecision.editor;

import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.ChangeElementNameCommand;
import com.xrosstools.xdecision.editor.commands.ChangeElementTypeCommand;
import com.xrosstools.xdecision.editor.commands.CreateElementCommand;
import com.xrosstools.xdecision.editor.commands.CreateUserDefineidTypeCommand;
import com.xrosstools.xdecision.editor.commands.DeleteElementCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.NamedType;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;

public class UserDefinedTypeContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    private DecisionTreeDiagramEditor editor;
    
    public UserDefinedTypeContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu) {
//        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
//        
//        // create type
//        menu.add(new InputTextCommandAction(editor, FACTOR_TYPE_NAME_MSG, CREATE_NEW_USER_DEFINED_TYPE_MSG, "", new CreateUserDefineidTypeCommand(diagram)));
//        
//        List<DataType> types = diagram.getAllTypes();
//        for(DataType udfType: diagram.getUserDefinedTypes()) {
//            MenuManager typeSub = new MenuManager(udfType.getName());
//            
//            createNamedTypeMenu(typeSub, FIELD_MSG, types, udfType.getFields(), new FieldDefinition());
//            
//            typeSub.add(new Separator());
//            
//            createNamedTypeMenu(typeSub, METHOD_MSG, types, udfType.getMethods(), new MethodDefinition());
//            
//            menu.add(typeSub);
//        }
    }

//    public void createNamedTypeMenu(MenuManager typeSub, String category, List<DataType> types, List<?> elements, NamedType newElement) {
//        // create element
//        MenuManager createElementMenu = new MenuManager(String.format(CREATE_MSG, category));
//        createElementMenu(createElementMenu, category, types, elements, newElement, "%s");
//        
//        MenuManager createListMenu = new MenuManager("List");
//        createElementMenu(createListMenu, category, types, elements, newElement, "List<%s>");
//        createElementMenu.add(createListMenu);
//        
//        MenuManager createSetMenu = new MenuManager("Set");
//        createElementMenu(createSetMenu, category, types, elements, newElement, "Set<%s>");
//        createElementMenu.add(createSetMenu);
//        /*
//        MenuManager createIntMapMenu = new MenuManager("Map<Integer>");
//        createElementMenu(createIntMapMenu, category, types, elements, newElement, "Map<Integer, %s>");
//        createElementMenu.add(createIntMapMenu);
//        
//        MenuManager createStringMapMenu = new MenuManager("Map<Integer>");
//        createElementMenu(createStringMapMenu, category, types, elements, newElement, "Map<Integer, %s>");
//        createElementMenu.add(createStringMapMenu);
//        */        
//        typeSub.add(createElementMenu);
//
//        //Remove element
//        MenuManager removeElementMenu = new MenuManager(String.format(REMOVE_MSG, category));
//        for(Object elementObj: elements) {
//            FieldDefinition element = (FieldDefinition)elementObj;
//            removeElementMenu.add(new CommandAction(editor, element.getName(), false, new DeleteElementCommand(elements, element)));
//        }
//        typeSub.add(removeElementMenu);
//        
//        typeSub.add(new Separator());
//        
//        for(Object elementObj: elements) {
//            FieldDefinition element = (FieldDefinition)elementObj;
//            MenuManager fieldMenu = new MenuManager(element.getName());
//            //Change Field Name
//            fieldMenu.add(new InputTextCommandAction(editor, "New Name", String.format(CHANGE_NAME_MSG, category), element.getName(), new ChangeElementNameCommand(element)));
//            
//            // change field type
//            MenuManager fieldTypeMenu = new MenuManager(String.format(CHANGE_TYPE_MSG, category));
//            changeElementTypeMenu(fieldTypeMenu, types, element, "%s");
//            fieldMenu.add(fieldTypeMenu);
//            
//            MenuManager changeListTypeMenu = new MenuManager("List");
//            changeElementTypeMenu(changeListTypeMenu, types, element, "List<%s>");
//            fieldTypeMenu.add(changeListTypeMenu);
//            
//            MenuManager changeSetTypeMenu = new MenuManager("Set");
//            changeElementTypeMenu(changeSetTypeMenu, types, element, "Set<%s>");
//            fieldTypeMenu.add(changeSetTypeMenu);
//            
//            handleMethodParameters(fieldMenu, types, element);
//            
//            typeSub.add(fieldMenu);
//        }
//
//    }
//    
//    private void handleMethodParameters(MenuManager methodMenu, List<DataType> types, FieldDefinition element) {
//        if(!MethodDefinition.class.isInstance(element))
//            return;
//            
//        methodMenu.add(new Separator());
//        createNamedTypeMenu(methodMenu, PARAMETER_MSG, types, ((MethodDefinition)element).getParameters(), new FieldDefinition());
//    }
//
//    private void createElementMenu(MenuManager createElementMenu, String category, List<DataType> types, List<?> elements, NamedType newElement, String typeTemplate) {
//        for(DataType type: types) {
//            String typeName = String.format(typeTemplate, type.getName());
//            createElementMenu.add(new InputTextCommandAction(editor, String.format("Set %s name", category), typeName, "", new CreateElementCommand(elements, newElement, type)));
//        }
//    }
//
//    private void changeElementTypeMenu(MenuManager changeTypeMenu, List<DataType> types, FieldDefinition element, String typeTemplate) {
//        for(DataType type: types) {
//            String typeName = String.format(typeTemplate, type.getName());
//            changeTypeMenu.add(new CommandAction(editor, typeName, element.getTypeName().equals(typeName), new ChangeElementTypeCommand(element, type)));
//        }
//    }
////
////    private void createFieldMenu(MenuManager typeSub, List<DataType> types, DataType udfType) {
////        createNamedTypeMenu(typeSub, FIELD_MSG, types, udfType.getFields(), new FieldDefinition());
////    }
////
////    private void createMethodMenu(MenuManager typeSub, List<DataType> types, DataType udfType) {
////        createNamedTypeMenu(typeSub, METHOD_MSG, types, udfType.getMethods(), new MethodDefinition());
////        
////    }
}
