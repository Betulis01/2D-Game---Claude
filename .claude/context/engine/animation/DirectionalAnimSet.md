# DirectionalAnimSet

**File:** `core/.../engine/animation/DirectionalAnimSet.java`
**Extends/Type:** Plain class
**Role:** Loads one animation state (e.g. "idle" or "walk") for all 8 directions from a TextureAtlas, pre-baking flipped copies at construction time.

## Fields
| Field | Type | Purpose |
|---|---|---|
| `anims` | `EnumMap<CharacterDirection, Animation<TextureRegion>>` | One animation per direction |

## Key Methods / Logic
- **Constructor**: loads 5 drawn directions from atlas regions named `"_<animTag>_<dirSuffix>"`. Falls back to `_<animTag>_s` if a specific direction not found. Calls `bakeFlipped()` for NW/W/SW.
- **`bakeFlipped(source, target)`**: creates new `TextureRegion` copies of each frame, calls `flip(true, false)`, stores as a new `Animation` under the target direction.
- **`getKeyFrame(dir, stateTime)`**: looks up animation for `dir`, falls back to S if null. Returns null if nothing found.

## Dependencies
- `CharacterDirection` — for direction lookups
- `TextureAtlas` — source of atlas regions
- LibGDX `Animation<TextureRegion>`

## Rules
- Atlas must exist before constructing. Null atlas is handled gracefully (constructor returns early).
- All flipped copies are pre-baked at construction — no runtime flipping.
