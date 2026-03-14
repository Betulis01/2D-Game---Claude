# UIPanel

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/core/UIPanel.java`
**Extends:** `UIWidget`
**Role:** Base class for all togglable HUD panels — draws a background (PNG or manual pixel fallback), title text, and routes input to children.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `title` | `String` | Title text drawn in the title bar |
| `children` | `List<UIWidget>` | Child widgets rendered/updated by this panel |
| `background` | `Texture` | (legacy, unused) |
| `panelBg` | `Texture` | PNG background; null = fall back to manual pixel draw |
| `panelPixel` | `static Texture` | Injected by UIManager — 1×1 white texture for manual draw fallback |

## Key Methods / Logic
- **`loadPanelBg(String path)`** — loads a PNG via `Gdx.files.local(path)`, sets `panelBg`, and updates `w`/`h` to match PNG pixel dimensions so `contains()` stays accurate.
- **`render()`** — if `panelBg != null`, draws it at 1:1 scale (WHITE tint). Otherwise draws manual pixel rect (BG_COLOR) + title bar strip (TITLE_BG). Title text always drawn on top.
- **`onClick()` / `onMouseDown()`** — consume if panel is visible and cursor is inside.
- **`onMouseUp()`** — returns `contains(mx, my)` when visible (was `false`), preventing clicks from leaking through open panels to the game world.
- **`toggle()`** — flips `visible`.
- **`add(UIWidget)`** — registers a child.

## Constants
- `TITLE_BAR_H = 18f`
- `BG_COLOR = 0x141426EB` (approx)
- `TITLE_BG = 0x26264DFF` (approx)

## Dependencies
- `UIWidget`, `Gdx`

## Rules
- `panelPixel` must be injected by `UIManager` before any panel renders.
- `loadPanelBg()` overwrites `w`/`h` — call it before child slot positions that depend on `w`/`h`, or after if slots use hardcoded constants.
- Panel starts hidden (`visible = false`).
