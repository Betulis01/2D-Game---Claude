# CharacterDirection

**File:** `core/.../engine/animation/CharacterDirection.java`
**Extends/Type:** `enum`
**Role:** 8-direction enum for character facing, with helpers for atlas sprite lookup and horizontal flip detection.

## Values
Enum order is **fixed**: N, NE, E, SE, S, SW, W, NW (do not reorder — `fromVector` uses ordinal index).

## Key Methods / Logic
| Method | Notes |
|---|---|
| `fromVector(dx, dy)` | Compass bearing formula: `(90 - angle + 360) % 360`. 0=N, 90=E, 180=S, 270=W. Divides into 8 sectors of 45°. |
| `getDrawnDir()` | Maps left-side dirs to right-side equivalents: SW→SE, W→E, NW→NE, others→self. |
| `isFlipped()` | True for SW, W, NW — these use horizontally mirrored sprites. |
| `getAtlasSuffix()` | Returns "n","ne","e","se","s" for the 5 drawn directions (calls `getDrawnDir()` first). |

## Rules
- Enum order must remain N, NE, E, SE, S, SW, W, NW — `fromVector` uses `values()[sector]`.
- Only 5 directions (N, NE, E, SE, S) have real atlas sprites; left-side dirs are mirrored at runtime.
