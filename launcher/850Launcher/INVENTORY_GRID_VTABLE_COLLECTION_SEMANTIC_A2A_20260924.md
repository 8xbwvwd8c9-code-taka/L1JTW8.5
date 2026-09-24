# GOAL
Determine the semantic purpose of the GRID member cluster around `+0x1D8..+0x1F8`, with special focus on the B triple `+0x1E4/+0x1E8/+0x1EC`, by tracing only GRID vtable methods and decoder-aligned member accesses. Do not perform broad runtime memory scans.

Authority:
- client: `I:\8.50c客服端\Lin.bin2`
- SHA256: `FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`
- GRID_VTABLE_RVA=`0x00EDDE38`
- ROOT_GLOBAL_RVA=`0x012BCEE8` (restart-stable; do not rediscover)

Current runtime evidence:
- V7 B triple is the only monotonic aligned shape:
  - `GRID+0x1E4 = BEGIN`
  - `GRID+0x1E8 = END`
  - `GRID+0x1EC = CAP`
- controlled `stack_count` action produced no change.
- controlled distinct `record_add` action also produced no change.
- therefore B is not yet proven as the backing inventory record container.

# MUST
- SUBAGENTS=0.
- NO_REPO_WIDE_SCAN.
- Use existing Capstone x86/32 decoder tooling if possible.
- Read only module image / static client bytes.
- Enumerate a bounded prefix of GRID vtable function pointers (start with first 64 entries; expand to max 128 only if needed and record why).
- Decode each unique in-module target function using function-boundary heuristics already used in the repo.
- Identify only decoder-aligned reads/writes whose effective address is `[this + offset]` or an alias demonstrably derived from the same `this`, for offsets:
  `0x1D8,0x1DC,0x1E0,0x1E4,0x1E8,0x1EC,0x1F0,0x1F4,0x1F8`.
- For each access report:
  vtable slot, function RVA, instruction RVA, read/write, width, base register/provenance, offset, nearby calls, and whether the function looks like constructor/destructor/insert/erase/sort/render/selection/bookkeeping.
- Pay special attention to functions that mutate `+0x1E4/+0x1E8/+0x1EC` together.
- If a function calls another helper that receives addresses of these members, follow only that direct helper edge one level deep.
- Cross-check against known GRID constructor RVA neighborhood around `0x007013D8` but do not assume STL/vector semantics.
- Save report to `launcher/850Launcher/INVENTORY_GRID_COLLECTION_SEMANTIC_TRACE_20260924.md`.

# DO NOT
- Do not scan heap or MEM_PRIVATE.
- Do not broad-scan the whole module for displacement literals as the primary method.
- Do not use donor addresses from 381/880.
- Do not modify `runtime-map.ini` or game memory.
- Do not call B an inventory vector solely because it has BEGIN/END/CAP shape.
- Do not reopen ROOT owner discovery.

# VALIDATE
PASS requires at least one of:
1. A decoder-aligned GRID vtable method clearly mutates B triple with recognizable collection semantics (insert/erase/clear/reserve/iteration) and `this` provenance is proven; or
2. Strong negative result: bounded GRID vtable method analysis shows B is used for UI/bookkeeping/render state rather than inventory record storage.

If evidence is mixed, report `SEMANTICS_UNRESOLVED` and rank exact next static function(s) to inspect. No runtime broad-scan fallback.

# FINAL
Return:
```text
STATUS=<PASS_COLLECTION_SEMANTICS|PASS_NONINVENTORY_SEMANTICS|SEMANTICS_UNRESOLVED|BLOCKED>
VTABLE_SLOTS_ANALYZED=<n>
UNIQUE_FUNCTIONS_ANALYZED=<n>
B_TRIPLE_ACCESS_FUNCTIONS=<n>
B_TRIPLE_MUTATORS=<rvas|NONE>
BEST_SEMANTIC_CLASS=<inventory_collection|ui_bookkeeping|render_selection|unknown>
ROOT_DISCOVERY_REOPENED=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
REPORT=launcher/850Launcher/INVENTORY_GRID_COLLECTION_SEMANTIC_TRACE_20260924.md
NEXT=<one sentence>
```
