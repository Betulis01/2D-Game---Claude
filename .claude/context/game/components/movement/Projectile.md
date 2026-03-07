# Projectile

**File:** `core/.../game/components/movement/Projectile.java`
**Extends:** `Movement`
**Role:** Fixed-direction movement for attack entities. Sets direction once at creation and never changes it. Always moving.

## Construction
```java
new Projectile(float dirX, float dirY)
// direction normalized on construction, moving = true always
```

## Rules
- Direction is immutable after construction
- `moving` is always true — projectiles never stop
- No bounds clamping — removed by AttackOutsideMapDespawner when out of bounds
