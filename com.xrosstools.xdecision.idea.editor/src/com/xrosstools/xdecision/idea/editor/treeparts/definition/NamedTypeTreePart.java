package com.xrosstools.xdecision.idea.editor.treeparts.definition;

public class NamedTypeTreePart extends NamedElementTreePart {
    public NamedTypeTreePart(Object model) {
        super(model);
     }

    public String getText() {
        return getModel().toString();
    }
}