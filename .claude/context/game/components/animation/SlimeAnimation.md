# SlimeAnimation

**File:** `core/.../game/components/animation/SlimeAnimation.java`
**Extends:** `Component`
**Role:** Simple state-based animation controller for slimes. Plays "jump" while moving, "idle" while stopped.

## Update Logic
```
if entityMover.isMoving(): director.play("jump", true)
else:                      director.play("idle", true)
```

## Dependencies
- `EntityMover` (sibling) — moving flag (NOT Movement directly — EntityMover is the source of truth)
- `AnimationDirector` (sibling) — clip switching

## Rules
- Must read from `EntityMover`, not `getComponent(Movement.class)` — the slime has multiple Movement components and EntityMover tracks which is active
- No directional animation — slime uses single-row sprite sheet
- Clip names ("jump", "idle") must match what's registered in SlimePrefab
