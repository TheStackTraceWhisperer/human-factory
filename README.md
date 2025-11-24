# Human Factory

A procedural human skeleton generator that bridges anatomical identity with physics simulation. Human Factory generates complete, rigged skeletal hierarchies from genetic parameters using the JOML (Java OpenGL Mathematics Library) for 3D math.

## Features

### BodyGenerator
The core of the Human Factory system - a procedural factory that turns DNA parameters into complete, physically-accurate skeletons.

- **Anthropometric Scaling**: Uses the "8-Head" artistic standard for realistic proportions
- **Automatic Symmetry**: Handles Left/Right mirroring automatically
- **Iterative Generation**: Efficiently generates vertebrae, ribs, and digits without code duplication
- **Physics-Ready**: Provides simplified Box, Capsule, and Sphere collision shapes
- **Joint Integration**: Automatically fetches joint constraints from the JointRegistry

### Key Components

#### BoneShape
Sealed interface representing collision shapes for bones:
- `Box` - Cuboid collision shape with half-extents
- `Capsule` - Cylinder with hemispherical caps
- `Sphere` - Simple spherical collision shape

#### BoneDefinition
Record containing complete physical and geometric properties:
- Length, position, rotation
- Mass distribution
- Collision shapes
- Joint limits (from JointRegistry)

#### BodyDNA
Genetic blueprint record with parameters:
- `heightMeters` - Overall height (e.g., 1.80m)
- `massKg` - Total body mass (e.g., 78kg)
- `buildFactor` - Width/thickness modifier (0.5=slender, 1.5=stocky)
- `headRatio` - Head size as proportion of height (default 0.125)
- `legRatio` - Leg length as proportion of height (default 0.48)

## Building

This project requires **Java 21**:

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
mvn clean verify
```

## Running the Demo

```bash
mvn exec:java -Dexec.mainClass="com.humanfactory.App"
```

Example output:
```
=== Human Factory BodyGenerator Demo ===

Generating skeleton with DNA parameters:
  Height: 1.8 meters
  Mass: 78.0 kg
  Build Factor: 1.0
  Head Ratio: 0.125
  Leg Ratio: 0.48

Generated 164 bones.
```

## Usage Example

```java
// Create generator
BodyGenerator generator = new BodyGenerator();

// Define DNA parameters
BodyGenerator.BodyDNA dna = new BodyGenerator.BodyDNA(
    1.80f,   // height in meters
    78.0f,   // mass in kg
    1.0f,    // build factor (1.0 = average)
    0.125f,  // head ratio (1/8th)
    0.48f    // leg ratio (48% of height)
);

// Or use preset
BodyGenerator.BodyDNA averageMale = BodyGenerator.BodyDNA.averageMale();

// Generate skeleton
Map<Bone, BoneDefinition> skeleton = generator.generate(dna);

// Access individual bones
BoneDefinition femur = skeleton.get(Bone.FEMUR_LEFT);
System.out.println("Femur length: " + femur.length() + "m");
System.out.println("Femur mass: " + femur.mass() + "kg");
```

## Architecture

### Skeleton Hierarchy
The generator creates bones in a hierarchical structure with the **Sacrum** as the root:

1. **Axial Skeleton**
   - Spine: 5 Lumbar + 12 Thoracic + 7 Cervical vertebrae
   - Ribs: 12 pairs attached to thoracic vertebrae
   - Head: Skull bones (occipital, frontal, parietal, temporal, sphenoid, mandible)

2. **Appendicular Skeleton**
   - Upper limbs: Clavicle, Scapula, Humerus, Radius, Ulna, Hand (27 bones each)
   - Lower limbs: Pelvis, Femur, Patella, Tibia, Fibula, Foot (26 bones each)

### Joint Constraints
Joint limits are automatically retrieved from the `JointRegistry` which defines:
- Joint type (ball-and-socket, hinge, pivot, etc.)
- Rotational limits (pitch, yaw, roll)
- Parent-child relationships

## Testing

The project includes comprehensive tests:
```bash
mvn test
```

Test coverage includes:
- Complete skeleton generation (164 bones)
- Spine and rib generation
- Head bones
- Symmetric limb generation
- Hand and foot digit generation
- Joint limit integration
- Custom DNA parameters

**Total: 48 tests, all passing**

## Dependencies

- **JOML 1.10.8** - Java OpenGL Mathematics Library for 3D math
- **JUnit 5.11.0** - Testing framework

## License

MIT License