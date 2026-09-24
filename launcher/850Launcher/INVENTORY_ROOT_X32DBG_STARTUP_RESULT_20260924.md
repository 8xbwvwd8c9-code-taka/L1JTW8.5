# 850 Inventory ROOT x32dbg startup-watch result — 2026-09-24

```text
STATUS=NO_STARTUP_HIT
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
PID=22376
PROCESS_START_UTC=2026-09-24T04:10:39.0444770Z
MODULE_BASE=0x00400000
ROOT_GLOBAL_RVA=0x012BCEE8
ROOT_GLOBAL_VA=0x016BCEE8
CURRENT_VALUE_AT_ATTACH=0x2179AA90
WATCH_TYPE=HARDWARE_WRITE_DWORD
HITS=0
TARGET_MEMORY_WRITE=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
```

## Interpretation

This is not contradictory evidence against the ROOT owner anchor.

The launch-under-debugger path was not viable because of the elevation/protection gate. The debugger therefore attached to an already-running client process, and the target global was already nonzero (`0x2179AA90`) when the watch became armed. No subsequent lifecycle write occurred during that watch window.

Therefore:

```text
X32DBG_STARTUP_LANE=DIAGNOSTIC_ONLY
OWNER_ANCHOR_NEGATIVE_EVIDENCE=NO
MAINLINE_BLOCKER=NO
RERUN_REQUIRED=NO
```

Known static writer evidence remains authoritative for code identity:

```text
NONZERO_WRITER_RVA=0x00C9A1E3
NONZERO_ASM=mov dword ptr [ROOT_GLOBAL], ecx
ZERO_WRITER_RVA=0x00701E76
ZERO_POST_WRITE_EIP_RVA=0x00701E80
ZERO_ASM=mov dword ptr [ROOT_GLOBAL], 0
```

V5 provenance independently proves the nonzero assignment comes from the ROOT constructor result, and V6 proves the live runtime graph for the current process.

## Mainline

Do not spend more time forcing x32dbg launch-under-debugger unless later evidence becomes contradictory.

Continue with:

```text
V6b full-process restart gate
 -> PASS_ROOT_OWNER_RESTART_STABLE
 -> freeze ROOT_GLOBAL_RVA=0x012BCEE8
 -> bounded exact-offset collection investigation only
```

Hard boundaries remain:

```text
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
MEMORY_WRITE=NO
```
