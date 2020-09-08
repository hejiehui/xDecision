package com.xrosstools.gef.parts;

import com.xrosstools.gef.figures.Figure;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EditContext {
    private JComponent contentPane;
    private List<Trinity> contents = new ArrayList<>();

    public EditContext(JComponent contentPane) {
        this.contentPane = contentPane;
    }

    public JComponent getContentPane() {
        return contentPane;
    }

    public void add(EditPart part, Object model) {
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

    public EditPart findEditPart(Object model) {
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
        EditPart  editPart;
        TreeEditPart treeEditPart;
        EditPart getEditPart(){return editPart;}
        TreeEditPart getTreeEditPart(){return treeEditPart;}
    }
}
