# Hurtbox

**File:** `core/.../game/components/collision/Hurtbox.java`
**Extends:** `Component`
**Role:** Defensive collision volume. Receives hits from Hitboxes. Identical math to Hitbox — only semantic difference.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `offsetX`, `offsetY` | `float` | Offset from entity center |
| `width`, `height` | `float` | Box dimensions |
| `worldBounds` | `AABB` | World-space AABB, updated every frame |

## Update Logic
```java
worldBounds.x = transform.worldX + offsetX - width / 2
worldBounds.y = transform.worldY + offsetY - height / 2
worldBounds.width = width
worldBounds.height = height
```

## Rules
- Identical formula to Hitbox — center-anchored, offset relative to entity center
- AABB updated in-place each frame
- Dimensions and offset from EntityConfig
- Queried by DamageOnHit during collision check
