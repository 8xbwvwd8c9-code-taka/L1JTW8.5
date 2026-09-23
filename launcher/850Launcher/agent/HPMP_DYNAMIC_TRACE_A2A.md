GOAL
Automate 850 HP/MP runtime-source localization. User only performs in-game actions when prompted. Agent owns all tooling, debugger setup, logging, parsing, and report generation.

MUST
- REPO=I:\L1JTW8.5-launcher
- BRANCH=work/850-launcher-helper
- CLIENT=I:\8.50c客服端\Lin.bin2
- AUTH_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
- Prefer existing debugger in this order: cdb/windbg -> x32dbg. Detect automatically.
- If no supported debugger exists: STOP and report one exact install command; do not install without explicit approval.
- Reuse existing read-only evidence/tools under launcher/850Launcher/tools.
- Before attach, resolve current authoritative Lin.bin2 PID and current HP/MP Gauge objects from fresh runtime evidence; do not reuse stale absolute addresses after process restart.
- Known class evidence: Gauge field +0x1C0 is dynamic fill/state candidate. Setter candidate RVA 0x00877D50 writes this+0x1C0 <- arg1 and this+0x1C4 <- arg2. Ratio caller RVA 0x00C5BF80 shows IMUL then IDIV before setter. Treat as candidates only.
- Create one automated launcher script, preferred path:
  launcher/850Launcher/tools/run_850_hpmp_dynamic_watch.ps1
- Script responsibilities:
  1) verify client SHA256 and authoritative process path;
  2) refresh Gauge object addresses using existing owner/named-object scan if needed;
  3) compute HP_FIELD=HP_GAUGE+0x1C0 and MP_FIELD=MP_GAUGE+0x1C0;
  4) attach debugger read-only and set hardware WRITE watchpoints (4-byte if alignment permits; otherwise safe supported width);
  5) auto-log on each hit: tag HP_FIELD/MP_FIELD, timestamp, PID, EIP/RVA, registers, stack, and short call stack; auto-continue;
  6) prompt only these user actions: HP phase = make HP change; MP phase = make MP change; final idle;
  7) stop capture automatically after sufficient distinct hits or timeout;
  8) normalize hits by writer RVA/function and correlate HP-only / MP-only / shared writers;
  9) emit report to I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_dynamic_watch.txt.
- Keep debugger interaction scripted/non-interactive as far as supported.
- If using cdb/windbg, use command file/logging. If using x32dbg, generate and run debugger script/commands; do not require user to type debugger commands manually.
- If elevation is required, request/launch elevation once; no repeated manual debugger steps.
- Update repo with scripts/docs only after static validation.

DO NOT
- NO WriteProcessMemory.
- NO memory/code patching, freeze, NOP, packet injection, raw socket send, game-state mutation, or breakpoint handlers that modify target state.
- NO stale absolute runtime addresses across PID restart.
- NO manual address entry by user.
- NO repo-wide/archive scan.
- NO destructive Git operations.
- SUBAGENTS=0.
- CONTEXT_EXPANSION=NO.

VALIDATE
- PowerShell syntax parse PASS for new script.
- SHA authority gate present.
- Process path authority gate present.
- MEMORY_WRITE=NO in generated report.
- Watchpoint width/alignment validated before debugger launch.
- User interaction reduced to game actions only.
- Capture must distinguish HP_FIELD vs MP_FIELD hits.
- PASS gate requires at least one real runtime hit for HP and one for MP, with EIP/RVA + register snapshot + call stack. If runtime capture cannot be performed by agent environment, return READY_FOR_USER_RUN, not PASS.

FINAL
Return A2A only:
STATUS=<PASS|READY_FOR_USER_RUN|BLOCKED>
DEBUGGER=<cdb|windbg|x32dbg|NONE>
SCRIPT=<path|NONE>
OUTPUT=<path|NONE>
HP_HITS=<n|UNKNOWN>
MP_HITS=<n|UNKNOWN>
HP_WRITER_RVAS=<csv|UNKNOWN>
MP_WRITER_RVAS=<csv|UNKNOWN>
SHARED_WRITER_RVAS=<csv|NONE|UNKNOWN>
MEMORY_WRITE=NO
USER_ACTIONS=<exact minimal in-game actions only>
BLOCKER=<none|text>
NEXT=<one concise step>