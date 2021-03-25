package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.TreeItem;
import com.germaniumhq.magic_group.service.DataLoader;
import com.intellij.openapi.project.Project;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {
    private JTree itemTree;
    private JPanel rootPanel;
    private JTextField searchTextField;
    private JButton newButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton findButton;
    private JTextField descriptionTextField;
    private JEditorPane longDescriptionEditorPane;

    private MgTreeNode<? extends TreeItem> selectedTreeItem;

    public void initialize(@NotNull Project project) {
        DefaultTreeTableModel model = new DefaultTreeTableModel();
        Group rootGroup = DataLoader.INSTANCE.loadRootGroup(model);

        MgTreeNode<? extends TreeItem> rootNode = DataLoader.INSTANCE.createGroupNode(rootGroup);
        model.setRoot(rootNode);

        itemTree.setModel(model);
        itemTree.setCellRenderer(new LabelTreeRenderer());
        itemTree.setToggleClickCount(0);

        itemTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TreePath path = itemTree.getSelectionModel().getSelectionPath();

                if (path != null) {
                    for (Object item: path.getPath()) {
                        System.out.println(item);
                    }
                } else {
                    System.out.println("NO PATH");
                }

                if (path != null && path.getPath().length > 0) { // if we have an actual item
                    MgTreeNode<? extends TreeItem> selectedNode = (MgTreeNode<? extends TreeItem>) path.getLastPathComponent();
                    setSelectedTreeItem(selectedNode);
                }

                if (e.getClickCount() == 2 && selectedTreeItem != null) {
                    FileOpener.openFile(project, selectedTreeItem);
                }
            }
        });

        descriptionTextField.getDocument().addDocumentListener((ChangeListener) content -> {
            if (selectedTreeItem == null) {
                return;
            }

            selectedTreeItem.setDescription(content);
        });

        longDescriptionEditorPane.getDocument().addDocumentListener((ChangeListener) content -> {
            if (selectedTreeItem == null) {
                return;
            }

            selectedTreeItem.setLongDescription(content);
        });

        newButton.addActionListener(actionEvent -> {
            GroupEditor groupEditor = new GroupEditor((MgTreeNode<Group>) selectedTreeItem);
            groupEditor.pack();

            groupEditor.onOk((String name, String description, String longDescription) -> {
                DataLoader.INSTANCE.addGroup(
                        (MgTreeNode<Group>) selectedTreeItem,
                        Group.builder()
                                .name(name)
                                .description(description)
                                .longDescription(longDescription)
                                .build()
                );
            });

            groupEditor.setVisible(true);
        });

        ToolTipManager.sharedInstance().registerComponent(itemTree);
    }

    private void setSelectedTreeItem(MgTreeNode<? extends TreeItem> treeItem) {
        this.selectedTreeItem = treeItem;

        if (treeItem == null) {
            descriptionTextField.setEnabled(false);
            descriptionTextField.setText("");
            longDescriptionEditorPane.setEnabled(false);
            longDescriptionEditorPane.setText("");
            removeButton.setEnabled(false);
            editButton.setEnabled(false);

            return;
        }

        descriptionTextField.setEnabled(true);
        descriptionTextField.setText(treeItem.getDescription());
        longDescriptionEditorPane.setEnabled(true);
        longDescriptionEditorPane.setText(treeItem.getLongDescription());
        removeButton.setEnabled(true);
        editButton.setEnabled(true);
    }

    public JComponent getContent() {
        return rootPanel;
    }
}
