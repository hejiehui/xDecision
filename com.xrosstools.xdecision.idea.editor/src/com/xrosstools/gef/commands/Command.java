package com.xrosstools.gef.commands;

public abstract class Command implements Runnable {
    @Override
    public void run() {
        execute();
    }

    public boolean canExecute() {
        return true;
    }

    public abstract void execute();

    public abstract String  getLabel();

    public boolean canRedo() {
        return true;
    }

    public void redo() {
        execute();
    }

    public boolean canUndo() {
        return true;
    }

    public abstract void undo();
}
