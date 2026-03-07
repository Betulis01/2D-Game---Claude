# Transform

**File:** `core/.../engine/system/Transform.java`
**Extends:** `Component`
**Role:** Manages position, rotation, and scale. Supports parent/child hierarchy. **Always center-anchored.**

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `worldX`, `worldY` | `float` | Center position in world space |
| `localX`, `localY` | `float` | Position relative to parent |
| `rotation` | `float` | Degrees, world space |
| `scaleX`, `scaleY` | `float` | Scale multipliers |
| `parent` | `Transform` | Parent transform (null = world root) |
| `children` | `List<Transform>` | Child transforms |
| `z` | `float` | Depth layer (for future depth sorting) |
| `zVelocity` | `float` | Z-axis movement (currently unused) |

## Key Methods
- `setPosition(x, y)` — sets world center position
- `setWorldPosition(x, y)` — same, also propagates to children
- `translate(dx, dy)` — moves by delta
- `setLocalPosition(x, y)` — sets local offset from parent
- `getWorldX/Y()` — returns world-space center
- `setParent(Transform)` — attaches to hierarchy, recalculates local coords
- `rotate(degrees)` — adds to current rotation

## Critical Rule
**Transform is CENTER-ANCHORED. This is non-negotiable.**
- `worldX/Y` = **center** of the object
- Renderers draw at `(worldX - width/2, worldY - height/2)`
- AABB bounds: `minX = worldX + offsetX - width/2`
- Movement clamp: `min = halfSize`, `max = mapSize - halfSize`

## Known Issues
- `zVelocity` field exists but is never updated — dead code until depth sorting is implemented
