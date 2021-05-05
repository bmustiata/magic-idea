package com.germaniumhq.magic_group.service;

import com.germaniumhq.magic_group.model.Group;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

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
}
