param([string]$Configuration = "Release")
$ErrorActionPreference = "Stop"
$project = Join-Path $PSScriptRoot "850Launcher.csproj"

$msbuildCandidates = @(
    (Get-Command msbuild.exe -ErrorAction SilentlyContinue | Select-Object -ExpandProperty Source -ErrorAction SilentlyContinue),
    "$env:WINDIR\Microsoft.NET\Framework\v4.0.30319\MSBuild.exe",
    "$env:WINDIR\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe"
) | Where-Object { $_ -and (Test-Path $_) } | Select-Object -Unique

foreach ($msbuild in $msbuildCandidates) {
    & $msbuild $project /t:Build /p:Configuration=$Configuration /p:Platform=x86 /m
    if ($LASTEXITCODE -eq 0) { exit 0 }
}

$csc = "$env:WINDIR\Microsoft.NET\Framework\v4.0.30319\csc.exe"
if (Test-Path $csc) {
    $outDir = Join-Path $PSScriptRoot ("bin\" + $Configuration)
    New-Item -ItemType Directory -Force -Path $outDir | Out-Null
    $sources = Get-ChildItem $PSScriptRoot -Filter *.cs | ForEach-Object FullName
    $outExe = Join-Path $outDir "850Launcher.exe"
    & $csc /nologo /target:winexe /platform:x86 /optimize+ /win32manifest:(Join-Path $PSScriptRoot "app.manifest") /r:System.dll /r:System.Core.dll /r:System.Drawing.dll /r:System.Windows.Forms.dll /out:$outExe $sources
    exit $LASTEXITCODE
}

throw "No usable .NET Framework 4.x MSBuild/csc was found."
