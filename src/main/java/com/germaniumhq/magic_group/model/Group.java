package com.germaniumhq.magic_group.model;

import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.Builder
public class Group implements TreeItem {
    String name;
    String description;
    String longDescription;
    List<Group> childGroups;
    List<SourceReference> childReferences;
}
