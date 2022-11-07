package com.xrosstools.xdecision.editor.model;

import static com.xrosstools.common.XmlHelper.getValidChildNodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.draw2d.geometry.Dimension;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.xrosstools.xdecision.editor.model.expression.ExpressionParser;
import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class DecisionTreeV1FormatReader {
    private static final String INDEX = "index";

    public static final String DELIMITER = "|";
    public static final char FACTOR_VALUE_DELIMITER = ':';
    public static final String PATHS = "paths";
    
    public static final String VALUE = "value";

    public static boolean isV1Format(Document doc) {
        return doc.getElementsByTagName(PATHS).getLength() > 0;
    }

    /**
      <paths>
          <path index="0">0:0|1:0</path>
           <path index="10">0:3|1:1|5:1|6:1</path>
     </paths>
     */
    
    public static DecisionTreePath[] createPaths(Document doc) {
        if(doc.getElementsByTagName(PATHS).getLength() == 0)
            return null;

        List<Node> pathNodes = getValidChildNodes(doc.getElementsByTagName(PATHS).item(0));
        DecisionTreePath[] paths = new DecisionTreePath[pathNodes.size()];
        for(int i = 0; i < paths.length; i++){
            paths[i] = getDecisionTreePath(pathNodes.get(i).getTextContent(), DecisionTreeDiagramFactory.getIntAttribute(pathNodes.get(i), INDEX));
        }
        return paths;
    }
    
    /**
     * @param value looks like 1:2|2:1|..., which means factor 1, value 2
     * @return
     */
    private static DecisionTreePath getDecisionTreePath(String value, int decisionId){
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
    
    /**
     * The following is only for 1.0 or 2.0 model file format
          <factor id="riskLevel" index="1">
               <value>IN 'low', 'middle'</value>
               <value>== 'high'</value>
               <value>=='low'</value>
               <value>IN 'middle', 'high'</value>
          </factor>
     */
    public static void addFactorValues(Node factorNode, Map<Integer, String[]> factorValuesMap) {
        List<Node> valueNodes = getValidChildNodes(factorNode);
        String[] values = new String[valueNodes.size()];
        for(int j = 0; j < values.length; j++){
            values[j] = valueNodes.get(j).getTextContent();
        }
        factorValuesMap.put(DecisionTreeDiagramFactory.getIntAttribute(factorNode, INDEX), values);
    }    
    
    public static void buildTree(ExpressionParser parser, DecisionTreePath[] paths, DecisionTreeDiagram diagram, Map<Integer, String[]> factorValuesMap){
        Map<Integer, DecisionTreeNode> roots = new HashMap<Integer, DecisionTreeNode>();
         for(DecisionTreePath path: paths){
             Integer rootFactor = new Integer(path.getPathEntries()[0].getNodeIndex());
             DecisionTreeNode parent = null;
             if(!roots.containsKey(rootFactor)){
                 parent = new DecisionTreeNode();
                 parent.setSize(new Dimension(diagram.getNodeWidth(), diagram.getNodeHeight()));
                 diagram.addNode(parent);
                 roots.put(rootFactor, parent);
             }else
                 parent = roots.get(rootFactor);
             
             for(DecisionTreePathEntry entry: path.getPathEntries()){
                 String pathValue = factorValuesMap.get(entry.getNodeIndex())[entry.getValueIndex()];
                 parent.setNodeExpression(parser.parseExpression(diagram.getFactorById(entry.getNodeIndex()).getFactorName()));
                 DecisionTreeNode child = null;
                 for(DecisionTreeNodeConnection conn: parent.getOutputs()){
                     if(conn.getValueId() != entry.getValueIndex())
                         continue;
                     
                     child = conn.getChild();
                     break;
                 }
                 
                 if(child == null){
                     child = new DecisionTreeNode();
                     child.setSize(new Dimension(diagram.getNodeWidth(), diagram.getNodeHeight()));
                     diagram.addNode(child);
                     DecisionTreeNodeConnection conn = new DecisionTreeNodeConnection(parent, child);
                     conn.setValueId(entry.getValueIndex());
                     populateOperatorAndExpression(parser, conn, pathValue);
                 }
                 
                 parent = child;
             }
             
             parent.setDecision(diagram.getDecisions().get(path.getDecisionIndex()));
         }
    } 
    

    public static void populateOperatorAndExpression(ExpressionParser parser, DecisionTreeNodeConnection conn, String pathValue){
        if(pathValue == null) {
            conn.setOperator(ConditionOperator.EQUAL);
            conn.setExpression(parser.parseExpression(""));
            return ;
        }

        ConditionOperator foundOperator = null;
        if(pathValue.startsWith(ConditionOperator.GREATER_THAN_EQUAL.getText()) || pathValue.startsWith(ConditionOperator.LESS_THAN_EQUAL.getText())) {
            foundOperator = pathValue.startsWith(ConditionOperator.GREATER_THAN_EQUAL.getText()) ? ConditionOperator.GREATER_THAN_EQUAL : ConditionOperator.LESS_THAN_EQUAL;
        } else {
            for (ConditionOperator operator: ConditionOperator.values()) {
                if(pathValue.startsWith(operator.getText())) {
                    foundOperator = operator;
                    break;
                }
            }
        }

        String expStr = null;
        if(foundOperator == null) {
            foundOperator = ConditionOperator.EQUAL;
            expStr = String.format("'%s'", pathValue);
        }else {
            expStr = foundOperator == null? pathValue : pathValue.replaceFirst(foundOperator.getText(), "").trim();
        }

        conn.setOperator(foundOperator);
        conn.setExpression(parser.parseExpression(expStr));
    }
}
