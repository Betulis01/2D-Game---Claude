# EntityConfig

**File:** `core/.../engine/config/EntityConfig.java`
**Role:** Serializable data class representing a complete entity definition loaded from JSON. All balance stats and dimensions live here, not in Java code.

## Structure
```json
{
  "id": "player",
  "name": "Player",
  "sprite":     { "sheet": "path.png", "width": 32, "height": 32, "frames": 3, "rows": 8 },
  "collision":  { "width": 14, "height": 14, "offsetX": 0, "offsetY": 0 },
  "hurtbox":    { "width": 10, "height": 20, "offsetX": 0, "offsetY": -2 },
  "hitbox":     { "width": 10, "height": 20, "offsetX": 0, "offsetY": -2 },
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
| `Sprite` | `sheet`, `width`, `height`, `frames`, `rows` |
| `Collision` | `width`, `height`, `offsetX`, `offsetY` |
| `Hurtbox` | `width`, `height`, `offsetX`, `offsetY` |
| `Hitbox` | `width`, `height`, `offsetX`, `offsetY` |
| `Stats` | `moveSpeed`, `maxHealth`, `attack`, `defense`, `attackSpeed`, `critChance`, `damage`, `cooldown`, `duration` |

## Rules
- All public fields — required by LibGDX Json deserializer
- No game logic — pure data
- Prefab factories receive EntityConfig as a parameter and read values from it
- No hardcoded stats anywhere in Java — they belong here

## Known Issues
- No validation on load — missing required fields silently default to 0/null
- `directions` field defined in some configs but unused
