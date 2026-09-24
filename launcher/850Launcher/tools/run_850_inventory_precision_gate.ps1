param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputRoot = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [int]$WatchTimeoutSec = 180,
    [int]$WatchMaxHits = 24
)

$ErrorActionPreference = 'Stop'
if (Get-Variable -Name PSNativeCommandUseErrorActionPreference -ErrorAction SilentlyContinue) {
    $PSNativeCommandUseErrorActionPreference = $false
}
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

if (-not (Test-Path -LiteralPath $ClientPath)) {
    Write-Host 'STATUS=CLIENT_NOT_FOUND'
    Write-Host "CLIENT=$ClientPath"
    exit 2
}
$full = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$matches = @()
foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
    try {
        if ($p.HasExited -or -not $p.MainModule) { continue }
        if ([string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName), $full, [StringComparison]::OrdinalIgnoreCase)) {
            $matches += $p
        }
    } catch { }
}
if ($matches.Count -eq 0) {
    Write-Host 'STATUS=CLIENT_RUNNING_NO'
    Write-Host 'NEXT=Start the authoritative Lin.bin2 and stop at login/character selection if possible, then rerun.'
    exit 3
}
if ($matches.Count -ne 1) { throw "Expected exactly one authoritative Lin.bin2 process; found $($matches.Count)." }

$proc = $matches[0]
$processStartUtc = $proc.StartTime.ToUniversalTime()
if (-not (Test-Path -LiteralPath $OutputRoot)) { New-Item -ItemType Directory -Force -Path $OutputRoot | Out-Null }

$v4c = Join-Path $PSScriptRoot 'run_850_inventory_vtable_rtti_v4c.ps1'
$v4b = Join-Path $PSScriptRoot 'run_850_inventory_root_global_xref_v4b.ps1'
$watch = Join-Path $PSScriptRoot 'run_850_inventory_root_global_watch.ps1'
$review = Join-Path $PSScriptRoot 'review_850_inventory_root_global_watch.ps1'
$correlate = Join-Path $PSScriptRoot 'correlate_850_inventory_root_watch_v4b.ps1'
foreach ($path in @($v4c,$v4b,$watch,$review,$correlate)) {
    if (-not (Test-Path -LiteralPath $path)) { throw "Missing precision-gate tool: $path" }
}

$v4cOut = Join-Path $OutputRoot '850_inventory_vtable_rtti_v4c.txt'
$v4bOut = Join-Path $OutputRoot '850_inventory_root_global_xref_v4b.txt'
$watchOut = Join-Path $OutputRoot '850_inventory_root_global_watch.txt'
$reviewOut = Join-Path $OutputRoot '850_inventory_root_global_watch_review.txt'
$correlationOut = Join-Path $OutputRoot '850_inventory_root_watch_v4b_correlation.txt'
$summaryOut = Join-Path $OutputRoot '850_inventory_precision_gate_summary.txt'

function Stamp-Identity([string]$Path, [string]$Gate) {
    if (-not (Test-Path -LiteralPath $Path)) { throw "Cannot stamp missing output: $Path" }
    $stamp = @(
        '',
        '[RUN_IDENTITY]',
        "GATE=$Gate",
        "PID=$($proc.Id)",
        "PROCESS_START_UTC=$($processStartUtc.ToString('o'))",
        "CLIENT_SHA256=$sha",
        'CLIENT_AUTHORITY=1',
        'MEMORY_WRITE=NO'
    )
    $appendText = ($stamp -join [Environment]::NewLine) + [Environment]::NewLine
    [IO.File]::AppendAllText($Path,$appendText,[Text.UTF8Encoding]::new($false))
}

function Assert-SameProcessIdentity {
    $live = Get-Process -Id $proc.Id -ErrorAction Stop
    $liveStart = $live.StartTime.ToUniversalTime()
    $livePath = [IO.Path]::GetFullPath($live.MainModule.FileName)
    if ($liveStart -ne $processStartUtc -or
        -not [string]::Equals($livePath, $full, [StringComparison]::OrdinalIgnoreCase)) {
        throw 'Lin.bin2 process identity changed during the precision gate; rerun from the start.'
    }
}

function Write-Summary(
    [string]$Status,
    [int]$WatchExit,
    [string]$Next
) {
    $lines = @(
        "TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))",
        'MODE=850_INVENTORY_PRECISION_GATE_SUMMARY',
        "CLIENT=$full",
        "CLIENT_SHA256=$sha",
        'CLIENT_AUTHORITY=1',
        "PID=$($proc.Id)",
        "PROCESS_START_UTC=$($processStartUtc.ToString('o'))",
        "STATUS=$Status",
        "WATCH_EXIT=$WatchExit",
        "V4C=$v4cOut",
        "V4B=$v4bOut",
        "WATCH=$watchOut",
        "REVIEW=$reviewOut",
        "CORRELATION=$correlationOut",
        'FORMAL_WP5=NOT_YET',
        'FORMAL_WP6=NOT_YET',
        'OWNER_PROMOTION=NOT_YET',
        'RAW_E8_USED=NO',
        'HEAP_SCAN=NO',
        'MEM_PRIVATE_SCAN=NO',
        'MEMORY_WRITE=NO',
        "NEXT=$Next"
    )
    [IO.File]::WriteAllLines($summaryOut, $lines, [Text.UTF8Encoding]::new($false))
}

Write-Host 'STATUS=PRECISION_GATE_STATIC_CONTEXT'
Write-Host "CLIENT=$full"
Write-Host "CLIENT_SHA256=$sha"
Write-Host "PID=$($proc.Id)"
Write-Host "PROCESS_START_UTC=$($processStartUtc.ToString('o'))"
Write-Host 'ORDER=V4C_RTTI,V4B_EXACT_GLOBAL_REFS,HARDWARE_WRITE_WATCH,REVIEW,CORRELATION'
Write-Host 'RAW_E8_CALLER_HEURISTIC=NOT_USED'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'

Write-Host 'BEGIN=V4C_RTTI'
& $v4c -ClientPath $full -OutputPath $v4cOut
Stamp-Identity $v4cOut 'V4C_RTTI'
Write-Host "END=V4C_RTTI OUTPUT=$v4cOut"

Assert-SameProcessIdentity

Write-Host 'BEGIN=V4B_EXACT_GLOBAL_REFS'
& $v4b -ClientPath $full -OutputPath $v4bOut
Stamp-Identity $v4bOut 'V4B_ROOT_GLOBAL'
Write-Host "END=V4B_EXACT_GLOBAL_REFS OUTPUT=$v4bOut"

Assert-SameProcessIdentity

# Transient files must belong to this run. Historical watch reports are archived by the watcher itself.
foreach ($transient in @($watchOut,$reviewOut,$correlationOut,$summaryOut)) {
    if (Test-Path -LiteralPath $transient) { Remove-Item -LiteralPath $transient -Force }
}

Write-Host 'BEGIN=ROOT_GLOBAL_HARDWARE_WATCH'
Write-Host 'ACTION=When the watcher reports READY, use normal game flow: enter/re-enter the world, open/close inventory, and if practical logout/relogin without closing Lin.bin2.'
$pwsh = (Get-Process -Id $PID).Path
& $pwsh -NoProfile -ExecutionPolicy Bypass -File $watch -ClientPath $full -OutputPath $watchOut -TimeoutSec $WatchTimeoutSec -MaxHits $WatchMaxHits
$watchExit = [int]$LASTEXITCODE
Write-Host "END=ROOT_GLOBAL_HARDWARE_WATCH EXIT=$watchExit OUTPUT=$watchOut"

$pipelineStatus = 'WATCH_TOOL_FAILED'
$next = 'Inspect the watch report/console status and fix only the targeted watcher; do not widen scan scope.'

if ($watchExit -eq 0 -or $watchExit -eq 5) {
    if (-not (Test-Path -LiteralPath $watchOut)) {
        $pipelineStatus = 'WATCH_OUTPUT_MISSING'
        $next = 'Rerun the precision gate; a valid watch exit must produce a fresh report.'
    }
    else {
        Write-Host 'BEGIN=ROOT_WATCH_REVIEW'
        & $review -InputPath $watchOut -OutputPath $reviewOut
        Write-Host "END=ROOT_WATCH_REVIEW OUTPUT=$reviewOut"

        Write-Host 'BEGIN=ROOT_WATCH_V4B_CORRELATION'
        & $correlate -WatchPath $watchOut -V4bPath $v4bOut -OutputPath $correlationOut
        Write-Host "END=ROOT_WATCH_V4B_CORRELATION OUTPUT=$correlationOut"

        if ($watchExit -eq 0) {
            $pipelineStatus = 'PASS_PRECISION_EVIDENCE_CAPTURED'
            $next = 'Return the correlation report; repeat across a fresh client process before any owner promotion.'
        }
        else {
            $pipelineStatus = 'PASS_PRECISION_GATE_NO_WRITE_OBSERVED'
            $next = 'Rerun from login/character selection and exercise enter-world/logout-relogin while the watch remains active; do not widen scan scope.'
        }
    }
}
else {
    Write-Host "STATUS=WATCH_TOOL_FAILED EXIT=$watchExit"
}

Write-Summary -Status $pipelineStatus -WatchExit $watchExit -Next $next

Write-Host ''
Write-Host '[PRECISION_GATE_SUMMARY]'
Write-Host "STATUS=$pipelineStatus"
Write-Host "V4C=$v4cOut"
Write-Host "V4B=$v4bOut"
Write-Host "WATCH=$watchOut"
Write-Host "REVIEW=$reviewOut"
Write-Host "CORRELATION=$correlationOut"
Write-Host "SUMMARY=$summaryOut"
Write-Host "WATCH_EXIT=$watchExit"
Write-Host 'FORMAL_WP5=NOT_YET'
Write-Host 'FORMAL_WP6=NOT_YET'
Write-Host 'OWNER_PROMOTION=NOT_YET'
Write-Host 'RAW_E8_USED=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
Write-Host "NEXT=$next"

if ($pipelineStatus -eq 'WATCH_TOOL_FAILED' -or $pipelineStatus -eq 'WATCH_OUTPUT_MISSING') { exit 10 }
exit 0
