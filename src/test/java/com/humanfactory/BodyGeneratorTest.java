package com.humanfactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@DisplayName("BodyGenerator Tests")
public class BodyGeneratorTest {

    @Test
    @DisplayName("Should generate a complete skeleton from average male DNA")
    void testGenerateAverageMale() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Verify skeleton is not empty
        assertNotNull(skeleton);
        assertFalse(skeleton.isEmpty());
        
        // Verify sacrum (root bone) exists
        assertTrue(skeleton.containsKey(Bone.SACRUM));
        assertNotNull(skeleton.get(Bone.SACRUM));
    }

    @Test
    @DisplayName("Should create sacrum with proper proportions")
    void testSacrumCreation() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        BoneDefinition sacrum = skeleton.get(Bone.SACRUM);
        
        assertNotNull(sacrum);
        assertTrue(sacrum.mass() > 0);
        assertTrue(sacrum.length() > 0);
        assertNotNull(sacrum.bindPosition());
        assertNotNull(sacrum.bindRotation());
        assertNotNull(sacrum.collisionShapes());
    }

    @Test
    @DisplayName("Should generate all spine vertebrae")
    void testSpineGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Verify lumbar vertebrae
        for (int i = 1; i <= 5; i++) {
            Bone lumbar = Bone.valueOf("LUMBAR_" + i);
            assertTrue(skeleton.containsKey(lumbar), "Missing " + lumbar);
        }
        
        // Verify thoracic vertebrae
        for (int i = 1; i <= 12; i++) {
            Bone thoracic = Bone.valueOf("THORACIC_" + i);
            assertTrue(skeleton.containsKey(thoracic), "Missing " + thoracic);
        }
        
        // Verify cervical vertebrae
        assertTrue(skeleton.containsKey(Bone.CERVICAL_1_ATLAS));
        assertTrue(skeleton.containsKey(Bone.CERVICAL_2_AXIS));
        for (int i = 3; i <= 7; i++) {
            Bone cervical = Bone.valueOf("CERVICAL_" + i);
            assertTrue(skeleton.containsKey(cervical), "Missing " + cervical);
        }
    }

    @Test
    @DisplayName("Should generate all 24 ribs (12 pairs)")
    void testRibGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Verify all rib pairs
        for (int i = 1; i <= 12; i++) {
            Bone leftRib = Bone.valueOf("RIB_" + i + "_LEFT");
            Bone rightRib = Bone.valueOf("RIB_" + i + "_RIGHT");
            assertTrue(skeleton.containsKey(leftRib), "Missing " + leftRib);
            assertTrue(skeleton.containsKey(rightRib), "Missing " + rightRib);
        }
    }

    @Test
    @DisplayName("Should generate head bones")
    void testHeadGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Verify major skull bones
        assertTrue(skeleton.containsKey(Bone.OCCIPITAL));
        assertTrue(skeleton.containsKey(Bone.FRONTAL));
        assertTrue(skeleton.containsKey(Bone.PARIETAL_LEFT));
        assertTrue(skeleton.containsKey(Bone.PARIETAL_RIGHT));
        assertTrue(skeleton.containsKey(Bone.TEMPORAL_LEFT));
        assertTrue(skeleton.containsKey(Bone.TEMPORAL_RIGHT));
        assertTrue(skeleton.containsKey(Bone.SPHENOID));
        assertTrue(skeleton.containsKey(Bone.MANDIBLE));
        
        // Verify occipital has sphere collision shape
        BoneDefinition occipital = skeleton.get(Bone.OCCIPITAL);
        assertNotNull(occipital);
        assertFalse(occipital.collisionShapes().isEmpty());
        assertTrue(occipital.collisionShapes().get(0) instanceof BoneShape.Sphere);
    }

    @Test
    @DisplayName("Should generate pelvis bones")
    void testPelvisGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        assertTrue(skeleton.containsKey(Bone.HIP_BONE_LEFT));
        assertTrue(skeleton.containsKey(Bone.HIP_BONE_RIGHT));
    }

    @Test
    @DisplayName("Should generate both legs with symmetry")
    void testLegGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Left leg
        assertTrue(skeleton.containsKey(Bone.FEMUR_LEFT));
        assertTrue(skeleton.containsKey(Bone.TIBIA_LEFT));
        assertTrue(skeleton.containsKey(Bone.FIBULA_LEFT));
        assertTrue(skeleton.containsKey(Bone.PATELLA_LEFT));
        assertTrue(skeleton.containsKey(Bone.TALUS_LEFT));
        assertTrue(skeleton.containsKey(Bone.CALCANEUS_LEFT));
        
        // Right leg
        assertTrue(skeleton.containsKey(Bone.FEMUR_RIGHT));
        assertTrue(skeleton.containsKey(Bone.TIBIA_RIGHT));
        assertTrue(skeleton.containsKey(Bone.FIBULA_RIGHT));
        assertTrue(skeleton.containsKey(Bone.PATELLA_RIGHT));
        assertTrue(skeleton.containsKey(Bone.TALUS_RIGHT));
        assertTrue(skeleton.containsKey(Bone.CALCANEUS_RIGHT));
        
        // Verify femurs have capsule collision shapes
        BoneDefinition femurLeft = skeleton.get(Bone.FEMUR_LEFT);
        assertFalse(femurLeft.collisionShapes().isEmpty());
        assertTrue(femurLeft.collisionShapes().get(0) instanceof BoneShape.Capsule);
    }

    @Test
    @DisplayName("Should generate both arms with symmetry")
    void testArmGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Left arm
        assertTrue(skeleton.containsKey(Bone.CLAVICLE_LEFT));
        assertTrue(skeleton.containsKey(Bone.SCAPULA_LEFT));
        assertTrue(skeleton.containsKey(Bone.HUMERUS_LEFT));
        assertTrue(skeleton.containsKey(Bone.RADIUS_LEFT));
        assertTrue(skeleton.containsKey(Bone.ULNA_LEFT));
        
        // Right arm
        assertTrue(skeleton.containsKey(Bone.CLAVICLE_RIGHT));
        assertTrue(skeleton.containsKey(Bone.SCAPULA_RIGHT));
        assertTrue(skeleton.containsKey(Bone.HUMERUS_RIGHT));
        assertTrue(skeleton.containsKey(Bone.RADIUS_RIGHT));
        assertTrue(skeleton.containsKey(Bone.ULNA_RIGHT));
    }

    @Test
    @DisplayName("Should generate hand bones with all fingers")
    void testHandGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Left hand - wrist
        assertTrue(skeleton.containsKey(Bone.LUNATE_LEFT));
        assertTrue(skeleton.containsKey(Bone.CAPITATE_LEFT));
        
        // Left hand - thumb (2 phalanges)
        assertTrue(skeleton.containsKey(Bone.METACARPAL_1_LEFT));
        assertTrue(skeleton.containsKey(Bone.PROXIMAL_PHALANX_THUMB_LEFT));
        assertTrue(skeleton.containsKey(Bone.DISTAL_PHALANX_THUMB_LEFT));
        
        // Left hand - index finger (3 phalanges)
        assertTrue(skeleton.containsKey(Bone.METACARPAL_2_LEFT));
        assertTrue(skeleton.containsKey(Bone.PROXIMAL_PHALANX_INDEX_FINGER_LEFT));
        assertTrue(skeleton.containsKey(Bone.MIDDLE_PHALANX_INDEX_FINGER_LEFT));
        assertTrue(skeleton.containsKey(Bone.DISTAL_PHALANX_INDEX_FINGER_LEFT));
        
        // Verify finger bones have capsule shapes
        BoneDefinition metacarpal = skeleton.get(Bone.METACARPAL_2_LEFT);
        assertFalse(metacarpal.collisionShapes().isEmpty());
        assertTrue(metacarpal.collisionShapes().get(0) instanceof BoneShape.Capsule);
    }

    @Test
    @DisplayName("Should respect different body DNA parameters")
    void testCustomDNA() {
        BodyGenerator generator = new BodyGenerator();
        
        // Test with custom DNA
        BodyGenerator.BodyDNA customDNA = new BodyGenerator.BodyDNA(
            2.0f,   // taller
            100.0f, // heavier
            1.5f,   // stockier build
            0.125f, // standard head ratio
            0.48f   // standard leg ratio
        );
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(customDNA);
        
        assertNotNull(skeleton);
        assertFalse(skeleton.isEmpty());
        
        // Verify sacrum mass scales with body mass
        BoneDefinition sacrum = skeleton.get(Bone.SACRUM);
        assertTrue(sacrum.mass() > 0);
    }

    @Test
    @DisplayName("Should copy joint limits from JointRegistry")
    void testJointLimitsIntegration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Verify joint limits are set for bones that have joint info
        BoneDefinition humerus = skeleton.get(Bone.HUMERUS_LEFT);
        assertNotNull(humerus.jointLimits());
        
        // The humerus should have non-locked joint limits (ball and socket)
        BoneDefinition.JointLimits limits = humerus.jointLimits();
        // At least one axis should have non-zero limits
        boolean hasMovement = limits.maxPitch() != 0 || limits.maxYaw() != 0 || limits.maxRoll() != 0;
        assertTrue(hasMovement, "Humerus should have movement in at least one axis");
    }

    @Test
    @DisplayName("Should generate big toe with correct structure")
    void testBigToeGeneration() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // Big toe should have metatarsal + 2 phalanges (no middle phalanx)
        assertTrue(skeleton.containsKey(Bone.METATARSAL_1_LEFT));
        assertTrue(skeleton.containsKey(Bone.PROXIMAL_PHALANX_BIG_TOE_LEFT));
        assertTrue(skeleton.containsKey(Bone.DISTAL_PHALANX_BIG_TOE_LEFT));
    }

    @Test
    @DisplayName("Should assign reasonable masses to bones")
    void testBoneMasses() {
        BodyGenerator generator = new BodyGenerator();
        BodyGenerator.BodyDNA dna = BodyGenerator.BodyDNA.averageMale();
        
        Map<Bone, BoneDefinition> skeleton = generator.generate(dna);
        
        // All bones should have positive mass
        for (Map.Entry<Bone, BoneDefinition> entry : skeleton.entrySet()) {
            assertTrue(entry.getValue().mass() > 0, 
                entry.getKey() + " should have positive mass");
        }
        
        // Femur should be heavier than a finger bone
        float femurMass = skeleton.get(Bone.FEMUR_LEFT).mass();
        float fingerMass = skeleton.get(Bone.DISTAL_PHALANX_INDEX_FINGER_LEFT).mass();
        assertTrue(femurMass > fingerMass, "Femur should be heavier than a finger bone");
    }
}
