package com.xrosstools.xdecision.editor.commands.definition;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xdecision.editor.model.DecisionTreeDecision;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;

public class DeleteDecisionCommand extends Command{
    private DecisionTreeDiagram diagram;
    private NamedElementContainer<DecisionTreeDecision> container;
    private DecisionTreeDecision decision;
    
    private List<DecisionTreeNode> nodes;
    
    public DeleteDecisionCommand(DecisionTreeDiagram diagram, DecisionTreeDecision decision){
        this.diagram = diagram;
        this.decision = decision;
    }
    
    public void execute() {
        container = diagram.getDecisions();
        nodes = new ArrayList<DecisionTreeNode>();
        for(DecisionTreeNode node: diagram.getNodes())
            if(node.getDecision() == decision)
                nodes.add(node);

        redo();
    }

    public String getLabel() {
        return "Delete element";
    }

    public void redo() {
        for(DecisionTreeNode node: nodes)
            node.setDecision(null);
        container.remove(decision);
    }

    public void undo() {
        container.add(decision);
        for(DecisionTreeNode node: nodes)
            node.setDecision(decision);
    }
}