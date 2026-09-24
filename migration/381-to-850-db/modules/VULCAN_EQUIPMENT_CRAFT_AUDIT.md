# 381 -> 850 Vulcan Equipment Craft Audit

## Scope

381 module:
- `w_火神裝備製作`

Primary runtime:
- `com.add.system.L1BlendTable`
- `com.add.system.L1Blend`
- `com.lineage.data.npc.other.Npc_CraftDesk`

Separate legacy table:
- `w_道具火神製作`

The latter is NOT proven to be the same runtime path and is not bundled into this audit.

## Current content inventory

Current split SQL contains:

```text
ACTIVE_RECIPE_ROWS=138
```

Observed feature usage:

```text
chance < 100        = 56
chance-boost item   = 2
failure return item = 32
input_amount        = 0
all_in_once         = 0
bonus item          = 0
HP/MP consume       = 0
success/fail HTML   = 0
world broadcast     = 94
```

This is important: the donor engine supports many advanced behaviors, but the current content uses only a subset.

## Source DB authority

Current split artifact is INSERT-only.

Runtime fields include:
- npcid
- action
- new_item
- note
- new_item_counts
- rnd
- 顯示假機率
- 自訂機率道具
- 自訂機率道具名稱
- 自訂機率數量上限
- new_Enchantlvl_SW
- new_item_Enchantlvl
- new_item_Bless
- bonus_item
- bonus_item_count
- bonus_item_enchant
- checkLevel
- checkClass
- hpConsume
- mpConsume
- materials
- materials_count
- materials_enchants
- residue_item
- residue_count
- replacement_count
- input_amount
- all_in_once
- sucess_html
- fail_html
- Allmessage
- 公告

CREATE schema/key/default authority is not proven.

## Loader

`L1BlendTable.loadBlendTable()` reads:

```sql
SELECT * FROM w_火神裝備製作
```

Recipes are keyed by:

```text
npcid + action
```

It also builds a second map for NPC craft-list display.

## NPC entry/UI

`Npc_CraftDesk.talk(...)` reads the recipe names for the current NPC and renders:

```text
smithitem1
```

The craft detail runtime renders:

```text
ItemBlend
```

However the current recipe rows do not actively use per-recipe `sucess_html/fail_html`.

This means donor-specific HTML exists at the menu/detail layer, but the target is not forced to preserve that UI if recipes are converted to 850 native `craft/html_craft`.

Therefore:

```text
DONOR_HTML_DEP=YES
TARGET_CLIENT_EXTENSION_REQUIRED=NO_PROVEN
```

Do not classify the whole module as L4 solely because donor UI uses `smithitem1/ItemBlend`.

## Donor engine capability

`L1Blend` supports:
- level restriction
- class restriction
- HP/MP cost
- exact material enchant requirements
- success chance
- chance-boost item
- boost-item cap
- fixed/random output enchant
- output bless
- failure-return item
- replacement material
- quantity input
- batch/all-in-once mode
- bonus output item
- success/fail HTML
- global success announcement
- material-state inheritance fields in the runtime class

The current table does not use all of these.

## Current content profile

### Native/simple candidates

Most current rows structurally use:
- fixed output item/count
- material IDs/counts/enchant requirements
- normal success chance
- failure return on selected upgrade chains
- fixed output enchant
- optional announcement

These align closely with 850 native craft primitives.

### Extension-dependent rows

Current rows can still require target extensions for:
- world announcement
- chance-boost item cap semantics
- output blessing
- exact donor chance semantics where display text disagrees with actual `rnd`

Examples of source-content mismatch:

```text
configured rnd=40
display text="成功機率50%"
```

and:

```text
configured rnd=70
display text="成功機率80%"
```

The actual runtime uses `rnd`, not the display text.

Migration must preserve authoritative execution values unless data-owner correction is explicit.

## 850 native compatibility

Existing 850 migration mapping proves native support for:
- output item/count
- fixed output enchant
- materials/counts/enchants
- level constraints
- success chance
- fail item/count
- some chance-item behavior
- some quantity behavior
- legacy NPC/action crafting via `html_craft`

850 native tables:
- `craft`
- `craft_exchange`
- `html_craft`

Therefore most current `w_火神裝備製作` rows should NOT require donor `L1Blend` to be ported wholesale.

## Feature mapping

### Direct / L2 candidates

```text
new_item
new_item_counts
rnd
materials
materials_count
materials_enchants
checkLevel
residue_item
residue_count
fixed new_item_Enchantlvl
npcid/action through html_craft or native menu binding
```

### Small generic extension candidates

```text
公告 / Allmessage
自訂機率道具數量上限
checkClass
output bless
```

### Currently unused donor features

Current 138 rows do not use:
- HP consume
- MP consume
- bonus item
- input amount
- all-in-once
- per-recipe success HTML
- per-recipe fail HTML

These should not be implemented just because donor engine supports them.

## Critical donor hazards

### 1. Do not port unused engine surface

The donor runtime is much broader than current data.

Porting the full class would introduce:
- unnecessary state-transfer logic
- custom NPC UI coupling
- legacy hardcoded special cases
- more rollback/test surface

Target should migrate only features proven by active rows.

### 2. Hardcoded replacement item

`CheckForReplacement(...)` hardcodes:

```text
replacement item = 80322
```

rather than deriving a replacement item ID from the recipe.

Current rows show `replacement_count=0`, so this path is inactive for current content.

Do not port it unless future active rows prove need.

### 3. Hardcoded special output IDs

The donor craft runtime contains special-case checks for specific outputs such as:
- `56147`
- `56148`

These are engine contamination from unrelated content.

Target native conversion must not inherit these special cases globally.

### 4. Chance display text is not authoritative

Execution uses:

```text
rnd
```

while `顯示假機率` can intentionally or accidentally differ.

Do not derive success chance from display text.

### 5. Chance boost is additive

Runtime calculates:

```text
TotalChance = rnd + heldBoostItemCount
```

capped by `自訂機率數量上限`.

This exact behavior must be verified against 850 `add_chance_itemid` semantics before direct mapping.

### 6. Output blessing needs explicit target handling

850 craft mapping does not prove a direct generic output-bless column equivalent.

Rows using `new_item_Bless` require either:
- target native support proven elsewhere, or
- a small generic extension/post-create mutation.

### 7. Announcement is cross-cutting

94 rows use `公告=1`.

A single generic craft-success announcement hook is preferable to per-recipe custom runtime.

### 8. Item identity is the main content dependency

Many outputs/materials are custom 381 content.

Each content family still needs:
- semantic item mapping
- item definition migration
- client invgfx/name/resource validation
- custom item runtime audit when classname/effect tables are involved

This was already proven in prior MasterCraft family audits and applies here as well.

## Relationship to prior MasterCraft audit

This module is a DIFFERENT donor table/runtime from `x_大師製作系統`, but its migration strategy is similar:

```text
prefer 850 native craft
+ only reusable small extensions
+ split by content family
```

Do not merge the source tables operationally without preserving recipe ownership/provenance.

## Recommended migration strategy

### Phase A: normalize rows

For every recipe derive a normalized model:

```text
source_table
npc/action
output
success chance
materials
material enchants
failure return
boost item/cap
output enchant/bless
announcement
dependencies
```

### Phase B: classify recipe

```text
Tier A = pure 850 native craft/html_craft
Tier B = native + small generic extension
Tier C = content/runtime dependency outside crafting
```

### Phase C: package by content family

Examples:

```text
vulcan-boss-badges/
vulcan-map-badges/
vulcan-dolls/
vulcan-legacy-weapons/
vulcan-shirts/
vulcan-event-items/
```

Each package must be independently installable/removable.

## Difficulty

```text
ENGINE_PORT=NOT_RECOMMENDED
CURRENT_MODULE_LEVEL=L3
MOST_RECIPE_ROWS=L2_CANDIDATE
```

Why module-level L3:
- 850 native framework covers most data
- but current content still uses cross-cutting announcement
- chance-boost cap semantics need verification/extension
- output bless mapping is not fully proven
- content dependencies vary by family

This is not L4 because donor-specific HTML can be replaced by 850 native craft UI; no custom client protocol is required.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_火神裝備製作
LEVEL=L3
ENGINE=L1BlendTable+L1Blend
ACTIVE_ROWS=138
CHANCE_LT_100=56
FAIL_RETURN_ROWS=32
BOOST_ITEM_ROWS=2
BROADCAST_ROWS=94
HP_MP_ROWS=0
BONUS_ITEM_ROWS=0
INPUT_AMOUNT_ROWS=0
ALL_IN_ONCE_ROWS=0
SUCCESS_FAIL_HTML_ROWS=0
850_NATIVE_CRAFT=YES
WHOLESALE_ENGINE_PORT=NO
CLIENT_PROTOCOL_DEP=NO
TARGET_CLIENT_EXTENSION_REQUIRED=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=schema,item semantic mapping,content-family dependencies,output bless mapping,boost-cap semantics,announcement hook
```
