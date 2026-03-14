# PlayerStats

**File:** `core/.../game/ui/PlayerStats.java`
**Extends/Type:** Plain class
**Role:** Holds computed player stats (base + equipment bonuses). Recalculated whenever equipment changes or the player levels up.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `baseStrength/Agility/Stamina` | `int` | Base stats before equipment |
| `baseSpeed/baseDamage` | `float` | Base speed and damage |
| `strength/agility/stamina` | `int` | Computed stats (base + equipment) |
| `speed/damage` | `float` | Computed speed and damage |
| `currentEquipment` | `Equipment` | Last equipment passed to recalculate(); used by levelUp() |

## Key Methods
- `init(EntityConfig.Stats)` — sets base stats from player config; calls `recalculate(new Equipment())`
- `recalculate(Equipment eq)` — stores `currentEquipment`, resets to base, sums all equipped item bonuses
- `levelUp()` — increments all base stats by 2 (`baseStrength/Agility/Stamina/Damage += 2`), calls `recalculate(currentEquipment)`

## Rules
- `recalculate()` always called after equip/unequip in UIManager
- `levelUp()` called from the `PlayerXP.onLevelUp` callback in UIManager
- `currentEquipment` stored so `levelUp()` can re-apply bonuses without UIManager passing it
