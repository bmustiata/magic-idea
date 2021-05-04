package com.germaniumhq.magic_group.ui;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.germaniumhq.magic_group.service.DataLoader;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

public class MagicGroupTransferHandler extends TransferHandler {
    // used for the drop part
    @Override
    public boolean canImport(TransferSupport support) {
        Transferable transferable = support.getTransferable();

        //noinspection RedundantIfStatement
        if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return false;
        }

        return true;
    }

    @SneakyThrows
    @Override
    public boolean importData(TransferSupport support) {
        Transferable transferable = support.getTransferable();
        List<File> data = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

        TreePath path = ((JTree.DropLocation)support.getDropLocation()).getPath();
        MgTreeNode<? extends TreeItem> selectedNode = (MgTreeNode<? extends TreeItem>) path.getLastPathComponent();

        while (!(selectedNode.getTreeItem() instanceof Group)) {
            selectedNode = (MgTreeNode<? extends TreeItem>) selectedNode.getParent();
        }
        MgTreeNode<Group> parentGroup = (MgTreeNode<Group>) selectedNode;

        for (File f: data) {
            DataLoader.INSTANCE.addSourceReference(parentGroup, SourceReference.builder().uri(f.toURI().toASCIIString()).build());
        }

        return super.importData(support);
    }

    // used for the drag part
    @Nullable
    @Override
    protected Transferable createTransferable(JComponent c) {
        return super.createTransferable(c);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        super.exportDone(source, data, action);
    }
}
