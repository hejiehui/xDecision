package com.xrosstools.xdecision.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeActionConstants;
import com.xrosstools.xdecision.editor.actions.DecisionTreeChoseValueAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateDecisionAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateFactorAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateValueAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.AddFactorOpratorValueCommand;
import com.xrosstools.xdecision.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.editor.commands.ChangeFactorCommand;
import com.xrosstools.xdecision.editor.commands.ChangeFactorFieldCommand;
import com.xrosstools.xdecision.editor.commands.ChangeFactorTypeCommand;
import com.xrosstools.xdecision.editor.commands.ChangeFieldNameCommand;
import com.xrosstools.xdecision.editor.commands.ChangeFieldTypeCommand;
import com.xrosstools.xdecision.editor.commands.ChangeFunctionCommand;
import com.xrosstools.xdecision.editor.commands.CreateFactorCommand;
import com.xrosstools.xdecision.editor.commands.CreateUserDefineidTypeCommand;
import com.xrosstools.xdecision.editor.commands.CreateUserDefineidTypeFieldCommand;
import com.xrosstools.xdecision.editor.commands.DeleteUserDefineidTypeFieldCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.FactorType;
import com.xrosstools.xdecision.editor.model.FieldDefinition;
import com.xrosstools.xdecision.editor.model.MethodDefinition;
import com.xrosstools.xdecision.editor.model.UserDefinedType;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodeConnectionPart;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodePart;

public class DecisionTreeContextMenuProvider extends ContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
	private ActionRegistry actionRegistry;
	private DecisionTreeDiagramEditor editor;
    public DecisionTreeContextMenuProvider(EditPartViewer viewer, ActionRegistry registry, DecisionTreeDiagramEditor editor) {
        super(viewer);
        actionRegistry = registry;
        this.editor = editor;
    }
    public void buildContextMenu(IMenuManager menu) {
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        EditPartViewer viewer = this.getViewer();
        List selected = viewer.getSelectedEditParts();
        
        if(selected.size() == 1) {
            if(selected.get(0) instanceof DecisionTreeNodeConnectionPart) {
                getConnectionMenu(menu, (DecisionTreeNodeConnectionPart)selected.get(0));
                return;
            }else if(selected.get(0) instanceof DecisionTreeNodePart) {
                getNodeMeun(menu, (DecisionTreeNodePart)selected.get(0));
                return;
            }
        }
        
        // Add standard action groups to the menu
    	 GEFActionConstants.addStandardActionGroups(menu);
    	menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.GEN_JUNIT_TEST_CODE));
    	menu.add(new Separator());
    	menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.CREATE_NEW_DECISION));
    	
    	menu.add(createNewFactorMeun(diagram));
    	menu.add(changeFactorTypeMeun(diagram));
    	
    	//menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.CREATE_NEW_FACTOR));
    	
    	menu.add(new Separator());
    	MenuManager sub = new MenuManager("New value for factor");
    	getFactorActions(sub);
    	menu.add(sub);
    	menu.add(new Separator());
    	getUserDefinedMeun(menu);
    }
    
    private void getFactorActions(IMenuManager menu){
		DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
		for(DecisionTreeFactor factor: diagram.getFactors())
			menu.add(new DecisionTreeCreateValueAction(editor, factor));
    }

    private void getConnectionMenu(IMenuManager menu, DecisionTreeNodeConnectionPart connPart){
        //1. Create new factor value
        //2. display all valid values and mark selected
        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection)connPart.getModel();
        int factorId = conn.getParent().getFactorId();
        if(factorId == -1)
            return;
        
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        DecisionTreeFactor factor = diagram.getFactors().get(factorId);
        
        //Reuse existing factor evaluation expression
        int i = 0;
        for(String factorValue: factor.getFactorValues())
            menu.add(new DecisionTreeChoseValueAction(editor, conn, factorValue, i++));

        menu.add(new Separator());
        DecisionTreeCreateValueAction act = new DecisionTreeCreateValueAction(editor, factor, conn);
        act.setText(DecisionTreeMessages.CREATE_NEW_FACTOR_VALUE_MSG);
        menu.add(act);
        
        //Create new factor evaluation expression from template
        menu.add(new Separator());
        createExpressionMeun(menu, diagram, conn);
    }
    
    private void createExpressionMeun(IMenuManager menu, DecisionTreeDiagram diagram, DecisionTreeNodeConnection conn) {
        DecisionTreeFactor factor = diagram.getFactors().get(conn.getParent().getFactorId());
        
        for(String operator: SINGLE_OPERAND_OPERATOR)
            menu.add(new CommandAction(editor, operator, false, new AddFactorOpratorValueCommand(factor, operator, conn)));
        
        menu.add(new Separator());
        for(String operator: COMPARE_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));
        
        menu.add(new Separator());
        for(String operator: STRING_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));
        
        menu.add(new Separator());
        for(String operator: BETWEEN_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));

        menu.add(new Separator());
        for(String operator: IN_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));
    }
    
    private void reuseExpressionMeun(IMenuManager menu, DecisionTreeNodeConnection conn) {
    }


    private void getNodeMeun(IMenuManager menu, DecisionTreeNodePart nodePart) {
        DecisionTreeNode node = (DecisionTreeNode)nodePart.getModel();
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        
        int i = 0;
        for(DecisionTreeFactor factor: diagram.getFactors()) {
            if(factor.getType() != FactorType.USER_DEFINED)
                menu.add(new CommandAction(editor, factor.getFactorName(), node.getFactorId() == i, new ChangeFactorCommand(node, i++)));
            else
                menu.add(selectFactorFieldMeun(diagram, node, factor));
        }

        menu.add(new Separator());
        menu.add(new DecisionTreeCreateFactorAction(editor, node));
        menu.add(new Separator());
        
        //Select function
        selectFunctionMeun(menu, diagram, node);
        
        menu.add(new Separator());
        i = 0;
        for(String decision: diagram.getDecisions()) {
            menu.add(new CommandAction(editor, decision, node.getDecisionId() == i, new ChangeDecisionCommand(node, i++)));
        }
        
        menu.add(new Separator());
        menu.add(new DecisionTreeCreateDecisionAction(editor, node));
    }
    
    private MenuManager createNewFactorMeun(DecisionTreeDiagram diagram) {
        MenuManager subMenu = new MenuManager(CREATE_NEW_FACTOR_MSG);
        
        for(FactorType type: FactorType.getDisplayTypes()) {
            subMenu.add(new InputTextCommandAction(editor, type.toString(), CREATE_NEW_FACTOR_MSG, "", new CreateFactorCommand(diagram, type)));
        }
        
        for(UserDefinedType udfedType: diagram.getUserDefinedTypes()) {
            subMenu.add(new InputTextCommandAction(editor, udfedType.getName(), CREATE_NEW_FACTOR_MSG, "", new CreateFactorCommand(diagram, FactorType.USER_DEFINED, udfedType.getName())));
        }
        
        return subMenu;
    }
    
    private MenuManager changeFactorTypeMeun(DecisionTreeDiagram diagram) {
        MenuManager subMenu = new MenuManager(CHANGE_FACTOR_TYPE_MSG);
        
        for(DecisionTreeFactor factor: diagram.getFactors()) {
            subMenu.add(changeFactorTypeMeun(diagram, factor));
        }
        
        return subMenu;
    }
    
    private MenuManager changeFactorTypeMeun(DecisionTreeDiagram diagram, DecisionTreeFactor factor) {
        MenuManager subMenu = new MenuManager(factor.getFactorName());
        
        for(FactorType type: FactorType.getDisplayTypes()) {
            subMenu.add(new CommandAction(editor, type.toString(), factor.getType() == type, new ChangeFactorTypeCommand(factor, type)));
        }
        
        for(UserDefinedType udfedType: diagram.getUserDefinedTypes()) {
            subMenu.add(new CommandAction(editor, udfedType.getName(), factor.getCustomizedType() == udfedType.getName(), new ChangeFactorTypeCommand(factor, FactorType.USER_DEFINED, udfedType.getName())));
        }
        
        return subMenu;
    }
    
    private MenuManager selectFactorFieldMeun(DecisionTreeDiagram diagram, DecisionTreeNode node, DecisionTreeFactor factor) {
        MenuManager subMenu = new MenuManager(factor.getFactorName());
        // select self
        int index = diagram.getFactors().indexOf(factor);
        subMenu.add(new CommandAction(editor, factor.getFactorName(), node.getFactorId() == index, new ChangeFactorCommand(node, index)));
        subMenu.add(new Separator());
        
        // select field
        UserDefinedType type = diagram.findUserDefinedType(factor.getCustomizedType());
        for(FieldDefinition field: type.getFields()) {
            subMenu.add(new CommandAction(editor, field.getName(), field.getName().equals(node.getFactorField()), new ChangeFactorFieldCommand(node, field.getName())));
        }
        return subMenu;
    }
    
    private void selectFunctionMeun(IMenuManager menu, DecisionTreeDiagram diagram, DecisionTreeNode node) {
        for(FactorType type: FactorType.getDisplayTypes()) {
            if(type.getStaticMethods().length == 0)
                continue;
            
            MenuManager subMenu = new MenuManager(type.toString());
            for(MethodDefinition mtd: type.getStaticMethods()) {
                subMenu.add(new CommandAction(editor, mtd.getName(), mtd.getName().equals(node.getFunctionName()), new ChangeFunctionCommand(node, mtd.getName())));
            }
            menu.add(subMenu);
        }
    }
    
    private void getUserDefinedMeun(IMenuManager menu) {
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        
        // create type
        menu.add(new InputTextCommandAction(editor, CREATE_NEW_USER_DEFINED_TYPE_MSG, FACTOR_TYPE_NAME_MSG, "", new CreateUserDefineidTypeCommand(diagram)));
        
        for(UserDefinedType udfType: diagram.getUserDefinedTypes()) {
            MenuManager typeSub = new MenuManager(udfType.getName());
            // create field
            MenuManager createFieldMenu = new MenuManager(CREATE_NEW_FIELD_DEFINITION_MSG);
            for(FactorType type: FactorType.getDisplayTypes()) {
                createFieldMenu.add(new InputTextCommandAction(editor, type.toString(), "Field Name", "", new CreateUserDefineidTypeFieldCommand(udfType, type, null)));
            }
            
            for(UserDefinedType udfedType: diagram.getUserDefinedTypes()) {
                createFieldMenu.add(new InputTextCommandAction(editor, udfedType.getName(), "Field Name", "", new CreateUserDefineidTypeFieldCommand(udfType, FactorType.USER_DEFINED, udfedType.getName())));
            }
            typeSub.add(createFieldMenu);
            
            //Remove field
            MenuManager removeFieldMenu = new MenuManager(DELETE_FIELD_MSG);
            for(FieldDefinition field: udfType.getFields()) {
                removeFieldMenu.add(new CommandAction(editor, field.getName(), false, new DeleteUserDefineidTypeFieldCommand(udfType, field)));
            }
            typeSub.add(removeFieldMenu);
            
            typeSub.add(new Separator());
            for(FieldDefinition field: udfType.getFields()) {
                MenuManager fieldMenu = new MenuManager(field.getName());
                //Change Field Name
                fieldMenu.add(new InputTextCommandAction(editor, CHANGE_FIELD_NAME_MSG, "New Name", field.getName(), new ChangeFieldNameCommand(field)));
                
                // change field type
                for(FactorType type: FactorType.getDisplayTypes()) {
                    fieldMenu.add(new CommandAction(editor, type.toString(), field.getType() == type, new ChangeFieldTypeCommand(field, type)));
                }
                
                for(UserDefinedType udfedType: diagram.getUserDefinedTypes()) {
                    fieldMenu.add(new CommandAction(editor, udfedType.getName(), field.getCustomizedType() == udfedType.getName(), new ChangeFieldTypeCommand(field, FactorType.USER_DEFINED, udfedType.getName())));
                }
                
                typeSub.add(fieldMenu);
            }
            menu.add(typeSub);
        }
    }
}
