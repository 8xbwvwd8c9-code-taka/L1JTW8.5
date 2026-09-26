# L1JTW8.5 自動狩獵對話交接包 — 2026-09-26

## GOAL

繼續 `work/850-auto-hunting` 的 850 自動狩獵開發。不得另開自動狩獵或核心施工支線。

## AUTHORITIES

- Host/runtime authority: 850 repaired core
- Core donor: L381
- UI/control donor: L880C
- 850 類別 mapping authority: `class_source_mapping.csv`

## CURRENT BRANCH

- Repo: `8xbwvwd8c9-code-taka/L1JTW8.5`
- Branch: `work/850-auto-hunting`
- HP Auto Potion production wiring commit: `5306065444b791459f6f1420becba7fae88855fc`
- Homepage HP Auto Potion closure commit: `f164a456b233fa7ba59d81502a3f72e334381c32`

新對話第一件事仍是重新抓 branch HEAD；若更晚，先看新 commit 再施工。

## CLOSED

```text
LIFECYCLE=CLOSED
TARGET_SEARCH=CLOSED
BASIC_MOVE=CLOSED
BASIC_ATTACK=CLOSED
SINGLE_TARGET_ACTIVE_SKILL=CLOSED
AUTO_POTION=CLOSED
```

### HP Auto Potion closure

Production:

- `recovered-src-obf/auto/hunt/AutoHuntConsumableController.java`
- `recovered-src-obf/auto/hunt/AutoHunt850ConsumableAdapter.java`
- `recovered-src-obf/auto/hunt/AutoHuntRuntimeSettings.java`
- `recovered-src-obf/auto/hunt/AutoHuntService.java`

Regression:

- `tools/auto-hunt/AutoHuntConsumableControllerTest.java`
- `tools/auto-hunt/AutoHunt850ConsumableAdapterTest.java`
- `tools/auto-hunt/AutoHuntRuntimeSettingsPotionTest.java`
- `tools/auto-hunt/AutoHuntServicePotionWiringTest.java`

Behavior:

- threshold mode = PERCENT / ABSOLUTE
- boundary = `<=`
- HP = `pc.ea()`
- max HP = `pc.ew()`
- inventory = 850 `pc.j()`
- HP potion material type = 23..25
- 850 potion behavior = `aw.d.a(pc, item)`
- 850 item delay = `av.a.a(pc.aK(), item)`
- delay-effect timestamp is preserved
- auto-hunt never directly mutates HP
- independent potion cooldown
- action priority = Potion -> Skill -> Move -> Basic Attack
- successful potion consumes the current tick
- rejected / missing / cooldown / above-threshold potion falls through to normal combat flow
- Stop/reset clears potion timing

Fresh closure verification:

```text
AUTO_HUNT_CONSUMABLE_CONTROLLER_TEST=PASS
AUTO_HUNT_850_CONSUMABLE_ADAPTER_TEST=PASS
SETTINGS_GREEN=PASS
AUTO_HUNT_SERVICE_POTION_WIRING_TEST=PASS
```

TDD sequence:

- `242d7be9cb0b303696fceb7bb38ed286359cfdec` — controller cooldown RED
- `0d073e73b83dcd98846f0dd16f485b359855eb99` — controller cooldown GREEN
- `5f133ed5f68a272344cbee85ee43e7d4e0d38c10` — adapter RED
- `9b90c16625b070fca10d1cb112e67520e6fc2a05` — adapter GREEN
- `56c8abb7a3b77b5e54d52919b0098829bdb9fe45` — settings RED
- `ee5b2b87ea13902bb33153aa0069272c3177e085` — settings GREEN
- `d5a42dfa74af2f9cf8823c9f9c147f4e5deaf118` — service wiring RED
- `5306065444b791459f6f1420becba7fae88855fc` — service wiring GREEN

## STILL OPEN

```text
MP_POTION_POLICY=NOT_STARTED
880_UI_SETTINGS_TRANSPORT=UNVERIFIED
PICKUP=NOT_IN_SCOPE
RECYCLE=NOT_IN_SCOPE
DISSOLVE=NOT_IN_SCOPE
FULL_PROJECT_COMPILE=NOT_CLAIMED
```

MP potion 不得因 HP potion 完成而直接複製／宣告完成；先釐清 MP 使用策略、門檻與 item selection。

## IMPORTANT DONOR DISTINCTION

L381 `Atu_supply_Timer` 是 30 秒級的補貨／購買服務，不是 combat-time threshold potion controller。

不得混入：

- pickup
- recycle
- dissolve
- skill-healing
- supply purchasing

## 880 STATUS

880 UI / packet schema 尚未證實完整 runtime settings 欄位。`AutoHuntRuntimeSettings` 仍保持 transport-agnostic；不得自行捏造 880 欄位或預設值。

## NEXT EXECUTION ORDER

1. Recheck `work/850-auto-hunting` HEAD.
2. Decide MP potion policy separately from HP.
3. TDD MP threshold / selection / cooldown semantics if MP potion is approved next.
4. Only map 880 settings transport when real donor UI/packet evidence exists.
5. Keep pickup/recycle/dissolve/supply as independent modules.
6. Run actual project build before ever setting `FULL_PROJECT_COMPILE=PASS`.

## DO NOT

- Do not create another auto-hunt/core branch.
- Do not merge the whole completed core branch.
- Do not copy L381 timer topology literally.
- Do not directly mutate HP/MP for potion or skill effects.
- Do not invent 880 settings/packet fields.
- Do not mix Boss/elite affix rules into player auto-hunt.
- Do not mix pickup/recycle/dissolve/supply purchasing into combat potion.
- Do not claim full-project compile unless actually run.

## FINAL STATUS

```text
BRANCH=work/850-auto-hunting
LIFECYCLE=CLOSED
TARGET_SEARCH=CLOSED
BASIC_MOVE=CLOSED
BASIC_ATTACK=CLOSED
SINGLE_TARGET_ACTIVE_SKILL=CLOSED
AUTO_POTION=CLOSED
AUTO_POTION_THRESHOLD_CONTROLLER=IMPLEMENTED
AUTO_POTION_850_ITEM_ADAPTER=IMPLEMENTED
AUTO_POTION_SETTINGS=IMPLEMENTED
AUTO_POTION_SERVICE_WIRING=IMPLEMENTED
MP_POTION_POLICY=NOT_STARTED
FULL_PROJECT_COMPILE=NOT_CLAIMED
NEXT=MP potion policy or verified 880 UI/settings transport
```
