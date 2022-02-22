package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.editor.commands.CreateElementCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeCreateDecisionAction extends WorkbenchPartAction
        implements DecisionTreeActionConstants, DecisionTreeMessages {
    private DecisionTreeNode node;

    public DecisionTreeCreateDecisionAction(IWorkbenchPart part) {
        super(part);
        setId(ID_PREFIX + CREATE_NEW_DECISION);
        setText(CREATE_NEW_DECISION_MSG);
    }

    public DecisionTreeCreateDecisionAction(IWorkbenchPart part, DecisionTreeNode node) {
        this(part);
        this.node = node;
    }

    protected boolean calculateEnabled() {
        return true;
    }

    public void run() {
        InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Create new decision: ", "Decision",
                "new decision", null);
        if (dlg.open() != Window.OK)
            return;
        
        String value = dlg.getValue();
        CommandChain cc = new CommandChain();
        CreateElementCommand createCmd = new CreateElementCommand(null, node.getDecisionTreeManager().getDecisions());
        createCmd.setInputText(value);
        cc.add(createCmd);
        cc.add(new ChangeDecisionCommand(node));
        execute(cc);
    }
}
