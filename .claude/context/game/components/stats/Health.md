# Health

**File:** `core/.../game/components/stats/Health.java`
**Extends:** `Component`
**Role:** Manages an entity's HP. Handles damage application with iframe cooldown, healing, and death (fires DeathListener then destroys GameObject on 0 HP).

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `max` | `float` | Maximum health points |
| `current` | `float` | Current health points |
| `dead` | `boolean` | Set true on death to prevent double-fire |
| `damageCooldown` | `float` | Min seconds between damage applications |
| `damageTimer` | `float` | Time remaining on current cooldown |
| `deathListener` | `DeathListener` | Optional callback fired before destroy on death |

## DeathListener Interface
```java
public interface DeathListener {
    void onDeath(float worldX, float worldY, Scene scene);
}
```
Set via `setDeathListener(DeathListener)`. Fires **before** `getGameObject().destroy()`.

## Key Methods
- `applyDamage(float dmg)` — reduces HP if not dead and off cooldown; calls `onDeath()` if HP ≤ 0
- `heal(float amount)` — increases HP, capped at max
- `regenerate(dt, regen)` — stub, not implemented
- `isAlive()`, `getHealth()`, `getMaxHealth()`, `isDead()` — read accessors

## Death Sequence
1. `current ≤ 0` → `onDeath()` called
2. `dead = true`
3. `deathListener.onDeath(worldX, worldY, scene)` fired (if set)
4. `getGameObject().destroy()`

## Rules
- `damageCooldown` from constructor — not hardcoded
- `heal()` always caps at max
- `dead` guard prevents double-death if `destroy()` is called externally before `applyDamage` path fires

## Known Issues
- `regenerate()` is an empty stub — implement or remove
- No death animation — entity instantly disappears on death
- No damage variation (crits, resistance) — all damage is flat
