package com.xrosstools.xdecision.editor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xdecision.editor.actions.CommandAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeChoseValueAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeCreateValueAction;
import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;
import com.xrosstools.xdecision.editor.actions.InputTextCommandAction;
import com.xrosstools.xdecision.editor.commands.AddFactorOpratorValueCommand;
import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.editor.model.XrossEvaluatorConstants;
import com.xrosstools.xdecision.editor.parts.DecisionTreeNodeConnectionPart;

public class ConnectionContextMenuProvider implements XrossEvaluatorConstants {
    private DecisionTreeDiagramEditor editor;
    
    public ConnectionContextMenuProvider(DecisionTreeDiagramEditor editor) {
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu, DecisionTreeNodeConnectionPart connPart){
        //1. Create new factor value
        //2. display all valid values and mark selected
        DecisionTreeNodeConnection conn = (DecisionTreeNodeConnection)connPart.getModel();
        int factorId = -1;//conn.getParent().getFactorId();
        if(factorId == -1)
            return;
        
        DecisionTreeDiagram diagram = (DecisionTreeDiagram)editor.getRootEditPart().getContents().getModel();
        DecisionTreeFactor factor = diagram.getFactors().get(factorId);
        
        //Reuse existing factor evaluation expression
        int i = 0;
        for(String factorValue: factor.getFactorValues())
            menu.add(new DecisionTreeChoseValueAction(editor, conn, factorValue, i++));

        menu.add(new Separator());
        DecisionTreeCreateValueAction act = new DecisionTreeCreateValueAction(editor, factor, conn);
        act.setText(DecisionTreeMessages.CREATE_NEW_FACTOR_VALUE_MSG);
        menu.add(act);
        
        //Create new factor evaluation expression from template
        menu.add(new Separator());
        createExpressionMeun(menu, diagram, conn);
    }
    
    private void createExpressionMeun(IMenuManager menu, DecisionTreeDiagram diagram, DecisionTreeNodeConnection conn) {
        DecisionTreeFactor factor = diagram.getFactors().get(0);//conn.getParent().getFactorId()
        
        for(String operator: SINGLE_OPERAND_OPERATOR)
            menu.add(new CommandAction(editor, operator, false, new AddFactorOpratorValueCommand(factor, operator, conn)));
        
        menu.add(new Separator());
        for(String operator: COMPARE_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));
        
        menu.add(new Separator());
        for(String operator: STRING_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));
        
        menu.add(new Separator());
        for(String operator: BETWEEN_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));

        menu.add(new Separator());
        for(String operator: IN_OPERATOR)
            menu.add(new InputTextCommandAction(editor, operator, operator, "", new AddFactorOpratorValueCommand(factor, operator, conn)));
    }
    

}
