param(
    [string]$InputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch_capstone.txt',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$setup = Join-Path $PSScriptRoot 'setup_capstone_decoder.ps1'
$script = Join-Path $PSScriptRoot 'decode_850_inventory_watch.py'
$venvPython = Join-Path $PSScriptRoot '.venv\Scripts\python.exe'

if (-not (Test-Path -LiteralPath $InputPath)) { throw "Missing watch report: $InputPath" }
if (-not (Test-Path -LiteralPath $script)) { throw "Missing decoder: $script" }

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $venvPython)) {
    if (-not (Test-Path -LiteralPath $setup)) { throw "Missing setup script: $setup" }
    & $setup
}
if (-not (Test-Path -LiteralPath $venvPython)) { throw "Decoder Python missing after setup: $venvPython" }

$version = (& $venvPython -c "import capstone; print(capstone.__version__)" | Select-Object -Last 1).Trim()
if ($version -ne '5.0.9') { throw "Unexpected Capstone version: $version" }

& $venvPython $script --input $InputPath --output $OutputPath
if ($LASTEXITCODE -ne 0) { throw "Capstone decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutputPath)) { throw "Decoder output missing: $OutputPath" }

Write-Host 'STATUS=PASS_CAPSTONE_DECODER_EXECUTED'
Write-Host "CAPSTONE_VERSION=$version"
Write-Host "OUTPUT=$OutputPath"
Write-Host 'RUNTIME_TARGET_ATTACH=NO'
Write-Host 'MEMORY_WRITE_TO_GAME=NO'
