package com.germaniumhq.magic_group.model;

import java.util.ArrayList;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode
public class Group implements TreeItem {
    String name;
    String description;
    String longDescription;

    @lombok.Builder.Default
    List<Group> childGroups = new ArrayList<>();

    @lombok.Builder.Default
    List<SourceReference> sourceReferences = new ArrayList<>();

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                '}';
    }
}
