package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.Action;
import com.xrosstools.gef.BaseDialogAction;
import com.xrosstools.gef.CommandChain;
import com.xrosstools.xdecision.idea.editor.commands.AddFactorValueCommand;
import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.commands.SetNewFactorValueCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;

public class DecisionTreeCreateValueAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeFactor factor;
	private DecisionTreeNodeConnection nodeConn;

	public DecisionTreeCreateValueAction(Project project, DecisionTreeFactor factor){
		super(project, CREATE_NEW_FACTOR_VALUE_MSG, "Value", "new factor value");
		setText(factor.getFactorName());
		this.factor = factor;
	}

	public DecisionTreeCreateValueAction(Project project, DecisionTreeFactor factor, DecisionTreeNodeConnection nodeConn){
		this(project, factor);
		this.nodeConn = nodeConn;
	}

	@Override
	protected Command createCommand(String value) {
        if(nodeConn == null)
            return createValueCommand(value);
        else
            return createAndSetValueCommand(value);
	}

    public Command createValueCommand(String newValue) {
        int length = factor.getFactorValues().length;
        String[] newValues = new String[length + 1];
        System.arraycopy(factor.getFactorValues(), 0, newValues, 0, length);
        newValues[length] = newValue;
        return new AddFactorValueCommand(factor, factor.getFactorValues(), newValues);
    }

    public Command createAndSetValueCommand(String newValue) {
        Command createValue = createValueCommand(newValue);
        if(createValue == null)
            return null;

        CommandChain cc = new CommandChain();
        cc.add(createValue);
        cc.add(new SetNewFactorValueCommand(nodeConn, factor.getFactorValueNum()));
        return cc;
    }

}
