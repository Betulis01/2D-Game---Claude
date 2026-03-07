# AnimationAutoDespawner

**File:** `core/.../engine/animation/AnimationAutoDespawner.java`
**Extends:** `Component`
**Role:** Destroys the parent GameObject when a non-looping animation finishes. Used for one-shot effects (explosions, hit sparks).

## Update Logic
```
if director.isFinished(): gameObject.destroy()
```

## Dependencies
- `AnimationDirector` — polls `isFinished()`

## Usage
- Attach to any effect GameObject that should self-destruct after playing once
- Assumes the AnimationDirector is configured with a non-looping clip

## Rules
- Only useful when the clip is non-looping — attaching to a looping clip does nothing
- Destruction is deferred to end-of-frame via Scene's removal queue
