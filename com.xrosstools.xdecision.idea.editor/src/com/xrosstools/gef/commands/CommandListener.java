package com.xrosstools.gef.commands;

public interface CommandListener {
    void postExecute();
    void postRedo();
    void postUndo();
}
