package com.xrosstools.xdecision.idea.editor.treeparts;

import java.util.List;

import com.xrosstools.gef.parts.TreeEditPart;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeRoot;

public class DecisionTreeDiagramTreePart extends TreeEditPart {
    protected List<DecisionTreeRoot> getModelChildren() {
    	return ((DecisionTreeDiagram)getModel()).getRoots();
    }
}
