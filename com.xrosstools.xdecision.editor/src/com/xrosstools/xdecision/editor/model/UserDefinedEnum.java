package com.xrosstools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.List;

public class UserDefinedEnum extends DataType {
    public UserDefinedEnum(String name) {
        super(name);
    }

    private List<NamedElement> values = new ArrayList<NamedElement>();
    
    public List<NamedElement> getValues() {
        return values;
    }

    public void remove(String value) {
        int i = 0;
        for(NamedElement enumValue: values) {
            if(enumValue.getName().equals(value))
                break;
            i++;
        }
        if(i < values.size())
            values.remove(i);
    }

    public void add(String newValue) {
        NamedElement enumValue = new NamedElement(NamedElementTypeEnum.FIELD);
        enumValue.setName(newValue);
        values.add(enumValue);
    }
}
