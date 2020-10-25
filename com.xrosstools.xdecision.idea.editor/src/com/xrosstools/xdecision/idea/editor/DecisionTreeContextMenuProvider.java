package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.gef.CommandAction;
import com.xrosstools.gef.ContextMenuProvider;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.xdecision.idea.editor.actions.*;
import com.xrosstools.xdecision.idea.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.idea.editor.commands.ChangeFactorCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeNodeConnectionPart;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeNodePart;

import javax.swing.*;

public class DecisionTreeContextMenuProvider implements DecisionTreeMessages, ContextMenuProvider {
	private Project project;
    private DecisionTreeDiagram diagram;
    private VirtualFile virtualFile;

    DecisionTreeContextMenuProvider(Project project, VirtualFile virtualFile, DecisionTreeDiagram diagram) {
        this.project = project;
        this.diagram = diagram;
        this.virtualFile = virtualFile;
    }

    @Override
	public JPopupMenu buildContextMenu(EditPart select) {
        // Add standard action groups to the menu
		JPopupMenu menu = new JPopupMenu();

        if(select instanceof DecisionTreeNodeConnectionPart) {
            getConnectionMenu(menu, (DecisionTreeNodeConnectionPart)select);
            return menu;
        }else if(select instanceof DecisionTreeNodePart) {
            getNodeMenu(menu, (DecisionTreeNodePart)select);
            return menu;
        }

        menu.add(createItem(new DecisionTreeCodeGenAction(virtualFile, diagram)));
        menu.addSeparator();
        menu.add(createItem(new DecisionTreeCreateDecisionAction(project, diagram)));
        menu.add(createItem(new DecisionTreeCreateFactorAction(project, diagram)));
        menu.addSeparator();
        JMenu sub = new JMenu("New value for factor");
    	getFactorActions(sub);
    	menu.add(sub);

    	return menu;
    }
    
    private void getFactorActions(JMenu menu){
		for(DecisionTreeFactor factor: diagram.getFactors())
			menu.add(createItem(new DecisionTreeCreateValueAction(project, factor)));
    }

    private void getConnectionMenu(JPopupMenu menu, DecisionTreeNodeConnectionPart connPart){
        //1. Create new factor value
        //2. display all valid values and mark selected
        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection)connPart.getModel();
        int factorId = conn.getParent().getFactorId();
        if(factorId == -1)
            return;

        DecisionTreeFactor factor = diagram.getFactors().get(factorId);

        int i = 0;
        for(String factorValue: factor.getFactorValues())
            menu.add(createItem(new DecisionTreeChoseValueAction(conn, factorValue, i++)));

        menu.addSeparator();
        DecisionTreeCreateValueAction act = new DecisionTreeCreateValueAction(project, factor, conn);
        act.setText(DecisionTreeMessages.CREATE_NEW_FACTOR_VALUE_MSG);
        menu.add(createItem(act));
    }

    private void getNodeMenu(JPopupMenu menu, DecisionTreeNodePart nodePart) {
        DecisionTreeNode node = (DecisionTreeNode)nodePart.getModel();

        int i = 0;
        for(DecisionTreeFactor factor: diagram.getFactors())
            menu.add(createItem(new CommandAction(factor.getFactorName(), node.getFactorId() == i, new ChangeFactorCommand(node, i++))));

        menu.addSeparator();
        menu.add(createItem(new DecisionTreeCreateFactorAction(project, diagram, node)));
        menu.addSeparator();

        i = 0;
        for(String decision: diagram.getDecisions()) {
            menu.add(createItem(new CommandAction(decision, node.getDecisionId() == i, new ChangeDecisionCommand(node, i++))));
        }

        menu.addSeparator();
        menu.add(createItem(new DecisionTreeCreateDecisionAction(project, diagram, node)));
    }
}
