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
import com.xrosstools.xdecision.ext.XrossEvaluatorConstants;

public class XDecisionTreeFactory implements XrossEvaluatorConstants {
	private static final String DECISION_TREE = "decision_tree";
	
	private static final String COMMENTS = "comments";
	private static final String PARSER = "parser";
	private static final String EVALUATOR = "evaluator";
	
	private static final String FACTORS = "factors";
	private static final String FACTOR = "factor";
	private static final String VALUE = "value";
		
	private static final String DECISIONS = "decisions";
	private static final String DECISION = "decision";

	private static final String PATHS = "paths";
	private static final String PATH = "path";
	
	private static final String ID = "id";
	private static final String INDEX = "index";

	
    private static final String USER_DEFINED_TYPES = "user_defined_types";
    private static final String USER_DEFINED_TYPE = "user_defined_type";
    
    private static final String NAME = "name";
    private static final String LABEL = "label";
    private static final String TYPE = "type";
    private static final String TYPE_NAME = "type_name";
    
    private static final String FIELDS = "fields";
    private static final String FIELD = "field";
    private static final String FIELD_SEPARATOR = ".";

    private static final String NODES = "nodes";
    private static final String NODE = "node";
    private static final String FACTOR_INDEX = "factor_index";
    private static final String DECISION_INDEX = "decision_index";
    private static final String EXPRESSION = "expression";
    private static final String OPERATOR = "operator";

    private static final String FACTOR_FIELD = "factor_field";
    private static final String FUNCTION_NAME = "function_name";

    private static final String VALUE_INDEX = "value_index";
    private static final String NODE_INDEX = "node_index";
    
	private static final String DELIMITER = "|";
	private static final char FACTOR_VALUE_DELIMITER = ':';
	
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
		            (XDecisionTree<T>)createV2Tree(doc, evaluator, decisions, factors, parser);
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
	
    public <T> XDecisionTree<T> createV2Tree(Document doc, PathEvaluator evaluator, List<T> decisions, FactorDefinition[] factors, XDecisionTreeParser<T> parser) {
        XDecisionTree<T> tree = new XDecisionTree<T>(evaluator);
        
        List<DecisionTreeNode> nodes = new ArrayList<>();
        
        List<Node> nodeNodes = getValidChildNodes(doc.getElementsByTagName(NODES).item(0));
        
        for(int i = 0; i < nodeNodes.size(); i++)
            nodes.add(create(nodeNodes.get(i), factors, parser));
        
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
                entries.add(0, new XDecisionPathEntry(node.parent.nodeExpression, node.factorValue));
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
			
			addFactorValues(parser, factorNode, factor);
		}
		
		return factors;
	}

    /**
     * The following is only for 1.0 or 2.0 model file format
          <factor id="riskLevel" index="1">
               <value>IN 'low', 'middle'</value>
               <value>== 'high'</value>
               <value>=='low'</value>
               <value>IN 'middle', 'high'</value>
          </factor>
     */
    private void addFactorValues(XDecisionTreeParser parser, Node factorNode, FactorDefinition factor) {
        List<Node> valueNodes = getValidChildNodes(factorNode);
        Object[] values = new Object[valueNodes.size()];
        factor.values = values;
        for(int j = 0; j < values.length; j++){
            String pathValue = valueNodes.get(j).getTextContent();
            String operator = identifyOperator(pathValue);

            String expStr = operator == null? pathValue : pathValue.replaceFirst(operator, "").trim();
        	values[j] = parser.parseDecisionPath(factor.factorName, operator, expStr);
        }
    }
	
    private String identifyOperator(String pathValue) {
        for (String[] operators: ALL_OPERATORS) {
            for(String operand: operators)
                if(pathValue.startsWith(operand))
                    return operand;
        }
        return null;
    }
	
	private <T> List<T> createDecisions(Document doc, XDecisionTreeParser<T> parser) {
		List<Node> decisionNodes = getValidChildNodes(doc.getElementsByTagName(DECISIONS).item(0));
		
		List<T> decisions = new ArrayList<>();
		
		for(Node decisionNode: decisionNodes) {
			decisions.add(parser.parseDecision(getAttribute(decisionNode, ID)));
		}

		return decisions;
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
        String nodeExpression;

        DecisionTreeNode parent;
        Object factorValue;
	    
	    List<DecisionTreePath> pathes = new ArrayList<>();
	}
	    
	/**
        <node factor_index="2" index="8">
        <node decision_index="6" index="9"/>
	 */
    private <T> DecisionTreeNode create(Node nodeNode, FactorDefinition[] factors, XDecisionTreeParser<T> parser) {
        DecisionTreeNode node = new DecisionTreeNode();

        node.decisionId = getIntAttribute(nodeNode, DECISION_INDEX, -1);
        
        if(getAttribute(nodeNode, EXPRESSION) != null) {
            createV2_3(nodeNode, node, factors, parser);
        } else {
            createV2_0(nodeNode, node, factors);
        }
        
        return node;
	}
    
    /**
     * For the initial version of expression support, requires FACTOR_INDEX, FACTOR_FIELD or FUNCTION_NAME
     * 
        <node factor_index="2" index="8">
           <path node_index="9" value_index="0"/>
           <path node_index="10" value_index="1"/>
           <path node_index="11" value_index="2"/>
        </node>
        <node decision_index="6" index="9"/>
     */
    private void createV2_0(Node nodeNode, DecisionTreeNode node, FactorDefinition[] factors) {
        int factorId = getIntAttribute(nodeNode, FACTOR_INDEX, -1);
        if(factorId < 0)
            return;
        
        node.factorId = factorId;
        node.nodeExpression = createNodeExpression(nodeNode, factors[factorId]);
        
        List<Node> pathNodes = getValidChildNodes(nodeNode);
        for(int i = 0; i < pathNodes.size(); i++){
            Node pathNode = pathNodes.get(i);
            int factorValueIndex = getIntAttribute(pathNode, VALUE_INDEX, -1);
            
            if(factorValueIndex < 0)
                continue;
            
            node.pathes.add(new DecisionTreePath(factors[node.factorId].values[factorValueIndex], getIntAttribute(pathNode, NODE_INDEX)));
        }
    }

    private String createNodeExpression(Node nodeNode, FactorDefinition factor) {
        StringBuffer displayText = new StringBuffer();

        //The following two are rarely used
        String factorField = getAttribute(nodeNode, FACTOR_FIELD);
        String functionName = getAttribute(nodeNode, FUNCTION_NAME);
        
        displayText.append(factor.factorName);

        if(factorField != null)
            displayText.append(FIELD_SEPARATOR).append(factorField);
    
        if(functionName != null)
            displayText = new StringBuffer(String.format("%s(%s)", functionName, displayText.toString()));

        return displayText.toString();
    }
    
    /**
     * For the complete version of expression support, requires only EXPRESSION.
     */
    private <T> void createV2_3(Node nodeNode, DecisionTreeNode node, FactorDefinition[] factors, XDecisionTreeParser<T> parser) {
        node.nodeExpression = getAttribute(nodeNode, EXPRESSION);
        
        List<Node> pathNodes = getValidChildNodes(nodeNode);
        for(int i = 0; i < pathNodes.size(); i++){
            Node pathNode = pathNodes.get(i);
            String operator = getAttribute(pathNode, OPERATOR);
            String pathExpression =  getAttribute(pathNode, EXPRESSION);
            
            node.pathes.add(new DecisionTreePath(parser.parseDecisionPath(node.nodeExpression, operator, pathExpression), getIntAttribute(pathNode, NODE_INDEX)));
        }
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
}