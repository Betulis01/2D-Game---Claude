# PlayerController

**File:** `core/.../game/components/movement/PlayerController.java`
**Extends:** `Movement`
**Role:** Maps WASD keys to a normalized movement direction each frame.

## Update Logic
```
read MOVE_UP/DOWN/LEFT/RIGHT from InputBindings → build direction vector
if any key held: normalize direction, moving = true
else: direction = (0,0), moving = false
```

## Dependencies
- `InputBindings` — held key state for movement actions

## Rules
- Diagonal movement normalized to prevent faster diagonal speed
- Direction is zero vector (not null) when not moving
