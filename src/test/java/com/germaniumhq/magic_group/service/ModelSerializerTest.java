package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelSerializerTest {
    @Test
    void load() {
        Group data = ModelSerializer.INSTANCE.load();
        assertNotNull(data);
    }
}