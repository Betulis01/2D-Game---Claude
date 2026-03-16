# SimpleAnimRenderer

**File:** `core/.../engine/render/SimpleAnimRenderer.java`
**Extends/Type:** `Component`
**Role:** Replaces SpriteRenderer + AnimationDirector + AnimationUpdater for all non-directional entities. Manages named animation clips and renders the current frame via LibGDX `Animation<TextureRegion>`.

## Fields
| Field | Type | Purpose |
|---|---|---|
| `clips` | `Map<String, Animation<TextureRegion>>` | Named animation clips |
| `currentAnim` | `Animation<TextureRegion>` | Currently playing animation |
| `stateTime` | `float` | Time accumulator for animation playback |
| `loop` | `boolean` | Whether current clip loops |
| `width`, `height` | `float` | World-space sprite size |
| `sortLayer` | `int` | Render sort layer (default 2) |
| `sortYOffset` | `float` | Y offset for depth sort (default height/2) |

## Key Methods / Logic
| Method | Notes |
|---|---|
| `clipFromSheet(tex, w, h, row, dur)` | Static helper: slices all columns of a spritesheet row |
| `clipFromSheet(..., frameCount)` | Static helper: slices fixed number of frames |
| `clipFromAtlas(atlas, regionName, dur)` | Static helper: builds Animation from indexed atlas regions. Returns null if atlas null or no regions found. |
| `addClip(name, anim)` | Registers clip. First clip auto-set as current. Null anim is ignored. |
| `play(name, loop)` | Resets stateTime=0 when switching clips; skips if same clip+loop and not finished. |
| `isFinished()` | True when non-looping animation has played to end. |
| `isLooping()` | Returns current loop flag. |
| `getCurrentFrame()` | Protected getter for current TextureRegion — used by subclasses. |
| `update(dt)` | Increments stateTime. |
| `render(batch)` | Transforms world→screen via Camera, draws centered frame. |

## Dependencies
- `Camera` (via `getScene().getCamera()`)
- LibGDX `Animation<TextureRegion>`, `TextureAtlas`, `Texture`

## Rules
- `RotatedSpriteRenderer` extends this class — override only `render()`.
- Sort layer defaults to 2; use `setSortLayer()` to change (e.g. layer 3 for projectiles).
- Null atlas in `clipFromAtlas` returns null — `addClip` ignores null safely.
