package com.xrosstools.xdecision.editor;

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
import com.xrosstools.xdecision.editor.commands.CreateFieldCommand;
import com.xrosstools.xdecision.editor.commands.CreateMethodCommand;
import com.xrosstools.xdecision.editor.commands.CreateUserDefineidTypeCommand;
import com.xrosstools.xdecision.editor.commands.DeleteFieldCommand;
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
        
        for(DataType udfType: diagram.getUserDefinedTypes()) {
            MenuManager typeSub = new MenuManager(udfType.getName());
            
            menu.add(createFieldMenu(typeSub, diagram, udfType));
            typeSub.add(new Separator());
            menu.add(createMethodMenu(typeSub, diagram, udfType));
        }
    }

    private MenuManager createFieldMenu(MenuManager typeSub, DecisionTreeDiagram diagram, DataType udfType) {
        // create field
        MenuManager createFieldMenu = new MenuManager(CREATE_NEW_FIELD_DEFINITION_MSG);
        for(String type: DataType.getPredefinedTypeNames()) {
            createFieldMenu.add(new InputTextCommandAction(editor, type.toString(), "Field Name", "", new CreateFieldCommand(udfType, type)));
        }
        
        for(DataType udfedType: diagram.getUserDefinedTypes()) {
            createFieldMenu.add(new InputTextCommandAction(editor, udfedType.getName(), "Field Name", "", new CreateFieldCommand(udfType, udfedType.getName())));
        }
        typeSub.add(createFieldMenu);
        
        //Remove field
        MenuManager removeFieldMenu = new MenuManager(DELETE_FIELD_MSG);
        for(FieldDefinition field: udfType.getFields()) {
            removeFieldMenu.add(new CommandAction(editor, field.getName(), false, new DeleteFieldCommand(udfType, field)));
        }
        typeSub.add(removeFieldMenu);
        
        typeSub.add(new Separator());
        for(FieldDefinition field: udfType.getFields()) {
            MenuManager fieldMenu = new MenuManager(field.getName());
            //Change Field Name
            fieldMenu.add(new InputTextCommandAction(editor, CHANGE_FIELD_NAME_MSG, "New Name", field.getName(), new ChangeFieldNameCommand(field)));
            
            // change field type
            for(String type: DataType.getPredefinedTypeNames()) {
                fieldMenu.add(new CommandAction(editor, type.toString(), field.getTypeName().equals(type), new ChangeTypeCommand(field, type)));
            }
            
            for(DataType udfedType: diagram.getUserDefinedTypes()) {
                fieldMenu.add(new CommandAction(editor, udfedType.getName(), field.getTypeName().equals(udfType.getName()), new ChangeTypeCommand(field, udfedType.getName())));
            }
            
            typeSub.add(fieldMenu);
        }
        return typeSub;
    }

    private MenuManager createMethodMenu(MenuManager typeSub, DecisionTreeDiagram diagram, DataType udfType) {
        // create method
        MenuManager createFieldMenu = new MenuManager(CREATE_NEW_METHOD_DEFINITION_MSG);
        for(String type: DataType.getPredefinedTypeNames()) {
            createFieldMenu.add(new InputTextCommandAction(editor, type.toString(), "Method Name", "", new CreateMethodCommand(udfType, type)));
        }
        
        for(DataType udfedType: diagram.getUserDefinedTypes()) {
            createFieldMenu.add(new InputTextCommandAction(editor, udfedType.getName(), "Method Name", "", new CreateMethodCommand(udfType, udfedType.getName())));
        }
        typeSub.add(createFieldMenu);
        
        //Remove method
        MenuManager removeFieldMenu = new MenuManager(DELETE_METHOD_MSG);
        for(MethodDefinition method: udfType.getMethods()) {
            removeFieldMenu.add(new CommandAction(editor, method.getName(), false, new DeleteMethodCommand(udfType, method)));
        }
        typeSub.add(removeFieldMenu);
        
        typeSub.add(new Separator());
        for(MethodDefinition method: udfType.getMethods()) {
            MenuManager fieldMenu = new MenuManager(method.getName());
            //Change method Name
            fieldMenu.add(new InputTextCommandAction(editor, CHANGE_METHOD_NAME_MSG, "New Name", method.getName(), new ChangeMethodNameCommand(method)));
            
            // change method type
            for(String type: DataType.getPredefinedTypeNames()) {
                fieldMenu.add(new CommandAction(editor, type.toString(), method.getTypeName().equals(type), new ChangeTypeCommand(method, type)));
            }
            
            for(DataType udfedType: diagram.getUserDefinedTypes()) {
                fieldMenu.add(new CommandAction(editor, udfedType.getName(), method.getTypeName().equals(udfedType.getName()), new ChangeMethodTypeCommand(method, udfedType.getName())));
            }
            
            typeSub.add(fieldMenu);
        }
        return typeSub;
    }
}
