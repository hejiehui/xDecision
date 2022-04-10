package com.xrosstools.xdecision.editor;

import org.eclipse.jface.action.IMenuManager;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.commands.expression.ChangeConditionOperatorCommand;
import com.xrosstools.xdecision.editor.model.ConditionOperator;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodeConnectionPart;

public class ConnectionContextMenuProvider {
    private DecisionTreeDiagramEditor editor;
    
    public ConnectionContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, DecisionTreeNodeConnectionPart connPart){
        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection)connPart.getModel();
        for(ConditionOperator oprValue: ConditionOperator.values())
            menu.add(new CommandAction(editor, oprValue.getText(), conn.getOperator() == oprValue, new ChangeConditionOperatorCommand(conn, oprValue)));
    }
}
