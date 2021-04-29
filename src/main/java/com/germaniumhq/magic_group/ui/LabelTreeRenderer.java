package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.TreeItem;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeRegistry;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class LabelTreeRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value, boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {

        MgTreeNode<? extends TreeItem> item = (MgTreeNode<? extends TreeItem>) value;

        @Nullable VirtualFile file = VirtualFileManager.getInstance().findFileByUrl("file:///home/raptor/projects/mgroup2/src/main/java/com/germaniumhq/magic_group/service/DataLoader.java");
        @NotNull FileType fileType = file.getFileType();

        if (fileType != null) {
            @Nullable Icon icon = fileType.getIcon();
            setIcon(icon);
        }

        StringBuilder html = new StringBuilder("<html>")
            .append(
                String.format(
                    "%s",
                    item.getName()
                )
            );

        if (!isEmpty(item.getDescription())) {
            html.append("<span style='color: #cccccc'>&nbsp;-&nbsp;")
                    .append(item.getDescription())
                    .append("</span>");
        }

        html.append("</html>");

        updateToolTip(item);

        return super.getTreeCellRendererComponent(
                tree, html.toString(), sel, expanded, leaf, row, hasFocus);
    }

    @NotNull
    private void updateToolTip(TreeItem item) {
        StringBuilder tooltip = new StringBuilder();

        if (!isEmpty(item.getDescription())) {
            tooltip.append(item.getDescription());
        }

        String longDescription = item.getLongDescription();
        if (!isEmpty(item.getDescription()) && !isEmpty(longDescription)) {
            tooltip.append("<br><br>");
        }

        if (!isEmpty(longDescription)) {
            tooltip.append(longDescription.replaceAll("\\n", "<br>"));
        }

        String tooltipString = tooltip.toString();
        if (isEmpty(tooltipString)) {
            setToolTipText(null);
            return;
        }

        setToolTipText(tooltipString);
    }

    private boolean isEmpty(String str) {
        return str == null || str.isBlank();
    }
}
