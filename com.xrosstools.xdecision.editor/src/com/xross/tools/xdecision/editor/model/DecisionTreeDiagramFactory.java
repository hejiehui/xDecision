package com.xross.tools.xdecision.editor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Document;

public class DecisionTreeDiagramFactory {
	public DecisionTreeDiagram getFromXML(Document doc){
		DecisionTreeXMLSerializer saver = new DecisionTreeXMLSerializer();
		return convert(saver.readMode(doc));
		
	}

	private DecisionTreeDiagram convert(DecisionTreeModel model){
		DecisionTreeDiagram diagram = new DecisionTreeDiagram();
		diagram.setDescription(model.getComments());
		diagram.setAlignment(0.5f);
		for(String decision: model.getDecisions())
			diagram.getDecisions().add(decision);
		
		for(DecisionTreeFactor factor: model.getFactors())
			diagram.getFactors().add(factor);
		
		Map<Integer, DecisionTreeNode> roots = new HashMap<Integer, DecisionTreeNode>();
		for(DecisionTreePath path: model.getPathes()){
			Integer rootFactor = new Integer(path.getPathEntries()[0].getFactorIndex());
			DecisionTreeNode parent = null;
			if(!roots.containsKey(rootFactor)){
				parent = new DecisionTreeNode();
				diagram.addNode(parent);
				roots.put(rootFactor, parent);
			}else
				parent = roots.get(rootFactor);
			
			for(DecisionTreePathEntry entry: path.getPathEntries()){
				parent.setFactorId(entry.getFactorIndex());
				DecisionTreeNode child = null;
				for(DecisionTreeNodeConnection conn: parent.getOutputs()){
					if(conn.getValueId() != entry.getValueIndex())
						continue;
					
					child = conn.getChild();
					break;
				}
				
				if(child == null){
					child = new DecisionTreeNode();
					diagram.addNode(child);
					DecisionTreeNodeConnection conn = new DecisionTreeNodeConnection(parent, child);
					conn.setValueId(entry.getValueIndex());
				}
				
				parent = child;
			}
			
			parent.setDecisionId(path.getDecisionIndex());
		}
		return diagram;
	}
	
	public DecisionTreeDiagram getEmptyDiagram(){
		DecisionTreeDiagram diagram = new DecisionTreeDiagram();
		
		int depth = 2;
		int width = 2;
		diagram.setAlignment(0.5f);
		for(int i = 0; i < depth + 1; i++){
			DecisionTreeFactor factor = new DecisionTreeFactor();
			factor.setFactorName("factor" + i);
			factor.setFactorValues(new String[]{"value1", "value2"});
			diagram.getFactors().add(factor);
			diagram.getDecisions().add("decision" + i);
		}
		
		DecisionTreeNode rootNode = new DecisionTreeNode();
		rootNode.setFactorId(0);
		List<DecisionTreeNode> last = new ArrayList<DecisionTreeNode>();
		last.add(rootNode);
		diagram.addNode(rootNode);
		for(int i = 0; i< depth; i++){
			List<DecisionTreeNode> newLast = new ArrayList<DecisionTreeNode>();
			for(DecisionTreeNode node : last){
				for(int j = 0; j< width; j++){
					DecisionTreeNode newNode = new DecisionTreeNode();
					newNode.setFactorId(i+1);
					newNode.setDecisionId(i+1);
					diagram.addNode(newNode);
					DecisionTreeNodeConnection path = new DecisionTreeNodeConnection(node, newNode);
					path.setValueId(1);
					newLast.add(newNode);
				}
			}
			last = newLast;
		}
		
		return diagram;
	}
	
	public Document convertToXML(DecisionTreeDiagram diagram) {
		return new DecisionTreeXMLSerializer().writeModel(convert(diagram));
	}
	
	public DecisionTreeModel convert(DecisionTreeDiagram diagram) {
		DecisionTreeModel model = new DecisionTreeModel();
		model.setComments(diagram.getDescription());
		model.setDecisions(diagram.getDecisions().toArray(new String[0]));
		model.setFactors(diagram.getFactors().toArray(new DecisionTreeFactor[0]));
		model.setPathes(createPath(diagram));
		
		return model;
	}
	
	private DecisionTreePath[] createPath(DecisionTreeDiagram diagram){
		List<DecisionTreePath> pathes = new ArrayList<DecisionTreePath>();
		for(DecisionTreeNode node: diagram.getNodes()){
			List<DecisionTreePathEntry> pathEntries = new ArrayList<DecisionTreePathEntry>();
			if(node.getOutputs().size() != 0 && node.getDecisionId() == -1)
				continue;
			
			DecisionTreeNode curNode = node;
			while(curNode.getInput() != null){
				int valueId = curNode.getInput().getValueId();
				pathEntries.add(new DecisionTreePathEntry(curNode.getInput().getParent().getFactorId(), valueId ));
				curNode = curNode.getInput().getParent();
			}
			if(pathEntries.size() > 0){
				DecisionTreePathEntry[] entries = new DecisionTreePathEntry[pathEntries.size()];
				for(int i = 0; i < pathEntries.size(); i++)
					entries[pathEntries.size() - (i+1)] = pathEntries.get(i);
				pathes.add(new DecisionTreePath(entries, node.getDecisionId()));
			}
		}
		return pathes.toArray(new DecisionTreePath[0]);
	}
}
