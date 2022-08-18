package com.xrosstools.idea.gef.actions;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.commands.CommandListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public abstract class Action implements ActionListener, CommandListener {
    private String text;
    private boolean checked;
    private PropertyChangeListener listener;

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    protected boolean calculateEnabled() {
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Command c = createCommand();
        if(c == null)
            return;

        c.setListener(this);
        c.run();
    }

    public abstract Command createCommand();

    private void postProcess() {
        if(listener != null)
            listener.propertyChange(null);
    }

    public void postExecute() {
        postProcess();
    }
    public void postRedo() {
        postProcess();
    }
    public void postUndo() {
        postProcess();
    }
}
