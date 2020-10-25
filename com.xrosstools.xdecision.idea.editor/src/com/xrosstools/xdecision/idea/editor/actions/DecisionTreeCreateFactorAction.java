package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.BaseDialogAction;
import com.xrosstools.gef.CommandChain;
import com.xrosstools.xdecision.idea.editor.commands.AddFactorCommand2;
import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.commands.ChangeFactorCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

public class DecisionTreeCreateFactorAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeDiagram diagram;
    private DecisionTreeNode node;

    public DecisionTreeCreateFactorAction(Project project, DecisionTreeDiagram diagram){
		super(project, CREATE_NEW_FACTOR_MSG, "Factor", "new factor");
		this.diagram = diagram;
	}

	public DecisionTreeCreateFactorAction(Project project, DecisionTreeDiagram diagram, DecisionTreeNode node){
		this(project, diagram);
		this.node = node;
	}

	@Override
	protected Command createCommand(String value) {
		DecisionTreeFactor factor = new DecisionTreeFactor();
		factor.setFactorName(value);

        if(node == null)
            return new AddFactorCommand2(diagram, factor);
        else {
            CommandChain cc = new CommandChain();
            cc.add(new AddFactorCommand2(diagram, factor));
            cc.add(new ChangeFactorCommand(node, diagram.getFactors().size()));
            return cc;
        }
	}
}
