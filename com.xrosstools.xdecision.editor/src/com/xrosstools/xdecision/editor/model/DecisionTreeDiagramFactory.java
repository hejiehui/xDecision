package com.xrosstools.xdecision.editor.model;

import org.w3c.dom.Document;

public class DecisionTreeDiagramFactory {
	public DecisionTreeDiagram getFromXML(Document doc){
		DecisionTreeXMLSerializer saver = new DecisionTreeXMLSerializer();
		return saver.readMode(doc);
		
	}

	public DecisionTreeDiagram getEmptyDiagram(){
		DecisionTreeDiagram diagram = new DecisionTreeDiagram();
		return diagram;
	}
	
	public Document convertToXML(DecisionTreeDiagram diagram) {
		return new DecisionTreeXMLSerializer().writeModel(convert(diagram));
	}
	
	public DecisionTreeModel convert(DecisionTreeDiagram diagram) {
		DecisionTreeModel model = new DecisionTreeModel();
		model.setComments(diagram.getDescription());
		model.setParserClass(diagram.getParserClass());
		model.setEvaluatorClass(diagram.getEvaluatorClass());
		model.setDecisions(diagram.getDecisions().getElements().toArray(new DecisionTreeDecision[0]));
		model.setFactors(diagram.getFactorList().toArray(new DecisionTreeFactor[0]));
		model.setTypes(diagram.getUserDefinedTypeList().toArray(new DataType[0]));
		model.setNodes(diagram.getNodes().toArray(new DecisionTreeNode[0]));
		
		return model;
	}
}
