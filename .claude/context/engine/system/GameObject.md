# GameObject

**File:** `core/.../engine/system/GameObject.java`
**Role:** Pure container. Holds components and delegates lifecycle to them. Has no logic of its own.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `name` | `String` | Debug identifier |
| `components` | `List<Component>` | All attached components |
| `transform` | `Transform` | Always-present position/rotation/scale component |
| `scene` | `Scene` | Owning scene reference |

## Lifecycle (delegated to components)
```
start()     → calls component.start() on all components
update(dt)  → calls component.update(dt) on all enabled components
render(batch) → calls component.render(batch) on all enabled components
destroy()   → calls component.onDestroy(), removes self from scene
```

## Key Methods
- `addComponent(Component)` — attaches component, sets back-references
- `getComponent(Class<T>)` — returns first matching component or **null** (callers must null-check)
- `getComponents(Class<T>)` — returns all matching components
- `removeComponent(Component)` — detaches and calls onDestroy on it
- `destroy()` — removes this object from the scene

## Rules
- Every GameObject always has exactly one Transform (created in constructor)
- No game logic in GameObject — all behavior is in components
- `getComponent()` returns null if absent — NullPointerException is not an acceptable failure mode
