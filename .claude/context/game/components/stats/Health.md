# Health

**File:** `core/.../game/components/stats/Health.java`
**Extends:** `Component`
**Role:** Manages an entity's HP. Handles damage application with iframe cooldown, healing, and death (destroy on 0 HP).

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `currentHp` | `int` | Current health points |
| `maxHp` | `int` | Maximum health points |
| `damageCooldown` | `float` | Min seconds between damage applications |
| `damageCooldownTimer` | `float` | Time since last damage taken |

## Key Methods
- `applyDamage(int amount)` — reduces HP if off cooldown; destroys GameObject if HP ≤ 0
- `heal(int amount)` — increases HP, capped at maxHp
- `getCurrentHp()`, `getMaxHp()` — read by HealthRenderer
- `regenerate()` — **stub, not implemented**

## Death
- `gameObject.destroy()` called immediately when HP ≤ 0
- No death animation or state — instant removal

## Rules
- `damageCooldown` from EntityConfig — not hardcoded
- `heal()` always caps at maxHp
- First damage frame always applies regardless of cooldown timer state

## Known Issues
- `regenerate()` is an empty stub — implement or remove
- No death animation — entity instantly disappears on death
- No damage variation (crits, resistance) — all damage is flat
