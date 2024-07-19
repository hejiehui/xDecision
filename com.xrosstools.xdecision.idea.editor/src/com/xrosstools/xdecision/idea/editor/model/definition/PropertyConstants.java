package com.xrosstools.xdecision.idea.editor.model.definition;

import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;

public interface PropertyConstants {
	String PROP_EXPRESSION = "Expression";
    String PROP_DATA_TYPE = "Data type";
    String PROP_CONDITION = "Condition";
    
    String PROP_LABEL = "label";
    String PROP_KEY_TYPE = "keyType";
    String PROP_VALUE_TYPE = "valueType";
    
	String PROP_FIELD_NAME = "Field name";
	String PROP_FUNCTION_NAME = "Function name";
	String PROP_DECISION = "Decision";
	String PROP_DESCRIPTION = "Description";
	String PROP_IDENTIFIER = "identifier";
	
	String PROP_NAME_TPL = "%s name";
	String PROP_TYPE_TPL = "%s type";
	String PROP_VALUE_TYPE_TPL = "%s value type";
	String PROP_KEY_TYPE_TPL = "%s key type";
	
	String PROP_VALUE = DecisionTreeMessages.CONSTANT_MSG + " value";
	String PROP_ELEMENTS = "elements";
	String PROP_INPUTS = "inputs";
	String PROP_OUTPUTS = "outputs";
	
	String PROP_SIZE = "size";
	String PROP_LOCATION = "location";
	String PROP_LAYOUT = "layout";
	
	String FIELD_SEPARATOR = ".";
}

