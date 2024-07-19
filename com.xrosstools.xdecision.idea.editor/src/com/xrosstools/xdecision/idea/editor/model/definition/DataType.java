package com.xrosstools.xdecision.idea.editor.model.definition;

import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeMessages;

public class DataType extends NamedElement implements DecisionTreeMessages {
    public static final DataType STRING_TYPE = new DataType(DataTypeEnum.STRING); 
    public static final DataType NUMBER_TYPE = new DataType(DataTypeEnum.NUMBER);
    public static final DataType BOOLEAN_TYPE = new DataType(DataTypeEnum.BOOLEAN);
    public static final DataType DATE_TYPE = new DataType(DataTypeEnum.DATE);
    
    public static final String[] PREDEFINED_ALL_TYPE_NAMES = new String[] {
            DataTypeEnum.STRING.getName(),
            DataTypeEnum.NUMBER.getName(),
            DataTypeEnum.BOOLEAN.getName(),
            DataTypeEnum.DATE.getName(),
            DataTypeEnum.ARRAY.getName(),
            DataTypeEnum.COLLECTION.getName(),
            DataTypeEnum.LIST.getName(),
            DataTypeEnum.SET.getName(),
            DataTypeEnum.MAP.getName()};
    
    public static final String[] PREDEFINED_VALUE_TYPE_NAMES = new String[] {
            DataTypeEnum.STRING.getName(),
            DataTypeEnum.NUMBER.getName(),
            DataTypeEnum.BOOLEAN.getName(),
            DataTypeEnum.DATE.getName()};

    public static final String[] CONSTANT_TYPE_NAMES = PREDEFINED_VALUE_TYPE_NAMES;

    private DataTypeEnum metaType;
    private String label;
    private NamedElementContainer<FieldDefinition> fields = new NamedElementContainer<>(FIELDS_MSG, NamedElementTypeEnum.FIELD);
    private NamedElementContainer<MethodDefinition> methods = new NamedElementContainer<>(METHODS_MSG, NamedElementTypeEnum.METHOD);

    public static final DataType NOT_MATCHED = new DataType("Not Matched!"); 
    
    public DataType(DataTypeEnum metaType) {
        super(metaType.getName(), NamedElementTypeEnum.DATA_TYPE);
        this.metaType = metaType;
    }

    public DataType(String name) {
        super(name, NamedElementTypeEnum.DATA_TYPE);
        this.metaType = DataTypeEnum.USER_DEFINED;
    }

    public boolean isConcernedProperty(Object propName) {
        return false;
    }

    public static String[] getConstantTypeNames() {
        return CONSTANT_TYPE_NAMES;
    }
    
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        // Do not allow name change for non predefined type
        return DataTypeEnum.isUserDefined(metaType) ? super.getPropertyDescriptors() : NONE;
    }

    public void add(MethodDefinition method) {
        methods.add(method);
    }

    public void add(FieldDefinition field) {
        fields.add(field);
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
        firePropertyChange(PROP_LABEL, null, label);
    }

    public DataTypeEnum getMetaType() {
        return metaType;
    }

    public NamedElementContainer<FieldDefinition> getFields() {
        return fields;
    }

    public NamedElementContainer<MethodDefinition> getMethods() {
        return methods;
    }

    public FieldDefinition findField(String fieldName) {
        return fields.findByName(fieldName);
    }

    public MethodDefinition findMethod(String methodName) {
        return methods.findByName(methodName);
    }
    
    public static DataType getType(NamedElement refrenceElement) {
        if(refrenceElement instanceof EnumType)
            return (DataType)refrenceElement;
        
        if(refrenceElement instanceof NamedType)
            return ((NamedType) refrenceElement).getType();
        
        return null;
    }
}
