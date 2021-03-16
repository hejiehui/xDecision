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
	public static final String DECISION_TREE = "decision_tree";
	
	public static final String COMMENTS = "comments";
    public static final String PARSER = "parser";
    public static final String EVALUATOR = "evaluator";

	public static final String FACTORS = "factors";
	public static final String FACTOR = "factor";
	public static final String VALUE = "value";
		
	public static final String DECISIONS = "decisions";
	public static final String DECISION = "decision";
	
	public static final String USER_DEFINED_TYPES = "user_defined_types";
	public static final String USER_DEFINED_TYPE = "user_defined_type";
	
    public static final String NAME = "name";
    public static final String LABEL = "label";
    public static final String TYPE = "type";
    public static final String TYPE_NAME = "type_name";
    
    public static final String FIELDS = "fields";
    public static final String FIELD = "field";

    public static final String METHODS = "methods";
    public static final String METHOD = "method";
    
    public static final String PARAMETERS = "parameters";
    public static final String PARAMETER = "parameter";
    
    public static final String NODES = "nodes";
	public static final String NODE = "node";
	public static final String FACTOR_INDEX = "factor_index";
	public static final String DECISION_INDEX = "decision_index";
	public static final String FACTOR_FIELD = "factor_field";
	public static final String FUNCTION_NAME = "function_name";
	public static final String VALUE_INDEX = "value_index";
	public static final String EXPRESSION = "expression";

	public static final String PATH = "path";
	public static final String NODE_INDEX = "node_index";
	
	public static final String ID = "id";
	public static final String INDEX = "index";

	
	public static final String TOTAL_FACTOR_NUMBER = "total_factor_num";
	public static final String TOTAL_DECISION_NUMBER = "total_decision_num";
	public static final String TOTAL_PATH_NUMBER = "total_path_num";
	
	public static final String _DECISION = "_decision";

	
	public DecisionTreeModel readMode(Document doc) {
		DecisionTreeModel model = new DecisionTreeModel();
		model.setComments(getNodeValue(doc, COMMENTS, ""));
		model.setParserClass(getNodeValue(doc, PARSER, ""));
		model.setEvaluatorClass(getNodeValue(doc, EVALUATOR, ""));
		model.setFactors(createFactors(doc));
		model.setDecisions(createDecisions(doc));
		
		if(DecisionTreeV1FormatReader.isV1Format(doc))
		    model.setPathes(DecisionTreeV1FormatReader.createPaths(doc));
		else {
		    model.setTypes(createTypes(doc));
		    model.setNodes(createNodes(doc));
		}
		
		return model;
	}
	
	private String getNodeValue(Document doc, String nodeName, String defaultValue){
		if(doc.getElementsByTagName(nodeName).getLength() == 0)
			return "";
		return doc.getElementsByTagName(nodeName).item(0).getTextContent();
	}

	private UserDefinedType[] createTypes(Document doc) {
	    if(doc.getElementsByTagName(USER_DEFINED_TYPES).item(0) == null)
	        return new UserDefinedType[0];

	    List<Node> typeNodes = getValidChildNodes(doc.getElementsByTagName(USER_DEFINED_TYPES).item(0));
        
        UserDefinedType[] types = new UserDefinedType[typeNodes.size()];
        for(int i = 0; i < types.length; i++){
            Node typeNode = typeNodes.get(i);
            UserDefinedType type = new UserDefinedType();
            
            type.setName(getAttribute(typeNode, NAME));
            type.setLabel(getAttribute(typeNode, LABEL));
            types[getIntAttribute(typeNode, INDEX)] = type;
            
            List<Node> valueNodes = getValidChildNodes(typeNode);
            List<FieldDefinition> fields = new ArrayList<FieldDefinition>();
            type.setFields(fields);
            List<MethodDefinition> methods = new ArrayList<MethodDefinition>();
            type.setMethods(methods);
            
            for(int j = 0; j < valueNodes.size(); j++){
                Node node = valueNodes.get(j);
                if(node.getNodeName().equals(FIELD)) {
                    FieldDefinition field = new FieldDefinition();
                    readType(node, field);
                    fields.add(field);
                }else {
                    //Methods
                    MethodDefinition method = new MethodDefinition();
                    readType(node, method);
                    for(Node paramNode: getValidChildNodes(node)) {
                        FieldDefinition param = new FieldDefinition();
                        readType(node, param);
                        method.getParameters().add(param);
                    }
                    
                    methods.add(method);                    
                }
            }
        }
        
        return types;    
	}

    private void readType(Node typeNode, FieldDefinition field) {
        field.setName(getAttribute(typeNode, NAME));
        field.setLabel(getAttribute(typeNode, LABEL));

        DataTypeEnum factorType = null;
        String userDefinedType = null;
        if(getAttribute(typeNode, TYPE) != null)
            factorType = DataTypeEnum.valueOf(getAttribute(typeNode, TYPE));

        if(factorType == DataTypeEnum.USER_DEFINED && getAttribute(typeNode, TYPE_NAME) != null)
            userDefinedType = getAttribute(typeNode, TYPE_NAME);
        
        DataType dataType = userDefinedType == null ? new DataType(factorType):new DataType(userDefinedType);
        field.setType(dataType);
    }

	private DecisionTreeFactor[] createFactors(Document doc) {
		List<Node> factorNodes = getValidChildNodes(doc.getElementsByTagName(FACTORS).item(0));
		
		DecisionTreeFactor[] factors = new DecisionTreeFactor[factorNodes.size()];
		for(int i = 0; i < factors.length; i++){
			Node factorNode = factorNodes.get(i);
			DecisionTreeFactor factor = new DecisionTreeFactor();
			factor.setType(new DataType(DataTypeEnum.STRING));
			
			factor.setFactorName(getAttribute(factorNode, ID));
			
            DataTypeEnum factorType = null;
            String userDefinedType = null;
            if(getAttribute(factorNode, TYPE) != null)
                factorType = DataTypeEnum.valueOf(getAttribute(factorNode, TYPE));

            if(factorType == DataTypeEnum.USER_DEFINED && getAttribute(factorNode, TYPE_NAME) != null)
                userDefinedType = getAttribute(factorNode, TYPE_NAME);
            
            DataType dataType = userDefinedType == null ? new DataType(factorType):new DataType(userDefinedType);
            factor.setType(dataType);

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

            node.setFactorId(getIntAttribute(nodeNode, FACTOR_INDEX, -1));
            node.setFactorField(getAttribute(nodeNode, FACTOR_FIELD));
            node.setFunctionName(getAttribute(nodeNode, FUNCTION_NAME));

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
        UserDefinedType[] types = model.getTypes();
        for(int i = 0; i < types.length; i++){
            UserDefinedType type = types[i];
            Element typeNode = (Element)doc.createElement(USER_DEFINED_TYPE);
            typeNode.setAttribute(NAME, type.getName());
            typeNode.setAttribute(LABEL, type.getLabel());
            typeNode.setAttribute(INDEX, String.valueOf(i));
            typesNode.appendChild(typeNode);
            
            for(FieldDefinition field: type.getFields())
                typeNode.appendChild(writeType(doc, FIELD, field));
            
            for(MethodDefinition method: type.getMethods()) {
                Element methodNode = writeType(doc, METHOD, method);
                for(FieldDefinition param: method.getParameters())
                    methodNode.appendChild(writeType(doc, PARAMETER, param));
                
                typeNode.appendChild(methodNode);
            }
        }
    }

    private Element writeType(Document doc, String nodeName, FieldDefinition field) {
        Element fieldNode = (Element)doc.createElement(nodeName);
        fieldNode.setAttribute(NAME, field.getName());
        fieldNode.setAttribute(LABEL, field.getLabel());
        fieldNode.setAttribute(TYPE, field.getType().getType().toString());
        DataType fieldType = field.getType();
        if(fieldType.getType() == DataTypeEnum.USER_DEFINED)
            fieldNode.setAttribute(TYPE_NAME, fieldType.getCustomizedType());
        return fieldNode;
    }
        
	private void writeFactors(Document doc, Element factorsNode, DecisionTreeModel model){
		DecisionTreeFactor[] factors = model.getFactors();
		for(int i = 0; i < factors.length; i++){
			DecisionTreeFactor factor = factors[i];
			Element factorNode = (Element)doc.createElement(FACTOR);
			factorNode.setAttribute(ID, factor.getFactorName());

			if(factor.getType() != null)
			    factorNode.setAttribute(TYPE, factor.getType().getType().toString());

			if(factor.getType().getType() == DataTypeEnum.USER_DEFINED && factor.getType().getCustomizedType() != null)
			    factorNode.setAttribute(TYPE_NAME, factor.getType().getCustomizedType());

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

            if(node.getFactorId() >= 0)
                treeNode.setAttribute(FACTOR_INDEX, String.valueOf(node.getFactorId()));

            if(node.getDecisionId() >= 0)
                treeNode.setAttribute(DECISION_INDEX, String.valueOf(node.getDecisionId()));

            if(node.getFactorField() != null)
                treeNode.setAttribute(FACTOR_FIELD, node.getFactorField());

            if(node.getFunctionName() != null)
                treeNode.setAttribute(FUNCTION_NAME, node.getFunctionName());
            
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
		String[] decisions = model.getDecisions();
		
		for(int i = 0; i < decisions.length; i++){
			Element decisionNode = (Element)doc.createElement(DECISION);
			decisionNode.setAttribute(ID, decisions[i]);
			decisionNode.setAttribute(INDEX, String.valueOf(i));
			decisionsNode.appendChild(decisionNode);
		}
	}
}