# PlayerAnimation

**File:** `core/.../game/components/animation/PlayerAnimation.java`
**Extends:** `Component`
**Role:** Maps player movement direction to the correct 8-directional walk or idle animation clip each frame.

## Update Logic
```
if entityMover.isMoving():
  dir = entityMover.getDirection()
  select walk clip from 8-way directional mapping
  director.play(clipName, true)
  lastDir = dir
else:
  select idle clip based on lastDir
  director.play(clipName, false)
```

## Direction Mapping (8-way)
```
(+x,+y)=up_right  (+x,-y)=down_right  (-x,+y)=up_left  (-x,-y)=down_left
(+x,0)=right  (-x,0)=left  (0,+y)=up  (0,-y)=down
```

## Dependencies
- `EntityMover` (sibling) — direction vector and moving flag (NOT Movement directly)
- `AnimationDirector` (sibling) — clip switching

## Rules
- Must read from `EntityMover`, not `getComponent(Movement.class)` — single source of truth for movement state
- Remembers `lastDir` for idle pose — entity faces last movement direction when standing
- Animation names defined once in the prefab's clip registration

## Known Issues
- Walk and idle direction logic duplicated in separate methods — refactor to single direction→clip lookup
- Animation names hardcoded as strings in this class
