package com.xrosstools.gef;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public abstract class Action implements ActionListener {
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

        c.execute();
        if(listener != null)
            listener.propertyChange(null);
    }

    public abstract Command createCommand();
}
