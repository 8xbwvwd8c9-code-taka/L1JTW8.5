# 850 Inventory Backing Model Adjudication — 2026-09-24

```text
STATUS=INFERENCE_REFINED
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
MEMORY_WRITE=NO
NO_BROAD_RESCAN=YES
```

## Evidence A — backing-model scan

Authoritative run:

```text
TIME=2026-09-24 00:25:17
PID=35568
PROCESS_START_UTC=2026-09-23T16:17:01.6385489Z
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
GRID=0x20F74730
ROOT=0x217D4418
INVWIN=0x0F4FBAF8
```

Verified graph:

```text
GRID +0xEC -> ROOT
ROOT +0x15C -> GRID
ROOT +0x168 -> INVWIN
```

The strongest item-shaped local candidate was:

```text
INVWIN+0x220
BEGIN=0x0F0FF488
END  =0x0F0FFB88
CAP  =0x0F0FFBC8
USED =0x700 = 1792 = 28 * 64
CAPACITY_BYTES=0x740 = 1856 = 29 * 64
```

A pointer-record analysis at that begin address reported:

```text
STRIDE=64
ITEM_OFF=+0x04
NONZERO=27
MATCHES=27
MATCH_PCT=100
UNIQUE=12
```

This is strong **single-run** evidence for a 64-byte item-shaped buffer.

## INVWIN+0x210 is not an independent model

The same scan reported:

```text
PTR INVWIN+0x210 = 0x0F0FF508
PTR INVWIN+0x220 = 0x0F0FF488
```

Difference:

```text
0x0F0FF508 - 0x0F0FF488 = 0x80 = 2 * 64
```

The +0x210 candidate therefore lands exactly two 64-byte records into the +0x220 backing region and reproduces the same catalog sequence shifted by two records.

Classification:

```text
INVWIN+0x210=ALIAS_OR_INTERIOR_POINTER_CANDIDATE
DO_NOT_COUNT_AS_SECOND_MODEL=YES
```

## Evidence B — restart/recreated-object validation

Later authoritative run:

```text
TIME=2026-09-24 00:34:41
PID=36132
PROCESS_START_UTC=2026-09-23T16:33:56.3704043Z
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
GRID=0x2104A6E0
ROOT=0x21865F98
INVWIN=0x021A11D0
VERIFIED_GRID_OBJECTS=1
```

Targeted `INVWIN+0x1E0..0x230` vector64 validation returned:

```text
CANDIDATE_COUNT=0
STATUS=NO_STRONG_VECTOR64_CURRENT_PROCESS
```

Therefore `INVWIN+0x220` did **not** survive the next full process/runtime instance.

Classification:

```text
INVWIN+0x220=SINGLE_RUN_STRONG_BUT_RESTART_UNSTABLE
PROMOTE_TO_LAYOUT=NO
FORMAL_WP5=NOT_YET
```

Possible explanations remain open: transient UI state, stale/alternate backing store, mode-dependent layout, or a false-positive monotonic pointer window. No explanation is promoted without new evidence.

## Fixed-window scorer overread correction

The backing-model scorer tested fixed record windows beginning at pointer-valued fields, even when the corresponding vector-like triple had a much smaller logical `USED` range. Therefore high catalog scores can come from bytes **after** a vector's `END` and must not be promoted without a bounds check.

### GRID +0x0F4 / +0x0F8 / +0x0FC

Real triple:

```text
GRID+0x0F4 = BEGIN=0x29680D28
GRID+0x0F8 = END  =0x29680D58
GRID+0x0FC = CAP  =0x29680D5C
USED=48 bytes
```

Consequences:

```text
PTR GRID+0xF8 starts exactly at END
PTR GRID+0xFC starts exactly at CAP
```

Any 32/48/64/96-byte × 64-record score beginning at `+0xF8` or `+0xFC` is necessarily reading outside the vector's used range. Even the `+0xF4` scans that treat the target as 64 records exceed the logical 48-byte range by a large margin.

Classification:

```text
GRID+0x0F4=REAL_SMALL_VECTOR_OR_STATE
GRID+0x0F8=VECTOR_END_POINTER_NOT_MODEL_START
GRID+0x0FC=VECTOR_CAP_POINTER_NOT_MODEL_START
PLAYER_ITEM_BACKING_MODEL=UNPROVEN
```

### ROOT +0x0C0 / +0x0C4

Real triple:

```text
ROOT+0x0C0 = BEGIN=0x2BC6C288
ROOT+0x0C4 = END  =0x2BC6C2A4
ROOT+0x0C8 = CAP  =0x2BC6C2AC
USED=28 bytes
```

The scorer's 80-byte × 64-record candidates starting at `ROOT+0xC0` or `ROOT+0xC4` therefore overrun the logical range immediately.

Classification:

```text
ROOT+0x0C0=REAL_SMALL_VECTOR_OR_STATE
ROOT+0x0C4=VECTOR_END_POINTER_NOT_MODEL_START
80_BYTE_RECORD_MODEL_FROM_THIS_TRIPLE=REJECTED
```

### INVWIN +0x0F4

Real triple:

```text
INVWIN+0x0F4 BEGIN=0x2BC6CEB8
END=0x2BC6CED4
CAP=0x2BC6CEDC
USED=28 bytes
```

The scorer's 80-byte × 64-record interpretation exceeds the real used range and is rejected as inventory-record proof.

### ROOT +0x154 low-entropy high-score artifact

One high-scoring view reported:

```text
STRIDE=64
ITEM_OFF=+0x0C
MATCHES=59/60 nonzero
MATCH_PCT=98
UNIQUE=2
```

The samples are dominated by repeated item IDs `257` and `1`. High match percentage with only two unique IDs is low-entropy evidence and is not consistent enough with a normal heterogeneous player inventory to promote.

A second `ROOT+0x154` view at `ITEM_OFF=+0x10` had 26 unique IDs but only 58% match rate. It also remains unpromoted.

Classification:

```text
ROOT+0x154=UNPROVEN_POINTER_DATA
HIGH_SCORE_LOW_ENTROPY=REJECTED_AS_PROMOTION_CRITERION
```

## Structural survivor analysis

After applying logical `BEGIN..END` bounds and restart stability, none of the old top-30 backing candidates currently satisfies all of:

```text
1. belongs to a proven GRID/ROOT/INVWIN field
2. buffer interpretation respects logical used bounds
3. record stride matches used/capacity arithmetic
4. item-id field has meaningful catalog correlation
5. survives a new process / recreated UI graph
```

`INVWIN+0x220` is the only old candidate that was structurally coherent in one run (`28*64` used, `29*64` capacity, 27/27 nonzero item IDs), but it failed gate 5.

Result:

```text
OLD_BACKING_MODEL_WINNER=NONE
DO_NOT_RESUME_BROAD_BACKING_SCAN=YES
```

## Constructor-code evidence remains separate

V3 runtime module analysis supports a different class of evidence:

```text
GRID constructor-like region: dense zero-initialized cluster around +0x1D8..+0x204
ROOT +0x168: constructor-supported pointer/owned-reference slot
```

This evidence describes object layout/bookkeeping but does not prove which field is the live item-record container.

Keep the two evidence classes separate:

```text
CODE_LAYOUT_EVIDENCE != LIVE_ITEM_BACKING_PROOF
```

## Root global candidate

V3 also exposed an absolute module-global zero store immediately after the ROOT_B vtable write:

```text
ROOT_B_XREF_RVA=0x00701E6B
GLOBAL_VA=0x016BCEE8
GLOBAL_RVA=0x012BCEE8
OP=C7 05 <abs32> 00000000
```

If ROOT_B is a teardown/destructor context, this is a strong candidate for a singleton/global owner slot being cleared. It may also be a state flag; construction-side evidence is required.

Prepared tool:

```text
launcher/850Launcher/tools/run_850_inventory_root_global_xref_v4b.ps1
```

Promotion rule:

```text
construction-side store of this/returned ROOT object to RVA 0x012BCEE8
OR repeated ROOT-context pointer loads from same global
=> STABLE_ROOT_OWNER_CANDIDATE

otherwise
=> FLAG_OR_STATE_GLOBAL
```

## Current ranking

```text
A. UI object graph
   STATUS=STRONG
   GRID+0xEC -> ROOT
   ROOT+0x15C -> GRID
   ROOT+0x168 -> INVWIN

B. ROOT global RVA 0x012BCEE8
   STATUS=HIGH_PRIORITY_CODE_ANCHOR
   TEARDOWN_ZERO_STORE=PROVEN
   OWNER_SEMANTICS=NOT_YET

C. INVWIN+0x220 28x64 buffer
   STATUS=SINGLE_RUN_STRONG / RESTART_UNSTABLE
   ITEM_OFF=+0x04 candidate
   PROMOTION=NO

D. GRID+0x1D8..+0x204
   STATUS=CONSTRUCTOR-SUPPORTED_COLLECTION/BOOKKEEPING_CLUSTER
   VECTOR_SEMANTICS=UNPROVEN

E. GRID+0x0F4
   STATUS=REAL_SMALL_VECTOR/STATE
   INVENTORY_BACKING=UNPROVEN
```

## Next runtime order

Do not run a broad scan.

```text
1. run_850_inventory_ctor_owner_trace_v4.ps1
2. run_850_inventory_root_global_xref_v4b.ps1
3. if stable ROOT/GRID owner path is proven, perform only fixed-offset reads
4. re-check GRID+0x1D8..0x204 and INVWIN+0x200..0x228 without searching other memory
5. only a restart-stable 64-byte candidate may proceed to ObjectId/Count/Enchant/Equipped correlation
```

```text
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
MEMORY_WRITE=NO
```
