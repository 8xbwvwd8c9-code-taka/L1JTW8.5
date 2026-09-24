# 381 → 850 Migration Document Drift Cleanup Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Remove the confirmed auto-learn and transformation-card contradictions while preserving the repository's 850-first, analysis-only migration authority.

**Architecture:** Update each specialized audit before the cross-module isolation ledger. Treat the homepage and `TRANSFORM_CARD_COLLECTION_SET_AUDIT.md` as current evidence, preserve donor analysis as superseded history, and verify that no active statement still routes skipped work into implementation or calls the proven login consumer unproven.

**Tech Stack:** Markdown, Git, PowerShell, ripgrep

**Spec:** `docs/superpowers/specs/2026-09-24-381-to-850-db-audit-design.md`

## Global Constraints

- 850 repaired core is the only target/core authority; 381 is donor/reference only.
- This phase changes audit documentation only; it does not modify production Java, SQL, configuration, client resources, or branch topology.
- All records are committed and pushed only to the existing `analysis/381-to-850-db-migration` branch.
- Each optional DB module remains independently auditable and must not silently depend on another optional module.
- Unknown facts use `NOT_PROVEN` or `HOLD`; no ID, schema, resource, or runtime behavior is guessed.
- One commit covers one module or one inseparable lifecycle family.

## Review Focus

- Historical implementation prose in `AUTO_LEARN_SKILL.md` must not remain presented as an active implementation plan.
- The login correction must distinguish proven donor application from the still-unproven 850 recompute implementation.
- Base-card and set-bonus ownership must remain one collection lifecycle without claiming their DB tables are one inseparable install package.
- Client/UI/polymorph gates must remain separate from the server-side L3 collection owner.
- Cross-module summaries must point to specialized audits and must not override the homepage precedence rule.

---

### Task 1: Close Auto Learn Skill by explicit user decision

**Files:**
- Modify: `migration/381-to-850-db/modules/AUTO_LEARN_SKILL.md`

**Interfaces:**
- Consumes: homepage authority `w_自動學習技能 = SKIP_USER_DECISION`
- Produces: an archival specialized audit that cannot be mistaken for queued implementation work

- [ ] **Step 1: Record the pre-change contradiction**

Run:
```powershell
rg -n "Status: READY FOR IMPLEMENTATION DESIGN|Difficulty: L2|Migration architecture|PASS gate before implementation" migration/381-to-850-db/modules/AUTO_LEARN_SKILL.md
```
Expected: active implementation language is found, proving the conflict.

- [ ] **Step 2: Replace the active status and add supersession authority**

Use this header:
```markdown
Status: SKIP_USER_DECISION
Decision: SKIP
Difficulty: NOT_APPLICABLE_BY_USER_DECISION
Branch: `analysis/381-to-850-db-migration`

> **SUPERSEDED IMPLEMENTATION DESIGN:** The user explicitly decided not to migrate `w_自動學習技能`. The donor and compatibility analysis below is retained as historical evidence only. It is not an implementation queue, dependency, or migration recommendation.
```

Rename `## 3. Migration architecture` to `## 3. Superseded migration architecture (historical only)` and `## 6. PASS gate before implementation` to `## 6. Superseded implementation gate (do not execute)`. Append:
```text
STATUS=SKIP_USER_DECISION
DECISION=SKIP
IMPLEMENTATION_QUEUE=NO
REOPEN_ONLY_BY_EXPLICIT_USER_DECISION=YES
```

- [ ] **Step 3: Verify no active implementation status remains**

Run:
```powershell
rg -n "SKIP_USER_DECISION|SUPERSEDED IMPLEMENTATION DESIGN|IMPLEMENTATION_QUEUE=NO|READY FOR IMPLEMENTATION DESIGN" migration/381-to-850-db/modules/AUTO_LEARN_SKILL.md
```
Expected: the first three markers are present; the old status is absent.

- [ ] **Step 4: Validate and commit the module correction**

Run:
```powershell
git diff --check
git diff -- migration/381-to-850-db/modules/AUTO_LEARN_SKILL.md
git add -- migration/381-to-850-db/modules/AUTO_LEARN_SKILL.md
git commit -m "docs(migration): skip auto learn skill by user decision"
```
Expected: the commit contains only the auto-learn audit.

### Task 2: Correct transformation-card login ability evidence

**Files:**
- Modify: `migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md`

**Interfaces:**
- Consumes: `TRANSFORM_CARD_COLLECTION_SET_AUDIT.md` proof of `C_LoginToServer.getCard()` gameplay mutation
- Produces: corrected donor lifecycle evidence and an 850-first blocker list

- [ ] **Step 1: Record all stale claims**

Run:
```powershell
rg -n "STAT_APPLICATION_ON_LOGIN=NOT_PROVEN|LOGIN_STAT_APPLY=NOT_PROVEN|actual login stat-application hook still unclosed|login/recalc stat application hook" migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md
```
Expected: four stale claim classes are found.

- [ ] **Step 2: Replace the ownership and runtime conclusion**

State that quest state owns unlocks and `C_LoginToServer.getCard(pc)` mutates live player stats for unlocked base cards and sets. Record:
```text
UNLOCK_OWNER=QUEST_STATE
CARD_IDENTITY=TABLE_ROW/QUEST_ID
POLY_USE=PROVEN
STAT_DISPLAY_AGGREGATION=PROVEN
STAT_APPLICATION_ON_LOGIN=PROVEN
LOGIN_STAT_APPLY=PROVEN
DONOR_LOGIN_REAPPLY=PROVEN
DONOR_RECOMPUTE=NO
DONOR_DRIFT_RISK=HIGH
```
Explain that repeated incremental application is not a safe 850 design and does not prove an idempotent recompute or removal path.

- [ ] **Step 3: Rewrite classification and blockers around the 850 gap**

Use:
```text
SERVER_LEVEL=L3
CLIENT_DEP=YES
CLIENT_GATE=L4_BLOCKED_BY_SEMANTIC_MAPPING
BLOCKERS=850 CollectionOwner and idempotent recompute; authoritative CREATE schema; quest/card identity mapping; base-card/set integration without double application; semantic polymorph mapping; 850-native UI/client resource mapping
```
Do not claim donor HTML alone makes all server work L4.

- [ ] **Step 4: Verify specialized audits agree**

Run:
```powershell
rg -n "STAT_APPLICATION_ON_LOGIN=PROVEN|LOGIN_STAT_APPLY=PROVEN|SERVER_LEVEL=L3|CLIENT_GATE=L4_BLOCKED_BY_SEMANTIC_MAPPING|DONOR_DRIFT_RISK=HIGH" migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md
rg -n "STAT_APPLICATION=PROVEN|LOGIN_HOOK=C_LoginToServer.getCard|DONOR_RECOMPUTE=NO|DRIFT_RISK=HIGH" migration/381-to-850-db/modules/TRANSFORM_CARD_COLLECTION_SET_AUDIT.md
rg -n "STAT_APPLICATION_ON_LOGIN=NOT_PROVEN|LOGIN_STAT_APPLY=NOT_PROVEN|actual login stat-application hook still unclosed" migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md
```
Expected: the first two commands agree; the third produces no output.

- [ ] **Step 5: Validate and commit the correction**

Run:
```powershell
git diff --check
git diff -- migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md
git add -- migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md
git commit -m "docs(migration): prove transformation card login bonuses"
```
Expected: the commit contains only the login-ability audit.

### Task 3: Supersede stale isolation-ledger conclusions

**Files:**
- Modify: `migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md`

**Interfaces:**
- Consumes: corrected Auto Learn and Transformation Card specialized audits
- Produces: a cross-module ledger that cannot override newer canonical evidence

- [ ] **Step 1: Add a document-level authority warning**

Add after the title and branch authority lines:
```markdown
> **SUPERSEDED PARTIAL HISTORY:** This file preserves chronological first-pass findings. When an older section conflicts with the migration homepage or a newer specialized audit, the homepage precedence rule applies. Sections explicitly marked `SUPERSEDED` are evidence history only and must not drive implementation.
```

- [ ] **Step 2: Mark both Auto Learn sections superseded**

Add `Status: **SUPERSEDED by SKIP_USER_DECISION**` to the initial Auto Learn section and `Auto Learn Skill -> L2 confirmed`. Keep call-path evidence but state it no longer authorizes migration or implementation.

- [ ] **Step 3: Mark early transformation-card classifications superseded**

Mark the initial `Provisional L3` section and `Transformation Card / Collection -> L3 provisional confirmed` as superseded by `TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md` and `TRANSFORM_CARD_COLLECTION_SET_AUDIT.md`. Record: server collection owner L3; client gate separate; donor login stat application proven; 850 idempotent recompute blocked.

- [ ] **Step 4: Verify active cross-module statements**

Run:
```powershell
rg -n "SUPERSEDED by SKIP_USER_DECISION|SUPERSEDED.*TRANSFORM_CARD|server collection owner is L3|login stat application is proven|idempotent recompute" migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md
rg -n "Auto Learn Skill -> L2 confirmed|Transformation Card / Collection -> L3 provisional confirmed" migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md
```
Expected: historical headings remain, and nearby text marks them superseded.

- [ ] **Step 5: Validate and commit the ledger correction**

Run:
```powershell
git diff --check
git diff -- migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md
git add -- migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md
git commit -m "docs(migration): supersede stale module isolation findings"
```
Expected: the commit contains only the isolation ledger.

### Task 4: Run the canonical consistency gate and publish

**Files:**
- Verify: `migration/381-to-850-db/README.md`
- Verify: `migration/381-to-850-db/modules/AUTO_LEARN_SKILL.md`
- Verify: `migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md`
- Verify: `migration/381-to-850-db/modules/TRANSFORM_CARD_COLLECTION_SET_AUDIT.md`
- Verify: `migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md`

**Interfaces:**
- Consumes: Tasks 1–3 commits
- Produces: remote evidence that the first drift-cleanup batch is complete

- [ ] **Step 1: Verify Auto Learn authority is consistent**

Run:
```powershell
rg -n "w_自動學習技能.*SKIP_USER_DECISION|SKIP_USER_DECISION|IMPLEMENTATION_QUEUE=NO" migration/381-to-850-db/README.md migration/381-to-850-db/modules/AUTO_LEARN_SKILL.md migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md
```
Expected: homepage, specialized audit, and isolation ledger agree.

- [ ] **Step 2: Verify transformation-card authority is consistent**

Run:
```powershell
rg -n "STAT_APPLICATION(_ON_LOGIN)?=PROVEN|LOGIN_STAT_APPLY=PROVEN|CollectionOwner|idempotent|SERVER_LEVEL=L3|CLIENT_GATE" migration/381-to-850-db/README.md migration/381-to-850-db/modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md migration/381-to-850-db/modules/TRANSFORM_CARD_COLLECTION_SET_AUDIT.md migration/381-to-850-db/MODULE_ISOLATION_AUDIT.md
```
Expected: all active conclusions identify the donor login consumer as proven and the missing 850 owner/recompute plus semantic mappings as blockers.

- [ ] **Step 3: Scan for forbidden stale active conclusions**

Run:
```powershell
rg -n "STAT_APPLICATION_ON_LOGIN=NOT_PROVEN|LOGIN_STAT_APPLY=NOT_PROVEN|actual login stat-application hook still unclosed|Status: READY FOR IMPLEMENTATION DESIGN" migration/381-to-850-db
```
Expected: no output.

- [ ] **Step 4: Verify branch scope and commit sequence**

Run:
```powershell
git status --short
git diff --check
git log -4 --oneline
git diff --name-only 1624ec15625c282db0a2f44f083239a891bfdc32..HEAD
```
Expected: clean status, no whitespace errors, three focused commits after the plan commit, and only the three intended audit files changed during execution.

- [ ] **Step 5: Push the existing migration branch**

Run:
```powershell
git push origin HEAD:analysis/381-to-850-db-migration
git ls-remote origin refs/heads/analysis/381-to-850-db-migration
git rev-parse HEAD
```
Expected: remote ref and local `HEAD` resolve to the same SHA.
