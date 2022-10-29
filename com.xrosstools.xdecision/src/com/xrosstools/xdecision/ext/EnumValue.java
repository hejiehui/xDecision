package com.xrosstools.xdecision.ext;

public class EnumValue {
    private EnumType parent;
    private String name;
    public EnumValue(EnumType parent, String name) {
        super();
        this.parent = parent;
        this.name = name;
    }
    public EnumType getParent() {
        return parent;
    }
    public String getName() {
        return name;
    }
}
