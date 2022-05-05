package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.editor.commands.definition.CreateElementCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeCreateDecisionAction extends BaseDialogAction
        implements DecisionTreeActionConstants, DecisionTreeMessages {
    private DecisionTreeNode node;

    public DecisionTreeCreateDecisionAction(IWorkbenchPart part, DecisionTreeNode node) {
        super(part, CREATE_NEW_DECISION_MSG, "Decision", "new decision");
        this.node = node;
    }

    protected boolean calculateEnabled() {
        return true;
    }

    @Override
    protected Command createCommand(String value) {
        CommandChain cc = new CommandChain();
        CreateElementCommand createCmd = new CreateElementCommand(node.getDecisionTreeManager().getDiagram(), node.getDecisionTreeManager().getDecisions());
        createCmd.setInputText(value);
        cc.add(createCmd);
        cc.add(new ChangeDecisionCommand(node));
        return cc;
    }
}
