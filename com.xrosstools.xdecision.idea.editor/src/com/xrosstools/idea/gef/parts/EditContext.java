package com.xrosstools.idea.gef.parts;

import com.xrosstools.idea.gef.EditorPanel;
import com.xrosstools.idea.gef.figures.Figure;

import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class EditContext {
    private EditorPanel contentPane;

    private List<Trinity> contents = new ArrayList<>();

    public EditContext(EditorPanel contentPane) {
        this.contentPane = contentPane;
    }

    public EditorPanel getContentPane() {
        return contentPane;
    }

    public DefaultTreeModel getTreeModel() {
        return contentPane.getTreeModel();
    }

    public void add(GraphicalEditPart part, Object model) {
        Trinity trinity = findContent(model);

        if(trinity == null) {
            trinity = new Trinity();
            trinity.model = model;
            contents.add(trinity);
        }
        trinity.editPart = part;
    }

    public void add(TreeEditPart part, Object model) {
        Trinity trinity = findContent(model);

        if(trinity == null) {
            trinity = new Trinity();
            trinity.model = model;
            contents.add(trinity);
        }
        trinity.treeEditPart = part;
    }

    public void remove(Object model) {
        Trinity t = findContent(model);

        if(t.editPart == null && t.treeEditPart == null)
            contents.remove(findContent(model));
    }

    public GraphicalEditPart findEditPart(Object model) {
        Trinity t = findContent(model);
        return t == null ? null : t.editPart;
    }

    public TreeEditPart findTreeEditPart(Object model) {
        Trinity t = findContent(model);
        return t == null ? null : t.treeEditPart;
    }

    public Figure findFigure(Object model) {
        Trinity comp = findContent(model);
        if(comp == null)
            return null;

        if(comp.getEditPart() == null)
            return null;

        return comp.getEditPart().getFigure();
    }

    private Trinity findContent(Object model) {
        for(Trinity trinity: contents){
            if(trinity.model == model)
                return trinity;
        }
        return null;
    }

    private class Trinity {
        Object model;
        GraphicalEditPart editPart;
        TreeEditPart treeEditPart;
        GraphicalEditPart getEditPart(){return editPart;}
        TreeEditPart getTreeEditPart(){return treeEditPart;}
    }
}
