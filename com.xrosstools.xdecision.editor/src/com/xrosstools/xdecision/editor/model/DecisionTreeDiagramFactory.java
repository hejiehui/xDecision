package com.xrosstools.xdecision.editor.model;

import java.util.Arrays;

import org.w3c.dom.Document;

public class DecisionTreeDiagramFactory {
	public DecisionTreeDiagram getFromXML(Document doc){
		DecisionTreeXMLSerializer saver = new DecisionTreeXMLSerializer();
		return convert(saver.readMode(doc));
		
	}

	private DecisionTreeDiagram convert(DecisionTreeModel model){
	    DecisionTreeDiagram diagram = new DecisionTreeDiagram();
	    diagram.setDescription(model.getComments());
	    diagram.setParserClass(model.getParserClass());
	    diagram.setEvaluatorClass(model.getEvaluatorClass());
		
	    diagram.setAlignment(0.5f);

	    diagram.getDecisions().addAll(Arrays.asList(model.getDecisions()));
	    diagram.getFactors().addAll(Arrays.asList(model.getFactors()));

		if(DecisionTreeV1FormatReader.isV1Format(model))
		    DecisionTreeV1FormatReader.buildTree(model, diagram);
		else {
		    diagram.getUserDefinedTypes().addAll(Arrays.asList(model.getTypes()));
		    diagram.getNodes().addAll(Arrays.asList(model.getNodes()));
		}

        return diagram;
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
		model.setDecisions(diagram.getDecisions().toArray(new String[0]));
		model.setFactors(diagram.getFactors().toArray(new DecisionTreeFactor[0]));
		model.setTypes(diagram.getUserDefinedTypes().toArray(new UserDefinedType[0]));
		model.setNodes(diagram.getNodes().toArray(new DecisionTreeNode[0]));
		
		return model;
	}
}
