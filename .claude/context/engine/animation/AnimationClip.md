# AnimationClip

**File:** `core/.../engine/animation/AnimationClip.java`
**Role:** Immutable data object. Holds a set of animation frames extracted from a sprite sheet, and the duration each frame is displayed.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `frames` | `TextureRegion[][]` | `[frameIndex][row]` — all frames for all directions |
| `frameDuration` | `float` | Seconds per frame (uniform across entire clip) |
| `frameCount` | `int` | Number of frames in the clip |

## Construction
```java
new AnimationClip(sheet, frameBegin, frameEnd, rowBegin, rowEnd, frameDuration)
// Extracts frames from SpriteSheetSlicer using the given row/frame ranges
```

## Key Method
- `getFrame(int frameIndex, int row)` — returns the TextureRegion for a given frame and direction row

## Rules
- Immutable after construction — never modified at runtime
- Frame duration is per-clip, not per-frame (per-frame support is a future improvement)
- Rows map to directions (e.g., row 0 = south, row 1 = southwest, etc.)

## Known Issues
- Uniform `frameDuration` for entire clip — cannot vary speed per frame
