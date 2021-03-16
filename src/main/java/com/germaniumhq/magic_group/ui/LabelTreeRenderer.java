package com.germaniumhq.magic_group.ui;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

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

        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            html.append("<span style='color: #cccccc'>&nbsp;-&nbsp;")
                    .append(item.getDescription())
                    .append("</span>");
        }

        html.append("</html>");

        return super.getTreeCellRendererComponent(
                tree, html.toString(), sel, expanded, leaf, row, hasFocus);
    }
}
