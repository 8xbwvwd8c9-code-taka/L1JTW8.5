# L1JTW8.5 Auto Potion Architecture Design

Date: 2026-09-26

## Goal

建立獨立於 Auto Hunt 的自動喝水系統。

玩家可以：

- 只開自動喝水
- 只開自動狩獵
- 同時開啟兩者
- 同時關閉兩者

登入器負責設定 UI 與設定封包；850 server 負責實際 HP/MP 判斷、道具驗證、冷卻與原生 Potion 執行。

## Runtime Authority

Production Java authority：

`core/src/l1j/server/**`

`recovered-src-obf/**` 僅作為：

- 反編譯來源
- 行為對照
- migration reference

不得再作為新功能正式 runtime source。

Fast Dev 日常編譯：

`./build850.ps1`

預設 incremental。

`-Full` 僅在完整驗收需要時使用。

## Proven 850 Native Mappings

Class mappings:

- `ap/q` -> `l1j.server.model.instance.L1ItemInstance`
- `ap/s` -> `l1j.server.model.instance.L1MonsterInstance`
- `ap/t` -> `l1j.server.model.instance.L1NpcInstance`
- `ap/u` -> `l1j.server.model.instance.L1PcInstance`
- `aq/f` -> `l1j.server.model.L1Character`
- `aw/d` -> `l1j.server.model.item.action.Potion`

MP access:

- `pc.eb()` = current MP
- `pc.ex()` = max MP
- `pc.i_(int)` = set MP with native cap/update behavior

Inventory:

- `pc.j()` = `L1PcInventory`
- inherited `L1Inventory.b(int itemId)` returns `L1ItemInstance` by item ID

Potion dispatch:

- HP potion item type `aP()` 23..25 -> `Potion.a(pc, item)`
- MP potion item type `aP()` 27 -> `Potion.b(pc, item)`

Potion handlers themselves consume one item.

## Architecture

One player automation scheduler/tick owns ordering.

Services remain independent:

```text
PlayerAutomation
├─ AutoPotionService
│  ├─ HP
│  └─ MP
└─ AutoHuntService
   ├─ target
   ├─ movement
   ├─ skill
   └─ attack
```

Do not create separate competing threads for Auto Potion and Auto Hunt.

## Tick Ordering

When both systems are enabled:

```text
1. HP potion check
2. If HP potion CONSUMED -> end current tick
3. MP potion check
4. If MP potion CONSUMED -> end current tick
5. Auto Hunt action
```

This prevents one tick from doing combinations such as:

- HP potion + MP potion
- potion + skill
- potion + basic attack

HP and MP cooldown state are independent.

Stopping/resetting automation resets HP and MP adapter timing independently.

## Auto Potion Behavior

HP and MP are independently configurable.

Each supports:

- enabled / disabled
- PERCENT threshold mode
- ABSOLUTE threshold mode
- threshold value
- potion item ID
- cooldown

Threshold boundary is inclusive:

`current <= threshold`

Percent calculations must use long-safe arithmetic:

`current * 100 <= max * threshold`

A missing or rejected potion does not start adapter cooldown.

A successful native potion use starts only that potion adapter's cooldown.

Cooldown addition must saturate at `Long.MAX_VALUE` on overflow.

## Existing Production Components

Already compiling under Fast Dev authority:

- `AutoHuntConsumableController`
- `AutoHuntHpConsumableAdapter`
- `AutoHuntMpConsumableAdapter`
- `AutoHuntRuntimeSettings`

Current successful incremental builds include:

- 2-class build for controller + MP adapter
- 1-class build for RuntimeSettings
- 1-class build for HP adapter

These components may be renamed later if needed, but migration must not break existing behavior merely to improve naming.

## Service Separation

`AutoPotionService` owns:

- HP/MP configuration
- HP/MP adapters
- consume ordering
- independent reset
- operation even when Auto Hunt is disabled

`AutoHuntService` owns only hunt behavior:

- target
- movement
- active skill
- basic attack
- hunt lifecycle

Potion logic must not require Auto Hunt to be enabled.

## Client / Launcher Contract

Launcher UI is the primary control surface.

850 built-in auto-potion UI is reference/compatibility only.

Launcher sends configuration changes rather than repeatedly sending drink-potion commands.

Logical packet/config separation:

```text
AUTO_POTION_CONFIG
AUTO_HUNT_CONFIG
```

Auto Potion configuration contains independently:

HP:
- enabled
- mode
- threshold
- item ID

MP:
- enabled
- mode
- threshold
- item ID

The server remains authoritative for:

- current HP/MP
- inventory ownership
- potion type
- item consumption
- native potion effect
- cooldown
- blocked state

The launcher must not calculate healing/mana recovery or directly mutate player resources.

## Launcher Potion Selection

Final launcher UI should select potion candidates from player inventory rather than hard-code one global potion ID.

Server still validates the supplied item ID before use.

HP candidate must ultimately satisfy native HP potion type rules.

MP candidate must ultimately satisfy native MP potion type rule `aP()==27`.

## Required Runtime Modes

All must work independently:

```text
AutoPotion OFF + AutoHunt OFF
AutoPotion ON  + AutoHunt OFF
AutoPotion OFF + AutoHunt ON
AutoPotion ON  + AutoHunt ON
```

## Testing Strategy

Use TDD for each production step.

Minimum contracts:

1. AutoPotion works without AutoHunt.
2. HP is evaluated before MP.
3. HP CONSUMED prevents MP and hunt action in same tick.
4. MP CONSUMED prevents hunt action in same tick.
5. HP and MP cooldown states are independent.
6. Reset clears both adapter cooldown states.
7. Disabled HP does not affect MP.
8. Disabled MP does not affect HP.
9. Missing/rejected item does not consume tick as success.
10. Existing AutoHunt behavior remains unchanged when AutoPotion is disabled.

Normal development validation:

`./build850.ps1`

Expected changed-source-only incremental compile.

Full validation may use:

`./build850.ps1 -Full`

only at final regression stage.

## Non-Goals

This phase does not:

- redesign the full 850 built-in auto-potion UI
- make launcher perform healing logic
- create separate HP/MP worker threads
- rewrite native Potion formulas
- semantic-rename the entire 850 obfuscated method surface
- hard-code one universal HP or MP potion ID

## Migration Order

1. Lock AutoPotion service contract with RED tests.
2. Implement independent `AutoPotionService`.
3. Connect existing HP and MP adapters.
4. Add shared player automation tick orchestration.
5. Remove potion ownership from AutoHuntService.
6. Verify four runtime mode combinations.
7. Define server packet/config adapter.
8. Add launcher HP/MP configuration UI.
9. Add inventory potion selection.
10. Run final regression/full build.
