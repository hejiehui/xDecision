package com.xrosstools.xdecision.editor.model;

import java.util.Arrays;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public abstract class TemplateType extends DataType {
    private DecisionTreeDiagram diagram;
    private DataType valueType = DataType.STRING_TYPE;
    private String propertyValueType;

    public TemplateType(DecisionTreeDiagram diagram, DataTypeEnum metaType) {
        super(metaType);
        this.diagram = diagram;
        propertyValueType = String.format(PROP_VALUE_TYPE_TPL, getMetaType().getName());
    }

    public DecisionTreeDiagram getDiagram() {
        return diagram;
    }
    
    public DataType getValueType() {
        return valueType;
    }
    
    public void setValueType(DataType valueType) {
        this.valueType = valueType;
        firePropertyChange(propertyValueType, null,  valueType);
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
    
    @Override
    public boolean isConcernedProperty(Object propName) {
        return propName == propertyValueType;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {new ComboBoxPropertyDescriptor(propertyValueType, propertyValueType, getValueTypeNames())};
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyValueType.equals(propName))
            return findIndex(getValueTypeNames(), valueType.getName());

        return null;
    }
    
    protected int findIndex(String[] values, String value) {
        int i = 0;
        for(String v: values) {
            if(value.equals(v))
                return i;
            i++;
        }
        return -1;
//        return Arrays.binarySearch(values, value);
    }

    public void setPropertyValue(Object propName, Object value){
        if (propertyValueType.equals(propName))
            setValueType(findDataType(getValueTypeNames()[(Integer)value]));
    }
}
