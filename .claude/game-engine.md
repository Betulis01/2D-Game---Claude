# 2D Game Engine — Authoritative Architecture Reference

> This document defines the engine's architecture, design rules, and implementation contracts.
> It is **engine law**. Code must conform to this document, not the other way around.
> When current code diverges from this spec, the spec wins — refactor the code.

---

## Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | LibGDX 1.14.0 |
| Build | Gradle multi-module |
| Desktop backend | LWJGL3 |
| Map format | Tiled TMX/TSX (XML) |
| Config format | JSON (LibGDX Json) |

**Module layout:**
```
core/     — All engine and game logic
lwjgl3/   — Desktop launcher only (no game logic here)
```

---

## 1. Engine Architecture

The engine follows a **Unity-inspired component composition model**:

```
Game (ApplicationAdapter)
 └── Scene
      └── GameObject[]
           └── Component[]
                └── Transform (always present)
```

**Rules:**
- Composition over inheritance — behavior lives in components, not subclasses
- `GameObject` is a pure container — no logic lives in it
- Every `GameObject` always has exactly one `Transform`
- Scenes own object lifetime — components never spawn or destroy objects directly
- Engine/Game owns scene lifetime — scenes never load other scenes directly

---

## 2. Game (Main Loop)

`Game` extends LibGDX `ApplicationAdapter` and owns:
- The `SpriteBatch` (single shared instance)
- The active `Scene`
- Global timing: `deltaTime`, FPS cap
- Input processor setup
- Asset initialization and disposal

**Update order per frame:**
1. `scene.update(dt)` — all game logic
2. `scene.render(batch)` — all rendering

Scene swaps happen at end-of-frame to avoid mid-iteration state corruption.

---

## 3. Scene

A `Scene`:
- Owns a `SnapshotArray<GameObject>` (safe to modify during iteration)
- Stores the loaded `TiledMap` and world `AABB` bounds
- Exposes `addObject()` / `removeObject()` — called only by scene itself or a dedicated spawner component

**Lifecycle:**
```
load() → update(dt) loop → unload()
```

Scenes represent discrete game contexts: levels, menus, cinematics. Each scene is a Java class. Level data (entity positions, map file) comes from Tiled object layers — not hardcoded in Java.

---

## 4. GameObject

- Container only — no logic
- Always has a `Transform`
- Components are added at construction time (in prefab factories)
- Lifecycle delegated to components: `start()` → `update(dt)` → `render(batch)` → `onDestroy()`
- Component lookup: `getComponent(Class<T>)` returns `null` if absent — callers must null-check

---

## 5. Component

Base class for all behavior. Every component has access to:
- `gameObject` — parent container
- `transform` — shortcut to parent's Transform
- `scene` — current scene

**Lifecycle callbacks:**
| Method | When called |
|--------|-------------|
| `awake()` | On component creation, before scene start |
| `start()` | First frame the scene runs |
| `update(dt)` | Every frame |
| `render(batch)` | Every frame, after all updates |
| `onDestroy()` | When GameObject is removed from scene |

**Rules:**
- One component = one responsibility
- Components communicate via direct `getComponent()` calls to siblings on the same GameObject
- Cross-object communication uses scene queries or a future event bus — never hard-wired scene traversal

---

## 6. Transform — HARD RULES

**Transform position is CENTER-ANCHORED. This is non-negotiable.**

- `worldX`, `worldY` = center of the object in world space
- Hierarchy: parent/child transforms with local ↔ world conversion
- Rotation and scale applied from center

**Consequences that must be upheld everywhere:**
- Renderers draw at `(worldX - width/2, worldY - height/2)`
- AABB bounds compute `minX = worldX + offsetX - width/2`
- Movement clamps: `min = halfSize`, `max = mapSize - halfSize`
- Camera follow targets center directly

Mixing center-anchored and top-left math is the root cause of all offset bugs. It is never acceptable.

---

## 7. Rendering Pipeline

**Single SpriteBatch** shared across all renderers. Batch is begun and ended by `Game`, not by individual components.

**Render order (in scene iteration order):**
1. `TileMapRenderer` — ground tile layers
2. `ObjectLayerRenderer` — static Tiled object tiles (decorations)
3. `SimpleAnimRenderer` / `LayeredSpriteRenderer` / `RotatedSpriteRenderer` — entity sprites (Y-sorted)
4. `HealthRenderer` — world-space UI (health bars)
5. `DebugRender` — always on top, screen-space

**Depth sorting:** `Scene.renderComparator` sorts by `sortLayer` first, then by Y descending (lower Y renders on top). Checks `SimpleAnimRenderer` first, then `LayeredSpriteRenderer`. `RotatedSpriteRenderer extends SimpleAnimRenderer` and is caught automatically.

**Camera transform applied in every renderer:**
```java
screenX = (worldX - camera.x) * zoom + screenWidth / 2
screenY = (worldY - camera.y) * zoom + screenHeight / 2
```

Zoom affects rendering only — never movement, collision, or combat math.

---

## 8. Camera

The camera is a **rendering service component** attached to its own `GameObject`.

- Follows a target `Transform` with frame-rate-independent lerp:
  ```java
  actualLerp = 1.0 - pow(1.0 - lerp, dt * 60)
  ```
- Clamps to world bounds so edges are never visible
- Supports zoom (scroll input via `CameraZoom` component)
- Exposes `worldToScreen` and `screenToWorld` coordinate conversion

**Rules:**
- Camera never affects simulation (movement, collision)
- Zoom range: 0.25× – 4.0× (configurable, not hardcoded)
- One camera per scene

---

## 9. AABB (Math Primitive)

Engine-owned `AABB` with:
- `x`, `y` = **min corner** (not center)
- `width`, `height`
- Accessors: `minX()`, `minY()`, `maxX()`, `maxY()`
- `intersects(AABB)` overlap test

**Rules:**
- AABB is mutable and allocation-free (update in place, not new each frame)
- World-space only — no screen-space AABBs
- LibGDX Rectangle is not used for engine logic

---

## 10. Hitbox & Hurtbox

Hitbox = offensive collision volume. Hurtbox = defensive (receiving) collision volume.

**World bounds formula (mandatory):**
```java
worldMinX = transform.worldX + offsetX - width / 2
worldMinY = transform.worldY + offsetY - height / 2
```

Both Hitbox and Hurtbox use identical math. No exceptions.

**Rules:**
- World bounds updated once per frame in `update()`
- AABB is reused (mutated), not re-allocated each frame
- Offset is relative to entity center

---

## 11. Collision System

**Current:** Hitbox iterates all scene GameObjects looking for Hurtboxes — O(n²).

**Rule:** This is acceptable up to ~50 entities. Beyond that, a spatial grid or quadtree is required. Do not extend the O(n²) approach to new entity types without implementing spatial partitioning first.

**Damage flow:**
```
Hitbox intersects Hurtbox
  → DamageOnHit applies damage to Health
  → Spawns hit effect
  → Destroys attack entity
```

No team/faction system yet — all hitboxes hit all hurtboxes. When factions are needed, add a `Faction` component and filter in `DamageOnHit`.

---

## 12. Combat & Attack System

**Architecture:**
```
Input / AI
  → AttackSpawner (cooldown gating)
    → Scene.addObject(AttackPrefab)
      → Hitbox (on attack entity)
        → Hurtbox (on victim)
          → Health.applyDamage()
```

**Rules:**
- Entities do not deal damage — they spawn attack entities that carry a Hitbox
- Attack entities are ephemeral GameObjects managed by the scene
- `AttackSpawner` reads from `EntityConfig` — no hardcoded stats
- Attack type dispatch (fireball vs lightning) belongs in `AttackPrefabs`, not in `AttackSpawner`
- Damage cooldown (iframes): `Health.damageCooldown` timer blocks re-application

**CombatState:** Tracks whether an entity is in active combat. Health bar visibility is gated on this. Combat exits after a configurable idle timeout (default 10s — must come from config, not hardcoded).

---

## 13. Movement System

**Architecture:**
```
Movement (abstract) → provides direction vector + moving flag
  PlayerController   — WASD → direction
  WanderMovement     — Reynolds circle-ahead wander with idle pauses and home radius
  ChaseMovement      — direct pursuit of a target Transform
  Projectile         — fixed direction, always moving

EntityMover          — integrates direction * speed * dt → Transform position
                       holds the *active* Movement (swappable at runtime via setMovement())

AI (game/components/ai/)
  SlimeAI            — state machine that switches EntityMover between WanderMovement and ChaseMovement
```

**Rules:**
- Direction vector is always normalized before use
- World bounds clamping in `EntityMover`:
  ```java
  clampedX = clamp(worldX, halfWidth, mapWidth - halfWidth)
  clampedY = clamp(worldY, halfHeight, mapHeight - halfHeight)
  ```
- Movement speed comes from `EntityConfig` — not hardcoded in Movement subclasses
- Projectiles bypass bounds clamping (destroyed by `AttackOutsideMapDespawner` instead)
- `EntityMover` is the single source of truth for movement state — animation components must call `entityMover.isMoving()` / `entityMover.getDirection()`, never `getComponent(Movement.class)` directly
- Each Movement subclass owns its speed value and exposes `getSpeed()` — AI controllers read this when switching behaviors

---

## 14. Animation System

All animation uses **LibGDX `Animation<TextureRegion>`** loaded from **TextureAtlas** files packed by `./gradlew packAllAtlases`.

### Non-directional entities (slime, fireball, effects)
```
SimpleAnimRenderer  — named clip registry, play(name, loop), isFinished()
                    — clipFromAtlas(atlas, regionName, dur) static helper
                    — clipFromSheet(tex, w, h, row, dur) static helper
RotatedSpriteRenderer extends SimpleAnimRenderer — adds rotation at render time
AnimationAutoDespawner — destroys when isFinished() on a non-looping clip
```

### Directional player
```
CharacterDirection  — 8-way enum; fromVector() uses bearing (90-angle+360)%360
DirectionalAnimSet  — loads 5 drawn dirs from atlas, pre-bakes flipped NW/W/SW copies
LayeredSpriteRenderer — list of layers (Map<CharacterState, DirectionalAnimSet>)
                      — stateTime resets on state change only (not direction)
CharacterAnimController — reads EntityMover, drives LayeredSpriteRenderer
EquipmentLayerManager  — adds/removes layers 1+ for equipped visual items
```

### Atlas naming convention
- Region name: `_<animTag>_<dirSuffix>` for directional (e.g. `_walk_se`)
- Region name: `_<animTag>` or `<animTag>` for non-directional (e.g. `_fly`, `explode`)
- Frame index is the numeric suffix before `.png`

### Rules
- All atlas files produced by `./gradlew packAllAtlases` — source PNGs are individual frames
- `Assets.getAtlas()` returns null if atlas not packed yet — all callers null-check gracefully
- No `AnimationClip`, `AnimationDirector`, `AnimationUpdater`, or `SpriteSheetSlicer` — all deleted
- `AnimationAutoDespawner` uses `SimpleAnimRenderer.isFinished()` — not `AnimationDirector`
- Equipment layer atlas path: `"equipment/<ItemConfig.spriteLayer>.atlas"`

---

## 15. Input System

`InputBindings` is a LibGDX `InputAdapter` registered as the global input processor.

- Actions defined as an `enum` (DEBUG, MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, LIGHTNING_BOLT, FIREBALL, etc.)
- `isPressed(action)` — true on the frame it was pressed
- `isHeld(action)` — true while key is held
- Key bindings are data in an `EnumMap` — rebindable at runtime

`PlayerInput` translates input + mouse position → attack direction → `AttackSpawner.tryAttack()`.

**Rules:**
- No magic key literals outside `InputBindings`
- Scroll delta must be cleared each frame after consumption
- Screen→world coordinate conversion for mouse always goes through `Camera.screenToWorld()`

---

## 16. Tiled Map System

**Loader** (`TiledMapLoader`):
- Parses TMX (XML) and external TSX tilesets
- Supports grid tilesets and image-collection tilesets
- Caches textures to avoid duplicate loads
- Produces a `TiledMap` data object

**TiledMap** contains:
- Dimensions (tiles and pixels)
- `TileLayer[]` — GID grids for rendering
- `ObjectLayer[]` — named layers of `MapObject`
- `Map<GID, TileData>` — tile metadata

**Object layers drive entity spawning.** Spawn positions, entity types, and counts come from Tiled object properties — not hardcoded in scene Java files:
```xml
<property name="prefab" value="Slime"/>
```

**Rules:**
- Tile coordinate origin: top-left (Tiled convention) — rendering accounts for this
- Object `x/y` in Tiled = bottom-left of object — renderers apply the Tiled offset correction
- Texture disposal is the loader's responsibility — dispose when scene unloads

---

## 17. Configuration System

Entity definitions live in JSON files under `assets/entities/`:
```json
{
  "id": "player",
  "name": "Player",
  "sprite": { "sheet": "path/to/sheet.png", "width": 32, "height": 32, "frames": 3, "rows": 8 },
  "collision": { "width": 14, "height": 14, "offsetX": 0, "offsetY": 0 },
  "hurtbox":   { "width": 10, "height": 20, "offsetX": 0, "offsetY": -2 },
  "hitbox":    { "width": 10, "height": 20, "offsetX": 0, "offsetY": -2 },
  "stats": {
    "moveSpeed": 120.0, "maxHealth": 100,
    "attack": 10, "defense": 5,
    "attackSpeed": 1.2, "critChance": 0.05,
    "damage": 25, "cooldown": 0.5, "duration": 1.5
  }
}
```

**Rules:**
- No balance numbers hardcoded in Java — they live in JSON
- Prefab factories read from `EntityConfig` — they do not define stats inline
- Config fields are validated on load — missing required fields throw a clear error, not a NullPointerException at runtime

---

## 18. Prefab System

Prefabs are **static factory methods** that construct a fully configured `GameObject`.

```java
public class PlayerPrefab {
    public static GameObject create(Scene scene, EntityConfig cfg) { ... }
}
```

**Rules:**
- Prefab = wiring only (add components, set config values) — no game logic
- Similar entities share a base setup method — no copy-paste between prefabs
- All configurable values come from `EntityConfig` parameter
- Entity names use `cfg.id`, not hardcoded strings

---

## 19. Asset Management

`Assets` owns all loaded textures, atlases, and the LibGDX `AssetManager`.

- All asset paths defined in one place (`Assets.java`) — no path strings scattered in components
- 1×1 white pixel texture created at startup for debug/UI rectangle drawing
- `Assets.dispose()` called on application exit
- `loadAtlasIfExists(path)` — skips load if file doesn't exist (graceful degradation before packing)
- `getAtlas(path)` — returns null if not loaded; all callers must null-check

**Rules:**
- No `new Texture(...)` outside `Assets` or `TiledMapLoader`
- TiledMapLoader's texture cache is cleared when the scene that loaded it is unloaded
- New atlases require: (1) constant in `Assets.java`, (2) `loadAtlasIfExists()` call in `load()`, (3) Gradle pack task in `build.gradle`

---

## 20. Folder Structure

```
core/src/main/java/
├── engine/
│   ├── system/          Game, Scene, GameObject, Component, Transform
│   ├── audio/           SoundCategory, SoundDef, AudioManager, AudioPlayer
│   ├── render/          SimpleAnimRenderer, RotatedSpriteRenderer, LayeredSpriteRenderer,
│   │                    TileMapRenderer, ObjectLayerRenderer, DebugRender
│   ├── animation/       CharacterState, CharacterDirection, DirectionalAnimSet,
│   │                    AnimationAutoDespawner
│   ├── camera/          Camera, CameraZoom
│   ├── tiled/           TiledMap, TiledMapLoader, TileData, TileLayer,
│   │                    ObjectLayer, MapObject
│   ├── math/            Vector2, AABB
│   ├── config/          ConfigLoader, EntityConfig
│   └── utils/           Assets
│
└── game/
    ├── input/           InputBindings, PlayerInput
    ├── components/
    │   ├── movement/    Movement, PlayerController, EntityMover, Projectile,
    │   │                WanderMovement, ChaseMovement
    │   ├── ai/          SlimeAI
    │   ├── combat/      AttackSpawner, AttackDurationDespawner, DamageOnHit,
    │   │                CombatState
    │   ├── collision/   Hitbox, Hurtbox, AttackOutsideMapDespawner
    │   ├── animation/   CharacterAnimController, SlimeAnimation
    │   ├── render/      EquipmentLayerManager
    │   ├── stats/       Health, HealthRenderer, PlayerXP, XPReward
    │   ├── items/       LootDropper, WorldItem
    │   └── (root)       FloatingText, Interactable, InteractPrompt
    ├── items/           ItemDefinition, ItemConfig, Inventory
    ├── prefabs/         PlayerPrefab, SlimePrefab, AttackPrefabs,
    │                    FireballExplosion, SlimeDeathEffect, CameraPrefab,
    │                    WorldItemPrefab, FloatingTextPrefab
    ├── scenes/          DeathValley, (future scenes)
    └── ui/
        ├── widgets/     SpellSlot, ItemSlot, UILabel, EquipmentSlot
        └── panels/      XPBarPanel, CharacterPanel, BagPanel, SpellBookPanel,
                         TalentPanel, SpellBarPanel, MouseSpellBarPanel, PanelMenuBar
```

Engine code (`engine/`) has **zero dependencies** on game code (`game/`). Game code depends on engine freely.

---

## 21. Audio System

### Architecture
```
Game
 └── AudioManager (owns all Sound/Music instances)
      └── loaded from data/config/audio/sounds.json

AudioPlayer (Component, fire-and-forget)
 └── start() → audioManager.play(id, worldX, worldY, camera) → setEnabled(false)
```

### Sound Categories
| Category | Behaviour |
|----------|-----------|
| `GAMEPLAY` | Spatial: volume + pan from distance to camera |
| `MENU` | Flat one-shot (no position) |
| `MUSIC` | Streamed, looping, flat |

### Spatial Formula (GAMEPLAY)
```java
dist = distance(soundPos, cameraPos)
vol  = max(0, 1 - dist / maxDistance) * baseVolume * catVol
pan  = clamp(dx / (maxDistance * 0.5), -1, 1)
sound.play(vol, 1, pan)
```

### sounds.json Format
```json
[
  { "id": "fireball_spawn", "path": "abilities/fireball/sounds/fireball_2.mp3",
    "category": "GAMEPLAY", "baseVolume": 1.0, "maxDistance": 400.0 }
]
```

### Wiring a Spawn Sound to an Entity
1. Add entry to `sounds.json`
2. Add `"spawnSounds": ["your_id"]` to the entity's JSON config (`EntityConfig.spawnSounds`)
3. Prefab factory loops `cfg.spawnSounds` and adds `new AudioPlayer(id)` — done

### Rules
- `AudioManager` is owned and disposed by `Game`
- `AudioPlayer` is the only way sounds trigger from world entities — no direct `audioManager` calls from game components
- Music/ambient managed directly: `audioManager.playMusic(id)` / `stopMusic()`
- All sound paths are in `sounds.json` — no path strings in Java code

---

## 22. Item & Loot System  <!-- was §21 -->

### Data Layer
- `ItemConfig` — POJO deserialized from `data/config/items/*.json` via LibGDX `Json`
- `ItemDefinition` — runtime descriptor: name, icon `Texture`, `ItemType`, `ItemConfig`; immutable
- `Inventory` — 4×4 flat array (`ItemDefinition[16]`); owned by `UIManager`

### Loot Drop Flow
```
Slime dies → LootDropper.onDestroy()
  roll < dropChance (0.6)
    → WorldItemPrefab.create(x ± 20, y ± 20, itemDef)
      → scene.addObject()
```

### Pickup Flow
```
Interactable.update(): squared dist < range² (20px) → inRange
  F pressed → onInteract.run() → WorldItem.tryPickup()
    → ui.getInventory().addItem(itemDef)
    → ui.refreshBag()
    → gameObject.destroy() → Interactable.onDestroy()
      → scene.removeOverlayObject(promptObject) → InteractPrompt disposed
```

### Interact Prompt Architecture
`WorldItemPrefab` attaches both `WorldItem` (icon render + tryPickup) and `Interactable` (range + input). `Interactable.start()` spawns a companion `InteractPrompt` GameObject into `overlayObjects` — it always renders on top of world entities. On destroy, `Interactable.onDestroy()` calls `scene.removeOverlayObject()` to prevent orphan prompts.

### Bag Drag-Drop Flow
```
ItemSlot.onMouseDown → UIManager.startItemDrag() → clear slot, store draggingItem

Released inside BagPanel           → slot-to-slot swap
Released on compatible EquipSlot   → equip; displaced item returns to source bag slot
Released on incompatible EquipSlot → item returns to original bag slot
Released in open space             → spawnWorldItemAtPlayer()
```

### Equipment Drag-Drop Flow
```
EquipSlot.onMouseDown → startEquipDrag() → unequip, store draggingEquipItem

Released on compatible EquipSlot   → equip; displaced goes to bag
Released on incompatible EquipSlot → item returns to source EquipSlot (re-equipped)
Released on BagSlot                → placed in bag
Released in open space             → spawnWorldItemAtPlayer()
```

### Config Format (`data/config/items/*.json`)
```json
{ "name": "Small Sword", "itemLevel": 5, "slot": "WEAPON",
  "damage": { "min": 5, "max": 8 }, "stats": { "strength": 2 } }
```

### Rules
- `WorldItem` handles only icon rendering and inventory insertion — all proximity/input logic lives in `Interactable`
- `Interactable` is reusable — attach to any GameObject needing proximity interaction (chests, NPCs, map objects)
- Interact button texture (`assets/ui/interact_button.png`) is loaded/disposed per `InteractPrompt` instance
- Companion prompt removal must use `scene.removeOverlayObject()` — `GameObject.destroy()` only removes from `objects`
- Item icon texture is owned by `Assets`, never disposed by item classes
- Item drag and spell drag are tracked independently in `UIManager`

---

## 23. XP, Leveling & Floating Text  <!-- was §22 -->

### XP Flow
```
Mob dies → XPReward.deathListener fires
  → playerXP.addXP(amount)
  → scene.addObject(FloatingTextPrefab "+N XP")
  → if leveled: playerXP.onLevelUp.run()
    → playerStats.levelUp() (+2 all base stats)
    → scene.addObject(FloatingTextPrefab "Level Up!")
```

### XPReward Self-Wiring Pattern
`XPReward.start()` registers its own `Health.DeathListener`. It caches `PlayerXP` at start time by scanning scene objects **once** — not inside the death callback — to avoid nested iterator crashes.

Any mob that should award XP: add `new XPReward(cfg.stats.xpReward)` to its prefab. No scene wiring needed.

### FloatingText
- `FloatingText` component: rises at `riseSpeed` world-units/sec, fades out linearly over `duration`
- Position converted world→screen via `Camera.worldToScreenX/Y()` each frame
- Font created in constructor (not `start()`) — safe for same-frame render
- Self-destroys when elapsed ≥ duration; disposes font in `onDestroy()`

### XP Bar Overlay
`XPBarPanel` renders a dark-purple overlay on newly-gained segments for 0.8s after each gain. On level-up the bar resets to 0; no overlay is shown.

### Nested Iterator Constraint
libGDX `SnapshotArray` supports at most **2 simultaneous for-each iterators**. The `DamageOnHit` loop consumes one slot. Any callback fired from within that loop may use a second. A third nested for-each throws `#iterator() cannot be used nested`. To avoid this:
- Cache scene lookups at `start()`, not in update/death callbacks
- `UIManager.onLevelUp` captures player Transform at `init()` time, not inside the lambda

---

## 24. Known Issues (Must Fix Before Extending)

| Issue | Location | Fix |
|-------|----------|-----|
| Scroll delta assignment bug `delta =- x` | `InputBindings:87` | `delta = -scrollDelta` |
| `Health.regenerate()` is empty stub | `Health.java` | Implement or remove |
| Hardcoded spawn positions | `DeathValley.java` | Use Tiled object layer |
| Hardcoded slime count (10 at same pos) | `DeathValley.java` | Use Tiled spawn objects |
| Combat timeout hardcoded `10.0f` | `CombatState.java` | Read from EntityConfig |
| `AttackSpawner` switch on attack ID | `AttackSpawner.java` | Move dispatch to prefab factory |
| Fireball/LightningBolt code duplicated | `AttackPrefabs.java` | Extract shared `createProjectile()` base |
| EntityConfig fields all public | `EntityConfig.java` | No change needed for now (LibGDX Json requires it) |
| `SoundCategory.valueOf()` throws on bad input | `AudioManager.java` | Wrap in try/catch, log warning, skip entry |
| Music volume not updated by `setCategoryVolume` while playing | `AudioManager.java` | Call `currentMusic.setVolume()` inside `setCategoryVolume` |

---

## 25. Design Principles (Always Apply)

1. **Center-anchored everywhere** — transforms, bounds, rendering
2. **Config-driven stats** — no magic numbers in Java
3. **Entities spawn attack entities** — hitboxes are ephemeral GameObjects
4. **Scene owns lifetime** — components request removal, scene executes it
5. **Engine knows nothing about game** — zero upward dependencies
6. **One component, one job** — split when a component has two reasons to change
7. **Null-check getComponent()** — it returns null; NPEs are not acceptable failures
8. **Tiled drives levels** — spawn points, entity placement, map data all from Tiled files
