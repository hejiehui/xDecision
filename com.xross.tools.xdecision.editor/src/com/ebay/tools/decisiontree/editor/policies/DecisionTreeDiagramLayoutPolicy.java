package com.ebay.tools.decisiontree.editor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.ebay.tools.decisiontree.editor.commands.CreateNodeCommand;
import com.ebay.tools.decisiontree.editor.commands.LayoutTreeCommand;
import com.ebay.tools.decisiontree.editor.commands.MoveNodeCommand;
import com.ebay.tools.decisiontree.editor.commands.ResizeNodeCommand;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNode;
import com.ebay.tools.decisiontree.editor.requests.DecisionTreeLayoutRequest;
import com.ebay.tools.decisiontree.editor.requests.DecisionTreeResizeRequest;

public class DecisionTreeDiagramLayoutPolicy extends XYLayoutEditPolicy {

    protected Command createAddCommand(EditPart child, Object constraint) {
        return null;
    }

    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
        if (!(child instanceof AbstractGraphicalEditPart))
            return null;
        
        MoveNodeCommand cmd = new MoveNodeCommand();
        cmd.setNode((DecisionTreeNode) child.getModel());
        cmd.setConstraint((Rectangle)constraint);
        return cmd;
    }

    public Command getCommand(Request request) {
    	if(request.getType() == RequestConstants.REQ_ALIGN){
    		DecisionTreeLayoutRequest layoutReq = (DecisionTreeLayoutRequest)request;
    		return new LayoutTreeCommand(layoutReq.getDiagram(), layoutReq.isHorizantal(), layoutReq.getAlignment());
    	}
    	
    	if(request.getType() == RequestConstants.REQ_RESIZE){
    		DecisionTreeResizeRequest resizeReq = (DecisionTreeResizeRequest)request;
    		return new ResizeNodeCommand(resizeReq.getDiagram(), resizeReq.isNodeSize(), resizeReq.isHorizantal(), resizeReq.isIncrease());
    	}
    	
    	return super.getCommand(request);
    }
    
    protected Command getCreateCommand(CreateRequest request) {
        return new CreateNodeCommand(
        		(DecisionTreeDiagram)getHost().getModel(),
        		(DecisionTreeNode)request.getNewObject(),
        		((Rectangle) getConstraintFor(request)).getLocation());
    }
}