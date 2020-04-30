package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

import java.awt.*;

public class CreateNodeCommand extends Command {
    private DecisionTreeDiagram diagram;
    private DecisionTreeNode node;
    private Point location;
    
    public CreateNodeCommand(
    		DecisionTreeDiagram diagram, 
    		DecisionTreeNode node, 
    		Point location){
    	this.diagram = diagram;
    	this.node = node;
    	this.location = location;
    }
    
    public void execute() {
        node.setLocation(location);
        node.setSize(new Dimension(diagram.getNodeWidth(), diagram.getNodeHeight()));
        diagram.addNode(node);
    }

    public String getLabel() {
        return "Create Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        diagram.removeNode(node);
    }
}
