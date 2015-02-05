package com.xross.tools.xdecision;

import java.util.List;

public class XDecisionPath<T> {
	private List<XDecisionPathEntry> path;
	private T decision;
	
	public XDecisionPath(List<XDecisionPathEntry> path, T decision) {
		this.path = path;
		this.decision = decision;
	}

	public int length() {
		return path.size();
	}
	
	public XDecisionPathEntry getPathEntry(int depth) {
		return path.get(depth);
	}

	public T getDecision() {
		return decision;
	}
}
