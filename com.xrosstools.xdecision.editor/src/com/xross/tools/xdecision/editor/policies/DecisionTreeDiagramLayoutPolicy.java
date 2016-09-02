package com.xross.tools.xdecision.editor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.xross.tools.xdecision.editor.commands.CreateNodeCommand;
import com.xross.tools.xdecision.editor.commands.LayoutTreeCommand;
import com.xross.tools.xdecision.editor.commands.MoveNodeCommand;
import com.xross.tools.xdecision.editor.commands.ResizeNodeCommand;
import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.model.DecisionTreeNode;
import com.xross.tools.xdecision.editor.requests.DecisionTreeLayoutRequest;
import com.xross.tools.xdecision.editor.requests.DecisionTreeResizeRequest;

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