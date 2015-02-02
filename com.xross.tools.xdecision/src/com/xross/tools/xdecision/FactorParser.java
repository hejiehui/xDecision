package com.xross.tools.xdecision;

public interface FactorParser {
	Object parse(String name, String value);

	/**
	 * Default implementation which just use the string as value
	 * @author jhhe
	 */
	public class StringFactorParser implements FactorParser {
		@Override
		public Object parse(String name, String value) {
			return value;
		}
		
	}
}
