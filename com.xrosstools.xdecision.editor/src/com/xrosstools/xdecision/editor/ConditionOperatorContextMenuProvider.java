package com.xrosstools.xdecision.editor;

import org.eclipse.jface.action.IMenuManager;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.commands.expression.ChangeConditionOperatorCommand;
import com.xrosstools.xdecision.editor.model.ConditionOperator;
import com.xrosstools.xdecision.editor.model.OperatorReference;
import com.xrosstools.xdecision.editor.parts.OperatorReferencePart;

public class ConditionOperatorContextMenuProvider {
    private DecisionTreeDiagramEditor editor;
    
    public ConditionOperatorContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, OperatorReferencePart oprPart){
        OperatorReference oprRef = (OperatorReference)oprPart.getModel();
        
        //Reuse existing factor evaluation expression
        for(ConditionOperator oprValue: ConditionOperator.values())
            menu.add(new CommandAction(editor, oprValue.getText(), oprRef.getOperator() == oprValue, new ChangeConditionOperatorCommand(oprRef, oprValue)));
    }
}
