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
| `WorldItem` | Handles rendering, proximity, F-key pickup |

## Rules
- No scene reference needed at creation — `WorldItem.start()` resolves scene dependencies
- Called from `LootDropper.onDestroy()` and `UIManager.spawnWorldItemAtPlayer()`
