package com.germaniumhq.magic_group.model;

@lombok.Getter
@lombok.Setter
@lombok.Builder
public class LineReference implements TreeItem {
    String expression;
    String description;
    String longDescription;

    @Override
    public String getName() {
        return expression;
    }
}
