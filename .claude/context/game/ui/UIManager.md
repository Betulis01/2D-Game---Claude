# UIManager

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/UIManager.java`
**Extends/Type:** Plain class (no base)
**Role:** Central coordinator for all HUD panels — owns textures, inventory model, constructs panels, routes input, drives update/render, and handles both spell and item drag-drop.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `font` | `BitmapFont` | Shared font passed to all panels |
| `input` | `InputBindings` | Keybind state |
| `game` | `Game` | Stored for scene access (world item spawning) |
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
| `draggingItem` | `ItemDefinition` | Item being dragged from bag (null = not dragging) |
| `draggingItemSlot` | `int` | Source slot index of dragged item (-1 = none) |
| `dragX/Y` | `float` | Current drag icon position |
| `inventory` | `Inventory` | 4×4 backing model for BagPanel |
| `whitePixel` | `Texture` | 1×1 white fallback texture |
| `slotBg` | `Texture` | Slot background PNG |
| `xpFull/xpEmpty` | `Texture` | XP bar fill/empty PNGs |
| `fireballIcon` / `lightningIcon` | `Texture` | Spell icon PNGs |
| `btnBag/btnChar/btnBook/btnTal` | `Texture` | Panel button face PNGs (fallback to whitePixel) |
| `equipTextures` | `Map<String,Texture>` | Keyed by slot name (e.g. `equip_head`) |
| `screenW/H` | `int` | Screen dimensions |

## Key Methods / Logic
- **`init(Game, PlayerXP)`** — creates `whitePixel`, loads textures, constructs all panels (including `Inventory`), wires spell and item drag listeners.
- **`loadTextures()`** — loads all PNGs via `loadUI()`.
- **`loadUI(String path)`** — returns texture from `Gdx.files.local(path)` or `whitePixel` if missing.
- **`update(float dt)`** — polls keybind toggles, tracks drag position, fires `handleMouseDown/Up` on button events.
- **`render(SpriteBatch)`** — renders all panels then drag-icon overlays (spell and item separately).
- **`handleMouseDown/Up`** — routes spell drag to spell bar, item drag to bag slots or world spawn.
- **`startItemDrag(item, slotIndex)`** — clears inventory slot, stores dragging state, refreshes bag visual.
- **`spawnWorldItemAtPlayer(item)`** — scans scene for `PlayerController`, spawns `WorldItemPrefab` near player.
- **`getInventory()`** — returns the Inventory model (used by `WorldItem.tryPickup()`).
- **`refreshBag()`** — calls `bag.refresh(inventory)` (called after pickup or drag completion).

## Item Drag Flow
```
mouseDown on filled ItemSlot → startItemDrag() → clear slot + store draggingItem
  mouseUp inside BagPanel → slot-to-slot swap (with existing item going to source slot)
  mouseUp outside BagPanel → spawnWorldItemAtPlayer() → bag.refresh()
```

## Dependencies
- All panel classes in `game.ui.panels`, `game.ui.widgets`
- `InputBindings`, `PlayerXP`, `Game`
- `SpellBar`, `SpellDefinition`, `SpellSlot`
- `Inventory`, `ItemDefinition`, `WorldItemPrefab`, `PlayerController`

## Rules
- `UIPanel.panelPixel` is set to `whitePixel` immediately after creation — all panels share this reference.
- Panel construction order in `init()` determines Z-order in `render()`.
- `loadUI()` must never throw — missing assets get `whitePixel` silently.
- Button texture filenames for PanelMenuBar: `bag.png`, `char.png`, `spellbook.png`, `talent.png` (under `/assets/ui/`).
- Item drag and spell drag are independent systems tracked with separate fields.
