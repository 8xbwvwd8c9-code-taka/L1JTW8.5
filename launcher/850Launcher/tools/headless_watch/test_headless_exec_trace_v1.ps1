$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
$source = Join-Path $root 'HeadlessExecTrace850V1.cs'
$build = Join-Path $root 'build_headless_exec_trace.ps1'
$runner = Join-Path (Split-Path $root -Parent) 'run_850_useitem_exec_trace_v19.ps1'

if (-not (Test-Path -LiteralPath $source)) { throw "RED: missing $source" }
if (-not (Test-Path -LiteralPath $build)) { throw "RED: missing $build" }
if (-not (Test-Path -LiteralPath $runner)) { throw "RED: missing $runner" }

$text = Get-Content -LiteralPath $source -Raw
if ($text -match '(?i)WriteProcessMemory\s*\(') { throw 'Safety failure: WriteProcessMemory found.' }
if ($text -match '(?i)VirtualProtectEx\s*\(') { throw 'Safety failure: VirtualProtectEx found.' }
if ($text -match '(?i)CreateRemoteThread\s*\(') { throw 'Safety failure: CreateRemoteThread found.' }
if ($text -notmatch 'MODE=HEADLESS_X86_HARDWARE_EXEC_TRACE_V1') { throw 'Missing execution-trace mode marker.' }
if ($text -notmatch 'TARGET_MEMORY_WRITE=NO') { throw 'Missing no-write marker.' }
if ($text -notmatch 'PACKET_SEND=NO') { throw 'Missing no-send marker.' }
if ($text -notmatch 'context\.Dr7 \|= \(1u << \(i \* 2\)\)') { throw 'Missing local DRx enable.' }
if ($text -match '0xDu << \(16 \+ \(i \* 4\)\)') { throw 'Write-watch DR7 mode leaked into execution tracer.' }

$runnerText = Get-Content -LiteralPath $runner -Raw
if ($runnerText -notmatch 'chigamec\.dll\+0x0000100C') { throw 'Missing bounded chigamec target.' }
if ($runnerText -notmatch 'Lineage\.exe\+0x00084110') { throw 'Missing bounded Lineage target.' }
if ($runnerText -match '(?i)WriteProcessMemory|send\s*\(') { throw 'Runner contains forbidden active behavior.' }

Write-Host 'STATUS=PASS_STATIC_SAFETY_TEST'
Write-Host 'TARGETS=chigamec.dll+0x0000100C;Lineage.exe+0x00084110'
Write-Host 'TARGET_MEMORY_WRITE=NO'
Write-Host 'PACKET_SEND=NO'
