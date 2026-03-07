# AttackOutsideMapDespawner

**File:** `core/.../game/components/collision/AttackOutsideMapDespawner.java`
**Extends:** `Component`
**Role:** Destroys an attack entity when it leaves the map bounds. Prevents projectiles from flying infinitely off-screen.

## Update Logic
```
if NOT hitbox.worldBounds.intersects(scene.getMapBounds()):
  gameObject.destroy()
```

## Dependencies
- `Hitbox` (sibling) — bounds to test
- `Scene.getMapBounds()` — world AABB of the entire map

## Rules
- Check is every frame — fires as soon as the hitbox fully exits map bounds
- Works with any attack entity that has a Hitbox component
