param(
    [string]$InputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch_decoded.txt',
    [string]$Python = 'python',
    [switch]$NoBootstrap
)

$ErrorActionPreference = 'Stop'
$decoderDir = Join-Path $PSScriptRoot 'x86_decoder'
$decoder = Join-Path $decoderDir 'decode_root_watch.py'
$setup = Join-Path $decoderDir 'setup_850_x86_decoder.ps1'
$vendor = Join-Path $decoderDir '_vendor'

if (-not (Test-Path -LiteralPath $InputPath)) { throw "Missing watch report: $InputPath" }
if (-not (Test-Path -LiteralPath $decoder)) { throw "Missing decoder: $decoder" }

$pythonCmd = Get-Command $Python -ErrorAction SilentlyContinue
if (-not $pythonCmd) {
    Write-Host 'STATUS=BLOCKED_PYTHON_NOT_FOUND'
    exit 30
}

function Test-CapstoneReady {
    if (-not (Test-Path -LiteralPath $vendor)) { return $false }
    $probe = @"
import sys
sys.path.insert(0, r'''$vendor''')
import capstone
"@
    & $pythonCmd.Source -c $probe *> $null
    return $LASTEXITCODE -eq 0
}

if (-not (Test-CapstoneReady)) {
    if ($NoBootstrap) {
        Write-Host 'STATUS=BLOCKED_CAPSTONE_NOT_READY'
        Write-Host "NEXT=pwsh -File `"$setup`""
        exit 32
    }
    & $setup -Python $Python
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
    if (-not (Test-CapstoneReady)) {
        Write-Host 'STATUS=BLOCKED_CAPSTONE_NOT_READY_AFTER_BOOTSTRAP'
        exit 33
    }
}

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }
if (Test-Path -LiteralPath $OutputPath) { Remove-Item -LiteralPath $OutputPath -Force }

& $pythonCmd.Source $decoder --input $InputPath --output $OutputPath --vendor $vendor
$exit = $LASTEXITCODE
Write-Host "DECODER_EXIT=$exit"
Write-Host "OUTPUT=$OutputPath"
Write-Host 'TARGET_MEMORY_WRITE=NO'
exit $exit
