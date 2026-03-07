# HealthRenderer

**File:** `core/.../game/components/stats/HealthRenderer.java`
**Extends:** `Component`
**Role:** Renders a world-space health bar above the entity. Only visible when the entity is in combat.

## Render Logic
```
if NOT combatState.isInCombat(): return

barY = transform.worldY + spriteHeight / 2 + margin
screenX = camera.worldToScreenX(transform.worldX - barWidth/2)
screenY = camera.worldToScreenY(barY)

// Background (dark gray): full barWidth
batch.draw(whitePixel, screenX, screenY, barWidth * zoom, barHeight * zoom, dark gray)

// Fill (firebrick red): barWidth * (currentHp / maxHp)
fillWidth = barWidth * (hp / maxHp)
batch.draw(whitePixel, screenX, screenY, fillWidth * zoom, barHeight * zoom, red)
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `barWidth` | `float` | Health bar pixel width (24) |
| `barHeight` | `float` | Health bar pixel height (6) |
| `spriteHeight` | `float` | Entity sprite height for vertical offset |

## Dependencies
- `Health` (sibling) — current/max HP
- `CombatState` (sibling) — visibility gate
- `Camera` (via scene) — world→screen transform
- `Assets.whitePixel` — texture for drawing filled rectangles

## Rules
- Bar centered on entity horizontally
- Positioned above sprite top (worldY + spriteHeight/2)
- Bar dimensions are fixed at 24×6 — consider making configurable

## Known Issues
- Bar dimensions hardcoded — should come from EntityConfig or be a constructor parameter
