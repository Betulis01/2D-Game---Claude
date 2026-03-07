# InputBindings

**File:** `core/.../game/input/InputBindings.java`
**Extends:** `InputAdapter` (LibGDX)
**Role:** Global input processor. Maps keyboard/mouse buttons to typed actions via an enum. Tracks pressed, held, and scroll state.

## Action Enum
```
DEBUG, MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, LIGHTNING_BOLT, FIREBALL
```

## Fields
| Field | Type | Purpose |
|-------|------|---------|
| `keyBindings` | `EnumMap<Action, Integer>` | Action → LibGDX keycode |
| `heldKeys` | `Set<Integer>` | Currently held keycodes |
| `pressedKeys` | `Set<Integer>` | Keys pressed this frame (cleared next frame) |
| `scrollDelta` | `float` | Scroll wheel delta this frame |

## Key Methods
- `isPressed(Action)` — true only on the frame the key was first pressed
- `isHeld(Action)` — true every frame the key is held
- `getScrollDelta()` — raw scroll delta (negative = zoom out, positive = zoom in)
- `clearFrameState()` — clears `pressedKeys` and `scrollDelta` each frame

## Rules
- Registered as LibGDX `Gdx.input.setInputProcessor(inputBindings)` in `Game.create()`
- `clearFrameState()` must be called once per frame (by Game) to reset single-frame state
- No raw keycodes outside this class — always use Actions
- Bindings are rebindable at runtime via `keyBindings.put(Action, keycode)`

## Known Issues
- Bug on scroll: `delta =- scrollDelta` (line ~87) has no effect — should be `delta = -scrollDelta`
