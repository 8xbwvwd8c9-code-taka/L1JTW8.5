# 381 -> 850 Negative Magic Probability Audit

## Scope
Table:
- w_負面魔法機率

Current visible rows:
- 釋放元素
- 弱化屬性
- 魔力奪取
- 起死回生

Split INSERT does not include `skill_id`, while donor loader expects it.
Therefore:
```text
SKILL_ID_MAPPING=NOT_PROVEN
```

## Donor runtime
Loader:
- com.lineage.server.datatables.SkillsProbabilityTable

Runtime consumer:
- com.lineage.server.model.L1MagicPc.calcProbability(...)

The table writes `type1..type28` directly into `L1Skills` objects used by the probability calculation path.

## Current visible row semantics

### 釋放元素 / 弱化屬性
Configured:
```text
type1=20
type2=15
type3=10
type4..28=0
```

Exact extension-layer piecewise result:
```text
A>D  => P=20
A=D  => P=15
A<D  => P=10
```

BASE is overwritten in all three relative-level domains.

### 魔力奪取
All visible type1..28 are zero.

Therefore this extension layer leaves:
```text
P=BASE
```

### 起死回生
Only:
```text
type6=10
```

Donor expression:
```text
P += type6 * INT / 10
```

With type6=10:
```text
P += 10 * INT / 10
P += INT
```

Thus:
```text
P=BASE+INT
```
before later class-specific post-processing.

## Java integer semantics

Java integer division truncates toward zero.

Examples:
```text
-31/10=-3
-30/10=-3
-29/10=-2
-11/10=-1
-10/10=-1
-9/10=0
-1/10=0
9/10=0
10/10=1
31/10=3
```

This behavior is essential for:
- type5
- type14..18
- type24..28
- Elf final halving

Do not replace with mathematical floor division.

## type5 level-step asymmetry

```text
P += type5 * (DIFF / 10)
```

Java truncation produces:
```text
DIFF -9..9      => 0
DIFF -10..-19   => -1
DIFF 10..19     => +1
DIFF -20..-29   => -2
DIFF 20..29     => +2
```

Important edge:
```text
-9/10=0
-10/10=-1
-11/10=-1
```

## type6 intermediate overflow

Donor evaluates:
```text
type6 * INT / 10
```
with int multiplication first.

A tested overflowing case:
```text
type6=10000
INT=1000000
product=10000000000 > Integer.MAX_VALUE
```

Safe target arithmetic:
```java
long delta = ((long) type6 * intStat) / 10L;
```

## type12 / type13 cast behavior

Donor uses:
```text
(int)(T + (AMR - T) * 0.02)
(int)(T + (AMR - T) * 0.04)
```

These use floating-point arithmetic followed by Java truncation toward zero.

Therefore:
- repeated integer-output ranges exist
- AMR<T can produce results below T
- exact boundary behavior depends on double representation and cast semantics

850 should preserve this intentionally if semantic compatibility is required.

## type14..18 threshold subtraction

These fields use a guard:
```text
TMR >= typeN
```
but then subtract integer division of the configured threshold itself.

Minimum configured values producing at least 1 subtraction:
```text
type14 / 7  => 7
type15 / 9  => 9
type16 / 13 => 13
type17 / 15 => 15
type18 / 20 => 20
```

Smaller nonzero configured values can satisfy the guard while subtracting zero.

## Override ordering

### type20
```text
if DIFF > type20:
    P=70
```

Strict `>`.
Previous P is destroyed.

### type21
```text
if P >= type21:
    P=type21
```

Inclusive cap.
Runs after type20.

### type22
```text
if PC target and TMR >= type22:
    P=5
```

Full override.
Runs after type20/type21.

Therefore runtime order is semantically significant:
```text
additive/penalty stages
-> type20 override
-> type21 cap
-> type22 override
-> type24..28 post-adjustments
-> class post-processing
```

## Critical type24..28 sign-reversal behavior

Form:
```text
P -= (TMR - typeN) / divisor
```

There is no `TMR >= typeN` guard.

If:
```text
TMR < typeN
```
then numerator is negative.

With Java truncation:
```text
P -= negative_value
```
can INCREASE P.

Exact increase region:
```text
TMR <= typeN - divisor
```

For negative difference with magnitude below divisor:
```text
-(divisor-1)..-1
```
integer division becomes zero, so P is unchanged.

Thus:
```text
LOW_MR_CAN_INCREASE_PROBABILITY=YES
```

This is a proven donor semantic and likely bug/risk if these fields are populated.

## Wizard post-processing

```text
if P>70:
    P=70
```

Examples:
```text
70 -> 70
71 -> 70
100 -> 70
-1 -> -1
```

No lower-bound clamp is implied.

## Elf post-processing

```text
if P>70:
    P=55
else:
    P=P/2
```

Using Java integer division:
```text
70 -> 35
71 -> 55
-1 -> 0
-3 -> -1
-9 -> -4
```

Critical discontinuity:
```text
P=70 -> 35
P=71 -> 55
```

## Probability domain

Without a separately proven final clamp, the supplied formula can produce:
```text
P<0
P=0
0<P<100
P=100
P>100
```

Examples:
- negative: P=5 then type19 penalty => -10
- above 100: BASE=0, type6=10, INT=200 => 200

Wizard/Elf post-processing may later reduce some high values, but no global 0..100 clamp is proven here.

## Safe target evaluator

850 should preserve donor ordering while widening arithmetic:

```text
long p = BASE;
long diff = (long)A - D;
```

Use widened multiplication for all potentially large terms.

For type5, preserve Java truncation-toward-zero semantics.

For type12/type13, preserve donor floating-point + cast behavior only if exact compatibility is required.

Do not silently clamp to 0..100 unless an explicit target policy decides to.

## Migration implications

850 migration must separate:
```text
ARITHMETIC_SAFETY
vs
GAMEPLAY_POLICY
```

Arithmetic safety:
- widen intermediate multiplication
- preserve operation ordering
- preserve Java truncation semantics where compatibility required
- validate type1..type28 ranges
- reject malformed source rows

Gameplay policy still required for:
- whether negative configured values are allowed
- whether final probability must clamp to 0..100
- whether type24..28 low-MR probability increase is intentional
- whether Elf 70/71 discontinuity is intentional
- type23 semantics, currently unused/unproven

## Classification

```text
DATA_LEVEL=L1
SERVER_RUNTIME_LEVEL=L3
CLIENT_DEP=NO_CURRENT_PROOF
FINAL_LEVEL=L3
```

Reason:
- modifies core spell probability calculation
- operation ordering and class post-processing are runtime semantics
- no custom client resource dependency is proven

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_負面魔法機率
SOURCE_ROWS=4_VISIBLE
DONOR_LOADER=SkillsProbabilityTable
RUNTIME=L1MagicPc.calcProbability
SKILL_ID_MAPPING=NOT_PROVEN
JAVA_DIVISION=PROVEN
CURRENT_TYPE123_FORMULA=PROVEN
CURRENT_TYPE6_FORMULA=P_BASE_PLUS_INT
TYPE5_STEP_ASYMMETRY=PROVEN
TYPE6_OVERFLOW=PROVEN_POSSIBLE
TYPE12_13_CAST=PROVEN
TYPE14_18_MIN_EFFECTIVE=7,9,13,15,20
TYPE20_OVERRIDE=PROVEN
TYPE21_CAP=PROVEN
TYPE22_OVERRIDE=PROVEN
TYPE24_28_LOW_MR_EFFECT=CAN_INCREASE_PROBABILITY
WIZARD_POSTPROCESS=PROVEN
ELF_POSTPROCESS=PROVEN
ELF_70_71_DISCONTINUITY=35_TO_55
NEGATIVE_PROBABILITY=PROVEN_POSSIBLE
ABOVE_100_PROBABILITY=PROVEN_POSSIBLE_BEFORE_POSTPROCESS
850_NATIVE=PARTIAL
CLIENT_DEP=NO_CURRENT_PROOF
LEVEL=L3
BLOCKERS=skill_id mapping; exact 850 probability formula mapping; type23 semantics; final clamp policy; type24-28 sign-reversal policy; data range validation
```
