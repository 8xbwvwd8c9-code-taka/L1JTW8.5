# 850 UI Resource Anchors — 2026-09-23

Authority: 850 client resources extracted read-only with LineageAIResourceToolkit. These are static resource facts only; no runtime map is accepted from this document alone.

## HP / MP

`MainCharInfoUI.xml` does **not** expose HP/MP controls. It contains level, adena, AC, MR, alignment, SP, carry, food and shout-time controls.

High-value HP/MP UI anchors are instead in status resources:

- `EquipNStatusUI.xml`
  - `Control Name="HPGauge"`
  - `Control Name="MPGauge"`
  - gauge PNGs `10899` / `10900`
  - HP/MP text labels are also present in the status window.
- `ReNStatusUIEx-c.xml`
  - `Gauge Name="HpGauge_Image"`
  - `Gauge Name="MpGauge_Image"`
- `ReNStatusWinUI-c.xml`
  - `Gauge Name="HpGauge_Image"`
  - `Gauge Name="MpGauge_Image"`

Implication: future static/runtime correlation should prioritize status-window handlers and these exact control names instead of `MainCharInfoUI`.

## Inventory

`ReInventory.xml` provides the strongest client-side inventory anchors found so far:

- root: `Inventory`
- window: `InvWin`
- grid: `InventoryItemGrid`
- dimensions: `Row="6" Column="4"` (24 visible cells)
- unit: `34 x 34`
- count label: `ItemCountLabel`
- scrolling handler: `InventoryScroll`
- tabs: `TabAll`, `TabEquipables`, `TabQuest`, `TabCustom`
- delete control: `InventoryDeleteButton`
- delete handler: `MouseEvent="DeleteItem"`
- promote/seal controls are also present.

This is materially stronger than generic ItemId-memory hits. A formal inventory mapping should correlate candidate structures with the inventory UI/update path or with object-id/count records, not merely with catalog-valid ItemIds.

## Quick slots

`WidgetSlot.xml` and `WidgetSubSlot.xml` expose repeated `ItemIcon` controls with visible item counts. These are useful negative/alternate anchors because item IDs observed in these UI caches must not be confused with the authoritative inventory container.

## Pet HP

`PetSummonUI.xml` contains ten pet buttons and, for every visible pet cell:

- `HP_Image`
- `HP_Blank_Image`
- `Pet_Image`

Handlers include:

- `Action_PetWin`
- `Click_PetWin`

This confirms a dedicated owner-visible pet HP UI path exists in the 850 client.

## Summon

`SummonUI.xml` exposes:

- `SummonButton`
- `SummonLevelButton`
- `QuickSummonButton`
- level/tag metadata

This is a useful client UI anchor but does not by itself prove the runtime active-summon HP/state structure.

## Buff / skill

Useful resource anchors include:

- `RankBuffMatching.xml`
- `effectlist.xml`
- `effectlist2.xml`
- `effectlist2-c.xml`
- `SpellList.xml`
- `SpellUI.xml`
- `passiveSpells.xml`

`RankBuffMatching.xml` directly maps ranking-state ranges to icons/tooltips, but is not a general active-buff list.

## Magic doll

`MagicDollDesc.xml` is a state/description resource keyed by doll sprite and conditions such as zone, hunting and motion. It helps identify doll UI/state code, but it is not proof of the active doll runtime object.

## Runtime policy

Use these anchors in this order:

1. resource/control/event string static search in authoritative `Lin.bin2`;
2. native xrefs/functions when present;
3. read-only runtime correlation while the relevant UI/action changes;
4. restart / multi-PID stability;
5. semantic mapping gate.

Do not enable item deletion, dissolve, item use or skill use from resource-name evidence alone.
