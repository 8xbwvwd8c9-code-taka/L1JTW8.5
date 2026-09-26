param(
    [string]$Target = (Join-Path $PSScriptRoot 'run_850_inventory_ctor_owner_trace_v4.ps1')
)

$ErrorActionPreference = 'Stop'

if (-not (Test-Path -LiteralPath $Target)) {
    throw "Target not found: $Target"
}

$text = [IO.File]::ReadAllText($Target)
$backup = $Target + '.identity-before'

$metadataLine = '$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString(''o''))")'

if ($text.Contains($metadataLine)) {
    Write-Host 'STATUS=PASS_ALREADY_PATCHED'
    Write-Host "FILE=$Target"
    Write-Host 'IDENTITY_METADATA=PID,PROCESS_START_UTC,CLIENT_SHA256'
    Write-Host 'SCAN_SCOPE_CHANGED=NO'
    Write-Host 'MEMORY_WRITE_CHANGED=NO'
    exit 0
}

$anchor = '$lines.Add("PID=$($proc.Id)")'
if (-not $text.Contains($anchor)) {
    throw 'Expected PID metadata anchor not found; refusing broad edit.'
}

[IO.File]::WriteAllText($backup, $text, [Text.UTF8Encoding]::new($false))
$patched = $text.Replace($anchor, $anchor + "`r`n" + $metadataLine)
[IO.File]::WriteAllText($Target, $patched, [Text.UTF8Encoding]::new($false))

$tokens = $null
$errors = $null
[System.Management.Automation.Language.Parser]::ParseFile($Target, [ref]$tokens, [ref]$errors) | Out-Null
if ($errors.Count -gt 0) {
    Copy-Item -LiteralPath $backup -Destination $Target -Force
    throw ("Patch caused parse errors and was rolled back: " + (($errors | ForEach-Object Message) -join '; '))
}

$verify = [IO.File]::ReadAllText($Target)
if (-not $verify.Contains($metadataLine)) {
    Copy-Item -LiteralPath $backup -Destination $Target -Force
    throw 'PROCESS_START_UTC metadata line missing after patch; rolled back.'
}

Write-Host 'STATUS=PASS_PATCHED_IDENTITY'
Write-Host "FILE=$Target"
Write-Host "BACKUP=$backup"
Write-Host 'IDENTITY_METADATA=PID,PROCESS_START_UTC,CLIENT_SHA256'
Write-Host 'SCAN_SCOPE_CHANGED=NO'
Write-Host 'MEMORY_WRITE_CHANGED=NO'
