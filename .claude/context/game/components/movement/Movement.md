# Movement

**File:** `core/.../game/components/movement/Movement.java`
**Extends:** `Component`
**Type:** Abstract base
**Role:** Provides a normalized direction vector and moving flag. Subclasses implement different movement strategies.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `direction` | `Vector2` | Normalized movement direction (0,0 when still) |
| `moving` | `boolean` | True if entity is currently moving |

## Abstract Method
- `updateMovement(float dt)` — subclasses fill in `direction` and `moving` each frame

## Implementations
| Class | Strategy |
|-------|---------|
| `PlayerController` | WASD input |
| `SlimeMovement` | Random wander AI |
| `Projectile` | Fixed direction, always moving |

## Rules
- `direction` must always be normalized or zero — never raw input values
- `moving` flag is read by animation controllers to switch walk/idle clips
- `EntityMover` reads from this component to apply actual position change
