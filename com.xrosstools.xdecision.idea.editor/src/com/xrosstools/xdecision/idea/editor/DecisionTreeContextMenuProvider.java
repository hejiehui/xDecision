package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.gef.ContextMenuProvider;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.xdecision.idea.editor.actions.*;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;

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

	public JPopupMenu buildContextMenu(EditPart select) {
        // Add standard action groups to the menu
		JPopupMenu menu = new JPopupMenu();
        //GEFActionConstants.addStandardActionGroups(menu);

        menu.add(createItem(GEN_JUNIT_TEST_CODE_MSG, new DecisionTreeCodeGenAction(virtualFile, diagram)));
        menu.addSeparator();
        menu.add(createItem(CREATE_NEW_DECISION_MSG, new DecisionTreeCreateDecisionAction(project, diagram)));
        menu.add(createItem(CREATE_NEW_FACTOR_MSG, new DecisionTreeCreateFactorAction(project, diagram)));
        menu.addSeparator();
        JMenu sub = new JMenu("New value for factor");
    	getFactorActions(sub);
    	menu.add(sub);

    	return menu;
    }
    
    private void getFactorActions(JMenu menu){
		for(DecisionTreeFactor factor: diagram.getFactors())
			menu.add(createItem(factor.getFactorName(), new DecisionTreeCreateValueAction(project, factor)));
    }
}
