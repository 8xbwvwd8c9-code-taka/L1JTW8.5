param(
    [string]$JarPath = (Join-Path $PSScriptRoot "..\..\l1jserver2.jar"),
    [string]$TargetList = (Join-Path $PSScriptRoot "..\..\recovery\target_classes.txt"),
    [string]$OutputDir = (Join-Path $PSScriptRoot "..\..\recovery\javap-targets")
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path -LiteralPath $JarPath)) {
    throw "JAR not found: $JarPath"
}
if (-not (Test-Path -LiteralPath $TargetList)) {
    throw "Target list not found: $TargetList"
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null

$targets = Get-Content -LiteralPath $TargetList |
    ForEach-Object { $_.Trim() } |
    Where-Object { $_ -and -not $_.StartsWith("#") }

$ok = 0
$fail = 0

foreach ($class in $targets) {
    $safe = $class -replace '[^A-Za-z0-9._-]', '_'
    $out = Join-Path $OutputDir ($safe + ".javap.txt")

    & javap -classpath $JarPath -p -c -verbose $class 2>&1 |
        Set-Content -LiteralPath $out -Encoding UTF8

    if ($LASTEXITCODE -eq 0) {
        $ok++
        Write-Host ("PASS {0}" -f $class)
    } else {
        $fail++
        Write-Host ("FAIL {0}" -f $class)
    }
}

Write-Host ("TARGETS={0}" -f $targets.Count)
Write-Host ("PASS={0}" -f $ok)
Write-Host ("FAIL={0}" -f $fail)
Write-Host ("OUT={0}" -f $OutputDir)

if ($fail -gt 0) {
    exit 1
}
