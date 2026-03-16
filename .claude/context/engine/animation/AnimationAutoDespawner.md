# AnimationAutoDespawner

**File:** `core/.../engine/animation/AnimationAutoDespawner.java`
**Extends/Type:** `Component`
**Role:** Destroys its GameObject when the current non-looping animation finishes playing.

## Fields
| Field | Type | Purpose |
|---|---|---|
| `renderer` | `SimpleAnimRenderer` | Renderer to poll for animation completion |

## Key Methods / Logic
- `start()`: resolves `SimpleAnimRenderer` from the same GameObject.
- `update(dt)`: if `renderer != null && renderer.isFinished()` → calls `getGameObject().destroy()`.

## Dependencies
- `SimpleAnimRenderer.isFinished()`

## Rules
- Only fires when the animation is non-looping AND has reached its end (`isFinished()` is false for looping clips).
- Used on `FireballExplosion` and `SlimeDeathEffect`.
