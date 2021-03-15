package com.germaniumhq.magic_group;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import javax.swing.*;

public class MainWindow {
    private JTree itemTree;
    private JPanel rootPanel;
    private JTextField searchTextField;
    private JButton newButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton findButton;
    private JTextField textField1;
    private JEditorPane descriptionPane;

    public void initialize() {
        DefaultTreeTableModel model = new DefaultTreeTableModel();
        DefaultMutableTreeTableNode rootNode = new DefaultMutableTreeTableNode("wut");
        DefaultMutableTreeTableNode a1 = new DefaultMutableTreeTableNode("a1");
        rootNode.add(a1);
        a1.add(new DefaultMutableTreeTableNode("x1"));
        model.setRoot(rootNode);

        itemTree.setModel(model);
    }

    public JComponent getContent() {
        return rootPanel;
    }
}
