package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.BaseDialogAction;
import com.xrosstools.xdecision.idea.editor.commands.AddFactorValueCommand;
import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;

public class DecisionTreeCreateValueAction extends BaseDialogAction implements DecisionTreeActionConstants, DecisionTreeMessages{
	private DecisionTreeFactor factor;
	public DecisionTreeCreateValueAction(Project project, DecisionTreeFactor factor){
		super(project, CREATE_NEW_FACTOR_VALUE_MSG, "Value", "new factor value");
		this.factor = factor;
	}

	@Override
	protected Command createCommand(String newValue) {
		int length = factor.getFactorValues().length;
		String[] newValues = new String[length + 1];
		System.arraycopy(factor.getFactorValues(), 0, newValues, 0, length);
		newValues[length] = newValue;
		return new AddFactorValueCommand(factor, factor.getFactorValues(), newValues);
	}
}
