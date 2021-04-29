package com.germaniumhq.magic_group.ui;

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
        @Nullable VirtualFile file = VirtualFileManager.getInstance().findFileByUrl("file:///home/raptor/projects/magic-idea/src/main/java/com/germaniumhq/magic_group/service/DataLoader.java");

        @NotNull Navigatable item = new OpenFileDescriptor(project, file, 10, 0);
        item.navigate( true);
    }
}
