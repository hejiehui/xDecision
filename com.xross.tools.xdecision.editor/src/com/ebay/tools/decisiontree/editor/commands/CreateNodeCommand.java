package com.ebay.tools.decisiontree.editor.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.ebay.tools.decisiontree.editor.model.DecisionTreeDiagram;
import com.ebay.tools.decisiontree.editor.model.DecisionTreeNode;

public class CreateNodeCommand extends Command{
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
