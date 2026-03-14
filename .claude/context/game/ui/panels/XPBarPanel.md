# XPBarPanel

**File:** `core/.../game/ui/panels/XPBarPanel.java`
**Extends:** `UIWidget`
**Role:** Renders the XP bar as 6 segments at screen top-center. Overlays a dark-purple pulse on newly-gained XP segments when gainTimer > 0.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `xpFull` | `Texture` | Filled segment texture |
| `xpEmpty` | `Texture` | Empty segment texture |
| `playerXP` | `PlayerXP` | Data source for current/max XP and gain overlay state |
| `SEGMENTS` | `int` | 6 — number of bar segments |
| `SEG_W/H` | `float` | 20×5px per segment |
| `SEG_GAP` | `float` | 2px gap between segments |

## Render Logic
1. Draw each segment as xpFull or xpEmpty based on `currentXP / maxXP` ratio
2. If `gainTimer > 0`: overlay dark-purple (`0.35, 0, 0.55`) tinted xpFull on segments within `[gainStartRatio, gainEndRatio]`, alpha = `gainTimer / 0.8`

## Rules
- `setPlayerXP(PlayerXP)` must be called before first render
- Position: `screenW/2 - totalWidth/2, y=0` (bottom edge of screen, centered)
- Gain overlay only appears for non-level-up XP gains (level-ups reset bar, no overlay)
