# Hitbox

**File:** `core/.../game/components/collision/Hitbox.java`
**Extends:** `Component`
**Role:** Offensive collision volume. Maintained by attack entities. Checked against Hurtboxes by DamageOnHit.

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
- World bounds use **center-anchored formula** — offset relative to entity center
- AABB is updated in-place each frame (no allocation)
- Dimensions and offset from EntityConfig — not hardcoded
