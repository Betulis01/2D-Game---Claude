Wrap up the session by syncing all documentation to match the current state of the codebase.

---

## 1. Update `.claude/changes.md`

Add a compact session entry at the TOP (below the header). Format:

```
## [YYYY-MM-DD]
- PROMPT → OUTCOME (one line per significant change or decision)
```

Rules:
- Max 6 lines per session block. Abbreviate freely — this is a log, not prose.
- If today already has an entry, APPEND to it — no duplicate date headers.
- Only log things that changed code, architecture, or design decisions. Skip pure Q&A.
- Each line ≤ 120 chars.

---

## 2. Update `.claude/context/` files

For every source file that was **created, modified, or deleted** this session, update its corresponding `.md` in `.claude/context/`. The context tree mirrors the source tree:

```
engine/system/Game.java              → .claude/context/engine/system/Game.md
game/components/stats/Health.java    → .claude/context/game/components/stats/Health.md
```

Rules:
- **Modified**: update fields, methods, logic, or Known Issues to match current code
- **New file**: create the `.md` using the standard format below
- **Deleted**: delete the corresponding `.md`
- **Renamed/moved**: move the `.md` to match, update content
- Do NOT touch context files for files not changed this session

Standard `.md` format:
```markdown
# ClassName

**File:** `core/.../path/ClassName.java`
**Extends/Type:** ...
**Role:** One sentence — what this class does and why it exists.

## Fields
| Field | Type | Purpose |

## Key Methods / Logic
...

## Dependencies
...

## Rules
- Engine/design rules this class must follow

## Known Issues  ← only if actual issues exist
- ...
```

---

## 3. Update `.claude/game-engine.md` if needed

Read the current `game-engine.md`. Update ONLY sections that are now incorrect or incomplete:

- New systems → add a section
- Resolved known issues → remove from §21 Known Issues table
- Changed design decisions → update the relevant section
- New folder entries → update §20 Folder Structure

Do NOT rewrite accurate sections. If code diverges from spec in a bad way, add it to §21 — do not update the spec to match bad code.

---

## 4. Confirm

```
## Session Closed

**changes.md:** [what was logged]
**context files:** [list of files updated, or "none"]
**game-engine.md:** [sections updated, or "no changes needed"]
```
