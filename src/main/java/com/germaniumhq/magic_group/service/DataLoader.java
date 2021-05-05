package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.germaniumhq.magic_group.ui.MgTreeNode;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import java.util.*;

public class DataLoader {
    public static DataLoader INSTANCE = new DataLoader();

    // We need a reference to the tree model, to do the updates in the UI
    // after we update the local model.
    private DefaultTreeModel treeModel;

    // Keep a node cache by IDs
    private Map<String, MgTreeNode<? extends TreeItem>> nodeCache = new HashMap<>();

    private DataLoader() {}

    public void addGroup(MgTreeNode<Group> treeNode, Group child) {
        Group parentGroup = treeNode.getTreeItem();

        // we update the model
        parentGroup.getChildGroups().add(child);

        // we update also the visual model
        treeModel.insertNodeInto(
                createGroupNode(child),
                treeNode,
                parentGroup.getChildGroups().size() - 1
        );
    }

    public void addSourceReference(MgTreeNode<Group> treeNode, SourceReference child) {
        Group parentGroup = treeNode.getTreeItem();

        if (parentGroup.getSourceReferences() == null) {
            parentGroup.setSourceReferences(new ArrayList<>());
        }

        // we update the model
        parentGroup.getSourceReferences().add(child);

        // we update also the visual model
        treeModel.insertNodeInto(
                createSourceReferenceNode(child),
                treeNode,
                parentGroup.getSourceReferences().size() - 1
        );
    }

    public void addLineReference(MgTreeNode<SourceReference> treeNode, LineReference child) {
        SourceReference parentSource = treeNode.getTreeItem();

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

        for (Group childGroup: group.getChildGroups()) {
            result.add(createGroupNode(childGroup));
        }

        for (SourceReference sourceReference: group.getSourceReferences()) {
            result.add(createSourceReferenceNode(sourceReference));
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

    public boolean reparentNode(
            MgTreeNode<? extends TreeItem> oldParent,
            MgTreeNode<? extends TreeItem> newParent,
            MgTreeNode<? extends TreeItem> sourceNode) {
        if (oldParent == null || newParent == null || sourceNode == null) {
            return false;
        }

        if (!oldParent.getTreeItem().getClass().equals(newParent.getTreeItem().getClass())) {
            return false;
        }

        // TODO: do the insertion with indexes
        if (sourceNode.getTreeItem() instanceof LineReference) {
            LineReference lineReference = (LineReference) sourceNode.getTreeItem();
            ((SourceReference)oldParent.getTreeItem()).getLineReferences().remove(lineReference);
            ((SourceReference)newParent.getTreeItem()).getLineReferences().add(lineReference);
        } else if (sourceNode.getTreeItem() instanceof SourceReference) {
            SourceReference sourceReference = (SourceReference) sourceNode.getTreeItem();
            ((Group)oldParent.getTreeItem()).getSourceReferences().remove(sourceReference);
            ((Group)newParent.getTreeItem()).getSourceReferences().add(sourceReference);
        } else if (sourceNode.getTreeItem() instanceof Group) {
            Group sourceGroup = (Group) sourceNode.getTreeItem();
            ((Group)oldParent.getTreeItem()).getChildGroups().remove(sourceGroup);
            ((Group)newParent.getTreeItem()).getChildGroups().add(sourceGroup);
        } else {
            throw new IllegalArgumentException("Unknown type for " + sourceNode.getTreeItem());
        }

        treeModel.removeNodeFromParent(sourceNode);
        treeModel.insertNodeInto(
                sourceNode,
                newParent,
                0);

        return true;
    }

    public void remove(MgTreeNode<? extends TreeItem> selectedTreeItem) {
        // remove it from the model
        MgTreeNode<? extends TreeItem> parent = (MgTreeNode<? extends TreeItem>) selectedTreeItem.getParent();

        if (selectedTreeItem.getTreeItem() instanceof LineReference) {
            ((SourceReference)parent.getTreeItem()).getLineReferences().remove(selectedTreeItem.getTreeItem());
        } else if (selectedTreeItem.getTreeItem() instanceof SourceReference) {
            ((Group)parent.getTreeItem()).getSourceReferences().remove(selectedTreeItem.getTreeItem());
        } else if (selectedTreeItem.getTreeItem() instanceof Group) {
            ((Group)parent.getTreeItem()).getChildGroups().remove(selectedTreeItem.getTreeItem());
        } else {
            throw new IllegalArgumentException("Unknown type for item " + selectedTreeItem);
        }

        // clean the tree node cache
        Deque<MgTreeNode<? extends TreeItem>> nodesToClean = new LinkedList<>();
        nodesToClean.addLast(selectedTreeItem);

        while (!nodesToClean.isEmpty()) {
            MgTreeNode<? extends TreeItem> currentItem = nodesToClean.removeFirst();
            nodeCache.remove(currentItem.getUid());

            for (int i = 0; i < currentItem.getChildCount(); i++) {
                nodesToClean.addLast((MgTreeNode<? extends TreeItem>) currentItem.getChildAt(i));
            }
        }

        // remove the tree node
        treeModel.removeNodeFromParent(selectedTreeItem);
    }

    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
    }
}
