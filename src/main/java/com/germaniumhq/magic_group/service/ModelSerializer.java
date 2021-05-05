package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import com.intellij.openapi.components.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
@State(
    name="group",
    storages = {
        @Storage(value = "magic-group.xml", roamingType = RoamingType.DISABLED)
    }
)
final public class ModelSerializer implements PersistentStateComponent<Group> {
    private Group rootGroup = Group.builder()
            .name("root")
            .build();

    ModelSerializer() {
    }

    @Override
    public @Nullable Group getState() {
        return rootGroup;
    }

    @Override
    public void loadState(@NotNull Group state) {
        rootGroup = state;
    }
}
