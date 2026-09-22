param([string]$Configuration = "Release")
$ErrorActionPreference = "Stop"
$project = Join-Path $PSScriptRoot "850Launcher.csproj"

$msbuild = Get-Command msbuild.exe -ErrorAction SilentlyContinue
if ($msbuild) {
    & $msbuild.Source $project /t:Restore,Build /p:Configuration=$Configuration /m
    exit $LASTEXITCODE
}

$dotnet = Get-Command dotnet.exe -ErrorAction SilentlyContinue
if ($dotnet) {
    & $dotnet.Source build $project -c $Configuration
    exit $LASTEXITCODE
}

throw "Neither MSBuild nor dotnet SDK was found."
