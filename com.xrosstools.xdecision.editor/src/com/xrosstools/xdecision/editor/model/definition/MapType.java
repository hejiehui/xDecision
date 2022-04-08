package com.xrosstools.xdecision.editor.model.definition;

import static java.util.Arrays.asList;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xrosstools.xdecision.editor.model.DecisionTreeDiagram;

public class MapType extends TemplateType {
    private static final String[] KEY_TYPE_NAMES = new String[] {DataTypeEnum.STRING.getName(), DataTypeEnum.NUMBER.getName()};
    
    public static final String VALUE = "value";
    
    private DataType keyType = DataType.STRING_TYPE;
    
    private String propertyKeyType;
    
    private MethodDefinition size;
    private MethodDefinition isEmpty;
    private MethodDefinition containsKey;
    private MethodDefinition containsValue;
    private MethodDefinition containsAll;
    private MethodDefinition get;
    
    public MapType(DecisionTreeDiagram diagram) {
        super(diagram, DataTypeEnum.MAP);
        propertyKeyType = String.format(PROP_KEY_TYPE_TPL, getMetaType().getName());
        
        add(size = new MethodDefinition(diagram, "size", DataType.NUMBER_TYPE));
        add(isEmpty = new MethodDefinition(diagram, "isEmpty", DataType.BOOLEAN_TYPE));
        add(containsKey = new MethodDefinition(diagram, "containsKey", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, keyType))));
        add(containsValue = new MethodDefinition(diagram, "containsValue", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, getValueType()))));
        add(containsAll = new MethodDefinition(diagram, "containsAll", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, this))));
        add(get = new MethodDefinition(diagram, "get", getValueType(), asList(new ParameterDefinition(diagram, VALUE, keyType))));
    }

    @Override
    public String toString() {
        return getMetaType().getName() + "<" + keyType.toString() + ", " + getValueType().toString() + ">";
    }

    public DataType getKeyType() {
        return keyType;
    }

    public void setKeyType(DataType keyType) {
        this.keyType = keyType;
        
        containsKey.findParameterByName(VALUE).setType(keyType);
        get.findParameterByName(VALUE).setType(keyType);
        
        firePropertyChange(propertyKeyType, null,  keyType);
    }

    @Override
    public void setValueType(DataType valueType) {
        containsValue.findParameterByName(VALUE).setType(valueType);
        get.setType(valueType);
        super.setValueType(valueType);  
    }
    
    @Override
    public boolean isConcernedProperty(Object propName) {
        return propName == propertyKeyType || super.isConcernedProperty(propName);
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return combine(new IPropertyDescriptor[] {
                new ComboBoxPropertyDescriptor(propertyKeyType, propertyKeyType, KEY_TYPE_NAMES),},
                super.getPropertyDescriptors());
    }
    
    public Object getPropertyValue(Object propName) {
        if (propertyKeyType.equals(propName))
            return findIndex(KEY_TYPE_NAMES, keyType.getName());

        return super.getPropertyValue(propName);
    }

    public void setPropertyValue(Object propName, Object value){
        if (propertyKeyType.equals(propName))
            setKeyType(findDataType(KEY_TYPE_NAMES[(Integer)value]));

        super.setPropertyValue(propName, value);
    }
}
