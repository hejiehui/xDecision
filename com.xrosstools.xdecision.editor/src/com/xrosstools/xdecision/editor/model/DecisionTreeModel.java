package com.xrosstools.xdecision.editor.model;


public class DecisionTreeModel {
	private String comments;
	private String parserClass;
	private String evaluatorClass;

	private String[] decisions;
	private DecisionTreeFactor[] factors;
	private UserDefinedType[] types;
	private DecisionTreeNode[] nodes;
	
	// for backward compatible
	private DecisionTreePath[] pathes;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getParserClass() {
        return parserClass;
    }
    public void setParserClass(String parserClass) {
        this.parserClass = parserClass;
    }
    public String getEvaluatorClass() {
        return evaluatorClass;
    }
    public void setEvaluatorClass(String evaluatorClass) {
        this.evaluatorClass = evaluatorClass;
    }
    public String[] getDecisions() {
		return decisions;
	}
	public void setDecisions(String[] decisions) {
		this.decisions = decisions;
	}
	public DecisionTreeFactor[] getFactors() {
		return factors;
	}
	public void setFactors(DecisionTreeFactor[] factors) {
		this.factors = factors;
	}
    public UserDefinedType[] getTypes() {
        return types;
    }
    public void setTypes(UserDefinedType[] types) {
        this.types = types;
    }
    public DecisionTreeNode[] getNodes() {
        return nodes;
    }
    public void setNodes(DecisionTreeNode[] nodes) {
        this.nodes = nodes;
    }
    public DecisionTreePath[] getPathes() {
        return pathes;
    }
    public void setPathes(DecisionTreePath[] pathes) {
        this.pathes = pathes;
    }
}
