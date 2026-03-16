# UIManager

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/UIManager.java`
**Extends/Type:** Plain class (no base)
**Role:** Central coordinator for all HUD panels — owns textures, inventory/equipment models, constructs panels, routes input, drives update/render, and handles spell/item/equipment drag-drop.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `font` | `BitmapFont` | Shared font passed to all panels |
| `input` | `InputBindings` | Keybind state |
| `game` | `Game` | Stored for scene access |
| `spellBar` | `SpellBarPanel` | Bottom-center spell hotbar |
| `mouseSpellBar` | `MouseSpellBarPanel` | Mouse-button spell bar |
| `panelMenu` | `PanelMenuBar` | Row of panel-toggle buttons |
| `spellBook` | `SpellBookPanel` | Spell book panel |
| `bag` | `BagPanel` | Inventory panel |
| `character` | `CharacterPanel` | Character/equipment panel |
| `xpBar` | `XPBarPanel` | XP bar |
| `talents` | `TalentPanel` | Talent tree panel |
| `playerXP` | `PlayerXP` | Stored for level-up callback setup |
| `playerStats` | `PlayerStats` | Recalculated on equip/unequip/level-up |
| `equipment` | `Equipment` | Current equipped items model |
| `inventory` | `Inventory` | 4×4 backing model for BagPanel |
| `dragging` | `SpellDefinition` | Spell being dragged |
| `draggingItem` | `ItemDefinition` | Bag item being dragged |
| `draggingItemSlot` | `int` | Source bag slot index (-1 = none) |
| `draggingEquipItem` | `ItemDefinition` | Equipment item being dragged |
| `draggingFromEquipSlot` | `EquipmentSlot` | Source equip slot for dragged equip item |
| `dragX/Y` | `float` | Current drag icon screen position |

## Key Methods
- **`init(Game, PlayerXP)`** — constructs all panels, wires listeners, captures player transform for level-up callback, sets `playerXP.setOnLevelUp()`
- **`findPlayerTransform()`** — scans scene once at init time; result stored in lambda closure (no runtime scan)
- **`update(float dt)`** — polls keybind toggles, tracks drag position, fires `handleMouseDown/Up`
- **`render(SpriteBatch)`** — renders all panels then drag-icon overlays
- **`handleMouseUp()`** — routes spell/item/equip drag completion
- **`autoEquip(item, bagSlotIndex)`** — RMB from bag → first compatible empty equip slot, or swap with first compatible occupied slot
- **`autoUnequip(EquipmentSlot)`** — RMB from equip slot → first empty bag slot
- **`spawnWorldItemAtPlayer(item)`** — spawns WorldItemPrefab near player position

## Item Drag Flow (Bag → Anywhere)
```
mouseDown on filled ItemSlot → startItemDrag() → clear slot + store draggingItem
  mouseUp inside BagPanel → slot-to-slot swap
  mouseUp on compatible EquipSlot → equip; displaced item returns to source bag slot
  mouseUp on incompatible EquipSlot → item returns to original bag slot
  mouseUp in open space → spawnWorldItemAtPlayer()
```

## Equipment Drag Flow (EquipSlot → Anywhere)
```
mouseDown on EquipSlot with item → startEquipDrag() → unequip + store draggingEquipItem
  mouseUp on compatible EquipSlot → equip; displaced goes to bag
  mouseUp on incompatible EquipSlot → item returns to source EquipSlot (re-equipped)
  mouseUp on BagSlot → placed in bag
  mouseUp in open space → spawnWorldItemAtPlayer()
```

## Level-Up Callback
Set in `init()`:
```java
playerXP.setOnLevelUp(() -> {
    playerStats.levelUp();
    scene.addObject(FloatingTextPrefab.create(px, py + 40f, "Level Up!", Color.GOLD, 2.0f));
});
```
Player transform captured once via `findPlayerTransform()` — NOT inside the callback — to avoid nested iterator crash.

## Key Accessors
- `getCharacterPanel()` — returns `character`; used by `DeathValley.wireUI()` to init `EquipmentLayerManager`

## Dependencies
- All panel classes in `game.ui.panels`, `game.ui.widgets`
- `InputBindings`, `PlayerXP`, `PlayerStats`, `Equipment`, `Game`
- `Inventory`, `ItemDefinition`, `WorldItemPrefab`, `PlayerController`, `FloatingTextPrefab`

## Rules
- `UIPanel.panelPixel` set to `whitePixel` immediately after creation — all panels share this reference
- `loadUI()` must never throw — missing assets get `whitePixel` silently
- `findPlayerTransform()` called once at init, not in callbacks — avoids nested iterator crash during level-up
- `playerStats.recalculate(equipment)` called after every equip/unequip operation
