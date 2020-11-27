package com.xrosstools.xdecision.editor.commands;

import org.eclipse.gef.commands.Command;

public abstract class InputTextCommand extends Command {
    private String inputText;

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }
}
