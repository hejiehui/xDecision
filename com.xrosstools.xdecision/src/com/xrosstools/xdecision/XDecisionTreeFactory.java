package com.xrosstools.xdecision;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xrosstools.xdecision.XDecisionPath.XDecisionPathEntry;

public class XDecisionTreeFactory {
	public static final String DECISION_TREE = "decision_tree";
	
	public static final String COMMENTS = "comments";
	public static final String PARSER = "parser";
	
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
		public Object parseDecision(String name) {
			return name;
		}
	}
	
	private static final XDecisionTreeParser defaultParser = new StringParser();

	public static <T> XDecisionTree<T> create(URL url) throws Exception {
        return create(url.openStream());
	}
	
	/**
	 * It will first check model file from file path, if it does not exist, it will try classpath then. 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static <T> XDecisionTree<T> create(String path) throws Exception {
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

	public <T> XDecisionTree<T> create(Document doc) throws Exception {
		XDecisionTree<T> tree = new XDecisionTree<T>();
		
		XDecisionTreeParser parser = createParser(doc);
		Object[] decisions = createDecisions(doc, parser);
		FactorDefinition[] factors = createFactors(doc, parser);

		List<Node> pathNodes = getValidChildNodes(doc.getElementsByTagName(PATHS).item(0));
		
		for(int i = 0; i < pathNodes.size(); i++){
			Node pathNode = pathNodes.get(i);
			
			StringTokenizer t = new StringTokenizer(pathNode.getTextContent(), DELIMITER);
			int entryLength = t.countTokens();
			List<XDecisionPathEntry> entries = new ArrayList<XDecisionPathEntry>(entryLength);
			
			for(int j = 0; j < entryLength; j++){
				String pair = t.nextToken();
				int index = pair.indexOf(FACTOR_VALUE_DELIMITER);
				int factorIndex = Integer.parseInt(pair.substring(0, index));
				int valueIndex = Integer.parseInt(pair.substring(index+1, pair.length()));
				FactorDefinition factor = factors[factorIndex];
				XDecisionPathEntry entry = new XDecisionPathEntry(factor.factorName, factor.values[valueIndex]);
				entries.add(entry);
			}

			XDecisionPath<T> path = new XDecisionPath<T>(entries, (T)decisions[getIntAttribute(pathNode, INDEX)]);
			tree.add(path);
		}
		
		return tree;
	}
	
	private XDecisionTreeParser createParser(Document doc) throws Exception {
		Node parserNode = doc.getElementsByTagName(PARSER).item(0);
		if(parserNode == null)
			return defaultParser;

		String parserClassName = parserNode.getTextContent();
		if(parserClassName == null || parserClassName.trim().equals(""))
			return defaultParser;
		
		return (XDecisionTreeParser)Class.forName(parserClassName).newInstance();
	}
	
	private FactorDefinition[] createFactors(Document doc, XDecisionTreeParser parser) {
		List<Node> factorNodes = getValidChildNodes(doc.getElementsByTagName(FACTORS).item(0));
		
		FactorDefinition[] factors = new FactorDefinition[factorNodes.size()];
		
		for(int i = 0; i < factors.length; i++){
			Node factorNode = factorNodes.get(i);
			
			FactorDefinition factor = new FactorDefinition();
			
			factor.factorName = getAttribute(factorNode, ID);
			factors[getIntAttribute(factorNode, INDEX)] = factor;
			
			List<Node> valueNodes = getValidChildNodes(factorNode);
			Object[] values = new Object[valueNodes.size()];
			factor.values = values;
			for(int j = 0; j < values.length; j++){
				values[j] = parser.parseFact(factor.factorName, valueNodes.get(j).getTextContent());
			}
		}
		
		return factors;
	}
	
	private Object[] createDecisions(Document doc, XDecisionTreeParser parser) {
		List<Node> decisionNodes = getValidChildNodes(doc.getElementsByTagName(DECISIONS).item(0));
		
		Object[] decisions = new String[decisionNodes.size()];
		
		for(int i = 0; i < decisions.length; i++){
			Node decisionNode = decisionNodes.get(i);
			decisions[getIntAttribute(decisionNode, INDEX)] = parser.parseDecision(getAttribute(decisionNode, ID));
		}

		return decisions;
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
	
	private boolean isValidNode(Node node) {
		return !node.getNodeName().equals("#text");
	}
	
	private List<Node> getValidChildNodes(Node node) {
		List<Node> nl = new ArrayList<>();
		NodeList nodeList = node.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++){
			if(isValidNode(nodeList.item(i)))
				nl.add(nodeList.item(i));
		}
		return nl;
	}
}