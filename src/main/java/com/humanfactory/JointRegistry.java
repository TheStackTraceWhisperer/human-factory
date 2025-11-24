package com.humanfactory;

import java.util.Map;
import static java.util.Map.entry;

public class JointRegistry {

    // Now includes the Limits
    public record Joint(Bone parent, JointType type, JointLimits limits) {}

    private static final Map<Bone, Joint> REGISTRY = Map.ofEntries(

        // ============================================================
        // AXIAL SKELETON (Spine & Head)
        // ============================================================
        
        // --- Spine (Building Up from Sacrum) ---
        // Sacrum is ROOT, so Coccyx is a child of it
        entry(Bone.COCCYX,        new Joint(Bone.SACRUM, JointType.CARTILAGINOUS, JointLimits.LOCKED)),
        
        // Lumbar - Small individual movements that aggregate to large curves
        entry(Bone.LUMBAR_5,      new Joint(Bone.SACRUM, JointType.CARTILAGINOUS, JointLimits.ball(-15, 5, -5, 5, -5, 5))),
        entry(Bone.LUMBAR_4,      new Joint(Bone.LUMBAR_5, JointType.CARTILAGINOUS, JointLimits.ball(-10, 5, -5, 5, -5, 5))),
        entry(Bone.LUMBAR_3,      new Joint(Bone.LUMBAR_4, JointType.CARTILAGINOUS, JointLimits.ball(-10, 5, -5, 5, -5, 5))),
        entry(Bone.LUMBAR_2,      new Joint(Bone.LUMBAR_3, JointType.CARTILAGINOUS, JointLimits.ball(-10, 5, -5, 5, -5, 5))),
        entry(Bone.LUMBAR_1,      new Joint(Bone.LUMBAR_2, JointType.CARTILAGINOUS, JointLimits.ball(-10, 5, -5, 5, -5, 5))),

        // Thoracic - More twist capability
        entry(Bone.THORACIC_12,   new Joint(Bone.LUMBAR_1, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_11,   new Joint(Bone.THORACIC_12, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_10,   new Joint(Bone.THORACIC_11, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_9,    new Joint(Bone.THORACIC_10, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_8,    new Joint(Bone.THORACIC_9, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_7,    new Joint(Bone.THORACIC_8, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_6,    new Joint(Bone.THORACIC_7, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_5,    new Joint(Bone.THORACIC_6, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_4,    new Joint(Bone.THORACIC_5, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_3,    new Joint(Bone.THORACIC_4, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_2,    new Joint(Bone.THORACIC_3, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),
        entry(Bone.THORACIC_1,    new Joint(Bone.THORACIC_2, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -15, 15, -5, 5))),

        // Cervical (Neck)
        entry(Bone.CERVICAL_7,       new Joint(Bone.THORACIC_1, JointType.CARTILAGINOUS, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.CERVICAL_6,       new Joint(Bone.CERVICAL_7, JointType.CARTILAGINOUS, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.CERVICAL_5,       new Joint(Bone.CERVICAL_6, JointType.CARTILAGINOUS, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.CERVICAL_4,       new Joint(Bone.CERVICAL_5, JointType.CARTILAGINOUS, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.CERVICAL_3,       new Joint(Bone.CERVICAL_4, JointType.CARTILAGINOUS, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.CERVICAL_2_AXIS,  new Joint(Bone.CERVICAL_3, JointType.CARTILAGINOUS, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.CERVICAL_1_ATLAS, new Joint(Bone.CERVICAL_2_AXIS, JointType.PIVOT, JointLimits.pivot(-80, 80))), // Head shake "No" (Rotation)

        // --- Skull ---
        entry(Bone.OCCIPITAL,     new Joint(Bone.CERVICAL_1_ATLAS, JointType.CONDYLOID, JointLimits.biaxial(-25, 25, -10, 10))), // Head nod "Yes" (Flexion/Extension)
        entry(Bone.SPHENOID,      new Joint(Bone.OCCIPITAL, JointType.FIBROUS, JointLimits.LOCKED)),
        entry(Bone.FRONTAL,       new Joint(Bone.SPHENOID, JointType.FIBROUS, JointLimits.LOCKED)),
        entry(Bone.PARIETAL_LEFT, new Joint(Bone.OCCIPITAL, JointType.FIBROUS, JointLimits.LOCKED)),
        entry(Bone.PARIETAL_RIGHT, new Joint(Bone.OCCIPITAL, JointType.FIBROUS, JointLimits.LOCKED)),
        entry(Bone.TEMPORAL_LEFT, new Joint(Bone.PARIETAL_LEFT, JointType.FIBROUS, JointLimits.LOCKED)),
        entry(Bone.TEMPORAL_RIGHT, new Joint(Bone.PARIETAL_RIGHT, JointType.FIBROUS, JointLimits.LOCKED)),
        entry(Bone.MANDIBLE,      new Joint(Bone.TEMPORAL_LEFT, JointType.HINGE, JointLimits.hinge(0, 45))), // Jaw open

        // --- Rib Cage ---
        entry(Bone.STERNUM,       new Joint(Bone.THORACIC_4, JointType.CARTILAGINOUS, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_1_LEFT,    new Joint(Bone.THORACIC_1, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_1_RIGHT,   new Joint(Bone.THORACIC_1, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_2_LEFT,    new Joint(Bone.THORACIC_2, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_2_RIGHT,   new Joint(Bone.THORACIC_2, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_3_LEFT,    new Joint(Bone.THORACIC_3, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_3_RIGHT,   new Joint(Bone.THORACIC_3, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_4_LEFT,    new Joint(Bone.THORACIC_4, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_4_RIGHT,   new Joint(Bone.THORACIC_4, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_5_LEFT,    new Joint(Bone.THORACIC_5, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_5_RIGHT,   new Joint(Bone.THORACIC_5, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_6_LEFT,    new Joint(Bone.THORACIC_6, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_6_RIGHT,   new Joint(Bone.THORACIC_6, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_7_LEFT,    new Joint(Bone.THORACIC_7, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_7_RIGHT,   new Joint(Bone.THORACIC_7, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_8_LEFT,    new Joint(Bone.THORACIC_8, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_8_RIGHT,   new Joint(Bone.THORACIC_8, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_9_LEFT,    new Joint(Bone.THORACIC_9, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_9_RIGHT,   new Joint(Bone.THORACIC_9, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_10_LEFT,   new Joint(Bone.THORACIC_10, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_10_RIGHT,  new Joint(Bone.THORACIC_10, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_11_LEFT,   new Joint(Bone.THORACIC_11, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_11_RIGHT,  new Joint(Bone.THORACIC_11, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_12_LEFT,   new Joint(Bone.THORACIC_12, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.RIB_12_RIGHT,  new Joint(Bone.THORACIC_12, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),


        // ============================================================
        // UPPER LIMBS (LEFT)
        // ============================================================
        
        // Pectoral Girdle
        entry(Bone.CLAVICLE_LEFT, new Joint(Bone.STERNUM, JointType.SADDLE, JointLimits.biaxial(-10, 20, -10, 10))),
        entry(Bone.SCAPULA_LEFT,  new Joint(Bone.CLAVICLE_LEFT, JointType.GLIDING, JointLimits.ball(-20, 20, -20, 20, -20, 20))),

        // Arm
        entry(Bone.HUMERUS_LEFT,  new Joint(Bone.SCAPULA_LEFT, JointType.BALL_AND_SOCKET, JointLimits.ball(-90, 180, -90, 90, -45, 135))),
        entry(Bone.ULNA_LEFT,     new Joint(Bone.HUMERUS_LEFT, JointType.HINGE, JointLimits.hinge(0, 145))),
        entry(Bone.RADIUS_LEFT,   new Joint(Bone.HUMERUS_LEFT, JointType.PIVOT, JointLimits.pivot(-90, 90))),

        // Wrist
        entry(Bone.LUNATE_LEFT,   new Joint(Bone.RADIUS_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-60, 60, -30, 30))),
        entry(Bone.SCAPHOID_LEFT, new Joint(Bone.RADIUS_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.TRIQUETRUM_LEFT, new Joint(Bone.LUNATE_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PISIFORM_LEFT,   new Joint(Bone.TRIQUETRUM_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.CAPITATE_LEFT, new Joint(Bone.LUNATE_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.HAMATE_LEFT,   new Joint(Bone.CAPITATE_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.TRAPEZIUM_LEFT, new Joint(Bone.SCAPHOID_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.TRAPEZOID_LEFT, new Joint(Bone.SCAPHOID_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        
        // Fingers - Thumb
        entry(Bone.METACARPAL_1_LEFT,           new Joint(Bone.TRAPEZIUM_LEFT, JointType.SADDLE, JointLimits.ball(-20, 20, -20, 20, -45, 45))),
        entry(Bone.PROXIMAL_PHALANX_THUMB_LEFT, new Joint(Bone.METACARPAL_1_LEFT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_THUMB_LEFT,   new Joint(Bone.PROXIMAL_PHALANX_THUMB_LEFT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers - Index
        entry(Bone.METACARPAL_2_LEFT,                   new Joint(Bone.CAPITATE_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_INDEX_FINGER_LEFT,  new Joint(Bone.METACARPAL_2_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_INDEX_FINGER_LEFT,    new Joint(Bone.PROXIMAL_PHALANX_INDEX_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_INDEX_FINGER_LEFT,    new Joint(Bone.MIDDLE_PHALANX_INDEX_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers - Middle
        entry(Bone.METACARPAL_3_LEFT,                   new Joint(Bone.CAPITATE_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_MIDDLE_FINGER_LEFT, new Joint(Bone.METACARPAL_3_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_MIDDLE_FINGER_LEFT,   new Joint(Bone.PROXIMAL_PHALANX_MIDDLE_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_MIDDLE_FINGER_LEFT,   new Joint(Bone.MIDDLE_PHALANX_MIDDLE_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers - Ring
        entry(Bone.METACARPAL_4_LEFT,                 new Joint(Bone.HAMATE_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_RING_FINGER_LEFT, new Joint(Bone.METACARPAL_4_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_RING_FINGER_LEFT,   new Joint(Bone.PROXIMAL_PHALANX_RING_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_RING_FINGER_LEFT,   new Joint(Bone.MIDDLE_PHALANX_RING_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers - Little
        entry(Bone.METACARPAL_5_LEFT,                   new Joint(Bone.HAMATE_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_LITTLE_FINGER_LEFT, new Joint(Bone.METACARPAL_5_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_LITTLE_FINGER_LEFT,   new Joint(Bone.PROXIMAL_PHALANX_LITTLE_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_LITTLE_FINGER_LEFT,   new Joint(Bone.MIDDLE_PHALANX_LITTLE_FINGER_LEFT, JointType.HINGE, JointLimits.hinge(0, 80))),


        // ============================================================
        // UPPER LIMBS (RIGHT) - Mirrored
        // ============================================================
        
        entry(Bone.CLAVICLE_RIGHT, new Joint(Bone.STERNUM, JointType.SADDLE, JointLimits.biaxial(-10, 20, -10, 10))),
        entry(Bone.SCAPULA_RIGHT,  new Joint(Bone.CLAVICLE_RIGHT, JointType.GLIDING, JointLimits.ball(-20, 20, -20, 20, -20, 20))),
        entry(Bone.HUMERUS_RIGHT,  new Joint(Bone.SCAPULA_RIGHT, JointType.BALL_AND_SOCKET, JointLimits.ball(-90, 180, -90, 90, -45, 135))),
        entry(Bone.ULNA_RIGHT,     new Joint(Bone.HUMERUS_RIGHT, JointType.HINGE, JointLimits.hinge(0, 145))),
        entry(Bone.RADIUS_RIGHT,   new Joint(Bone.HUMERUS_RIGHT, JointType.PIVOT, JointLimits.pivot(-90, 90))),
        
        // Wrist Right
        entry(Bone.LUNATE_RIGHT,   new Joint(Bone.RADIUS_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-60, 60, -30, 30))),
        entry(Bone.SCAPHOID_RIGHT, new Joint(Bone.RADIUS_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.TRIQUETRUM_RIGHT, new Joint(Bone.LUNATE_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PISIFORM_RIGHT,   new Joint(Bone.TRIQUETRUM_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.CAPITATE_RIGHT, new Joint(Bone.LUNATE_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.HAMATE_RIGHT,   new Joint(Bone.CAPITATE_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.TRAPEZIUM_RIGHT, new Joint(Bone.SCAPHOID_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.TRAPEZOID_RIGHT, new Joint(Bone.SCAPHOID_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        
        // Fingers Right - Thumb
        entry(Bone.METACARPAL_1_RIGHT,           new Joint(Bone.TRAPEZIUM_RIGHT, JointType.SADDLE, JointLimits.ball(-20, 20, -20, 20, -45, 45))),
        entry(Bone.PROXIMAL_PHALANX_THUMB_RIGHT, new Joint(Bone.METACARPAL_1_RIGHT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_THUMB_RIGHT,   new Joint(Bone.PROXIMAL_PHALANX_THUMB_RIGHT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers Right - Index
        entry(Bone.METACARPAL_2_RIGHT,                   new Joint(Bone.CAPITATE_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_INDEX_FINGER_RIGHT,  new Joint(Bone.METACARPAL_2_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_INDEX_FINGER_RIGHT,    new Joint(Bone.PROXIMAL_PHALANX_INDEX_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_INDEX_FINGER_RIGHT,    new Joint(Bone.MIDDLE_PHALANX_INDEX_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers Right - Middle
        entry(Bone.METACARPAL_3_RIGHT,                   new Joint(Bone.CAPITATE_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_MIDDLE_FINGER_RIGHT, new Joint(Bone.METACARPAL_3_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_MIDDLE_FINGER_RIGHT,   new Joint(Bone.PROXIMAL_PHALANX_MIDDLE_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_MIDDLE_FINGER_RIGHT,   new Joint(Bone.MIDDLE_PHALANX_MIDDLE_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers Right - Ring
        entry(Bone.METACARPAL_4_RIGHT,                 new Joint(Bone.HAMATE_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_RING_FINGER_RIGHT, new Joint(Bone.METACARPAL_4_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_RING_FINGER_RIGHT,   new Joint(Bone.PROXIMAL_PHALANX_RING_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_RING_FINGER_RIGHT,   new Joint(Bone.MIDDLE_PHALANX_RING_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 80))),

        // Fingers Right - Little
        entry(Bone.METACARPAL_5_RIGHT,                   new Joint(Bone.HAMATE_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_LITTLE_FINGER_RIGHT, new Joint(Bone.METACARPAL_5_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 90, -20, 20))),
        entry(Bone.MIDDLE_PHALANX_LITTLE_FINGER_RIGHT,   new Joint(Bone.PROXIMAL_PHALANX_LITTLE_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 100))),
        entry(Bone.DISTAL_PHALANX_LITTLE_FINGER_RIGHT,   new Joint(Bone.MIDDLE_PHALANX_LITTLE_FINGER_RIGHT, JointType.HINGE, JointLimits.hinge(0, 80))),


        // ============================================================
        // LOWER LIMBS (LEFT)
        // ============================================================
        
        // Pelvis (Hips connect to Sacrum)
        entry(Bone.HIP_BONE_LEFT, new Joint(Bone.SACRUM, JointType.FIBROUS, JointLimits.LOCKED)),
        
        // Leg
        entry(Bone.FEMUR_LEFT,    new Joint(Bone.HIP_BONE_LEFT, JointType.BALL_AND_SOCKET, JointLimits.ball(-20, 120, -30, 30, -10, 45))),
        entry(Bone.PATELLA_LEFT,  new Joint(Bone.FEMUR_LEFT, JointType.GLIDING, JointLimits.ball(-10, 10, -5, 5, -5, 5))),
        entry(Bone.TIBIA_LEFT,    new Joint(Bone.FEMUR_LEFT, JointType.HINGE, JointLimits.hinge(0, 150))),
        entry(Bone.FIBULA_LEFT,   new Joint(Bone.TIBIA_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        
        // Foot
        entry(Bone.TALUS_LEFT,     new Joint(Bone.TIBIA_LEFT, JointType.HINGE, JointLimits.hinge(-20, 50))),
        entry(Bone.CALCANEUS_LEFT, new Joint(Bone.TALUS_LEFT, JointType.GLIDING, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.NAVICULAR_LEFT, new Joint(Bone.TALUS_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.CUBOID_LEFT,    new Joint(Bone.CALCANEUS_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.MEDIAL_CUNEIFORM_LEFT, new Joint(Bone.NAVICULAR_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.INTERMEDIATE_CUNEIFORM_LEFT, new Joint(Bone.NAVICULAR_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.LATERAL_CUNEIFORM_LEFT, new Joint(Bone.NAVICULAR_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        
        // Toes - Big Toe
        entry(Bone.METATARSAL_1_LEFT,               new Joint(Bone.MEDIAL_CUNEIFORM_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_BIG_TOE_LEFT,   new Joint(Bone.METATARSAL_1_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.DISTAL_PHALANX_BIG_TOE_LEFT,     new Joint(Bone.PROXIMAL_PHALANX_BIG_TOE_LEFT, JointType.HINGE, JointLimits.hinge(0, 60))),

        // Toes - Toe 2
        entry(Bone.METATARSAL_2_LEFT,              new Joint(Bone.INTERMEDIATE_CUNEIFORM_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_TOE_2_LEFT,    new Joint(Bone.METATARSAL_2_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_TOE_2_LEFT,      new Joint(Bone.PROXIMAL_PHALANX_TOE_2_LEFT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_TOE_2_LEFT,      new Joint(Bone.MIDDLE_PHALANX_TOE_2_LEFT, JointType.HINGE, JointLimits.hinge(0, 50))),

        // Toes - Toe 3
        entry(Bone.METATARSAL_3_LEFT,              new Joint(Bone.LATERAL_CUNEIFORM_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_TOE_3_LEFT,    new Joint(Bone.METATARSAL_3_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_TOE_3_LEFT,      new Joint(Bone.PROXIMAL_PHALANX_TOE_3_LEFT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_TOE_3_LEFT,      new Joint(Bone.MIDDLE_PHALANX_TOE_3_LEFT, JointType.HINGE, JointLimits.hinge(0, 50))),

        // Toes - Toe 4
        entry(Bone.METATARSAL_4_LEFT,              new Joint(Bone.CUBOID_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_TOE_4_LEFT,    new Joint(Bone.METATARSAL_4_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_TOE_4_LEFT,      new Joint(Bone.PROXIMAL_PHALANX_TOE_4_LEFT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_TOE_4_LEFT,      new Joint(Bone.MIDDLE_PHALANX_TOE_4_LEFT, JointType.HINGE, JointLimits.hinge(0, 50))),

        // Toes - Little Toe
        entry(Bone.METATARSAL_5_LEFT,              new Joint(Bone.CUBOID_LEFT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_LITTLE_TOE_LEFT, new Joint(Bone.METATARSAL_5_LEFT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_LITTLE_TOE_LEFT,   new Joint(Bone.PROXIMAL_PHALANX_LITTLE_TOE_LEFT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_LITTLE_TOE_LEFT,   new Joint(Bone.MIDDLE_PHALANX_LITTLE_TOE_LEFT, JointType.HINGE, JointLimits.hinge(0, 50))),


        // ============================================================
        // LOWER LIMBS (RIGHT)
        // ============================================================

        entry(Bone.HIP_BONE_RIGHT, new Joint(Bone.SACRUM, JointType.FIBROUS, JointLimits.LOCKED)),
        entry(Bone.FEMUR_RIGHT,    new Joint(Bone.HIP_BONE_RIGHT, JointType.BALL_AND_SOCKET, JointLimits.ball(-20, 120, -30, 30, -10, 45))),
        entry(Bone.PATELLA_RIGHT,  new Joint(Bone.FEMUR_RIGHT, JointType.GLIDING, JointLimits.ball(-10, 10, -5, 5, -5, 5))),
        entry(Bone.TIBIA_RIGHT,    new Joint(Bone.FEMUR_RIGHT, JointType.HINGE, JointLimits.hinge(0, 150))),
        entry(Bone.FIBULA_RIGHT,   new Joint(Bone.TIBIA_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        
        entry(Bone.TALUS_RIGHT,     new Joint(Bone.TIBIA_RIGHT, JointType.HINGE, JointLimits.hinge(-20, 50))),
        entry(Bone.CALCANEUS_RIGHT, new Joint(Bone.TALUS_RIGHT, JointType.GLIDING, JointLimits.ball(-10, 10, -10, 10, -10, 10))),
        entry(Bone.NAVICULAR_RIGHT, new Joint(Bone.TALUS_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.CUBOID_RIGHT,    new Joint(Bone.CALCANEUS_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.MEDIAL_CUNEIFORM_RIGHT, new Joint(Bone.NAVICULAR_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.INTERMEDIATE_CUNEIFORM_RIGHT, new Joint(Bone.NAVICULAR_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.LATERAL_CUNEIFORM_RIGHT, new Joint(Bone.NAVICULAR_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        
        entry(Bone.METATARSAL_1_RIGHT,               new Joint(Bone.MEDIAL_CUNEIFORM_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_BIG_TOE_RIGHT,   new Joint(Bone.METATARSAL_1_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.DISTAL_PHALANX_BIG_TOE_RIGHT,     new Joint(Bone.PROXIMAL_PHALANX_BIG_TOE_RIGHT, JointType.HINGE, JointLimits.hinge(0, 60))),

        entry(Bone.METATARSAL_2_RIGHT,              new Joint(Bone.INTERMEDIATE_CUNEIFORM_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_TOE_2_RIGHT,    new Joint(Bone.METATARSAL_2_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_TOE_2_RIGHT,      new Joint(Bone.PROXIMAL_PHALANX_TOE_2_RIGHT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_TOE_2_RIGHT,      new Joint(Bone.MIDDLE_PHALANX_TOE_2_RIGHT, JointType.HINGE, JointLimits.hinge(0, 50))),

        entry(Bone.METATARSAL_3_RIGHT,              new Joint(Bone.LATERAL_CUNEIFORM_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_TOE_3_RIGHT,    new Joint(Bone.METATARSAL_3_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_TOE_3_RIGHT,      new Joint(Bone.PROXIMAL_PHALANX_TOE_3_RIGHT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_TOE_3_RIGHT,      new Joint(Bone.MIDDLE_PHALANX_TOE_3_RIGHT, JointType.HINGE, JointLimits.hinge(0, 50))),

        entry(Bone.METATARSAL_4_RIGHT,              new Joint(Bone.CUBOID_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_TOE_4_RIGHT,    new Joint(Bone.METATARSAL_4_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_TOE_4_RIGHT,      new Joint(Bone.PROXIMAL_PHALANX_TOE_4_RIGHT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_TOE_4_RIGHT,      new Joint(Bone.MIDDLE_PHALANX_TOE_4_RIGHT, JointType.HINGE, JointLimits.hinge(0, 50))),

        entry(Bone.METATARSAL_5_RIGHT,              new Joint(Bone.CUBOID_RIGHT, JointType.GLIDING, JointLimits.ball(-5, 5, -5, 5, -5, 5))),
        entry(Bone.PROXIMAL_PHALANX_LITTLE_TOE_RIGHT, new Joint(Bone.METATARSAL_5_RIGHT, JointType.CONDYLOID, JointLimits.biaxial(-10, 60, -10, 10))),
        entry(Bone.MIDDLE_PHALANX_LITTLE_TOE_RIGHT,   new Joint(Bone.PROXIMAL_PHALANX_LITTLE_TOE_RIGHT, JointType.HINGE, JointLimits.hinge(0, 60))),
        entry(Bone.DISTAL_PHALANX_LITTLE_TOE_RIGHT,   new Joint(Bone.MIDDLE_PHALANX_LITTLE_TOE_RIGHT, JointType.HINGE, JointLimits.hinge(0, 50)))
    );

    public static Joint getJoint(Bone bone) {
        return REGISTRY.get(bone);
    }
}
