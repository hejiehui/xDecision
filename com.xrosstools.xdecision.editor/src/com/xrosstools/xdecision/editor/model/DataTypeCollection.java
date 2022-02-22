package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

public class DataTypeCollection extends DataTypeTemplate {
    public static final String VALUE = "value";
    
    public static final DataType DEFAULT_VALUE_TYPE = DataType.STRING_TYPE;
    
    private DataType valueType = DEFAULT_VALUE_TYPE;
    private String propertyType;
    
    private MethodDefinition size;
    private MethodDefinition isEmpty;
    private MethodDefinition contains;
    private MethodDefinition containsAll;
    
    public DataTypeCollection(DecisionTreeDiagram diagram) {
        this(diagram, DataTypeEnum.COLLECTION);
    }

    public DataTypeCollection(DecisionTreeDiagram diagram, DataTypeEnum type) {
        super(diagram, type);
        propertyType = String.format(PROP_VALUE_TYPE_TPL, getType().getName());
        
        add(size = new MethodDefinition(diagram, "size", DataType.NUMBER_TYPE));
        add(isEmpty = new MethodDefinition(diagram, "isEmpty", DataType.BOOLEAN_TYPE));
        add(contains = new MethodDefinition(diagram, "contains", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, valueType))));
        add(containsAll = new MethodDefinition(diagram, "containsAll", DataType.BOOLEAN_TYPE, asList(new ParameterDefinition(diagram, VALUE, this))));
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
