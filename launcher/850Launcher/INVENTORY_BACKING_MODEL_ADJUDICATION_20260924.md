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

## GRID+0x0F4 high catalog score is not enough

The backing scan found a real vector-like triple:

```text
GRID+0x0F4
BEGIN=0x29680D28
END  =0x29680D58
CAP  =0x29680D5C
USED =48 bytes
```

But pointer-record probing starting at the same address reported high catalog scores over many 64/96-byte records, far beyond the vector's logical `END`.

That means the high catalog score is not proof that `GRID+0x0F4` is the player's inventory vector. The probe was observing memory after the logical 48-byte used range and can therefore correlate with unrelated item/template-like data.

Classification:

```text
GRID+0x0F4=REAL_SMALL_VECTOR_OR_STATE
PLAYER_ITEM_BACKING_MODEL=UNPROVEN
CATALOG_SCORE_ALONE=REJECTED_AS_PROMOTION_CRITERION
```

The same caution applies to ROOT pointer targets whose logical vector sizes are much smaller than the fixed record windows used by the backing-model scorer.

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
