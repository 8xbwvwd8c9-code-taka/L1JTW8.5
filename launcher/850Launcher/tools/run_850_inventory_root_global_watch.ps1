param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [int]$TimeoutSec = 180,
    [int]$MaxHits = 24,
    [switch]$Elevated
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
[uint64]$RootGlobalRva = 0x012BCEE8

function Test-IsAdministrator {
    $identity = [Security.Principal.WindowsIdentity]::GetCurrent()
    $principal = [Security.Principal.WindowsPrincipal]::new($identity)
    return $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

function Quote-NativeArg([string]$Value) {
    return '"' + ($Value -replace '"', '\"') + '"'
}

if (-not (Test-Path -LiteralPath $ClientPath)) {
    Write-Host 'STATUS=CLIENT_NOT_FOUND'
    Write-Host "CLIENT=$ClientPath"
    exit 2
}

$fullClient = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $fullClient -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) {
    throw "Client authority mismatch: $sha"
}

if (-not (Test-IsAdministrator)) {
    if ($Elevated) {
        Write-Host 'STATUS=BLOCKED_ELEVATION_FAILED'
        Write-Host 'NEXT=Run the same script from an elevated PowerShell session.'
        exit 3
    }

    $pwsh = (Get-Process -Id $PID).Path
    $argList = @(
        '-NoProfile',
        '-ExecutionPolicy', 'Bypass',
        '-File', (Quote-NativeArg $PSCommandPath),
        '-ClientPath', (Quote-NativeArg $fullClient),
        '-OutputPath', (Quote-NativeArg $OutputPath),
        '-TimeoutSec', $TimeoutSec.ToString([Globalization.CultureInfo]::InvariantCulture),
        '-MaxHits', $MaxHits.ToString([Globalization.CultureInfo]::InvariantCulture),
        '-Elevated'
    )

    try {
        Write-Host 'STATUS=REQUESTING_ONE_TIME_UAC'
        $child = Start-Process -FilePath $pwsh -Verb RunAs -ArgumentList $argList -Wait -PassThru
        exit $child.ExitCode
    }
    catch {
        Write-Host 'STATUS=BLOCKED_UAC_CANCELLED_OR_FAILED'
        Write-Host "ERROR=$($_.Exception.Message)"
        exit 4
    }
}

$matches = New-Object System.Collections.Generic.List[object]
foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
    try {
        if ($p.HasExited -or -not $p.MainModule) { continue }
        $path = [IO.Path]::GetFullPath($p.MainModule.FileName)
        if ([string]::Equals($path, $fullClient, [StringComparison]::OrdinalIgnoreCase)) {
            $matches.Add($p)
        }
    }
    catch { }
}

if ($matches.Count -eq 0) {
    Write-Host 'STATUS=CLIENT_RUNNING_NO'
    Write-Host "CLIENT=$fullClient"
    Write-Host 'NEXT=Start the authoritative Lin.bin2, then rerun before or during login.'
    exit 5
}
if ($matches.Count -ne 1) {
    Write-Host 'STATUS=BLOCKED_MULTIPLE_CLIENTS'
    Write-Host "MATCHES=$($matches.Count)"
    Write-Host 'NEXT=Keep only one authoritative Lin.bin2 process running.'
    exit 6
}

$proc = $matches[0]
$module = $proc.MainModule
$startUtc = $proc.StartTime.ToUniversalTime()
[uint64]$moduleBase64 = [uint64]$module.BaseAddress.ToInt64()
[uint64]$moduleSize64 = [uint64]$module.ModuleMemorySize
[uint64]$moduleEnd64 = $moduleBase64 + $moduleSize64
[uint64]$watchVa64 = $moduleBase64 + $RootGlobalRva

if ($moduleBase64 -gt [uint32]::MaxValue -or $moduleSize64 -gt [uint32]::MaxValue -or $watchVa64 -gt [uint32]::MaxValue) {
    throw 'Target/module address does not fit the required x86 address space.'
}
if ($watchVa64 -lt $moduleBase64 -or ($watchVa64 + 4) -gt $moduleEnd64) {
    throw ('ROOT_GLOBAL_RVA 0x{0:X8} is outside the loaded Lin.bin2 module.' -f $RootGlobalRva)
}
if (($watchVa64 -band 3) -ne 0) {
    throw ('ROOT global watch VA 0x{0:X8} is not 4-byte aligned.' -f $watchVa64)
}

$helperDir = Join-Path $PSScriptRoot 'headless_watch'
$buildScript = Join-Path $helperDir 'build_headless_watch.ps1'
$helperExe = Join-Path $helperDir 'bin\HeadlessWatch850V2.exe'
if (-not (Test-Path -LiteralPath $buildScript)) { throw "Missing build script: $buildScript" }

& $buildScript -OutputPath $helperExe
if ($LASTEXITCODE -ne 0 -and $LASTEXITCODE -ne $null) {
    throw "Headless watch build failed: exit=$LASTEXITCODE"
}
if (-not (Test-Path -LiteralPath $helperExe)) { throw "Missing helper after build: $helperExe" }

# Re-check identity after compilation. Never reuse a PID/start identity if the client restarted.
$live = Get-Process -Id $proc.Id -ErrorAction Stop
$liveStartUtc = $live.StartTime.ToUniversalTime()
$livePath = [IO.Path]::GetFullPath($live.MainModule.FileName)
if ($liveStartUtc -ne $startUtc -or -not [string]::Equals($livePath, $fullClient, [StringComparison]::OrdinalIgnoreCase)) {
    throw 'Lin.bin2 process identity changed while preparing the watcher; rerun the script.'
}

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}

$debugModeEntered = $false
$seDebugStatus = 'NOT_ENABLED'
try {
    [Diagnostics.Process]::EnterDebugMode()
    $debugModeEntered = $true
    $seDebugStatus = 'ENABLED_FOR_RUN'
}
catch {
    $seDebugStatus = 'ENABLE_FAILED:' + $_.Exception.GetType().Name
}

Write-Host 'STATUS=READY_ROOT_GLOBAL_WATCH'
Write-Host "PID=$($proc.Id)"
Write-Host "PROCESS_START_UTC=$($startUtc.ToString('o'))"
Write-Host ('MODULE_BASE=0x{0:X8}' -f $moduleBase64)
Write-Host ('ROOT_GLOBAL_RVA=0x{0:X8}' -f $RootGlobalRva)
Write-Host ('ROOT_GLOBAL_VA=0x{0:X8}' -f $watchVa64)
Write-Host "SEDEBUG=$seDebugStatus"
Write-Host 'ACTION=During the watch window, use normal game flow only: enter/re-enter the world and open/close inventory. A logout/relogin can help expose teardown + construction writes.'
Write-Host 'TARGET_MEMORY_WRITE=NO'

$helperArgs = @(
    '--pid', $proc.Id.ToString([Globalization.CultureInfo]::InvariantCulture),
    '--base', ('0x{0:X8}' -f $moduleBase64),
    '--size', ('0x{0:X8}' -f $moduleSize64),
    '--start-utc', $startUtc.ToString('o'),
    '--watch', ('ROOT_GLOBAL=0x{0:X8}' -f $watchVa64),
    '--timeout-sec', $TimeoutSec.ToString([Globalization.CultureInfo]::InvariantCulture),
    '--max-hits', $MaxHits.ToString([Globalization.CultureInfo]::InvariantCulture),
    '--output', $OutputPath
)

$helperExit = 99
try {
    & $helperExe $helperArgs
    $helperExit = $LASTEXITCODE
}
finally {
    if ($debugModeEntered) {
        try { [Diagnostics.Process]::LeaveDebugMode() } catch { }
    }
}

$raw = if (Test-Path -LiteralPath $OutputPath) { Get-Content -LiteralPath $OutputPath -Raw } else { '' }
$header = @(
    "RUNNER_TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))",
    "CLIENT=$fullClient",
    "CLIENT_SHA256=$sha",
    'CLIENT_AUTHORITY=1',
    "PID=$($proc.Id)",
    "PROCESS_START_UTC=$($startUtc.ToString('o'))",
    ('ROOT_GLOBAL_RVA=0x{0:X8}' -f $RootGlobalRva),
    ('ROOT_GLOBAL_VA=0x{0:X8}' -f $watchVa64),
    "HELPER_EXIT=$helperExit",
    'RUNNER_ELEVATED=YES',
    "SEDEBUG=$seDebugStatus",
    'TARGET_MEMORY_WRITE=NO',
    '---HELPER---'
) -join "`r`n"
[IO.File]::WriteAllText($OutputPath, $header + "`r`n" + $raw, [Text.UTF8Encoding]::new($false))

$archiveDir = Join-Path $parent '850_inventory_root_global_watch_history'
if (-not (Test-Path -LiteralPath $archiveDir)) { New-Item -ItemType Directory -Force -Path $archiveDir | Out-Null }
$stamp = $startUtc.ToString('yyyyMMddTHHmmssfffZ')
$archivePath = Join-Path $archiveDir ("root_global_watch_{0}_pid{1}.txt" -f $stamp, $proc.Id)
Copy-Item -LiteralPath $OutputPath -Destination $archivePath -Force

Write-Host "OUTPUT=$OutputPath"
Write-Host "ARCHIVE=$archivePath"
Write-Host "HELPER_EXIT=$helperExit"
Write-Host "SEDEBUG=$seDebugStatus"
Write-Host 'TARGET_MEMORY_WRITE=NO'
if ($helperExit -eq 0) {
    Write-Host 'STATUS=PASS_OBSERVED_WRITE'
    exit 0
}
elseif ($helperExit -eq 5) {
    Write-Host 'STATUS=NO_WRITE_OBSERVED'
    exit 5
}
else {
    Write-Host 'STATUS=HEADLESS_WATCH_FAILED'
    exit $helperExit
}
