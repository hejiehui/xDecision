package com.xrosstools.xdecision.idea.editor;

import com.xrosstools.idea.gef.DefaultNewModelFileAction;

public class NewDecisionTreeAction extends DefaultNewModelFileAction {
    private static final String template = getTemplate(NewDecisionTreeAction.class, "/templates/template.xdecision");

    public NewDecisionTreeAction() {
        super("Xross Decision Tree", XdecisionFileType.EXTENSION, XdecisionsIcons.TREE, "new_xdecision_file", template);
    }
}
