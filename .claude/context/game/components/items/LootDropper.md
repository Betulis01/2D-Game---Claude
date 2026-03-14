# LootDropper

**File:** `core/.../game/components/items/LootDropper.java`
**Extends:** `Component`
**Role:** Probabilistic loot spawner. On entity death, rolls against `dropChance` and spawns a WorldItem at a random offset from the death position.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `texturePath` | `String` | Asset path for item icon (e.g. `Assets.small_sword`) |
| `configPath` | `String` | Path to item JSON config |
| `dropChance` | `float` | 0.0–1.0 probability |
| `itemDef` | `ItemDefinition` | Built in `start()`, null until then |

## Key Methods
- **`start()`** — loads texture via Assets, deserializes ItemConfig via LibGDX Json, derives `ItemType` from `cfg.slot` via `itemTypeFromSlot()`, constructs `ItemDefinition`
- **`onDestroy()`** — if `itemDef != null` and `Math.random() < dropChance`, creates `WorldItemPrefab` at ±20px random offset
- **`itemTypeFromSlot(String slot)`** — static helper: `"WEAPON"→WEAPON`, armor slots→ARMOR, everything else→ACCESSORY

## ItemType Derivation
```
"WEAPON"                            → ItemType.WEAPON
"HEAD","SHOULDER","BACK","CHEST",
"WRIST","HANDS","WAIST","LEGS","FEET" → ItemType.ARMOR
"RING","TRINKET","RELIC", null, etc. → ItemType.ACCESSORY
```

## Dependencies
- `Assets` (via `getScene().getGame().getAssets()`)
- `ItemDefinition`, `ItemConfig`, `WorldItemPrefab`

## Rules
- Multiple `LootDropper` components on one GameObject = multiple independent drop rolls (each fires in `onDestroy`)
- `itemDef == null` guard handles edge case where entity destroyed before first frame
- Drop offset: random in range [-20, +20] on both axes
- Config loaded from `Gdx.files.internal()` (assets folder)
