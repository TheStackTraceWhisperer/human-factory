package com.humanfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Unit test for JointLimits record.
 */
public class JointLimitsTest {

    private static final float DELTA = 0.001f; // Tolerance for float comparisons
    private static final float D2R = (float) (Math.PI / 180.0);

    /**
     * Test LOCKED preset has all zeros.
     */
    @Test
    public void lockedShouldHaveZeroLimits() {
        JointLimits locked = JointLimits.LOCKED;
        assertNotNull(locked);
        assertEquals(0f, locked.minPitch(), DELTA);
        assertEquals(0f, locked.maxPitch(), DELTA);
        assertEquals(0f, locked.minYaw(), DELTA);
        assertEquals(0f, locked.maxYaw(), DELTA);
        assertEquals(0f, locked.minRoll(), DELTA);
        assertEquals(0f, locked.maxRoll(), DELTA);
    }

    /**
     * Test hinge factory method creates pitch-only movement.
     */
    @Test
    public void hingeShouldHavePitchOnly() {
        JointLimits hinge = JointLimits.hinge(0, 90);
        assertNotNull(hinge);
        assertEquals(0f, hinge.minPitch(), DELTA);
        assertEquals(90 * D2R, hinge.maxPitch(), DELTA);
        assertEquals(0f, hinge.minYaw(), DELTA);
        assertEquals(0f, hinge.maxYaw(), DELTA);
        assertEquals(0f, hinge.minRoll(), DELTA);
        assertEquals(0f, hinge.maxRoll(), DELTA);
    }

    /**
     * Test pivot factory method creates yaw-only movement.
     */
    @Test
    public void pivotShouldHaveYawOnly() {
        JointLimits pivot = JointLimits.pivot(-90, 90);
        assertNotNull(pivot);
        assertEquals(0f, pivot.minPitch(), DELTA);
        assertEquals(0f, pivot.maxPitch(), DELTA);
        assertEquals(-90 * D2R, pivot.minYaw(), DELTA);
        assertEquals(90 * D2R, pivot.maxYaw(), DELTA);
        assertEquals(0f, pivot.minRoll(), DELTA);
        assertEquals(0f, pivot.maxRoll(), DELTA);
    }

    /**
     * Test ball factory method creates movement on all axes.
     */
    @Test
    public void ballShouldHaveAllAxes() {
        JointLimits ball = JointLimits.ball(-20, 120, -30, 30, -10, 45);
        assertNotNull(ball);
        assertEquals(-20 * D2R, ball.minPitch(), DELTA);
        assertEquals(120 * D2R, ball.maxPitch(), DELTA);
        assertEquals(-30 * D2R, ball.minYaw(), DELTA);
        assertEquals(30 * D2R, ball.maxYaw(), DELTA);
        assertEquals(-10 * D2R, ball.minRoll(), DELTA);
        assertEquals(45 * D2R, ball.maxRoll(), DELTA);
    }

    /**
     * Test biaxial factory method creates pitch and roll, but no yaw.
     */
    @Test
    public void biaxialShouldHavePitchAndRollOnly() {
        JointLimits biaxial = JointLimits.biaxial(-60, 60, -30, 30);
        assertNotNull(biaxial);
        assertEquals(-60 * D2R, biaxial.minPitch(), DELTA);
        assertEquals(60 * D2R, biaxial.maxPitch(), DELTA);
        assertEquals(0f, biaxial.minYaw(), DELTA);
        assertEquals(0f, biaxial.maxYaw(), DELTA);
        assertEquals(-30 * D2R, biaxial.minRoll(), DELTA);
        assertEquals(30 * D2R, biaxial.maxRoll(), DELTA);
    }

    /**
     * Test that degrees are converted to radians correctly.
     */
    @Test
    public void shouldConvertDegreesToRadians() {
        JointLimits hinge180 = JointLimits.hinge(0, 180);
        assertEquals((float) Math.PI, hinge180.maxPitch(), DELTA);
        
        JointLimits hinge90 = JointLimits.hinge(0, 90);
        assertEquals((float) (Math.PI / 2), hinge90.maxPitch(), DELTA);
        
        JointLimits pivot360 = JointLimits.pivot(-180, 180);
        assertEquals(-(float) Math.PI, pivot360.minYaw(), DELTA);
        assertEquals((float) Math.PI, pivot360.maxYaw(), DELTA);
    }

    /**
     * Test record creation with explicit values.
     */
    @Test
    public void shouldCreateRecordWithExplicitValues() {
        JointLimits custom = new JointLimits(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f);
        assertEquals(0.1f, custom.minPitch(), DELTA);
        assertEquals(0.2f, custom.maxPitch(), DELTA);
        assertEquals(0.3f, custom.minYaw(), DELTA);
        assertEquals(0.4f, custom.maxYaw(), DELTA);
        assertEquals(0.5f, custom.minRoll(), DELTA);
        assertEquals(0.6f, custom.maxRoll(), DELTA);
    }
}
