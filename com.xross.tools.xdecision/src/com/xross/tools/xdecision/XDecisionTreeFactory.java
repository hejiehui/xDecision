package com.xross.tools.xdecision;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xross.tools.xdecision.utils.DecisionTreeFactor;
import com.xross.tools.xdecision.utils.DecisionTreeModel;
import com.xross.tools.xdecision.utils.DecisionTreePath;
import com.xross.tools.xdecision.utils.DecisionTreePathEntry;

public class XDecisionTreeFactory {
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
	
	private static final XDecisionTreeFactory factory = new XDecisionTreeFactory();

	/**
	 * Default implementation which just use the string as value
	 * @author Jerry He
	 */
	private static class StringParser implements XDecisionTreeParser {
		@Override
		public Object parseFact(String name, String value) {
			return value;
		}

		@Override
		public Object parseDecision(String name, String value) {
			return value;
		}
	}
	
	private static final XDecisionTreeParser defaultParser = new StringParser();

	public static <T> XDecisionTree<T> create(URL url) throws Exception {
        return create(url, defaultParser);
	}
	
	public static <T> XDecisionTree<T> create(URL url, XDecisionTreeParser parser) throws Exception {
        return create(url.openStream(), parser);
	}
	
	/**
	 * It will first check model file from file path, if it does not exist, it will try classpath then. 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static <T> XDecisionTree<T> create(String path) throws Exception {
		return create(path, defaultParser);
	}
	
	public static <T> XDecisionTree<T> create(String path, XDecisionTreeParser parser) throws Exception {
		InputStream in;
		File f = new File(path);
		if(f.exists())
			in = new FileInputStream(f);
		else {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = XDecisionTreeFactory.class.getClassLoader();
			}
			in = classLoader.getResource(path).openStream();
		}
		
		return create(in);
	}
	
	public static <T> XDecisionTree<T> create(InputStream in) throws Exception {
		return create(in, defaultParser);
	}
	
	public static <T> XDecisionTree<T> create(InputStream in, XDecisionTreeParser parser) throws Exception {
		try{
			Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			return factory.create(doc);
		} finally {
			try{
				if(in != null)
					in.close();
			}catch(Throwable e1){
			}
		}
	}
	
	private class FactorDefinition {
		String factorName;
		Object[] values;
	}

	public <T> XDecisionTree<T> create(Document doc) {
		XDecisionTree<T> tree = new XDecisionTree<T>();
		
		XDecisionPath<T>[] paths = createPaths(doc);
		FactorDefinition[] factors = createFactors(doc);
		String[] decisions = createDecisions(doc);
		
		for(int i = 0; i < paths.length; i++){
			TreePath[] entries = paths[i].getPathEntries();
			Object[][] newPath = new Object[entries.length][2];
			for(int j = 0; j < entries.length; j++){
				newPath[j][0] = entries[j].getFactorIndex();
				newPath[j][1] = factors[entries[j].getFactorIndex()].getFactorValues()[entries[j].getValueIndex()];
			}
			tree.add(newPath, decisions[paths[i].getDecisionIndex()]);
		}
		
		return tree;
	}
	
	private FactorDefinition[] createFactors(Document doc) {
		NodeList factorNodes = doc.getElementsByTagName(FACTORS).item(0).getChildNodes();
		
		FactorDefinition[] factors = new FactorDefinition[factorNodes.getLength()];
		for(int i = 0; i < factors.length; i++){
			Node factorNode = factorNodes.item(i);
			DecisionTreeFactor factor = new DecisionTreeFactor();
			
			factor.setFactorName(getAttribute(factorNode, ID));
			factors[getIntAttribute(factorNode, INDEX)] = factor;
			
			NodeList valueNodes = factorNode.getChildNodes();
			String[] values = new String[valueNodes.getLength()];
			factor.setFactorValues(values);
			for(int j = 0; j < values.length; j++){
				values[j] = valueNodes.item(j).getTextContent();
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
	
	private  <T> XDecisionPath<T>[] createPaths(Document doc) {
		NodeList pathNodes = doc.getElementsByTagName(PATHS).item(0).getChildNodes();
		XDecisionPath<T>[] paths = new XDecisionPath<T>[pathNodes.getLength()];
		for(int i = 0; i < paths.length; i++){
			paths[i] = getDecisionTreePath(pathNodes.item(i).getTextContent(), getIntAttribute(pathNodes.item(i), INDEX));
		}
		return paths;
	}
	
	/**
	 * @param value looks like 1:2|2:1|..., which means factor 1, value 2
	 * @return
	 */
	private  <T> XDecisionPath<T> getDecisionTreePath(String value, int decisionId){
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

		return new XDecisionPath(entries, decisionId);
	}
	
	private String[] createDecisions(Document doc) {
		NodeList decisionNodes = doc.getElementsByTagName(DECISIONS).item(0).getChildNodes();
		String[] decisions = new String[decisionNodes.getLength()];
		
		for(int i = 0; i < decisions.length; i++){
			decisions[getIntAttribute(decisionNodes.item(i), INDEX)] = getAttribute(decisionNodes.item(i), ID);
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