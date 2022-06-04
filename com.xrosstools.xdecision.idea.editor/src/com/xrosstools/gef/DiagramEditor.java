package com.xrosstools.gef;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.util.Key;
import com.xrosstools.gef.util.IPropertySource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class DiagramEditor<T extends IPropertySource> implements FileEditor, FileEditorManagerListener {
    private String name;
    private PanelContentProvider<T> contentProvider;
    private JComponent panel;

    public DiagramEditor(String name, PanelContentProvider<T> contentProvider) {
        this.name = name;
        this.contentProvider = contentProvider;
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        if(panel != null)
            return panel;

        try{
            panel = new EditorPanel<>(contentProvider);
        }catch(Throwable e) {
            panel = new JLabel("Failed to load model File: " + e);
            e.printStackTrace(System.err);
        }

        return panel;

    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return panel;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void selectNotify() {
        panel.repaint();
    }

    @Override
    public void deselectNotify() {
    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {
    }

    @Nullable
    @Override
    public <K> K getUserData(@NotNull Key<K> key) {
        return null;
    }

    @Override
    public <K> void putUserData(@NotNull Key<K> key, @Nullable K t) {
    }
}