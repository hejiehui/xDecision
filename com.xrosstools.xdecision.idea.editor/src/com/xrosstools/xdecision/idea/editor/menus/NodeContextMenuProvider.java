package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import static com.xrosstools.idea.gef.ContextMenuProvider.*;

import com.xrosstools.idea.gef.actions.CommandAction;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeCreateDecisionAction;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeCreateFactorAction;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.idea.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDecision;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.idea.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.idea.editor.model.expression.VariableExpression;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeNodePart;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class NodeContextMenuProvider implements DecisionTreeMessages {
    private Project project;
    private DecisionTreeDiagram diagram;

    public NodeContextMenuProvider(Project project, DecisionTreeDiagram diagram, PropertyChangeListener listener) {
        this.project = project;
        this.diagram = diagram;
    }

    public void buildContextMenu(JPopupMenu menu, DecisionTreeNodePart nodePart) {
        DecisionTreeNode node = (DecisionTreeNode)nodePart.getModel();

        createAndSetDecisionMenu(menu, node, diagram);
        menu.addSeparator();
        
        menu.addSeparator();
        createAndSetFactorMenu(menu, node, diagram);
    }

    private void createAndSetDecisionMenu(JPopupMenu menu, DecisionTreeNode node, DecisionTreeDiagram diagram) {
        for(DecisionTreeDecision decision: diagram.getDecisions().getElements()) {
            menu.add(createItem(new CommandAction(decision.getName(), node.getDecision() == decision, new ChangeDecisionCommand(node, decision))));
        }
        
        menu.addSeparator();
        menu.add(createItem(new DecisionTreeCreateDecisionAction(project, diagram, node)));
    }
    
    public void createAndSetFactorMenu(JPopupMenu menu, DecisionTreeNode node, DecisionTreeDiagram diagram) {
        for(DecisionTreeFactor factor: diagram.getFactorList()) {
            ExpressionDefinition exp = node.getNodeExpression();
            boolean selected = exp != null && exp.toString().equals(factor.getFactorName());
            menu.add(createItem(new CommandAction(factor.getFactorName(), selected, new ChangeChildCommand(node, node.getNodeExpression(), new VariableExpression(factor.getFactorName())))));
        }

        addSeparator(menu);

        NamedElementContainer<DecisionTreeFactor> container = diagram.getFactors();
        String[] typeNames = container.getElementType().getQualifiedDataTypes(diagram);

        String category = container.getElementType().getTypeName();
        JMenu createElementMenu = new JMenu(String.format(CREATE_NEW_TEMPLATE_MSG, category));
        
        // create element
        for(String typeName: typeNames) {
            createElementMenu.add(createItem(new DecisionTreeCreateFactorAction(project, diagram, node, typeName)));
        }

        menu.add(createElementMenu);
    }

}
