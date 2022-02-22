package com.xrosstools.xdecision.editor.model;

public abstract class DataTypeTemplate extends DataType {
    private DecisionTreeDiagram diagram;

    public DataTypeTemplate(DecisionTreeDiagram diagram, DataTypeEnum metaType) {
        super(metaType);
        this.diagram = diagram;
    }
    public DecisionTreeDiagram getDiagram() {
        return diagram;
    }
    
    public String[] getValueTypeNames() {
        return combine(PREDEFINED_VALUE_TYPE_NAMES, diagram.getUserDefinedTypes().getElementNames());
    }

    public static String[] combine(String[] str1, String[] str2) {
        String[] allNames = new String[str1.length + str2.length];
        
        System.arraycopy(str1, 0, allNames, 0, str1.length);
        System.arraycopy(str2, 0, allNames, str1.length, str2.length);

        return allNames;
    }
    
    public DataType findDataType(String name) {
        return diagram.findDataType(name);
    }
}
