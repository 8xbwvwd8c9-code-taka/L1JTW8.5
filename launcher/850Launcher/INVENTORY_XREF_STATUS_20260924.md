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
KNOWN_ZERO_STORE_POST_EIP_RVA=0x00701E80
TEARDOWN_CLEAR=PROVEN_FROM_RECONSTRUCTION
OWNER_SEMANTICS=NOT_YET
```

Only ROOT_B among the reconstructed target teardown-shaped snippets clears a module-global immediately after its vtable write. This matches ROOT being the higher-level object in the proven UI graph, but a construction-side assignment and restart stability are still required before owner promotion.

V4b independently decodes the `C7 05 [global] imm32` immediate and counts a teardown clear only when `imm32 == 0`; a generic `C7 05` hit is not enough.

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

## WP6 semantic contract — prepared / implementation hardened

```text
launcher/850Launcher/INVENTORY_WP6_SEMANTIC_CONTRACT_20260924.md
```

850-only minimum record semantics are separated from memory offsets:

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

WP6 restart comparison enforces:

```text
>=3 authoritative sessions
all sessions PASS
all sessions have PID + PROCESS_START_UTC
>=2 distinct process-start identities
same exact itemId=count expectation set
same ItemId set
>=2 expected ItemIds/session
RecordCount > 0/session
RecordCount == UniqueObjectIds/session
```

Sessions lacking authoritative SHA/CLIENT_AUTHORITY/process-start identity are ignored for PASS purposes. If known counts change, begin a separate proof set instead of mixing different truth inputs into one restart-stability result.

WP6 promotion still requires a restart-stable bounded record source and multi-session ObjectId/ItemId/Count validation.

## Tooling upgrade — implemented hardware-watch layer

Research report:

```text
launcher/850Launcher/INVENTORY_TOOLING_RESEARCH_20260924.md
```

A reusable x86 hardware-write watch helper now exists:

```text
launcher/850Launcher/tools/headless_watch/HeadlessWatch850V2.cs
launcher/850Launcher/tools/headless_watch/build_headless_watch.ps1
```

Properties:

```text
compiled x86
DebugActiveProcess
DR0..DR3 / DR7 hardware write watchpoints
new threads armed on debug events
writer EIP/registers/stack captured
watched DWORD read after each write
DR6 cleared after observation
DR0..DR7 cleared before detach
WriteProcessMemory=NO
TARGET_MEMORY_WRITE=NO
```

Important distinction:

```text
TARGET_MEMORY_WRITE=NO
DEBUG_REGISTER_CONTEXT_WRITE=YES
```

The helper changes thread debug-register context only to install/remove hardware breakpoints. It does not modify target game data/code memory.

The build output is local only and ignored by git:

```text
launcher/850Launcher/tools/headless_watch/bin/
```

## ROOT global hardware-watch runner

```text
launcher/850Launcher/tools/run_850_inventory_root_global_watch.ps1
```

Authority/safety gates:

```text
exact Lin.bin2 path
exact SHA256
exactly one matching process
PID + PROCESS_START_UTC
x86 module/RVA bounds
4-byte watch alignment
one-time UAC elevation if required
attempt per-run SeDebugPrivilege enable
no permanent privilege/policy change
```

Watch target:

```text
moduleBase + 0x012BCEE8
```

Runtime output includes:

```text
POST_WRITE_VALUE
writer EIP / EIP_RVA
EAX/EBX/ECX/EDX/ESI/EDI/EBP/ESP
DR6
bounded code bytes around post-write EIP
bounded stack bytes
module-range return-address candidates
```

The runner archives each completed watch report by process-start timestamp + PID.

## Hardware-watch reviewer

```text
launcher/850Launcher/tools/review_850_inventory_root_global_watch.ps1
```

It separates:

```text
zero writes
nonzero writes
known ROOT_B teardown clear at post-write RVA 0x00701E80
other writer RVAs
```

It never promotes ROOT ownership from a single process.

## V4b exact-store correlation

```text
launcher/850Launcher/tools/correlate_850_inventory_root_watch_v4b.ps1
```

This combines actual executed hardware-watch EIP with V4b's exact absolute-store candidates.

Promotion-critical matching rules:

```text
C7 05 [global] imm32:
  literal RVA + 8 = post-write EIP RVA

A3 [global]:
  literal RVA + 4 = post-write EIP RVA

89 /r [abs32 global]:
  literal RVA + 4 = post-write EIP RVA
```

Semantic classification:

```text
nonzero + STORE_A3/STORE_89_* => construction-side assignment candidate
zero + STORE_IMM_C705 + IMM32=0 => teardown clear candidate
```

A nonzero write alone is not enough; it must correlate to a register-store form. A zero write alone is not enough; it must correlate to the decoded immediate-zero store.

## V4c — vtable RTTI

```text
launcher/850Launcher/tools/run_850_inventory_vtable_rtti_v4c.ps1
```

Purpose: fixed GRID/ROOT/INVWIN vtables -> MSVC x86 `vtable[-1]` CompleteObjectLocator -> TypeDescriptor/ClassHierarchyDescriptor/base names when internally valid; also compare first 32 vtable entries.

This remains the preferred static/runtime-module identity gate because it may identify class relationships without touching heap inventory data.

## V4 legacy diagnostic trace

```text
launcher/850Launcher/tools/run_850_inventory_ctor_owner_trace_v4.ps1
```

V4 is retained for diagnostics only.

Its raw byte `E8` candidates are explicitly:

```text
ALIGNMENT_PROOF=NO
PROMOTABLE=NO
```

Function starts/ends are prologue/RET heuristics and are not symbol-level truth.

V4 is no longer part of the default next-runtime path.

## Precision runtime gate — authoritative next path

```text
launcher/850Launcher/tools/run_850_inventory_precision_gate.ps1
```

Order:

```text
1. V4c RTTI
2. V4b exact references to ROOT global
3. hardware write watch on module+0x012BCEE8
4. watch report review
5. actual-writer EIP <-> V4b exact-store correlation
```

Explicitly excluded:

```text
RAW_E8_CALLER_HEURISTIC=NOT_USED
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
TARGET_MEMORY_WRITE=NO
```

Evidence lifecycle hardening:

```text
old transient watch/review/correlation files removed before each watch
watch exit 0 = executed write observed
watch exit 5 = valid run, no write observed
other watch exit = tool failure; no review/correlation promotion
fresh compact precision-gate summary emitted every run
```

Next boot command:

```powershell
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_precision_gate.ps1
```

Primary outputs:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_precision_gate_summary.txt
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_watch_v4b_correlation.txt
```

If no write is observed, rerun from login/character selection and exercise normal enter-world/logout-relogin lifecycle. Do not widen memory scanning.

## Legacy one-command gate

```text
launcher/850Launcher/tools/run_850_inventory_next_gate.ps1
```

Status:

```text
LEGACY_DIAGNOSTIC_ONLY
```

It still runs V4c -> V4b -> V4, but V4 raw-call/function heuristics cannot promote ownership and this is no longer the preferred runtime path.

## Remaining tooling gap

The hardware-watch layer is implemented. The remaining high-value tooling gap is a real decoder-backed x86 instruction layer:

```text
preferred=Zydis or Capstone
```

Until that exists:

```text
runtime hardware EIP + exact-store correlation > raw byte instruction guessing
raw E8/prologue/RET heuristics remain non-promotable
```

## Hard boundaries

```text
TARGET_MEMORY_WRITE=NO
WRITEPROCESSMEMORY=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
NO_381_880_RUNTIME_ADDRESS_REUSE=YES
NO_RETURN_TO_V2_V3_BROAD_RANKING=YES
RAW_E8_CALLERS_PROMOTABLE=NO
```

## Current gate

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
ROOT_GLOBAL_RVA=0x012BCEE8
KNOWN_TEARDOWN_POST_EIP_RVA=0x00701E80
OWNER_PROMOTION=NOT_YET
NEXT_RUNTIME=run_850_inventory_precision_gate.ps1
NEXT_TOOLING=add real x86 decoder layer
NEXT_AFTER_OWNER_PROOF=fixed-offset read only; no broad scan
```
