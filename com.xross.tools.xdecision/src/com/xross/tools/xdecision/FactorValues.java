package com.xross.tools.xdecision;

import java.util.HashMap;
import java.util.Map;

public interface FactorValues {
	Object get(String name);
	
	/**
	 * Default implementation, which use a map internally to hold the factory values
	 * @author jhhe
	 */
	public class DefaultFactorValues implements FactorValues {
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
	}
}
