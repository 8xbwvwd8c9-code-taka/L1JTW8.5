# 850 Inventory Precision Gate V2 — 2026-09-24

```text
STATUS=STATIC_TOOLING_CI_PASS_RUNTIME_NOT_YET
BRANCH=work/850-inventory-helper
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
OWNER_PROMOTION=NOT_YET
MEMORY_WRITE=NO
```

## CI validation

Windows GitHub Actions validation completed successfully:

```text
WORKFLOW=850 Inventory Tooling CI
RUN_ID=35944881299
RESULT=SUCCESS
PYTHON=3.13
CAPSTONE_PACKAGE_VERSION=5.0.9
CAPSTONE_BINDING_VERSION=5.0.7
CAPSTONE_SEMANTIC_SELFTEST=PASS
PYTHON_COMPILE=PASS
POWERSHELL_PARSER=PASS
HEADLESS_WATCH_X86_BUILD=PASS
WRITEPROCESSMEMORY_GATE=PASS
```

Important version note:

```text
pip/package metadata = 5.0.9
capstone.__version__ = 5.0.7
```

Package metadata is the dependency authority. The binding string is recorded diagnostically only; semantic self-tests are still mandatory.

This CI validates tooling syntax/build/decoder behavior only. It does not substitute for runtime owner or inventory evidence.

## Why V2 exists

The earlier V3/V4 byte scanners proved that raw byte `E8` and prologue/RET heuristics are not safe promotion authority for x86 control flow.

V2 changes the promotion chain to:

```text
fixed authoritative Lin.bin2
 -> V4c RTTI
 -> V4b exact ROOT-global absolute-reference set
 -> x86 DR hardware write watch on module+0x012BCEE8
 -> Capstone 5.0.9 decode of the instruction immediately before post-write EIP
 -> require unique aligned absolute memory writer to ROOT_GLOBAL_VA
 -> correlate decoder-proven writer EIP with V4b exact store
 -> fresh-process repeat before owner promotion
```

## New files

```text
launcher/850Launcher/tools/headless_watch/HeadlessWatch850V2.cs
launcher/850Launcher/tools/headless_watch/build_headless_watch.ps1
launcher/850Launcher/tools/run_850_inventory_root_global_watch.ps1

launcher/850Launcher/tools/decoder/requirements.txt
launcher/850Launcher/tools/decoder/setup_capstone_decoder.ps1
launcher/850Launcher/tools/decoder/decode_850_inventory_watch.py
launcher/850Launcher/tools/decoder/selftest_capstone_decoder.py
launcher/850Launcher/tools/decoder/run_850_inventory_watch_decoder.ps1

launcher/850Launcher/tools/correlate_850_inventory_root_watch_decoder_v2.ps1
launcher/850Launcher/tools/run_850_inventory_precision_gate_v2.ps1
.github/workflows/850-inventory-tooling-ci.yml
```

## Decoder authority

Pinned stable dependency:

```text
capstone==5.0.9
ARCH=x86
MODE=32
ABSOLUTE_MEMORY_TARGET_REQUIRED=YES
```

The setup script runs semantic self-tests after installation:

```text
C7 05 <abs32> 00000000
A3 <abs32>
```

Both must resolve as a unique memory writer ending exactly at the supplied post-write EIP and targeting the watched absolute address.

After first setup, if package metadata remains 5.0.9, repeated setup runs do not need network access; they rerun the semantic self-test.

## Writer alignment rule

For each hardware-watch hit:

```text
EIP = CPU address after the instruction that triggered the hardware data breakpoint
```

The watcher captures:

```text
CODE_FROM = EIP-24
CODE_BYTES = 64 bytes
```

The decoder tries only possible x86 predecessor starts within the architectural maximum 15-byte instruction length.

A hit is decoder-authoritative only when exactly one candidate satisfies all of:

```text
instruction start is inside the captured window
instruction end == hardware-watch EIP
Capstone detail marks a memory operand as WRITE
memory operand uses absolute addressing (base=0,index=0)
absolute target == watched ROOT_GLOBAL_VA
```

Register-based memory writers remain diagnostic only because hardware-breakpoint register state is post-instruction state.

Classification:

```text
PASS_UNIQUE_TARGET_WRITER
AMBIGUOUS_MULTIPLE_TARGET_WRITERS
ALIGNED_INSTRUCTION_BUT_ABSOLUTE_TARGET_NOT_PROVEN
NO_ALIGNED_PREDECESSOR
```

No raw opcode fallback is permitted for promotion.

## Hardware-watch safety boundary

```text
DebugActiveProcess=YES
DR0..DR3 / DR6 / DR7=debug-register control only
ReadProcessMemory=read-only evidence capture
WriteProcessMemory=NO
TARGET_MEMORY_WRITE=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
```

The runner requests one-time elevation and attempts `Process.EnterDebugMode()` for the current run only. It does not change persistent Windows security policy.

The helper clears debug registers before detach.

## ROOT owner correlation V2

The final V2 correlation accepts a strong single-process candidate only when all evidence has the same:

```text
CLIENT_SHA256
PID
PROCESS_START_UTC
CLIENT_AUTHORITY=1
CAPSTONE package metadata version=5.0.9
```

and the hit simultaneously has:

```text
hardware write event
+ PASS_UNIQUE_TARGET_WRITER from Capstone
+ matching V4b exact store post-EIP
```

Construction-side candidate:

```text
POST_WRITE_VALUE != 0
V4b kind = STORE_A3 or STORE_89_*
```

Teardown candidate:

```text
POST_WRITE_VALUE == 0
V4b kind = STORE_IMM_C705
decoded IMM32 == 0
```

Both in one process produce only:

```text
OWNER_ANCHOR_STRONG_DECODER_RUNTIME_CANDIDATE
```

This is still not final owner promotion.

Final owner promotion requires the writer RVA relationship to survive a fresh Lin.bin2 process.

## Next boot — preferred flow

First-time decoder preparation can be done before starting the client:

```powershell
cd I:\L1JTW85_inventory_run
git fetch origin
git switch --detach origin/work/850-inventory-helper

pwsh -File .\launcher\850Launcher\tools\run_850_inventory_precision_gate_v2.ps1 -PrepareDecoderOnly
```

Then start the authoritative 8.50c client and run:

```powershell
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_precision_gate_v2.ps1
```

During the watch window use normal client lifecycle actions only.

Primary result to return:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_watch_decoder_correlation_v2.txt
```

If decoder diagnosis is required, also return:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch_capstone.txt
```

## Retired/default-disabled paths

```text
run_850_inventory_next_gate.ps1 = legacy diagnostic only
run_850_inventory_ctor_owner_trace_v4.ps1 raw-E8 caller candidates = non-promotable diagnostic only
V2/V3 broad rankers = retired
```

## Hard stop rules

```text
NO_BROAD_RUNTIME_MEMORY_SCAN
NO_REPEAT_CRASHING_VECTOR_SCAN
NO_HEAP_SCAN
NO_MEM_PRIVATE_SCAN
NO_VECTOR_WIDE_SCAN
NO_381_880_RUNTIME_ADDRESS_REUSE
NO_TARGET_MEMORY_WRITE
NO_RAW_BYTE_OPCODE_PROMOTION
```
