param(
    [string]$InputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b_decoded.txt',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$setup = Join-Path $PSScriptRoot 'setup_capstone_decoder.ps1'
$script = Join-Path $PSScriptRoot 'decode_850_inventory_v4b_writers.py'
$venvPython = Join-Path $PSScriptRoot '.venv\Scripts\python.exe'

if (-not (Test-Path -LiteralPath $InputPath)) { throw "Missing V4B report: $InputPath" }
if (-not (Test-Path -LiteralPath $script)) { throw "Missing V4B writer decoder: $script" }

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $venvPython)) {
    if (-not (Test-Path -LiteralPath $setup)) { throw "Missing decoder setup: $setup" }
    & $setup
}
if (-not (Test-Path -LiteralPath $venvPython)) { throw "Decoder Python missing after setup: $venvPython" }

$packageVersion = (& $venvPython -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($packageVersion -ne '5.0.9') { throw "Unexpected Capstone package version: $packageVersion" }
$bindingVersion = (& $venvPython -c "import capstone; print(getattr(capstone,'__version__','UNKNOWN'))" | Select-Object -Last 1).Trim()

& $venvPython $script --input $InputPath --output $OutputPath
if ($LASTEXITCODE -ne 0) { throw "V4B writer decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutputPath)) { throw "V4B decoded output missing: $OutputPath" }

Write-Host 'STATUS=PASS_V4B_WRITER_DECODER_EXECUTED'
Write-Host "CAPSTONE_PACKAGE_VERSION=$packageVersion"
Write-Host "CAPSTONE_BINDING_VERSION=$bindingVersion"
Write-Host "OUTPUT=$OutputPath"
Write-Host 'RUNTIME_TARGET_ATTACH=NO'
Write-Host 'HEAP_DEREFERENCE=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
