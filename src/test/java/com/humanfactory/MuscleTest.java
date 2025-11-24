package com.humanfactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit test for Muscle enum.
 */
public class MuscleTest {

    /**
     * Test that the Muscle enum has a reasonable number of muscles.
     * This enum contains 387 major skeletal muscles.
     */
    @Test
    public void shouldHaveMultipleMuscles() {
        assertTrue(Muscle.values().length > 300, "Should have more than 300 major muscles defined");
    }

    /**
     * Test that all enum values are not null.
     */
    @Test
    public void shouldHaveNonNullValues() {
        for (Muscle muscle : Muscle.values()) {
            assertNotNull(muscle, "Muscle enum value should not be null");
            assertNotNull(muscle.name(), "Muscle enum name should not be null");
        }
    }

    /**
     * Test that specific major muscles exist.
     */
    @Test
    public void shouldHaveSpecificMuscles() {
        assertNotNull(Muscle.valueOf("BICEPS_BRACHII_LEFT"), "Should have left biceps brachii");
        assertNotNull(Muscle.valueOf("TRICEPS_BRACHII_RIGHT"), "Should have right triceps brachii");
        assertNotNull(Muscle.valueOf("RECTUS_ABDOMINIS_LEFT"), "Should have left rectus abdominis");
        assertNotNull(Muscle.valueOf("PECTORALIS_MAJOR_RIGHT"), "Should have right pectoralis major");
        assertNotNull(Muscle.valueOf("RECTUS_FEMORIS_LEFT"), "Should have left rectus femoris");
        assertNotNull(Muscle.valueOf("GASTROCNEMIUS_RIGHT"), "Should have right gastrocnemius");
        assertNotNull(Muscle.valueOf("DELTOID_LEFT"), "Should have left deltoid");
    }
}
