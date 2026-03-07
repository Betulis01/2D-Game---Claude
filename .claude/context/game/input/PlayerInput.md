# PlayerInput

**File:** `core/.../game/input/PlayerInput.java`
**Extends:** `Component`
**Role:** Translates player input into ability casts. Reads ability keys from InputBindings, converts mouse screen position to world position, and calls AttackSpawner.

## Update Logic
```
if FIREBALL pressed:   castAbility("fireball",       mouseWorldX, mouseWorldY)
if LIGHTNING pressed:  castAbility("lightning_bolt", mouseWorldX, mouseWorldY)

castAbility(id, targetX, targetY):
  dirX = targetX - transform.worldX
  dirY = targetY - transform.worldY
  normalize direction
  spawner.tryAttack(id, dirX, dirY)
```

## Dependencies
- `InputBindings` — ability key state
- `Camera` (via scene) — `screenToWorldX/Y()` for mouse position
- `AttackSpawner` (sibling component) — executes the attack

## Rules
- Mouse coordinates converted via Camera — never use raw screen coords in world logic
- Direction is always normalized before passing to AttackSpawner
- New ability types added here by extending the `if pressed` checks and providing the attack ID
