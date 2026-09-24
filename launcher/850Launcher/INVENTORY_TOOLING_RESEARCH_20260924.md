# 850 Inventory Tooling Research — 2026-09-24

```text
STATUS=RESEARCH_COMPLETE
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
MEMORY_WRITE=NO
NO_BROAD_RUNTIME_MEMORY_SCAN=YES
```

## Problem found in current tooling

The current inventory branch contains many custom PowerShell/C# scanners, but the V3/V4 work already demonstrated a core weakness in byte-oriented x86 analysis:

```text
raw byte E8 != proven CALL
prologue/RET search != proven function boundary
literal match != aligned instruction reference
```

A known false positive occurred when byte `E8` inside the absolute address of:

```text
C7 05 E8 CE 6B 01 00 00 00 00
```

was interpreted as a CALL candidate.

Therefore future owner/call analysis should not be promoted from byte scanning alone.

## Recommended method stack

### 1. Real x86 decoder first — highest priority

Replace promotion-critical raw-byte instruction guessing with a mature x86 decoder.

Preferred choices:

```text
A. Zydis
B. Capstone
```

Use the decoder for:

```text
instruction boundaries
operand type/width
absolute memory operands
relative CALL/JMP destination
register read/write identity
ModRM/SIB/displacement semantics
basic-block termination
```

Current PowerShell scanners may remain as evidence collectors, but a result must pass decoder alignment before being called:

```text
CALL
GLOBAL STORE
MEMBER ACCESS
FUNCTION/BASIC-BLOCK EDGE
```

Recommended architecture:

```text
PowerShell orchestration
 -> read authoritative Lin.bin2 MEM_IMAGE bytes
 -> send bounded code window to x86 decoder helper
 -> emit normalized instruction JSON/text
 -> V4/V4b reviewer consumes decoded instructions
```

Do not introduce a broad memory scan while doing this.

### 2. Hardware data watchpoint for ROOT_GLOBAL_RVA

Current best owner anchor:

```text
ROOT_GLOBAL_RVA=0x012BCEE8
```

Once the client is available, the shortest path to owner semantics is not another pointer/vector scan. It is a write hardware breakpoint/watchpoint on:

```text
moduleBase + 0x012BCEE8
```

Capture only:

```text
EIP/RVA
thread id
registers
stack / return addresses
old/current global value when readable
process-start identity
```

A construction-time nonzero write plus teardown zero write to the same slot would be much stronger owner evidence than neighborhood scoring.

This uses debugger registers/thread context, not `WriteProcessMemory`.

The repository already contains a reusable headless debugger design in:

```text
launcher/850Launcher/agent/HPMP_HEADLESS_DEBUGGER_A2A.md
launcher/850Launcher/agent/HPMP_DYNAMIC_TRACE_A2A.md
```

That design should be generalized into a small x86 watchpoint helper instead of building another inventory-specific memory scanner.

### 3. Existing x32dbg can be secondary verification

Existing repository evidence records a local x32dbg path:

```text
I:\L共通工具\snapshot_2026-05-27_12-11\release\x32\x32dbg.exe
```

Use it for:

```text
hardware write breakpoint
conditional logging
disassembly / graph
call stack
module memory map
manual secondary verification
```

Do not make GUI automation the formal PASS authority. Prefer the headless helper for repeatable reports.

### 4. Dump unpacked Lin.bin2 module image, then analyze offline

Because disk-static V2 produced zero relevant runtime xrefs while loaded MEM_IMAGE exposed them, an unpacked runtime-module snapshot is valuable.

Preferred route when needed:

```text
loaded Lin.bin2 module
 -> x32dbg integrated Scylla/import reconstruction
 -> reconstructed PE copy
 -> Ghidra offline/headless analysis
```

The dump is a new analysis file; do not modify the running target.

Do not use a generic whole-process heap dump as the default path.

### 5. Ghidra headless after reconstructed PE

For a reconstructed 32-bit Windows PE, automate Ghidra headless with:

```text
processor=x86:LE:32:default
compiler spec=windows / Visual Studio
```

Use it for:

```text
function discovery
CFG/call graph
MSVC RTTI
vtable hierarchy
xrefs to ROOT_GLOBAL_RVA
pseudocode around six known vtable xrefs
```

Prefer reconstructed PE import over loading a generic minidump, because the Windows x86 PE RTTI analyzer has known limitations with minidump-loader programs.

### 6. WinDbg non-invasive inspection — useful secondary path

WinDbg non-invasive attach is useful for module/disassembly inspection where supported.

For lifecycle write attribution, hardware watchpoint/headless debug attach is still more direct.

### 7. WinDbg TTD — last resort, not default

Time Travel Debugging can search memory accesses and function-call timelines after recording, but it is invasive and can cause major slowdown and large traces.

Use only if a normal hardware watchpoint cannot catch the relevant lifecycle event reliably.

### 8. Do not adopt PE-sieve for this WP

PE-sieve is designed around scanning a process for modified/injected PE/shellcode/hook material and dumping findings.

That scope is broader than this project's current inventory gate and conflicts with:

```text
NO_BROAD_RUNTIME_MEMORY_SCAN
NO_MEM_PRIVATE_SCAN
```

It is not required for WP5/WP6.

## Repository audit result

Current branch already has:

```text
many read-only runtime scanners
runtime/module xref scripts
RTTI parser experiment
restart evidence comparers
headless debugger design
x32dbg secondary-debugger path
```

Missing reusable layer:

```text
real x86 instruction decoder abstraction
reusable hardware-watchpoint helper for arbitrary fixed VA/RVA
reconstructed-PE -> Ghidra headless pipeline
```

Those three additions offer more value than another V5 raw-byte scanner.

## Recommended implementation order

```text
WP-A  Add decoder-backed instruction analysis (Zydis or Capstone)
WP-B  Re-run six known inventory xrefs through decoder; retire raw-E8 promotion entirely
WP-C  Generalize headless DRx hardware watch helper
WP-D  Watch ROOT_GLOBAL_RVA=0x012BCEE8 for exact writer context
WP-E  If packed code still blocks static ownership recovery, create one reconstructed Lin.bin2 PE copy and run Ghidra headless
WP-F  Only after stable owner proof, perform one narrow fixed-offset inventory read
```

## Promotion rules after tooling upgrade

```text
CALL=decoder-aligned relative CALL instruction only
GLOBAL_STORE=decoder-aligned memory destination only
FUNCTION_EDGE=decoder/CFG-supported only
OWNER_GLOBAL=construction writer + teardown clear + restart stability
WP5/WP6=unchanged semantic/restart gates
```

## Keep current STOP rules

```text
NO_BROAD_RUNTIME_MEMORY_SCAN
NO_REPEAT_CRASHING_VECTOR_SCAN
NO_HEAP_SCAN
NO_MEM_PRIVATE_SCAN
NO_VECTOR_WIDE_SCAN
NO_381_880_RUNTIME_ADDRESS_REUSE
NO_MEMORY_WRITE
```

```text
DECISION=STOP_BUILDING_RAW_BYTE_V5_SCANNERS
NEXT=ADD_REAL_X86_DECODER_AND_REUSABLE_HARDWARE_WATCHPOINT_LAYER
```
