package com.xross.tools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xdecision.editor.model.DecisionTreeDiagram;
import com.xross.tools.xdecision.editor.model.DecisionTreeNode;
import com.xross.tools.xdecision.editor.model.DecisionTreeNodeConnection;

public class DeleteNodeCommand extends Command{
    private DecisionTreeDiagram diagram;
    private DecisionTreeNode node;
    
    public DeleteNodeCommand(
    		DecisionTreeDiagram diagram, 
    		DecisionTreeNode node){
    	this.diagram = diagram;
    	this.node = node;
    }
    
    public void execute() {
        diagram.removeNode(node);
        for(DecisionTreeNodeConnection path: node.getOutputs()){
        	path.getChild().setInput(null);
        }
        
        if(node.getInput() == null)
        	return;
    	node.getInput().getParent().removeOutput(node.getInput());
    }

    public String getLabel() {
        return "Delete Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        diagram.addNode(node);
        for(DecisionTreeNodeConnection path: node.getOutputs()){
        	path.getChild().setInput(path);
        }
        
        if(node.getInput() == null)
        	return;
    	node.getInput().getParent().addOutput(node.getInput());
    }
}
