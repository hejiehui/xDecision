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
        if(listener != null) listener.postExecute();
    }

    public void _undo() {
        if(!canUndo())
            return;
        undo();
        if(listener != null) listener.postUndo();
    }

    public void _redo() {
        if(!canRedo())
            return;
        redo();
        if(listener != null) listener.postRedo();
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
