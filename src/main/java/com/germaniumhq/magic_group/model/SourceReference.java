package com.germaniumhq.magic_group.model;

import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.Builder
public class SourceReference implements TreeItem {
    String description;
    String longDescription;
    String url;  // path to resource
    List<LineReference> lineReferences;

    @Override
    public String getName() {
        return url; // probably only the file name
    }
}
