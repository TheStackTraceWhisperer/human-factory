package com.humanfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Bone enum.
 */
public class BoneTest {

    /**
     * Test that the Bone enum has 206 values (adult human skeleton).
     */
    @Test
    public void shouldHave206Bones() {
        assertEquals(206, Bone.values().length, "Adult human skeleton should have 206 bones");
    }

    /**
     * Test that all enum values are not null.
     */
    @Test
    public void shouldHaveNonNullValues() {
        for (Bone bone : Bone.values()) {
            assertNotNull(bone, "Bone enum value should not be null");
            assertNotNull(bone.name(), "Bone enum name should not be null");
        }
    }

    /**
     * Test that specific bones exist.
     */
    @Test
    public void shouldHaveSpecificBones() {
        assertNotNull(Bone.valueOf("FEMUR_LEFT"), "Should have left femur");
        assertNotNull(Bone.valueOf("FEMUR_RIGHT"), "Should have right femur");
        assertNotNull(Bone.valueOf("FRONTAL"), "Should have frontal bone");
        assertNotNull(Bone.valueOf("MANDIBLE"), "Should have mandible");
        assertNotNull(Bone.valueOf("HUMERUS_LEFT"), "Should have left humerus");
        assertNotNull(Bone.valueOf("TIBIA_RIGHT"), "Should have right tibia");
    }
}
