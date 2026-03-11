# UIManager

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/UIManager.java`
**Extends/Type:** Plain class (no base)
**Role:** Central coordinator for all HUD panels — owns textures, constructs panels, routes input, and drives update/render.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `font` | `BitmapFont` | Shared font passed to all panels |
| `input` | `InputBindings` | Keybind state |
| `spellBar` | `SpellBarPanel` | Bottom-center spell hotbar |
| `mouseSpellBar` | `MouseSpellBarPanel` | Mouse-button spell bar |
| `panelMenu` | `PanelMenuBar` | Row of panel-toggle buttons |
| `spellBook` | `SpellBookPanel` | Spell book panel |
| `bag` | `BagPanel` | Inventory panel |
| `character` | `CharacterPanel` | Character/equipment panel |
| `xpBar` | `XPBarPanel` | XP bar |
| `talents` | `TalentPanel` | Talent tree panel |
| `spellBarData` / `mouseSpellBarData` | `SpellBar` | Spell slot data models |
| `dragging` | `SpellDefinition` | Spell being dragged (null = not dragging) |
| `dragX/Y` | `float` | Current drag icon position |
| `whitePixel` | `Texture` | 1×1 white fallback texture |
| `slotBg` | `Texture` | Slot background PNG |
| `xpFull/xpEmpty` | `Texture` | XP bar fill/empty PNGs |
| `fireballIcon` / `lightningIcon` | `Texture` | Spell icon PNGs |
| `btnBag/btnChar/btnBook/btnTal` | `Texture` | Panel button face PNGs (fallback to whitePixel) |
| `equipTextures` | `Map<String,Texture>` | Keyed by slot name (e.g. `equip_head`) |
| `screenW/H` | `int` | Screen dimensions |

## Key Methods / Logic
- **`init(Game, PlayerXP)`** — creates `whitePixel`, loads textures, constructs all panels, wires drag listeners.
- **`loadTextures()`** — loads all PNGs via `loadUI()`; btn textures: `bag.png`, `char.png`, `spellbook.png`, `talent.png`.
- **`loadUI(String path)`** — returns texture from `Gdx.files.local(path)` or `whitePixel` if missing (safe fallback).
- **`update(float dt)`** — polls keybind toggles, tracks drag position, routes mouse events, calls update on all panels.
- **`render(SpriteBatch)`** — renders drag icon then all panels in fixed Z-order.
- **`handleMouseDown/Up`** — routes click to panels in priority order; drag is completed on mouse-up.

## Dependencies
- All panel classes in `game.ui.panels`
- `InputBindings`, `PlayerXP`, `Game`
- `SpellBar`, `SpellDefinition`, `SpellSlot`

## Rules
- `UIPanel.panelPixel` is set to `whitePixel` immediately after creation — all panels share this reference.
- Panel construction order in `init()` determines Z-order in `render()`.
- `loadUI()` must never throw — missing assets get `whitePixel` silently.
- Button texture filenames for PanelMenuBar: `bag.png`, `char.png`, `spellbook.png`, `talent.png` (under `/assets/ui/`).
