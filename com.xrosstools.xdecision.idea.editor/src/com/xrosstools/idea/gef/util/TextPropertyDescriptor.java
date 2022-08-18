package com.xrosstools.idea.gef.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextPropertyDescriptor extends PropertyDescriptor{
    private JTextField editor;

    public TextPropertyDescriptor(Object propertyId, Object propertyId2) {
        this(propertyId);
    }
    public TextPropertyDescriptor(Object propertyId) {
        editor = new JTextField();
        setId(propertyId);
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == e.VK_ENTER || e.getKeyChar() == '\n')
                    editor.transferFocusUpCycle();
            }
        });
    }

    public JComponent getEditor(Object value) {
        editor.setText((String)value);
        editor.setBorder(null);
        return editor;
    }
}
