# Vector2

**File:** `core/.../engine/math/Vector2.java`
**Role:** Mutable 2D float vector for directions, offsets, and velocities.

## Fields
- `x`, `y` — float components (public)

## Methods
| Method | Returns | Notes |
|--------|---------|-------|
| `add(float x, float y)` | `this` | In-place addition |
| `scale(float s)` | `this` | Uniform scale |
| `normalize()` | `this` | Unit vector (safe — checks length > 0) |
| `length()` | `float` | Euclidean length |
| `copy()` | `Vector2` | New instance with same values |
| `set(float x, float y)` | `this` | Setter |

## Rules
- Mutable — methods modify in-place and return `this` for chaining
- Always normalize direction vectors before multiplying by speed
- Do not use LibGDX's `Vector2` — use this engine-owned type

## Known Issues
- `translate()` method returns a new Vector2 copy but appears unused — dead code
