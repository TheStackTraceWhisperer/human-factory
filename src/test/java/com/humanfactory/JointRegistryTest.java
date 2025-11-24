package com.humanfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Unit test for JointRegistry.
 */
public class JointRegistryTest {

    /**
     * Test that the SACRUM (root bone) returns null as it has no parent.
     */
    @Test
    public void sacrumShouldHaveNoParent() {
        assertNull(JointRegistry.getJoint(Bone.SACRUM), 
            "SACRUM is the root bone and should have no parent joint");
    }

    /**
     * Test spine connections from sacrum upward.
     */
    @Test
    public void shouldHaveCorrectSpineHierarchy() {
        // Coccyx connects to Sacrum
        JointRegistry.Joint coccyxJoint = JointRegistry.getJoint(Bone.COCCYX);
        assertNotNull(coccyxJoint, "Coccyx should have a joint");
        assertEquals(Bone.SACRUM, coccyxJoint.parent(), "Coccyx should connect to SACRUM");
        assertEquals(JointType.CARTILAGINOUS, coccyxJoint.type());

        // Lumbar 5 connects to Sacrum
        JointRegistry.Joint l5Joint = JointRegistry.getJoint(Bone.LUMBAR_5);
        assertNotNull(l5Joint);
        assertEquals(Bone.SACRUM, l5Joint.parent());
        assertEquals(JointType.CARTILAGINOUS, l5Joint.type());

        // Lumbar 4 connects to Lumbar 5
        JointRegistry.Joint l4Joint = JointRegistry.getJoint(Bone.LUMBAR_4);
        assertNotNull(l4Joint);
        assertEquals(Bone.LUMBAR_5, l4Joint.parent());
        
        // Lumbar 1 connects to Lumbar 2
        JointRegistry.Joint l1Joint = JointRegistry.getJoint(Bone.LUMBAR_1);
        assertNotNull(l1Joint);
        assertEquals(Bone.LUMBAR_2, l1Joint.parent());

        // Thoracic 12 connects to Lumbar 1
        JointRegistry.Joint t12Joint = JointRegistry.getJoint(Bone.THORACIC_12);
        assertNotNull(t12Joint);
        assertEquals(Bone.LUMBAR_1, t12Joint.parent());
        
        // Thoracic 1 connects to Thoracic 2
        JointRegistry.Joint t1Joint = JointRegistry.getJoint(Bone.THORACIC_1);
        assertNotNull(t1Joint);
        assertEquals(Bone.THORACIC_2, t1Joint.parent());

        // Cervical 7 connects to Thoracic 1
        JointRegistry.Joint c7Joint = JointRegistry.getJoint(Bone.CERVICAL_7);
        assertNotNull(c7Joint);
        assertEquals(Bone.THORACIC_1, c7Joint.parent());
        
        // Cervical 1 (Atlas) connects to Cervical 2 (Axis)
        JointRegistry.Joint atlasJoint = JointRegistry.getJoint(Bone.CERVICAL_1_ATLAS);
        assertNotNull(atlasJoint);
        assertEquals(Bone.CERVICAL_2_AXIS, atlasJoint.parent());
        assertEquals(JointType.PIVOT, atlasJoint.type(), "Atlas-Axis should be a PIVOT joint for rotation (shaking head 'No')");
    }

    /**
     * Test skull connections.
     */
    @Test
    public void shouldHaveCorrectSkullHierarchy() {
        // Occipital connects to Atlas
        JointRegistry.Joint occipitalJoint = JointRegistry.getJoint(Bone.OCCIPITAL);
        assertNotNull(occipitalJoint);
        assertEquals(Bone.CERVICAL_1_ATLAS, occipitalJoint.parent());
        assertEquals(JointType.CONDYLOID, occipitalJoint.type());

        // Frontal connects to Sphenoid
        JointRegistry.Joint frontalJoint = JointRegistry.getJoint(Bone.FRONTAL);
        assertNotNull(frontalJoint);
        assertEquals(Bone.SPHENOID, frontalJoint.parent());
        assertEquals(JointType.FIBROUS, frontalJoint.type());

        // Mandible connects to Temporal (TMJ)
        JointRegistry.Joint mandibleJoint = JointRegistry.getJoint(Bone.MANDIBLE);
        assertNotNull(mandibleJoint);
        assertEquals(Bone.TEMPORAL_LEFT, mandibleJoint.parent());
        assertEquals(JointType.HINGE, mandibleJoint.type(), "TMJ should be HINGE");
    }

    /**
     * Test rib connections.
     */
    @Test
    public void shouldHaveCorrectRibConnections() {
        // Sternum connects to Thoracic 4
        JointRegistry.Joint sternumJoint = JointRegistry.getJoint(Bone.STERNUM);
        assertNotNull(sternumJoint);
        assertEquals(Bone.THORACIC_4, sternumJoint.parent());

        // Rib 1 Left connects to Thoracic 1
        JointRegistry.Joint rib1LeftJoint = JointRegistry.getJoint(Bone.RIB_1_LEFT);
        assertNotNull(rib1LeftJoint);
        assertEquals(Bone.THORACIC_1, rib1LeftJoint.parent());
        assertEquals(JointType.GLIDING, rib1LeftJoint.type());

        // Rib 12 Right connects to Thoracic 12
        JointRegistry.Joint rib12RightJoint = JointRegistry.getJoint(Bone.RIB_12_RIGHT);
        assertNotNull(rib12RightJoint);
        assertEquals(Bone.THORACIC_12, rib12RightJoint.parent());
        assertEquals(JointType.GLIDING, rib12RightJoint.type());
    }

    /**
     * Test left upper limb connections.
     */
    @Test
    public void shouldHaveCorrectLeftUpperLimbHierarchy() {
        // Clavicle connects to Sternum
        JointRegistry.Joint clavicleJoint = JointRegistry.getJoint(Bone.CLAVICLE_LEFT);
        assertNotNull(clavicleJoint);
        assertEquals(Bone.STERNUM, clavicleJoint.parent());
        assertEquals(JointType.SADDLE, clavicleJoint.type());

        // Scapula connects to Clavicle
        JointRegistry.Joint scapulaJoint = JointRegistry.getJoint(Bone.SCAPULA_LEFT);
        assertNotNull(scapulaJoint);
        assertEquals(Bone.CLAVICLE_LEFT, scapulaJoint.parent());
        assertEquals(JointType.GLIDING, scapulaJoint.type());

        // Humerus connects to Scapula
        JointRegistry.Joint humerusJoint = JointRegistry.getJoint(Bone.HUMERUS_LEFT);
        assertNotNull(humerusJoint);
        assertEquals(Bone.SCAPULA_LEFT, humerusJoint.parent());
        assertEquals(JointType.BALL_AND_SOCKET, humerusJoint.type(), "Shoulder should be ball and socket");

        // Ulna connects to Humerus
        JointRegistry.Joint ulnaJoint = JointRegistry.getJoint(Bone.ULNA_LEFT);
        assertNotNull(ulnaJoint);
        assertEquals(Bone.HUMERUS_LEFT, ulnaJoint.parent());
        assertEquals(JointType.HINGE, ulnaJoint.type(), "Elbow should be hinge");

        // Radius connects to Humerus
        JointRegistry.Joint radiusJoint = JointRegistry.getJoint(Bone.RADIUS_LEFT);
        assertNotNull(radiusJoint);
        assertEquals(Bone.HUMERUS_LEFT, radiusJoint.parent());
        assertEquals(JointType.PIVOT, radiusJoint.type());
    }

    /**
     * Test left hand connections.
     */
    @Test
    public void shouldHaveCorrectLeftHandHierarchy() {
        // Lunate connects to Radius
        JointRegistry.Joint lunateJoint = JointRegistry.getJoint(Bone.LUNATE_LEFT);
        assertNotNull(lunateJoint);
        assertEquals(Bone.RADIUS_LEFT, lunateJoint.parent());

        // Thumb metacarpal connects to Trapezium
        JointRegistry.Joint thumbMetacarpalJoint = JointRegistry.getJoint(Bone.METACARPAL_1_LEFT);
        assertNotNull(thumbMetacarpalJoint);
        assertEquals(Bone.TRAPEZIUM_LEFT, thumbMetacarpalJoint.parent());
        assertEquals(JointType.SADDLE, thumbMetacarpalJoint.type(), "Thumb base should be saddle");

        // Thumb proximal phalanx connects to metacarpal
        JointRegistry.Joint thumbProximalJoint = JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_THUMB_LEFT);
        assertNotNull(thumbProximalJoint);
        assertEquals(Bone.METACARPAL_1_LEFT, thumbProximalJoint.parent());
        assertEquals(JointType.HINGE, thumbProximalJoint.type());

        // Index finger knuckle
        JointRegistry.Joint indexProximalJoint = JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_INDEX_FINGER_LEFT);
        assertNotNull(indexProximalJoint);
        assertEquals(Bone.METACARPAL_2_LEFT, indexProximalJoint.parent());
        assertEquals(JointType.CONDYLOID, indexProximalJoint.type(), "Knuckle should be condyloid");
    }

    /**
     * Test right upper limb mirrors left.
     */
    @Test
    public void shouldHaveCorrectRightUpperLimbHierarchy() {
        // Humerus connects to Scapula
        JointRegistry.Joint humerusJoint = JointRegistry.getJoint(Bone.HUMERUS_RIGHT);
        assertNotNull(humerusJoint);
        assertEquals(Bone.SCAPULA_RIGHT, humerusJoint.parent());
        assertEquals(JointType.BALL_AND_SOCKET, humerusJoint.type());

        // Thumb
        JointRegistry.Joint thumbMetacarpalJoint = JointRegistry.getJoint(Bone.METACARPAL_1_RIGHT);
        assertNotNull(thumbMetacarpalJoint);
        assertEquals(Bone.TRAPEZIUM_RIGHT, thumbMetacarpalJoint.parent());
        assertEquals(JointType.SADDLE, thumbMetacarpalJoint.type());
    }

    /**
     * Test left lower limb connections.
     */
    @Test
    public void shouldHaveCorrectLeftLowerLimbHierarchy() {
        // Hip bone connects to Sacrum
        JointRegistry.Joint hipJoint = JointRegistry.getJoint(Bone.HIP_BONE_LEFT);
        assertNotNull(hipJoint);
        assertEquals(Bone.SACRUM, hipJoint.parent());
        assertEquals(JointType.FIBROUS, hipJoint.type(), "SI joint should be fibrous");

        // Femur connects to Hip bone
        JointRegistry.Joint femurJoint = JointRegistry.getJoint(Bone.FEMUR_LEFT);
        assertNotNull(femurJoint);
        assertEquals(Bone.HIP_BONE_LEFT, femurJoint.parent());
        assertEquals(JointType.BALL_AND_SOCKET, femurJoint.type(), "Hip should be ball and socket");

        // Tibia connects to Femur
        JointRegistry.Joint tibiaJoint = JointRegistry.getJoint(Bone.TIBIA_LEFT);
        assertNotNull(tibiaJoint);
        assertEquals(Bone.FEMUR_LEFT, tibiaJoint.parent());
        assertEquals(JointType.HINGE, tibiaJoint.type(), "Knee should be hinge");

        // Patella connects to Femur
        JointRegistry.Joint patellaJoint = JointRegistry.getJoint(Bone.PATELLA_LEFT);
        assertNotNull(patellaJoint);
        assertEquals(Bone.FEMUR_LEFT, patellaJoint.parent());
        assertEquals(JointType.GLIDING, patellaJoint.type());
    }

    /**
     * Test left foot connections.
     */
    @Test
    public void shouldHaveCorrectLeftFootHierarchy() {
        // Talus connects to Tibia
        JointRegistry.Joint talusJoint = JointRegistry.getJoint(Bone.TALUS_LEFT);
        assertNotNull(talusJoint);
        assertEquals(Bone.TIBIA_LEFT, talusJoint.parent());
        assertEquals(JointType.HINGE, talusJoint.type(), "Ankle should be hinge");

        // Calcaneus connects to Talus
        JointRegistry.Joint calcaneusJoint = JointRegistry.getJoint(Bone.CALCANEUS_LEFT);
        assertNotNull(calcaneusJoint);
        assertEquals(Bone.TALUS_LEFT, calcaneusJoint.parent());

        // Big toe metatarsal connects to Medial Cuneiform
        JointRegistry.Joint bigToeMetatarsalJoint = JointRegistry.getJoint(Bone.METATARSAL_1_LEFT);
        assertNotNull(bigToeMetatarsalJoint);
        assertEquals(Bone.MEDIAL_CUNEIFORM_LEFT, bigToeMetatarsalJoint.parent());

        // Big toe proximal phalanx
        JointRegistry.Joint bigToeProximalJoint = JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_BIG_TOE_LEFT);
        assertNotNull(bigToeProximalJoint);
        assertEquals(Bone.METATARSAL_1_LEFT, bigToeProximalJoint.parent());
        assertEquals(JointType.CONDYLOID, bigToeProximalJoint.type());
    }

    /**
     * Test right lower limb mirrors left.
     */
    @Test
    public void shouldHaveCorrectRightLowerLimbHierarchy() {
        // Femur connects to Hip bone
        JointRegistry.Joint femurJoint = JointRegistry.getJoint(Bone.FEMUR_RIGHT);
        assertNotNull(femurJoint);
        assertEquals(Bone.HIP_BONE_RIGHT, femurJoint.parent());
        assertEquals(JointType.BALL_AND_SOCKET, femurJoint.type());

        // Big toe
        JointRegistry.Joint bigToeProximalJoint = JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_BIG_TOE_RIGHT);
        assertNotNull(bigToeProximalJoint);
        assertEquals(Bone.METATARSAL_1_RIGHT, bigToeProximalJoint.parent());
        assertEquals(JointType.CONDYLOID, bigToeProximalJoint.type());
    }

    /**
     * Test that all ribs (2-12) are present.
     */
    @Test
    public void shouldHaveAllRibs() {
        for (int i = 1; i <= 12; i++) {
            Bone leftRib = Bone.valueOf("RIB_" + i + "_LEFT");
            Bone rightRib = Bone.valueOf("RIB_" + i + "_RIGHT");
            
            assertNotNull(JointRegistry.getJoint(leftRib), "Rib " + i + " left should have a joint");
            assertNotNull(JointRegistry.getJoint(rightRib), "Rib " + i + " right should have a joint");
        }
    }

    /**
     * Test that all fingers are present for left hand.
     */
    @Test
    public void shouldHaveAllLeftFingers() {
        // Thumb (2 phalanges)
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_THUMB_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_THUMB_LEFT));

        // Index finger (3 phalanges)
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_INDEX_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_INDEX_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_INDEX_FINGER_LEFT));

        // Middle finger
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_MIDDLE_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_MIDDLE_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_MIDDLE_FINGER_LEFT));

        // Ring finger
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_RING_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_RING_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_RING_FINGER_LEFT));

        // Little finger
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_LITTLE_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_LITTLE_FINGER_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_LITTLE_FINGER_LEFT));
    }

    /**
     * Test that all toes are present for left foot.
     */
    @Test
    public void shouldHaveAllLeftToes() {
        // Big toe (2 phalanges)
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_BIG_TOE_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_BIG_TOE_LEFT));

        // Toe 2 (3 phalanges)
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_TOE_2_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_TOE_2_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_TOE_2_LEFT));

        // Toe 3
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_TOE_3_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_TOE_3_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_TOE_3_LEFT));

        // Toe 4
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_TOE_4_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_TOE_4_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_TOE_4_LEFT));

        // Little toe
        assertNotNull(JointRegistry.getJoint(Bone.PROXIMAL_PHALANX_LITTLE_TOE_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.MIDDLE_PHALANX_LITTLE_TOE_LEFT));
        assertNotNull(JointRegistry.getJoint(Bone.DISTAL_PHALANX_LITTLE_TOE_LEFT));
    }

    /**
     * Test that the Joint record works correctly.
     */
    @Test
    public void shouldCreateJointRecordCorrectly() {
        JointRegistry.Joint testJoint = new JointRegistry.Joint(Bone.SACRUM, JointType.CARTILAGINOUS, JointLimits.LOCKED);
        assertEquals(Bone.SACRUM, testJoint.parent());
        assertEquals(JointType.CARTILAGINOUS, testJoint.type());
        assertNotNull(testJoint.limits());
    }

    /**
     * Test special joint types - PIVOT for neck rotation and CONDYLOID for nodding.
     */
    @Test
    public void shouldHavePivotJointForNeckRotation() {
        // Atlas-Axis joint allows rotation (shaking head "No")
        JointRegistry.Joint atlasJoint = JointRegistry.getJoint(Bone.CERVICAL_1_ATLAS);
        assertNotNull(atlasJoint);
        assertEquals(JointType.PIVOT, atlasJoint.type(), "Atlas-Axis joint should be PIVOT for head rotation");
        
        // Occipital-Atlas joint allows nodding (nodding head "Yes")
        JointRegistry.Joint occipitalJoint = JointRegistry.getJoint(Bone.OCCIPITAL);
        assertNotNull(occipitalJoint);
        assertEquals(JointType.CONDYLOID, occipitalJoint.type(), "Occipital-Atlas joint should be CONDYLOID for head nodding");
    }

    /**
     * Test that all joints have JointLimits defined.
     */
    @Test
    public void shouldHaveJointLimitsForAllJoints() {
        // Test a few representative joints
        JointRegistry.Joint shoulderJoint = JointRegistry.getJoint(Bone.HUMERUS_LEFT);
        assertNotNull(shoulderJoint.limits(), "Shoulder should have limits defined");
        
        JointRegistry.Joint elbowJoint = JointRegistry.getJoint(Bone.ULNA_LEFT);
        assertNotNull(elbowJoint.limits(), "Elbow should have limits defined");
        
        JointRegistry.Joint hipJoint = JointRegistry.getJoint(Bone.FEMUR_LEFT);
        assertNotNull(hipJoint.limits(), "Hip should have limits defined");
        
        JointRegistry.Joint kneeJoint = JointRegistry.getJoint(Bone.TIBIA_LEFT);
        assertNotNull(kneeJoint.limits(), "Knee should have limits defined");
    }

    /**
     * Test that locked joints (like skull sutures) have LOCKED limits.
     */
    @Test
    public void shouldHaveLockedLimitsForSutures() {
        JointRegistry.Joint frontalJoint = JointRegistry.getJoint(Bone.FRONTAL);
        assertNotNull(frontalJoint.limits());
        assertEquals(JointLimits.LOCKED, frontalJoint.limits(), "Skull sutures should be LOCKED");
        
        JointRegistry.Joint coccyxJoint = JointRegistry.getJoint(Bone.COCCYX);
        assertNotNull(coccyxJoint.limits());
        assertEquals(JointLimits.LOCKED, coccyxJoint.limits(), "Coccyx should be LOCKED");
    }
}
