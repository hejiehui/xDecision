package com.ebay.tools.decisiontree.editor.policies;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.ebay.tools.decisiontree.editor.commands.AddFactorCommand;
import com.ebay.tools.decisiontree.editor.commands.ChangeFactorCommand;
import com.ebay.tools.decisiontree.editor.figures.DecisionTreeNodeFigure;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNode;
import com.ebay.tools.decisiontree.utils.DecisionTreeFactor;

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
    			return new ChangeFactorCommand(node, factors.indexOf(factor));
    	}
    	
    	DecisionTreeFactor factor = new DecisionTreeFactor();
    	factor.setFactorName(newFactor);
    	return new AddFactorCommand(factors, node, factor);
    }
    protected void showCurrentEditValue(DirectEditRequest request) {
        String value = (String) request.getCellEditor().getValue();
        ((DecisionTreeNodeFigure) getHostFigure()).setFactor(value);
    }
}
