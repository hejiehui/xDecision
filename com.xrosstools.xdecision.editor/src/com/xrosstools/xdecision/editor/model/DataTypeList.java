package com.xrosstools.xdecision.editor.model;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DataTypeList extends DataType {
    private static final String VALUE = "value";
    private static final String CONTAINS = "contains";
    private static final String CONTAINS_ALL = "containsAll";
    private static final String INDEX_OF = "indexOf";
    private static final String LAST_INDEX_OF = "lastIndexOf";
    private static final String GET = "get";
    private static final String LENGTH = "length";
    private static final String SIZE = "size";
    private static final String IS_EMPTY = "isEmpty";


    private DataType valueType;
    private String propertyType;
    
    public DataTypeList() {
        super(DataTypeEnum.LIST);
        propertyType = String.format(PROP_TYPE_TPL, getType().getName());
        add(new FieldDefinition(LENGTH, DataType.NUMBER_TYPE));
    }

    public DataType getValueType() {
        return valueType;
    }

    public void setValueType(DataType valueType) {
        this.valueType = valueType;
        firePropertyChange(propertyType, null,  valueType);
    }
    
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        //TODO add user defined types
        return new IPropertyDescriptor[] {new ComboBoxPropertyDescriptor(PROP_VALUE_TYPE, PROP_VALUE_TYPE, DataTypeEnum.getValueTypeNames())};
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyType.equals(propName))
            return valueType.getType().ordinal();

        return super.getPropertyValue(propName);
    }

    public void setPropertyValue(Object propName, Object value){
        if (propertyType.equals(propName))
            setValueType(DataTypeEnum.values()[(Integer)value].createDataType());
    }
}
