package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.service.DataLoader;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.pom.Navigatable;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
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
    private JTextField textField1;
    private JEditorPane descriptionPane;

    public void initialize(@NotNull Project project) {
        Group rootGroup = DataLoader.INSTANCE.loadRootGroup();

        DefaultTreeTableModel model = new DefaultTreeTableModel();

        DefaultMutableTreeTableNode rootNode = createGroupNode(rootGroup);
        model.setRoot(rootNode);

        itemTree.setModel(model);
        itemTree.setCellRenderer(new LabelTreeRenderer());
        itemTree.setToggleClickCount(0);

        itemTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked: " + e.getClickCount());
                // update the selection + description

                if (e.getClickCount() == 2) {
                    e.consume();
                    @Nullable VirtualFile file = VirtualFileManager.getInstance().findFileByUrl("file:///home/raptor/projects/mgroup2/src/main/java/com/germaniumhq/magic_group/service/DataLoader.java");
                    @NotNull Navigatable item = new OpenFileDescriptor(project, file, 10, 0);
                    item.navigate(true);
                }
            }
        });
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
