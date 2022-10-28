package com.xrosstools.xdecision;

public interface XDecisionTreeParser<T> {
	Object parseDecisionPath(String nodeExpression, String operator, String pathExpression);
	T parseDecision(String name);
}
