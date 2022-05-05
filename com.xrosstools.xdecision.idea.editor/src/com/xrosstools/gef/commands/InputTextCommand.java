package com.xrosstools.gef.commands;

public abstract class InputTextCommand extends Command {
    private String inputText;

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }
}
