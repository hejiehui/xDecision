package com.xrosstools.gef.parts;

import com.xrosstools.gef.EditorPanel;
import com.xrosstools.gef.figures.Figure;

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
        if(trinity.treeEditPart != null)
            ;
        trinity.treeEditPart = part;
    }

    public GraphicalEditPart findEditPart(Object model) {
        return findContent(model).getEditPart();
    }

    public TreeEditPart findTreeEditPart(Object model) {
        return findContent(model).getTreeEditPart();
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
