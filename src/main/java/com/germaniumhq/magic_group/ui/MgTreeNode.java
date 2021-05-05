package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.TreeItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

import java.util.UUID;


@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MgTreeNode<T extends TreeItem> extends DefaultMutableTreeTableNode implements TreeItem {
    final T treeItem;

    @EqualsAndHashCode.Exclude
    final String uid;

    public MgTreeNode(T treeItem) {
        this.treeItem = treeItem;
        this.uid = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return treeItem.getName();
    }

    @Override
    public String getDescription() {
        return treeItem.getDescription();
    }

    @Override
    public String getLongDescription() {
        return treeItem.getLongDescription();
    }

    @Override
    public void setDescription(String newValue) {
        treeItem.setDescription(newValue);
    }

    @Override
    public void setLongDescription(String newValue) {
        treeItem.setLongDescription(newValue);
    }

    @Override
    public String toString() {
        return "MgTreeNode{" +
                "treeItem=" + treeItem +
                ", uid='" + uid + '\'' +
                '}';
    }
}
