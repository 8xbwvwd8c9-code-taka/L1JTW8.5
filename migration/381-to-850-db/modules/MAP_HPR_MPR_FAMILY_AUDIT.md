# 381 -> 850 Map HPR/MPR Family Audit

## Migration authority
- 850 core/runtime/data model is authoritative.
- 381 is donor/reference only.
- Difficulty is measured by capability missing from 850, not by donor framework size.

## Scope
Tables:
- `w_指定地圖回血魔`
- `w_hprmprrangemap`
- `w_地圖回血魔設置` (candidate/delta dataset; exact runtime table mapping not fully proven)

## Donor runtimes
- whole map: `MapHprMprTable`
- range map: `MapHprMprRangeTable`

Both use:
```text
Map<Integer, one record>
```

Therefore duplicate map ids use last-write-wins semantics.

## Whole-map model
Examples:
```text
map15 => 300/300
map29 => 300/300
map52 => 300/300
map64 => 300/300
map260 => 300/300
map300 => 300/300
map350 => 50/50
map800 => 200/200
map32767 => 250/250
```

Many hotel / clan-house maps also use 50/50.

## Range geometry
Known visible map4 candidate rectangles:

```text
R1 X=33443..33446 Y=32762..32787 => area 104, 200/200
R2 X=33407..33445 Y=32796..32828 => area 1287, 200/200
R3 X=32581..32638 Y=32709..32824 => area 6728, 0/0
R4 X=33587..33621 Y=33222..33270 => area 1715, 0/0
R5 X=33066..33088 Y=33374..33421 => area 1104, 120/60
R6 X=32606..32633 Y=33157..33204 => area 1344, 0/0
```

All boundaries are inclusive:
```text
minX <= X <= maxX
minY <= Y <= maxY
```

Pairwise geometry proof:
```text
ALL_MAP4_PAIRWISE_OVERLAPS=0
MAP4_UNION_AREA=12282
SUM_INDIVIDUAL_AREAS=12282
```

So there are no overlapping-value conflicts among the six visible map4 rectangles.

## Critical donor defect: single-mapid data loss
Range runtime stores:
```java
_maphprmprList.put(mapid, map);
```

For six map4 rows:
```text
MAX_RETAINED=1
LOST_ROWS=5
FINAL_ROW=LAST_ROW_LOADED
```

If load order is R1..R6:
```text
final=R6
value=0/0
retained_area=1344
lost_union_area=10938
lost_percent=89.06%
```

If reverse R6..R1:
```text
final=R1
value=200/200
retained_area=104
lost_union_area=12178
lost_percent=99.15%
```

This is a donor defect and MUST NOT be reproduced in 850.

Preferred 850 structure:
```text
Map<Integer, List<RectangleRule>>
```

## map800 invalid rectangle
Candidate row:
```text
X=32801..32833
Y=32926..32894
```

Here:
```text
minY > maxY
```

Current donor `checkLoc` accepts no point.
Therefore:
```text
EFFECTIVE_AREA=0
ROW_UNREACHABLE=YES
```

Target import should reject or explicitly normalize invalid rectangles.

## Area arithmetic
Use `long` for area arithmetic:
```java
long area=((long)width)*height;
```

because `50000*50000=2,500,000,000 > Integer.MAX_VALUE`.

## Underwater HPR/MPR asymmetry
Whole-map and range donor runtimes can suppress HPR under underwater protection while still applying MPR.
For a configured 50/50 rule this can become:
```text
HPR=0
MPR=50
```

Therefore:
```text
UNDERWATER_ASYMMETRY=PROVEN
```

Target policy must decide whether this donor asymmetry is intentional.

## 850 native comparison
850 repaired core already contains native regeneration lifecycle:
- `recovery/normalized-src-vf/l1r/bc/HpRegenerationTimer.java`
- `recovery/normalized-src-vf/l1r/bc/MpRegenerationTimer.java`
- `recovery/normalized-src-vf/l1r/ap/L1PcInstance.java`

`L1PcInstance` owns and schedules HP/MP regeneration timers. The native regeneration calculations already include map/coordinate/location-dependent bonus logic.

Therefore the 381 behavior does **not** require a new persistent owner, a new scheduler, or a parallel regeneration framework. The 850-first target is:

```text
381 rule data
  -> 850 DB loader / validated rule model
  -> mapId -> List<whole-map/range rules>
  -> small lookup adapter inside existing HpRegenerationTimer / MpRegenerationTimer calculation
```

Do not port `MapHprMprTable` / `MapHprMprRangeTable` as parallel lifecycle owners.

## Client dependency
```text
CLIENT_DEP=NO
```
No dedicated client UI/protocol/resource dependency is required for server-side HPR/MPR calculation.

## Classification
```text
DATA_LEVEL=L1
SERVER_LEVEL=L2
CLIENT_DEP=NO
FINAL_LEVEL=L2
```

L2 reason:
- 850 native regeneration lifecycle already exists.
- Required work is validated DB conversion plus a small native regen lookup adapter.
- No new persistent state owner/session/scheduler is required.

## Status
```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=MAP_HPR_MPR_FAMILY
TARGET_POLICY=850_NATIVE_FIRST
WHOLE_MAP_RUNTIME=MapHprMprTable
RANGE_RUNTIME=MapHprMprRangeTable
MAP4_VISIBLE_RANGES=6
MAP4_UNION_AREA=12282
MAP4_PAIRWISE_OVERLAP=NONE
MAP4_HASHMAP_LOSS=5_OF_6_ROWS
LOAD_ORDER_DEPENDENT=YES
MAP800_INVALID_RECTANGLE=YES
AREA_INT_OVERFLOW=PROVEN_POSSIBLE
UNDERWATER_ASYMMETRY=HPR_0_MPR_NONZERO
DUPLICATE_MAPID_LAST_WRITE_WINS=YES
850_NATIVE_REGEN=PROVEN
850_TARGET_STRUCTURE=mapId_to_List_of_rules
CLIENT_DEP=NO
LEVEL=L2
BLOCKERS=exact mapping of w_地圖回血魔設置 to donor runtime; invalid rectangle policy; underwater HPR/MPR policy; authoritative map-id/data mapping
PRODUCTION_PORT=NO
```
