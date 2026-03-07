# AABB

**File:** `core/.../engine/math/AABB.java`
**Role:** Axis-aligned bounding box. Used for collision detection, map bounds, and camera view bounds.

## Fields
- `x`, `y` — **min corner** (not center) — public floats
- `width`, `height` — extents — public floats

## Methods
| Method | Returns | Notes |
|--------|---------|-------|
| `minX()` | `float` | = x |
| `minY()` | `float` | = y |
| `maxX()` | `float` | = x + width |
| `maxY()` | `float` | = y + height |
| `intersects(AABB)` | `boolean` | Overlap test (inclusive) |
| `translate(dx, dy)` | `AABB` | Returns new translated copy |

## Rules
- `x/y` is always the **min corner**, never center
- Mutable — update in-place rather than allocating new instances per frame
- World-space only — no screen-space AABBs
- Do not use LibGDX `Rectangle` for engine/simulation logic

## Known Issues
- `translate()` returns a copy but is unused — dead code
- Public fields bypass encapsulation — acceptable for now given LibGDX patterns
