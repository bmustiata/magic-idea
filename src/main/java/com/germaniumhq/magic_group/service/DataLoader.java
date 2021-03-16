package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;

import java.util.List;

public class DataLoader {
    public static DataLoader INSTANCE = new DataLoader();

    private DataLoader() {}

    public Group loadRootGroup() {
        return Group.builder()
                .name("root")
                .description("none")
                .childGroups(
                        List.of(
                                Group.builder()
                                    .name("child1")
                                .description("child 1 desc")
                                .build()
                        )
                )
                .childReferences(
                        List.of(
                                SourceReference.builder()
                                    .uri("wut.txt")
                                    .lineReferences(
                                            List.of(
                                                    LineReference.builder().expression("abc").build()
                                            )
                                    )
                                    .build()
                        )
                )
                .build();
    }
}
