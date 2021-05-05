package com.germaniumhq.magic_group.ui.dnd;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.germaniumhq.magic_group.service.DataLoader;
import com.germaniumhq.magic_group.ui.MgTreeNode;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class MagicGroupTransferHandler extends TransferHandler {
    // used for the drop part
    @Override
    public boolean canImport(TransferSupport support) {
        Transferable transferable = support.getTransferable();

        //noinspection RedundantIfStatement
        if (!transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor) &&
            !transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }

        return true;
    }

    @SneakyThrows
    @Override
    public boolean importData(TransferSupport support) {
        Transferable transferable = support.getTransferable();
        TreePath path = ((JTree.DropLocation)support.getDropLocation()).getPath();
        MgTreeNode<Group> parentGroup = detectParentGroup(path);

        // this is a DND inside the tree, so we use the node ID we're moving
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor) &&
            transferable.getTransferData(DataFlavor.stringFlavor).toString().startsWith("mg:")) {

            String data = transferable.getTransferData(DataFlavor.stringFlavor).toString();
            MgTreeNode<? extends TreeItem> sourceNode = DataLoader.INSTANCE.get(data.substring(3));

            assert sourceNode != null;

            return true;
        }

        // this is a DND from outside the tree, so we look for some files
        if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            List<File> data = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

            for (File f: data) {
                // the file.toURI.toURL returns an unusable potato.
                String fileUrl = Paths.get(f.getAbsolutePath()).toUri().toString();
                DataLoader.INSTANCE.addSourceReference(
                        parentGroup,
                        SourceReference.builder().url(fileUrl).build());
            }

            return true;
        }

        return false;
    }

    @NotNull
    private MgTreeNode<Group> detectParentGroup(TreePath path) {
        MgTreeNode<? extends TreeItem> selectedNode = (MgTreeNode<? extends TreeItem>) path.getLastPathComponent();

        while (!(selectedNode.getTreeItem() instanceof Group)) {
            selectedNode = (MgTreeNode<? extends TreeItem>) selectedNode.getParent();
        }
        MgTreeNode<Group> parentGroup = (MgTreeNode<Group>) selectedNode;
        return parentGroup;
    }

    // used for the drag part
    @Nullable
    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        MgTreeNode<? extends TreeItem> selectedItem = (MgTreeNode<? extends TreeItem>)
                tree.getSelectionModel().getSelectionPath().getLastPathComponent();

        try {
            return new NodesTransferable(selectedItem);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
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
