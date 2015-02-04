package com.xross.tools.xdecision;

public class XDecisionPath<T> {
	private XDecisionPathEntry[] path;
	private T decision;
	
	public XDecisionPath(XDecisionPathEntry[] path, T decision) {
		this.path = path;
		this.decision = decision;
	}

	public int length() {
		return path.length;
	}
	
	public XDecisionPathEntry getPathEntry(int depth) {
		return path[depth];
	}

	public T getDecision() {
		return decision;
	}
}
