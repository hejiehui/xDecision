package com.xross.tools.xdecision;

import java.util.HashMap;
import java.util.Map;

public interface Facts {
	Object get(String name);
	
	/**
	 * Default implementation, which use a map internally to hold the factory values
	 * @author jhhe
	 */
	public class DefaultFacts implements Facts {
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
