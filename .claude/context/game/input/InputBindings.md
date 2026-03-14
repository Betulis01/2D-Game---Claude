# InputBindings

**File:** `core/.../game/input/InputBindings.java`
**Extends:** `InputAdapter` (LibGDX)
**Role:** Global input processor. Maps keyboard/mouse buttons to typed actions via an enum. Tracks pressed, held, and scroll state.

## Action Enum
```
DEBUG
MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT
SPELL_1, SPELL_2, SPELL_3, SPELL_4
MOUSE_SPELL_1, MOUSE_SPELL_2
TOGGLE_BAG, TOGGLE_CHARACTER, TOGGLE_SPELLBOOK, TOGGLE_TALENT
PICKUP_ITEM
```

## Default Bindings
| Action | Key/Button |
|--------|-----------|
| MOVE_* | WASD |
| SPELL_1-4 | Q, E, R, T |
| MOUSE_SPELL_1/2 | LMB, RMB |
| TOGGLE_BAG | B |
| TOGGLE_CHARACTER | C |
| TOGGLE_SPELLBOOK | N |
| TOGGLE_TALENT | T |
| PICKUP_ITEM | F |

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `keyBindings` | `EnumMap<Action, Integer>` | Action → LibGDX keycode |
| `mouseBindings` | `EnumMap<Action, Integer>` | Action → LibGDX mouse button |
| `scrollDelta` | `float` | Scroll wheel delta this frame |

## Key Methods
- `isPressed(Action)` — true only on the frame the key/button was first pressed (uses LibGDX `isKeyJustPressed`)
- `isHeld(Action)` — true every frame the key/button is held
- `getDisplayName(Action)` — returns "Q", "LMB", "RMB", etc. for UI labels
- `bind(Action, int key)` — rebind at runtime
- `getZoomDelta()` — scroll amount (negative = down), resets after consumption

## Rules
- Registered as LibGDX `Gdx.input.setInputProcessor(inputBindings)` in `Game.initInput()`
- No raw keycodes outside this class — always use Actions
- Bindings are rebindable at runtime via `bind(Action, key)`
