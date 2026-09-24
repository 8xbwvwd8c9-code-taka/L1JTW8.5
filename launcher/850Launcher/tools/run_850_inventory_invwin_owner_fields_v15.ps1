param(
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$V8RawPath = Join-Path $OutputDir '850_inventory_invwin_methods_v8_raw.txt'
$OutPath = Join-Path $OutputDir '850_inventory_invwin_owner_field_lifecycle_v15.txt'
$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$DecoderPath = Join-Path $DecoderDir 'decode_850_inventory_invwin_owner_fields_v15.py'
$SetupPath = Join-Path $DecoderDir 'setup_capstone_decoder.ps1'
$PythonPath = Join-Path $DecoderDir '.venv\Scripts\python.exe'

function Read-KvFile([string]$Path) {
    $m = @{}
    foreach ($line in Get-Content -LiteralPath $Path -ErrorAction Stop) {
        $i = $line.IndexOf('=')
        if ($i -le 0) { continue }
        $m[$line.Substring(0,$i).Trim()] = $line.Substring($i+1).Trim()
    }
    return $m
}

if (-not (Test-Path -LiteralPath $V8RawPath)) { throw "Missing V8 raw capture: $V8RawPath" }
if (-not (Test-Path -LiteralPath $DecoderPath)) { throw "Missing V15 decoder: $DecoderPath" }

$v8Meta = Read-KvFile $V8RawPath
if ($v8Meta['CLIENT_SHA256'] -ne $ExpectedSha256 -or $v8Meta['CLIENT_AUTHORITY'] -ne '1') {
    throw 'V15 blocked: V8 authority gate failed.'
}
if ($v8Meta['HEAP_SCAN'] -ne 'NO' -or $v8Meta['MEM_PRIVATE_SCAN'] -ne 'NO' -or $v8Meta['MEMORY_WRITE'] -ne 'NO') {
    throw 'V15 blocked: V8 safety metadata mismatch.'
}

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $PythonPath)) {
    if (-not (Test-Path -LiteralPath $SetupPath)) { throw "Missing decoder setup: $SetupPath" }
    & $SetupPath
}
if (-not (Test-Path -LiteralPath $PythonPath)) { throw "Decoder Python missing after setup: $PythonPath" }
$packageVersion = (& $PythonPath -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($packageVersion -ne '5.0.9') { throw "Unexpected Capstone package version: $packageVersion" }

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
& $PythonPath $DecoderPath --input $V8RawPath --output $OutPath
if ($LASTEXITCODE -ne 0) { throw "V15 decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutPath)) { throw "V15 output missing: $OutPath" }

Write-Host 'STATUS=PASS_INVWIN_OWNER_FIELD_LIFECYCLE_V15_EXECUTED'
Write-Host "OUTPUT=$OutPath"
Write-Host 'RUNTIME_ATTACH=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
