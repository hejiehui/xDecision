package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DataTypeMap extends DataTypeTemplate {
    private static final String[] KEY_TYPE_NAMES = new String[] {DataTypeEnum.STRING.getName(), DataTypeEnum.NUMBER.getName()};
    
    public static final String VALUE = "value";
    
    private DataType keyType = DataType.STRING_TYPE;
    private DataType valueType = DataType.STRING_TYPE;
    
    private String propertyKeyType;
    private String propertyValueType;
    
    private MethodDefinition size;
    private MethodDefinition isEmpty;
    private MethodDefinition containsKey;
    private MethodDefinition containsValue;
    private MethodDefinition containsAll;
    private MethodDefinition get;
    
    public DataTypeMap(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.MAP);
        propertyKeyType = String.format(PROP_KEY_TYPE_TPL, getMetaType().getName());
        propertyValueType = String.format(PROP_VALUE_TYPE_TPL, getMetaType().getName());
        
        add(size = new MethodDefinition(diagram, "size", DataType.NUMBER_TYPE));
        add(isEmpty = new MethodDefinition(diagram, "isEmpty", DataType.BOOLEAN_TYPE));
        add(containsKey = new MethodDefinition(diagram, "containsKey", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, keyType))));
        add(containsValue = new MethodDefinition(diagram, "containsValue", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, valueType))));
        add(containsAll = new MethodDefinition(diagram, "containsAll", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, this))));
        add(get = new MethodDefinition(diagram, "get", valueType, asList(new ParameterDefinition(diagram, VALUE, keyType))));
    }

    @Override
    public String toString() {
        return getMetaType().getName() + "<" + keyType.toString() + ", " + valueType.toString() + ">";
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
        return new IPropertyDescriptor[] {
                new ComboBoxPropertyDescriptor(propertyKeyType, propertyKeyType, KEY_TYPE_NAMES),
                new ComboBoxPropertyDescriptor(propertyValueType, propertyValueType, getValueTypeNames()),
                };
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyKeyType.equals(propName))
            return keyType.getMetaType().ordinal();

        if (valueType.equals(propName))
            return valueType.getMetaType().ordinal();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (propertyKeyType.equals(propName))
            setKeyType(findDataType(KEY_TYPE_NAMES[(Integer)value]));

        if (propertyValueType.equals(propName))
            setValueType(findDataType(getValueTypeNames()[(Integer)value]));
    }
}
