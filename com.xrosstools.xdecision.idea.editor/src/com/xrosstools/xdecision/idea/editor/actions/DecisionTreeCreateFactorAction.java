package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.BaseDialogAction;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.commands.CommandChain;
import com.xrosstools.xdecision.idea.editor.commands.AddFactorCommand2;
import com.xrosstools.xdecision.idea.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.expression.VariableExpression;

public class DecisionTreeCreateFactorAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeDiagram diagram;
    private DecisionTreeNode node;
	private String typeName;

    public DecisionTreeCreateFactorAction(Project project){
		super(project, CREATE_NEW_FACTOR_MSG, "Factor", "new factor");
	}

	public void setDiagram(DecisionTreeDiagram diagram) {
		this.diagram = diagram;
	}

	public DecisionTreeCreateFactorAction(Project project, DecisionTreeDiagram diagram, DecisionTreeNode node, String typeName){
        super(project, CREATE_NEW_FACTOR_MSG, "Factor", "new factor");
        setDiagram(diagram);
		this.node = node;
		this.typeName = typeName;
		setText(typeName);
	}

	@Override
	public Command createCommand(String value) {
		DecisionTreeFactor factor = new DecisionTreeFactor(diagram, value);
		factor.setType(diagram.findDataType(typeName));

        AddFactorCommand2 addFactorCommand = new AddFactorCommand2(diagram, factor);

        if(node == null)
            return addFactorCommand;

		CommandChain cc = new CommandChain();
		cc.add(new AddFactorCommand2(diagram, factor));
		cc.add(new ChangeChildCommand(node, node.getNodeExpression(), new VariableExpression(factor)));
		return cc;
	}
}
