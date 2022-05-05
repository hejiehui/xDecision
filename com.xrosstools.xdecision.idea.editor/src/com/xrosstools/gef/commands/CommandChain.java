package com.xrosstools.gef.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandChain extends Command{
    private List<Command> commands = new ArrayList<Command>();
    public void add(Command cmd) {
        commands.add(cmd);
    }

    public String  getLabel() {
        return "A command chain";
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