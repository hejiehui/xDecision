package com.xrosstools.xdecision.editor.model;

import static java.util.Arrays.asList;

import java.util.Collections;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;

public class DataType extends NamedElement implements DecisionTreeMessages {
    public static final DataType STRING_TYPE = new DataType(DataTypeEnum.STRING); 
    public static final DataType NUMBER_TYPE = new DataType(DataTypeEnum.NUMBER);
    public static final DataType BOOLEAN_TYPE = new DataType(DataTypeEnum.BOOLEAN);
    public static final DataType DATE_TYPE = new DataType(DataTypeEnum.DATE);
    
    public static final List<DataType> PREDEFINED_TYPES = Collections.unmodifiableList(asList(STRING_TYPE, NUMBER_TYPE, BOOLEAN_TYPE, DATE_TYPE));
    
    
    private DataTypeEnum metaType;
    private String label;
    private NamedElementContainer<FieldDefinition> fields = new NamedElementContainer<FieldDefinition>(FIELDS_MSG, NamedElementTypeEnum.FIELD);
    private NamedElementContainer<MethodDefinition> methods = new NamedElementContainer<MethodDefinition>(METHODS_MSG, NamedElementTypeEnum.METHOD);

    
    //For map, we only support Integer and String as key type for now
    private DataType keyType;
    private DataType valueType;
    
    public static final DataType NOT_MATCHED = new DataType("Not Matched!"); 
    
    public static DataType createEnumType(String name) {
        return new UserDefinedEnum(name);
    }
    
//    public static DataType createSetType(DataType elementType) {
//        DataType setType = new DataType(String.format("Set<%s>", elementType.getName()));
//        setType.setValueType(elementType);
//        
//        setType.getMethods().addAll(getCommonMethod(elementType));
//        
//        setType.add(new MethodDefinition("contains", BOOLEAN_TYPE, asList(new FieldDefinition("value", elementType))));
//        setType.add(new MethodDefinition("containsAll", BOOLEAN_TYPE, asList(new FieldDefinition("value", setType))));
//
//        return setType;
//    }
//    
//    public static DataType createMapType(DataType keyType, DataType valueType) {
//        DataType mapType = new DataType(String.format("Set<%s>", valueType.getName()));
//        mapType.setKeyType(keyType);
//        mapType.setValueType(valueType);
//        
//        mapType.getMethods().addAll(getCommonMethod(valueType));
//        
//        mapType.add(new MethodDefinition("containsKey", BOOLEAN_TYPE, asList(new FieldDefinition("value", keyType))));
//        mapType.add(new MethodDefinition("containsValue", BOOLEAN_TYPE, asList(new FieldDefinition("value", valueType))));
//        mapType.add(new MethodDefinition("containsAll", BOOLEAN_TYPE, asList(new FieldDefinition("value", mapType))));
//        mapType.add(new MethodDefinition("get", valueType, asList(new FieldDefinition("value", keyType))));
//
//        return mapType;
//    }
    
    public DataType(DataTypeEnum metaType) {
        super(metaType.getName(), NamedElementTypeEnum.DATA_TYPE);
        this.metaType = metaType;
    }

    public DataType(String name) {
        super(name, NamedElementTypeEnum.DATA_TYPE);
    }
    
    public boolean isConcernedProperty(Object propName) {
        return false;
    }
    
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return DataTypeEnum.isPredefined(metaType) ? NONE : super.getPropertyDescriptors();
    }
    
    protected void add(MethodDefinition method) {
        methods.add(method);
    }
    
    protected void add(FieldDefinition field) {
        fields.add(field);
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
        firePropertyChange(PROP_LABEL, null, label);
    }

    public DataTypeEnum getType() {
        return metaType;
    }

    public DataType getKeyType() {
        return keyType;
    }

    public void setKeyType(DataType keyType) {
        this.keyType = keyType;
        firePropertyChange(PROP_KEY_TYPE, null, keyType);
    }

    public DataType getValueType() {
        return valueType;
    }

    public void setValueType(DataType valueType) {
        this.valueType = valueType;
        firePropertyChange(PROP_VALUE_TYPE, null, valueType);
    }

    public NamedElementContainer<FieldDefinition> getFields() {
        return fields;
    }

    public NamedElementContainer<MethodDefinition> getMethods() {
        return methods;
    }

    public FieldDefinition findField(String fieldName) {
        return (FieldDefinition)fields.findByName(fieldName);
    }

    public MethodDefinition findMethod(String methodName) {
        return (MethodDefinition)methods.findByName(methodName);
    }
}
