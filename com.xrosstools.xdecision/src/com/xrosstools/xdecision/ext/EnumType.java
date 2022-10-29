package com.xrosstools.xdecision.ext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumType {
    private String name;
    private Map<String, EnumValue> values = new HashMap<>();
    
    public EnumType(String name, List<String> enumValues) {
        this.name = name;
        for(String v: enumValues)
            values.put(v,  new EnumValue(this, v));
    }
    
    public String getName() {
        return name;
    }
    
    public EnumValue valueOf(String name) {
        return values.get(name);
    }

    public boolean contains(String name) {
        return values.containsKey(name);
    }

}
