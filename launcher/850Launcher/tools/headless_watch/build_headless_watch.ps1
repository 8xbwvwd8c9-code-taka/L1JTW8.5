param(
    [string]$OutputPath = (Join-Path $PSScriptRoot 'bin\HeadlessWatch850V2.exe')
)

$ErrorActionPreference = 'Stop'
$source = Join-Path $PSScriptRoot 'HeadlessWatch850V2.cs'
if (-not (Test-Path -LiteralPath $source)) { throw "Missing source: $source" }

$text = Get-Content -LiteralPath $source -Raw
if ($text -match '(?i)WriteProcessMemory\s*\(') {
    throw 'Safety gate failed: source declares or calls WriteProcessMemory.'
}
if ($text -notmatch 'TARGET_MEMORY_WRITE=NO') {
    throw 'Safety gate failed: TARGET_MEMORY_WRITE=NO marker missing.'
}

$candidates = @(
    "$env:WINDIR\Microsoft.NET\Framework\v4.0.30319\csc.exe",
    "$env:WINDIR\Microsoft.NET\Framework64\v4.0.30319\csc.exe"
)
$csc = $candidates | Where-Object { Test-Path -LiteralPath $_ } | Select-Object -First 1
if (-not $csc) { throw 'C# compiler not found under .NET Framework v4.0.30319.' }

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}

$args = @(
    '/nologo',
    '/target:exe',
    '/platform:x86',
    '/optimize+',
    '/debug-',
    '/reference:System.dll',
    ("/out:$OutputPath"),
    $source
)

& $csc $args
if ($LASTEXITCODE -ne 0) { throw "csc failed with exit code $LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutputPath)) { throw 'Build reported success but output is missing.' }

$sha = (Get-FileHash -LiteralPath $OutputPath -Algorithm SHA256).Hash.ToUpperInvariant()
Write-Host 'STATUS=PASS_BUILD'
Write-Host "COMPILER=$csc"
Write-Host 'PLATFORM=x86'
Write-Host "OUTPUT=$OutputPath"
Write-Host "OUTPUT_SHA256=$sha"
Write-Host 'TARGET_MEMORY_WRITE=NO'
