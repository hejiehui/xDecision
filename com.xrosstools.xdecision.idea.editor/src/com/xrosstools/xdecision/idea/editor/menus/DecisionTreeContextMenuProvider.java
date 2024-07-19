package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeDiagramPart;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeNodeConnectionPart;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeNodePart;
import com.xrosstools.xdecision.idea.editor.parts.expression.BaseExpressionPart;

import javax.swing.*;

public class DecisionTreeContextMenuProvider extends ContextMenuProvider implements DecisionTreeMessages {
    private NodeContextMenuProvider nodeMenuProvider;
	private ConnectionContextMenuProvider connMenuProvider;
	private ExpressionContextMenuProvider expMenuProvider;
    private DiagramContextMenuProvider diagramMenuProvider;


    public DecisionTreeContextMenuProvider(Project project, DecisionTreeDiagram diagram) {

        nodeMenuProvider = new NodeContextMenuProvider(project, diagram);
        expMenuProvider = new ExpressionContextMenuProvider(project, diagram);
        connMenuProvider = new ConnectionContextMenuProvider(project, diagram);
        diagramMenuProvider = new DiagramContextMenuProvider(project, diagram);
    }

    public JPopupMenu buildContextMenu(Object selected) {
        EditPart part = (EditPart)selected;
        // Add standard action groups to the menu
        JPopupMenu menu = new JPopupMenu();
        if(part instanceof DecisionTreeNodeConnectionPart) {
            connMenuProvider.buildContextMenu(menu, (DecisionTreeNodeConnectionPart)part);
        }else if(part instanceof DecisionTreeNodePart) {
            nodeMenuProvider.buildContextMenu(menu, (DecisionTreeNodePart)part);
        }else if(part instanceof BaseExpressionPart) {
            expMenuProvider.buildContextMenu(menu, (BaseExpressionPart)part);
        }else if(part instanceof DecisionTreeDiagramPart) {
            diagramMenuProvider.buildContextMenu(menu, (DecisionTreeDiagramPart)part);
        }

        return menu;
    }
}
