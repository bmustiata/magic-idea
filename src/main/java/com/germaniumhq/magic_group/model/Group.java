package com.germaniumhq.magic_group.model;

import com.germaniumhq.magic_group.ui.TreeItem;

import java.util.List;

@lombok.Data
@lombok.Builder
public class Group implements TreeItem {
    String name;
    String description;
    String longDescription;
    List<Group> childGroups;
    List<SourceReference> childReferences;
}
