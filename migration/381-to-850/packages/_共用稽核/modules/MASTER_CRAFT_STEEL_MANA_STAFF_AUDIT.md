# Master Craft Family Audit: Steel Mana Staff

## Scope
381 MasterCraft:
- NPC 99200
- category: 進階武器製作 / 特殊武器製作
- output: item 127 鋼鐵瑪那魔杖

## 381 recipe
Output:
- 127 鋼鐵瑪那魔杖 x1

Materials:
- 126 瑪那魔杖 x1, enchant +8
- 131 力量魔法杖 x1, enchant +8
- 41246 魔法結晶體 x10000
- 40318 魔法寶石 x500
- 40408 金屬塊 x100
- 40308 金幣 x5000000

Success:
- 100%

Custom behavior:
- no inheritance
- no bonus item
- no HP/MP cost
- no class restriction
- no failure-return logic
- announcement only

## 850 compatibility
850 already contains:
- output weapon 127 鋼鐵瑪那魔杖
- 126 瑪那魔杖
- 131 力量魔法杖
- 41246 魔法結晶體
- 40318 魔法寶石
- 40408 金屬塊
- 40308 金幣

All item identities needed for the recipe exist natively in 850.

The donor output weapon itself also has no custom classname dependency.

## Important craft-id collision
850 `craft.id=127` is already occupied by a different recipe:
- 法利昂的霸氣
- output 21122

Therefore the donor MasterCraft action/id must NOT be reused as the target craft primary key.

Migration must allocate a new non-conflicting 850 craft ID.

## Enchant requirements
The donor recipe requires:
- item 126 at enchant +8
- item 131 at enchant +8

850 native `craft.material_enchant` supports these requirements directly.

## Classification
**L2 confirmed / implementation-ready design**

No core migration is required for the actual recipe.

Optional 381 global-success announcement can be omitted or handled later by a generic craft broadcast extension.

## Recommended package
```
craft-steel-mana-staff/
  install.sql
  rollback.sql
  DEPENDENCIES.md
  VALIDATION.md
```

Install should:
- allocate one free craft ID
- target existing output 127
- reference existing materials
- set material_enchant for 126/131 to 8
- avoid touching existing craft id 127

## Validation
PASS only if:
- new craft ID does not collide
- output remains item 127
- 126/131 require +8
- exact material counts match donor
- recipe is independently removable
