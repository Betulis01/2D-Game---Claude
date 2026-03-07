# AttackSpawner

**File:** `core/.../game/components/combat/AttackSpawner.java`
**Extends:** `Component`
**Role:** Cooldown-gated attack dispatcher. Spawns attack GameObjects into the scene when called, if off cooldown.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `cooldown` | `float` | Seconds between attacks (from EntityConfig) |
| `cooldownTimer` | `float` | Time since last attack |

## Key Method
```java
tryAttack(String attackId, float dirX, float dirY)
  if cooldownTimer < cooldown: return   // on cooldown
  cooldownTimer = 0
  switch(attackId):
    "fireball"      → AttackPrefabs.createFireball(scene, transform, dirX, dirY, cfg)
    "lightning_bolt"→ AttackPrefabs.createLightningBolt(scene, transform, dirX, dirY, cfg)
  scene.addObject(attackObj)
```

## Rules
- Cooldown value comes from EntityConfig — not hardcoded
- Attack type dispatch (switch) belongs here only as a router — actual construction in AttackPrefabs
- Direction must be pre-normalized by caller (PlayerInput)

## Known Issues
- Switch on string attack ID is fragile — any new attack type requires editing this class
- Better design: AttackSpawner receives a factory reference, not a string ID
