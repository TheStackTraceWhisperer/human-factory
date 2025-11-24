# GitHub Copilot Instructions

## Java Version

**CRITICAL: This project MUST use Java 21.**

- **NEVER** change the `maven.compiler.release` property in `pom.xml` from 21 to any other version.
- **DO NOT** modify the Java version for ANY reason, including:
  - Build compatibility issues
  - Environment limitations
  - Missing Java installations
  - Any other technical constraints
- If Java 21 is not available in the environment, **STOP** and inform the user rather than changing the version.
- If builds fail due to Java version issues, **DO NOT** downgrade the version. Instead, investigate alternative solutions or request user guidance.

The project owner has explicitly required Java 21, and this requirement must be respected regardless of any circumstances you may encounter.
