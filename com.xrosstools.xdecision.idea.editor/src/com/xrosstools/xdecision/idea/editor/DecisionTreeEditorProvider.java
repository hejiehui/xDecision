package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractDiagramEditorProvider;
import com.xrosstools.idea.gef.Activator;
import com.xrosstools.idea.gef.PanelContentProvider;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeDiagram;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNode;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeNodeConnection;
import com.xrosstools.xdecision.idea.editor.model.DecisionTreeRoot;
import org.jetbrains.annotations.NotNull;

import static com.xrosstools.idea.gef.Activator.register;

public class DecisionTreeEditorProvider extends AbstractDiagramEditorProvider {
    public static final String TREE = "tree";
    public static final String NODE = "node";
    public static final String CONNECTION = "connection";

    static {
        register(DecisionTreeDiagram.class, TREE);
        register(DecisionTreeRoot.class, TREE);
        register(DecisionTreeNode.class, NODE);
        register(DecisionTreeNodeConnection.class, CONNECTION);
    }
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
