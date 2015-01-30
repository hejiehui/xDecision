package com.xross.tools.xdecision;

public interface FactorValueParser {
	Object parse(String name, String value);

	/**
	 * Default implementation which just use the string as value
	 * @author jhhe
	 */
	public class StringFactorValueParser implements FactorValueParser {
		@Override
		public Object parse(String name, String value) {
			return value;
		}
		
	}
}
