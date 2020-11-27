package com.xrosstools.xdecision.editor.commands;

import com.xrosstools.xdecision.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.editor.model.DecisionTreeNodeConnection;

public class AddFactorOpratorValueCommand extends InputTextCommand {
    private DecisionTreeFactor factor;
    private DecisionTreeNodeConnection conn;
    private int oldValueId;
    private String[] oldValues;
    private String[] newValues;
    private String operator;
    
    public AddFactorOpratorValueCommand(DecisionTreeFactor factor, String operator){
        this.factor = factor;
        this.oldValues = factor.getFactorValues();
        this.operator = operator;
    }
    
    public AddFactorOpratorValueCommand(DecisionTreeFactor factor, String operator, DecisionTreeNodeConnection conn){
        this(factor, operator);
        this.conn = conn;
        oldValueId = conn.getValueId();
    }
    
    public void execute() {
        String newValue = getInputText() == null ? operator : operator + " " + getInputText();
        
        int length = oldValues.length;
        newValues = new String[length + 1];
        System.arraycopy(factor.getFactorValues(), 0, newValues, 0, length);
        newValues[length] = newValue;

        redo();
    }

    public String getLabel() {
        return "Create factor value with operator";
    }

    public void redo() {
        if(newValues == null)
            return;
        
        factor.setFactorValues(newValues);
        
        if(conn == null)
            return;
        
        conn.setValueId(newValues.length - 1);
    }

    public void undo() {
        factor.setFactorValues(oldValues);
        
        if(conn == null)
            return;

        conn.setValueId(oldValueId);
    }
}