package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.TreeItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;


@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MgTreeNode<T extends TreeItem> extends DefaultMutableTreeTableNode implements TreeItem {
    final T treeItem;

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
}
