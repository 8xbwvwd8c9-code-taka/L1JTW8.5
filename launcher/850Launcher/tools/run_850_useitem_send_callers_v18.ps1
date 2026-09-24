param(
    [string]$EvidencePath = 'I:\8.50c客服端\auto_static_game_network_evidence.txt',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'

$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$DecoderPath = Join-Path $DecoderDir 'decode_850_useitem_send_callers_v18.py'
$SetupPath = Join-Path $DecoderDir 'setup_capstone_decoder.ps1'
$PythonPath = Join-Path $DecoderDir '.venv\Scripts\python.exe'
$OutPath = Join-Path $OutputDir '850_useitem_send_callers_v18.txt'

if (-not (Test-Path -LiteralPath $EvidencePath)) {
    throw "Missing static network evidence: $EvidencePath"
}
if (-not (Test-Path -LiteralPath $DecoderPath)) {
    throw "Missing V18 decoder: $DecoderPath"
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

& $PythonPath $DecoderPath --evidence $EvidencePath --output $OutPath
if ($LASTEXITCODE -ne 0) {
    throw "V18 decoder failed: exit=$LASTEXITCODE"
}
if (-not (Test-Path -LiteralPath $OutPath)) {
    throw "V18 output missing: $OutPath"
}

Write-Host 'STATUS=PASS_USEITEM_SEND_CALLERS_V18_EXECUTED'
Write-Host "OUTPUT=$OutPath"
Write-Host 'RUNTIME_SCAN=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
Write-Host 'SEND_PACKET=NO'
