package com.xrosstools.gef.parts;

import com.intellij.openapi.util.IconLoader;
import com.xrosstools.xdecision.idea.editor.Activator;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class TreeEditPart implements PropertyChangeListener {
    private TreeEditPartFactory factory;
    private TreeEditPart parent;
    private EditContext editContext;
    private Object model;
    private DefaultMutableTreeNode treeNode;
    private List<TreeEditPart> childEditParts = new ArrayList<>();

    public TreeEditPart(){}

    public TreeEditPart(Object model){
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public DefaultMutableTreeNode getTreeNode() {
        return treeNode;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    protected List getModelChildren() {
        return new ArrayList<>();
    }

    public final DefaultMutableTreeNode build() {
        treeNode = new DefaultMutableTreeNode(this);
        List children = getModelChildren();
        for (int i = 0; i < children.size(); i++) {
            TreeEditPart childEditPart = factory.createEditPart(this, children.get(i));
            childEditParts.add(childEditPart);
            treeNode.add(childEditPart.build());
        }

        return treeNode;
    }

    protected String getText() {
        return "";
    }

    public Icon getImage() {
        return IconLoader.findIcon(Activator.getIconPath(getModel().getClass()));
    }

    public void refresh() {

    }

    public TreeEditPart getRoot() {
        TreeEditPart part = this;
        while(part.parent != null) {
            part = part.parent;
        }

        return part;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }

    public EditContext getContext() {
        return editContext;
    }

    public final TreeEditPart findEditPart(Object model) {
        return editContext.findTreeEditPart(model);
    }

    public void setContext(EditContext editContext) {
        this.editContext = editContext;
    }

    public TreeEditPart getParent() {
        return parent;
    }

    public void setParent(TreeEditPart parent) {
        this.parent = parent;
    }

    public void setEditPartFactory(TreeEditPartFactory factory) {
        this.factory = factory;
    }
}
