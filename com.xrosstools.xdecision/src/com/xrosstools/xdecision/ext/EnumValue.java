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

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;

        if(o == this)
            return true;
        
        if(o.getClass().isEnum()) {
            if(o.getClass().getSimpleName().equals(getParent().getName()))
                return o.toString().equals(name);
            return false;
        }
        
        if(o instanceof EnumValue) {
            EnumValue ev = (EnumValue)o;
            return ev.getParent() == getParent();
        }
        return false;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
