package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.BaseDialogAction;
import com.xrosstools.gef.CommandChain;
import com.xrosstools.xdecision.idea.editor.commands.AddDecisionCommand;
import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.commands.ChangeDecisionCommand;
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
	    if(node == null)
		    return new AddDecisionCommand(diagram, value);
	    else {
            CommandChain cc = new CommandChain();
            cc.add(new AddDecisionCommand(diagram, value));
            cc.add(new ChangeDecisionCommand(node, diagram.getDecisions().size()));
            return cc;
        }
	}
}
