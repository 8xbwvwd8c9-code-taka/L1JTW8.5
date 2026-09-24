param(
    [string]$Python = 'python'
)

$ErrorActionPreference = 'Stop'
$requirements = Join-Path $PSScriptRoot 'requirements.txt'
$vendor = Join-Path $PSScriptRoot '_vendor'

if (-not (Test-Path -LiteralPath $requirements)) { throw "Missing requirements: $requirements" }
$pythonCmd = Get-Command $Python -ErrorAction SilentlyContinue
if (-not $pythonCmd) {
    Write-Host 'STATUS=BLOCKED_PYTHON_NOT_FOUND'
    Write-Host 'NEXT=Install/repair Python, then rerun this setup.'
    exit 30
}

$version = & $pythonCmd.Source -c "import sys; print('.'.join(map(str,sys.version_info[:3])))"
if ($LASTEXITCODE -ne 0) { throw 'Python version probe failed.' }
Write-Host "PYTHON=$($pythonCmd.Source)"
Write-Host "PYTHON_VERSION=$version"

if (-not (Test-Path -LiteralPath $vendor)) { New-Item -ItemType Directory -Force -Path $vendor | Out-Null }

& $pythonCmd.Source -m pip install --disable-pip-version-check --no-input --upgrade --target $vendor -r $requirements
if ($LASTEXITCODE -ne 0) {
    Write-Host "STATUS=BLOCKED_CAPSTONE_INSTALL EXIT=$LASTEXITCODE"
    exit $LASTEXITCODE
}

$probe = @"
import sys
sys.path.insert(0, r'''$vendor''')
import capstone
print(capstone.__version__)
"@
$capstoneVersion = & $pythonCmd.Source -c $probe
if ($LASTEXITCODE -ne 0) {
    Write-Host 'STATUS=BLOCKED_CAPSTONE_IMPORT_AFTER_INSTALL'
    exit 31
}

Write-Host 'STATUS=PASS_DECODER_READY'
Write-Host "VENDOR=$vendor"
Write-Host "CAPSTONE_VERSION=$capstoneVersion"
Write-Host 'TARGET_MEMORY_WRITE=NO'
