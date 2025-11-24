package com.humanfactory;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The procedural factory that turns DNA parameters into a complete, 
 * rigged, and weighted skeleton hierarchy.
 */
public class BodyGenerator {

    /**
     * The genetic blueprint.
     */
    public record BodyDNA(
        float heightMeters,    // e.g., 1.80f
        float massKg,          // e.g., 75.0f
        float buildFactor,     // 0.5 (Slender) to 1.5 (Stocky). Affects width/thickness.
        float headRatio,       // Default 0.125 (1/8th height).
        float legRatio         // Default 0.48 (Legs are ~48% of total height).
    ) {
        public static BodyDNA averageMale() {
            return new BodyDNA(1.80f, 78.0f, 1.0f, 0.125f, 0.48f);
        }
    }

    // Constants for internal layout logic
    private static final float DENSITY_BONE = 1500f; // kg/m^3 approx
    private static final Vector3f UP = new Vector3f(0, 1, 0);

    /**
     * Main Generation Entry Point.
     */
    public Map<Bone, BoneDefinition> generate(BodyDNA dna) {
        Map<Bone, BoneDefinition> skeleton = new HashMap<>();

        // 1. Calculate Core Proportions
        float headSize = dna.heightMeters * dna.headRatio;
        float legLength = dna.heightMeters * dna.legRatio;
        float torsoLength = dna.heightMeters - legLength - headSize;
        
        // Width modifiers
        float shoulderWidth = (dna.heightMeters * 0.23f) * dna.buildFactor;
        float hipWidth = (dna.heightMeters * 0.16f) * dna.buildFactor;

        // 2. ROOT: The Sacrum (Center of Mass / Physics Root)
        // Positioned at the top of the legs (Hip height)
        createBone(skeleton, Bone.SACRUM, 
            headSize * 0.8f, 
            new Vector3f(0, legLength, 0), // World Space Start Position
            dna.massKg * 0.10f, 
            List.of(new BoneShape.Box(
                new Vector3f(hipWidth * 0.4f, headSize * 0.4f, headSize * 0.3f), 
                new Vector3f(), new Quaternionf()))
        );
        
        createBone(skeleton, Bone.COCCYX, headSize * 0.2f,
            new Vector3f(0, -headSize * 0.4f, -0.02f),
            0.05f, List.of()
        );

        // 3. REGIONS
        generatePelvis(skeleton, hipWidth, dna);
        generateSpineAndRibs(skeleton, torsoLength, dna);
        generateHead(skeleton, headSize, dna);
        
        // Symmetrical Limbs
        generateLeg(skeleton, true, legLength, hipWidth, dna);
        generateLeg(skeleton, false, legLength, hipWidth, dna);
        
        generateArm(skeleton, true, shoulderWidth, dna);
        generateArm(skeleton, false, shoulderWidth, dna);

        return skeleton;
    }

    // ============================================================
    // SPINE & TORSO
    // ============================================================

    private void generateSpineAndRibs(Map<Bone, BoneDefinition> map, float torsoLen, BodyDNA dna) {
        // Ratios: Lumbar (35%), Thoracic (45%), Cervical (20%)
        
        // --- LUMBAR (Lower Back) ---
        // Array ordered bottom-up (L5 is lowest, connects to sacrum)
        Bone[] lumbar = {Bone.LUMBAR_5, Bone.LUMBAR_4, Bone.LUMBAR_3, Bone.LUMBAR_2, Bone.LUMBAR_1};
        float lumbarSegH = (torsoLen * 0.35f) / lumbar.length;
        
        for (Bone b : lumbar) {
            createBone(map, b, lumbarSegH, 
                new Vector3f(0, lumbarSegH, 0), // Stack Y-Up
                dna.massKg * 0.015f,
                List.of(new BoneShape.Box(new Vector3f(0.04f * dna.buildFactor, lumbarSegH * 0.9f, 0.04f), new Vector3f(), new Quaternionf()))
            );
        }

        // --- THORACIC (Rib Cage Area) ---
        // Array ordered bottom-up (T12 is lowest, T1 is highest/connects to cervical)
        Bone[] thoracic = {
            Bone.THORACIC_12, Bone.THORACIC_11, Bone.THORACIC_10, Bone.THORACIC_9, 
            Bone.THORACIC_8, Bone.THORACIC_7, Bone.THORACIC_6, Bone.THORACIC_5, 
            Bone.THORACIC_4, Bone.THORACIC_3, Bone.THORACIC_2, Bone.THORACIC_1
        };
        float thoracicSegH = (torsoLen * 0.45f) / thoracic.length;

        for (int i = 0; i < thoracic.length; i++) {
            Bone b = thoracic[i];
            createBone(map, b, thoracicSegH,
                new Vector3f(0, thoracicSegH, 0),
                dna.massKg * 0.012f,
                List.of(new BoneShape.Box(new Vector3f(0.035f * dna.buildFactor, thoracicSegH * 0.9f, 0.035f), new Vector3f(), new Quaternionf()))
            );
            
            // Attach corresponding rib pair (index 0 = T12 = Rib 12, etc.)
            int ribNum = 12 - i; 
            generateRibPair(map, ribNum, dna);
        }

        // --- CERVICAL (Neck) ---
        Bone[] cervical = {
            Bone.CERVICAL_7, Bone.CERVICAL_6, Bone.CERVICAL_5, Bone.CERVICAL_4, 
            Bone.CERVICAL_3, Bone.CERVICAL_2_AXIS, Bone.CERVICAL_1_ATLAS
        };
        float cervicalSegH = (torsoLen * 0.20f) / cervical.length;

        for (Bone b : cervical) {
            createBone(map, b, cervicalSegH,
                new Vector3f(0, cervicalSegH, 0),
                dna.massKg * 0.008f,
                List.of(new BoneShape.Box(new Vector3f(0.025f, cervicalSegH * 0.8f, 0.025f), new Vector3f(), new Quaternionf()))
            );
        }
    }

    private void generateRibPair(Map<Bone, BoneDefinition> map, int ribNumber, BodyDNA dna) {
        Bone left = getRibBone(ribNumber, true);
        Bone right = getRibBone(ribNumber, false);
        if (left == null || right == null) return;

        // Rib shape varies by number (1 is small ring, 7 is huge arc, 12 is floating spike)
        float ribScale = (ribNumber > 7) ? (12 - ribNumber) * 0.15f : ribNumber * 0.15f; 
        Vector3f ribSize = new Vector3f(0.1f + ribScale, 0.02f, 0.05f + ribScale);

        // Left Rib
        createBone(map, left, 0.1f, 
            new Vector3f(0.03f, 0, 0), // Offset from spine
            0.05f, 
            List.of(new BoneShape.Box(ribSize, new Vector3f(ribSize.x/2, 0, ribSize.z/2), new Quaternionf()))
        );
        
        // Right Rib
        createBone(map, right, 0.1f, 
            new Vector3f(-0.03f, 0, 0),
            0.05f,
            List.of(new BoneShape.Box(ribSize, new Vector3f(-ribSize.x/2, 0, ribSize.z/2), new Quaternionf()))
        );
    }

    // ============================================================
    // HEAD
    // ============================================================

    private void generateHead(Map<Bone, BoneDefinition> map, float size, BodyDNA dna) {
        // Base Skull
        createBone(map, Bone.OCCIPITAL, size,
            new Vector3f(0, 0.02f, 0), // Top of Atlas
            dna.massKg * 0.05f,
            List.of(new BoneShape.Sphere(size * 0.5f, new Vector3f(0, size * 0.4f, 0.05f)))
        );
        
        // Face plates (Fixed to Occipital via fibrous joints)
        Bone[] skullParts = {
            Bone.FRONTAL, Bone.PARIETAL_LEFT, Bone.PARIETAL_RIGHT, 
            Bone.TEMPORAL_LEFT, Bone.TEMPORAL_RIGHT, Bone.SPHENOID
        };
        for (Bone b : skullParts) {
            createBone(map, b, size * 0.2f, new Vector3f(0,0,0), 0.1f, List.of()); // Simplified geometry
        }

        // Jaw
        createBone(map, Bone.MANDIBLE, size * 0.4f,
            new Vector3f(0, 0.03f, 0.05f), // Hinge point near ear
            0.3f,
            List.of(new BoneShape.Box(new Vector3f(0.06f, 0.02f, 0.08f), new Vector3f(0, -0.05f, 0.06f), new Quaternionf()))
        );
    }

    // ============================================================
    // LIMBS
    // ============================================================

    private void generatePelvis(Map<Bone, BoneDefinition> map, float hipWidth, BodyDNA dna) {
        // Hips attach to Sacrum
        Bone[] hips = {Bone.HIP_BONE_LEFT, Bone.HIP_BONE_RIGHT};
        float sign = 1f;
        
        for (Bone b : hips) {
            createBone(map, b, 0.2f,
                new Vector3f(sign * (hipWidth * 0.5f), 0, 0),
                dna.massKg * 0.04f,
                List.of(new BoneShape.Box(new Vector3f(0.1f * dna.buildFactor, 0.14f, 0.08f), new Vector3f(), new Quaternionf()))
            );
            sign = -1f;
        }
    }

    private void generateLeg(Map<Bone, BoneDefinition> map, boolean isLeft, float totalLen, float hipWidth, BodyDNA dna) {
        float sign = isLeft ? 1f : -1f;
        float femurLen = totalLen * 0.52f;
        float tibiaLen = totalLen * 0.40f;
        float footHeight = totalLen * 0.08f;

        // 1. Femur
        Bone femur = isLeft ? Bone.FEMUR_LEFT : Bone.FEMUR_RIGHT;
        createBone(map, femur, femurLen,
            new Vector3f(sign * 0.08f, -0.05f, 0.02f), // Acetabulum offset
            dna.massKg * 0.12f,
            List.of(new BoneShape.Capsule(0.05f * dna.buildFactor, femurLen, new Vector3f(0, -femurLen/2, 0), new Quaternionf()))
        );

        // 2. Tibia
        Bone tibia = isLeft ? Bone.TIBIA_LEFT : Bone.TIBIA_RIGHT;
        createBone(map, tibia, tibiaLen,
            new Vector3f(0, -femurLen, 0), // Knee
            dna.massKg * 0.06f,
            List.of(new BoneShape.Capsule(0.04f * dna.buildFactor, tibiaLen, new Vector3f(0, -tibiaLen/2, 0), new Quaternionf()))
        );
        
        // 3. Patella (Kneecap)
        Bone patella = isLeft ? Bone.PATELLA_LEFT : Bone.PATELLA_RIGHT;
        createBone(map, patella, 0.05f,
            new Vector3f(0, 0, 0.04f), // Front of knee
            0.1f,
            List.of(new BoneShape.Sphere(0.03f, new Vector3f()))
        );

        // 4. Fibula (Lateral leg bone)
        Bone fibula = isLeft ? Bone.FIBULA_LEFT : Bone.FIBULA_RIGHT;
        createBone(map, fibula, tibiaLen,
            new Vector3f(sign * 0.03f, 0, 0), // Next to Tibia
            dna.massKg * 0.01f,
            List.of(new BoneShape.Capsule(0.015f, tibiaLen, new Vector3f(0, -tibiaLen/2, 0), new Quaternionf()))
        );

        // 5. Foot
        generateFoot(map, isLeft, tibiaLen, dna);
    }

    private void generateFoot(Map<Bone, BoneDefinition> map, boolean isLeft, float tibiaLen, BodyDNA dna) {
        // Ankle (Talus)
        Bone talus = isLeft ? Bone.TALUS_LEFT : Bone.TALUS_RIGHT;
        createBone(map, talus, 0.05f, new Vector3f(0, -tibiaLen, 0), 0.1f, List.of(new BoneShape.Box(0.04f, 0.04f, 0.04f)));
        
        // Heel (Calcaneus)
        Bone calcaneus = isLeft ? Bone.CALCANEUS_LEFT : Bone.CALCANEUS_RIGHT;
        createBone(map, calcaneus, 0.08f, new Vector3f(0, -0.03f, -0.03f), 0.1f, List.of(new BoneShape.Box(0.04f, 0.04f, 0.06f)));

        // Midfoot (Navicular/Cuboid/Cuneiforms) - Simplified as one block for procedural gen
        Bone navicular = isLeft ? Bone.NAVICULAR_LEFT : Bone.NAVICULAR_RIGHT;
        createBone(map, navicular, 0.04f, new Vector3f(0, -0.02f, 0.04f), 0.05f, List.of());

        // Toes (Metatarsals + Phalanges)
        float sign = isLeft ? 1f : -1f;
        
        // Big Toe (2 phalanges)
        generateDigit(map, 
            isLeft ? Bone.METATARSAL_1_LEFT : Bone.METATARSAL_1_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_BIG_TOE_LEFT : Bone.PROXIMAL_PHALANX_BIG_TOE_RIGHT,
            null, // No middle phalanx for big toe
            isLeft ? Bone.DISTAL_PHALANX_BIG_TOE_LEFT : Bone.DISTAL_PHALANX_BIG_TOE_RIGHT,
            new Vector3f(sign * 0.02f, 0, 0.05f),
            0.08f, 0.02f
        );

        // Toe 2 (3 phalanges)
        generateDigit(map,
            isLeft ? Bone.METATARSAL_2_LEFT : Bone.METATARSAL_2_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_TOE_2_LEFT : Bone.PROXIMAL_PHALANX_TOE_2_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_TOE_2_LEFT : Bone.MIDDLE_PHALANX_TOE_2_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_TOE_2_LEFT : Bone.DISTAL_PHALANX_TOE_2_RIGHT,
            new Vector3f(sign * 0.01f, 0, 0.05f),
            0.07f, 0.015f
        );

        // Toe 3 (3 phalanges)
        generateDigit(map,
            isLeft ? Bone.METATARSAL_3_LEFT : Bone.METATARSAL_3_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_TOE_3_LEFT : Bone.PROXIMAL_PHALANX_TOE_3_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_TOE_3_LEFT : Bone.MIDDLE_PHALANX_TOE_3_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_TOE_3_LEFT : Bone.DISTAL_PHALANX_TOE_3_RIGHT,
            new Vector3f(0, 0, 0.05f),
            0.065f, 0.015f
        );

        // Toe 4 (3 phalanges)
        generateDigit(map,
            isLeft ? Bone.METATARSAL_4_LEFT : Bone.METATARSAL_4_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_TOE_4_LEFT : Bone.PROXIMAL_PHALANX_TOE_4_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_TOE_4_LEFT : Bone.MIDDLE_PHALANX_TOE_4_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_TOE_4_LEFT : Bone.DISTAL_PHALANX_TOE_4_RIGHT,
            new Vector3f(sign * -0.01f, 0, 0.05f),
            0.06f, 0.015f
        );

        // Little Toe (3 phalanges)
        generateDigit(map,
            isLeft ? Bone.METATARSAL_5_LEFT : Bone.METATARSAL_5_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_LITTLE_TOE_LEFT : Bone.PROXIMAL_PHALANX_LITTLE_TOE_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_LITTLE_TOE_LEFT : Bone.MIDDLE_PHALANX_LITTLE_TOE_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_LITTLE_TOE_LEFT : Bone.DISTAL_PHALANX_LITTLE_TOE_RIGHT,
            new Vector3f(sign * -0.02f, 0, 0.05f),
            0.055f, 0.012f
        );
    }

    private void generateArm(Map<Bone, BoneDefinition> map, boolean isLeft, float shoulderWidth, BodyDNA dna) {
        float sign = isLeft ? 1f : -1f;
        float armLen = dna.heightMeters * 0.42f;
        float humerusLen = armLen * 0.48f;
        float radiusLen = armLen * 0.42f;

        // 1. Clavicle
        Bone clavicle = isLeft ? Bone.CLAVICLE_LEFT : Bone.CLAVICLE_RIGHT;
        createBone(map, clavicle, shoulderWidth * 0.45f,
            new Vector3f(sign * 0.02f, 0.08f, 0.04f), // Top of Sternum
            dna.massKg * 0.02f,
            List.of(new BoneShape.Capsule(0.02f, shoulderWidth * 0.4f, new Vector3f(sign * (shoulderWidth * 0.2f), 0, 0), new Quaternionf()))
        );

        // 2. Scapula
        Bone scapula = isLeft ? Bone.SCAPULA_LEFT : Bone.SCAPULA_RIGHT;
        createBone(map, scapula, 0.15f,
            new Vector3f(sign * (shoulderWidth * 0.4f), 0, -0.05f), // End of clavicle
            dna.massKg * 0.03f,
            List.of(new BoneShape.Box(0.1f, 0.12f, 0.02f))
        );

        // 3. Humerus
        Bone humerus = isLeft ? Bone.HUMERUS_LEFT : Bone.HUMERUS_RIGHT;
        createBone(map, humerus, humerusLen,
            new Vector3f(sign * 0.05f, -0.02f, 0), // Shoulder joint
            dna.massKg * 0.05f,
            List.of(new BoneShape.Capsule(0.04f * dna.buildFactor, humerusLen, new Vector3f(0, -humerusLen/2, 0), new Quaternionf()))
        );

        // 4. Radius & Ulna
        Bone radius = isLeft ? Bone.RADIUS_LEFT : Bone.RADIUS_RIGHT;
        createBone(map, radius, radiusLen,
            new Vector3f(0, -humerusLen, 0), // Elbow
            dna.massKg * 0.02f,
            List.of(new BoneShape.Capsule(0.025f * dna.buildFactor, radiusLen, new Vector3f(0, -radiusLen/2, 0), new Quaternionf()))
        );
        Bone ulna = isLeft ? Bone.ULNA_LEFT : Bone.ULNA_RIGHT;
        createBone(map, ulna, radiusLen,
            new Vector3f(0, -humerusLen, 0), 
            dna.massKg * 0.02f,
            List.of(new BoneShape.Capsule(0.02f, radiusLen, new Vector3f(0, -radiusLen/2, 0), new Quaternionf()))
        );

        // 5. Hand
        generateHand(map, isLeft, radiusLen, dna);
    }

    private void generateHand(Map<Bone, BoneDefinition> map, boolean isLeft, float forearmLen, BodyDNA dna) {
        float sign = isLeft ? 1f : -1f;
        // Wrist Root
        Bone lunate = isLeft ? Bone.LUNATE_LEFT : Bone.LUNATE_RIGHT;
        createBone(map, lunate, 0.03f, new Vector3f(0, -forearmLen, 0), 0.01f, List.of(new BoneShape.Box(0.03f, 0.03f, 0.02f)));
        
        // Carpals simplified block
        Bone capitate = isLeft ? Bone.CAPITATE_LEFT : Bone.CAPITATE_RIGHT;
        createBone(map, capitate, 0.02f, new Vector3f(0, -0.02f, 0), 0.01f, List.of());

        // --- FINGERS ---
        // 1. Thumb (Metacarpal 1)
        generateDigit(map, 
            isLeft ? Bone.METACARPAL_1_LEFT : Bone.METACARPAL_1_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_THUMB_LEFT : Bone.PROXIMAL_PHALANX_THUMB_RIGHT,
            null,
            isLeft ? Bone.DISTAL_PHALANX_THUMB_LEFT : Bone.DISTAL_PHALANX_THUMB_RIGHT,
            new Vector3f(sign * 0.03f, -0.02f, 0.02f), // Angled out
            0.05f, 0.012f
        );

        // 2. Index
        generateDigit(map,
            isLeft ? Bone.METACARPAL_2_LEFT : Bone.METACARPAL_2_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_INDEX_FINGER_LEFT : Bone.PROXIMAL_PHALANX_INDEX_FINGER_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_INDEX_FINGER_LEFT : Bone.MIDDLE_PHALANX_INDEX_FINGER_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_INDEX_FINGER_LEFT : Bone.DISTAL_PHALANX_INDEX_FINGER_RIGHT,
            new Vector3f(sign * 0.015f, -0.03f, 0), 
            0.09f, 0.01f
        );

        // 3. Middle
        generateDigit(map,
            isLeft ? Bone.METACARPAL_3_LEFT : Bone.METACARPAL_3_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_MIDDLE_FINGER_LEFT : Bone.PROXIMAL_PHALANX_MIDDLE_FINGER_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_MIDDLE_FINGER_LEFT : Bone.MIDDLE_PHALANX_MIDDLE_FINGER_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_MIDDLE_FINGER_LEFT : Bone.DISTAL_PHALANX_MIDDLE_FINGER_RIGHT,
            new Vector3f(0, -0.03f, 0), 
            0.10f, 0.01f
        );
        
        // 4. Ring
        generateDigit(map,
            isLeft ? Bone.METACARPAL_4_LEFT : Bone.METACARPAL_4_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_RING_FINGER_LEFT : Bone.PROXIMAL_PHALANX_RING_FINGER_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_RING_FINGER_LEFT : Bone.MIDDLE_PHALANX_RING_FINGER_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_RING_FINGER_LEFT : Bone.DISTAL_PHALANX_RING_FINGER_RIGHT,
            new Vector3f(sign * -0.015f, -0.03f, 0), 
            0.09f, 0.01f
        );

        // 5. Little
        generateDigit(map,
            isLeft ? Bone.METACARPAL_5_LEFT : Bone.METACARPAL_5_RIGHT,
            isLeft ? Bone.PROXIMAL_PHALANX_LITTLE_FINGER_LEFT : Bone.PROXIMAL_PHALANX_LITTLE_FINGER_RIGHT,
            isLeft ? Bone.MIDDLE_PHALANX_LITTLE_FINGER_LEFT : Bone.MIDDLE_PHALANX_LITTLE_FINGER_RIGHT,
            isLeft ? Bone.DISTAL_PHALANX_LITTLE_FINGER_LEFT : Bone.DISTAL_PHALANX_LITTLE_FINGER_RIGHT,
            new Vector3f(sign * -0.03f, -0.03f, 0), 
            0.07f, 0.008f
        );
    }

    private void generateDigit(Map<Bone, BoneDefinition> map, Bone meta, Bone prox, Bone mid, Bone dist, 
                               Vector3f startOffset, float totalLen, float width) {
        
        float metaLen = totalLen * 0.4f;
        float phalLen = (totalLen * 0.6f) / (mid == null ? 2 : 3);

        // Metacarpal
        createBone(map, meta, metaLen, startOffset, 0.005f, List.of(new BoneShape.Capsule(width, metaLen, new Vector3f(0, -metaLen/2, 0), new Quaternionf())));
        
        // Proximal
        createBone(map, prox, phalLen, new Vector3f(0, -metaLen, 0), 0.002f, List.of(new BoneShape.Capsule(width * 0.9f, phalLen, new Vector3f(0, -phalLen/2, 0), new Quaternionf())));
        
        Vector3f nextOffset = new Vector3f(0, -phalLen, 0);

        // Middle (Optional for Thumb)
        if (mid != null) {
            createBone(map, mid, phalLen, nextOffset, 0.002f, List.of(new BoneShape.Capsule(width * 0.8f, phalLen, new Vector3f(0, -phalLen/2, 0), new Quaternionf())));
        }

        // Distal
        createBone(map, dist, phalLen * 0.8f, nextOffset, 0.001f, List.of(new BoneShape.Capsule(width * 0.7f, phalLen * 0.8f, new Vector3f(0, -(phalLen*0.8f)/2, 0), new Quaternionf())));
    }

    // ============================================================
    // UTILS
    // ============================================================

    private void createBone(Map<Bone, BoneDefinition> map, Bone id, float length, Vector3f bindPos, float mass, List<BoneShape> shapes) {
        // Automatically fetch constraints from the Static Registry
        JointRegistry.Joint jointInfo = JointRegistry.getJoint(id);
        
        BoneDefinition.JointLimits limits;
        if (jointInfo != null && jointInfo.limits() != null) {
            limits = new BoneDefinition.JointLimits(
                jointInfo.limits().minPitch(), jointInfo.limits().maxPitch(),
                jointInfo.limits().minYaw(),   jointInfo.limits().maxYaw(),
                jointInfo.limits().minRoll(),  jointInfo.limits().maxRoll()
            );
        } else {
            limits = BoneDefinition.JointLimits.LOCKED;
        }

        BoneDefinition def = new BoneDefinition(
            length,
            bindPos,
            new Quaternionf(), 
            mass,
            shapes,
            limits
        );

        map.put(id, def);
    }
    
    // Helper to map Rib numbers to Enums
    private Bone getRibBone(int i, boolean isLeft) {
        if (isLeft) {
            return switch(i) {
                case 1 -> Bone.RIB_1_LEFT; case 2 -> Bone.RIB_2_LEFT; case 3 -> Bone.RIB_3_LEFT;
                case 4 -> Bone.RIB_4_LEFT; case 5 -> Bone.RIB_5_LEFT; case 6 -> Bone.RIB_6_LEFT;
                case 7 -> Bone.RIB_7_LEFT; case 8 -> Bone.RIB_8_LEFT; case 9 -> Bone.RIB_9_LEFT;
                case 10 -> Bone.RIB_10_LEFT; case 11 -> Bone.RIB_11_LEFT; case 12 -> Bone.RIB_12_LEFT;
                default -> null;
            };
        } else {
            return switch(i) {
                case 1 -> Bone.RIB_1_RIGHT; case 2 -> Bone.RIB_2_RIGHT; case 3 -> Bone.RIB_3_RIGHT;
                case 4 -> Bone.RIB_4_RIGHT; case 5 -> Bone.RIB_5_RIGHT; case 6 -> Bone.RIB_6_RIGHT;
                case 7 -> Bone.RIB_7_RIGHT; case 8 -> Bone.RIB_8_RIGHT; case 9 -> Bone.RIB_9_RIGHT;
                case 10 -> Bone.RIB_10_RIGHT; case 11 -> Bone.RIB_11_RIGHT; case 12 -> Bone.RIB_12_RIGHT;
                default -> null;
            };
        }
    }
}
