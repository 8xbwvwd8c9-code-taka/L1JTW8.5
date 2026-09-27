[CmdletBinding()]
param(
    [string]$ToolkitRoot = 'I:\L共通工具\LineageAIResourceToolkit',
    [string]$SourceTextIdx = 'I:\L880C\TEST\Text.idx.before_880_list_spr',
    [string]$SourceTextPak = 'I:\L880C\TEST\Text.pak.before_880_list_spr',
    [string]$OutputRoot = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_auto_hunt_880_scan',
    [string[]]$Terms = @('autohunt', 'auto', 'hunt', 'setting', 'config', 'ui', 'macro')
)

$ErrorActionPreference = 'Stop'

$ToolPath = Join-Path $ToolkitRoot 'scripts\lineage-tool.ps1'
$Scanner = Join-Path $PSScriptRoot 'scan_880_client_resources.py'

foreach ($required in @($ToolPath, $Scanner, $SourceTextIdx, $SourceTextPak)) {
    if (-not (Test-Path -LiteralPath $required)) {
        throw "Required path not found: $required"
    }
}

$python = Get-Command python -ErrorAction Stop
New-Item -ItemType Directory -Force -Path $OutputRoot | Out-Null
$StageRoot = Join-Path $OutputRoot 'input_copy'
New-Item -ItemType Directory -Force -Path $StageRoot | Out-Null

$beforeIdx = (Get-FileHash -Algorithm SHA256 -LiteralPath $SourceTextIdx).Hash
$beforePak = (Get-FileHash -Algorithm SHA256 -LiteralPath $SourceTextPak).Hash

# Stage canonical names so the toolkit can resolve the paired archive without
# touching the original or backup resources.
$StagedIdx = Join-Path $StageRoot 'Text.idx'
$StagedPak = Join-Path $StageRoot 'Text.pak'
Copy-Item -LiteralPath $SourceTextIdx -Destination $StagedIdx -Force
Copy-Item -LiteralPath $SourceTextPak -Destination $StagedPak -Force

$logs = New-Object System.Collections.Generic.List[string]
foreach ($term in $Terms) {
    if ([string]::IsNullOrWhiteSpace($term)) { continue }
    $safeTerm = ($term -replace '[^A-Za-z0-9._-]+', '_').Trim('_')
    if ([string]::IsNullOrWhiteSpace($safeTerm)) { $safeTerm = 'term' }
    $log = Join-Path $OutputRoot ("pak-list-{0}.txt" -f $safeTerm)

    # Read-only toolkit operation. Do not call the underlying runtime directly.
    $lines = & powershell -NoProfile -ExecutionPolicy Bypass -File $ToolPath pak list $StagedIdx --filter $term 2>&1
    $exitCode = $LASTEXITCODE
    $lines | Out-File -FilePath $log -Encoding utf8
    if ($exitCode -ne 0) {
        throw "lineage-tool pak list failed for term '$term' with exit code $exitCode. See $log"
    }
    $logs.Add($log)
}

$Report = Join-Path $OutputRoot 'L880C_AUTO_HUNT_RESOURCE_SCAN.md'
$scannerArgs = @()
foreach ($log in $logs) {
    $scannerArgs += @('--input', $log)
}
$scannerArgs += @('--output', $Report)
& $python.Source $Scanner @scannerArgs
if ($LASTEXITCODE -ne 0) {
    throw "Resource evidence scanner failed with exit code $LASTEXITCODE"
}

$afterIdx = (Get-FileHash -Algorithm SHA256 -LiteralPath $SourceTextIdx).Hash
$afterPak = (Get-FileHash -Algorithm SHA256 -LiteralPath $SourceTextPak).Hash
if ($beforeIdx -ne $afterIdx -or $beforePak -ne $afterPak) {
    throw 'Source Text archive hash changed unexpectedly; stop and inspect before continuing.'
}

Write-Host "REPORT=$Report"
Write-Host 'SOURCE_MODIFIED=NO'
Write-Host "STAGED_IDX=$StagedIdx"
Write-Host "STAGED_PAK=$StagedPak"
