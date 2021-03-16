package com.germaniumhq.magic_group.model;

import com.germaniumhq.magic_group.ui.TreeItem;

@lombok.Data
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
