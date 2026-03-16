# ItemConfig

**File:** `core/.../game/items/ItemConfig.java`
**Extends/Type:** Plain POJO
**Role:** Deserialization target for item JSON files (e.g. `data/config/items/small-sword.json`). Mirrors JSON structure with public fields so LibGDX `Json` can deserialize automatically.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `name` | `String` | Display name |
| `itemLevel` | `int` | Item level |
| `requiredLevel` | `int` | Level requirement |
| `rarity` | `String` | "Common", "Uncommon", etc. |
| `slot` | `String` | Equipment slot ("WEAPON", etc.) |
| `type` | `String` | Sub-type ("One-Hand Sword", etc.) |
| `damage` | `Damage` | Nested: min/max damage |
| `speed` | `float` | Attack speed |
| `stats` | `Stats` | Nested: strength, agility, etc. |

## Nested Classes
- `Damage` — `int min`, `int max`
- `Stats` — `int strength`, `int agility`

| `spriteLayer` | `String` | Nullable. Atlas name for equipment visual layer (e.g. "Leather_Chest"). Used by `EquipmentLayerManager`. |

## Rules
- All fields public, no-arg constructor (LibGDX Json requirement)
- No game logic — pure data
- `spriteLayer` is nullable — items without visual layers simply omit the field
