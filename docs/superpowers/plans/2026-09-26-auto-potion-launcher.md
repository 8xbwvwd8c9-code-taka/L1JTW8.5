# Auto Potion Launcher Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace the launcher's direct-use auto-potion path with independent HP/MP configuration UI that sends Auto Potion settings to the authoritative 850 server.

**Architecture:** Keep the existing WinForms launcher, inventory view, runtime read bridge, and persisted helper settings. Split potion settings into HP and MP sections, select potion item IDs from the existing inventory list, and send configuration changes through a dedicated server config bridge. The launcher must stop using `IItemUseBridge.UseObject()` for production auto-potion execution.

**Tech Stack:** C#/.NET Framework 4.0 x86 WinForms, INI persistence, existing launcher inventory/runtime bridges, PowerShell build script.

**Spec:** `docs/superpowers/specs/2026-09-26-auto-potion-design.md`

## Global Constraints

- Launcher is configuration/UI authority only; server is execution authority.
- Do not calculate heal/mana recovery in the launcher.
- Do not repeatedly send drink commands from the 100 ms potion timer.
- HP and MP can each be enabled/disabled independently and can coexist with Auto Hunt.
- Each resource supports percent or absolute threshold mode and a selected potion item ID.
- Potion candidates come from the current inventory list; do not hard-code one universal potion ID.
- Server must revalidate item existence/type before use.
- Keep .NET Framework 4.0 and x86 compatibility.
- `pwsh -File ./launcher/850Launcher/build.ps1` must remain the release build path.

## Review Focus

- Existing `helper.ini` from the old HP-only schema loads without crashing and migrates predictably.
- Inventory disconnect/unmapped state does not erase saved potion selections.
- User selects the same item for HP and MP: UI permits storage but server validation remains authoritative.
- Config transport unavailable: UI reports unsent state and does not fall back to direct native item use.
- Rapid UI changes: config sends are coalesced/on-save, not emitted every 100 ms.

---

### Task 1: Split persisted HP and MP settings

**Files:**
- Modify: `launcher/850Launcher/HelperSettings.cs`
- Modify: `launcher/850Launcher/helper.ini.example`
- Create: `launcher/850Launcher/AutoPotionSettingsValidation.cs`
- Modify: `launcher/850Launcher/850Launcher.csproj`

**Interfaces:**
- Produces independent HP/MP fields: enabled, usePercent, percent threshold, exact threshold, itemId, cooldownMs.
- Preserves old `[Potion]` keys as a migration source for HP only.

- [ ] **Step 1: Write failing settings validation**

Add a lightweight validation class invoked from a development/test entrypoint or PowerShell harness. Assert:
- old `Enabled/UsePercent/HPPercent/HPExact/ItemIds/CooldownMs` loads into HP settings.
- MP defaults disabled.
- new save/load round-trip preserves HP and MP independently.
- invalid/duplicate list input does not crash migration.

- [ ] **Step 2: Run RED validation**

Expected: FAIL because split MP settings do not exist.

- [ ] **Step 3: Implement new schema**

Use explicit sections/keys, for example `[PotionHP]` and `[PotionMP]`, while retaining read compatibility with legacy `[Potion]`. Store one selected item ID per resource in the final server-config model; legacy priority lists may be retained only for migration/UI convenience.

- [ ] **Step 4: Add new source to `850Launcher.csproj` and build**

Run: `pwsh -File ./launcher/850Launcher/build.ps1`
Expected: release x86 build PASS.

- [ ] **Step 5: Commit**

Commit message: `refactor(launcher): split hp and mp potion settings`

### Task 2: Replace client-side potion policy with config model

**Files:**
- Retire from production path: `launcher/850Launcher/AutoPotionController.cs`
- Retire from production path: `launcher/850Launcher/AutoPotionPolicy.cs`
- Create: `launcher/850Launcher/AutoPotionConfig.cs`
- Create: `launcher/850Launcher/AutoPotionConfigBridge.cs`
- Modify: `launcher/850Launcher/850Launcher.csproj`

**Interfaces:**
- `AutoPotionConfig` contains HP/MP enabled, mode, threshold, itemId, cooldown fields.
- `IAutoPotionConfigBridge.Send(AutoPotionConfig config)` returns status/success.
- No interface method accepts objectId for drinking a potion.

- [ ] **Step 1: Write RED config serialization/bridge tests**

Assert that HP and MP values serialize distinctly, disabled resources serialize empty/zero fields as required by the server contract, and no current HP/MP or recovery amount is included.

- [ ] **Step 2: Run RED test**

Expected: FAIL because config bridge/model do not exist.

- [ ] **Step 3: Implement config model and an unmapped/safe bridge first**

The safe bridge reports unavailable and sends nothing. Do not reuse `IItemUseBridge.UseObject()`.

- [ ] **Step 4: Remove `AutoPotionController` construction/tick from production MainForm flow**

Do not delete diagnostic ItemUse files yet; they remain useful for WP7 evidence but are no longer Auto Potion execution.

- [ ] **Step 5: Build and commit**

Run build script; expected PASS.
Commit: `refactor(launcher): stop direct potion item use`

### Task 3: Rebuild Potion tab for independent HP/MP controls

**Files:**
- Modify: `launcher/850Launcher/MainForm.cs`

**Interfaces:**
- Produces UI controls for HP enabled/mode/threshold/item and MP enabled/mode/threshold/item.
- Save action persists settings and requests one config send.

- [ ] **Step 1: Add UI contract validation or focused manual checklist**

Pin required controls/names/text in a source-level PowerShell test if no WinForms unit-test harness exists.

Required visible behavior:
- separate `自動 HP 藥水` and `自動 MP 藥水` toggles.
- each has percentage vs exact threshold controls.
- each shows selected potion item name/ID.
- no normal-user control depends on the old `UseItem bridge` status.

- [ ] **Step 2: Run RED source/UI contract**

Expected: FAIL against current single HP-only Potion tab.

- [ ] **Step 3: Implement UI with existing WinForms style**

Keep `BuildPotionTab`, `LoadValues`, and `SaveAll` structure. Remove the 100 ms drink-execution behavior; if a timer remains, it may refresh status/inventory display only.

- [ ] **Step 4: Build**

Run `pwsh -File ./launcher/850Launcher/build.ps1`.
Expected: PASS.

- [ ] **Step 5: Commit**

Commit message: `feat(launcher): add independent hp mp potion ui`

### Task 4: Add inventory-backed HP/MP potion selection

**Files:**
- Modify: `launcher/850Launcher/MainForm.cs`
- Use: `launcher/850Launcher/InventoryBridge.cs`
- Use: `launcher/850Launcher/RuntimeBridge.cs`
- Use: `launcher/850Launcher/ItemNameResolver.cs`

**Interfaces:**
- Consumes existing `RuntimeSnapshot.Items` (`ObjectId`, `ItemId`, `Count`, `Name`).
- Produces selected HP itemId and MP itemId in settings/config; objectId is not sent as the persistent setting.

- [ ] **Step 1: Write RED selection contract**

Verify selecting an inventory row can assign its ItemId separately to HP or MP, count <= 0 cannot be selected as currently usable, and selection persists after list refresh by ItemId rather than ListView row identity.

- [ ] **Step 2: Run RED test/check**

Expected: current `加入喝水清單` single-list behavior fails the new contract.

- [ ] **Step 3: Replace single `加入喝水清單` action**

Provide two explicit actions: assign selected inventory item to HP potion, assign selected inventory item to MP potion. Show item name + ItemId in Potion tab.

- [ ] **Step 4: Build and manual smoke test**

With mapped inventory, select HP and MP items, refresh inventory, save, reopen launcher, confirm selections remain.

- [ ] **Step 5: Commit**

Commit message: `feat(launcher): select hp mp potions from inventory`

### Task 5: Implement AUTO_POTION_CONFIG transport after server contract is fixed

**Files:**
- Modify: `launcher/850Launcher/AutoPotionConfigBridge.cs`
- Modify/add transport file matching the proven server extension path.
- Modify: `launcher/850Launcher/MainForm.cs`

**Interfaces:**
- Consumes exact server contract from server plan Task 5.
- Produces one config update on save/change boundary, not a drink action.

- [ ] **Step 1: Import exact server wire contract into a launcher test fixture**

The test must compare byte-for-byte or field-for-field with the server parser contract. No guessed opcode or framing.

- [ ] **Step 2: Run RED transport test**

Expected: safe/unmapped bridge cannot send yet.

- [ ] **Step 3: Implement transport using the proven extension path**

If client session encryption/framing is required, use the validated client-owned send path; do not write raw unframed game packets to the socket.

- [ ] **Step 4: Send config only when settings are saved/changed**

Remove any production dependency on `_potionPollTimer` for execution. Reconnect/relogin may resend last saved config once.

- [ ] **Step 5: Run contract test and build**

Expected: PASS.

- [ ] **Step 6: Commit**

Commit message: `feat(launcher): send auto potion config to server`

### Task 6: Update launcher documentation and regression

**Files:**
- Modify: `launcher/850Launcher/README.md`
- Modify: `launcher/850Launcher/helper.ini.example`
- Keep diagnostic WP7 files but mark them non-production for Auto Potion.

**Interfaces:**
- Produces player-facing and developer-facing documentation matching the new architecture.

- [ ] **Step 1: Update README architecture**

Replace old chain `HP policy -> inventory priority itemId -> objectId -> cooldown -> IItemUseBridge` with `UI/settings -> AUTO_POTION_CONFIG -> server AutoPotionService -> native Potion`.

- [ ] **Step 2: Run launcher build**

Run: `pwsh -File ./launcher/850Launcher/build.ps1`
Expected: build artifact exists and SHA256 is printed.

- [ ] **Step 3: Smoke-test the four combinations**

AutoPotion OFF/Hunt OFF; Potion ON/Hunt OFF; Potion OFF/Hunt ON; both ON. Launcher must remain usable when config transport is temporarily unavailable and must never fall back to direct item use.

- [ ] **Step 4: Commit**

Commit message: `docs(launcher): document server-authoritative auto potion`
