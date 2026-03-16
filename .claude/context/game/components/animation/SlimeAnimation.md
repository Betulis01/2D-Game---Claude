# SlimeAnimation

**File:** `core/.../game/components/animation/SlimeAnimation.java`
**Extends:** `Component`
**Role:** Simple state-based animation controller for slimes. Plays "jump" while moving, "idle" while stopped.

## Update Logic
```
if !renderer.isLooping() && !renderer.isFinished(): return  // don't interrupt one-shot anims
if entityMover.isMoving(): renderer.play("walk", true)
else:                      renderer.play("idle", true)
```

## Dependencies
- `EntityMover` (sibling) — moving flag (NOT Movement directly — EntityMover is the source of truth)
- `SimpleAnimRenderer` (sibling) — clip switching

## Rules
- Must read from `EntityMover`, not `getComponent(Movement.class)` — the slime has multiple Movement components and EntityMover tracks which is active
- No directional animation — slime uses single-row sprite sheet
- Clip names ("jump", "idle") must match what's registered in SlimePrefab
