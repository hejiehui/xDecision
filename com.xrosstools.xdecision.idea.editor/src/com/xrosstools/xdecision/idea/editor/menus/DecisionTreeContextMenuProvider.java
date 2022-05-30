package com.xrosstools.xdecision.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.gef.ContextMenuProvider;
import com.xrosstools.gef.actions.Action;
import com.xrosstools.gef.parts.EditPart;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeDiagramPart;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeNodeConnectionPart;
import com.xrosstools.xdecision.idea.editor.parts.DecisionTreeNodePart;
import com.xrosstools.xdecision.idea.editor.parts.expression.BaseExpressionPart;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class DecisionTreeContextMenuProvider extends ContextMenuProvider implements DecisionTreeMessages {
    private NodeContextMenuProvider nodeMenuProvider;
	private ConnectionContextMenuProvider connMenuProvider;
	private ExpressionContextMenuProvider expMenuProvider;
    private DiagramContextMenuProvider diagramCMenuProvider;


    public DecisionTreeContextMenuProvider(Project project, DecisionTreeDiagram diagram, PropertyChangeListener listener) {
        super((listener));

        nodeMenuProvider = new NodeContextMenuProvider(project, diagram, listener);
        expMenuProvider = new ExpressionContextMenuProvider(project, diagram, listener);
        connMenuProvider = new ConnectionContextMenuProvider(project, diagram, listener);
        diagramCMenuProvider = new DiagramContextMenuProvider(project, diagram, listener);
    }

    public JPopupMenu buildContextMenu(EditPart part) {
        // Add standard action groups to the menu
        JPopupMenu menu = new JPopupMenu();
        if(part instanceof DecisionTreeNodeConnectionPart) {
            connMenuProvider.buildContextMenu(menu, (DecisionTreeNodeConnectionPart)part);
        }else if(part instanceof DecisionTreeNodePart) {
            nodeMenuProvider.buildContextMenu(menu, (DecisionTreeNodePart)part);
        }else if(part instanceof BaseExpressionPart) {
            expMenuProvider.buildContextMenu(menu, (BaseExpressionPart)part);
        }else if(part instanceof DecisionTreeDiagramPart) {
            diagramCMenuProvider.buildContextMenu(menu, (DecisionTreeDiagramPart)part);
        }

        attachListener(menu);
        return menu;
    }
}
