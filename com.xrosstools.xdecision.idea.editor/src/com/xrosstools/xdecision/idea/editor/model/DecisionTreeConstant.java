package com.xrosstools.xdecision.idea.editor.model;

import com.xrosstools.idea.gef.util.ComboBoxPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;
import com.xrosstools.xdecision.idea.editor.model.definition.DataType;
import com.xrosstools.xdecision.idea.editor.model.definition.DataTypeEnum;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementTypeEnum;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedType;

import java.util.Date;

public class DecisionTreeConstant extends NamedType {
    private static final String[] BOOLEAN_VALUES = new String[] {"false", "true"};

    public static final DecisionTreeConstant TRUE = new DecisionTreeConstant(null, "true");
    public static final DecisionTreeConstant FALSE = new DecisionTreeConstant(null, "false");

    static {
        TRUE.setType(DataType.BOOLEAN_TYPE);
        TRUE.setValue("true");

        FALSE.setType(DataType.BOOLEAN_TYPE);
        FALSE.setValue("false");
    }

    public DecisionTreeConstant(DecisionTreeDiagram diagram, String name) {
        super(diagram, name, NamedElementTypeEnum.CONSTANT, DEFAULT_TYPE);
    }

    private String value = "";
    
    @Override
    public void setType(DataType type) {
        super.setType(type);

        //Make sure init proper value for type
        switch (type.getMetaType()) {
        case STRING:
            setValue("");
            break;
        case NUMBER:
            setValue("0");
            break;
        case BOOLEAN:
            setValue("false");
            break;
        case DATE:
            setValue(new Date().toString());
            break;
        default:
            setValue("");
            break;
        }
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        firePropertyChange(PROP_VALUE, null, value);
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor valueDesc = getType().getMetaType() == DataTypeEnum.BOOLEAN ?
                new ComboBoxPropertyDescriptor(PROP_VALUE, BOOLEAN_VALUES) :
                    new TextPropertyDescriptor(PROP_VALUE);

        return combine(super.getPropertyDescriptors(),
                new IPropertyDescriptor[] {valueDesc});
    }
    
    public Object getPropertyValue(Object propName) {
        if (PROP_VALUE.equals(propName)) {
            return getType().getMetaType() == DataTypeEnum.BOOLEAN ? 
                    value.equals(BOOLEAN_VALUES[0]) ? 0:1:
                        value;
        }            

        return super.getPropertyValue(propName);
    }

    public void setPropertyValue(Object propName, Object value){
        super.setPropertyValue(propName, value);

        if (PROP_VALUE.equals(propName)) {
            if(getType().getMetaType() == DataTypeEnum.BOOLEAN)
                setValue(BOOLEAN_VALUES[(Integer)value]);
            else
                setValue((String)value);
        }
    }
}
