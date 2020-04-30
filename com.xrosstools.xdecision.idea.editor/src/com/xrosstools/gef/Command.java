package com.xrosstools.gef;

public abstract class Command implements Runnable {
    @Override
    public void run() {
        execute();
    }

    public abstract void execute();

    public abstract String  getLabel();

    public void redo() {
        execute();
    }

    public abstract void undo();
}
