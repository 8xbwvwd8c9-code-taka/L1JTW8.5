param(
    [string]$ClientDir = "I:\8.50c客服端",
    [string]$Configuration = "Release"
)

$ErrorActionPreference = "Stop"

$exe = Join-Path $PSScriptRoot ("bin\" + $Configuration + "\850Launcher.exe")
if (-not (Test-Path -LiteralPath $exe)) {
    throw "Build output not found: $exe"
}

if (-not (Test-Path -LiteralPath $ClientDir)) {
    throw "Client directory not found: $ClientDir"
}

$required = @("Lin.bin2","LoginWithoutUI.exe")
foreach ($name in $required) {
    $p = Join-Path $ClientDir $name
    if (-not (Test-Path -LiteralPath $p)) {
        throw "Required client file missing: $p"
    }
}

Copy-Item -LiteralPath $exe -Destination (Join-Path $ClientDir "850Launcher.exe") -Force

$launcherIni = Join-Path $ClientDir "launcher.ini"
if (-not (Test-Path -LiteralPath $launcherIni)) {
    Copy-Item -LiteralPath (Join-Path $PSScriptRoot "launcher.ini.example") -Destination $launcherIni
}

$helperIni = Join-Path $ClientDir "helper.ini"
if (-not (Test-Path -LiteralPath $helperIni)) {
    Copy-Item -LiteralPath (Join-Path $PSScriptRoot "helper.ini.example") -Destination $helperIni
}

Write-Host "STATUS=PASS"
Write-Host "DEPLOYED=$(Join-Path $ClientDir '850Launcher.exe')"
Write-Host "LAUNCHER_INI=$launcherIni"
Write-Host "HELPER_INI=$helperIni"
Write-Host "NEXT=Run 850Launcher.exe and test Save + Launch 850."
