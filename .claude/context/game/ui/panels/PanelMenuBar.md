# PanelMenuBar

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/panels/PanelMenuBar.java`
**Extends/Type:** `UIWidget`
**Role:** A row of small texture-based buttons that each toggle a UI panel; sits to the right of the mouse spell bar.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `BTN_W` | `float` (44) | Button width |
| `BTN_H` | `float` (40) | Button height |
| `BTN_GAP` | `float` (2) | Gap between buttons |
| `HIGHLIGHT` | `Color` (0.6, 0.8, 1, 1) | Tint applied when the button's panel is open |
| `entries` | `List<Entry>` | Ordered list of button entries |
| `input` | `InputBindings` | Used to resolve keybind display names |

## Entry (inner static class)
| Field | Type | Purpose |
|-------|------|---------|
| `label` | `String` | Button label text (currently unused — keybind hint only shown) |
| `boundAction` | `Action` | Keybind action for hint display |
| `target` | `UIPanel` | Panel this button toggles |
| `tex` | `Texture` | PNG texture drawn as button face |
| `x, y, w, h` | `float` | Layout bounds |

## Key Methods / Logic
- **Constructor** `(float rightEdgeX, float y, InputBindings input)` — no pixel texture; x/w finalized after buttons are added.
- **`addButton(label, action, target, tex)`** — appends an Entry and calls `rebuildLayout()`.
- **`setRightEdge(float rightEdgeX)`** — sets `x = rightEdgeX - w` then re-runs layout; call after all buttons added.
- **`render()`** — draws `e.tex` tinted `HIGHLIGHT` when panel is open, `WHITE` otherwise. Shows keybind hint in `DARK_GRAY` at bottom-left.
- **`onClick / onMouseDown`** — hit-tests each entry; calls `target.toggle()` on match.

## Dependencies
- `UIWidget`, `UIPanel` (core UI abstractions)
- `InputBindings` (keybind display names)
- `Texture` (one per button, provided by UIManager)

## Rules
- Buttons are added left-to-right; `setRightEdge()` must be called after all `addButton()` calls.
- No pixel texture field — all rect drawing is gone; button visuals come entirely from the `tex` PNG.
- `loadUI()` in UIManager falls back to `whitePixel` for missing textures, so missing PNGs are safe.
