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
import com.xrosstools.xdecision.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.editor.commands.ChangeFactorCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodeConnectionPart;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodePart;

public class DecisionTreeContextMenuProvider extends ContextMenuProvider {
	private ActionRegistry actionRegistry;
	private DecisionTreeDiagramEditor editor;
    public DecisionTreeContextMenuProvider(EditPartViewer viewer, ActionRegistry registry, DecisionTreeDiagramEditor editor) {
        super(viewer);
        actionRegistry = registry;
        this.editor = editor;
    }
    public void buildContextMenu(IMenuManager menu) {
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

    private void getConnectionMenu(IMenuManager menu, DecisionTreeNodeConnectionPart connPart){
        //1. Create new factor value
        //2. display all valid values and mark selected
        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection)connPart.getModel();
        int factorId = conn.getParent().getFactorId();
        if(factorId == -1)
            return;
        
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        DecisionTreeFactor factor = diagram.getFactors().get(factorId);
        
        int i = 0;
        for(String factorValue: factor.getFactorValues())
            menu.add(new DecisionTreeChoseValueAction(editor, conn, factorValue, i++));

        menu.add(new Separator());
        DecisionTreeCreateValueAction act = new DecisionTreeCreateValueAction(editor, factor, conn);
        act.setText(DecisionTreeMessages.CREATE_NEW_FACTOR_VALUE_MSG);
        menu.add(act);
    }

    private void getNodeMeun(IMenuManager menu, DecisionTreeNodePart nodePart) {
        DecisionTreeNode node = (DecisionTreeNode)nodePart.getModel();
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        
        int i = 0;
        for(DecisionTreeFactor factor: diagram.getFactors())
            menu.add(new CommandAction(editor, factor.getFactorName(), node.getFactorId() == i, new ChangeFactorCommand(node, i++)));

        menu.add(new Separator());
        menu.add(new DecisionTreeCreateFactorAction(editor, node));
        menu.add(new Separator());
        
        i = 0;
        for(String decision: diagram.getDecisions()) {
            menu.add(new CommandAction(editor, decision, node.getDecisionId() == i, new ChangeDecisionCommand(node, i++)));
        }
        
        menu.add(new Separator());
        menu.add(new DecisionTreeCreateDecisionAction(editor, node));
    }
}
