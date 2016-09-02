package com.xrosstools.xdecision;

import java.util.List;

public class XDecisionPath<T> {
	public static class XDecisionPathEntry {
		private String factorName;
		private Object value;

		public XDecisionPathEntry(String factorName, Object value) {
			this.factorName = factorName;
			this.value = value;
		}
		
		public String getFactorName() {
			return factorName;
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
