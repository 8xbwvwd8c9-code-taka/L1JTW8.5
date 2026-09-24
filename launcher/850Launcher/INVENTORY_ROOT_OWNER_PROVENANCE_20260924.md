# 850 Inventory ROOT Owner Provenance — 2026-09-24

## Authority

```text
CLIENT=I:\8.50c客服端\Lin.bin2
SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
BRANCH=work/850-inventory-helper
```

## Decoder-proven ROOT_GLOBAL lifecycle

Authoritative ROOT global:

```text
ROOT_GLOBAL_RVA=0x012BCEE8
```

Capstone V4B writer decoder proves exactly two target writers in the current executable image:

```text
ZERO_CLEAR_WRITER_RVA=0x00701E76
ZERO_CLEAR_POST_EIP_RVA=0x00701E80
ZERO_CLEAR_ASM=mov dword ptr [ROOT_GLOBAL], 0

ASSIGN_WRITER_RVA=0x00C9A1E3
ASSIGN_POST_EIP_RVA=0x00C9A1E9
ASSIGN_ASM=mov dword ptr [ROOT_GLOBAL], ecx
ASSIGN_SOURCE=ECX
```

The assignment source immediately before the global write is:

```text
RVA 0x00C9A1DD
mov ecx, dword ptr [ebp-0x3B8]
```

Therefore the remaining static provenance problem is:

```text
[EBP-0x3B8]
  -> ECX
  -> ROOT_GLOBAL
```

The current run also observed:

```text
SEED_GLOBAL_DWORD=0x2179AA90
SEED_GLOBAL_CLASS=NONMODULE_VALUE_OR_POINTER
```

This value is not dereferenced for promotion.

## Current classification

```text
ROOT_GLOBAL_LIFECYCLE=STRONG_STATIC_DECODER_PROOF
ZERO_CLEAR=PROVEN
NONZERO_CAPABLE_REGISTER_ASSIGNMENT=PROVEN
ASSIGNMENT_STACK_SOURCE=PROVEN
STACK_SOURCE_PROVENANCE=NOT_YET
OWNER_PROMOTION=NOT_YET
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

## V5 narrow provenance gate

Files:

```text
launcher/850Launcher/tools/run_850_inventory_root_assignment_source_v5.ps1
launcher/850Launcher/tools/decoder/trace_850_inventory_root_assignment_source.py
```

V5 reads only this executable module-image window:

```text
TARGET_ASSIGN_RVA=0x00C9A1E3
WINDOW_BEFORE=0x4000
WINDOW_AFTER=0x0200
WINDOW_START_RVA=0x00C961E3
WINDOW_END_RVA=0x00C9A3E3
```

It requires every covered memory region to be:

```text
MEM_COMMIT
MEM_IMAGE
READABLE
EXECUTABLE
```

It does not read heap/MEM_PRIVATE and does not write target memory.

The Capstone stage:

1. finds standard/hotpatch x86 EBP-frame prologue candidates within the narrow window;
2. accepts only a function stream containing both exact structural instructions:
   - `0x00C9A1DD: ECX <- [EBP-0x3B8]`
   - `0x00C9A1E3: [ROOT_GLOBAL] <- ECX`;
3. finds all writes to `[EBP-0x3B8]` before the load;
4. reports source operand and nearby defining instruction;
5. never infers control-flow dominance from textual ordering alone.

Possible decisions:

```text
PASS_UNIQUE_STACK_SLOT_WRITE_CANDIDATE
PASS_MULTIPLE_STACK_SLOT_WRITE_CANDIDATES
STACK_SOURCE_NOT_FOUND_IN_WINDOW
NO_STANDARD_FUNCTION_CHAIN
REJECT_IDENTITY_GATE
```

Even a unique static write remains `OWNER_PROMOTION=NOT_YET` until adjudicated with the independent startup x32dbg lane or equivalent execution proof.

## Run

```powershell
cd I:\L1JTW85_inventory_run
git fetch origin
git switch --detach origin/work/850-inventory-helper

pwsh -File .\launcher\850Launcher\tools\run_850_inventory_root_assignment_source_v5.ps1 -SkipSetup
```

Return:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_assignment_source_v5.txt
```

## Hard boundaries

```text
TARGET_MEMORY_WRITE=NO
WRITEPROCESSMEMORY=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
RAW_E8_PROMOTION=NO
DECODER_ALIGNED_PROMOTION=YES
```
