# GOAL
Use the existing x32dbg snapshot to capture the startup-time writer(s) of the 850 inventory ROOT global without broad memory scanning or target-memory modification.

Authoritative debugger:
`I:\L共通工具\snapshot_2026-05-27_12-11\release\x32\x32dbg.exe`

Authoritative client:
`I:\8.50c客服端\Lin.bin2`
SHA256=`FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`

Target:
`ROOT_GLOBAL_RVA=0x012BCEE8`
Runtime VA = `Lin.bin2 module base + 0x012BCEE8`.

Known static evidence to cross-check only after capture:
- teardown absolute-address literal RVA `0x00701E78`
- expected teardown writer start RVA `0x00701E76`
- expected teardown post-write EIP RVA `0x00701E80`
- second absolute store literal candidate RVA `0x00C9A1E5`

# MUST
- SUBAGENTS=0.
- NO_REPO_WIDE_SCAN.
- Verify the exact client path and SHA256 before collecting evidence.
- Prefer launch-under-debugger so the hardware write breakpoint is armed before ROOT initialization. If direct launch-under-debugger is not viable, attach at the earliest possible process lifetime and explicitly record that limitation.
- Set a hardware WRITE breakpoint on the single dword at `module_base + 0x012BCEE8`.
- Capture every hit up to a small bounded count (max 16): writer instruction address/RVA, post-write EIP, written value, EAX/EBX/ECX/EDX/ESI/EDI/EBP/ESP, thread ID, and call stack when available.
- Distinguish nonzero assignment from zero clear.
- Record whether the first nonzero value equals or is consistent with the live `SEED_GLOBAL_DWORD` observed by V4B (`0x2179AA90` in the current run); do not dereference that pointer.
- Correlate captured writer RVAs with the known V4B static candidates only after the runtime capture is complete.
- Save a concise report to:
  `I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_x32dbg_startup_watch.txt`

# DO NOT
- Do not use software patch breakpoints on target code when a hardware/data breakpoint is sufficient.
- Do not patch, NOP, freeze, inject, hook, alter packets, alter sockets, or call WriteProcessMemory against the game.
- Do not scan heap or MEM_PRIVATE regions.
- Do not perform broad pointer/vector scans.
- Do not change `runtime-map.ini`.
- Do not promote an owner from a single unexplained nonzero value.
- Do not modify repository source code in this task unless a small debugger automation script is strictly required; if created, keep it scoped to this target only.

# VALIDATE
PASS requires all of the following:
1. Exact client SHA256 matches authority.
2. Hardware breakpoint target is exactly `module_base + 0x012BCEE8`.
3. At least one decoder/debugger-aligned instruction is proven to write that exact dword.
4. For owner-lifecycle evidence, capture or independently prove both:
   - a nonzero assignment to the global, and
   - a zero clear to the same global.
5. Report actual writer RVA(s), source operand/register, written value, and process identity.
6. Confirm `TARGET_MEMORY_WRITE=NO`, `HEAP_SCAN=NO`, `MEM_PRIVATE_SCAN=NO`.

If only the zero clear is captured, report `PARTIAL_ZERO_CLEAR_ONLY`.
If only the nonzero assignment is captured, report `PARTIAL_ASSIGNMENT_ONLY`.
If no hit occurs before/through initialization, report `NO_STARTUP_HIT` and explain exactly when the breakpoint became armed; do not widen scope.

# FINAL
Return exactly this compact block plus the saved report path:

```text
STATUS=<PASS_OWNER_LIFECYCLE|PARTIAL_ZERO_CLEAR_ONLY|PARTIAL_ASSIGNMENT_ONLY|NO_STARTUP_HIT|BLOCKED>
CLIENT_SHA256=<sha256>
PID=<pid>
PROCESS_START_UTC=<utc>
MODULE_BASE=<hex>
ROOT_GLOBAL_VA=<hex>
NONZERO_WRITER_RVA=<hex|NONE>
NONZERO_SOURCE=<register/immediate|NONE>
NONZERO_VALUE=<hex|NONE>
ZERO_WRITER_RVA=<hex|NONE>
ZERO_POST_EIP_RVA=<hex|NONE>
KNOWN_TEARDOWN_MATCH=<YES|NO|NOT_CAPTURED>
TARGET_MEMORY_WRITE=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
REPORT=I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_x32dbg_startup_watch.txt
NEXT=<one sentence>
```
