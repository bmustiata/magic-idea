package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.ui.MgTreeNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import javax.swing.tree.TreeModel;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static DataLoader INSTANCE = new DataLoader();

    private DefaultTreeTableModel treeModel;

    private DataLoader() {}

    public Group loadRootGroup(DefaultTreeTableModel model) {
        this.treeModel = model;

        return Group.builder()
                .name("root")
                .description("none")
                .childGroups(
                        new ArrayList<>(List.of(
                                Group.builder()
                                    .name("child1")
                                .description("child 1 desc")
                                .build()
                        ))
                )
                .childReferences(
                        new ArrayList<>(List.of(
                                SourceReference.builder()
                                    .uri("wut.txt")
                                    .lineReferences(
                                            new ArrayList<>(List.of(
                                                    LineReference.builder().expression("abc").build()
                                            ))
                                    )
                                    .build()
                        ))
                )
                .build();
    }

    public void addGroup(MgTreeNode<Group> treeNode, Group child) {
        Group parentGroup = treeNode.getTreeItem();

        if (parentGroup.getChildGroups() == null) {
            parentGroup.setChildGroups(new ArrayList<>());
        }

        parentGroup.getChildGroups().add(child);

        treeModel.insertNodeInto(
                createGroupNode(child),
                treeNode,
                parentGroup.getChildGroups().size() - 1
        );

        // treeNode.add(createGroupNode(child));
    }

    public MgTreeNode<Group> createGroupNode(Group group) {
        MgTreeNode<Group> result = new MgTreeNode<>(group);
        result.setAllowsChildren(true);

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

    public MgTreeNode<SourceReference> createSourceReferenceNode(SourceReference sourceReference) {
        MgTreeNode<SourceReference> result = new MgTreeNode<>(sourceReference);

        if (sourceReference.getLineReferences() != null) {
            for (LineReference lineReference: sourceReference.getLineReferences()) {
                result.add(createLineReferenceNode(lineReference));
            }
        }

        return result;
    }

    public MgTreeNode<LineReference> createLineReferenceNode(LineReference lineReference) {
        return new MgTreeNode<>(lineReference);
    }
}
