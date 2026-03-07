Orient yourself for this session by reading the project context files.

**Step 1 — Always read:**
- `.claude/changes.md` — recent session history and what has changed

**Step 2 — Read if relevant to the user's stated task:**
- `.claude/game-engine.md` — full architecture spec (read when the task involves engine systems, new components, rendering, combat, movement, config, or map loading)

**Step 3 — Report back concisely:**

```
## Session Start

**Last changes:** [1-2 sentence summary of the most recent entry in changes.md]

**Engine state:** [1-2 sentences — what's solid, what's known-broken per game-engine.md §21]

**Ready for:** [restate the user's task in one line]
```

Do not read source files or explore the codebase — context loading only. Keep the briefing under 15 lines.
