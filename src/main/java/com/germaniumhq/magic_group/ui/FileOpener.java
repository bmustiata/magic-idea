package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileOpener {
    public static <T extends TreeItem> void openFile(@NotNull Project project, MgTreeNode<T> selectedTreeItem) {
        final SourceReference sourceReference;
        final int line;

        if (selectedTreeItem.getTreeItem() instanceof SourceReference) {
            sourceReference = (SourceReference) selectedTreeItem.getTreeItem();
            line = 0;
        } else {
            sourceReference = ((MgTreeNode<SourceReference>) selectedTreeItem.getParent()).getTreeItem();
            line = findLine(selectedTreeItem);
        }

        @Nullable VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(sourceReference.getUrl());

        @NotNull Navigatable item = new OpenFileDescriptor(project, file, line, 0);
        item.navigate( true);
    }

    private static <T extends TreeItem> int findLine(MgTreeNode<T> selectedTreeItem) {
        int userLine = Integer.parseInt(((LineReference) selectedTreeItem.getTreeItem()).getExpression());

        return userLine - 1;
    }
}
