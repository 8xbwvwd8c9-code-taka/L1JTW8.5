# Master Craft -> 850 Native Craft Mapping

Target authority: `completed/l1jtw85-core-fixes`

## 850 native tables

### craft
Supports:
- craft id / note / nameid
- output item IDs, counts, enchants
- material IDs, counts, enchants, bless
- min/max level
- min/max lawful / karma
- max_count
- change (success chance)
- add_chance_itemid
- fail_itemid / fail_item_count
- perfect_chance

### craft_exchange
Supports material-specific alternative/exchange outputs:
- material_itemid
- exchange_itemid
- exchange_count
- exchange_enchant
- exchange_bless

### html_craft
Supports NPC/action based legacy crafting:
- action
- npcid
- output IDs/counts
- materials/counts
- success/fail HTML
- quantity input

## 381 x_大師製作系統 field mapping

| 381 field | 850 target | Mapping |
|---|---|---|
| npcid | html_craft.npcid OR native craft menu binding | PARTIAL |
| action | html_craft.action OR native menu selection | PARTIAL |
| new_item | craft.craft_itemid | DIRECT |
| 製作類型 | none | DISPLAY/METADATA only |
| 分類1/2/3 | native craft category/menu metadata | CORE/UI extension likely |
| 分類1/2/3排序 | category ordering | CORE/UI extension likely |
| 項目排序 | menu ordering | CORE/UI extension likely |
| 是否顯示 | no direct craft column | FILTER/extension |
| 顯示名稱 | note/craft_nameid/client name | PARTIAL |
| 成品數量 | craft.craft_count | DIRECT |
| 成功機率 | craft.change | DIRECT candidate |
| 顯示機率文字 | none | UI-only |
| 加成道具ID | craft.add_chance_itemid | PARTIAL/DIRECT candidate |
| 加成道具名稱 | none | UI-only |
| 加成道具上限 | none | CORE extension |
| 是否繼承材料狀態 | none | CORE extension |
| 繼承材料序號 | none | CORE extension |
| 是否繼承祝福狀態 | none | CORE extension |
| 是否繼承附加素質 | none | CORE extension |
| 成品強化模式 | craft.craft_enchant only for fixed output enchant | PARTIAL |
| 成品強化值 | craft.craft_enchant | DIRECT only for fixed enchant |
| 成品祝福狀態 | no direct output bless field in craft | CORE extension / alternate handling |
| 額外獎勵道具 | no generic bonus-output array | CORE extension |
| 額外獎勵數量 | no generic bonus-output array | CORE extension |
| 額外獎勵強化值 | no generic bonus-output array | CORE extension |
| 等級限制 | craft.min_level/max_level | DIRECT if semantics match |
| 職業限制 | none | CORE extension |
| 消耗HP | none | CORE extension |
| 消耗MP | none | CORE extension |
| 材料道具清單 | craft.material | DIRECT |
| 材料數量清單 | craft.material_count | DIRECT |
| 材料強化清單 | craft.material_enchant | DIRECT |
| 失敗返還道具 | craft.fail_itemid | DIRECT |
| 失敗返還數量 | craft.fail_item_count | DIRECT |
| 替代材料數量 | craft_exchange can cover some alternatives | PARTIAL |
| 是否輸入數量 | html_craft.isInputable / native max_count | PARTIAL |
| 是否一次結算 | none | CORE behavior extension |
| 成功對話檔 | html_craft.success_html | DIRECT on legacy NPC path |
| 失敗對話檔 | html_craft.fail_html | DIRECT on legacy NPC path |
| 全服公告內容 | none | CORE extension |
| 是否公告 | none | CORE extension |

## Result

### Tier A: direct/native conversion
A large subset can be represented without porting MasterCraft:
- output item/count
- fixed output enchant
- materials/count/enchant
- material bless requirements
- level bounds
- success chance
- fail-return item/count
- quantity-related behavior in some cases
- some alternative-material/output behavior via craft_exchange

These recipes should be converted into isolated SQL targeting 850 native `craft/craft_exchange`.

### Tier B: native + small extension
Needs limited additions to 850 crafting core:
- class restriction
- visibility/category/order metadata
- boost-item cap/semantics
- HP/MP cost
- batch settlement
- announcement

### Tier C: advanced state-transfer behavior
Requires dedicated implementation:
- inherit material state
- inherit blessing
- inherit additional/custom attributes
- output blessing
- complex extra reward item(s)
- semantics where output is mutated from a selected material instance rather than newly created

## Migration policy
1. Do NOT port 381 MasterCraft wholesale first.
2. Convert Tier A recipes to 850 native craft SQL.
3. Add only small generic extensions needed by multiple Tier B recipes.
4. Keep Tier C recipes disabled/unmigrated until an explicit reusable extension exists.
5. Every converted recipe set must remain independently importable and removable.
