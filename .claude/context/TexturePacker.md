# TexturePacker & Atlas Workflow

## What is an atlas?

A **TextureAtlas** packs many individual PNG frames into one large PNG sheet, plus a `.atlas` text file that maps region names to pixel coordinates. LibGDX uses it at runtime to slice out individual frames by name.

```
assets/player/Orc/_idle_n_0.png   ─┐
assets/player/Orc/_idle_n_1.png   ─┤  packPlayerAtlas  →  assets/player/atlas/Orc.png
assets/player/Orc/_walk_s_0.png   ─┤                   →  assets/player/atlas/Orc.atlas
...                                ─┘
```

---

## Naming convention for PNG files

Frame filenames must follow this pattern for TexturePacker's index detection:

```
<regionName>_<index>.png
```

Examples:
- `_idle_n_0.png`, `_idle_n_1.png` → region `_idle_n`, indices 0 and 1
- `_walk_se_0.png` → region `_walk_se`, index 0
- `explode_0.png`, `explode_1.png`, `explode_2.png` → region `explode`, indices 0–2
- `_fly_0.png` … `_fly_7.png` → region `_fly`, indices 0–7

The last `_<number>` before `.png` is always stripped as the index. Everything before it is the region name.

---

## How to pack

Run from the project root:

```bash
./gradlew packAllAtlases        # repack everything
./gradlew packPlayerAtlas       # repack only the player
./gradlew packSlimeAtlas        # repack only the slime
# etc.
```

The packed output goes to the `atlas/` subfolder inside each sprite directory. Old files are overwritten.

---

## Current atlas tasks

| Gradle task | Source folder | Output |
|---|---|---|
| `packPlayerAtlas` | `assets/player/Orc/` | `assets/player/atlas/Orc.atlas` |
| `packFireballAtlas` | `assets/abilities/fireball/` | `assets/abilities/fireball/atlas/fireball.atlas` |
| `packLightningAtlas` | `assets/abilities/lightning_bolt/` | `assets/abilities/lightning_bolt/atlas/lightning_bolt.atlas` |
| `packSpitAtlas` | `assets/abilities/spit/` | `assets/abilities/spit/atlas/spit.atlas` |
| `packSlimeAtlas` | `assets/mob/slime/` | `assets/mob/slime/atlas/slime.atlas` |

---

## Updating an existing sprite

1. Export the new PNG frames into the same source folder, keeping the same naming convention.
2. Run the relevant pack task (e.g. `./gradlew packSlimeAtlas`).
3. No Java changes needed — the atlas is reloaded automatically next run.

---

## Adding a new animation to an existing entity

Example: adding an `_attack` animation to the slime.

1. Export frames as `_attack_0.png`, `_attack_1.png`, ... into `assets/mob/slime/`.
2. Run `./gradlew packSlimeAtlas`.
3. In `SlimePrefab.java`, add the clip:
   ```java
   renderer.addClip("attack", SimpleAnimRenderer.clipFromAtlas(slimeAtlas, "_attack", 0.1f));
   ```
4. Play it where needed (e.g. in `SlimeAI.java`):
   ```java
   renderer.play("attack", false); // false = non-looping
   ```

---

## Adding a completely new entity with sprites

### Step 1 — Export frames
Put your PNG frames in a new folder, e.g. `assets/mob/goblin/`.
Name them: `_idle_0.png`, `_walk_0.png`, `_walk_1.png`, etc.

### Step 2 — Add a pack task in `build.gradle`
```groovy
makePackTask('packGoblinAtlas', 'mob/goblin', 'mob/goblin/atlas', 'goblin')
```
Also add it to `packAllAtlases`:
```groovy
task packAllAtlases {
    dependsOn 'packPlayerAtlas', ..., 'packGoblinAtlas'
}
```
Run `./gradlew packGoblinAtlas`.

### Step 3 — Add atlas constant + loading in `Assets.java`
```java
public static final String goblin_atlas = "mob/goblin/atlas/goblin.atlas";

// inside load():
loadAtlasIfExists(goblin_atlas);
```

### Step 4 — Use it in your prefab
```java
TextureAtlas atlas = assets.getAtlas(Assets.goblin_atlas);
SimpleAnimRenderer renderer = new SimpleAnimRenderer(cfg.sprite.width, cfg.sprite.height);
renderer.addClip("walk", SimpleAnimRenderer.clipFromAtlas(atlas, "_walk", 0.15f));
renderer.addClip("idle", SimpleAnimRenderer.clipFromAtlas(atlas, "_idle", 0.3f));
goblinObj.addComponent(renderer);
```

---

## Adding a directional player/character sprite

For 8-direction characters using `LayeredSpriteRenderer`, the naming must include direction suffixes:

| Direction | Atlas suffix | Example filename |
|---|---|---|
| North | `n` | `_idle_n_0.png` |
| North-East | `ne` | `_walk_ne_0.png` |
| East | `e` | `_walk_e_0.png` |
| South-East | `se` | `_idle_se_1.png` |
| South | `s` | `_walk_s_0.png` |

**Only export 5 directions (N, NE, E, SE, S).** The left-side directions (NW, W, SW) are automatically created as horizontally-flipped copies by `DirectionalAnimSet` at load time.

After exporting frames and repacking, `DirectionalAnimSet` picks them up via:
```java
atlas.findRegions("_idle_n")  // returns frames sorted by index
```

---

## Graceful degradation

If an atlas file doesn't exist (e.g. you haven't packed yet), `Assets.getAtlas()` returns `null`. All downstream code (`clipFromAtlas`, `DirectionalAnimSet`, `SimpleAnimRenderer.addClip`) handles null gracefully — the entity just renders nothing. No crash.

---

## Common mistakes

| Mistake | Fix |
|---|---|
| Frames named `idle0.png` (no underscore before index) | Rename to `idle_0.png` |
| Exported NW/W/SW directions | Delete them — they're auto-flipped from E/NE/SE |
| New atlas not loading | Check `Assets.java` has the constant and `loadAtlasIfExists()` call |
| Pack task runs but atlas is empty | Check the source folder path in `build.gradle` is correct |
| Animation stuck on first frame | Frame duration is 0 — set a non-zero `frameDuration` in config |
