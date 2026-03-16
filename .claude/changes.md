# Session Changes Log

> Compact log of prompts and changes per session. Newest first.
> Format: `[DATE] | PROMPT → OUTCOME`

---

## 2026-03-15
- Audio system: new `engine/audio/` package — `SoundCategory` (enum), `SoundDef` (JSON POJO), `AudioManager` (load/play/spatial), `AudioPlayer` (fire-and-forget component)
- `AudioManager.play()`: spatial falloff (vol + pan from dist to camera) for GAMEPLAY; flat for MENU; streams/loops for MUSIC
- `sounds.json` at `data/config/audio/sounds.json`; `fireball_spawn` + `fireball_explosion` entries added
- `EntityConfig`: added `String[] spawnSounds` field; `fireball.json` wired with `["fireball_spawn"]`
- `AttackPrefabs.createFireball()`: null-safe loop adds `AudioPlayer` per `cfg.spawnSounds` entry
- `FireballExplosion`: adds `AudioPlayer("fireball_explosion")` — plays spatially at explosion world position
- Interactable component system: extracted proximity/input/prompt logic out of WorldItem into reusable Interactable + InteractPrompt
- New: `Interactable(range, action, onInteract)` — range check (squared dist), fires callback; spawns InteractPrompt companion into overlayObjects
- New: `InteractPrompt(source)` — overlay component; reads source transform, draws button above item only when inRange
- WorldItem: removed playerTransform/interactTexture/showInteract/input fields, removed update(), stripped prompt from render(); tryPickup() made public
- WorldItemPrefab: wires WorldItem + Interactable(20f, PICKUP_ITEM, worldItem::tryPickup) together
- Scene: added removeOverlayObject() so Interactable.onDestroy() can properly remove companion from overlayObjects

---

## 2026-03-14
- XP/level loop wired: slime death awards 2 XP, bar pulses dark-purple, level-up shows gold "Level Up!" text + stats +2
- New: FloatingText (rise+fade component), FloatingTextPrefab, XPReward (self-wiring death listener), PlayerXP leveling
- Health: DeathListener interface added; fires before destroy() with world pos + scene ref
- DamageOnHit: spawns red "-X" floating text on hit
- XPReward refactored → self-registers death listener in start(), caches PlayerXP to avoid nested iterator crash
- UIManager: onLevelUp callback captures player Transform at init (not in callback) to avoid 3rd nested for-each crash
- UIManager item drag: bag→equip slot equips if accepted; bag→incompatible equip slot returns to original bag slot
- UIManager equip drag: drag to incompatible slot returns item to source equip slot instead of spawning at player
- LootDropper: ItemType now derived from cfg.slot (not hardcoded WEAPON); blue_ring added (RING, +2 STA +1 AGI, 40%)
- PNG-backed UIPanel backgrounds → panels render dedicated .png at 1:1 instead of manual pixel+tint draw
- UIPanel.onMouseUp() → fixed to return contains(mx,my) instead of false (clicks no longer leak through open panels)
- UIPanel → added loadPanelBg(path): loads PNG, updates w/h to match pixel dimensions; panelBg field + Gdx import
- UIAssetGenerator → added writePanelBg() + generates spellbook/character/talent panel PNGs on first run
- BagPanel/SpellBookPanel/CharacterPanel/TalentPanel → each calls loadPanelBg() after super(); BagPanel gap 4→6px
- Loot drop system → slime death (60%) spawns WorldItem on ground; player picks up with F key into bag
- New: ItemDefinition, ItemConfig (JSON POJO), Inventory (4×4 model), LootDropper, WorldItem, WorldItemPrefab, ItemSlot
- BagPanel → refactored from SpellSlot to ItemSlot grid; accepts Inventory; exposes refresh() + getItemSlots()
- UIManager → owns Inventory; handles item drag (slot↔slot swap, drop-outside spawns WorldItemPrefab at player pos)
- Assets.java + InputBindings (PICKUP_ITEM→F) + UIAssetGenerator (writePickupPrompt 24×24 "F" keycap) updated
- Game.java → added getScene() getter for UIManager world-item spawning

---

## 2026-03-11
- PanelMenuBar → removed procedural pixel drawing; each button now uses its own PNG texture passed from UIManager
- UIManager → added `btnBag/btnChar/btnBook/btnTal` textures loaded via `loadUI()` (safe fallback to whitePixel)
- PanelMenuBar → open-state tint changed from dark-blue fill to light-blue (HIGHLIGHT) tint over the button texture
- Texture filenames: `bag.png` exists; `char.png`, `spellbook.png`, `talent.png` fall back to white until added

---

## 2026-03-07 (session 2)
- Slime chase AI → replaced SlimeMovement with WanderMovement + ChaseMovement + SlimeAI (strategy pattern + state machine)
- Reynolds circle-ahead wander → WanderMovement: smooth arcing paths, idle pauses 2-10s, home radius 100px
- All prefabs → all hardcoded animation/sprite values replaced with cfg.sprite.* (frameDuration, idleFrameDuration, frames, directions)
- FireballExplosion → loads JSON config; transform parented to target so effect follows during playback
- EntityMover → added setMovement/getMovement/isMoving/getDirection; animation components now read from EntityMover (single source of truth)
- DamageOnHit → calls SlimeAI.aggro() on hit; ChaseMovement.range derived in start() from SpriteRenderer

---

## 2026-03-07
- Reconnect git remote → changed origin to `https://github.com/Betulis01/2D-Game---Claude.git`
- Full codebase analysis → documented all systems, design flaws, anti-patterns in agent report
- Rewrite `game-engine.md` → replaced JavaFX-era doc with authoritative LibGDX spec (22 sections, §21 known issues table)
- Create `/changes`, `/start`, `/finish` skills → session workflow commands in `.claude/commands/`
- Create `.claude/context/` tree → 30 `.md` files mirroring every source script with fields, logic, rules, known issues
- Update `/changes` skill → now also syncs context files for any source touched in session
- Update `/finish` skill → now runs full /changes procedure then syncs `game-engine.md`
