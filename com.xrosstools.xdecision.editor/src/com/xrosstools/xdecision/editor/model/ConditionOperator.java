package com.xrosstools.xdecision.editor.model;

public enum ConditionOperator {
    EQUAL("=="),
    NOT_EQUAL("<>"),
    GREATER_THAN(">"),
    GREATER_THAN_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<="),
    STARTS_WITH("STARTS WITH"),
    ENDS_WITH("ENDS WITH"),
    CONTAINS("CONTAINS"),
    MATCHES("MATCHES"),
    
    NOT_STARTS_WITH("NOT STARTS WITH"),
    NOT_ENDS_WITH("NOT ENDS WITH"),
    NOT_CONTAINS("NOT CONTAINS"),
    NOT_MATCHES("NOT MATCHES"),

    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),
    
    IS_TRUE("IS TRUE"),
    IS_FALSE("IS FALSE"),
    
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    
    IN("IN"),
    NOT_IN("NOT IN");
    
    private String operator;
    ConditionOperator(String operator) {
        this.operator = operator;
    }
    
    public String getText() {
        return operator;
    }
    
    public static String[] getAllOperatorText() {
        String[] texts = new String[ConditionOperator.values().length];
        int i = 0;
        for(ConditionOperator op: ConditionOperator.values())
            texts[i++] = op.getText();
        
        return texts;
    }

    public static ConditionOperator locate(String operatorStr) {
        if(operatorStr == null)
            return null;

        for(ConditionOperator op: ConditionOperator.values())
            if(op.getText().equals(operatorStr))
                return op;

        return null;
    }

}
