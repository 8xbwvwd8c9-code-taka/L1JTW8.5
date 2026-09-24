# 850 Inventory — Next Boot Handoff — 2026-09-24

```text
BRANCH=work/850-inventory-helper
CLIENT=I:\8.50c客服端\Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
MEMORY_WRITE=NO
```

## Current proven baseline

```text
InventoryItemGrid +0xEC -> Root
Root +0x15C -> InventoryItemGrid
Root +0x168 -> InvWin

GRID_VTABLE_RVA=0x00EDDE38
ROOT_VTABLE_RVA=0x00EDE2F8
INVWIN_VTABLE_RVA=0x00EDE180

INVWIN_A=0x0070135A
GRID_A=0x007013DA
ROOT_A=0x0070184E
INVWIN_B=0x00701C2B
GRID_B=0x00701C8B
ROOT_B=0x00701E6B

ROOT_GLOBAL_RVA=0x012BCEE8
OWNER_SEMANTICS=NOT_YET
```

V3 nearby CALL results are retired. Raw byte `E8` is not accepted as CALL proof.

## Latest hardening

```text
WP6 restart gate:
  >=3 authoritative sessions
  PID + PROCESS_START_UTC required
  >=2 distinct process-start identities
  exact same itemId=count expectation set
  same ItemId set
  >=2 expected ItemIds/session
  RecordCount > 0/session
  RecordCount == UniqueObjectIds/session

V4:
  only six known owner/xref pairs are analyzed
  extra vtable literal hits are diagnostic only
  raw E8 target matches are ALIGNMENT_PROOF=NO
  raw E8 caller evidence is PROMOTABLE=NO

V4b:
  STORE_IMM_C705 alone is not a zero clear
  TEARDOWN_ZERO_STORE requires decoded IMM32 == 0

next_gate:
  stamps every output with PID + PROCESS_START_UTC + CLIENT_SHA256
```

## Hard STOP rules

```text
NO_BROAD_RUNTIME_MEMORY_SCAN
NO_REPEAT_CRASHING_VECTOR_SCAN
NO_HEAP_SCAN
NO_MEM_PRIVATE_SCAN
NO_VECTOR_WIDE_SCAN
NO_381_880_RUNTIME_ADDRESS_REUSE
NO_RUNTIME_MAP_PROMOTION_FROM_STATIC_OR_SINGLE_RUN_CANDIDATES
MEMORY_WRITE=NO
```

## Next boot — exact commands

Use the inventory branch only. Do not change the migration branch upstream.

```powershell
cd I:\L1JTW8.5

git fetch origin
git switch work/850-inventory-helper
git branch --set-upstream-to=origin/work/850-inventory-helper
git pull --ff-only

git branch --show-current
```

Expected:

```text
work/850-inventory-helper
```

Start/login the authoritative 850 client, then run:

```powershell
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_next_gate.ps1
```

This runs only:

```text
V4c RTTI
 -> V4b ROOT global xref
 -> V4 six-known-xref function context
```

Then run the offline reviewer:

```powershell
pwsh -File .\launcher\850Launcher\tools\review_850_inventory_targeted_gates.ps1
```

Paste back only the reviewer output or this file:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_targeted_gate_review.txt
```

Raw evidence remains at:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_vtable_rtti_v4c.txt
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b.txt
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_ctor_owner_trace_v4.txt
```

## Review decision gate

The reviewer may report:

```text
REJECT_IDENTITY_MISMATCH
  -> rerun one complete next_gate; do not combine different processes

REJECT_SIX_XREF_GATE
  -> inspect only missing known xref; do not widen scan

REJECT_CALLER_PROOF_STATE
  -> raw E8 must remain non-promotable

OWNER_ANCHOR_CANDIDATE_FOR_MANUAL_REVIEW
  -> inspect exact V4b construction store + V4 function context
  -> still no automatic promotion

OWNER_PROMOTION_NOT_YET
  -> stay inside RTTI / exact global xref / six-xref scope
```

Only after construction-side and teardown-side evidence prove the same ROOT owner slot may the next tool be prepared:

```text
ONE narrow fixed-offset read-only probe
NO broad scan
NO heap enumeration
NO memory write
```

## Current final state

```text
WP5=NOT_YET
WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
ROOT_GLOBAL_RVA=0x012BCEE8
OWNER_PROMOTION=NOT_YET
NEXT=run_850_inventory_next_gate.ps1 -> review_850_inventory_targeted_gates.ps1
MEMORY_WRITE=NO
```
