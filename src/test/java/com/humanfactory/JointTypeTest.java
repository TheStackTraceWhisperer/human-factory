package com.humanfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Unit test for JointType enum.
 */
public class JointTypeTest {

    /**
     * Test that the JointType enum has 8 values.
     */
    @Test
    public void shouldHave8JointTypes() {
        assertEquals(8, JointType.values().length, "Should have 8 joint types");
    }

    /**
     * Test that all enum values are not null.
     */
    @Test
    public void shouldHaveNonNullValues() {
        for (JointType jointType : JointType.values()) {
            assertNotNull(jointType, "JointType enum value should not be null");
            assertNotNull(jointType.name(), "JointType enum name should not be null");
        }
    }

    /**
     * Test that specific joint types exist.
     */
    @Test
    public void shouldHaveSpecificJointTypes() {
        assertNotNull(JointType.valueOf("BALL_AND_SOCKET"), "Should have ball and socket joint");
        assertNotNull(JointType.valueOf("HINGE"), "Should have hinge joint");
        assertNotNull(JointType.valueOf("PIVOT"), "Should have pivot joint");
        assertNotNull(JointType.valueOf("CONDYLOID"), "Should have condyloid joint");
        assertNotNull(JointType.valueOf("SADDLE"), "Should have saddle joint");
        assertNotNull(JointType.valueOf("GLIDING"), "Should have gliding joint");
        assertNotNull(JointType.valueOf("FIBROUS"), "Should have fibrous joint");
        assertNotNull(JointType.valueOf("CARTILAGINOUS"), "Should have cartilaginous joint");
    }
}
