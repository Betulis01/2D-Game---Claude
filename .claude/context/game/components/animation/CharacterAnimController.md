# CharacterAnimController

**File:** `core/.../game/components/animation/CharacterAnimController.java`
**Extends/Type:** `Component`
**Role:** Reads `EntityMover` movement state and drives `LayeredSpriteRenderer` state/direction. Replaces the old `PlayerAnimation`.

## Fields
| Field | Type | Purpose |
|---|---|---|
| `entityMover` | `EntityMover` | Source of movement data |
| `renderer` | `LayeredSpriteRenderer` | Target renderer to drive |
| `lastDir` | `CharacterDirection` | Last known direction (default S); retained so idle faces last-moved direction |

## Key Methods / Logic
- `start()`: resolves both components from the same GameObject.
- `update(dt)`: if moving → convert `EntityMover.getDirection()` via `CharacterDirection.fromVector()`, set WALK; if stopped → set IDLE with retained `lastDir`.

## Dependencies
- `EntityMover`, `LayeredSpriteRenderer`, `CharacterDirection`, `CharacterState`

## Rules
- Must be on the same GameObject as `EntityMover` and `LayeredSpriteRenderer`.
- Does not handle ATTACK state — that's left for future combat integration.
