# 381 -> 850 Module Audit: 天M合成系統

## Source
DB:
- `w_天m合成系統`

NPCs:
- 93064 娃娃合成
- 93068 變身合成

Spawn evidence:
- 93064 at 32819,32952 map 800
- 93068 at 33514,32714 map 725

## DB model
Fields:
- npcid
- note
- action
- 合成需求編號
- 物品合成數量
- 機率
- 獲取合成編號
- 物品不足Msg
- 失敗Msg
- 成功Msg
- 世界廣播
- 失敗是否退還
- 保底紀錄編號
- 保底次數

This is not a normal fixed-recipe craft table.

It models:
1. same-tier input pool
2. consume any N qualifying inputs
3. probabilistic tier-up
4. optional failure return
5. pity/guarantee counter
6. world broadcast
7. two content backends:
   - doll item pools
   - transformation-card/collection state

## Doll synthesis

### White -> Green
Input pool:
- 55002,55000,55011,55012,55006,80339,55005,55007
Options:
- 2 items / 50%
- 3 items / 70%
- 4 items / 90%
Output:
- 2402437 綠色娃娃禮盒

### Green -> Blue
Input pool:
- 55001,55010,55013,80208,55029,80341
Output:
- 2402438 藍色娃娃禮盒

### Blue -> Red
Input pool:
- 55046,80336,80337,55073,80338,80340
Output:
- 2402439 紅色娃娃禮盒

### Red -> Purple
Input pool:
- 55004,55047,80343,80342
Output:
- 2402440 紫色娃娃禮盒

### Purple -> Gold
Input pool:
- 80344,80345
Output:
- 2402441 金色娃娃禮盒

### Gold -> Special
Input pool:
- 92524,92525,92523,92522
Output:
- 2402442 特殊娃娃

Output item definitions are ordinary treasure-box items in 381.
The synthesis semantics themselves are the custom part.

## Transformation-card synthesis

NPC 93068 actions:
- po00..po17

Tiers:
- white -> green
- green -> blue
- blue -> red
- red -> purple
- purple -> gold
- gold -> unique

Unlike doll synthesis:
- 合成需求編號 is 0
- 獲取合成編號 is NULL
- 機率 is 0 in DB rows

Therefore transformation-card synthesis does NOT behave like a simple item-input/output recipe.
Its actual input/output pools and probability behavior must come from the transformation-card subsystem/runtime state.

This directly ties the module to the existing 381 card/collection family:
- ACardTable
- CardSetTable
- CardBook / CardBookCmd
- quest-state based card ownership

Do not model these rows as ordinary 850 craft recipes.

## Why 850 native craft is insufficient

850 `craft` handles:
- fixed material lists
- fixed output
- probability
- failure item
- exchange alternatives

But this module additionally requires:
- choose any N items from a configured same-tier pool
- dynamically select eligible inventory/card-state members
- consume arbitrary qualifying members
- pity counter per action/rule
- possible failure-return behavior
- card collection state rather than only item inventory
- tier-based random reward/result selection

These are runtime semantics, not just SQL mapping.

## Pity system

Rules use IDs:
- 20001..20036
with thresholds:
- commonly 50 / 30 / 20

No dedicated split DB table for pity records was found.
The only obvious generic persistent candidate is `character_quests`, but the exact storage path is **NOT YET PROVEN**.

Do not assume quest-state persistence until the runtime hook is found.

## Related systems checked

`L1BlendTable` / `w_火神裝備製作` is a different crafting subsystem.
It supports fixed materials and special crafting features, but does not implement this TianM same-tier synthesis model.

Therefore do not merge `w_天m合成系統` into the FireSmith/L1Blend migration.

## Difficulty

### Doll synthesis
**L3 confirmed**
Reason:
- custom same-tier pool selection
- probabilistic synthesis
- pity
- failure-return semantics
- reward box chain

### Transformation-card synthesis
**L3 confirmed, possible L4 only if client-specific UI/packet dependency is later proven**
Reason:
- depends on transformation-card ownership/collection state
- DB row itself has no item pool/output/probability data
- needs card subsystem integration

No current evidence requires protocol/client binary changes, so do NOT classify L4 yet.

## Recommended split

```
tianm-synthesis-core/
  rule loader
  eligible-pool selection
  consume-N logic
  chance logic
  pity persistence abstraction
  failure return
  world broadcast
  generic result interface

tianm-doll-synthesis/
  doll tier pools
  reward boxes
  NPC 93064/menu/actions

tianm-polycard-synthesis/
  card tier pools
  ACard/card ownership adapter
  NPC 93068/menu/actions
```

The doll and polycard content modules may depend on the shared synthesis core, but must not depend on each other.

## Migration recommendation
Do not place this in the first L2 migration batch.

Priority:
1. finish exact runtime trace for pity persistence
2. trace NPC action dispatcher for do00..do17 / po00..po17
3. trace doll reward box contents
4. trace transformation-card tier selection source
5. then design a reusable 850 synthesis core

## Status
**MODULE = L3**
**NATIVE_850_CRAFT_REPLACEMENT = NO**
**CLIENT_L4_DEPENDENCY = NOT_PROVEN**
**IMPLEMENT_NOW = NO**
