# SlimeAnimation

**File:** `core/.../game/components/animation/SlimeAnimation.java`
**Extends:** `Component`
**Role:** Simple state-based animation controller for slimes. Plays "jump" while moving, "idle" while stopped.

## Update Logic
```
if movement.isMoving(): director.play("jump", true)
else:                   director.play("idle", true)
```

## Dependencies
- `Movement` (sibling) — moving flag
- `AnimationDirector` (sibling) — clip switching

## Rules
- No directional animation — slime uses single-row sprite sheet
- Clip names ("jump", "idle") must match what's registered in SlimePrefab
