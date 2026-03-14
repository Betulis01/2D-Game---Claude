# LootDropper

**File:** `core/.../game/components/items/LootDropper.java`
**Extends:** `Component`
**Role:** Probabilistic loot spawner. On entity death, rolls against `dropChance` and spawns a WorldItem at a random offset from the death position.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `texturePath` | `String` | Asset path for item icon (e.g. `Assets.small_sword`) |
| `configPath` | `String` | Path to item JSON config (e.g. `data/config/items/small-sword.json`) |
| `dropChance` | `float` | 0.0–1.0 probability |
| `itemDef` | `ItemDefinition` | Built in `start()`, null until then |

## Key Methods / Logic
- **`start()`** — loads texture from `Assets`, deserializes `ItemConfig` via LibGDX `Json.fromJson()`, constructs `ItemDefinition`.
- **`onDestroy()`** — if `itemDef != null` and `Math.random() < dropChance`, creates `WorldItemPrefab` at ±20px random offset and calls `scene.addObject()`.

## Dependencies
- `Assets` (via `getScene().getGame().getAssets()`)
- `ItemDefinition`, `ItemConfig`
- `WorldItemPrefab`

## Rules
- `itemDef == null` guard in `onDestroy()` handles edge case where entity destroyed before first frame
- Drop offset: random in range [-20, +20] on both axes
- Config loaded from `Gdx.files.internal()` (assets folder)
