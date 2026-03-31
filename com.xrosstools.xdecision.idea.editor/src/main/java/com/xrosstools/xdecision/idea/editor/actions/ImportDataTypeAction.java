package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.commands.definition.CreateUserDefineidTypeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;

public class ImportDataTypeAction extends Action implements DecisionTreeActionConstants, DecisionTreeMessages{
    private Project project;
    private DecisionTreeDiagram diagram;

    public ImportDataTypeAction(Project project) {
        setText(IMPORT_NEW_TEMPLATE_MSG);
        this.project = project;
    }

    public void setDiagram(DecisionTreeDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public Command createCommand() {
        TreeClassChooser chooser = TreeClassChooserFactory.getInstance(project).createAllProjectScopeChooser("");
        chooser.showDialog();
        PsiClass selected = chooser.getSelected();
        if(selected == null)
            return null;

        return new CreateUserDefineidTypeCommand(diagram,selected);
    }
}
