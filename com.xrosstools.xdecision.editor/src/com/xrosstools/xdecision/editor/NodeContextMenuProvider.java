package com.xrosstools.xdecision.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateDecisionAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateFactorAction;
import com.xrosstools.xdecision.editor.commands.ChangeDecisionCommand;
import com.xrosstools.xdecision.editor.commands.expression.ChangeChildCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDecision;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.editor.model.expression.ExpressionDefinition;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodePart;

public class NodeContextMenuProvider {
    private DecisionTreeDiagramEditor editor;
    
    public NodeContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, DecisionTreeNodePart nodePart) {
        DecisionTreeNode node = (DecisionTreeNode)nodePart.getModel();
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        
        int i = 0;
        for(DecisionTreeFactor factor: diagram.getFactorList()) {
            ExpressionDefinition exp = node.getNodeExpression();
            boolean selected = exp != null && exp.toString().equals(factor.getFactorName());
            menu.add(new CommandAction(editor, factor.getFactorName(), selected, new ChangeChildCommand(node, node.getNodeExpression(), new VariableExpression(factor.getFactorName()))));
        }

        menu.add(new Separator());
        menu.add(new DecisionTreeCreateFactorAction(editor, node));
        menu.add(new Separator());
        
//        
//        menu.add(new Separator());
//        for(DecisionTreeDecision decision: diagram.getDecisions().getElements()) {
//            menu.add(new CommandAction(editor, decision, node.getDecision() == decision, new ChangeDecisionCommand(node, i++)));
//        }
//        
//        menu.add(new Separator());
//        menu.add(new DecisionTreeCreateDecisionAction(editor, node));
    }
}
