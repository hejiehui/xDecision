package com.xrosstools.gef.commands;

public abstract class Command implements Runnable {
    private CommandListener listener;

    public Command setListener(CommandListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void run() {
        if(!canExecute())
            return;
        execute();
        listener.postExecute();
    }

    public void _undo() {
        if(!canUndo())
            return;
        undo();
        listener.postUndo();
    }

    public void _redo() {
        if(!canRedo())
            return;
        redo();
        listener.postRedo();
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
