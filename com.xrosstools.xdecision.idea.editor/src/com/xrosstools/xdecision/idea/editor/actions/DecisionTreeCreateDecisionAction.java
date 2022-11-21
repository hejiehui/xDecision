package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.BaseDialogAction;
import com.xrosstools.idea.gef.commands.CommandChain;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.idea.editor.commands.definition.CreateElementCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

import java.beans.PropertyChangeListener;

public class DecisionTreeCreateDecisionAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeDiagram diagram;
    private DecisionTreeNode node;
	public DecisionTreeCreateDecisionAction(Project project, DecisionTreeDiagram diagram, PropertyChangeListener listener){
		super(project, CREATE_NEW_DECISION_MSG, "Decision", "new decision");
		this.diagram = diagram;
		setListener(listener);
	}

    public DecisionTreeCreateDecisionAction(Project project, DecisionTreeDiagram diagram, DecisionTreeNode node){
        super(project, CREATE_NEW_DECISION_MSG, "Decision", "new decision");
        this.diagram = diagram;
	    this.node = node;
    }

	@Override
	protected Command createCommand(String value) {
        CreateElementCommand createCmd = new CreateElementCommand(diagram, diagram.getDecisions());
        createCmd.setInputText(value);

        if(node == null)
            return createCmd;

        CommandChain cc = new CommandChain();
        cc.add(createCmd);
        cc.add(new ChangeDecisionCommand(node));
        return cc;
	}
}
