# WorldItemPrefab

**File:** `core/.../game/prefabs/WorldItemPrefab.java`
**Extends/Type:** Static factory
**Role:** Creates a world-pickup GameObject for a given ItemDefinition at a world-space position.

## Factory Method
```java
public static GameObject create(float x, float y, ItemDefinition itemDef)
```

## Produced GameObject: "WorldItem"
| Component | Purpose |
|-----------|---------|
| `Transform` | World position (x, y) |
| `WorldItem` | Icon rendering + inventory insertion (`tryPickup()`) |
| `Interactable` | Range detection (20px), F-key input, fires `worldItem::tryPickup` |

## Rules
- No scene reference needed at creation — components resolve dependencies in `start()`
- `Interactable` spawns its own `InteractPrompt` companion into `overlayObjects` automatically
- Called from `LootDropper.onDestroy()` and `UIManager.spawnWorldItemAtPlayer()`
