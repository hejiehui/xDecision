package com.xrosstools.gef.parts;

import com.intellij.openapi.util.IconLoader;
import com.xrosstools.xdecision.idea.editor.Activator;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class TreeEditPart extends AbstractEditPart {
    private DefaultMutableTreeNode treeNode;
    private List<TreeEditPart> childEditParts = new ArrayList<>();

    public TreeEditPart(Object model){
        setModel(model);
    }

    public List getChildren() {
        return childEditParts;
    }

    @Override
    protected void addChildPartVisual(EditPart childEditPart, int index) {
        treeNode.add(((TreeEditPart)childEditPart).build());
    }

    @Override
    protected void removeChildVisual(EditPart childEditPart) {
        treeNode.remove(((TreeEditPart)childEditPart).treeNode);
    }

    public DefaultMutableTreeNode getTreeNode() {
        return treeNode;
    }

    public final DefaultMutableTreeNode build() {
        treeNode = new DefaultMutableTreeNode(this);
        List children = getModelChildren();
        for (int i = 0; i < children.size(); i++) {
            addChildModel(childEditParts, children.get(i), i);
        }

        return treeNode;
    }

    public String getText() {
        return "";
    }

    public Icon getImage() {
        return IconLoader.findIcon(Activator.getIconPath(getModel().getClass()));
    }

    public void refreshVisuals() {
    }

    private void refreshChildren() {
        refreshModelPart(getChildren(), getModelChildren());
    }
    public void refresh() {
        refreshVisuals();
        refreshChildren();
    }

    public final TreeEditPart findEditPart(Object model) {
        return getContext().findTreeEditPart(model);
    }
}
