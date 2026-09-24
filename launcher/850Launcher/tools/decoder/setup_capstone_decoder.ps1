param(
    [string]$VenvPath = (Join-Path $PSScriptRoot '.venv')
)

$ErrorActionPreference = 'Stop'
$requirements = Join-Path $PSScriptRoot 'requirements.txt'
if (-not (Test-Path -LiteralPath $requirements)) { throw "Missing requirements: $requirements" }

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
if (-not (Test-Path -LiteralPath $venvPython)) {
    & $python @pythonArgs -m venv $VenvPath
    if ($LASTEXITCODE -ne 0) { throw "venv creation failed: exit=$LASTEXITCODE" }
}

& $venvPython -m pip install --disable-pip-version-check --requirement $requirements
if ($LASTEXITCODE -ne 0) { throw "pip install failed: exit=$LASTEXITCODE" }

$version = (& $venvPython -c "import capstone; print(capstone.__version__)" | Select-Object -Last 1).Trim()
if ($version -ne '5.0.9') { throw "Unexpected Capstone version: $version" }

Write-Host 'STATUS=PASS_CAPSTONE_DECODER_SETUP'
Write-Host "PYTHON=$venvPython"
Write-Host "CAPSTONE_VERSION=$version"
Write-Host 'NETWORK_USED_FOR_SETUP=POSSIBLE'
Write-Host 'RUNTIME_TARGET_ATTACH=NO'
Write-Host 'MEMORY_WRITE_TO_GAME=NO'
