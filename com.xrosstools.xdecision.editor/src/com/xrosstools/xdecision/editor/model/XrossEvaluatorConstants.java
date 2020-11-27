package com.xrosstools.xdecision.editor.model;

import java.util.regex.Pattern;

public interface XrossEvaluatorConstants {
    String EQUAL = "==";
    String NOT_EQUAL = "<>";
    String GREATER_THAN = ">";
    String GREATER_THAN_EQUAL = ">=";
    String LESS_THAN = "<";
    String LESS_THAN_EQUAL = "<=";
    String STARTS_WITH = "STARTS WITH";
    String ENDS_WITH = "ENDS WITH";
    String CONTAINS = "CONTAINS";
    String MATCHES = "MATCHES";
    
    String NOT_STARTS_WITH = "NOT STARTS WITH";
    String NOT_ENDS_WITH = "NOT ENDS WITH";
    String NOT_CONTAINS = "NOT CONTAINS";
    String NOT_MATCHES = "NOT MATCHES";

    String IS_NULL = "IS NULL";
    String IS_NOT_NULL = "IS NOT NULL";
    
    String IS_TRUE = "IS TRUE";
    String IS_FALSE = "IS FALSE";
    
    String BETWEEN = "BETWEEN";
    String NOT_BETWEEN = "NOT BETWEEN";
    
    String IN = "IN";
    String NOT_IN = "NOT IN";
    
    String STRING_DELIMITER = "'";
    Pattern VARIABLE = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
    
    
    String[] SINGLE_OPERAND_OPERATOR = new String[] {
            IS_NULL, IS_NOT_NULL,
            IS_TRUE, IS_FALSE,
    };

    String[] COMPARE_OPERATOR = new String[] {
            EQUAL, NOT_EQUAL, GREATER_THAN_EQUAL, GREATER_THAN, LESS_THAN_EQUAL, LESS_THAN, 
    };

    String[] STRING_OPERATOR = new String[] {
            STARTS_WITH, ENDS_WITH, CONTAINS, MATCHES, 
            NOT_STARTS_WITH, NOT_ENDS_WITH, NOT_CONTAINS, NOT_MATCHES,
    };

    String[] BETWEEN_OPERATOR = new String[] {
            BETWEEN, NOT_BETWEEN
    };

    String[] IN_OPERATOR = new String[] {
            IN, NOT_IN
    };
}
