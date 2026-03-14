# Assets

**File:** `core/.../engine/utils/Assets.java`
**Role:** Central asset store. Loads and exposes all textures needed by the game. Owns a 1×1 white pixel texture for UI/debug drawing.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `manager` | `AssetManager` | LibGDX asset manager |
| `pixelTex` | `Texture` | 1×1 white texture backing |
| `pixelRegion` | `TextureRegion` | Region wrapping pixelTex for SpriteBatch drawing |

## Key Methods
- `load()` — queues all assets, blocks until complete, then creates pixelTex
- `getTexture(String path)` — returns loaded Texture by asset path
- `getPixel()` — returns 1×1 white TextureRegion for colored rectangle drawing
- `dispose()` — disposes manager and pixelTex

## Asset Path Constants
| Constant | Path |
|----------|------|
| `orc_sheet` | `player/orc8.png` |
| `slime_sheet` | `mob/slime.png` |
| `fireball_fly/explode/nohit` | `abilities/fireball/*.png` |
| `lightning_bolt` | `abilities/lightning_bolt/lightning_bolt.png` |
| `small_sword` | `items/weapons/small-sword.png` |
| `blue_ring` | `items/accessories/blue_ring.png` |

## Rules
- All asset paths defined as `public static final String` constants — no path strings elsewhere in code
- `new Texture(...)` only allowed in Assets or TiledMapLoader — nowhere else
- `load()` must be called after OpenGL context is available (after app start)
