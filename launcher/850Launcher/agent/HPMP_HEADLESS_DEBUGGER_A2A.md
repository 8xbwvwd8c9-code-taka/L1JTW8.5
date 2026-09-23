GOAL
Build/run a headless x86 debugger helper for Lin.bin2 so no native-window controller is required. Attach to current Lin.bin2 PID, arm HW write breakpoints on the two Gauge+0x1C0 addresses derived from current runtime evidence, auto-log hit EIP/RVA/registers/stack/thread/call context, continue automatically, and emit one report. User only performs in-game HP/MP changes when prompted.

MUST
- TARGET=I:\8.50c客服端\Lin.bin2; verify SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4 before attach.
- Auto-resolve current PID by exact MainModule path; do not trust stale PID 22964 after restart.
- Re-resolve current HpGauge_Image/MpGauge_Image using existing owner-scan evidence or rerun the existing read-only owner scanner. Do not reuse stale absolute addresses after restart.
- Compute HP_WATCH=HP_GAUGE+0x1C0 and MP_WATCH=MP_GAUGE+0x1C0.
- PRIMARY_IMPL=headless helper, not GUI automation. Preferred: small x86 .NET console EXE compiled locally with Windows debugging APIs. Use DebugActiveProcess/WaitForDebugEvent/ContinueDebugEvent; enumerate all target threads; set DR0/DR1 + DR7 write/4-byte hardware breakpoints with x86 CONTEXT; arm new threads on CREATE_THREAD_DEBUG_EVENT; capture EXCEPTION_SINGLE_STEP hits. If helper is built/run from 64-bit host against 32-bit target, use the correct WOW64 context APIs/structures or compile helper x86.
- PRIVILEGE_GATE: before DebugActiveProcess, inspect whether the current process token is elevated and whether SeDebugPrivilege is present/enabled. If SeDebugPrivilege is present but disabled, enable it with OpenProcessToken + LookupPrivilegeValue + AdjustTokenPrivileges and verify GetLastWin32Error!=ERROR_NOT_ALL_ASSIGNED. If attach returns Win32=5 or the token is not elevated, self-relaunch the exact runner/helper with UAC via Start-Process -Verb RunAs (or equivalent ShellExecute runas), preserving arguments and output paths. Do not ask the user to type debugger commands; only ask them to approve the UAC prompt once. After elevation, re-resolve PID/start time/gauge addresses and retry exactly once.
- Do not require permanent local-security-policy changes and do not grant SeDebugPrivilege to an account permanently. Prefer one-run elevation only.
- On each hit record: timestamp, PID/TID, watch name HP/MP, watched VA, EIP, EIP_RVA relative to Lin.bin2 base when in module, EAX/EBX/ECX/EDX/ESI/EDI/EBP/ESP, DR6, 64 bytes stack around ESP via ReadProcessMemory, and best-effort return addresses/call chain inside Lin.bin2.
- Auto-continue after logging. Capture at least 3 HP hits and 3 MP hits if available.
- Console phases only: READY -> HP_PHASE -> MP_PHASE -> FINAL_IDLE -> DONE. Ask user only for game actions during HP_PHASE/MP_PHASE.
- OUTPUT=I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_dynamic_watch.txt
- Include SOURCE_MODIFIED=NO, TARGET_MEMORY_WRITE=NO, DEBUG_REGISTERS_USED=YES, CLIENT_AUTHORITY=1, PID/start time, module base, resolved gauge/watch addresses, privilege/elevation state, hit counts, unique writer RVAs.
- Cross-check hit RVAs against known static candidates, especially setter 0x00877D50 and ratio caller 0x00C5BF80, but do not force-match them.
- x32dbg may be used only as secondary verification. Its path is I:\L共通工具\snapshot_2026-05-27_12-11\release\x32\x32dbg.exe. Official x64dbg supports -pid attach and hardware breakpoint/script commands, but do not depend on GUI control for PASS.
- No install unless strictly required. Prefer built-in Windows APIs and existing local toolchain.

DO NOT
- DO NOT require native Windows UI automation.
- DO NOT ask user to enter debugger commands, addresses, breakpoints, registers, or stack data.
- DO NOT ask user to permanently weaken UAC, disable security software, alter Local Security Policy, or grant permanent debug rights.
- DO NOT WriteProcessMemory, patch, NOP, freeze, inject DLL/code, hook network, inject packets, or raw-send sockets.
- DO NOT modify Lin.bin2 or launcher runtime state beyond debugger attach/thread debug-register context required for observation.
- DO NOT claim raw HP/MP mapping from Gauge fill alone.
- DO NOT reuse absolute addresses across process restart.
- SUBAGENTS=0; CONTEXT_EXPANSION=NO; NO_REPO_WIDE_SCAN; NO_ARCHIVE_SCAN; NO_FULL_SUITE.

VALIDATE
- SHA gate PASS before attach.
- Exact MainModule path PASS.
- Current-process Gauge object evidence PASS before watchpoint calculation.
- PRIVILEGE_GATE PASS: elevated token confirmed and SeDebugPrivilege enable attempt recorded. If UAC elevation is required, resume automatically after the elevated child starts.
- Hardware watchpoints are 4-byte aligned and armed on all existing/new target threads.
- HP phase produces hits attributable to HP watch; MP phase produces hits attributable to MP watch. If one phase has zero hits, STATUS=PARTIAL and report why; do not guess.
- No target memory writes; ReadProcessMemory only for evidence.
- Detach cleanly with DebugActiveProcessStop when done/error if safe.
- PASS gate: report exists, authority checks PASS, >=1 observed hit, registers/stack captured, TARGET_MEMORY_WRITE=NO. Formal HPMP map still NOT_YET unless current/max operand provenance is directly proven and survives restart validation.

FINAL
Return compact A2A only:
STATUS=<PASS|PARTIAL|BLOCKED>
PID=<pid>
ELEVATED=<YES|NO>
SEDEBUG=<ENABLED|MISSING|FAILED>
HP_HITS=<n>
MP_HITS=<n>
UNIQUE_WRITER_RVAS=<csv>
OUTPUT=<path>
TARGET_MEMORY_WRITE=NO
BLOCKER=<none|reason>
NEXT=<single next action>
