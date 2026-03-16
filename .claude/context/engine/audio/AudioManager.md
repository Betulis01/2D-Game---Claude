# AudioManager

**File:** `core/.../engine/audio/AudioManager.java`
**Extends/Type:** Plain class (owned by `Game`)
**Role:** Central audio service. Loads sound definitions from JSON, manages LibGDX Sound/Music instances, and plays sounds with optional spatial falloff.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `defs` | `Map<String, SoundDef>` | All loaded sound definitions keyed by id |
| `sounds` | `Map<String, Sound>` | LibGDX Sound instances (GAMEPLAY + MENU) |
| `music` | `Map<String, Music>` | LibGDX Music instances (MUSIC category) |
| `categoryVolumes` | `Map<SoundCategory, Float>` | Per-category volume multipliers (default 1.0) |
| `currentMusic` | `Music` | Currently playing music track (null if none) |

## Key Methods / Logic

### `load()`
Reads `data/config/audio/sounds.json` via LibGDX `Json`. For each `SoundDef`:
- MUSIC → `Gdx.audio.newMusic()`, looping set true
- GAMEPLAY/MENU → `Gdx.audio.newSound()`

### `play(id, x, y, cam)`
```
GAMEPLAY + cam != null:
  dist = distance((x,y), camera world pos)
  vol  = max(0, 1 - dist/maxDistance) * baseVolume * catVol
  pan  = clamp(dx / (maxDistance*0.5), -1, 1)
  sound.play(vol, 1, pan)

MENU (or null cam):
  sound.play(baseVolume * catVol)
```
Silent no-op if id is not loaded.

### `playMusic(id)` / `stopMusic()`
Stops current track, starts named track at `baseVolume * catVol`.

### `setCategoryVolume(cat, vol)`
Updates runtime multiplier. Does **not** retroactively adjust currently-playing music volume.

### `dispose()`
Disposes all Sound and Music instances.

## Dependencies
- `SoundDef`, `SoundCategory`
- `Camera` — reads `cam.getGameObject().getTransform().getWorldX/Y()` for spatial calculation
- LibGDX: `Gdx.audio`, `Gdx.files`, `Sound`, `Music`, `Json`

## Rules
- Owned and disposed by `Game`
- `load()` called once in `Game.initSystems()`, before scene load
- All play calls are fire-and-forget — no handle tracking needed for one-shots

## Known Issues
- `setCategoryVolume(MUSIC, ...)` does not update volume of currently playing track
- `SoundCategory.valueOf(def.category)` throws unchecked exception on bad input — no try/catch
