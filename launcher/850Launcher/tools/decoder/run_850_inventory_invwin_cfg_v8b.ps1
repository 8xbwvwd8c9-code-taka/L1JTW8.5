param(
    [string]$InputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_invwin_methods_v8_raw.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_invwin_cfg_v8b.txt',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$setup = Join-Path $PSScriptRoot 'setup_capstone_decoder.ps1'
$script = Join-Path $PSScriptRoot 'decode_850_inventory_invwin_cfg_v8b.py'
$venvPython = Join-Path $PSScriptRoot '.venv\Scripts\python.exe'

if (-not (Test-Path -LiteralPath $InputPath)) { throw "Missing V8 raw report: $InputPath" }
if (-not (Test-Path -LiteralPath $script)) { throw "Missing V8b decoder: $script" }

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $venvPython)) {
    if (-not (Test-Path -LiteralPath $setup)) { throw "Missing decoder setup: $setup" }
    & $setup
}
if (-not (Test-Path -LiteralPath $venvPython)) { throw "Decoder Python missing after setup: $venvPython" }

$packageVersion = (& $venvPython -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($packageVersion -ne '5.0.9') { throw "Unexpected Capstone package version: $packageVersion" }

& $venvPython $script --input $InputPath --output $OutputPath
if ($LASTEXITCODE -ne 0) { throw "V8b CFG decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutputPath)) { throw "V8b output missing: $OutputPath" }

Write-Host 'STATUS=PASS_V8B_CFG_EXECUTED'
Write-Host "CAPSTONE_PACKAGE_VERSION=$packageVersion"
Write-Host "OUTPUT=$OutputPath"
Write-Host 'RUNTIME_TARGET_ATTACH=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
