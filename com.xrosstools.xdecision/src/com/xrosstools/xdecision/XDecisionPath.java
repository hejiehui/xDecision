package com.xrosstools.xdecision;

import java.util.List;

public class XDecisionPath<T> {
	public static class XDecisionPathEntry {
		private String factorExpression;
		private Object value;

		public XDecisionPathEntry(String factorExpression, Object value) {
			this.factorExpression = factorExpression;
			this.value = value;
		}
		
		public String getFactorExpression() {
			return factorExpression;
		}
		
		public Object getValue() {
			return value;
		}
	}
	
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
