package com.xrosstools.xdecision.editor.policies;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.xrosstools.xdecision.editor.commands.AddFactorCommand;
import com.xrosstools.xdecision.editor.commands.ChangeNodeFactorCommand;
import com.xrosstools.xdecision.editor.figures.DecisionTreeNodeFigure;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;

public class DecisionTreeNodeDirectEditPolicy extends DirectEditPolicy{
	private List<DecisionTreeFactor> factors;

	public DecisionTreeNodeDirectEditPolicy(List<DecisionTreeFactor> factors){
		this.factors = factors;
	}
	
    protected Command getDirectEditCommand(DirectEditRequest request) {
    	String newFactor = (String) request.getCellEditor().getValue();
    	DecisionTreeNode node = (DecisionTreeNode) getHost().getModel();
    	for(DecisionTreeFactor factor: factors){
    		if(factor.getFactorName().endsWith(newFactor))
    			return new ChangeNodeFactorCommand(node, factors.indexOf(factor));
    	}
    	
    	DecisionTreeFactor factor = new DecisionTreeFactor();
    	factor.setFactorName(newFactor);
    	return new AddFactorCommand(factors, node, factor);
    }
    protected void showCurrentEditValue(DirectEditRequest request) {
        String value = (String) request.getCellEditor().getValue();
//        ((DecisionTreeNodeFigure) getHostFigure()).setFactor(value);
    }
}
