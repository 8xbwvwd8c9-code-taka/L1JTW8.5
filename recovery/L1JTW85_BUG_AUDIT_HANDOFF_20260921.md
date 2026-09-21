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

`work/l1jtw85-core-fixes`

Primary audit report:

`recovery/BUG_AUDIT_2026-09-20.md`

### Legacy audit branch

`analysis/l1jtw85-bug-audit`

This branch is superseded by `work/l1jtw85-core-fixes` per the main/recovery branch lifecycle. Do not use it as the authoritative WIP branch.

Audit-only mode marker/report update:

`d970eba123dbd21bcdb3b12223bd9f4c6a20a849`

### Frozen repaired/decompiled snapshot

`completed/l1jtw85-core-fixes`

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

### Split DB audit source

For DB verification, prefer main-branch split table files under:

`db/無使用給AI檢查用資料庫DB/`

Use `_INDEX.md` first, then read only the specific `<table>.sql` needed for the finding. Do **not** read the monolithic `db/8.5.sql` unless a split-table file is missing or demonstrably incomplete.

Current split index covers **99 tables**.

Important correction:
- `mob_quest_week.sql` exists and contains 66 rows.
- `RISK-850-259` is therefore an undersized-source robustness risk, **not** a default clean-DB bootstrap failure.

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

`850-267`

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
- `850-211` — clan-watch relationships can be changed by non-leader members;
- `850-215` — clan emblem mutation relies on rank codes rather than authoritative leader identity;
- `850-218` — C_NpcTalk lacks generic same-map/range binding while concrete talk callbacks can mutate state;
- `850-220` — online-gift claim leaves ready state reusable;
- `850-221` — mentor unlink can clear another character's MasterID outside the caller's relationship;
- `850-222` — C_GMTeleport lacks GM/access authorization and trusts client destination;
- `850-226` — C_CharcterConfig allocates from an untrusted internal 32-bit length before validation;
- `850-227` — C_RestartDead does not require authoritative dead state before respawn transition;
- `850-228` — C_GotoPortal can replay stale stored teleport destination state;
- `850-244` — character gift claim grants after swallowed DB persistence failure, creating RAM/DB reward divergence;
- `850-245` — castle treasury deposit/withdrawal continue after swallowed castle persistence failure;
- `850-246` — logout buff persistence rewrites the durable snapshot non-atomically;
- `850-247` — RankingTable places Type 7 into the Type 3 bucket;
- `850-248` — RankingTable top-50 truncation only reassigns a local variable;
- `850-249` — PetTable create/delete can diverge RAM and DB on persistence failure;
- `850-250` — SoulTower live top-10 list is never trimmed;
- `850-251` — SoulTower leaderboard delete/rebuild is non-transactional;
- `850-252` — malformed character_mobs_week rows cannot self-heal;
- `850-254` — weekly reset continues after failed bulk delete;
- `850-255` — first-row INSERT failure leaves live progress without durable backing;
- `850-256` — reset tasks only reschedule after all work succeeds;
- `850-257` — SoulTower cannot bootstrap from an empty leaderboard;
- `850-258` — furniture world state mutates before persistence success;
- `850-260` — wildcard IP-ban matching can terminate the GameServer accept thread;
- `850-261` — cursed-drop branch mutates the wrong item object;
- `850-262` — weapon-skill proc probability is one percentage point high;
- `850-263` — account login publishes the connection before binding the account, so accounts.online is skipped.
- `850-264` — HtmlCraft amount arithmetic can wrap material requirements non-positive; failed material removal is ignored before output creation.
- `850-265` — HomeTown monthly settlement zeros Contribution before calculating Pay, so monthly salary generation uses zero.
- `850-266` — MobSkills inclusive 0..99 probability gate biases 989 bundled rows upward by one percentage point.
- `850-267` — MobGroupTable keeps per-spawn flags in shared singleton fields across concurrent group respawns; keep as L2 RISK pending deterministic runtime reproduction.

Recent verified defensive notes include:

- `850-188` — login exceptions fail closed through ClientThread cleanup;
- `850-189` — 850 CharReset does not use the 880 ORIGINAL_*[type] array path;
- `850-203` — normal attack has same-map/range/LOS checks;
- `850-209` — ship ticket identities confirm contexts but not authoritative destination coordinates;
- `850-212` — C_War enforces royal + clan-leader authority;
- `850-213` — player-call / teleport-to-player handlers are GM-gated.
- `850-219` — C_Fight / proposal initiation use a one-tile + facing helper and are not global-target authorization gaps;
- `850-224` — adjacent party handlers generally validate nullable party state before dereference;
- `850-225` — C_Title uses authoritative clan-leader identity rather than the rank-only emblem gate;
- `850-230` — trade add quantity is normalized/clamped in authoritative trade engine;
- `850-232` — C_FishClick stops fishing but does not early-trigger reward generation;
- `850-234` — C_Blink overwrites stale teleport destination with current position before finalization;
- `850-240` — JoinClan/C_Rank retain proximity, same-clan and delegated rank-scope guards.

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
   - 2026-09-21 follow-up proved ownerless spawning is production-reachable for gfx `1263` (mob poison smoke) and several non-worker visual/control effects.
   - Firewall gfx `168` and cube gfx `6706/6712/6718/6724` are created by recovered production skill paths with a non-null player owner.
   - No production caller has yet been proven to pair the ownerless overload with the owner-dependent firewall/cube workers; keep as RISK.

4. **Client packet handler coverage / residual risks**
   - `class_source_mapping.csv` C_* coverage is now complete: every mapped client handler has at least one BUG/RISK/NOTE classification in the audit report; unclassified C_* count = 0.
   - Residual `RISK-850-241`: C_ExitGhost is a no-op when isGhost=true; intended external state-machine semantics not yet proven.
   - Residual `RISK-850-242`: C_AttackContinue writes client target id to currentAttackID without validation. Targeted reader scan across 96 primary runtime files (ai/ap/aq/ba/bc/bj) found no production reader outside the L1PcInstance accessor; risk materially reduced but not yet closed.
   - Future object-id work should be evidence-driven outside the already-classified C_* set.

### Priority B — persistence / economic contracts

Continue looking for patterns where:

- RAM mutates before DB write;
- DB helper swallows SQLException;
- payout/reward occurs before durable consume/delete;
- two-table ownership changes lack one transaction;
- client amount/count/price reaches signed int multiplication.
- Recent persistence findings now include `850-244`, `850-245`, `850-246`, `850-249`, `850-251`, `850-252`, `850-254`, `850-255`, and `850-258`.
- Use the main-branch split DB table source for schema/seed verification before promoting any DB-dependent finding.
- `RISK-850-259` was corrected after split DB verification: the baseline candidate pool has 66 rows, so only the <9-row robustness defect remains.

Avoid duplicating any existing finding; current maximum is `850-267`.

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
6. commit **documentation only** on `work/l1jtw85-core-fixes`;
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

resume **audit-only** from `850-268` using the priorities above.

Do not return to repair mode unless the user explicitly asks to resume fixes.
