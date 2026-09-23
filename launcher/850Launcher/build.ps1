param([string]$Configuration = "Release")
$ErrorActionPreference = "Stop"
$project = Join-Path $PSScriptRoot "850Launcher.csproj"
$audit = Join-Path $PSScriptRoot "source_audit.ps1"

function Write-BuildArtifact([string]$Path) {
    if (-not (Test-Path -LiteralPath $Path)) {
        Write-Host "BUILD_ARTIFACT=MISSING"
        return
    }

    $item = Get-Item -LiteralPath $Path
    $hash = Get-FileHash -LiteralPath $Path -Algorithm SHA256
    Write-Host ("BUILD_ARTIFACT=" + $item.FullName)
    Write-Host ("BUILD_ARTIFACT_TIME=" + $item.LastWriteTime.ToString("yyyy-MM-dd HH:mm:ss.fff"))
    Write-Host ("BUILD_ARTIFACT_SIZE=" + $item.Length)
    Write-Host ("BUILD_ARTIFACT_SHA256=" + $hash.Hash)
}

if (Test-Path -LiteralPath $audit) {
    & $audit
}

$outDir = Join-Path $PSScriptRoot ("bin\" + $Configuration)
$outExe = Join-Path $outDir "850Launcher.exe"

$msbuildCandidates = @(
    (Get-Command msbuild.exe -ErrorAction SilentlyContinue | Select-Object -ExpandProperty Source -ErrorAction SilentlyContinue),
    "$env:WINDIR\Microsoft.NET\Framework\v4.0.30319\MSBuild.exe",
    "$env:WINDIR\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe"
) | Where-Object { $_ -and (Test-Path $_) } | Select-Object -Unique

foreach ($msbuild in $msbuildCandidates) {
    # Always rebuild. Incremental /t:Build previously allowed a stale launcher EXE to survive
    # even after a branch update, which made runtime evidence come from an older probe revision.
    & $msbuild $project /t:Rebuild /p:Configuration=$Configuration /p:Platform=x86 /m
    if ($LASTEXITCODE -eq 0) {
        Write-BuildArtifact $outExe
        exit 0
    }
}

$csc = "$env:WINDIR\Microsoft.NET\Framework\v4.0.30319\csc.exe"
if (Test-Path $csc) {
    New-Item -ItemType Directory -Force -Path $outDir | Out-Null
    $sources = Get-ChildItem $PSScriptRoot -Filter *.cs | ForEach-Object FullName
    & $csc /nologo /target:winexe /platform:x86 /optimize+ /win32manifest:(Join-Path $PSScriptRoot "app.manifest") /r:System.dll /r:System.Core.dll /r:System.Drawing.dll /r:System.Windows.Forms.dll /out:$outExe $sources
    if ($LASTEXITCODE -eq 0) {
        Write-BuildArtifact $outExe
    }
    exit $LASTEXITCODE
}

throw "No usable .NET Framework 4.x MSBuild/csc was found."
