package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.actions.BaseDialogAction;
import com.xrosstools.gef.commands.CommandChain;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.idea.editor.commands.definition.CreateElementCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

public class DecisionTreeCreateDecisionAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeDiagram diagram;
    private DecisionTreeNode node;
	public DecisionTreeCreateDecisionAction(Project project, DecisionTreeDiagram diagram){
		super(project, CREATE_NEW_DECISION_MSG, "Decision", "new decision");
		this.diagram = diagram;
	}

    public DecisionTreeCreateDecisionAction(Project project, DecisionTreeDiagram diagram, DecisionTreeNode node){
	    this(project, diagram);
	    this.node = node;
    }

	@Override
	protected Command createCommand(String value) {
        CreateElementCommand createCmd = new CreateElementCommand(diagram, node.getDecisionTreeManager().getDecisions());

        if(node == null)
            return createCmd;

        CommandChain cc = new CommandChain();
        createCmd.setInputText(value);
        cc.add(createCmd);
        cc.add(new ChangeDecisionCommand(node));
        return cc;
	}
}
