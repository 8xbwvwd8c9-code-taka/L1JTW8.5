[CmdletBinding()]
param(
    [switch]$Run,
    [switch]$Watch,
    [switch]$Full,
    [switch]$Clean,
    [switch]$Sync,
    [switch]$Pack
)

$ErrorActionPreference = 'Stop'
$RepoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$Python = Get-Command python -ErrorAction Stop
$Script = Join-Path $RepoRoot 'tools\850\fast_dev.py'

if (-not (Test-Path -LiteralPath $Script)) {
    throw "Fast Dev coordinator not found: $Script"
}

$Forward = @()
if ($Run)   { $Forward += '-Run' }
if ($Watch) { $Forward += '-Watch' }
if ($Full)  { $Forward += '-Full' }
if ($Clean) { $Forward += '-Clean' }
if ($Sync)  { $Forward += '-Sync' }
if ($Pack)  { $Forward += '-Pack' }

Push-Location $RepoRoot
try {
    & $Python.Source $Script @Forward
    exit $LASTEXITCODE
}
finally {
    Pop-Location
}
