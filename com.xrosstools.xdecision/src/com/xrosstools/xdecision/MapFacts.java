package com.xrosstools.xdecision;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation, which use a map internally to hold the factory values.
 * This is useful for cases that the calculation of facts is cheap. 
 * @author Jerry He
 */
public class MapFacts implements Facts {
	private Map<String, Object> values = new HashMap<String, Object>();

	@Override
	public Object get(String name) {
		return values.get(name);
	}
	
	public void set(String name, Object value) {
		values.put(name, value);
	}
	
	public void reset() {
		values.clear();
	}

    @Override
    public String[] getNames() {
        return values.keySet().toArray(new String[values.size()]);
        
    }

    @Override
    public boolean contains(String name) {
        return values.containsKey(name);
    }
}
