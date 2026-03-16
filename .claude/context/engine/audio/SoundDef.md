# SoundDef

**File:** `core/.../engine/audio/SoundDef.java`
**Extends/Type:** Plain data class (POJO)
**Role:** JSON-deserialized descriptor for a single sound. Loaded from `sounds.json` by AudioManager.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `id` | `String` | Unique key used in `AudioManager.play(id, ...)` and `AudioPlayer` |
| `path` | `String` | Asset-relative path to the audio file |
| `category` | `String` | `"MUSIC"`, `"GAMEPLAY"`, or `"MENU"` — parsed to `SoundCategory` enum |
| `baseVolume` | `float` | Per-sound volume multiplier (default `1.0`) |
| `maxDistance` | `float` | GAMEPLAY only — world-unit radius at which volume reaches 0 (default `400`) |

## Rules
- All fields public — required by LibGDX Json deserializer
- Pure data — no logic
- `maxDistance` is ignored for MUSIC and MENU categories

## Known Issues
- No validation on load — missing/misspelled fields default to 0/null silently
