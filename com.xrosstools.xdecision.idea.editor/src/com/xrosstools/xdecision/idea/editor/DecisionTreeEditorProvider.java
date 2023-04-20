package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractDiagramEditorProvider;
import com.xrosstools.idea.gef.PanelContentProvider;
import org.jetbrains.annotations.NotNull;

public class DecisionTreeEditorProvider extends AbstractDiagramEditorProvider {
    public static final String TREE = "tree";
    public static final String NODE = "node";
    public static final String CONNECTION = "connection";

    @Override
    public FileType getFileType() {
        return XdecisionFileType.INSTANCE;
    }

    @Override
    public String getExtention() {
        return XdecisionFileType.EXTENSION;
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "Xross Decision Tree Edtitor";
    }

    @Override
    public PanelContentProvider createPanelContentProvider(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return new DecisionTreePanelContentProvider(project, virtualFile);
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
