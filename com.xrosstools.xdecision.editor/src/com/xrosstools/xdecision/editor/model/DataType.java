package com.xrosstools.xdecision.editor.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xrosstools.xdecision.editor.actions.DecisionTreeMessages;

public class DataType extends NamedElement implements DecisionTreeMessages {
    public static final DataType STRING_TYPE = new DataType(DataTypeEnum.STRING); 
    public static final DataType NUMBER_TYPE = new DataType(DataTypeEnum.NUMBER);
    public static final DataType BOOLEAN_TYPE = new DataType(DataTypeEnum.BOOLEAN);
    public static final DataType DATE_TYPE = new DataType(DataTypeEnum.DATE);
    
    private static final String[] PREDEFINED_ALL_TYPE_NAMES = new String[] {
            DataTypeEnum.STRING.getName(),
            DataTypeEnum.NUMBER.getName(),
            DataTypeEnum.BOOLEAN.getName(),
            DataTypeEnum.DATE.getName(),
            DataTypeEnum.ARRAY.getName(),
            DataTypeEnum.COLLECTION.getName(),
            DataTypeEnum.LIST.getName(),
            DataTypeEnum.SET.getName(),
            DataTypeEnum.MAP.getName()};
    
    private static final String[] PREDEFINED_VALUE_TYPE_NAMES = new String[] {
            DataTypeEnum.STRING.getName(),
            DataTypeEnum.NUMBER.getName(),
            DataTypeEnum.BOOLEAN.getName(),
            DataTypeEnum.DATE.getName()};

    private static final String[] CONSTANT_TYPE_NAMES = PREDEFINED_VALUE_TYPE_NAMES;

    private static final NamedElementContainer<DataType> USER_DEFINED_TYPES = new NamedElementContainer<DataType>(DataTypeEnum.USER_DEFINED.getName(), NamedElementTypeEnum.DATA_TYPE);
    
    private DataTypeEnum metaType;
    private String label;
    private NamedElementContainer<FieldDefinition> fields = new NamedElementContainer<FieldDefinition>(FIELDS_MSG, NamedElementTypeEnum.FIELD);
    private NamedElementContainer<MethodDefinition> methods = new NamedElementContainer<MethodDefinition>(METHODS_MSG, NamedElementTypeEnum.METHOD);

    public static final DataType NOT_MATCHED = new DataType("Not Matched!"); 
    
    public static DataType createEnumType(String name) {
        return new UserDefinedEnum(name);
    }
    
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

    public static NamedElementContainer<DataType> getUserDefinedTypes() {
        return USER_DEFINED_TYPES;
    }

    public static String[] combine(String[] str1, String[] str2) {
        String[] allNames = new String[str1.length + str2.length];
        
        System.arraycopy(str1, 0, allNames, 0, str1.length);
        System.arraycopy(str2, 0, allNames, str1.length, str2.length);

        return allNames;
    }
    
    public static String[] getAllTypeNames() {
        return combine(PREDEFINED_ALL_TYPE_NAMES, USER_DEFINED_TYPES.getElementNames());
    }
    
    public static String[] getConstantTypeNames() {
        return CONSTANT_TYPE_NAMES;
    }
    
    public static String[] getValueTypeNames() {
        return combine(PREDEFINED_VALUE_TYPE_NAMES, USER_DEFINED_TYPES.getElementNames());
    }

    public static DataType findDataType(String name) {
        DataType type = USER_DEFINED_TYPES.findByName(name);
        
        if(type != null)
            return type;
            
        
        return findByName(name).createDataType();
    }

    public static DataTypeEnum findByName(String name) {
        for(DataTypeEnum e: DataTypeEnum.values())
            if(e.getName().equals(name))
                return e;
        return DataTypeEnum.USER_DEFINED;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        // Do not allow name change for non predefined type
        return DataTypeEnum.isUserDefined(metaType) ? super.getPropertyDescriptors() : NONE;
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
