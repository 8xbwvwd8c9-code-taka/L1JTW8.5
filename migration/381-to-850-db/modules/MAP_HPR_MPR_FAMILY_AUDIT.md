# 381 -> 850 Map HPR/MPR Family Audit

## Scope
Tables:
- w_指定地圖回血魔
- w_hprmprrangemap
- w_地圖回血魔設置 (candidate/delta dataset; exact runtime table mapping not fully proven)

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

## Critical single-mapid data loss
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

Candidate retained-area losses:
```text
R1: lost 12178 (99.15%)
R2: lost 10995 (89.52%)
R3: lost 5554  (45.22%)
R4: lost 10567 (86.04%)
R5: lost 11178 (91.01%)
R6: lost 10938 (89.06%)
```

This is a structural donor defect for multiple ranges sharing one map id.

Preferred 850 structure:
```text
Map<Integer, List<Rectangle>>
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

Current `checkLoc` accepts no point.
Therefore:
```text
EFFECTIVE_AREA=0
ROW_UNREACHABLE=YES
```

Target import should reject or normalize invalid rectangles explicitly, not silently preserve dead geometry.

## Area arithmetic
Safe examples:
```text
1000x1000=1,000,000
50000x50000=2,500,000,000 > Integer.MAX_VALUE
```

Thus rectangle area should use long:
```java
long area=((long)width)*height;
```

## Underwater HPR/MPR asymmetry

Whole-map runtime:
- HPR path checks underwater protection and can return 0
- MPR path has no pc parameter and no equivalent underwater protection check

Range runtime:
- HPR path checks underwater protection
- MPR path checks location only

For configured 50/50 under a protective condition:
```text
HPR=0
MPR=50
```

Therefore:
```text
UNDERWATER_ASYMMETRY=PROVEN
```

This may be intentional or donor defect; target policy required.

## Whole-map duplicate ids
Whole-map loader also uses:
```text
Map.put(mapid,value)
```

Thus:
```text
N duplicate rows -> 1 retained -> N-1 lost
```

## Regen throughput
Pure arithmetic examples:

For 1-second tick:
```text
50  => 3000/min, 180000/hour
70  => 4200/min, 252000/hour
120 => 7200/min, 432000/hour
200 => 12000/min, 720000/hour
250 => 15000/min, 900000/hour
300 => 18000/min, 1080000/hour
```

For longer tick intervals divide by interval seconds proportionally.
These are throughput calculations only; actual donor tick cadence is not proven in this audit.

## Target HP/MP clamp safety
Safe target model:
```text
newHP=min(maxHP,currentHP+regen)
newMP=min(maxMP,currentMP+regen)
```

This is target safety policy, not proven donor behavior from these table classes alone.

## Classification
```text
DATA_LEVEL=L1
RUNTIME_STRUCTURE_LEVEL=L3
CLIENT_DEP=NO_CURRENT_PROOF
FINAL_LEVEL=L3
```

## Status
```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=MAP_HPR_MPR_FAMILY
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
850_NATIVE=NOT_PROVEN
CLIENT_DEP=NO_CURRENT_PROOF
LEVEL=L3
BLOCKERS=exact mapping of w_地圖回血魔設置 to donor runtime; multi-range owner structure; invalid rectangle policy; underwater HPR/MPR policy; tick cadence; map-id semantic mapping
```
