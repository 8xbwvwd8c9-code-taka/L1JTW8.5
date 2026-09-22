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


## Runtime call path proven

381 dispatch path:

```
C_NPCAction
  -> if npc.ACTION handled: return
  -> Npc_ins.forNpcQuest(...)
  -> Npc_Combind.forNpcQuest(cmd, pc, npc, npcid, objid)
```

`Npc_Combind` lazily loads `w_天M合成系統` on first use.

Rule key:
- npcid
- action

Therefore actions `do00..do17` / `po00..po17` are not hard-coded in C_NPCAction.
They are table-driven through `Npc_Combind`.

## Doll synthesis runtime proven

`Npc_Combind.forNpcQuest()`:
1. finds matching npcid+action rule
2. iterates configured input item-id pool
3. collects qualifying inventory item IDs until requested count is reached
4. deletes active dolls before consumption
5. consumes the selected items
6. randomly chooses one output ID from configured output pool
7. performs success roll
8. on success:
   - create/store result item
   - success message
   - optional world broadcast
   - reset pity quest counter to 0
9. on failure:
   - increment pity quest counter
   - when threshold reached, grant result and reset counter
   - optionally return one random consumed item when 失敗是否退還=1

This confirms the same-tier arbitrary-input semantics and rules out native fixed-material `craft` as a full replacement.

## Pity persistence proven

`保底紀錄編號` is used directly as an L1PcQuest quest ID.

Examples:
- 20001..20036

Runtime:
- `pc.getQuest().get_step(rulePityId)`
- `pc.getQuest().set_step(rulePityId, value)`

`L1PcQuest.set_step()` persists through:
- `CharacterQuestReading.storeQuest/updateQuest`

Therefore pity is persisted in the existing character quest persistence mechanism.

Migration options:
1. preserve quest-ID based pity state, reserving an explicit quest-ID range, or
2. preferably abstract pity persistence behind a module-owned table to avoid collision with unrelated quests.

Do not silently reuse 20001..20036 in 850 until quest-ID collision audit passes.

## Donor bug: probability is off by one

381 success check:

```
_random.nextInt(100) + 1 < configuredChance
```

Because roll range is 1..100 and comparison is strict `<`:
- configured 50 => actual 49%
- configured 70 => actual 69%
- configured 90 => actual 89%
- configured 100 => actual 99%

Migration rule:
- do NOT preserve this accidental off-by-one behavior unless explicitly required.
- target semantics should normally use `roll <= configuredChance` or an equivalent exact-percent implementation.

## Donor bug: transformation-card rows break generic loader

`Npc_Combind.getData()` always executes:

```
getArray(rset.getString("獲取合成編號"), ",", 1)
```

Transformation-card rows have:
- `獲取合成編號 = NULL`

`getArray()` constructs a `StringTokenizer` directly from the supplied value.
A NULL value therefore raises an exception.

The outer `getData()` catches Exception silently and stops loading further data.

Practical consequence:
- doll rows before the first NULL transformation-card row can load
- transformation-card rows cannot be safely represented by the current generic loader
- remaining rows after the first failing row may never load

Therefore the current donor implementation itself is defective for the poly-card half.

Migration rule:
- do NOT port `Npc_Combind` verbatim
- split doll and poly-card adapters explicitly
- validate nullable input/output pools
- fail individual bad rules, not the entire table load

## Revised module architecture

```
tianm-synthesis-core/
  rule model
  exact-percent RNG
  choose-N-from-pool
  output selection
  pity persistence abstraction
  failure-return policy
  message/broadcast hooks
  validation/error isolation

tianm-doll-synthesis/
  item pool adapter
  active-doll cleanup
  reward item boxes
  NPC 93064

tianm-polycard-synthesis/
  transformation-card ownership adapter
  card tier pool
  reward-card selection
  NPC 93068
```

## Updated classification

Doll synthesis:
- **L3 confirmed**
- server-side only based on current evidence

Transformation-card synthesis:
- **L3 confirmed**
- depends on transformation-card subsystem
- current donor implementation is broken/incomplete
- L4 remains NOT PROVEN

## Migration safety
Do not preserve donor defects:
- strict-`<` probability bug
- NULL loader crash/silent abort
- giant if/else pity increment chain

Implement pity increment as bounded arithmetic with explicit threshold/reset.
