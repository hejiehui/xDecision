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
    
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        // Do not allow name change for non predefined type
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
