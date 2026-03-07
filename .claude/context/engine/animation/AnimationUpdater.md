# AnimationUpdater

**File:** `core/.../engine/animation/AnimationUpdater.java`
**Extends:** `Component`
**Role:** Advances the frame index of the active clip each frame based on elapsed time.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `frameIndex` | `int` | Current frame being displayed |
| `timer` | `float` | Time accumulated since last frame advance |
| `director` | `AnimationDirector` | Source of clip and loop state |

## Update Logic
```
timer += dt
if timer >= currentClip.frameDuration:
  timer -= frameDuration
  frameIndex++
  if frameIndex >= frameCount:
    if looping: frameIndex = 0
    else: frameIndex = frameCount - 1; director.finished = true
```

## Key Methods
- `reset()` — sets frameIndex=0 and timer=0. Called by AnimationDirector on clip switch.
- `getFrameIndex()` — read by SpriteRenderer to sample the correct frame

## Rules
- AnimationUpdater must run **before** SpriteRenderer in the same frame
- `reset()` is called by AnimationDirector — never call manually from other components
