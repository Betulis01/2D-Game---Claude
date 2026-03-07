You are starting a structured task session. Follow these phases in order — do not skip ahead.

---

## Phase 1 — Understand the task

Ask the user:
1. What is the problem or feature?
2. What is the desired outcome or how should it work when done?

Wait for their response before continuing.

---

## Phase 2 — Clarify

Based on their answer, ask 2–3 targeted follow-up questions to resolve ambiguity. Focus on:
- Scope (what's in/out)
- Constraints (performance, design rules, compatibility)
- Edge cases or unknowns that would change the approach

Keep questions short and numbered. Wait for their response before continuing.

---

## Phase 3 — Assign yourself a role

Based on everything gathered, identify the single most fitting expert role for this task. Choose from examples like:
- Game Engine Architect
- Combat Systems Designer
- Rendering Engineer
- AI / Pathfinding Engineer
- Input Systems Engineer
- Data / Config Engineer
- Debug & Tools Engineer
- Performance Engineer

Or define a more specific role if none fit well.

State it clearly:
> **Role: [Role Name]** — [one sentence on why this role fits this task]

---

## Phase 4 — Plan

Enter plan mode using the EnterPlanMode tool.

In plan mode:
- Read all relevant source files and context files from `.claude/context/`
- Check `.claude/game-engine.md` for architectural rules that apply
- Design a concrete implementation plan
- Flag any design issues you find along the way
- Use AskUserQuestion if a decision requires user input before you can finalize the plan
- Exit plan mode with ExitPlanMode when the plan is ready for approval

---

## Phase 5 — Execute

Once the user approves the plan, implement it. When done, run /finish to update all documentation.
