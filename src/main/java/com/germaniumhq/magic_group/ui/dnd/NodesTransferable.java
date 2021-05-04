package com.germaniumhq.magic_group.ui.dnd;

import com.germaniumhq.magic_group.model.SourceReference;
import com.germaniumhq.magic_group.model.TreeItem;
import com.germaniumhq.magic_group.ui.MgTreeNode;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NodesTransferable implements Transferable {
    private final Map<DataFlavor, Object> content = new HashMap<>();

    public NodesTransferable(MgTreeNode<? extends TreeItem> selectedItem) throws ClassNotFoundException {
        if (selectedItem.getTreeItem() instanceof SourceReference) {
            SourceReference sourceReference = (SourceReference) selectedItem.getTreeItem();
            File file = new File(sourceReference.getUri());
            content.put(DataFlavor.javaFileListFlavor, Collections.singletonList(file));
        }

        content.put(DataFlavor.stringFlavor, "mg:" + selectedItem.getUid());
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return content.keySet().toArray(new DataFlavor[0]);
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return content.containsKey(flavor);
    }

    @NotNull
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return content.get(flavor);
    }
}
