param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputRoot = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [int]$WatchTimeoutSec = 180,
    [int]$WatchMaxHits = 24,
    [switch]$PrepareDecoderOnly
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

$decoderDir = Join-Path $PSScriptRoot 'decoder'
$decoderSetup = Join-Path $decoderDir 'setup_capstone_decoder.ps1'
$decoderRun = Join-Path $decoderDir 'run_850_inventory_watch_decoder.ps1'
if (-not (Test-Path -LiteralPath $decoderSetup)) { throw "Missing decoder setup: $decoderSetup" }
if (-not (Test-Path -LiteralPath $decoderRun)) { throw "Missing decoder runner: $decoderRun" }

# Prepare and self-test the decoder before taking a client process identity.
& $decoderSetup
if ($PrepareDecoderOnly) {
    Write-Host 'STATUS=PASS_DECODER_PREPARED_ONLY'
    Write-Host 'NEXT=Start the authoritative Lin.bin2 and rerun without -PrepareDecoderOnly.'
    exit 0
}

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
    Write-Host 'NEXT=Start the authoritative Lin.bin2, then rerun this precision gate.'
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
$correlate = Join-Path $PSScriptRoot 'correlate_850_inventory_root_watch_decoder_v2.ps1'
foreach ($path in @($v4c,$v4b,$watch,$review,$correlate)) {
    if (-not (Test-Path -LiteralPath $path)) { throw "Missing precision-gate tool: $path" }
}

$v4cOut = Join-Path $OutputRoot '850_inventory_vtable_rtti_v4c.txt'
$v4bOut = Join-Path $OutputRoot '850_inventory_root_global_xref_v4b.txt'
$watchOut = Join-Path $OutputRoot '850_inventory_root_global_watch.txt'
$decoderOut = Join-Path $OutputRoot '850_inventory_root_global_watch_capstone.txt'
$reviewOut = Join-Path $OutputRoot '850_inventory_root_global_watch_review.txt'
$correlationOut = Join-Path $OutputRoot '850_inventory_root_watch_decoder_correlation_v2.txt'

# Never let stale evidence from a previous process enter this run.
foreach ($p in @($watchOut,$decoderOut,$reviewOut,$correlationOut)) {
    if (Test-Path -LiteralPath $p) { Remove-Item -LiteralPath $p -Force }
}

function Stamp-Identity([string]$Path,[string]$Gate) {
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

Write-Host 'STATUS=PRECISION_GATE_V2_STATIC_CONTEXT'
Write-Host "CLIENT=$full"
Write-Host "CLIENT_SHA256=$sha"
Write-Host "PID=$($proc.Id)"
Write-Host "PROCESS_START_UTC=$($processStartUtc.ToString('o'))"
Write-Host 'ORDER=V4C_RTTI,V4B_EXACT_GLOBAL_REFS,HARDWARE_WRITE_WATCH,CAPSTONE_DECODER,REVIEW,DECODER_CORRELATION'
Write-Host 'CAPSTONE_VERSION=5.0.9'
Write-Host 'RAW_E8_CALLER_HEURISTIC=NOT_USED'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'

Write-Host 'BEGIN=V4C_RTTI'
& $v4c -ClientPath $full -OutputPath $v4cOut
Stamp-Identity $v4cOut 'V4C_RTTI'
Write-Host "END=V4C_RTTI OUTPUT=$v4cOut"

Write-Host 'BEGIN=V4B_EXACT_GLOBAL_REFS'
& $v4b -ClientPath $full -OutputPath $v4bOut
Stamp-Identity $v4bOut 'V4B_ROOT_GLOBAL'
Write-Host "END=V4B_EXACT_GLOBAL_REFS OUTPUT=$v4bOut"

$live = Get-Process -Id $proc.Id -ErrorAction Stop
if ($live.StartTime.ToUniversalTime() -ne $processStartUtc -or
    -not [string]::Equals([IO.Path]::GetFullPath($live.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)) {
    throw 'Lin.bin2 process identity changed before hardware-watch phase; rerun the precision gate.'
}

Write-Host 'BEGIN=ROOT_GLOBAL_HARDWARE_WATCH'
Write-Host 'ACTION=Use normal game lifecycle only during the watch window: world entry/re-entry, inventory open/close, and if practical logout/relogin without closing Lin.bin2.'
$pwsh = (Get-Process -Id $PID).Path
& $pwsh -NoProfile -ExecutionPolicy Bypass -File $watch -ClientPath $full -OutputPath $watchOut -TimeoutSec $WatchTimeoutSec -MaxHits $WatchMaxHits
$watchExit = $LASTEXITCODE
Write-Host "END=ROOT_GLOBAL_HARDWARE_WATCH EXIT=$watchExit OUTPUT=$watchOut"

if ($watchExit -ne 0 -and $watchExit -ne 5) {
    Write-Host 'STATUS=WATCH_TOOL_FAILURE'
    Write-Host 'NEXT=Fix only hardware-watch attach/build/elevation; do not use stale reports or widen scan scope.'
    exit $watchExit
}
if (-not (Test-Path -LiteralPath $watchOut)) { throw 'Hardware-watch run completed without a report.' }

Write-Host 'BEGIN=CAPSTONE_DECODER'
& $decoderRun -InputPath $watchOut -OutputPath $decoderOut -SkipSetup
Write-Host "END=CAPSTONE_DECODER OUTPUT=$decoderOut"

Write-Host 'BEGIN=ROOT_WATCH_REVIEW'
& $review -InputPath $watchOut -OutputPath $reviewOut
Write-Host "END=ROOT_WATCH_REVIEW OUTPUT=$reviewOut"

Write-Host 'BEGIN=DECODER_CORRELATION_V2'
& $correlate -WatchPath $watchOut -DecoderPath $decoderOut -V4bPath $v4bOut -OutputPath $correlationOut
Write-Host "END=DECODER_CORRELATION_V2 OUTPUT=$correlationOut"

Write-Host ''
Write-Host '[PRECISION_GATE_V2_SUMMARY]'
Write-Host "V4C=$v4cOut"
Write-Host "V4B=$v4bOut"
Write-Host "WATCH=$watchOut"
Write-Host "DECODER=$decoderOut"
Write-Host "REVIEW=$reviewOut"
Write-Host "CORRELATION=$correlationOut"
Write-Host "WATCH_EXIT=$watchExit"
Write-Host 'FORMAL_WP5=NOT_YET'
Write-Host 'FORMAL_WP6=NOT_YET'
Write-Host 'OWNER_PROMOTION=NOT_YET'
Write-Host 'RAW_E8_USED=NO'
Write-Host 'DECODER_ALIGNED_PROMOTION=YES'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
Write-Host 'NEXT=Return only the decoder-correlation V2 report. Strong candidate still requires a fresh-process repeat before promotion.'
