package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;

public class ModelSerializer {
    public static ModelSerializer INSTANCE = new ModelSerializer();

    ModelSerializer() {

    }

    public Group load() {
        return Group.builder()
                .name("root")
                .description("none")
                .build();
    }

    public void persist(Group root) {
    }
}
