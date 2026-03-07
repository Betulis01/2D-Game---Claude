# AnimationDirector

**File:** `core/.../engine/animation/AnimationDirector.java`
**Extends:** `Component`
**Role:** Animation state machine. Stores named clips and manages which one is currently active.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `clips` | `HashMap<String, AnimationClip>` | Name → clip registry |
| `currentClip` | `AnimationClip` | Active clip |
| `currentClipName` | `String` | Name of active clip |
| `looping` | `boolean` | Whether current clip loops |
| `finished` | `boolean` | True when non-looping clip has played through |

## Key Methods
- `addClip(String name, AnimationClip clip)` — registers a clip
- `play(String name, boolean loop)` — switches to named clip, resets AnimationUpdater
- `getCurrentClip()` — active clip for renderers to sample
- `isFinished()` — true when non-looping clip has completed
- `isLooping()` — current loop state

## Rules
- Clip switch via `play()` always resets the AnimationUpdater's timer and frameIndex
- Calling `play()` with the same clip that is already playing is a no-op (avoid restart flicker)
- `finished` flag is set by AnimationUpdater, read by other components (e.g., AnimationAutoDespawner)
