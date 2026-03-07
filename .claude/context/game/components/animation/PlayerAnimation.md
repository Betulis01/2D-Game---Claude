# PlayerAnimation

**File:** `core/.../game/components/animation/PlayerAnimation.java`
**Extends:** `Component`
**Role:** Maps player movement direction to the correct 8-directional walk or idle animation clip and sprite row each frame.

## Update Logic
```
if movement.isMoving():
  clip = directional walk clip based on direction vector angle
  row = corresponding sprite sheet row
  director.play(clipName, true)
  renderer.row = row
else:
  play idle clip for last known direction
  renderer.row = last row
```

## Direction Mapping (8-way)
```
N, NE, E, SE, S, SW, W, NW → clip names "walk_N", "walk_NE", etc.
                             → sprite rows 0–7 (depends on sheet layout)
```

## Dependencies
- `Movement` (sibling) — direction vector and moving flag
- `AnimationDirector` (sibling) — clip switching
- `SpriteRenderer` (sibling) — sets `row` for correct direction strip

## Rules
- Remembers last direction for idle pose — entity faces last movement direction when standing
- Animation names defined once in the prefab's clip registration — not hardcoded here (ideally)
- Direction snapped to 8 angles from the normalized direction vector

## Known Issues
- Walk and idle direction logic duplicated in separate methods — refactor to single direction→(clip,row) lookup
- Animation names currently hardcoded as strings in this class
