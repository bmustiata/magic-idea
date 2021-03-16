package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.service.DataLoader;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

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
        Group rootGroup = DataLoader.INSTANCE.loadRootGroup();

        DefaultTreeTableModel model = new DefaultTreeTableModel();

        DefaultMutableTreeTableNode rootNode = createGroupNode(rootGroup);
        model.setRoot(rootNode);

        itemTree.setModel(model);
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
