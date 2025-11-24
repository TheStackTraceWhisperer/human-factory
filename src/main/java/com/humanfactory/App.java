package com.humanfactory;

import java.util.Map;

/**
 * Demo application for the Human Factory BodyGenerator.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("=== Human Factory BodyGenerator Demo ===\n");
        
        // Create a body generator
        BodyGenerator generator = new BodyGenerator();
        
        // Generate an average male skeleton
        BodyGenerator.BodyDNA averageMaleDNA = BodyGenerator.BodyDNA.averageMale();
        System.out.println("Generating skeleton with DNA parameters:");
        System.out.println("  Height: " + averageMaleDNA.heightMeters() + " meters");
        System.out.println("  Mass: " + averageMaleDNA.massKg() + " kg");
        System.out.println("  Build Factor: " + averageMaleDNA.buildFactor());
        System.out.println("  Head Ratio: " + averageMaleDNA.headRatio());
        System.out.println("  Leg Ratio: " + averageMaleDNA.legRatio());
        System.out.println();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(averageMaleDNA);
        
        System.out.println("Generated " + skeleton.size() + " bones.\n");
        
        // Display some interesting bones
        System.out.println("Sample Bone Properties:");
        System.out.println("------------------------");
        
        displayBone("SACRUM (Root)", skeleton.get(Bone.SACRUM));
        displayBone("FEMUR_LEFT", skeleton.get(Bone.FEMUR_LEFT));
        displayBone("HUMERUS_RIGHT", skeleton.get(Bone.HUMERUS_RIGHT));
        displayBone("OCCIPITAL (Head)", skeleton.get(Bone.OCCIPITAL));
        
        System.out.println("\n=== Generation Complete ===");
    }
    
    private static void displayBone(String name, BoneDefinition bone) {
        System.out.println("\n" + name + ":");
        System.out.printf("  Length: %.3f m\n", bone.length());
        System.out.printf("  Mass: %.3f kg\n", bone.mass());
        System.out.printf("  Position: (%.3f, %.3f, %.3f)\n", 
            bone.bindPosition().x, bone.bindPosition().y, bone.bindPosition().z);
        System.out.printf("  Collision Shapes: %d\n", bone.collisionShapes().size());
        if (!bone.collisionShapes().isEmpty()) {
            BoneShape shape = bone.collisionShapes().get(0);
            System.out.printf("  Primary Shape: %s\n", shape.getClass().getSimpleName());
        }
    }
}
