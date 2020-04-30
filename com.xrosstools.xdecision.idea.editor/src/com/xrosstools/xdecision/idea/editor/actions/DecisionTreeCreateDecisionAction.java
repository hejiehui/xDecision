package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.BaseDialogAction;
import com.xrosstools.xdecision.idea.editor.commands.AddDecisionCommand;
import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class DecisionTreeCreateDecisionAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeDiagram diagram;
	public DecisionTreeCreateDecisionAction(Project project, DecisionTreeDiagram diagram){
		super(project, CREATE_NEW_DECISION_MSG, "Decision", "new decision");
		this.diagram = diagram;
	}

	@Override
	protected Command createCommand(String value) {
		return new AddDecisionCommand(diagram, value);
	}
}
