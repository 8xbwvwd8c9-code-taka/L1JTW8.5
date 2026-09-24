# 850 Inventory ROOT Owner Anchor — FROZEN

DATE=2026-09-24
BRANCH=work/850-inventory-helper
CLIENT=I:\8.50c客服端\Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4

## Restart-stable authority

V6b passed across three distinct Lin.bin2 process instances:

```text
SESSION_COUNT=3
DISTINCT_PROCESS_IDENTITIES=3
DISTINCT_PROCESS_STARTS=3
STATUS=PASS_ROOT_OWNER_RESTART_STABLE
OWNER_ANCHOR=PASS_RESTART_STABLE
```

Frozen module-relative graph:

```text
ROOT_GLOBAL_RVA=0x012BCEE8
ROOT_VTABLE_RVA=0x00EDE2F8
GRID_VTABLE_RVA=0x00EDDE38
INVWIN_VTABLE_RVA=0x00EDE180
ROOT+0x15C -> GRID
ROOT+0x168 -> INVWIN
GRID+0xEC -> ROOT
```

Absolute object addresses are process-local and must never be persisted as authority.

## Supporting static provenance

```text
ROOT constructor RVA=0x00701810
construction assignment writer RVA=0x00C9A1E3
construction source=ECX from [EBP-0x3B8]
teardown zero writer RVA=0x00701E76
teardown post-write EIP RVA=0x00701E80
```

V5 proved a unique stack-slot write feeding the construction assignment.

## Frozen decision

```text
ROOT_OWNER_DISCOVERY=CLOSED
ROOT_GLOBAL_RVA=FROZEN
ROOT_GRAPH_OFFSETS=FROZEN
OWNER_ANCHOR=PASS_RESTART_STABLE
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

WP5 remains NOT_YET because its formal contract still requires an actual item collection/record layout and controlled semantic inventory validation.

## Next allowed scope

Only bounded collection investigation from the frozen ROOT/GRID/INVWIN graph is allowed.

Initial V7 scope:

```text
GRID+0x1D8
GRID+0x1DC
GRID+0x1E0
GRID+0x1E4
GRID+0x1E8
GRID+0x1EC
GRID+0x1F0
GRID+0x1F4
GRID+0x1F8
```

These offsets are constructor-supported bookkeeping/collection candidates only. V7 may classify triple shape but may not promote STL/vector semantics or inventory ownership without controlled semantic correlation and restart stability.

## Hard boundaries

```text
NO_RETURN_TO_ROOT_DISCOVERY=YES
NO_BROAD_RUNTIME_MEMORY_SCAN=YES
NO_MEM_PRIVATE_SCAN=YES
NO_VECTOR_WIDE_SCAN=YES
NO_REPEAT_CRASHING_VECTOR_SCAN=YES
TARGET_MEMORY_WRITE=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```
