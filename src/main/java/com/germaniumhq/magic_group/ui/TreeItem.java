package com.germaniumhq.magic_group.ui;

public interface TreeItem {
    String getName();
    String getDescription();
    String getLongDescription();

    void setDescription(String newValue);
    void setLongDescription(String newValue);
}
