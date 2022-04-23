package com.xrosstools.xdecision.editor.model;

import static com.xrosstools.common.XmlHelper.getValidChildNodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.draw2d.geometry.Dimension;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.xrosstools.xdecision.editor.model.definition.DataType;
import com.xrosstools.xdecision.editor.model.definition.EnumType;
import com.xrosstools.xdecision.editor.model.definition.EnumValue;
import com.xrosstools.xdecision.editor.model.definition.FieldDefinition;
import com.xrosstools.xdecision.editor.model.definition.MapType;
import com.xrosstools.xdecision.editor.model.definition.MethodDefinition;
import com.xrosstools.xdecision.editor.model.definition.NamedElementContainer;
import com.xrosstools.xdecision.editor.model.definition.NamedType;
import com.xrosstools.xdecision.editor.model.definition.ParameterDefinition;
import com.xrosstools.xdecision.editor.model.definition.TemplateType;
import com.xrosstools.xdecision.editor.model.expression.ExpressionParser;

public class DecisionTreeDiagramFactory {
    private static final String DECISION_TREE = "decision_tree";

    private static final String COMMENTS = "comments";
    private static final String PARSER = "parser";
    private static final String EVALUATOR = "evaluator";
    
    private static final String LAYOUT = "layout";
    private static final String ALIGNMENT = "alignment";

    private static final String FACTORS = "factors";
    private static final String FACTOR = "factor";
    private static final String VALUE = "value";

    private static final String DECISIONS = "decisions";
    private static final String DECISION = "decision";

    private static final String USER_DEFINED_TYPES = "user_defined_types";
    private static final String USER_DEFINED_TYPE = "user_defined_type";
    
    private static final String USER_DEFINED_ENUMS = "user_defined_enums";
    private static final String USER_DEFINED_ENUM = "user_defined_enum";

    private static final String CONSTANTS = "constants";
    private static final String CONSTANT = "constant";

    private static final String NAME = "name";
    private static final String LABEL = "label";
    private static final String TYPE = "type";
    private static final String KEY_TYPE = "key_type";
    private static final String VALUE_TYPE = "value_type";

    private static final String FIELD = "field";

    private static final String METHOD = "method";

    private static final String PARAMETER = "parameter";

    private static final String NODES = "nodes";
    private static final String NODE = "node";
    private static final String DECISION_INDEX = "decision_index";
    private static final String VALUE_INDEX = "value_index";
    private static final String EXPRESSION = "expression";

    private static final String PATH = "path";
    private static final String NODE_INDEX = "node_index";
    private static final String OPERATOR = "operator";

    private static final String ID = "id";
    private static final String INDEX = "index";

    public DecisionTreeDiagram getFromXML(Document doc){
        DecisionTreeDiagram diagram = new DecisionTreeDiagram();
        Node root = doc.getElementsByTagName(DECISION_TREE).item(0);

        diagram.setHorizantal(getIntAttribute(root, LAYOUT, 0) == 0 ? false: true);
        diagram.setAlignment(getFloatAttribute(root, ALIGNMENT, diagram.getAlignment()));

        diagram.setDescription(getNodeValue(doc, COMMENTS, ""));
        diagram.setParserClass(getNodeValue(doc, PARSER, ""));
        diagram.setEvaluatorClass(getNodeValue(doc, EVALUATOR, ""));
        
        diagram.getDecisions().addAll(createDecisions(doc));

        DecisionTreePath[] paths = null;
        if (DecisionTreeV1FormatReader.isV1Format(doc))
            paths = DecisionTreeV1FormatReader.createPaths(doc);
        else {
            createTypes(doc, diagram);
            diagram.getUserDefinedConstants().addAll(createConstants(doc, diagram));
        }

        diagram.getFactorList().addAll(createFactors(doc, diagram));
        
        if(DecisionTreeV1FormatReader.isV1Format(doc))
            DecisionTreeV1FormatReader.buildTree(paths, diagram);
        else
            diagram.getNodes().addAll(createNodes(doc, diagram));

        return diagram;
    }

    private String getNodeValue(Document doc, String nodeName, String defaultValue) {
        if (doc.getElementsByTagName(nodeName).getLength() == 0)
            return "";
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    private void createTypes(Document doc, DecisionTreeDiagram diagram) {
        List<Node> typeNodes = getValidChildNodes(doc.getElementsByTagName(USER_DEFINED_TYPES).item(0));
        List<Node> enumNodes = getValidChildNodes(doc.getElementsByTagName(USER_DEFINED_ENUMS).item(0));

        createTypeTemplate(doc, diagram, typeNodes, diagram.getUserDefinedTypes());
        createTypeTemplate(doc, diagram, enumNodes, diagram.getUserDefinedEnums());

        createTypeMember(diagram, typeNodes, diagram.getUserDefinedTypes());
        createTypeMember(diagram, enumNodes, diagram.getUserDefinedEnums());
    }

    private void createTypeMember(DecisionTreeDiagram diagram, List<Node> typeNodes, NamedElementContainer container) {
        List elements = container.getElements();
        for (int i = 0; i < elements.size(); i++) {
            Node typeNode = typeNodes.get(i);
            DataType type = (DataType)elements.get(i);

            List<Node> valueNodes = getValidChildNodes(typeNode);

            for (int j = 0; j < valueNodes.size(); j++) {
                Node node = valueNodes.get(j);
                if (node.getNodeName().equals(FIELD)) {
                    FieldDefinition field = new FieldDefinition(diagram, "");
                    readType(node, field, diagram);
                    type.getFields().add(field);
                } else if (node.getNodeName().equals(METHOD)) {
                    // Methods
                    MethodDefinition method = new MethodDefinition(diagram, "");
                    readType(node, method, diagram);
                    for (Node paramNode : getValidChildNodes(node)) {
                        ParameterDefinition param = new ParameterDefinition(diagram, "");
                        readType(paramNode, param, diagram);
                        method.getParameters().add(param);
                    }
                    type.getMethods().add(method);
                } else if (node.getNodeName().equals(VALUE) && type instanceof EnumType) {
                    EnumValue enumValue = new EnumValue(getAttribute(node, ID));
                    ((EnumType)type).getValues().add(enumValue);
                }
            }
        }
    }

    private void createTypeTemplate(Document doc, DecisionTreeDiagram diagram, List<Node> enumNodes, NamedElementContainer container) {
        List<EnumType> types = new ArrayList<EnumType>();
        // First init all DataTypes in case they refer to each other
        for (Node enumNode: enumNodes) {
            DataType type = (DataType)container.getElementType().newInstance(diagram, getAttribute(enumNode, NAME));
            type.setLabel(getAttribute(enumNode, LABEL));
            container.add(type);
        }
    }

    private void readType(Node typeNode, NamedType member, DecisionTreeDiagram diagram) {
        member.setName(getAttribute(typeNode, NAME));
        
        DataType type = diagram.findDataType(getAttribute(typeNode, TYPE));
        
        member.setType(type);
        
        if(!(type instanceof TemplateType))
            return;
        
        if(type instanceof MapType)
            ((MapType) type).setKeyType(diagram.findDataType(getAttribute(typeNode, KEY_TYPE)));
        
        ((TemplateType) type).setValueType(diagram.findDataType(getAttribute(typeNode, VALUE_TYPE)));
    }

    private List<DecisionTreeFactor> createFactors(Document doc, DecisionTreeDiagram diagram) {
        List<Node> factorNodes = getValidChildNodes(doc.getElementsByTagName(FACTORS).item(0));

        DecisionTreeFactor[] factors = new DecisionTreeFactor[factorNodes.size()];
        for (int i = 0; i < factors.length; i++) {
            Node factorNode = factorNodes.get(i);
            DecisionTreeFactor factor = new DecisionTreeFactor(diagram, "");

            factor.setFactorName(getAttribute(factorNode, ID));

            factor.setType(diagram.findDataType(getAttribute(factorNode, TYPE)));
            factors[getIntAttribute(factorNode, INDEX)] = factor;

            List<Node> valueNodes = getValidChildNodes(factorNode);
            String[] values = new String[valueNodes.size()];
            factor.setFactorValues(values);
            for (int j = 0; j < values.length; j++) {
                values[j] = valueNodes.get(j).getTextContent();
            }
        }

        return Arrays.asList(factors);
    }

    private List<DecisionTreeNode> createNodes(Document doc, DecisionTreeDiagram diagram) {
        if (doc.getElementsByTagName(NODES).getLength() == 0)
            return null;
        
        ExpressionParser parser = new DecisionTreeManager(diagram).getParser();

        List<Node> nodeNodes = getValidChildNodes(doc.getElementsByTagName(NODES).item(0));

        List<DecisionTreeNode> nodes = new ArrayList<DecisionTreeNode>();
        for (int i = 0; i < nodeNodes.size(); i++) {
            Node nodeNode = nodeNodes.get(i);
            DecisionTreeNode node = new DecisionTreeNode();

            int deciionId = getIntAttribute(nodeNode, DECISION_INDEX, -1);
            if (deciionId > -1)
                node.setDecision(diagram.getDecisions().get(deciionId));

            String rawExpression = getAttribute(nodeNode, EXPRESSION);
            node.setNodeExpression(parser.parseExpression(rawExpression));
            
            node.setSize(new Dimension(diagram.getNodeWidth(), diagram.getNodeHeight()));

            nodes.add(node);
        }

        // Link nodes
        for (int i = 0; i < nodeNodes.size(); i++)
            createPaths(nodeNodes.get(i), nodes, nodes.get(i), parser);

        return nodes;
    }

    private void createPaths(Node docNode, List<DecisionTreeNode> nodes, DecisionTreeNode node, ExpressionParser parser) {
        
        List<Node> pathNodes = getValidChildNodes(docNode);
        for (int i = 0; i < pathNodes.size(); i++) {
            Node pathNode = pathNodes.get(i);

            DecisionTreeNode child = nodes.get(getIntAttribute(pathNode, NODE_INDEX));
            DecisionTreeNodeConnection conn = new DecisionTreeNodeConnection(node, child);
            conn.setValueId(getIntAttribute(pathNode, VALUE_INDEX, -1));
            conn.setOperator(ConditionOperator.locate(getAttribute(pathNode, OPERATOR)));
            conn.parseExpression(parser, getAttribute(pathNode, EXPRESSION));
        }
    }

    public static int getIntAttribute(Node node, String attributeName, int defaultValue) {
        String value = getAttribute(node, attributeName);
        return value == null ? defaultValue : Integer.parseInt(value);
    }

    public static int getIntAttribute(Node node, String attributeName) {
        return Integer.parseInt(getAttribute(node, attributeName));
    }

    public static float getFloatAttribute(Node node, String attributeName, float defaultValue) {
        String value = getAttribute(node, attributeName);
        return value == null ? defaultValue : Float.parseFloat(value);
    }

    public static String getAttribute(Node node, String attributeName) {
        NamedNodeMap map = node.getAttributes();
        for (int i = 0; i < map.getLength(); i++) {
            if (attributeName.equals(map.item(i).getNodeName()))
                return map.item(i).getNodeValue();
        }

        return null;
    }

    private List<DecisionTreeDecision> createDecisions(Document doc) {
        List<Node> decisionNodes = getValidChildNodes(doc.getElementsByTagName(DECISIONS).item(0));
        DecisionTreeDecision[] decisions = new DecisionTreeDecision[decisionNodes.size()];

        for (int i = 0; i < decisions.length; i++) {
            decisions[getIntAttribute(decisionNodes.get(i), INDEX)] = new DecisionTreeDecision(
                    getAttribute(decisionNodes.get(i), ID));
        }

        return Arrays.asList(decisions);
    }

    private List<DecisionTreeConstant> createConstants(Document doc, DecisionTreeDiagram diagram) {
        if (doc.getElementsByTagName(CONSTANTS).getLength() == 0)
            return Collections.emptyList();
        
        List<Node> constantsNodes = getValidChildNodes(doc.getElementsByTagName(CONSTANTS).item(0));
        DecisionTreeConstant[] constants = new DecisionTreeConstant[constantsNodes.size()];

        for (int i = 0; i < constants.length; i++) {
            constants[i] = new DecisionTreeConstant(diagram, getAttribute(constantsNodes.get(i), ID));
            constants[i].setType(diagram.findDataType(getAttribute(constantsNodes.get(i), TYPE)));
            constants[i].setValue(getAttribute(constantsNodes.get(i), VALUE));
        }

        return Arrays.asList(constants);
    }

    public Document convertToXML(DecisionTreeDiagram diagram) {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement(DECISION_TREE);
            doc.appendChild(root);
            root.setAttribute(LAYOUT, String.valueOf(diagram.isHorizantal() ? 1: 0));
            root.setAttribute(ALIGNMENT, String.valueOf(diagram.getAlignment()));

            root.appendChild(createNode(doc, COMMENTS, diagram.getDescription()));
            root.appendChild(createNode(doc, PARSER, diagram.getParserClass()));
            root.appendChild(createNode(doc, EVALUATOR, diagram.getEvaluatorClass()));

            Element typesNode = doc.createElement(USER_DEFINED_TYPES);
            root.appendChild(typesNode);
            writeTypes(doc, typesNode, diagram.getUserDefinedTypes().getElements());

            Element enumsNode = doc.createElement(USER_DEFINED_ENUMS);
            root.appendChild(enumsNode);
            writeEnums(doc, enumsNode, diagram.getUserDefinedEnums().getElements());

            Element factorsNode = doc.createElement(FACTORS);
            root.appendChild(factorsNode);
            writeFactors(doc, factorsNode, diagram.getFactors().getElements());

            Element treeNodes = doc.createElement(NODES);
            root.appendChild(treeNodes);
            writeNodes(doc, treeNodes, diagram.getNodes().toArray(new DecisionTreeNode[0]));

            Element decisionsNode = doc.createElement(DECISIONS);
            root.appendChild(decisionsNode);
            writeDecisions(doc, decisionsNode, diagram.getDecisions().getElements());

            Element constantNode = doc.createElement(CONSTANTS);
            root.appendChild(constantNode);
            writeConstants(doc, constantNode, diagram.getUserDefinedConstants().getElements());

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Element createNode(Document doc, String nodeName, String value) {
        Element node = doc.createElement(nodeName);
        if (value != null)
            node.appendChild(doc.createTextNode(value));
        return node;
    }

    private void writeTypes(Document doc, Element typesNode, List<DataType> types) {
        for (DataType type: types) {
            Element typeNode = doc.createElement(USER_DEFINED_TYPE);
            typeNode.setAttribute(NAME, type.getName());
            typeNode.setAttribute(LABEL, type.getLabel());
            typesNode.appendChild(typeNode);

            writeFields(doc, type, typeNode);

            writeMethods(doc, type, typeNode);
        }
    }

    private void writeEnums(Document doc, Element typesNode, List<EnumType> types) {
        for (EnumType type: types) {
            Element typeNode = doc.createElement(USER_DEFINED_ENUM);
            typeNode.setAttribute(NAME, type.getName());
            typeNode.setAttribute(LABEL, type.getLabel());
            typesNode.appendChild(typeNode);

            writeEnumValues(doc, type, typeNode);
            
            writeFields(doc, type, typeNode);

            writeMethods(doc, type, typeNode);
        }
    }

    private void writeMethods(Document doc, DataType type, Element typeNode) {
        for (MethodDefinition method : type.getMethods().getElements()) {
            Element methodNode = writeType(doc, METHOD, method);
            for (ParameterDefinition param : method.getParameters().getElements())
                methodNode.appendChild(writeType(doc, PARAMETER, param));

            typeNode.appendChild(methodNode);
        }
    }

    private void writeFields(Document doc, DataType type, Element typeNode) {
        for (FieldDefinition field : type.getFields().getElements())
            typeNode.appendChild(writeType(doc, FIELD, field));
    }

    private void writeEnumValues(Document doc, EnumType type, Element typeNode) {
        for (EnumValue value : type.getValues().getElements()) {
            Element valueNode = doc.createElement(VALUE);
            valueNode.setAttribute(ID, value.getName());
            typeNode.appendChild(valueNode);
        }
    }

    private Element writeType(Document doc, String nodeName, NamedType field) {
        Element fieldNode = doc.createElement(nodeName);
        fieldNode.setAttribute(NAME, field.getName());
        // fieldNode.setAttribute(LABEL, field.getLabel());
        
        DataType type = field.getType();
        fieldNode.setAttribute(TYPE, type.getName());
        
        if(!(type instanceof TemplateType))
            return fieldNode;
        
        if(type instanceof MapType)
            fieldNode.setAttribute(KEY_TYPE, ((MapType)type).getKeyType().getName());
        
        fieldNode.setAttribute(VALUE_TYPE, ((TemplateType)type).getValueType().getName());
        
        return fieldNode;
    }

    private void writeFactors(Document doc, Element factorsNode, List<DecisionTreeFactor> factors) {
        int i = 0;
        for (DecisionTreeFactor factor: factors) {
            Element factorNode = doc.createElement(FACTOR);
            factorNode.setAttribute(ID, factor.getFactorName());

            if (factor.getTypeName() != null)
                factorNode.setAttribute(TYPE, factor.getTypeName());

            factorNode.setAttribute(INDEX, String.valueOf(i++));
            factorsNode.appendChild(factorNode);

            String[] values = factor.getFactorValues();
            for (int j = 0; j < values.length; j++) {
                Element valueNode = doc.createElement(VALUE);
                valueNode.appendChild(doc.createTextNode(values[j]));
                factorNode.appendChild(valueNode);
            }
        }
    }

    private void writeNodes(Document doc, Element treeNodes, DecisionTreeNode[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            DecisionTreeNode node = nodes[i];
            Element treeNode = doc.createElement(NODE);

            treeNode.setAttribute(INDEX, String.valueOf(i));

            if (node.getDecisionId() >= 0)
                treeNode.setAttribute(DECISION_INDEX, String.valueOf(node.getDecisionId()));

            if (node.getNodeExpression() != null)
                treeNode.setAttribute(EXPRESSION, node.getNodeExpression().toString());

            writePathes(doc, treeNode, nodes, node);

            treeNodes.appendChild(treeNode);
        }
    }

    private void writePathes(Document doc, Element treeNode, DecisionTreeNode[] nodes, DecisionTreeNode node) {
        for (DecisionTreeNodeConnection conn : node.getOutputs()) {
            Element pathNode = doc.createElement(PATH);
            pathNode.setAttribute(NODE_INDEX, String.valueOf(indexOf(nodes, conn.getChild())));
            pathNode.setAttribute(VALUE_INDEX, String.valueOf(conn.getValueId()));

            if(conn.getOperator() != null)
                pathNode.setAttribute(OPERATOR, String.valueOf(conn.getOperator().getText()));

            if(conn.getExpression() != null)
                pathNode.setAttribute(EXPRESSION, String.valueOf(conn.getExpression()));

            treeNode.appendChild(pathNode);
        }
    }

    private int indexOf(DecisionTreeNode[] nodes, DecisionTreeNode node) {
        for (int i = 0; i < nodes.length; i++)
            if (nodes[i] == node)
                return i;

        // No such case
        return -1;
    }

    private void writeDecisions(Document doc, Element decisionsNode, List<DecisionTreeDecision> decisions) {
        int i = 0;
        for (DecisionTreeDecision decision: decisions) {
            Element decisionNode = doc.createElement(DECISION);
            decisionNode.setAttribute(ID, decision.getName());
            decisionNode.setAttribute(INDEX, String.valueOf(i++));
            decisionsNode.appendChild(decisionNode);
        }
    }    

    private void writeConstants(Document doc, Element constantsNode, List<DecisionTreeConstant> constants) {
        for (DecisionTreeConstant constant: constants) {
            Element constantNode = doc.createElement(CONSTANT);
            constantNode.setAttribute(ID, constant.getName());
            constantNode.setAttribute(TYPE, constant.getTypeName());
            constantNode.setAttribute(VALUE, constant.getValue());
            constantsNode.appendChild(constantNode);
        }
    }    
}
