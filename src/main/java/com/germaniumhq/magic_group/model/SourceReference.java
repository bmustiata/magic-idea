package com.germaniumhq.magic_group.model;

import com.germaniumhq.magic_group.ui.TreeItem;

import java.util.List;

@lombok.Data
@lombok.Builder
public class SourceReference implements TreeItem {
    String description;
    String longDescription;
    String uri;  // path to resource
    List<LineReference> lineReferences;

    @Override
    public String getName() {
        return uri; // probably only the file name
    }
}
