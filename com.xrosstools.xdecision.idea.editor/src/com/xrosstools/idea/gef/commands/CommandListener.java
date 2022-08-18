package com.xrosstools.idea.gef.commands;

public interface CommandListener {
    void postExecute();
    void postRedo();
    void postUndo();
}
