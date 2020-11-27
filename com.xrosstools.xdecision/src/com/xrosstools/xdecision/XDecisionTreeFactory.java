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
	public static final String EVALUATOR = "evaluator";
	
	public static final String FACTORS = "factors";
	public static final String FACTOR = "factor";
	public static final String VALUE = "value";
		
	public static final String DECISIONS = "decisions";
	public static final String DECISION = "decision";

	public static final String PATHS = "paths";
	public static final String PATH = "path";
	
	public static final String ID = "id";
	public static final String INDEX = "index";

	
    public static final String USER_DEFINED_TYPES = "user_defined_types";
    public static final String USER_DEFINED_TYPE = "user_defined_type";
    
    public static final String NAME = "name";
    public static final String LABEL = "label";
    public static final String TYPE = "type";
    public static final String TYPE_NAME = "type_name";
    
    public static final String FIELDS = "fields";
    public static final String FIELD = "field";
    public static final String FIELD_SEPARATOR = ".";

    public static final String NODES = "nodes";
    public static final String NODE = "node";
    public static final String FACTOR_INDEX = "factor_index";
    public static final String DECISION_INDEX = "decision_index";
    public static final String FACTOR_FIELD = "factor_field";
    public static final String FUNCTION_NAME = "function_name";
    public static final String VALUE_INDEX = "value_index";
    public static final String NODE_INDEX = "node_index";
    
	public static final String DELIMITER = "|";
	public static final char FACTOR_VALUE_DELIMITER = ':';
	
	private static final XDecisionTreeFactory factory = new XDecisionTreeFactory();

	private static final XDecisionTreeParser defaultParser = new DefaultParser();
	private static final PathEvaluator defaultEvaluator = new DefaultEvaluator();

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
		XDecisionTreeParser<T> parser = createParser(doc);
		PathEvaluator evaluator = createEvaluator(doc);
		List<T> decisions = createDecisions(doc, parser);
		FactorDefinition[] factors = createFactors(doc, parser);

		return doc.getElementsByTagName(PATHS).getLength() > 0 ?
		        (XDecisionTree<T>)createV1Tree(doc, evaluator, decisions, factors) :
		            (XDecisionTree<T>)createV2Tree(doc, evaluator, decisions, factors);
	}

    private <T> XDecisionTree<T> createV1Tree(Document doc, PathEvaluator evaluator, List<T> decisions, FactorDefinition[] factors) {
        XDecisionTree<T> tree = new XDecisionTree<>(evaluator);
        
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

			XDecisionPath<T> path = new XDecisionPath<>(entries, decisions.get(getIntAttribute(pathNode, INDEX)));
			tree.add(path);
		}
        return tree;
    }
	
    public <T> XDecisionTree<T> createV2Tree(Document doc, PathEvaluator evaluator, List<T> decisions, FactorDefinition[] factors) {
        XDecisionTree<T> tree = new XDecisionTree<T>(evaluator);
        
        List<DecisionTreeNode> nodes = new ArrayList<>();
        
        List<Node> nodeNodes = getValidChildNodes(doc.getElementsByTagName(NODES).item(0));
        
        for(int i = 0; i < nodeNodes.size(); i++)
            nodes.add(create(nodeNodes.get(i), factors));
        
        //build tree
        for(DecisionTreeNode node: nodes) {
            for(DecisionTreePath path: node.pathes) {
                nodes.get(path.nodeIndex).parent = node;
                nodes.get(path.nodeIndex).factorValue = path.factorValue;
            }
        }
        
        for(DecisionTreeNode node: nodes) {
            if(node.decisionId < 0)
                continue;
            
            T decision = decisions.get(node.decisionId);
            
            List<XDecisionPathEntry> entries = new ArrayList<XDecisionPathEntry>();
            
            while(node.parent != null) {
                entries.add(0, new XDecisionPathEntry(node.parent.factorExpression, node.factorValue));
                node = node.parent;
            }
            
            tree.add(new XDecisionPath<T>(entries, decision));
        }
        
        return tree;
    }

    private <T> XDecisionTreeParser<T> createParser(Document doc) throws Exception {
	    return createPlugin(doc, PARSER, defaultParser);
	}
	
    private PathEvaluator createEvaluator(Document doc) throws Exception {
        return createPlugin(doc, EVALUATOR, defaultEvaluator);
    }
    
    private <T> T createPlugin(Document doc, String pluginKey, T defaultImplementation) throws Exception {
        Node pluginNode = doc.getElementsByTagName(pluginKey).item(0);
        if(pluginNode == null)
            return defaultImplementation;

        String pluginClassName = pluginNode.getTextContent();
        if(pluginClassName == null || pluginClassName.trim().equals(""))
            return defaultImplementation;
        
        return (T)Class.forName(pluginClassName).newInstance();
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
	
	private <T> List<T> createDecisions(Document doc, XDecisionTreeParser<T> parser) {
		List<Node> decisionNodes = getValidChildNodes(doc.getElementsByTagName(DECISIONS).item(0));
		
		List<T> decisions = new ArrayList<>();
		
		for(Node decisionNode: decisionNodes) {
			decisions.add(parser.parseDecision(getAttribute(decisionNode, ID)));
		}

		return decisions;
	}
	
    private int getIntAttribute(Node node, String attributeName, int defaultValue){
        String valueStr = getAttribute(node, attributeName);
        return valueStr == null ? defaultValue : Integer.parseInt(valueStr);
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
	
    private class DecisionTreePath {
        Object factorValue;
        int nodeIndex;
        
        DecisionTreePath(Object factorValue, int nodeIndex) {
            this.factorValue = factorValue;
            this.nodeIndex = nodeIndex;
        }
    }

	private class DecisionTreeNode {
	    int factorId;
	    int decisionId;
        String factorExpression;

        DecisionTreeNode parent;
        Object factorValue;
	    
	    List<DecisionTreePath> pathes = new ArrayList<>();
	}
	    
    private DecisionTreeNode create(Node nodeNode, FactorDefinition[] factors) {
        DecisionTreeNode node = new DecisionTreeNode();

        node.decisionId = getIntAttribute(nodeNode, DECISION_INDEX, -1);
        node.factorId = getIntAttribute(nodeNode, FACTOR_INDEX, -1);
        
        String factorField = getAttribute(nodeNode, FACTOR_FIELD);
        String functionName = getAttribute(nodeNode, FUNCTION_NAME);

        StringBuffer displayText = new StringBuffer();
        
        if(node.factorId != -1)
            displayText.append(factors[node.factorId].factorName);

        if(factorField != null)
            displayText.append(FIELD_SEPARATOR).append(factorField);
        
        if(functionName != null)
            displayText = new StringBuffer(String.format("%s(%s)", functionName, displayText.toString()));
        
        node.factorExpression = displayText.toString();

        
        List<Node> pathNodes = getValidChildNodes(nodeNode);
        for(int i = 0; i < pathNodes.size(); i++){
            Node pathNode = pathNodes.get(i);
            int factorValueIndex = getIntAttribute(pathNode, VALUE_INDEX, -1);
            
            if(factorValueIndex < 0)
                continue;
            
            node.pathes.add(new DecisionTreePath(factors[node.factorId].values[factorValueIndex], getIntAttribute(pathNode, NODE_INDEX)));
        }
       
        return node;
	}
}