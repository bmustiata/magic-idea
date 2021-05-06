package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.apache.commons.lang.StringEscapeUtils;
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
        updateIcon(item);

        StringBuilder html = new StringBuilder("<html>")
            .append(
                String.format(
                    "%s",
                    StringEscapeUtils.escapeHtml(item.getName())
                )
            );

        if (!isEmpty(item.getDescription())) {
            html.append("<span style='color: #cccccc'>&nbsp;-&nbsp;")
                    .append(StringEscapeUtils.escapeHtml(item.getDescription()))
                    .append("</span>");
        }

        html.append("</html>");

        updateToolTip(item);

        return super.getTreeCellRendererComponent(
                tree, html.toString(), sel, expanded, leaf, row, hasFocus);
    }

    private void updateIcon(MgTreeNode<? extends TreeItem> item) {
        if (item.getTreeItem() instanceof SourceReference) {
            String fileUri = ((SourceReference)item.getTreeItem()).getUrl();
            setAllIcons(findIconForResource(fileUri));
        } else if (item.getTreeItem() instanceof Group) {
            setAllIcons(IconLoader.findIcon("nodes/folder.svg"));
        } else if (item.getTreeItem() instanceof LineReference) {
            setAllIcons(IconLoader.findIcon("general/arrowRight.svg"));
        }
    }

    private void setAllIcons(Icon icon) {
        setIcon(icon);
        setLeafIcon(icon);
        setOpenIcon(icon);
        setClosedIcon(icon);
        setDisabledIcon(icon);
    }

    @Nullable
    private Icon findIconForResource(String url) {
        @Nullable VirtualFile file = VirtualFileManager.getInstance().findFileByUrl(url);

        if (file == null) {
            return IconLoader.findIcon("fileTypes/unknown.svg");
        }

        @NotNull FileType fileType = file.getFileType();
        @Nullable Icon icon = fileType.getIcon();

        return icon;
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
            tooltip.append(StringEscapeUtils.escapeHtml(longDescription).replaceAll("\\n", "<br>"));
        }

        String tooltipString = tooltip.toString();
        if (isEmpty(tooltipString)) {
            setToolTipText(null);
            return;
        }

        setToolTipText(tooltipString);
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
