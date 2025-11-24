package com.humanfactory;

import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Sealed interface representing simplified collision shapes for bones.
 * These shapes can be used for physics engines or SDF meshing.
 */
public sealed interface BoneShape {
    
    /**
     * Box/Cuboid collision shape.
     * 
     * @param halfExtents Half-width, half-height, half-depth (in meters)
     * @param offset Local position offset from bone origin
     * @param rotation Local rotation offset
     */
    record Box(Vector3f halfExtents, Vector3f offset, Quaternionf rotation) implements BoneShape {
        public Box(float halfWidth, float halfHeight, float halfDepth) {
            this(new Vector3f(halfWidth, halfHeight, halfDepth), new Vector3f(), new Quaternionf());
        }
    }
    
    /**
     * Capsule collision shape (cylinder with hemispherical caps).
     * 
     * @param radius Radius of the capsule (in meters)
     * @param length Total length of the capsule including both hemispherical caps (in meters)
     * @param offset Local position offset from bone origin
     * @param rotation Local rotation offset
     */
    record Capsule(float radius, float length, Vector3f offset, Quaternionf rotation) implements BoneShape {}
    
    /**
     * Sphere collision shape.
     * 
     * @param radius Radius of the sphere (in meters)
     * @param offset Local position offset from bone origin
     */
    record Sphere(float radius, Vector3f offset) implements BoneShape {}
}
