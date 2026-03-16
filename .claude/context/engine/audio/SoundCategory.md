# SoundCategory

**File:** `core/.../engine/audio/SoundCategory.java`
**Extends/Type:** `enum`
**Role:** Classifies sounds into three playback modes that determine how AudioManager handles volume and spatialization.

## Values
| Value | Behaviour |
|-------|-----------|
| `MUSIC` | Non-spatial, streamed, looping — background music |
| `GAMEPLAY` | Spatial: volume + pan derived from distance to camera |
| `MENU` | Non-spatial, one-shot — UI feedback sounds |

## Dependencies
- `AudioManager` — reads this enum to select playback path
- `SoundDef` — `category` string field is parsed via `SoundCategory.valueOf()`

## Known Issues
- `SoundCategory.valueOf()` throws `IllegalArgumentException` if `SoundDef.category` is misspelled or null — no graceful fallback
