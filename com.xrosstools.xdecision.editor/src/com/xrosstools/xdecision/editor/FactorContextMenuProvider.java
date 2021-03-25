package com.xrosstools.xdecision.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateValueAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.ChangeTypeCommand;
import com.xrosstools.xdecision.editor.commands.CreateFactorCommand;
import com.xrosstools.xdecision.editor.model.DataType;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;

public class FactorContextMenuProvider implements XrossEvaluatorConstants, DecisionTreeMessages {
    private DecisionTreeDiagramEditor editor;
    
    public FactorContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }

    public void buildContextMenu(IMenuManager menu){
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        menu.add(createNewFactorMeun(diagram));
        menu.add(changeFactorTypeMeun(diagram));
        
        //menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.CREATE_NEW_FACTOR));
        
        menu.add(new Separator());
        MenuManager sub = new MenuManager("New value for factor");
        getFactorActions(sub);
        menu.add(sub);

    }
    private void getFactorActions(IMenuManager menu){
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        for(DecisionTreeFactor factor: diagram.getFactors())
            menu.add(new DecisionTreeCreateValueAction(editor, factor));
    }

    private MenuManager createNewFactorMeun(DecisionTreeDiagram diagram) {
        MenuManager subMenu = new MenuManager(CREATE_NEW_FACTOR_MSG);
        
        for(String type: DataType.getPredefinedTypeNames()) {
            subMenu.add(new InputTextCommandAction(editor, CREATE_NEW_FACTOR_MSG, type, "", new CreateFactorCommand(diagram, type)));
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
        
        for(String type: DataType.getPredefinedTypeNames()) {
            subMenu.add(new CommandAction(editor, type, factor.getTypeName().equals(type), new ChangeTypeCommand(factor, type)));
        }
        
        for(DataType type: diagram.getUserDefinedTypes()) {
            subMenu.add(new CommandAction(editor, type.getName(), factor.getTypeName().equals(type.getName()), new ChangeTypeCommand(factor, type.getName())));
        }
        
        return subMenu;
    }
    
}
