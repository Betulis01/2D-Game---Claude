# AudioPlayer

**File:** `core/.../engine/audio/AudioPlayer.java`
**Extends:** `Component`
**Role:** Fire-and-forget sound trigger. Added to a GameObject at prefab construction; fires its sound on the first frame after the object enters the scene, then disables itself.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `soundId` | `String` | Id matching an entry in `sounds.json` |

## Key Methods / Logic

### `start()`
```
audioManager.play(soundId, transform.worldX, transform.worldY, scene.getCamera())
setEnabled(false)
```
Plays at the GameObject's world position (spatial) and goes dormant. Never runs `update()`.

## Dependencies
- `AudioManager` — obtained via `getScene().getGame().getAudioManager()`
- `Camera` — passed from `Scene.getCamera()`

## Rules
- One AudioPlayer per sound id — add multiple components for multiple sounds
- The sound plays at the transform position at the moment `start()` fires, not when the component was constructed
- Does not dispose anything — AudioManager owns all Sound/Music instances
