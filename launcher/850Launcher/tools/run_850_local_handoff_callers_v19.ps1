param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'

$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$V18Decoder = Join-Path $DecoderDir 'decode_850_session_wrapper_imports_v18.py'
$V19Decoder = Join-Path $DecoderDir 'decode_850_local_handoff_callers_v19.py'
$SetupPath = Join-Path $DecoderDir 'setup_capstone_decoder.ps1'
$PythonPath = Join-Path $DecoderDir '.venv\Scripts\python.exe'
$V18Out = Join-Path $OutputDir '850_session_wrapper_imports_v18.txt'
$V19Out = Join-Path $OutputDir '850_local_handoff_callers_v19.txt'

if (-not (Test-Path -LiteralPath $ClientPath)) {
    throw "Missing authoritative client: $ClientPath"
}
if (-not (Test-Path -LiteralPath $V18Decoder)) {
    throw "Missing V18 local import decoder: $V18Decoder"
}
if (-not (Test-Path -LiteralPath $V19Decoder)) {
    throw "Missing V19 classifier: $V19Decoder"
}

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $PythonPath)) {
    if (-not (Test-Path -LiteralPath $SetupPath)) {
        throw "Missing decoder setup: $SetupPath"
    }
    & $SetupPath
}
if (-not (Test-Path -LiteralPath $PythonPath)) {
    throw "Decoder Python missing after setup: $PythonPath"
}

$version = (& $PythonPath -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($version -ne '5.0.9') {
    throw "Unexpected Capstone package version: $version"
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null

& $PythonPath $V18Decoder --client $ClientPath --output $V18Out
if ($LASTEXITCODE -ne 0) {
    throw "V18 local import trace failed: exit=$LASTEXITCODE"
}
if (-not (Test-Path -LiteralPath $V18Out)) {
    throw "V18 output missing: $V18Out"
}

& $PythonPath $V19Decoder --client $ClientPath --evidence $V18Out --output $V19Out
if ($LASTEXITCODE -ne 0) {
    throw "V19 local handoff classifier failed: exit=$LASTEXITCODE"
}
if (-not (Test-Path -LiteralPath $V19Out)) {
    throw "V19 output missing: $V19Out"
}

$decision = Get-Content -LiteralPath $V19Out | Where-Object { $_ -like 'STATUS=*' } | Select-Object -Last 1
$targets = Get-Content -LiteralPath $V19Out | Where-Object { $_ -like 'TARGET=*' }

Write-Host 'STATUS=PASS_LOCAL_HANDOFF_V19_EXECUTED'
Write-Host "V18_OUTPUT=$V18Out"
Write-Host "V19_OUTPUT=$V19Out"
Write-Host "V19_DECISION=$decision"
Write-Host "EXACT_TARGET_COUNT=$($targets.Count)"
$targets | ForEach-Object { Write-Host $_ }
Write-Host 'RUNTIME_ATTACH=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
Write-Host 'PACKET_SEND=NO'
