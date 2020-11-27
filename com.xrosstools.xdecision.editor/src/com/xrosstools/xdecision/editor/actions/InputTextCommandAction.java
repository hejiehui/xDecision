package com.xrosstools.xdecision.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xdecision.editor.commands.InputTextCommand;

public class InputTextCommandAction extends BaseDialogAction {
    private InputTextCommand command;
    public InputTextCommandAction(IWorkbenchPart part, String dialogTitle, String dialogMessage, String initialValue, InputTextCommand command) {
        super(part, dialogTitle, dialogMessage, initialValue);
        this.command = command;
    }

    @Override
    protected Command createCommand(String value) {
        command.setInputText(value);
        return command;
    } 
}
