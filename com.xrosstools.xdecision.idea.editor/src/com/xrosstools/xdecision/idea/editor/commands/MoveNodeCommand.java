package com.xrosstools.xdecision.idea.editor.commands;

import com.xrosstools.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;

import java.awt.*;

public class MoveNodeCommand extends Command {
    private DecisionTreeNode node;

    private Rectangle oldConstraint;

    private Rectangle newConstraint;

    public void setConstraint(Rectangle c) {
    	newConstraint = c;
    }

    public void setNode(DecisionTreeNode node) {
        this.node = node;
    }

    public void execute() {
    	oldConstraint = new Rectangle();
    	oldConstraint.setLocation(node.getLocation());
    	oldConstraint.setSize(node.getSize());
        node.setLocation(newConstraint.getLocation());
        node.setSize(newConstraint.getSize());
    }

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        node.setLocation(newConstraint.getLocation());
        node.setSize(newConstraint.getSize());
    }

    public void undo() {
        node.setLocation(oldConstraint.getLocation());
        node.setSize(oldConstraint.getSize());
    }
}
