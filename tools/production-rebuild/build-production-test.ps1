[CmdletBinding()]
param(
    [string]$RepoRoot = 'I:\L1JTW8.5',
    [string]$CompletedRef = '3d4a392593d61c9203e3f22a154e752c2c51fa97',
    [switch]$StartServer
)

Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

$ExpectedOriginalSha256 = '8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814'
$OriginalJar = Join-Path $RepoRoot 'l1jserver2.jar'
$OutputDir = Join-Path $RepoRoot 'recovery\production-build'
$TestJar = Join-Path $OutputDir 'l1jserver2.repaired-test.jar'
$PipelineResult = Join-Path $OutputDir 'PIPELINE_RESULT.json'
$RuntimeLog = Join-Path $OutputDir 'LOCAL_RUNTIME.log'
$RuntimeErrorLog = Join-Path $OutputDir 'LOCAL_RUNTIME.err.log'
$RuntimePid = Join-Path $OutputDir 'LOCAL_RUNTIME.pid'
$Driver = Join-Path $RepoRoot 'tools\production-rebuild\run_production_rebuild.py'

function Get-Sha256([string]$Path) {
    return (Get-FileHash -LiteralPath $Path -Algorithm SHA256).Hash.ToUpperInvariant()
}

function Assert-Command([string]$Name) {
    $cmd = Get-Command $Name -ErrorAction SilentlyContinue
    if ($null -eq $cmd) {
        throw "Required command not found: $Name"
    }
    return $cmd
}

function Assert-Port2000Free {
    $listener = Get-NetTCPConnection -LocalPort 2000 -State Listen -ErrorAction SilentlyContinue
    if ($null -ne $listener) {
        $owners = ($listener | Select-Object -ExpandProperty OwningProcess -Unique) -join ','
        throw "Port 2000 is already listening (PID=$owners). Stop the existing server before -StartServer."
    }
}

if (-not (Test-Path -LiteralPath $RepoRoot -PathType Container)) {
    throw "RepoRoot does not exist: $RepoRoot"
}
if (-not (Test-Path -LiteralPath $OriginalJar -PathType Leaf)) {
    throw "Production JAR not found: $OriginalJar"
}
if (-not (Test-Path -LiteralPath $Driver -PathType Leaf)) {
    throw "Pipeline driver not found: $Driver"
}

$null = Assert-Command 'git'
$null = Assert-Command 'python'
$null = Assert-Command 'javac'

$OriginalBefore = Get-Sha256 $OriginalJar
if ($OriginalBefore -ne $ExpectedOriginalSha256) {
    throw "Original JAR SHA-256 mismatch. Expected=$ExpectedOriginalSha256 Actual=$OriginalBefore"
}

Write-Host "REPO_ROOT=$RepoRoot"
Write-Host "COMPLETED_REF=$CompletedRef"
Write-Host "ORIGINAL_JAR_SHA256_BEFORE=$OriginalBefore"
Write-Host 'PRODUCTION_JAR_OVERWRITE=DISALLOWED'

Push-Location $RepoRoot
try {
    & git cat-file -e "$CompletedRef`^{commit}" 2>$null
    if ($LASTEXITCODE -ne 0) {
        Write-Host "FETCH_COMPLETED_REF=$CompletedRef"
        & git fetch --no-tags --depth=1000 origin $CompletedRef
        if ($LASTEXITCODE -ne 0) {
            throw "Unable to fetch completed repair authority: $CompletedRef"
        }
        & git cat-file -e "$CompletedRef`^{commit}"
        if ($LASTEXITCODE -ne 0) {
            throw "Completed repair authority is still unavailable after fetch: $CompletedRef"
        }
    }

    $JavacVersion = (& javac -version 2>&1 | Out-String).Trim()
    Write-Host "JAVAC_VERSION=$JavacVersion"
    if ($JavacVersion -notmatch '^javac 1\.8\.') {
        throw "Java 8 javac is required for deployability validation. Actual: $JavacVersion"
    }

    & python 'tools\production-rebuild\run_production_rebuild.py' --repo-root $RepoRoot --completed-ref $CompletedRef
    if ($LASTEXITCODE -ne 0) {
        throw "run_production_rebuild.py failed with exit code $LASTEXITCODE"
    }
}
finally {
    Pop-Location
}

$OriginalAfter = Get-Sha256 $OriginalJar
if ($OriginalAfter -ne $ExpectedOriginalSha256 -or $OriginalAfter -ne $OriginalBefore) {
    throw "Production JAR changed. Before=$OriginalBefore After=$OriginalAfter"
}
if (-not (Test-Path -LiteralPath $TestJar -PathType Leaf)) {
    throw "Repaired test JAR was not produced: $TestJar"
}
if (-not (Test-Path -LiteralPath $PipelineResult -PathType Leaf)) {
    throw "Pipeline result was not produced: $PipelineResult"
}

$Result = Get-Content -LiteralPath $PipelineResult -Raw | ConvertFrom-Json
if (-not [bool]$Result.pass) {
    throw 'Pipeline result is not PASS.'
}
if (-not [bool]$Result.original_jar_unchanged) {
    throw 'Pipeline result says original JAR changed.'
}
if ([string]$Result.completed_ref -ne $CompletedRef) {
    throw "Pipeline authority mismatch. Expected=$CompletedRef Actual=$($Result.completed_ref)"
}

$TestSha = Get-Sha256 $TestJar
Write-Host "ORIGINAL_JAR_SHA256_AFTER=$OriginalAfter"
Write-Host 'ORIGINAL_JAR_UNCHANGED=YES'
Write-Host "TEST_JAR=$TestJar"
Write-Host "TEST_JAR_SHA256=$TestSha"
Write-Host "DEPLOYABLE_TOP_LEVEL=$($Result.deployability.deployable_count)"
Write-Host "DEFERRED_TOP_LEVEL=$($Result.deployability.deferred_count)"
Write-Host "REPLACED_RUNTIME_CLASSES=$($Result.build.replaced_class_count)"
Write-Host 'BUILD_STRUCTURAL_GATE=PASS'

if (-not $StartServer) {
    Write-Host 'RUNTIME_STARTUP_GATE=NOT_RUN'
    Write-Host 'REAL_CLIENT_LOGIN_GATE=NOT_RUN'
    Write-Host 'NEXT=pwsh -File .\tools\production-rebuild\build-production-test.ps1 -StartServer'
    exit 0
}

$null = Assert-Command 'java'
Assert-Port2000Free

$ServerProperties = Join-Path $RepoRoot 'config\server.properties'
if (-not (Test-Path -LiteralPath $ServerProperties -PathType Leaf)) {
    throw "Missing server.properties: $ServerProperties"
}
$ServerConfig = Get-Content -LiteralPath $ServerProperties -Raw
if ($ServerConfig -notmatch '(?m)^URL=.*useSSL=false') {
    throw 'Runtime JDBC URL must already contain useSSL=false. The local driver will not modify server.properties.'
}

New-Item -ItemType Directory -Path $OutputDir -Force | Out-Null
Remove-Item -LiteralPath $RuntimeLog -Force -ErrorAction SilentlyContinue
Remove-Item -LiteralPath $RuntimeErrorLog -Force -ErrorAction SilentlyContinue
Remove-Item -LiteralPath $RuntimePid -Force -ErrorAction SilentlyContinue

$JavaArgs = @(
    '-server',
    '-noverify',
    '-XX:+UseG1GC',
    '-XX:+UseStringDeduplication',
    '-XX:+UseFastAccessorMethods',
    '-XX:+AggressiveOpts',
    '-XX:+OptimizeStringConcat',
    '-XX:+UseBiasedLocking',
    '-jar',
    $TestJar
)

$JavaProcess = Start-Process -FilePath 'java' `
    -ArgumentList $JavaArgs `
    -WorkingDirectory $RepoRoot `
    -RedirectStandardOutput $RuntimeLog `
    -RedirectStandardError $RuntimeErrorLog `
    -PassThru

Set-Content -LiteralPath $RuntimePid -Value $JavaProcess.Id -Encoding ascii
Write-Host "RUNTIME_PID=$($JavaProcess.Id)"

$Listening = $false
for ($i = 0; $i -lt 60; $i++) {
    Start-Sleep -Seconds 1
    if ($JavaProcess.HasExited) {
        $stdout = if (Test-Path $RuntimeLog) { Get-Content $RuntimeLog -Raw } else { '' }
        $stderr = if (Test-Path $RuntimeErrorLog) { Get-Content $RuntimeErrorLog -Raw } else { '' }
        throw "Repaired server exited before port 2000 opened. ExitCode=$($JavaProcess.ExitCode)`nSTDOUT:`n$stdout`nSTDERR:`n$stderr"
    }

    $listener = Get-NetTCPConnection -LocalPort 2000 -State Listen -ErrorAction SilentlyContinue |
        Where-Object { $_.OwningProcess -eq $JavaProcess.Id }
    if ($null -ne $listener) {
        $Listening = $true
        break
    }
}

if (-not $Listening) {
    Stop-Process -Id $JavaProcess.Id -Force -ErrorAction SilentlyContinue
    throw "Repaired server stayed alive but did not listen on port 2000 within the startup gate. See $RuntimeLog"
}

Write-Host 'RUNTIME_STARTUP_GATE=PASS'
Write-Host 'PORT_2000=LISTENING'
Write-Host 'REAL_CLIENT_LOGIN_GATE=MANUAL_PENDING'
Write-Host 'CLIENT_AUTHORITY=I:\8.50c客服端\Lin.bin2'
Write-Host "LOG=$RuntimeLog"
Write-Host "PID_FILE=$RuntimePid"
Write-Host 'NEXT=Use the unchanged 8.50c client for account login -> character select -> enter-game. Record results per recovery\BUILD_REPRODUCTION.md.'
