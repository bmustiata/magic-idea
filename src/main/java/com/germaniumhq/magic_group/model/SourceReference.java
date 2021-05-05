package com.germaniumhq.magic_group.model;

import java.util.ArrayList;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode
public class SourceReference implements TreeItem {
    String description;
    String longDescription;
    String url;  // path to resource

    @lombok.Builder.Default
    List<LineReference> lineReferences = new ArrayList<>();

    @Override
    public String getName() {
        return url; // probably only the file name
    }

    @Override
    public String toString() {
        return "SourceReference{" +
                "url='" + url + '\'' +
                '}';
    }
}
