package com.xrosstools.xdecision.idea.editor.actions;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xdecision.idea.editor.commands.definition.CreateUserDefineidTypeCommand;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.definition.NamedElementTypeEnum;

public class ImportDataTypeAction extends Action implements DecisionTreeActionConstants, DecisionTreeMessages{
    private Project project;
    private DecisionTreeDiagram diagram;
    private NamedElementTypeEnum typeEnum;

    public ImportDataTypeAction(Project project, DecisionTreeDiagram diagram, NamedElementTypeEnum typeEnum) {
        setText(String.format(IMPORT_NEW_TEMPLATE_MSG, typeEnum.getTypeName()));
        this.project = project;
        this.diagram = diagram;
        this.typeEnum = typeEnum;
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
