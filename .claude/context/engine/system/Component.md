# Component

**File:** `core/.../engine/system/Component.java`
**Type:** Abstract base class
**Role:** Base for all behavior. Subclasses implement game logic by overriding lifecycle methods.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `gameObject` | `GameObject` | Parent container |
| `transform` | `Transform` | Shortcut to parent's Transform |
| `scene` | `Scene` | Current scene |
| `enabled` | `boolean` | If false, update/render are skipped |

## Lifecycle Methods (override in subclasses)
| Method | When called |
|--------|-------------|
| `awake()` | On component attachment, before scene starts |
| `start()` | First frame the scene runs |
| `update(float dt)` | Every frame (skipped if disabled) |
| `render(SpriteBatch batch)` | Every frame after all updates (skipped if disabled) |
| `onDestroy()` | When parent GameObject is removed from scene |

## Key Methods
- `setEnabled(boolean)` — toggles update/render participation
- `isEnabled()` — current state

## Rules
- One component = one responsibility
- Sibling components (on same GameObject) accessed via `gameObject.getComponent()`
- Cross-object references go through scene queries — never hard-wire scene traversal
- Never call `scene.addObject()` or `scene.removeObject()` directly — request through a dedicated mechanism
