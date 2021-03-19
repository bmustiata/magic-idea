package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.service.DataLoader;
import com.intellij.openapi.project.Project;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
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

    private TreeItem selectedTreeItem;

    public void initialize(@NotNull Project project) {
        Group rootGroup = DataLoader.INSTANCE.loadRootGroup();

        DefaultTreeTableModel model = new DefaultTreeTableModel();

        DefaultMutableTreeTableNode rootNode = createGroupNode(rootGroup);
        model.setRoot(rootNode);

        itemTree.setModel(model);
        itemTree.setCellRenderer(new LabelTreeRenderer());

        itemTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TreePath path = itemTree.getPathForLocation(e.getX(), e.getY());

                if (path != null) {
                    for (Object item: path.getPath()) {
                        System.out.println(item);
                    }
                } else {
                    System.out.println("NO PATH");
                }

                if (path != null && path.getPath().length > 0) { // if we have an actual item
                    DefaultMutableTreeTableNode selectedNode = (DefaultMutableTreeTableNode) path.getLastPathComponent();
                    setSelectedTreeItem((TreeItem) selectedNode.getValueAt(0));
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

        ToolTipManager.sharedInstance().registerComponent(itemTree);
    }

    private void setSelectedTreeItem(TreeItem treeItem) {
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

    private DefaultMutableTreeTableNode createGroupNode(Group group) {
        DefaultMutableTreeTableNode result = new DefaultMutableTreeTableNode(group);

        if (group.getChildGroups() != null) {
            for (Group childGroup: group.getChildGroups()) {
                result.add(createGroupNode(childGroup));
            }
        }

        if (group.getChildReferences() != null) {
            for (SourceReference sourceReference: group.getChildReferences()) {
                result.add(createSourceReferenceNode(sourceReference));
            }
        }

        return result;
    }

    private MutableTreeTableNode createSourceReferenceNode(SourceReference sourceReference) {
        DefaultMutableTreeTableNode result = new DefaultMutableTreeTableNode(sourceReference);

        if (sourceReference.getLineReferences() != null) {
            for (LineReference lineReference: sourceReference.getLineReferences()) {
                result.add(createLineReferenceNode(lineReference));
            }
        }

        return result;
    }

    private MutableTreeTableNode createLineReferenceNode(LineReference lineReference) {
        return new DefaultMutableTreeTableNode(lineReference);
    }

    public JComponent getContent() {
        return rootPanel;
    }
}
