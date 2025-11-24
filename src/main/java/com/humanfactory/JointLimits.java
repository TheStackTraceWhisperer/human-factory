package com.humanfactory;

/**
 * Defines the rotational limits of a joint in Radians.
 * Angles are relative to the Bind Pose (T-Pose).
 */
public record JointLimits(
    float minPitch, float maxPitch, // X-Axis (Flexion/Extension)
    float minYaw, float maxYaw,     // Y-Axis (Twist/Rotation)
    float minRoll, float maxRoll    // Z-Axis (Abduction/Adduction)
) {
    private static final float D2R = (float) (Math.PI / 180.0);

    // --- Standard Presets to keep the code readable ---

    // Fixed (Sutures)
    public static final JointLimits LOCKED = new JointLimits(0, 0, 0, 0, 0, 0);

    // Hinge (Knees, Elbows, Fingers) - Pitch only
    public static JointLimits hinge(float minDeg, float maxDeg) {
        return new JointLimits(minDeg * D2R, maxDeg * D2R, 0, 0, 0, 0);
    }

    // Pivot (Forearm twist, Neck rotation) - Yaw only
    public static JointLimits pivot(float minDeg, float maxDeg) {
        return new JointLimits(0, 0, minDeg * D2R, maxDeg * D2R, 0, 0);
    }

    // Ball & Socket (Shoulder/Hip) - Complex cone
    public static JointLimits ball(float pitchMin, float pitchMax, float yawMin, float yawMax, float rollMin, float rollMax) {
        return new JointLimits(
            pitchMin * D2R, pitchMax * D2R,
            yawMin * D2R, yawMax * D2R,
            rollMin * D2R, rollMax * D2R
        );
    }
    
    // Condyloid/Saddle (Wrist, Thumb) - Pitch + Roll, no Twist
    public static JointLimits biaxial(float pitchMin, float pitchMax, float rollMin, float rollMax) {
        return new JointLimits(
            pitchMin * D2R, pitchMax * D2R,
            0, 0,
            rollMin * D2R, rollMax * D2R
        );
    }
}
