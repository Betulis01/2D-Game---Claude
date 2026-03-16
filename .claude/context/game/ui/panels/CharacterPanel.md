# CharacterPanel

**File:** `core/src/main/java/com/Betulis/Game2D/game/ui/panels/CharacterPanel.java`
**Extends:** `UIPanel`
**Role:** Equipment panel displaying all gear slots and a stats block with level, STR/AGI/STA/SPD/DMG.

## Layout (10 slots)
- Left column (3 slots): Head, Chest, Legs
- Right column (3 slots): Weapon, Shield, Relic
- Bottom row (4 slots): Ring1, Ring2, Trinket1, Trinket2
- Center stats block: "Level X" in gold, then STR/AGI/STA/SPD/DMG in white

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `equipSlots` | `List<EquipmentSlot>` | All 10 equipment slot widgets |
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
