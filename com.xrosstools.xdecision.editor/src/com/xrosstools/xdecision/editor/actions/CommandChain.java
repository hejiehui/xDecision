package com.xrosstools.xdecision.editor.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

public class CommandChain extends Command{
    private List<Command> commands = new ArrayList<Command>();
    public void add(Command cmd) {
        commands.add(cmd);
    }

    public void execute() {
        for(Command cmd: commands)
            if(cmd.canExecute())
                cmd.execute(); 
    }

    public void redo() {
        for(Command cmd: commands)
            if(cmd.canRedo())
                cmd.redo(); 
    }

    public void undo() {
        for(int i = commands.size() -1; i >= 0; i--) {
            Command cmd = commands.get(i);
            if(cmd.canUndo())
                cmd.undo(); 
        }
    }
}