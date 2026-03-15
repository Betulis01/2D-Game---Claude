# Interactable

**File:** `core/.../game/components/Interactable.java`
**Extends:** `Component`
**Role:** Reusable proximity + input component. Detects when the player enters range, fires a callback on the bound action key. Spawns a companion `InteractPrompt` overlay object on `start()` and destroys it on `onDestroy()`.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `range` | `float` | Trigger radius in world units |
| `action` | `InputBindings.Action` | Input action to listen for |
| `onInteract` | `Runnable` | Callback fired when in range + action pressed |
| `playerTransform` | `Transform` | Found via scene scan in `start()` |
| `inRange` | `boolean` | True when player is within range this frame |
| `promptObject` | `GameObject` | Companion overlay object holding `InteractPrompt` |

## Key Methods / Logic
- **`start()`** — scans `getScene().getObjects()` for `PlayerController`; creates `InteractPrompt` companion and calls `scene.addOverlayObject()`.
- **`update(dt)`** — squared-distance check (no sqrt); sets `inRange`; fires `onInteract.run()` when in range + action just-pressed.
- **`onDestroy()`** — calls `scene.removeOverlayObject(promptObject)` then `promptObject.onDestroy()` to prevent orphan prompts.
- **`isInRange()`** — getter used by `InteractPrompt` to decide whether to render.

## Dependencies
- `PlayerController` (scene scan), `InputBindings`, `Scene.addOverlayObject/removeOverlayObject`
- `InteractPrompt` (companion component)

## Rules
- Uses squared distance — no `Math.sqrt()`
- Always cleans up companion via `removeOverlayObject` + `onDestroy()`, NOT `destroy()` (which only removes from `objects`)
- Can be attached to any GameObject that needs a proximity interaction — not WorldItem-specific
