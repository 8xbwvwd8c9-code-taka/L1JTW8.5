# Auto Potion Server Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build an Auto Potion subsystem in the 850 server that works independently from Auto Hunt while sharing a single player automation tick.

**Architecture:** `AutoPotionService` owns HP/MP potion evaluation and adapter state. A player-level automation coordinator runs potion first, then Auto Hunt only when no potion was consumed in that tick. Launcher configuration is accepted through a separate Auto Potion config contract; server remains authoritative for HP/MP, inventory, item validation, cooldown, and native `Potion.a/b` execution.

**Tech Stack:** Java 8, L1JTW8.5 normalized Fast Dev core, PowerShell contract tests, `build850.ps1` incremental compiler.

**Spec:** `docs/superpowers/specs/2026-09-26-auto-potion-design.md`

## Global Constraints

- Production Java authority is `core/src/l1j/server/**`; do not add new production behavior under `recovered-src-obf/**`.
- Normal development validation uses `./build850.ps1`; `-Full` is final-regression only.
- HP native potion type is `aP()` 23..25 and executes `Potion.a(pc, item)`.
- MP native potion type is `aP()` 27 and executes `Potion.b(pc, item)`.
- Potion handler consumes the item; adapters must not decrement inventory again.
- HP and MP cooldown state are independent.
- One player automation tick owns ordering; do not create competing HP/MP/AutoHunt worker threads.
- Tick order is HP -> MP -> Auto Hunt; successful potion consumption ends the current tick.
- Auto Potion must work when Auto Hunt is disabled.
- Existing Auto Hunt behavior must remain unchanged when Auto Potion is disabled.

## Review Focus

- Both HP and MP are simultaneously below threshold: only HP consumes this tick; MP waits until a later tick.
- HP is disabled while MP is enabled: MP still runs with no HP dependency.
- A configured item is missing or fails native validation: no success result and no cooldown starts.
- Cooldown addition near `Long.MAX_VALUE`: saturation, no overflow wrap.
- Stop/restart/death lifecycle: both HP and MP timing state reset without requiring Auto Hunt to be enabled.

---

### Task 1: Lock independent AutoPotion service ordering

**Files:**
- Create: `tools/auto-hunt/AutoPotionServiceTest.java`
- Create: `core/src/l1j/server/model/gamesystem/autohunt/AutoPotionService.java`
- Use: `core/src/l1j/server/model/gamesystem/autohunt/AutoHuntConsumableController.java`

**Interfaces:**
- Consumes: `AutoHuntConsumableController.Result` and two independently supplied potion actions.
- Produces: `AutoPotionService.TickResult tick()` with enough state to distinguish `HP_CONSUMED`, `MP_CONSUMED`, and `NO_CONSUME`; `void reset()` resets both adapters.

- [ ] **Step 1: Write the failing service-order test**

Assert all of these in one focused test class:
- HP is evaluated before MP.
- HP `CONSUMED` prevents MP evaluation.
- HP non-consume permits MP evaluation.
- MP `CONSUMED` returns consumed and ends potion phase.
- both disabled returns no-consume.
- `reset()` invokes HP and MP reset independently.

- [ ] **Step 2: Run RED test**

Run the existing auto-hunt Java test harness or a minimal `javac/java` command using `.build850/cache/850-dev-base.jar` plus `.build850/classes`.
Expected: FAIL because `AutoPotionService` does not exist.

- [ ] **Step 3: Implement minimal `AutoPotionService`**

Constructor receives HP and MP potion action abstractions or adapters plus immutable settings access. Keep service independent of target selection and Auto Hunt.

- [ ] **Step 4: Run test and Fast Dev build**

Run test, then `./build850.ps1`.
Expected: test PASS; incremental build compiles only changed identities.

- [ ] **Step 5: Commit**

Commit message: `feat(auto-potion): add independent potion service`

### Task 2: Harden normalized HP/MP adapters to native 850 validation

**Files:**
- Modify: `core/src/l1j/server/model/gamesystem/autohunt/AutoHuntHpConsumableAdapter.java`
- Modify: `core/src/l1j/server/model/gamesystem/autohunt/AutoHuntMpConsumableAdapter.java`
- Test: `tools/auto-hunt/AutoHuntHpConsumableAdapterTest.java`
- Test: `tools/auto-hunt/AutoHuntMpConsumableAdapterTest.java`

**Interfaces:**
- Consumes: `L1PcInstance`, `L1ItemInstance`, `Potion.a`, `Potion.b`.
- Produces: `AutoHuntConsumableController.Host` behavior with independent cooldown and `reset()`.

- [ ] **Step 1: Add RED tests for rejection/cooldown behavior**

Pin these contracts for both resources:
- missing item -> false, cooldown unchanged.
- wrong `aP()` type -> false, cooldown unchanged.
- successful native use -> true, only own cooldown starts.
- `reset()` clears own cooldown.
- overflow-safe cooldown saturates at `Long.MAX_VALUE`.

- [ ] **Step 2: Run RED tests**

Expected: current minimal normalized adapters fail at least the uncovered native/blocking contracts.

- [ ] **Step 3: Port only proven common native item-use guards**

Inspect normalized `C_ItemUSe` and existing HP adapter evidence before adding each guard. Do not guess obfuscated method semantics. Keep HP type 23..25 and MP type 27 distinct.

- [ ] **Step 4: Run adapter tests and `./build850.ps1`**

Expected: PASS.

- [ ] **Step 5: Commit**

Commit message: `fix(auto-potion): enforce native potion guards`

### Task 3: Finalize HP/MP runtime settings contract

**Files:**
- Modify: `core/src/l1j/server/model/gamesystem/autohunt/AutoHuntRuntimeSettings.java`
- Test: `tools/auto-hunt/AutoHuntRuntimeSettingsMpPotionTest.java`
- Test: `tools/auto-hunt/AutoHuntRuntimeSettingsPotionTest.java`

**Interfaces:**
- Produces getters for HP and MP: enabled, mode, threshold, itemId, cooldownMs.
- Backward constructors preserve previous Auto Hunt call sites with MP disabled by default.

- [ ] **Step 1: Add/port RED assertions against production `core/src` class**

Do not use a `test-support` replacement class. Verify old 8-arg constructor disables both resources; legacy HP constructor leaves MP disabled; full constructor exposes both resources; invalid enabled and disabled field combinations throw.

- [ ] **Step 2: Run tests**

Expected: PASS only when tests compile production `AutoHuntRuntimeSettings`.

- [ ] **Step 3: Remove any test-support shadow that can mask production settings**

Delete or stop compiling `tools/auto-hunt/test-support/auto/hunt/AutoHuntConsumableController.java` when it can hide the production contract.

- [ ] **Step 4: Run `./build850.ps1` and settings tests**

Expected: PASS.

- [ ] **Step 5: Commit**

Commit message: `test(auto-potion): bind settings tests to production core`

### Task 4: Add player automation coordinator and preserve Auto Hunt independence

**Files:**
- Create: `core/src/l1j/server/model/gamesystem/autohunt/PlayerAutomationService.java`
- Modify: normalized Auto Hunt service/session files after mapping their production paths.
- Test: `tools/auto-hunt/PlayerAutomationServiceTest.java`

**Interfaces:**
- Consumes: `AutoPotionService.tick()` and Auto Hunt tick action.
- Produces: one player tick where potion phase precedes hunt phase.

- [ ] **Step 1: Write RED four-mode tests**

Verify:
- potion OFF + hunt OFF -> no action.
- potion ON + hunt OFF -> potion may consume.
- potion OFF + hunt ON -> existing hunt action runs.
- potion ON + hunt ON -> HP/MP phase first; consumed potion suppresses hunt only for that tick.

Also test missing/rejected potion allows hunt to proceed because it is not a successful consume.

- [ ] **Step 2: Run RED test**

Expected: FAIL because player coordinator does not exist.

- [ ] **Step 3: Implement coordinator without a second scheduler thread**

Reuse the existing Auto Hunt scheduler/lifecycle where possible. If a player already has an automation session, add potion phase to that session; when hunt is disabled but potion is enabled, keep the same session alive for potion-only ticks.

- [ ] **Step 4: Wire lifecycle reset**

Disconnect, restart, death, and explicit stop reset both potion adapters and hunt combat state.

- [ ] **Step 5: Run tests + incremental build**

Expected: PASS.

- [ ] **Step 6: Commit**

Commit message: `refactor(automation): separate potion and hunt services`

### Task 5: Add server-side Auto Potion configuration adapter

**Files:**
- Create: normalized config/packet adapter under the existing 850 packet handling package after mapping the exact packet extension point.
- Modify: `PlayerAutomationService` configuration entrypoint.
- Test: `tools/auto-hunt/AutoPotionConfigContractTest.java`

**Interfaces:**
- Consumes launcher logical `AUTO_POTION_CONFIG` fields: HP enabled/mode/threshold/itemId and MP enabled/mode/threshold/itemId.
- Produces validated server runtime settings; client never supplies current HP/MP or recovery amount.

- [ ] **Step 1: Write RED config validation tests**

Reject invalid modes, threshold ranges, non-positive item IDs for enabled resources, and malformed payloads. Disabling a resource clears its fields.

- [ ] **Step 2: Select a collision-free 850 extension path**

Inspect existing packet handler/opcode space and the launcher transport mechanism. Do not reuse a live 850 opcode without proof.

- [ ] **Step 3: Implement parser/adapter**

Server only stores configuration. Do not execute a potion directly from the config packet.

- [ ] **Step 4: Run contract tests + incremental build**

Expected: PASS.

- [ ] **Step 5: Commit**

Commit message: `feat(auto-potion): add server config contract`

### Task 6: Final server regression

**Files:**
- Modify tests only if needed.

**Interfaces:**
- Produces validated server handoff for launcher integration.

- [ ] **Step 1: Run all Auto Potion and Auto Hunt tests**

Expected: all PASS.

- [ ] **Step 2: Run `./build850.ps1`**

Expected: `BUILD=PASS` and incremental/noop depending working tree state.

- [ ] **Step 3: Run `./build850.ps1 -Full` once**

Expected: full readable-source compile PASS.

- [ ] **Step 4: Verify no production dependency on `recovered-src-obf/auto/hunt/**`**

Production runtime classes must come from `core/src`.

- [ ] **Step 5: Commit final server regression state**

Commit message: `test(auto-potion): complete server regression`
