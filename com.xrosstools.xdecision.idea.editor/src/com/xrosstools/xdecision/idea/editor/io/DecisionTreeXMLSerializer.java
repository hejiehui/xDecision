package com.xrosstools.xdecision.idea.editor.io;

import static com.xrosstools.gef.util.XmlHelper.getValidChildNodes;

import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;

import com.xrosstools.xdecision.idea.editor.model.DecisionTreeFactor;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreePath;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreePathEntry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DecisionTreeXMLSerializer {
	public static final String DECISION_TREE = "decision_tree";
	
	public static final String COMMENTS = "comments";

	public static final String FACTORS = "factors";
	public static final String FACTOR = "factor";
	public static final String VALUE = "value";
		
	public static final String DECISIONS = "decisions";
	public static final String DECISION = "decision";

	public static final String PATHS = "paths";
	public static final String PATH = "path";
	
	public static final String ID = "id";
	public static final String INDEX = "index";

	
	public static final String TOTAL_FACTOR_NUMBER = "total_factor_num";
	public static final String TOTAL_DECISION_NUMBER = "total_decision_num";
	public static final String TOTAL_PATH_NUMBER = "total_path_num";
	
	public static final String _DECISION = "_decision";
	public static final String DELIMITER = "|";
	public static final char FACTOR_VALUE_DELIMITER = ':';

	
	public DecisionTreeModel readMode(Document doc) {
		DecisionTreeModel model = new DecisionTreeModel();
		model.setComments(getComments(doc));
		model.setFactors(createFactors(doc));
		model.setPathes(createPaths(doc));
		model.setDecisions(createDecisions(doc));
		return model;
	}
	
	private String getComments(Document doc){
		if(doc.getElementsByTagName(COMMENTS).getLength() == 0)
			return "";
		return doc.getElementsByTagName(COMMENTS).item(0).getNodeValue();
	}

	private DecisionTreeFactor[] createFactors(Document doc) {
		List<Node> factorNodes = getValidChildNodes(doc.getElementsByTagName(FACTORS).item(0));
		
		DecisionTreeFactor[] factors = new DecisionTreeFactor[factorNodes.size()];
		for(int i = 0; i < factors.length; i++){
			Node factorNode = factorNodes.get(i);
			DecisionTreeFactor factor = new DecisionTreeFactor();
			
			factor.setFactorName(getAttribute(factorNode, ID));
			factors[getIntAttribute(factorNode, INDEX)] = factor;
			
			List<Node> valueNodes = getValidChildNodes(factorNode);
			String[] values = new String[valueNodes.size()];
			factor.setFactorValues(values);
			for(int j = 0; j < values.length; j++){
				values[j] = valueNodes.get(j).getTextContent();
			}
		}
		
		return factors;
	}
	
	private int getIntAttribute(Node node, String attributeName){
		return Integer.parseInt(getAttribute(node, attributeName));
	}
	
	private String getAttribute(Node node, String attributeName){
		NamedNodeMap map = node.getAttributes();
		for(int i = 0; i < map.getLength(); i++){
			if(attributeName.equals(map.item(i).getNodeName()))
				return map.item(i).getNodeValue();
		}
		
		return null;
	}
	
	private DecisionTreePath[] createPaths(Document doc) {
		List<Node> pathNodes = getValidChildNodes(doc.getElementsByTagName(PATHS).item(0));
		DecisionTreePath[] paths = new DecisionTreePath[pathNodes.size()];
		for(int i = 0; i < paths.length; i++){
			paths[i] = getDecisionTreePath(pathNodes.get(i).getTextContent(), getIntAttribute(pathNodes.get(i), INDEX));
		}
		return paths;
	}
	
	/**
	 * @param value looks like 1:2|2:1|..., which means factor 1, value 2
	 * @return
	 */
	private DecisionTreePath getDecisionTreePath(String value, int decisionId){
		StringTokenizer t = new StringTokenizer(value, DELIMITER);
		DecisionTreePathEntry[] entries = new DecisionTreePathEntry[t.countTokens()];
		
		for(int i = 0; i < entries.length; i++){
			String pair = t.nextToken();
			int index = pair.indexOf(FACTOR_VALUE_DELIMITER);
			String factorIndexStr = pair.substring(0, index);
			String valueIndexStr = pair.substring(index+1, pair.length());
			DecisionTreePathEntry entry = new DecisionTreePathEntry(new Integer(factorIndexStr), Integer.parseInt(valueIndexStr));
			entries[i] = entry;
		}

		return new DecisionTreePath(entries, decisionId);
	}
	
	private String[] createDecisions(Document doc) {
		List<Node> decisionNodes = getValidChildNodes(doc.getElementsByTagName(DECISIONS).item(0));
		String[] decisions = new String[decisionNodes.size()];
		
		for(int i = 0; i < decisions.length; i++){
			decisions[getIntAttribute(decisionNodes.get(i), INDEX)] = getAttribute(decisionNodes.get(i), ID);
		}

		return decisions;
	}

	public Document writeModel(DecisionTreeModel model){
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = (Element)doc.createElement(DECISION_TREE);
			doc.appendChild(root);
			setComments(doc, root, model.getComments());
			
			Element factorsNode = (Element)doc.createElement(FACTORS);
			root.appendChild(factorsNode);
			writeFactors(doc, factorsNode, model);
			
			Element pathsNode = (Element)doc.createElement(PATHS);
			root.appendChild(pathsNode);
			writePathes(doc, pathsNode, model);
			
			Element decisionsNode = (Element)doc.createElement(DECISIONS);
			root.appendChild(decisionsNode);
			writeDecisions(doc, decisionsNode, model);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void setComments(Document doc, Element root, String comments){
		Element commentsNode = (Element)doc.createElement(COMMENTS);
		commentsNode.appendChild(doc.createTextNode(comments));
	}

	
	private void writeFactors(Document doc, Element factorsNode, DecisionTreeModel model){
		DecisionTreeFactor[] factors = model.getFactors();
		for(int i = 0; i < factors.length; i++){
			DecisionTreeFactor factor = factors[i];
			Element factorNode = (Element)doc.createElement(FACTOR);
			factorNode.setAttribute(ID, factor.getFactorName());
			factorNode.setAttribute(INDEX, String.valueOf(i));
			factorsNode.appendChild(factorNode);
			
			String[] values = factor.getFactorValues();
			for(int j = 0; j < values.length; j++){
				Element valueNode = (Element)doc.createElement(VALUE);
				valueNode.appendChild(doc.createTextNode(values[j]));
				factorNode.appendChild(valueNode);
			}
		}
	}
	
	private void writePathes(Document doc, Element pathsNode, DecisionTreeModel model){
		DecisionTreePath[] pathes = model.getPathes();
		
		for(int i = 0; i < pathes.length; i++){
			Element pathNode = (Element)doc.createElement(PATH);
			pathNode.setAttribute(INDEX, String.valueOf(pathes[i].getDecisionIndex()));
			pathsNode.appendChild(pathNode);
			pathNode.appendChild(doc.createTextNode(buildDecisionTreePath(pathes[i])));
		}
	}
	
	/**
	 * @return looks like 1:2|2:1|..., which means factor 1, value 2
	 */
	private String buildDecisionTreePath(DecisionTreePath path){
		DecisionTreePathEntry[] entries = path.getPathEntries();
		StringBuffer sbf = new StringBuffer();
		
		for(int i = 0; i < entries.length; i++){
			sbf.append(entries[i].getFactorIndex()).append(FACTOR_VALUE_DELIMITER).append(entries[i].getValueIndex());
			if(i < entries.length - 1)
				sbf.append(DELIMITER);	
		}
		
		return sbf.toString();
	}

	private void writeDecisions(Document doc, Element decisionsNode, DecisionTreeModel model){
		String[] decisions = model.getDecisions();
		
		for(int i = 0; i < decisions.length; i++){
			Element decisionNode = (Element)doc.createElement(DECISION);
			decisionNode.setAttribute(ID, decisions[i]);
			decisionNode.setAttribute(INDEX, String.valueOf(i));
			decisionsNode.appendChild(decisionNode);
		}
	}
}