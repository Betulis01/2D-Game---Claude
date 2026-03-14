# FloatingTextPrefab

**File:** `core/.../game/prefabs/FloatingTextPrefab.java`
**Role:** Static factory for FloatingText GameObjects. Creates a named object at a world position with a FloatingText component.

## Factory Method
```java
FloatingTextPrefab.create(float x, float y, String text, Color color, float duration)
```
- Sets transform position to (x, y)
- Adds `FloatingText` component

## Usage
```java
scene.addObject(FloatingTextPrefab.create(tx, ty, "-10", Color.RED, 1.0f));
scene.addObject(FloatingTextPrefab.create(dx, dy + 10f, "+2 XP", xpColor, 1.2f));
scene.addObject(FloatingTextPrefab.create(px, py + 40f, "Level Up!", Color.GOLD, 2.0f));
```

## Rules
- Always call `scene.addObject()` with the result — not just `create()`
- Safe to call from inside update loops — SnapshotArray defers the add until end()
