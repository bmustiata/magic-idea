package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.germaniumhq.magic_group.ui.MgTreeNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    public static DataLoader INSTANCE = new DataLoader();

    // We need a reference to the tree model, to do the updates in the UI
    // after we update the local model.
    private DefaultTreeTableModel treeModel;

    // Keep a node cache by IDs
    private Map<String, MgTreeNode<? extends TreeItem>> nodeCache = new HashMap<>();

    private DataLoader() {}

    public Group loadRootGroup(DefaultTreeTableModel model) {
        this.treeModel = model;

        return Group.builder()
                .name("root")
                .description("none")
                .build();
    }

    public void addGroup(MgTreeNode<Group> treeNode, Group child) {
        Group parentGroup = treeNode.getTreeItem();

        if (parentGroup.getChildGroups() == null) {
            parentGroup.setChildGroups(new ArrayList<>());
        }

        // we update the model
        parentGroup.getChildGroups().add(child);

        // we update also the visual model
        treeModel.insertNodeInto(
                createGroupNode(child),
                (MutableTreeTableNode) treeModel.getRoot(),
                parentGroup.getChildGroups().size() - 1
        );
    }

    public void addSourceReference(MgTreeNode<Group> treeNode, SourceReference child) {
        Group parentGroup = treeNode.getTreeItem();

        if (parentGroup.getChildReferences() == null) {
            parentGroup.setChildReferences(new ArrayList<>());
        }

        // we update the model
        parentGroup.getChildReferences().add(child);

        // we update also the visual model
        treeModel.insertNodeInto(
                createSourceReferenceNode(child),
                treeNode,
                parentGroup.getChildReferences().size() - 1
        );
    }

    public void addLineReference(MgTreeNode<SourceReference> treeNode, LineReference child) {
        SourceReference parentSource = treeNode.getTreeItem();

        if (parentSource.getLineReferences() == null) {
            parentSource.setLineReferences(new ArrayList<>());
        }

        // we update the model
        parentSource.getLineReferences().add(child);

        // we update also the visual model
        treeModel.insertNodeInto(
                createLineReferenceNode(child),
                treeNode,
                parentSource.getLineReferences().size() - 1
        );
    }

    public MgTreeNode<Group> createGroupNode(Group group) {
        MgTreeNode<Group> result = new MgTreeNode<>(group);
        result.setAllowsChildren(true);

        this.nodeCache.put(result.getUid(), result);

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

        this.nodeCache.put(result.getUid(), result);

        if (sourceReference.getLineReferences() != null) {
            for (LineReference lineReference: sourceReference.getLineReferences()) {
                result.add(createLineReferenceNode(lineReference));
            }
        }

        return result;
    }

    public MgTreeNode<LineReference> createLineReferenceNode(LineReference lineReference) {
        MgTreeNode<LineReference> result = new MgTreeNode<>(lineReference);

        this.nodeCache.put(result.getUid(), result);

        return result;
    }

    public MgTreeNode<? extends TreeItem> get(String nodeId) {
        return nodeCache.get(nodeId);
    }
}
