package com.xrosstools.xdecision;

public interface XDecisionTreeParser<T> {
	Object parseFact(String name, String value);
	T parseDecision(String name);
}
