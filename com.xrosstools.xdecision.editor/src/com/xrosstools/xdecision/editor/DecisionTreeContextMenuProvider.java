package com.xrosstools.xdecision.editor;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.DecisionTreeActionConstants;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateValueAction;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;

public class DecisionTreeContextMenuProvider extends ContextMenuProvider {
	private ActionRegistry actionRegistry;
	private DecisionTreeDiagramEditor editor;
    public DecisionTreeContextMenuProvider(EditPartViewer viewer, ActionRegistry registry, DecisionTreeDiagramEditor editor) {
        super(viewer);
        actionRegistry = registry;
        this.editor = editor;
    }
    public void buildContextMenu(IMenuManager menu) {
        // Add standard action groups to the menu
    	 GEFActionConstants.addStandardActionGroups(menu);
    	menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.GEN_JUNIT_TEST_CODE));
    	menu.add(new Separator());
    	menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.CREATE_NEW_DECISION));
    	menu.add(actionRegistry.getAction(DecisionTreeActionConstants.ID_PREFIX + DecisionTreeActionConstants.CREATE_NEW_FACTOR));
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
}
