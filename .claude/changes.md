# Session Changes Log

> Compact log of prompts and changes per session. Newest first.
> Format: `[DATE] | PROMPT → OUTCOME`

---

## 2026-03-14
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
