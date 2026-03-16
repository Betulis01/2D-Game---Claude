# EquipmentLayerManager

**File:** `core/.../game/components/render/EquipmentLayerManager.java`
**Extends/Type:** `Component`
**Role:** Manages visual equipment layers on the player's `LayeredSpriteRenderer`. Listens for equip/unequip events on visual slots (head, chest, legs) and adds/removes atlas-based animation layers.

## Fields
| Field | Type | Purpose |
|---|---|---|
| `VISUAL_SLOTS` | `String[]` | Slot keys with visual representation: "head", "chest", "legs" |
| `renderer` | `LayeredSpriteRenderer` | Target renderer |
| `assets` | `Assets` | For loading equipment atlases |
| `slotLayerIndex` | `Map<String, Integer>` | Maps slotKey → layer index in renderer |
| `nextLayerIndex` | `int` | Next available layer index (starts at 1) |

## Key Methods / Logic
- `start()`: resolves `renderer` and `assets` from scene/game.
- `init(CharacterPanel)`: iterates panel's equipment slots; registers `EquipListener` on visual slots only.
- `onItemEquipped(slotKey, item)`: loads atlas from `"equipment/<spriteLayer>.atlas"`, builds `DirectionalAnimSet` for IDLE+WALK, calls `renderer.addLayer()`. Skips if atlas not loaded.
- `onItemUnequipped(slotKey)`: calls `renderer.removeLayersFromIndex(idx)`, decrements `nextLayerIndex`, shifts remaining indices down.

## Dependencies
- `LayeredSpriteRenderer`, `CharacterPanel`, `EquipmentSlot.EquipListener`
- `DirectionalAnimSet`, `CharacterState`, `Assets`

## Rules
- `init()` must be called **after** UI is ready — called from `DeathValley.wireUI()`.
- Equipment atlas path convention: `"equipment/<ItemConfig.spriteLayer>.atlas"`.
- Gracefully skips if atlas not found (null check on `getAtlas()`).
