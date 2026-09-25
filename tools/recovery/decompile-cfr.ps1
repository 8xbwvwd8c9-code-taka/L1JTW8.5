param(
    [string]$JarPath = (Join-Path $PSScriptRoot "..\..\l1jserver2.jar"),
    [Parameter(Mandatory=$true)]
    [string]$CfrJar,
    [string]$OutputDir = (Join-Path $PSScriptRoot "..\..\recovered-src-obf")
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path -LiteralPath $JarPath)) {
    throw "Server JAR not found: $JarPath"
}
if (-not (Test-Path -LiteralPath $CfrJar)) {
    throw "CFR JAR not found: $CfrJar"
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null

& java -jar $CfrJar $JarPath --outputdir $OutputDir
if ($LASTEXITCODE -ne 0) {
    throw "CFR failed with EXIT=$LASTEXITCODE"
}

$javaCount = (Get-ChildItem -LiteralPath $OutputDir -Recurse -File -Filter *.java).Count
Write-Host "DECOMPILE=PASS"
Write-Host "JAVA_COUNT=$javaCount"
Write-Host "OUT=$OutputDir"
