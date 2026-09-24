# 850 Inventory ROOT Graph V6 Status — 2026-09-24

## Current authoritative V6 result

```text
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
PID=22376
PROCESS_START_UTC=2026-09-24T04:10:39.0444770Z
MODULE_BASE=0x00400000
ROOT_GLOBAL_RVA=0x012BCEE8
ROOT_OBJECT=0x2179AA90
ROOT_VTABLE=0x012DE2F8
GRID_OBJECT=0x20F28928
GRID_VTABLE=0x012DDE38
GRID_PARENT=0x2179AA90
INVWIN_OBJECT=0x2B8A4540
INVWIN_VTABLE=0x012DE180
ROOT_VTABLE_GATE=PASS
GRID_ROUNDTRIP_GATE=PASS
INVWIN_VTABLE_GATE=PASS
STATUS=PASS_ROOT_GRAPH_EXACT_OFFSETS
OWNER_ANCHOR_RUNTIME=PASS_CURRENT_PROCESS
```

This validates the exact read-only graph:

```text
module+0x012BCEE8 -> ROOT
ROOT[0]           -> module+0x00EDE2F8
ROOT+0x15C        -> GRID
GRID[0]           -> module+0x00EDDE38
GRID+0xEC         -> ROOT
ROOT+0x168        -> INVWIN
INVWIN[0]         -> module+0x00EDE180
```

Safety scope:

```text
READ_DWORD_COUNT=7
EXACT_TARGET_DEREFERENCE=YES
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_SCAN=NO
MEMORY_WRITE=NO
```

## Interpretation

Combined with the already-proven constructor/destructor provenance:

```text
allocate 0x18C
 -> ROOT constructor RVA 0x00701810
 -> return EAX
 -> [EBP-0x1EC]
 -> [EBP-0x3B8]
 -> ECX
 -> module+0x012BCEE8

teardown writer RVA 0x00701E76
 -> module+0x012BCEE8 = 0
```

we now have:

```text
STATIC_ROOT_OWNER_PROVENANCE=PASS
STATIC_ROOT_LIFECYCLE=PASS
RUNTIME_ROOT_GRAPH_CURRENT_PROCESS=PASS
```

This does NOT make FORMAL_WP5 PASS. WP5 still requires item collection/record semantics and controlled inventory-change validation.

## V6b restart gate

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_root_graph_restart_gate_v6b.ps1
```

The current V6 report must first be archived before closing the current client:

```powershell
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_root_graph_restart_gate_v6b.ps1 -UseExistingV6
```

Expected first-stage decision:

```text
STATUS=NEED_FRESH_PROCESS_REPEAT
OWNER_ANCHOR=PASS_CURRENT_PROCESS_ONLY
```

Then fully close Lin.bin2, start a fresh client process, enter the game world, and run:

```powershell
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_root_graph_restart_gate_v6b.ps1
```

Final restart PASS requires:

```text
SESSION_COUNT>=2
DISTINCT_PROCESS_IDENTITIES>=2
DISTINCT_PROCESS_STARTS>=2
all sessions PASS_ROOT_GRAPH_EXACT_OFFSETS
same module-relative ROOT/global/vtable RVAs
same offsets 0x15C / 0x168 / 0xEC
object absolute addresses may differ
```

Promotion result:

```text
STATUS=PASS_ROOT_OWNER_RESTART_STABLE
OWNER_ANCHOR=PASS_RESTART_STABLE
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

After this gate passes, freeze ROOT_GLOBAL as the stable owner anchor and investigate collection offsets only through bounded fixed-offset reads from the proven ROOT/GRID/INVWIN graph. Broad heap/vector scans remain retired.
