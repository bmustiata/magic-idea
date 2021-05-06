package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.pom.Navigatable;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileOpener {
    public static <T extends TreeItem> void openFile(@NotNull Project project, MgTreeNode<T> selectedTreeItem) {
        final SourceReference sourceReference;
        final int line;

        if (selectedTreeItem.getTreeItem() instanceof SourceReference) {
            sourceReference = (SourceReference) selectedTreeItem.getTreeItem();
        } else {
            sourceReference = ((MgTreeNode<SourceReference>) selectedTreeItem.getParent()).getTreeItem();
        }

        @Nullable VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(sourceReference.getUrl());

        line = selectedTreeItem.getTreeItem() instanceof SourceReference ? 0 : findLine(file, selectedTreeItem);

        @NotNull Navigatable item = new OpenFileDescriptor(project, file, line, 0);
        item.navigate( true);
    }

    @SneakyThrows
    private static <T extends TreeItem> int findLine(VirtualFile file, MgTreeNode<T> selectedTreeItem) {
        int userLine;
        LineReference lineReference = (LineReference) selectedTreeItem.getTreeItem();

        try {
            userLine = Integer.parseInt(lineReference.getExpression());
        } catch (NumberFormatException e) {
            userLine = 1;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "utf-8"))) {
                String line = reader.readLine();
                while (line != null && !line.contains(lineReference.getExpression())) {
                    userLine++;
                    line = reader.readLine();
                }
            }
        }

        return userLine - 1;
    }
}
