package com.xross.tools.xdecision;

public interface XDecisionTreeParser {
	Object parseFact(String name, String value);
	Object parseDecision(String name, String value);
}
