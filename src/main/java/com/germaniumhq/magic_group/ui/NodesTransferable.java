package com.germaniumhq.magic_group.ui;

import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class NodesTransferable implements Transferable {


    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[0];
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return false;
    }

    @NotNull
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return null;
    }
}
