package com.xrosstools.gef.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.gef.commands.Command;
import com.xrosstools.gef.commands.InputTextCommand;

public class InputTextCommandAction extends BaseDialogAction {
    private InputTextCommand command;
    public InputTextCommandAction(Project project, String dialogTitle, String dialogMessage, String initialValue, InputTextCommand command) {
        super(project, dialogTitle, dialogMessage, initialValue);
        this.command = command;
    }

    @Override
    protected Command createCommand(String value) {
        command.setInputText(value);
        return command;
    } 
}
