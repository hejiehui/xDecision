package com.xrosstools.xdecision.editor.model;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DataTypeArray extends DataTypeTemplate {
    private static final String LENGTH = "length";

    private DataType valueType = DataType.STRING_TYPE;
    private String propertyType;
    private FieldDefinition length;
    
    public DataTypeArray(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.ARRAY);
        propertyType = String.format(PROP_VALUE_TYPE_TPL, getType().getName());
        length = new FieldDefinition(diagram, LENGTH, DataType.NUMBER_TYPE);
        add(length);
    }
    
    @Override
    public String toString() {
        return valueType.toString() + "[]";
    }

    public DataType getValueType() {
        return valueType;
    }

    public void setValueType(DataType valueType) {
        this.valueType = valueType;
        length.setType(valueType);
        firePropertyChange(propertyType, null,  valueType);
    }
    
    @Override
    public boolean isConcernedProperty(Object propName) {
        return propName == propertyType;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {new ComboBoxPropertyDescriptor(propertyType, propertyType, getValueTypeNames())};
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyType.equals(propName))
            return valueType.getType().ordinal();

        return null;
    }
    

    public void setPropertyValue(Object propName, Object value){
        if (propertyType.equals(propName))
            setValueType(findDataType(getValueTypeNames()[(Integer)value]));
    }
}
