package com.xrosstools.xdecision.editor.model;

import static com.xrosstools.common.XmlHelper.getValidChildNodes;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DecisionTreeXMLSerializer {
	private static final String DECISION_TREE = "decision_tree";
	
	private static final String COMMENTS = "comments";
    private static final String PARSER = "parser";
    private static final String EVALUATOR = "evaluator";

	private static final String FACTORS = "factors";
	private static final String FACTOR = "factor";
	private static final String VALUE = "value";
		
	private static final String DECISIONS = "decisions";
	private static final String DECISION = "decision";
	
	private static final String USER_DEFINED_TYPES = "user_defined_types";
	private static final String USER_DEFINED_TYPE = "user_defined_type";
	
    private static final String NAME = "name";
    private static final String LABEL = "label";
    private static final String TYPE = "type";
    private static final String TYPE_NAME = "type_name";
    
    private static final String FIELDS = "fields";
    private static final String FIELD = "field";

    private static final String METHODS = "methods";
    private static final String METHOD = "method";
    
    private static final String PARAMETERS = "parameters";
    private static final String PARAMETER = "parameter";
    
    private static final String NODES = "nodes";
	private static final String NODE = "node";
	private static final String FACTOR_INDEX = "factor_index";
	private static final String DECISION_INDEX = "decision_index";
	private static final String FACTOR_FIELD = "factor_field";
	private static final String FUNCTION_NAME = "function_name";
	private static final String VALUE_INDEX = "value_index";
	private static final String EXPRESSION = "expression";

	private static final String PATH = "path";
	private static final String NODE_INDEX = "node_index";
	
	private static final String ID = "id";
	private static final String INDEX = "index";

	
	private static final String TOTAL_FACTOR_NUMBER = "total_factor_num";
	private static final String TOTAL_DECISION_NUMBER = "total_decision_num";
	private static final String TOTAL_PATH_NUMBER = "total_path_num";
	
	private static final String _DECISION = "_decision";

	
	public DecisionTreeModel readMode(Document doc) {
		DecisionTreeModel model = new DecisionTreeModel();
		model.setComments(getNodeValue(doc, COMMENTS, ""));
		model.setParserClass(getNodeValue(doc, PARSER, ""));
		model.setEvaluatorClass(getNodeValue(doc, EVALUATOR, ""));
		
		if(DecisionTreeV1FormatReader.isV1Format(doc))
		    model.setPathes(DecisionTreeV1FormatReader.createPaths(doc));
		else {
		    model.setTypes(createTypes(doc, model));
		    model.setNodes(createNodes(doc));
		}
		
        model.setFactors(createFactors(doc, model));
        model.setDecisions(createDecisions(doc));

        return model;
	}
	
	private String getNodeValue(Document doc, String nodeName, String defaultValue){
		if(doc.getElementsByTagName(nodeName).getLength() == 0)
			return "";
		return doc.getElementsByTagName(nodeName).item(0).getTextContent();
	}

	private DataType[] createTypes(Document doc, DecisionTreeModel model) {
	    if(doc.getElementsByTagName(USER_DEFINED_TYPES).item(0) == null)
	        return new DataType[0];

	    List<Node> typeNodes = getValidChildNodes(doc.getElementsByTagName(USER_DEFINED_TYPES).item(0));
        
	    DataType[] types = new DataType[typeNodes.size()];
	    //First init all DataTypes in case they refer to each other
        for(int i = 0; i < types.length; i++){
            Node typeNode = typeNodes.get(i);
            DataType type = new DataType(getAttribute(typeNode, NAME));
            
            type.setLabel(getAttribute(typeNode, LABEL));
            types[getIntAttribute(typeNode, INDEX)] = type;
        }

        for(int i = 0; i < types.length; i++){
            Node typeNode = typeNodes.get(i);
            DataType type = types[getIntAttribute(typeNode, INDEX)];

            List<Node> valueNodes = getValidChildNodes(typeNode);
            
            for(int j = 0; j < valueNodes.size(); j++){
                Node node = valueNodes.get(j);
                if(node.getNodeName().equals(FIELD)) {
                    FieldDefinition field = new FieldDefinition();
                    readType(node, field, types);
                    type.getFields().add(field);
                }else {
                    //Methods
                    MethodDefinition method = new MethodDefinition();
                    readType(node, method, types);
                    for(Node paramNode: getValidChildNodes(node)) {
                        FieldDefinition param = new FieldDefinition();
                        readType(paramNode, param, types);
                        method.getParameters().add(param);
                    }
                    
                    type.getMethods().add(method);                    
                }
            }
        }
        
        return types;    
	}

    private void readType(Node typeNode, NamedType field, DataType[] types) {
        field.setName(getAttribute(typeNode, NAME));
        //remove lable for now
//        field.setLabel(getAttribute(typeNode, LABEL));
        field.setType(findType(types, getAttribute(typeNode, TYPE)));
    }
    
    public DataType findType(DataType[] types, String typeName) {
        for(DataType type: DataType.PREDEFINED_TYPES)
            if(type.getName().equals(typeName))
                return type;
        
        for(DataType type: types)
            if(type.getName().equals(typeName))
                return type;

        //TODO We should record error here,maybe popup an alert
        return DataType.STRING_TYPE;
    }

    
	private DecisionTreeFactor[] createFactors(Document doc, DecisionTreeModel model) {
		List<Node> factorNodes = getValidChildNodes(doc.getElementsByTagName(FACTORS).item(0));
		
		DecisionTreeFactor[] factors = new DecisionTreeFactor[factorNodes.size()];
		for(int i = 0; i < factors.length; i++){
			Node factorNode = factorNodes.get(i);
			DecisionTreeFactor factor = new DecisionTreeFactor();
			
			factor.setFactorName(getAttribute(factorNode, ID));

            factor.setType(findType(model.getTypes(), getAttribute(factorNode, TYPE)));
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
	
    private DecisionTreeNode[] createNodes(Document doc) {
        if(doc.getElementsByTagName(NODES).getLength() == 0)
            return null;

        List<Node> nodeNodes = getValidChildNodes(doc.getElementsByTagName(NODES).item(0));
        
        DecisionTreeNode[] nodes = new DecisionTreeNode[nodeNodes.size()];
        for(int i = 0; i < nodes.length; i++){
            Node nodeNode = nodeNodes.get(i);
            DecisionTreeNode node = new DecisionTreeNode();
            
            node.setDecisionId(getIntAttribute(nodeNode, DECISION_INDEX, -1));
            node.setRawExpression(getAttribute(nodeNode, EXPRESSION));

            nodes[i] = node;
        }
        
        //Link nodes
        for(int i = 0; i < nodes.length; i++)
            createPaths(nodeNodes.get(i), nodes, nodes[i]);
        
        return nodes;
    }
    
    private void createPaths(Node docNode, DecisionTreeNode[] nodes, DecisionTreeNode node) {
        List<Node> pathNodes = getValidChildNodes(docNode);
        for(int i = 0; i < pathNodes.size(); i++){
            Node pathNode = pathNodes.get(i);
            
            DecisionTreeNode child = nodes[getIntAttribute(pathNode, NODE_INDEX)];
            DecisionTreeNodeConnection conn = new DecisionTreeNodeConnection(node, child);
            conn.setValueId(getIntAttribute(pathNode, VALUE_INDEX, -1));
        }
    }
    
    public static int getIntAttribute(Node node, String attributeName, int defaultValue){
        String value = getAttribute(node, attributeName);
        return value == null ? defaultValue : Integer.parseInt(value); 
    }

    public static int getIntAttribute(Node node, String attributeName){
		return Integer.parseInt(getAttribute(node, attributeName));
	}
	
    public static String getAttribute(Node node, String attributeName){
		NamedNodeMap map = node.getAttributes();
		for(int i = 0; i < map.getLength(); i++){
			if(attributeName.equals(map.item(i).getNodeName()))
				return map.item(i).getNodeValue();
		}
		
		return null;
	}
	
	private DecisionTreeDecision[] createDecisions(Document doc) {
		List<Node> decisionNodes = getValidChildNodes(doc.getElementsByTagName(DECISIONS).item(0));
		DecisionTreeDecision[] decisions = new DecisionTreeDecision[decisionNodes.size()];
		
		for(int i = 0; i < decisions.length; i++){
			decisions[getIntAttribute(decisionNodes.get(i), INDEX)] = new DecisionTreeDecision(getAttribute(decisionNodes.get(i), ID));
		}

		return decisions;
	}

	public Document writeModel(DecisionTreeModel model){
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = doc.createElement(DECISION_TREE);
			doc.appendChild(root);
			root.appendChild(createNode(doc, COMMENTS, model.getComments()));
			root.appendChild(createNode(doc, PARSER, model.getParserClass()));
			root.appendChild(createNode(doc, EVALUATOR, model.getEvaluatorClass()));
			
            Element typesNode = (Element)doc.createElement(USER_DEFINED_TYPES);
            root.appendChild(typesNode);
            writeTypes(doc, typesNode, model);

            Element factorsNode = (Element)doc.createElement(FACTORS);
			root.appendChild(factorsNode);
			writeFactors(doc, factorsNode, model);
			
            Element treeNodes = (Element)doc.createElement(NODES);
            root.appendChild(treeNodes);
            writeNodes(doc, treeNodes, model);

			Element decisionsNode = (Element)doc.createElement(DECISIONS);
			root.appendChild(decisionsNode);
			writeDecisions(doc, decisionsNode, model);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Element createNode(Document doc, String nodeName, String value){
		Element node = (Element)doc.createElement(nodeName);
		if(value != null)
		    node.appendChild(doc.createTextNode(value));
		return node;
	}

    private void writeTypes(Document doc, Element typesNode, DecisionTreeModel model){
        DataType[] types = model.getTypes();
        for(int i = 0; i < types.length; i++){
            DataType type = types[i];
            Element typeNode = (Element)doc.createElement(USER_DEFINED_TYPE);
            typeNode.setAttribute(NAME, type.getName());
            typeNode.setAttribute(LABEL, type.getLabel());
            typeNode.setAttribute(INDEX, String.valueOf(i));
            typesNode.appendChild(typeNode);
            
            for(FieldDefinition field: type.getFields().getElements())
                typeNode.appendChild(writeType(doc, FIELD, field));
            
            for(MethodDefinition method: type.getMethods().getElements()) {
                Element methodNode = writeType(doc, METHOD, method);
                for(FieldDefinition param: method.getParameters().getElements())
                    methodNode.appendChild(writeType(doc, PARAMETER, param));
                
                typeNode.appendChild(methodNode);
            }
        }
    }

    private Element writeType(Document doc, String nodeName, NamedType field) {
        Element fieldNode = (Element)doc.createElement(nodeName);
        fieldNode.setAttribute(NAME, field.getName());
//        fieldNode.setAttribute(LABEL, field.getLabel());
        fieldNode.setAttribute(TYPE, field.getTypeName());
        return fieldNode;
    }
        
	private void writeFactors(Document doc, Element factorsNode, DecisionTreeModel model){
		DecisionTreeFactor[] factors = model.getFactors();
		for(int i = 0; i < factors.length; i++){
			DecisionTreeFactor factor = factors[i];
			Element factorNode = (Element)doc.createElement(FACTOR);
			factorNode.setAttribute(ID, factor.getFactorName());

			if(factor.getTypeName() != null)
			    factorNode.setAttribute(TYPE, factor.getTypeName());

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
	
    private void writeNodes(Document doc, Element treeNodes, DecisionTreeModel model){
        DecisionTreeNode[] nodes = model.getNodes();
        for(int i = 0; i < nodes.length; i++){
            DecisionTreeNode node = nodes[i];
            Element treeNode = doc.createElement(NODE);
            
            treeNode.setAttribute(INDEX, String.valueOf(i));

            if(node.getDecisionId() >= 0)
                treeNode.setAttribute(DECISION_INDEX, String.valueOf(node.getDecisionId()));

            if(node.getNodeExpression() != null)
                treeNode.setAttribute(EXPRESSION, node.getNodeExpression().toString());
            
            writePathes(doc, treeNode, nodes, node);

            treeNodes.appendChild(treeNode);
        }
    }
    
	private void writePathes(Document doc, Element treeNode, DecisionTreeNode[] nodes, DecisionTreeNode node){
		for(DecisionTreeNodeConnection conn: node.getOutputs()){
			Element pathNode = (Element)doc.createElement(PATH);
			pathNode.setAttribute(NODE_INDEX, String.valueOf(indexOf(nodes, conn.getChild())));
			pathNode.setAttribute(VALUE_INDEX, String.valueOf(conn.getValueId()));
			treeNode.appendChild(pathNode);
		}
	}
	
	private int indexOf(DecisionTreeNode[] nodes, DecisionTreeNode node) {
	    for(int i = 0; i < nodes.length; i++)
	        if(nodes[i] == node)
	            return i;

	    // No such case
	    return -1;
	}

	private void writeDecisions(Document doc, Element decisionsNode, DecisionTreeModel model){
		DecisionTreeDecision[] decisions = model.getDecisions();
		
		for(int i = 0; i < decisions.length; i++){
			Element decisionNode = (Element)doc.createElement(DECISION);
			decisionNode.setAttribute(ID, decisions[i].getName());
			decisionNode.setAttribute(INDEX, String.valueOf(i));
			decisionsNode.appendChild(decisionNode);
		}
	}
}