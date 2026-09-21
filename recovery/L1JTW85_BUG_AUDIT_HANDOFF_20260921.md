# L1JTW8.5 850 Recovered Core — Bug Audit Conversation Handoff

Date: 2026-09-21

## 0. Current Mission

Continue auditing the recovered/decompiled **850 core**.

**MODE = AUDIT_ONLY**

From this handoff forward:

- inspect bugs;
- annotate evidence;
- classify severity as L1 / L2 / L3;
- use 880 + 381 audit documents as cross-check evidence;
- promote a cross-repo issue only after direct 850 confirmation;
- **DO NOT modify Java / SQL / runtime behavior** unless the user explicitly switches back to repair mode.

No new repair commits should be added during normal `GO` rounds.

---

## 1. Repository / Branch Roles

Repository:

`8xbwvwd8c9-code-taka/L1JTW8.5`

### Active audit branch

`analysis/l1jtw85-bug-audit`

Primary audit report:

`recovery/BUG_AUDIT_2026-09-20.md`

Audit-only mode marker/report update:

`d970eba123dbd21bcdb3b12223bd9f4c6a20a849`

### Frozen repaired/decompiled snapshot

`completed/l1jtw85-decompiled-fixes-20260921`

Snapshot marker:

`recovery/REPAIR_SNAPSHOT_COMPLETE_20260921.md`

Snapshot marker commit:

`0bc5c8cb87a361f6d5fcd96290a7edc106801fcc`

This branch contains the repair commits that had already been staged before the workflow returned to audit-only mode.

Treat it as **frozen**.

### Legacy repair branch

`fix/l1jtw85-audit-remediation`

Retained for history only.

Do not continue repair work there during audit-only mode.

---

## 2. Source / Recovery Boundaries

Recovered obfuscated Java:

`recovered-src-obf/`

Normalized recovered reference:

`recovery/normalized-src-vf/`

Class mapping:

`class_source_mapping.csv`

Recovery compile boundary:

- per-class sanitized-reference compile: **584 / 788 PASS**;
- **204** recovered classes still fail that recovery compile gate;
- normalized full-source compile: **FAIL**.

Therefore:

- a recovered-source compile error is **not automatically an original 850 runtime bug**;
- classify a runtime/core bug only from concrete semantics, data flow, persistence order, authorization boundaries, logs, or corroborating cross-version evidence;
- otherwise keep it RISK / NOTE / recovery defect.

---

## 3. Three-Level Severity Model

### L1 — Severe

Use for findings involving:

- economic/item integrity;
- cross-account / cross-player / cross-owner authorization;
- unrestricted character-state/stat mutation;
- unauthorized map/world-state mutation;
- castle/clan governance authorization with persistent effects;
- global/service-critical failures.

### L2 — Major

Use for:

- DB/RAM persistence divergence;
- non-atomic exchanges;
- lifecycle/logout/session inconsistency;
- concurrency races;
- repeatable subsystem failure;
- significant interaction-context / second-stage authorization failures.

### L3 — General

Use for:

- bounded validation issues;
- packet/session robustness;
- stale/null/type/index guards;
- lower-impact correctness/observability defects;
- defensive notes;
- conditional risks lacking full reachability evidence.

Detailed per-finding `Severity` text may be more specific and remains authoritative.

---

## 4. Current Audit Position

Current report maximum finding:

`850-225`

Finding identity must come from actual report headers:

`## BUG-850-NNN`
`## RISK-850-NNN`
`## NOTE-850-NNN`

Reserved/unused numbers caused by interrupted overlapping rounds:

`196, 204, 205, 206, 207`

Do **not** backfill those numbers.

Recent high-value findings include:

- `850-187` — pending-delete types 32–39 accepted by normal login;
- `850-190` — C_GiveItem not bound to actual target location;
- `850-193` — C_Door arbitrary object/type/map/distance boundary;
- `850-194` — cross-player pet target control;
- `850-195` — pet/summon action ownership gap;
- `850-197` — C_NpcAction pet/summon remote-control ownership gap;
- `850-198` — mercenary arrange trusts client castle/count;
- `850-199` — mercenary employ authorization + price/count/overflow defects;
- `850-200` — C_UsePetItem cross-player pet equipment mutation;
- `850-202` — C_LeaveClan trusts client clan name instead of authoritative clan id;
- `850-208` — C_GotoMap/C_Ship trusts client destination map/x/y; ticket failure originally ignored;
- `850-210` — castle tax mutation lacks leader authority;
- `850-211` — clan-watch relationships can be changed by non-leader members.

Recent verified defensive notes include:

- `850-188` — login exceptions fail closed through ClientThread cleanup;
- `850-189` — 850 CharReset does not use the 880 ORIGINAL_*[type] array path;
- `850-203` — normal attack has same-map/range/LOS checks;
- `850-209` — ship ticket identities confirm contexts but not authoritative destination coordinates;
- `850-212` — C_War enforces royal + clan-leader authority;
- `850-213` — player-call / teleport-to-player handlers are GM-gated.
- `850-219` — C_Fight / proposal initiation use a one-tile + facing helper and are not global-target authorization gaps;
- `850-224` — adjacent party handlers generally validate nullable party state before dereference;
- `850-225` — C_Title uses authoritative clan-leader identity rather than the rank-only emblem gate.

---

## 5. 880 / 381 Cross-Check Rules

880 and 381 audit reports are **comparison inputs**, not proof for 850.

Required promotion rule:

1. identify a documented 880/381 issue;
2. locate the corresponding 850 implementation;
3. prove the same data-flow / authorization / lifecycle defect in 850;
4. only then create an 850 finding.

If 850 differs defensively, create a NOTE instead of copying the bug.

Examples already excluded from 850 after direct comparison:

- L381 shared mutable PacketHandler singleton race;
- L381 static/global L1Magic combat context;
- L381 cached/unbounded thread-pool finding;
- exact L381 duplicate shutdown-save pattern;
- direct L381 C_Ship component mapping (850 has recovered C_GotoMap reporting C_Ship semantics instead);
- 880 trade target null dereference;
- 880 missing-peer trade recovery bug;
- 880 fixed-index party leader replacement;
- 880 chat-party fake-target null path;
- 880 destroyed-summon movement early return;
- 880 poison non-character cast;
- 880 pet-evolution consume-before-eligibility defect;
- 880 ORIGINAL_* CharReset array-bound path.

---

## 6. Repair Snapshot — Historical Only

Before audit-only mode, a separate fix branch accumulated repair commits.

Those repairs are now frozen in:

`completed/l1jtw85-decompiled-fixes-20260921`

Important rule:

**Do not infer that a finding is closed merely because a historical repair commit exists.**

The completed branch is a preserved repair snapshot and some changes were still pending full build/runtime validation.

The active audit report remains the evidence source of truth.

Do not add new fix commits while in audit-only mode.

---

## 7. Immediate Next Audit Priorities

### Priority A — unresolved L1/L2 boundaries

1. **850-208 transport destination authorization**
   - Continue reconstructing authoritative ship route destinations.
   - Current evidence proves ticket contexts but not exact server-authoritative map/x/y whitelist.
   - Audit only; do not hard-code a route.

2. **Skill trainer second-stage binding**
   - Existing finding `850-179`.
   - 2026-09-21 follow-up confirms the recovered four-stage flow itself contains no hidden pending trainer state: the list handlers do not store one, the server-packet constructors only enumerate skills, and the OK handlers do not read NPC/distance/pending context.
   - Retain as a confirmed second-stage interaction authorization gap; no code fix in audit-only mode.

3. **Ownerless effect worker reachability**
   - Existing `RISK-850-168`.
   - Find concrete production callers for caster-dependent effects spawned with null owner.
   - Promote only if reachable.

4. **Remaining object-id -> state mutation handlers**
   - Continue targeted scan of client packet handlers that resolve a global world object id and then mutate it.
   - Check: runtime type, ownership/master, same map, physical range, authoritative pending context.
   - Do not repo-wide scan unless targeted discovery requires it.

### Priority B — persistence / economic contracts

Continue looking for patterns where:

- RAM mutates before DB write;
- DB helper swallows SQLException;
- payout/reward occurs before durable consume/delete;
- two-table ownership changes lack one transaction;
- client amount/count/price reaches signed int multiplication.

Avoid duplicating existing 001–213 findings.

### Priority C — lifecycle / logout

Continue checking:

- stale object snapshots;
- double-delete / destroyed-state races;
- master/owner references cleared before override cleanup;
- exceptions inside one broad logout cleanup block that abort later stages.

---

## 8. Audit Workflow

For each `GO`:

1. choose a narrow subsystem / cross-version candidate;
2. fetch only targeted 850 classes and relevant 880/381 audit reference;
3. prove or exclude the defect;
4. assign L1 / L2 / L3;
5. append to `recovery/BUG_AUDIT_2026-09-20.md`;
6. commit **documentation only** on `analysis/l1jtw85-bug-audit`;
7. report new finding numbers and evidence summary.

Default:

- SUBAGENTS=0
- CONTEXT_EXPANSION=NO
- NO_REPO_WIDE_SCAN
- NO_ARCHIVE_SCAN
- NO_FULL_SUITE
- PUSH code fixes = NO
- MERGE = NO

Targeted source expansion is allowed when required to prove a finding.

---

## 9. Safety / Reporting Boundary

Several findings concern economic, authorization, and world-state integrity.

Reports should describe:

- missing validation;
- mutation ordering;
- persistence boundaries;
- impact;
- defensive repair concept only when useful for understanding.

Do not provide:

- packet-crafting recipes;
- concrete abuse values;
- live exploitation steps;
- repeated economic-abuse procedures.

---

## 10. Resume Command

If the user says:

`GO` / `繼續`

resume **audit-only** from the priorities above.

Do not return to repair mode unless the user explicitly asks to resume fixes.
