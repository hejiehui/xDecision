package com.xrosstools.xdecision.idea.editor.treeparts.definition;

public class NamedTypeTreePart extends NamedElementTreePart {
    public NamedTypeTreePart(Object model) {
        super(model);
     }

    protected String getText() {
        return getModel().toString();
    }
}