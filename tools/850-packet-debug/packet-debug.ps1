param(
    [switch]$BuildOnly,
    [string]$LogPath
)

$ErrorActionPreference = 'Stop'

$here = Split-Path -Parent $MyInvocation.MyCommand.Path
$root = (Resolve-Path (Join-Path $here '..\..')).Path
$src = Join-Path $here 'PacketDebugBootstrap.java'
$buildRoot = Join-Path $root '.build850\packet-debug'
$classes = Join-Path $buildRoot 'classes'
$bootstrapClass = Join-Path $classes 'PacketDebugBootstrap.class'
$runtimeClasses = Join-Path $root '.build850\classes'
$baseJar = Join-Path $root '.build850\cache\850-dev-base.jar'
$libWildcard = Join-Path $root 'lib\*'

if (-not $LogPath) {
    $LogPath = Join-Path $root 'logs\850-packet-debug.log'
}

if (-not (Test-Path $src)) {
    throw "Missing bootstrap source: $src"
}
if (-not (Test-Path $baseJar)) {
    throw "Missing dev baseline jar: $baseJar. Run build850.ps1 once first."
}
if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    throw 'javac not found in PATH'
}
if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    throw 'java not found in PATH'
}

New-Item -ItemType Directory -Force -Path $classes | Out-Null

$needBuild = -not (Test-Path $bootstrapClass)
if (-not $needBuild) {
    $needBuild = (Get-Item $src).LastWriteTimeUtc -gt (Get-Item $bootstrapClass).LastWriteTimeUtc
}

if ($needBuild) {
    & javac -source 8 -target 8 -encoding UTF-8 -d $classes $src
    if ($LASTEXITCODE -ne 0) {
        throw "Packet debug bootstrap build failed: exit=$LASTEXITCODE"
    }
    'PACKET_DEBUG_BUILD=PASS'
}
else {
    'PACKET_DEBUG_BUILD=NOOP'
}

if ($BuildOnly) {
    "PACKET_DEBUG_CLASS=$bootstrapClass"
    exit 0
}

$running = Get-CimInstance Win32_Process |
    Where-Object {
        $_.Name -eq 'java.exe' -and
        $_.CommandLine -and
        $_.CommandLine -like "*$root\.build850*"
    }

if ($running) {
    $pids = ($running | ForEach-Object { $_.ProcessId }) -join ','
    throw "850 dev server already running (PID=$pids). Stop it before packet-debug.ps1."
}

$logDir = Split-Path -Parent $LogPath
if ($logDir) {
    New-Item -ItemType Directory -Force -Path $logDir | Out-Null
}

$cp = "$classes;$runtimeClasses;$baseJar;$libWildcard"

"PACKET_DEBUG=ON"
"PACKET_DEBUG_LOG=$LogPath"
"PACKET_DEBUG_CLASSPATH=$cp"

Push-Location $root
try {
    & java -noverify -cp $cp PacketDebugBootstrap 2>&1 |
        Tee-Object -FilePath $LogPath
    $exitCode = $LASTEXITCODE
}
finally {
    Pop-Location
}

exit $exitCode
