package com.xrosstools.xdecision.idea.editor;

import com.xrosstools.idea.gef.DefaultNewModelFileAction;

public class NewDecisionTreeAction extends DefaultNewModelFileAction {
    public NewDecisionTreeAction() {
        super("Xross Decision Tree", XdecisionFileType.EXTENSION, "tree", "new_xdecision_file", "/templates/template.xdecision");
    }
}
