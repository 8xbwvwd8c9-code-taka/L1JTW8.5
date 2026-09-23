# 850 Inventory XREF Status — 2026-09-24

## Authority

```text
CLIENT=I:\8.50c客服端\Lin.bin2
SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
BRANCH=work/850-inventory-helper
```

## Proven UI graph

```text
InventoryItemGrid +0xEC -> Root
Root +0x15C -> InventoryItemGrid
Root +0x168 -> InvWin
```

These relations are runtime UI ownership evidence only; backing item collection and record layout remain unproven.

## V2 — completed / retired

```text
STATUS=PASS_STATIC_ONLY_V2
VTABLE_LITERAL_XREF_TOTAL=0
GLOBAL_MEMBER_REF_TOTAL=0
DISK_STATIC_RAW_XREF_PATH=EXHAUSTED
```

Reason: packed/virtual `Lin.bin2` does not expose the relevant unpacked runtime code in the raw-file executable view. Do not repeat or widen this scan.

## V3 — completed / coarse ranking retired

```text
STATUS=PASS_RUNTIME_MODULE_ONLY
VTABLE_RUNTIME_XREF_TOTAL=6
GLOBAL_RUNTIME_MEMBER_REF_TOTAL=7176
STRONG_OWNER_WINDOW_COUNT=0
BEST_OWNER=NONE
```

Recovered vtable xrefs:

```text
INVWIN_A=0x0070135A
GRID_A  =0x007013DA
ROOT_A  =0x0070184E
INVWIN_B=0x00701C2B
GRID_B  =0x00701C8B
ROOT_B  =0x00701E6B
```

The ±0x600 displacement scorer is retired because it crosses function/class boundaries. The V3 nearby-call list is also retired because its byte-level `E8` detector can misclassify an immediate byte as a CALL opcode.

Proven false positive:

```text
reported CALL RVA=0x00701E78
aligned instruction at 0x00701E76:
C7 05 E8 CE 6B 01 00 00 00 00
```

Here `E8` is part of absolute address `0x016BCEE8`, not a CALL.

Full reconstruction:

```text
launcher/850Launcher/INVENTORY_V3_BYTE_RECONSTRUCTION_20260924.md
```

## Code-layout evidence

Aligned reconstruction supports:

```text
INVWIN_A: vtable write + zero init around +0x178/+0x17C
GRID_A:   vtable write + dense zero-init cluster beginning +0x1D8
ROOT_A:   vtable write + ROOT+0x168=0 + embedded init at +0x16C
```

Candidate GRID triples remain inference only:

```text
VECTOR_A={+0x1D8,+0x1DC,+0x1E0}
VECTOR_B={+0x1E4,+0x1E8,+0x1EC}
VECTOR_C={+0x1F0,+0x1F4,+0x1F8}
```

No inheritance relation is inferred from vtable-xref spacing alone.

## Highest-priority code anchor

ROOT_B contains:

```text
RVA 0x00701E76
C7 05 E8 CE 6B 01 00 00 00 00
```

Therefore:

```text
GLOBAL_VA =0x016BCEE8
GLOBAL_RVA=0x012BCEE8
TEARDOWN_CLEAR=PROVEN
OWNER_SEMANTICS=NOT_YET
```

Only ROOT_B among the reconstructed target teardown-shaped snippets clears a module-global immediately after its vtable write. This matches ROOT being the higher-level object in the proven UI graph, but construction-side assignment/repeated ROOT-context loads are still required before promotion.

## Prior backing-model evidence adjudicated

One run produced a strong item-shaped buffer:

```text
INVWIN+0x220
USED=1792=28*64
CAPACITY=1856=29*64
ITEM_OFF=+0x04 candidate
CATALOG_MATCH=27/27 nonzero=100%
```

A later full client process re-established one valid GRID/ROOT/INVWIN graph but found zero vector64 candidates in `INVWIN+0x1E0..0x230`.

Therefore:

```text
INVWIN+0x220=SINGLE_RUN_STRONG_BUT_RESTART_UNSTABLE
PROMOTE_TO_LAYOUT=NO
```

Additional bounds checks rejected old high-score candidates that began at vector END/CAP pointers or read beyond their logical USED ranges.

Full report:

```text
launcher/850Launcher/INVENTORY_BACKING_MODEL_ADJUDICATION_20260924.md
```

## WP6 semantic contract — prepared

```text
launcher/850Launcher/INVENTORY_WP6_SEMANTIC_CONTRACT_20260924.md
```

850-only minimum record semantics are now separated from memory offsets:

```text
ObjectId = concrete item-instance identity used by C_ItemUSe
ItemId   = template/catalog identity used for item selection/name
Count    = stack quantity
Enchant  = secondary/unmapped
Equipped = secondary/unmapped
```

Existing 850 recovery proves the logical normal-item-use action contract:

```text
PacketHandler opcode 94 / 0x5E -> C_ItemUSe
first C_ItemUSe field = objectId via readD / LE32
normal healing potion payload = 5E <objectId LE32>
```

Important:

```text
ItemId MUST NOT be substituted for ObjectId
DIRECT_NETWORK_SEND=NO
SESSION_FRAMING_ENCRYPTION=CLIENT_OWNED
IItemUseBridge=UNMAPPED
```

WP6 promotion still requires a restart-stable bounded record source and multi-session ObjectId/ItemId/Count validation.

## Targeted runtime gates

### V4 — function/caller trace

```text
launcher/850Launcher/tools/run_850_inventory_ctor_owner_trace_v4.ps1
```

Purpose: exact six xrefs -> candidate function boundaries -> exact-target direct callers -> module-global stores.

### V4b — ROOT global trace / hardened

```text
launcher/850Launcher/tools/run_850_inventory_root_global_xref_v4b.ps1
SEED_GLOBAL_RVA=0x012BCEE8
```

Purpose: classify exact absolute references to the candidate global and split construction-side vs teardown-side contexts. It reads the module-global dword itself but never dereferences it into heap/MEM_PRIVATE.

### V4c — vtable RTTI / hardened

```text
launcher/850Launcher/tools/run_850_inventory_vtable_rtti_v4c.ps1
```

Purpose: fixed GRID/ROOT/INVWIN vtables -> MSVC x86 `vtable[-1]` CompleteObjectLocator -> TypeDescriptor/ClassHierarchyDescriptor/base names when internally valid; also compare first 32 vtable entries.

This is the preferred first gate because it may identify class relationships without touching heap inventory data.

## One-command next gate

```text
launcher/850Launcher/tools/run_850_inventory_next_gate.ps1
```

Behavior:

```text
client missing/not running -> safe status exit
SHA mismatch -> hard fail
running authoritative client -> V4c -> V4b -> V4
individual tool failure -> report failure, do not widen scope
```

Next boot command:

```powershell
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_next_gate.ps1
```

Expected outputs:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_vtable_rtti_v4c.txt
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b.txt
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_ctor_owner_trace_v4.txt
```

## Hard boundaries

```text
OpenProcess=QUERY_INFORMATION|VM_READ
MEMORY_WRITE=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
NO_381_880_RUNTIME_ADDRESS_REUSE=YES
NO_RETURN_TO_V2_V3_BROAD_RANKING=YES
```

## Current gate

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
NEXT_RUNTIME=run_850_inventory_next_gate.ps1
NEXT_AFTER_OWNER_PROOF=fixed-offset read only; no broad scan
```
