package com.xrosstools.gef.parts;

import com.intellij.openapi.util.IconLoader;
import com.xrosstools.xdecision.idea.editor.Activator;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public class TreeEditPart extends AbstractEditPart {
    private DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(this);
    private List<TreeEditPart> childEditParts = new ArrayList<>();

    public TreeEditPart(Object model){
        setModel(model);
    }

    public List getChildren() {
        return childEditParts;
    }

    @Override
    protected void addChildPartVisual(EditPart childEditPart, int index) {
        treeNode.insert(((TreeEditPart)childEditPart).treeNode, index);
    }

    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        treeNode.remove(((TreeEditPart)childEditPart).treeNode);
    }

    public DefaultMutableTreeNode getTreeNode() {
        return treeNode;
    }

    public String getText() {
        return "";
    }

    public Icon getImage() {
        return IconLoader.findIcon(Activator.getIconPath(getModel().getClass()));
    }

    private void refreshChildren() {
        refreshModelPart(getChildren(), getModelChildren());
    }
    public void refresh() {
        refreshChildren();
    }

    public final TreeEditPart findEditPart(Object model) {
        return getContext().findTreeEditPart(model);
    }
}
