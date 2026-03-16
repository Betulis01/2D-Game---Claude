# Assets

**File:** `core/.../engine/utils/Assets.java`
**Role:** Central asset loader using LibGDX `AssetManager`. Holds path constants and provides typed getters for textures and atlases.

## Fields / Constants
| Constant | Path | Type |
|---|---|---|
| `orc_sheet` | `player/orc8.png` | Texture |
| `small_sword` | `items/weapons/small-sword.png` | Texture |
| `blue_ring` | `items/accessories/blue_ring.png` | Texture |
| `orc_atlas` | `player/atlas/Orc.atlas` | TextureAtlas |
| `fireball_atlas` | `abilities/fireball/atlas/fireball.atlas` | TextureAtlas |
| `lightning_atlas` | `abilities/lightning_bolt/atlas/lightning_bolt.atlas` | TextureAtlas |
| `spit_atlas` | `abilities/spit/atlas/spit.atlas` | TextureAtlas |
| `slime_atlas` | `mob/slime/atlas/slime.atlas` | TextureAtlas |
| `pixelTex` / `pixelRegion` | — | Runtime 1×1 white texture for colored rects |

## Key Methods
| Method | Notes |
|---|---|
| `load()` | Queues all textures + atlases (atlas only if file exists), calls `finishLoading()`, creates pixel texture |
| `loadAtlasIfExists(path)` | Checks `Gdx.files.internal(path).exists()` before queuing atlas load |
| `getTexture(path)` | Returns `Texture` from manager |
| `getAtlas(path)` | Returns `TextureAtlas` or **null** if not loaded (graceful degradation) |
| `getPixel()` | Returns 1×1 white `TextureRegion` |

## Rules
- All asset paths defined as `public static final String` constants — no path strings elsewhere in code.
- Atlas files may not exist if `packAllAtlases` hasn't been run. `getAtlas()` returns null — all callers must null-check.
- Add new atlases in two places: constant + `loadAtlasIfExists()` call in `load()`.
- `new Texture(...)` only allowed in Assets or TiledMapLoader — nowhere else.
