package com.xrosstools.xdecision.ext;

import com.xrosstools.xdecision.Facts;
import com.xrosstools.xdecision.UserDefinedContext;

public class InternalFact implements Facts{
    private UserDefinedContext context;
    private Facts facts;

    public InternalFact(Facts facts, UserDefinedContext context) {
        this.facts = facts;
        this.context = context;
    }

    @Override
    public String[] getNames() {
        return UserDefinedContext.merge(facts, context);
    }

    @Override
    public Object get(String name) {
        if(facts.contains(name))
            return facts.get(name);
        
        return context.get(name);
    }

    @Override
    public boolean contains(String name) {
        if(facts.contains(name))
            return facts.contains(name);
        
        return context.contains(name);
    }

}
