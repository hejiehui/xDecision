package com.xrosstools.xdecision.idea.editor;

import com.xrosstools.xdecision.idea.editor.actions.DecisionTreeActionConstants;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeRoot;

import java.util.HashMap;
import java.util.Map;

/**
 * This is actually ICON path factory
 */
public class Activator implements DecisionTreeActionConstants {
	public static final String PLUGIN_ID	= "com.xrosstools.xdecision.editor";	//$NON-NLS-1$
	private static Activator plugin;
	public static final String HOME = "/icons/";
	public static final String ICO = ".png";
	
	public static final String TREE = "tree";
	public static final String NODE = "node";
	public static final String CONNECTION = "connection";

	public static String getIconPath(String iconId) {
		return HOME + iconId + ICO;
	}

    public static String getIconPath(Class clazz) {
        String iconId = reg.get(clazz);
        return HOME + iconId + ICO;
    }

    private static Map<Class, String> reg = new HashMap<>();

	static {
        reg.put(DecisionTreeDiagram.class, TREE);
        reg.put(DecisionTreeRoot.class, TREE);
		reg.put(DecisionTreeNode.class, NODE);
        reg.put(DecisionTreeNodeConnection.class, CONNECTION);
    }
}
