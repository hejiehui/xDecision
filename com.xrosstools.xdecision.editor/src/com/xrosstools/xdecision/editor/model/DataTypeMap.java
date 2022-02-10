package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

import java.util.Map;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DataTypeMap extends DataType {
    public static final String VALUE = "value";
    
    private DataType keyType = DataType.STRING_TYPE;
    private DataType valueType = DataType.STRING_TYPE;
    
    private String propertyKeyType;
    private String propertyValueType;
    
    
//  DataType mapType = new DataType(String.format("Set<%s>", valueType.getName()));
//  mapType.setKeyType(keyType);
//  mapType.setValueType(valueType);
//  
//  mapType.getMethods().addAll(getCommonMethod(valueType));
//  
//  mapType.add(new MethodDefinition("containsKey", BOOLEAN_TYPE, asList(new FieldDefinition("value", keyType))));
//  mapType.add(new MethodDefinition("containsValue", BOOLEAN_TYPE, asList(new FieldDefinition("value", valueType))));
//  mapType.add(new MethodDefinition("containsAll", BOOLEAN_TYPE, asList(new FieldDefinition("value", mapType))));
//  mapType.add(new MethodDefinition("get", valueType, asList(new FieldDefinition("value", keyType))));
//

    
    
    private MethodDefinition size = new MethodDefinition("size", DataType.NUMBER_TYPE);
    private MethodDefinition isEmpty = new MethodDefinition("isEmpty", DataType.BOOLEAN_TYPE);
    private MethodDefinition containsKey = new MethodDefinition("containsKey", DataType.BOOLEAN_TYPE, asList(new FieldDefinition(VALUE, keyType)));
    private MethodDefinition containsValue = new MethodDefinition("containsValue", DataType.BOOLEAN_TYPE, asList(new FieldDefinition(VALUE, valueType)));
    private MethodDefinition containsAll = new MethodDefinition("containsAll", DataType.BOOLEAN_TYPE, asList(new FieldDefinition(VALUE, this)));
    private MethodDefinition get = new MethodDefinition("get", valueType, asList(new FieldDefinition(VALUE, keyType)));
    
    public DataTypeMap() {
        super(DataTypeEnum.MAP);
        propertyKeyType = String.format(PROP_KEY_TYPE_TPL, getType().getName());
        propertyValueType = String.format(PROP_VALUE_TYPE_TPL, getType().getName());
        
        add(size);
        add(isEmpty);
        add(containsKey);
        add(containsValue);
        add(containsAll);
        add(get);
    }

    @Override
    public String toString() {
        return getType().getName() + "<" + keyType.toString() + ", " + valueType.toString() + ">";
    }

    public DataType getValueType() {
        return valueType;
    }

    public void setKeyType(DataType keyType) {
        this.keyType = keyType;
        
        containsKey.findParameterByName(VALUE).setType(keyType);
        get.findParameterByName(VALUE).setType(keyType);
        
        firePropertyChange(propertyKeyType, null,  keyType);
    }

    public void setValueType(DataType valueType) {
        this.valueType = valueType;
        
        containsValue.findParameterByName(VALUE).setType(valueType);
        get.setType(valueType);
        
        firePropertyChange(propertyValueType, null,  valueType);
    }
    
    @Override
    public boolean isConcernedProperty(Object propName) {
        return propName == propertyKeyType || propName == propertyValueType;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        //TODO add user defined types
        return new IPropertyDescriptor[] {
                new ComboBoxPropertyDescriptor(propertyKeyType, propertyKeyType, DataTypeEnum.getKeyTypeNames()),
                new ComboBoxPropertyDescriptor(propertyValueType, propertyValueType, DataTypeEnum.getValueTypeNames()),
                };
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyKeyType.equals(propName))
            return keyType.getType().ordinal();

        if (valueType.equals(propName))
            return valueType.getType().ordinal();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (propertyKeyType.equals(propName))
            setKeyType(DataTypeEnum.findByName(DataTypeEnum.getKeyTypeNames()[(Integer)value]).createDataType());

        if (propertyValueType.equals(propName))
            setValueType(DataTypeEnum.values()[(Integer)value].createDataType());
    }
}
