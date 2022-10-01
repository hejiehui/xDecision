package com.xrosstools.xdecision.idea.editor;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.Activator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class XdecisionFileType implements FileType {
    public static final String NAME = "Xross Decision Model File";
    public static final String DESCRIPTION = "Xross Decision Model File";
    public static final String EXTENSION = "xdecision";
    public static final String ICON = "tree";

    public static final XdecisionFileType INSTANCE = new XdecisionFileType();

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return IconLoader.findIcon(Activator.getIconPath(ICON));
    }

    @Override
    public boolean isBinary() {
        return false;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return null;
    }
}
