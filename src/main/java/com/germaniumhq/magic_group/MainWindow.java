package com.germaniumhq.magic_group;

import com.intellij.ui.treeStructure.treetable.ListTreeTableModel;
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

import javax.swing.*;

public class MainWindow {
    private JTree tree1;
    private JPanel rootPanel;
    private JTextField textField1;

    public void initialize() {
    }

    public JComponent getContent() {
        return rootPanel;
    }

    private void createUIComponents() {
        DefaultMutableTreeTableNode node = new DefaultMutableTreeTableNode();
        node.add(new DefaultMutableTreeTableNode("x1"));
        node.add(new DefaultMutableTreeTableNode("x2"));
        node.add(new DefaultMutableTreeTableNode("x3"));

        TreeTableModel model = new ListTreeTableModel(node, new ColumnInfo[] {
                new TreeColumnInfo("name"),
                new TreeColumnInfo("name2"),
        });
        tree1 = new TreeTable(model).getTree();

    }
}
