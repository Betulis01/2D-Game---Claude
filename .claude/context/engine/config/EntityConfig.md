# EntityConfig

**File:** `core/.../engine/config/EntityConfig.java`
**Role:** Serializable data class representing a complete entity definition loaded from JSON. All balance stats, dimensions, and animation timing live here, not in Java code.

## Top-Level Fields
| Field | Type | Purpose |
|-------|------|---------|
| `id`, `name` | `String` | Identity |
| `onHitEffects` | `String[]` | Effect ids to spawn on hit |
| `onDeathEffects` | `String[]` | Effect ids to spawn on death |
| `spawnSounds` | `String[]` | Sound ids played when the entity is spawned (via `AudioPlayer`) |
| `sprite`, `collision`, `hurtbox`, `hitbox`, `stats` | nested | See nested classes below |

## Structure
```json
{
  "id": "player",
  "name": "Player",
  "sprite":    { "sheet": "path.png", "width": 32, "height": 32, "frames": 3, "directions": 8, "frameDuration": 0.2, "idleFrameDuration": 0.3 },
  "collision": { "width": 14, "height": 14, "offsetX": 0, "offsetY": 0 },
  "hurtbox":   { "width": 10, "height": 20, "offsetX": 0, "offsetY": -2 },
  "hitbox":    { "width": 10, "height": 20, "offsetX": 0, "offsetY": -2 },
  "stats": {
    "moveSpeed": 120.0, "maxHealth": 100,
    "attack": 10, "defense": 5,
    "attackSpeed": 1.2, "critChance": 0.05,
    "damage": 25, "cooldown": 0.5, "duration": 1.5
  }
}
```

## Nested Classes
| Class | Fields |
|-------|--------|
| `Sprite` | `sheet`, `width`, `height`, `frames`, `directions`, `frameDuration`, `idleFrameDuration` |
| `Collision` | `width`, `height`, `offsetX`, `offsetY` |
| `Hurtbox` | `width`, `height`, `offsetX`, `offsetY` |
| `Hitbox` | `width`, `height`, `offsetX`, `offsetY` |
| `Stats` | `moveSpeed`, `maxHealth`, `attack`, `defense`, `attackSpeed`, `critChance`, `damage`, `cooldown`, `duration` |

## Rules
- All public fields — required by LibGDX Json deserializer
- No game logic — pure data
- Prefab factories read all values from this — no hardcoded numbers in Java
- `frameDuration` — per-frame animation duration for action clips (walk, fly, jump)
- `idleFrameDuration` — separate duration for idle clips (player only currently)
- `directions` — number of sprite sheet rows (replaces old `rows` field name)

## Known Issues
- No validation on load — missing fields silently default to 0/null
