# Game

**File:** `core/.../engine/system/Game.java`
**Extends:** `ApplicationAdapter` (LibGDX)
**Role:** Entry point. Owns the main loop, SpriteBatch, Assets, and active Scene.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `batch` | `SpriteBatch` | Shared renderer — passed to all render calls |
| `assets` | `Assets` | Central texture/asset store |
| `audioManager` | `AudioManager` | Central sound system — loaded in `initSystems()` |
| `scene` | `Scene` | Currently active scene |
| `input` | `InputBindings` | Global input processor |
| `screenWidth/Height` | `int` | Window dimensions |
| `deltaTime` | `float` | Current frame delta |

## Lifecycle
```
create() → load assets, set input processor, load initial scene
render() → scene.update(dt) → scene.render(batch)
dispose() → assets.dispose()
```

## Key Methods
- `loadScene(Scene)` — swaps active scene (safe to call mid-frame; takes effect next frame)
- `getAssets()` — returns shared Assets instance
- `getAudioManager()` — returns the AudioManager
- `getScene()` — returns the currently active Scene
- `getUI()` — returns the UIManager
- `getScreenWidth/Height()` — used by camera and renderers
- `getDeltaTime()` — returns current frame dt

## Rules
- SpriteBatch is begun/ended here, NOT in individual components
- Scene swap happens at end of frame to avoid mid-iteration corruption
- No game logic lives in Game — it only orchestrates
