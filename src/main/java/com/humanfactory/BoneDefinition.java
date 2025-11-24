package com.humanfactory;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.List;

/**
 * Defines the complete physical and geometric properties of a bone instance.
 * This bridges the abstract Bone enum with concrete procedural generation.
 */
public record BoneDefinition(
    float length,                    // Length of the bone segment (in meters)
    Vector3f bindPosition,           // Position relative to parent bone (in T-Pose)
    Quaternionf bindRotation,        // Rotation relative to parent bone (in T-Pose)
    float mass,                      // Mass in kilograms
    List<BoneShape> collisionShapes, // Simplified shapes for physics/rendering
    JointLimits jointLimits          // Rotational constraints (copied from JointRegistry)
) {
    /**
     * Standard joint limits for locked/fused bones.
     */
    public record JointLimits(
        float minPitch, float maxPitch, // X-Axis (Flexion/Extension) in radians
        float minYaw, float maxYaw,     // Y-Axis (Twist/Rotation) in radians
        float minRoll, float maxRoll    // Z-Axis (Abduction/Adduction) in radians
    ) {
        public static final JointLimits LOCKED = new JointLimits(0, 0, 0, 0, 0, 0);
    }
}
