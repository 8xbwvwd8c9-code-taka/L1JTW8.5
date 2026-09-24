param(
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$V8Raw = Join-Path $OutputDir '850_inventory_invwin_methods_v8_raw.txt'
$V13 = Join-Path $OutputDir '850_inventory_invwin_child_objects_v13.txt'
$Out = Join-Path $OutputDir '850_inventory_invwin_child_calls_v14.txt'
$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$Decoder = Join-Path $DecoderDir 'decode_850_inventory_invwin_child_calls_v14.py'
$Setup = Join-Path $DecoderDir 'setup_capstone_decoder.ps1'
$Python = Join-Path $DecoderDir '.venv\Scripts\python.exe'

function Read-KvFile([string]$Path) {
    $m = @{}
    foreach ($line in Get-Content -LiteralPath $Path -ErrorAction Stop) {
        $i = $line.IndexOf('=')
        if ($i -le 0) { continue }
        $m[$line.Substring(0,$i).Trim()] = $line.Substring($i+1).Trim()
    }
    return $m
}

if (-not (Test-Path -LiteralPath $V8Raw)) { throw "Missing V8 raw capture: $V8Raw" }
if (-not (Test-Path -LiteralPath $V13)) { throw "Missing V13 capture: $V13" }
if (-not (Test-Path -LiteralPath $Decoder)) { throw "Missing V14 decoder: $Decoder" }

$v8 = Read-KvFile $V8Raw
$v13 = Read-KvFile $V13
if ($v8['CLIENT_SHA256'] -ne $ExpectedSha256 -or $v8['CLIENT_AUTHORITY'] -ne '1') {
    throw 'V14 blocked: V8 authority gate failed.'
}
if ($v13['CLIENT_SHA256'] -ne $ExpectedSha256 -or $v13['CLIENT_AUTHORITY'] -ne '1' -or $v13['ROOT_GRAPH_REVALIDATED'] -ne 'PASS') {
    throw 'V14 blocked: V13 authority/root gate failed.'
}
if ($v8['HEAP_SCAN'] -ne 'NO' -or $v8['MEM_PRIVATE_SCAN'] -ne 'NO' -or $v8['MEMORY_WRITE'] -ne 'NO') {
    throw 'V14 blocked: V8 safety metadata mismatch.'
}
if ($v13['HEAP_SCAN'] -ne 'NO' -or $v13['MEM_PRIVATE_SCAN'] -ne 'NO' -or $v13['MEMORY_WRITE'] -ne 'NO') {
    throw 'V14 blocked: V13 safety metadata mismatch.'
}

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $Python)) {
    if (-not (Test-Path -LiteralPath $Setup)) { throw "Missing decoder setup: $Setup" }
    & $Setup
}
if (-not (Test-Path -LiteralPath $Python)) { throw "Decoder Python missing after setup: $Python" }
$version = (& $Python -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($version -ne '5.0.9') { throw "Unexpected Capstone package version: $version" }

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
& $Python $Decoder --v8-raw $V8Raw --v13 $V13 --output $Out
if ($LASTEXITCODE -ne 0) { throw "V14 decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $Out)) { throw "V14 output missing: $Out" }

Write-Host 'STATUS=PASS_INVWIN_CHILD_CALLS_V14_EXECUTED'
Write-Host "OUTPUT=$Out"
Write-Host 'RUNTIME_ATTACH=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
