# UIAssetGenerator

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/UIAssetGenerator.java`
**Extends/Type:** Static utility class
**Role:** Generates placeholder UI PNGs on first run if they don't exist on disk (under `ui/`).

## Generated Assets
| File | Size | Description |
|------|------|-------------|
| `ui/slot.png` | 40×40 | Item/spell slot background |
| `ui/fireball_icon.png` | 36×36 | Fireball spell icon |
| `ui/lightning_icon.png` | 36×36 | Lightning spell icon |
| `ui/xp_full.png` | 78×16 | XP bar full segment |
| `ui/xp_empty.png` | 78×16 | XP bar empty segment |
| `ui/equip_*.png` | 40×40 | Equipment slot icons (13 types) |
| `ui/pickup_prompt.png` | 24×24 | "F" keycap interact prompt |
| `ui/panels/spellbook_panel.png` | 300×200 | SpellBook panel background |
| `ui/panels/character_panel.png` | 200×480 | Character panel background |
| `ui/panels/talent_panel.png` | 164×184 | Talent panel background |

## Key Methods
- **`generateIfAbsent()`** — entry point; checks sentinel files and writes missing assets.
- **`writePanelBg(String path, int w, int h)`** — draws dark blue body (`0x141426EB`), title strip top 18px (`0x26264DFF`), 1px border (`0x8888aaff`).
- **`filledWithBorder()`**, **`filledWithLabel()`**, **`equipSlot()`** — helpers for other asset types.
- **`drawBorder()`** — draws N-pixel-thick rectangle outline.
- **`drawCenteredLabel()`** / **`drawPixelChar()`** — pixel-art 3×5 font renderer.
- **`toColor(int rgba)`** — converts packed RGBA int to LibGDX Color.

## Rules
- All writes are guarded by `.exists()` checks — safe to call every startup.
- Panel PNGs go under `ui/panels/` (local filesystem, not assets/); `BagPanel` uses `assets/ui/panels/inventory_panel.png` (real PNG, not generated).
- Call from `Game.create()` before any panel constructors run.
