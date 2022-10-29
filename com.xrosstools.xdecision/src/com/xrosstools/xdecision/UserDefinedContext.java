package com.xrosstools.xdecision;

public class UserDefinedContext implements Facts {
    private Facts constants;
    private Facts enums;
    public UserDefinedContext(Facts constants, Facts enums) {
        this.constants = constants;
        this.enums = enums;
    }

    @Override
    public String[] getNames() {
        return merge(constants, enums);
    }
    
    public static String[] merge(Facts fact1, Facts fact2) {
        String[] names1 = fact1.getNames();
        String[] names2 = fact2.getNames();
        String[] allNames = new String[names1.length + names2.length];
        
        System.arraycopy(names1, 0, allNames, 0, names1.length);
        System.arraycopy(names2, 0, allNames, names1.length, names2.length);
        
        return allNames;
    }
    
    @Override
    public boolean contains(String name) {
        if(constants.contains(name))
            return true;
        return enums.contains(name);
    }

    @Override
    public Object get(String name) {
        if(constants.contains(name))
            return constants.get(name);
        return enums.get(name);
    }    
}
