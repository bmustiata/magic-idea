package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import com.germaniumhq.magic_group.model.LineReference;
import com.germaniumhq.magic_group.model.SourceReference;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

public class YamlLoaderTest {
    @Test
    public void testYamlLoading() {
        Yaml yaml = new Yaml();

        Group group = yaml.loadAs("name: test", Group.class);

        assertEquals("test", group.getName());
        assertNull(group.getDescription());
        assertNull(group.getLongDescription());
        assertNotNull(group.getChildGroups());
        assertNotNull(group.getSourceReferences());
    }

    @Test
    public void testFullDeserialization() {
        Yaml yaml = new Yaml();

        Group group = Group.builder()
                .name("a")
                .childGroups(
                        singletonList(Group.builder().name("b").build())
                )
                .sourceReferences(
                        singletonList(SourceReference.builder()
                                .url("wut")
                                .lineReferences(singletonList(
                                        LineReference.builder()
                                                .expression("x")
                                                .build()
                                )).build()
                        )
                ).build();

        String data = yaml.dump(group);
        Group newGroup = yaml.loadAs(data, Group.class);

        assertEquals(group, newGroup);
    }
}
