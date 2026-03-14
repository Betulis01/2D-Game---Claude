# PlayerXP

**File:** `core/.../game/components/stats/PlayerXP.java`
**Extends:** `Component`
**Role:** Tracks the player's XP and level. Drives the XP bar gain overlay animation and fires an onLevelUp callback when a level threshold is crossed.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `currentXP` | `float` | XP accumulated in current level |
| `maxXP` | `float` | XP required per level (from player.json) |
| `level` | `int` | Current level (starts 1) |
| `gainStartRatio` | `float` | Bar ratio at start of last XP gain (for overlay) |
| `gainEndRatio` | `float` | Bar ratio at end of last XP gain (for overlay) |
| `gainTimer` | `float` | Time remaining on gain overlay pulse (0 = inactive) |
| `GAIN_DISPLAY` | `float` | Constant: 0.8s overlay duration |
| `onLevelUp` | `Runnable` | Callback fired when level-up occurs |

## Key Methods
- `init(EntityConfig.Stats)` — sets currentXP and maxXP from config
- `update(dt)` — decrements gainTimer
- `addXP(float amount) → boolean` — adds XP, handles multi-level overflow via while loop; sets gain overlay; fires onLevelUp; returns true if level-up occurred
- `setOnLevelUp(Runnable)` — wired by UIManager at scene load
- `getLevel()`, `getCurrentXP()`, `getMaxXP()`, `getGainTimer()`, `getGainStartRatio()`, `getGainEndRatio()`

## Level-Up Behavior
- On level-up: gain overlay is NOT set (bar resets, overlay would be misleading)
- `onLevelUp` callback fires once per threshold crossed (multiple levels in one addXP are each counted)

## Rules
- `onLevelUp` must not iterate scene objects — the callback fires from inside DamageOnHit's for-each loop, risking nested iterator crash
- `maxXP` comes from `player.json` — currently 10 (dev) or 100 (production)
