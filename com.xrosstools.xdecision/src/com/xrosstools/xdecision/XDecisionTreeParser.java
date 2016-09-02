package com.xrosstools.xdecision;

public interface XDecisionTreeParser {
	Object parseFact(String name, String value);
	Object parseDecision(String name);
}
