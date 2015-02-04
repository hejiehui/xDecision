package com.xross.tools.xdecision.utils;

import com.xross.tools.xdecision.XDecisionTreeParser;

/**
 * Default implementation which just use the string as value
 * @author jhhe
 */
public class StringFactorParser implements XDecisionTreeParser {
	@Override
	public Object parseFact(String name, String value) {
		return value;
	}

	@Override
	public Object parseDecision(String name, String value) {
		return value;
	}	
}
