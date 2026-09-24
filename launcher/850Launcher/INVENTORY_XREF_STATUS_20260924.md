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

These relations prove UI ownership only. Backing item collection and record layout remain unproven.

## Retired evidence paths

```text
V2 raw-file XREF scan              = RETIRED
V3 broad displacement ranking      = RETIRED
V3 raw byte E8 nearby-call ranking = RETIRED
V4 raw E8 caller heuristic         = DIAGNOSTIC_ONLY
```

Known false positive that forced this retirement:

```text
reported CALL RVA=0x00701E78
actual aligned instruction at 0x00701E76:
C7 05 E8 CE 6B 01 00 00 00 00
```

The byte `E8` belongs to absolute address `0x016BCEE8`; it is not a CALL opcode.

Historical details remain in:

```text
INVENTORY_V3_BYTE_RECONSTRUCTION_20260924.md
INVENTORY_BACKING_MODEL_ADJUDICATION_20260924.md
INVENTORY_OFFLINE_INFERENCE_20260924.md
```

## Highest-priority owner anchor

ROOT_B aligned reconstruction contains:

```text
RVA 0x00701E76
C7 05 E8 CE 6B 01 00 00 00 00
```

Therefore:

```text
ROOT_GLOBAL_RVA=0x012BCEE8
KNOWN_ZERO_STORE_POST_EIP_RVA=0x00701E80
TEARDOWN_ZERO_CLEAR=PROVEN_FROM_ALIGNED_RECONSTRUCTION
OWNER_SEMANTICS=NOT_YET
```

The current proof target is no longer "find another pointer". It is:

```text
construction-side nonzero assignment to same global
+
known teardown zero clear
+
fresh-process repeat with stable writer RVA(s)
```

## Real x86 decoder layer — IMPLEMENTED

Authoritative decoder path:

```text
launcher/850Launcher/tools/decoder/
```

Key files:

```text
decode_850_inventory_watch.py
selftest_capstone_decoder.py
setup_capstone_decoder.ps1
run_850_inventory_watch_decoder.ps1
requirements.txt
```

Current decoder authority:

```text
ENGINE=Capstone
PACKAGE_VERSION=5.0.9
ARCH=x86
MODE=32-bit
```

Decoder promotion gates:

```text
1. hardware-watch post-write EIP must be present
2. search only predecessor starts within x86 max instruction length
3. decoded instruction must end exactly at post-write EIP
4. decoded memory operand must be a WRITE
5. resolved memory destination must equal watched VA
6. exactly one target-writing predecessor is required
```

Only this result is promotable:

```text
PASS_UNIQUE_TARGET_WRITER
```

Ambiguous/no-alignment results never fall back to raw byte opcode guessing.

Self-test fixtures include:

```text
C7 05 [abs32] 00000000  -> immediate zero clear
A3 [abs32] eax           -> register assignment
```

Both require unique target writer and `writer end == post-write EIP`.

## Reusable x86 hardware-write watcher — IMPLEMENTED

```text
launcher/850Launcher/tools/headless_watch/HeadlessWatch850V2.cs
launcher/850Launcher/tools/headless_watch/build_headless_watch.ps1
launcher/850Launcher/tools/run_850_inventory_root_global_watch.ps1
```

Properties:

```text
compiled x86
DebugActiveProcess
DR0..DR3 / DR7 hardware write watchpoints
new threads armed on debug events
POST_WRITE_VALUE captured
writer EIP/registers captured
64-byte code window captured from EIP-24
bounded stack capture
DR6 observed then cleared
DR0..DR7 cleared before detach
WriteProcessMemory=NO
TARGET_MEMORY_WRITE=NO
```

Important distinction:

```text
TARGET_MEMORY_WRITE=NO
DEBUG_REGISTER_CONTEXT_WRITE=YES
```

The helper modifies only thread debug-register context to install/remove hardware breakpoints. It does not patch target game data/code memory.

## Exact global reference gate — V4b

```text
launcher/850Launcher/tools/run_850_inventory_root_global_xref_v4b.ps1
SEED_GLOBAL_RVA=0x012BCEE8
```

V4b classifies exact absolute references to the candidate module-global and separately decodes `C7 05` immediate values.

Critical rule:

```text
STORE_IMM_C705 != zero clear by itself
zero clear requires IMM32 == 0
```

## RTTI gate — V4c

```text
launcher/850Launcher/tools/run_850_inventory_vtable_rtti_v4c.ps1
```

Purpose:

```text
GRID/ROOT/INVWIN fixed vtable
 -> vtable[-1]
 -> MSVC x86 CompleteObjectLocator
 -> TypeDescriptor/ClassHierarchy when internally valid
```

This remains useful class-identity evidence and does not scan heap inventory data.

## Decoder-backed correlation — IMPLEMENTED

```text
launcher/850Launcher/tools/correlate_850_inventory_root_watch_decoder_v2.ps1
```

It requires all evidence to share:

```text
CLIENT_SHA256
CLIENT_AUTHORITY=1
PID
PROCESS_START_UTC
MEMORY_WRITE=NO
CAPSTONE_VERSION=5.0.9
```

Correlation logic:

```text
hardware-watch hit
 -> Capstone PASS_UNIQUE_TARGET_WRITER
 -> post-write EIP matches V4b exact-store post EIP
 -> classify semantics
```

Promotion candidates:

```text
nonzero write + decoder-proven writer + STORE_A3/STORE_89_*
  => construction-side assignment candidate

zero write + decoder-proven writer + STORE_IMM_C705 + IMM32=0
  => teardown zero-clear candidate
```

Strong single-process result:

```text
OWNER_ANCHOR_STRONG_DECODER_RUNTIME_CANDIDATE
```

This is still not final owner promotion. A fresh client process must reproduce stable writer RVA(s).

## Authoritative runtime pipeline — Precision Gate V2

Use only:

```text
launcher/850Launcher/tools/run_850_inventory_precision_gate_v2.ps1
```

Order:

```text
0. prepare/self-test Capstone decoder
1. V4c RTTI
2. V4b exact ROOT-global references
3. hardware write watch on module+0x012BCEE8
4. Capstone decode each actual writer hit
5. compact watch review
6. decoder-backed V4b correlation
```

Explicitly excluded:

```text
RAW_E8_CALLER_HEURISTIC=NOT_USED
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
TARGET_MEMORY_WRITE=NO
```

Stale-evidence protection:

```text
transient watch/decoder/review/correlation outputs are removed before each run
PID + PROCESS_START_UTC identity is retained
watch exit 0 = valid run with writes
watch exit 5 = valid run with no writes
other watch exit = tool failure; no promotion
```

Next boot command:

```powershell
cd I:\L1JTW85_inventory_run
git fetch origin
git switch --detach origin/work/850-inventory-helper

pwsh -File .\launcher\850Launcher\tools\run_850_inventory_precision_gate_v2.ps1
```

The only report normally needed back is:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_watch_decoder_correlation_v2.txt
```

If decoder setup alone is desired before starting the client:

```powershell
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_precision_gate_v2.ps1 -PrepareDecoderOnly
```

## Legacy tools

```text
run_850_inventory_precision_gate.ps1 = LEGACY_PRE_DECODER_PIPELINE
run_850_inventory_next_gate.ps1      = LEGACY_DIAGNOSTIC_ONLY
run_850_inventory_ctor_owner_trace_v4.ps1 = DIAGNOSTIC_ONLY
```

None of these may promote raw `E8` or prologue/RET heuristic evidence.

## WP6 semantic contract

```text
ObjectId = concrete item-instance identity used by C_ItemUSe
ItemId   = template/catalog identity
Count    = stack quantity
Enchant  = secondary/unmapped
Equipped = secondary/unmapped
```

Existing 850 recovery proves:

```text
PacketHandler opcode 94 / 0x5E -> C_ItemUSe
first field = objectId via readD / LE32
normal item-use logical payload = 5E <objectId LE32>
```

Rules:

```text
ItemId MUST NOT be substituted for ObjectId
DIRECT_NETWORK_SEND=NO
SESSION_FRAMING_ENCRYPTION=CLIENT_OWNED
IItemUseBridge=UNMAPPED
```

WP6 restart proof still requires:

```text
>=3 authoritative PASS sessions
PID + PROCESS_START_UTC on every session
>=2 distinct process identities
same exact itemId=count expectation set
>=2 expected ItemIds/session
RecordCount > 0
RecordCount == UniqueObjectIds
```

## Prior backing-model adjudication

The old strongest candidate:

```text
INVWIN+0x220
USED=28*64
CAPACITY=29*64
ITEM_OFF=+0x04 candidate
CATALOG_MATCH=27/27 nonzero
```

failed fresh-process stability and is not promoted.

```text
INVWIN+0x220=SINGLE_RUN_STRONG_BUT_RESTART_UNSTABLE
OLD_BACKING_MODEL_WINNER=NONE
```

Do not resume broad/vector scanning.

## Hard boundaries

```text
TARGET_MEMORY_WRITE=NO
WRITEPROCESSMEMORY=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
NO_REPEAT_CRASHING_VECTOR_SCAN=YES
NO_381_880_RUNTIME_ADDRESS_REUSE=YES
NO_RETURN_TO_V2_V3_BROAD_RANKING=YES
RAW_E8_CALLERS_PROMOTABLE=NO
DECODER_ALIGNED_PROMOTION=YES
```

## Current gate

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
ROOT_GLOBAL_RVA=0x012BCEE8
KNOWN_TEARDOWN_POST_EIP_RVA=0x00701E80
OWNER_PROMOTION=NOT_YET
NEXT_RUNTIME=run_850_inventory_precision_gate_v2.ps1
NEXT_REQUIRED_EVIDENCE=fresh-process stable decoder-backed assignment + teardown writer RVAs
NEXT_AFTER_OWNER_PROOF=narrow fixed-offset reads only; no broad scan
```
