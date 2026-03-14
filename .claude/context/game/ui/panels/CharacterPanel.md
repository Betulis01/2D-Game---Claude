# CharacterPanel

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/panels/CharacterPanel.java`
**Extends:** `UIPanel`
**Role:** Equipment panel displaying all gear slots and a stats block with level, STR/AGI/STA/SPD/DMG.

## Layout
- Position: `(20, screenH/2 - 250)`, Size: 200×480
- Background: `ui/panels/character_panel.png`
- Left column (5 slots): Head, Shoulder, Back, Chest, Wrist
- Right column (5 slots): Hands, Waist, Legs, Feet, Relic
- Bottom center (4 slots): Ring1, Ring2, Trinket1, Trinket2
- Weapon row (2 slots): Weapon left + right
- Center stats block: "Level X" in gold at (x+w/2-28, y+h-60), then STR/AGI/STA/SPD/DMG in white

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `equipSlots` | `List<EquipmentSlot>` | All 14 equipment slot widgets |
| `playerStats` | `PlayerStats` | Stats model for display |
| `playerXP` | `PlayerXP` | Provides level number for display |

## Key Methods
- `setPlayerStats(PlayerStats)` — wired from UIManager
- `setPlayerXP(PlayerXP)` — wired from UIManager; enables level display
- `getEquipSlots()` — returns all slots for drag listener wiring

## Rules
- Level displayed only if `playerXP != null`
- Stats displayed only if `playerStats != null && visible`
- Slot positions derived from `SLOT_SIZE=40f` and `SLOT_GAP=4f`
