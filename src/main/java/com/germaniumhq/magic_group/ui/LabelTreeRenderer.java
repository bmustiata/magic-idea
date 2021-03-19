package com.germaniumhq.magic_group.ui;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class LabelTreeRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value, boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {

        DefaultMutableTreeTableNode treeNode = (DefaultMutableTreeTableNode) value;
        TreeItem item = (TreeItem) treeNode.getValueAt(0);

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

        if (!isEmpty(item.getDescription()) && !isEmpty(item.getLongDescription())) {
            tooltip.append("<br><br>");
        }

        if (!isEmpty(item.getLongDescription())) {
            tooltip.append(item.getLongDescription());
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
