package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.BaseDialogAction;
import com.xrosstools.xdecision.idea.editor.commands.AddFactorCommand2;
import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;

public class DecisionTreeCreateFactorAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeDiagram diagram;
	public DecisionTreeCreateFactorAction(Project project, DecisionTreeDiagram diagram){
		super(project, CREATE_NEW_FACTOR_MSG, "Factor", "new factor");
		this.diagram = diagram;
	}

	@Override
	protected Command createCommand(String value) {
		DecisionTreeFactor factor = new DecisionTreeFactor();
		factor.setFactorName(value);
		return new AddFactorCommand2(diagram, factor);
	}
}
