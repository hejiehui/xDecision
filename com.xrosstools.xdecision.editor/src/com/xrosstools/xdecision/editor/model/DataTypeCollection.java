package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DataTypeCollection extends DataType {
    public static final String VALUE = "value";
    
    public static final DataType DEFAULT_VALUE_TYPE = DataType.STRING_TYPE;
    
    private DataType valueType = DEFAULT_VALUE_TYPE;
    private String propertyType;
    
    private MethodDefinition size = new MethodDefinition("size", DataType.NUMBER_TYPE);
    private MethodDefinition isEmpty = new MethodDefinition("isEmpty", DataType.BOOLEAN_TYPE);
    private MethodDefinition contains = new MethodDefinition("contains", DataType.BOOLEAN_TYPE, asList(new FieldDefinition(VALUE, valueType)));
    private MethodDefinition containsAll = new MethodDefinition("containsAll", DataType.BOOLEAN_TYPE, asList(new FieldDefinition(VALUE, this)));
    
    public DataTypeCollection() {
        super(DataTypeEnum.COLLECTION);
    }

    public DataTypeCollection(DataTypeEnum type) {
        super(type);
        propertyType = String.format(PROP_VALUE_TYPE_TPL, getType().getName());
        
        add(size);
        add(isEmpty);
        add(contains);
        add(containsAll);
    }

    @Override
    public String toString() {
        return getType().getName() + "<" + valueType.toString() + ">";
    }

    public DataType getValueType() {
        return valueType;
    }

    public void setValueType(DataType valueType) {
        this.valueType = valueType;
        
        contains.findParameterByName(VALUE).setType(valueType);
        
        firePropertyChange(propertyType, null,  valueType);
    }
    
    @Override
    public boolean isConcernedProperty(Object propName) {
        return propName == propertyType;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        //TODO add user defined types
        return new IPropertyDescriptor[] {new ComboBoxPropertyDescriptor(propertyType, propertyType, DataTypeEnum.getValueTypeNames())};
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyType.equals(propName))
            return valueType.getType().ordinal();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (propertyType.equals(propName))
            setValueType(DataTypeEnum.values()[(Integer)value].createDataType());
    }
}
