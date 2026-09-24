param(
    [string]$VenvPath = (Join-Path $PSScriptRoot '.venv')
)

$ErrorActionPreference = 'Stop'
$requirements = Join-Path $PSScriptRoot 'requirements.txt'
$selftest = Join-Path $PSScriptRoot 'selftest_capstone_decoder.py'
if (-not (Test-Path -LiteralPath $requirements)) { throw "Missing requirements: $requirements" }
if (-not (Test-Path -LiteralPath $selftest)) { throw "Missing self-test: $selftest" }

$python = $null
$pythonArgs = @()
$py = Get-Command py -ErrorAction SilentlyContinue
if ($py) {
    $python = $py.Source
    $pythonArgs = @('-3.13')
}
else {
    $pythonCmd = Get-Command python -ErrorAction SilentlyContinue
    if ($pythonCmd) { $python = $pythonCmd.Source }
}
if (-not $python) { throw 'Python 3.13/python not found.' }

$venvPython = Join-Path $VenvPath 'Scripts\python.exe'
$networkUsed = 'NO'
if (-not (Test-Path -LiteralPath $venvPython)) {
    & $python @pythonArgs -m venv $VenvPath
    if ($LASTEXITCODE -ne 0) { throw "venv creation failed: exit=$LASTEXITCODE" }
}

$version = ''
try {
    $version = (& $venvPython -c "import capstone; print(capstone.__version__)" 2>$null | Select-Object -Last 1).Trim()
} catch { $version = '' }

if ($version -ne '5.0.9') {
    $networkUsed = 'POSSIBLE'
    & $venvPython -m pip install --disable-pip-version-check --requirement $requirements
    if ($LASTEXITCODE -ne 0) { throw "pip install failed: exit=$LASTEXITCODE" }
    $version = (& $venvPython -c "import capstone; print(capstone.__version__)" | Select-Object -Last 1).Trim()
}
if ($version -ne '5.0.9') { throw "Unexpected Capstone version: $version" }

& $venvPython $selftest
if ($LASTEXITCODE -ne 0) { throw "Capstone decoder self-test failed: exit=$LASTEXITCODE" }

Write-Host 'STATUS=PASS_CAPSTONE_DECODER_SETUP'
Write-Host "PYTHON=$venvPython"
Write-Host "CAPSTONE_VERSION=$version"
Write-Host 'SELFTEST=PASS'
Write-Host "NETWORK_USED_FOR_SETUP=$networkUsed"
Write-Host 'RUNTIME_TARGET_ATTACH=NO'
Write-Host 'MEMORY_WRITE_TO_GAME=NO'
