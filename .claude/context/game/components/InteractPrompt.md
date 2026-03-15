# InteractPrompt

**File:** `core/.../game/components/InteractPrompt.java`
**Extends:** `Component`
**Role:** Overlay renderer for the interact button. Attached to a companion GameObject in `overlayObjects`. Reads the source `Interactable`'s world position and visibility state each frame — always renders on top of world entities.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `source` | `Interactable` | The Interactable this prompt belongs to |
| `texture` | `Texture` | `assets/ui/interact_button.png` loaded in `start()` |

## Key Methods / Logic
- **`start()`** — loads `assets/ui/interact_button.png` if it exists.
- **`render(batch)`** — skips if `!source.isInRange()` or texture null; reads `source.getGameObject().getTransform()` for world position; converts to screen coords via Camera; draws button 16×zoom above the source item (`sy + size * 0.5f`).
- **`onDestroy()`** — disposes texture.

## Dependencies
- `Interactable` (source reference), `Camera`

## Rules
- Lives in `overlayObjects`, not `objects` — always renders after the world pass
- Does NOT own proximity logic — delegates entirely to `source.isInRange()`
- Texture is loaded and owned locally — disposed in `onDestroy()`
- Button offset matches the original WorldItem prompt offset: `sy + size * 0.5f`
