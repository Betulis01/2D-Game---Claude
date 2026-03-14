# XPReward

**File:** `core/.../game/components/stats/XPReward.java`
**Extends:** `Component`
**Role:** Self-contained XP award system. In start(), registers a DeathListener on the sibling Health component that awards XP to the player and spawns floating "+N XP" text on death.

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `amount` | `float` | XP awarded on death (from EntityConfig.stats.xpReward) |
| `XP_COLOR` | `Color` | Static dark-purple color for the XP floating text |

## Key Methods
- `start()` — finds sibling `Health`, caches `PlayerXP` via `findPlayerXP()`, registers death listener
- `findPlayerXP()` — scans scene objects once at start time; safe because start() is not inside any iterator
- `getAmount()` — returns the reward value

## Death Listener Behavior
On death:
1. `playerXP.addXP(amount)` — awards XP (may trigger level-up via onLevelUp callback)
2. `scene.addObject(FloatingTextPrefab.create(...))` — spawns "+N XP" dark-purple text at death position + 10px

## Why PlayerXP is Cached in start()
The death listener fires from inside `DamageOnHit.update()`'s for-each loop. Scanning `scene.getObjects()` inside the listener would cause a nested iterator crash (libGDX SnapshotArray only supports 2 simultaneous for-each iterators). Caching in `start()` is safe because start() runs outside any iterator.

## Rules
- One `XPReward` per mob prefab — add `new XPReward(cfg.stats.xpReward)` in the prefab
- `xpReward` field must exist in the entity's JSON `stats` block and in `EntityConfig.Stats`
- No scene wiring needed — the component self-registers everything
