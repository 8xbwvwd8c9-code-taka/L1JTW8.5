param(
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$V8RawPath = Join-Path $OutputDir '850_inventory_invwin_methods_v8_raw.txt'
$V13Path = Join-Path $OutputDir '850_inventory_invwin_child_objects_v13.txt'
$OutPath = Join-Path $OutputDir '850_inventory_invwin_child_calls_v14.txt'
$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$DecoderPath = Join-Path $DecoderDir 'decode_850_inventory_invwin_child_calls_v14.py'
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
if (-not (Test-Path -LiteralPath $V13Path)) { throw "Missing V13 capture: $V13Path" }
if (-not (Test-Path -LiteralPath $DecoderPath)) { throw "Missing V14 decoder: $DecoderPath" }

$v8Meta = Read-KvFile $V8RawPath
$v13Meta = Read-KvFile $V13Path
if ($v8Meta['CLIENT_SHA256'] -ne $ExpectedSha256 -or $v8Meta['CLIENT_AUTHORITY'] -ne '1') {
    throw 'V14 blocked: V8 authority gate failed.'
}
if ($v13Meta['CLIENT_SHA256'] -ne $ExpectedSha256 -or $v13Meta['CLIENT_AUTHORITY'] -ne '1' -or $v13Meta['ROOT_GRAPH_REVALIDATED'] -ne 'PASS') {
    throw 'V14 blocked: V13 authority/root gate failed.'
}
if ($v8Meta['HEAP_SCAN'] -ne 'NO' -or $v8Meta['MEM_PRIVATE_SCAN'] -ne 'NO' -or $v8Meta['MEMORY_WRITE'] -ne 'NO') {
    throw 'V14 blocked: V8 safety metadata mismatch.'
}
if ($v13Meta['HEAP_SCAN'] -ne 'NO' -or $v13Meta['MEM_PRIVATE_SCAN'] -ne 'NO' -or $v13Meta['MEMORY_WRITE'] -ne 'NO') {
    throw 'V14 blocked: V13 safety metadata mismatch.'
}

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $PythonPath)) {
    if (-not (Test-Path -LiteralPath $SetupPath)) { throw "Missing decoder setup: $SetupPath" }
    & $SetupPath
}
if (-not (Test-Path -LiteralPath $PythonPath)) { throw "Decoder Python missing after setup: $PythonPath" }
$version = (& $PythonPath -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($version -ne '5.0.9') { throw "Unexpected Capstone package version: $version" }

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
& $PythonPath $DecoderPath --v8-raw $V8RawPath --v13 $V13Path --output $OutPath
if ($LASTEXITCODE -ne 0) { throw "V14 decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutPath)) { throw "V14 output missing: $OutPath" }

Write-Host 'STATUS=PASS_INVWIN_CHILD_CALLS_V14_EXECUTED'
Write-Host "OUTPUT=$OutPath"
Write-Host 'RUNTIME_ATTACH=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
