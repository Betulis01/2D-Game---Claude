# Assets

**File:** `core/.../engine/utils/Assets.java`
**Role:** Central asset store. Loads and exposes all textures needed by the game. Owns a 1×1 white pixel texture for UI/debug drawing.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `manager` | `AssetManager` | LibGDX asset manager |
| `whitePixel` | `Texture` | 1×1 white texture for rectangles/bars |

## Key Methods
- `load()` — queues all assets for loading, blocks until complete
- `getTexture(String path)` — returns loaded Texture by asset path
- `dispose()` — disposes all textures and the manager

## Asset Paths
All paths defined as `public static final String` constants inside `Assets.java` — no path strings elsewhere in code.

| Constant | Path |
|----------|------|
| `orc_sheet` | `player/orc8.png` |
| `slime_sheet` | `mob/slime.png` |
| `fireball_fly/explode/nohit` | `abilities/fireball/*.png` |
| `lightning_bolt` | `abilities/lightning_bolt/lightning_bolt.png` |
| `small_sword` | `items/weapons/small-sword.png` |

## Rules
- `new Texture(...)` only allowed in Assets or TiledMapLoader — nowhere else
- `whitePixel` used by DebugRender and HealthRenderer for colored rectangle drawing
- `dispose()` called once on application exit by `Game.dispose()`
