package com.humanfactory;

public enum JointType {
    /** * 3 Degrees of Freedom (DOF). 
     * Allows rotation on all axes. 
     * Examples: Shoulder, Hip.
     */
    BALL_AND_SOCKET,

    /** * 1 DOF. 
     * Allows flexion/extension only. 
     * Examples: Elbow, Knee, Finger joints.
     */
    HINGE,

    /** * 1 DOF. 
     * Allows rotation around a single axis. 
     * Examples: Atlas/Axis (neck rotation), Radius/Ulna (forearm twist).
     */
    PIVOT,

    /** * 2 DOF. 
     * Allows flexion/extension and abduction/adduction (side-to-side), but no rotation. 
     * Examples: Wrist, Knuckles.
     */
    CONDYLOID,

    /** * 2 DOF. 
     * Similar to Condyloid but allows greater movement, specifically opposition. 
     * Examples: Thumb base (Carpometacarpal joint).
     */
    SADDLE,

    /** * Translational/Sliding. 
     * Examples: Clavicle, Tarsals in the foot.
     */
    GLIDING,

    /** * Fixed/Fused. 
     * Mechanically rigid, though biologically distinct. 
     * Examples: Skull sutures, Pelvic fusion (Ilium to Sacrum).
     */
    FIBROUS,
    
    /**
     * Limited movement via flexible cartilage pads.
     * Examples: Vertebral discs, Pubic Symphysis.
     */
    CARTILAGINOUS
}
