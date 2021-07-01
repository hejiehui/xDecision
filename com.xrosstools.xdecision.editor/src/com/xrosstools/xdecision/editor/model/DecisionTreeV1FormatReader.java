package com.xrosstools.xdecision.editor.model;

import static com.xrosstools.common.XmlHelper.getValidChildNodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.xrosstools.xdecision.editor.model.expression.VariableExpression;

public class DecisionTreeV1FormatReader {
    public static final String DELIMITER = "|";
    public static final char FACTOR_VALUE_DELIMITER = ':';
    public static final String PATHS = "paths";

    public static boolean isV1Format(Document doc) {
        return doc.getElementsByTagName(PATHS).getLength() > 0;
    }

    public static boolean isV1Format(DecisionTreeModel model) {
        return model.getNodes() == null || model.getPathes() != null;
    }

    public static DecisionTreePath[] createPaths(Document doc) {
        if(doc.getElementsByTagName(PATHS).getLength() == 0)
            return null;

        List<Node> pathNodes = getValidChildNodes(doc.getElementsByTagName(PATHS).item(0));
        DecisionTreePath[] paths = new DecisionTreePath[pathNodes.size()];
        for(int i = 0; i < paths.length; i++){
            paths[i] = getDecisionTreePath(pathNodes.get(i).getTextContent(), DecisionTreeXMLSerializer.getIntAttribute(pathNodes.get(i), DecisionTreeXMLSerializer.INDEX));
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
    
    public static void buildTree(DecisionTreeModel model, DecisionTreeDiagram diagram){
        Map<Integer, DecisionTreeNode> roots = new HashMap<Integer, DecisionTreeNode>();
         for(DecisionTreePath path: model.getPathes()){
             Integer rootFactor = new Integer(path.getPathEntries()[0].getNodeIndex());
             DecisionTreeNode parent = null;
             if(!roots.containsKey(rootFactor)){
                 parent = new DecisionTreeNode();
                 diagram.addNode(parent);
                 roots.put(rootFactor, parent);
             }else
                 parent = roots.get(rootFactor);
             
             for(DecisionTreePathEntry entry: path.getPathEntries()){
                 parent.setNodeExpression(new VariableExpression(diagram.getFactorById(entry.getNodeIndex()).getFactorName()));
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
    } 
}
