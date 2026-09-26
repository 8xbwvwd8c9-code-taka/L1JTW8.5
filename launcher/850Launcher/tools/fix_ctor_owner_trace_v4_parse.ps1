param(
    [string]$TargetPath = (Join-Path $PSScriptRoot 'run_850_inventory_ctor_owner_trace_v4.ps1')
)

$ErrorActionPreference = 'Stop'

if (-not (Test-Path -LiteralPath $TargetPath)) {
    throw "Missing target: $TargetPath"
}

$text = Get-Content -LiteralPath $TargetPath -Raw
$old = '    if($a.End -gt 0){ $lines.Add(("  END_BYTES={0}" -f (Hex-At ([Math]::Max($a.Start,$a.End-24)) ([int]([Math]::Min(48,$a.End-[Math]::Max($a.Start,$a.End-24)+1))))) }'
$new = @'
    if($a.End -gt 0){
        $endStart=[Math]::Max($a.Start,$a.End-24)
        $endCount=[int][Math]::Min(48,($a.End-$endStart+1))
        $lines.Add(("  END_BYTES={0}" -f (Hex-At $endStart $endCount)))
    }
'@

if ($text.Contains($new.TrimEnd())) {
    Write-Host 'STATUS=PASS_ALREADY_FIXED'
    Write-Host "FILE=$TargetPath"
    exit 0
}

if (-not $text.Contains($old)) {
    throw 'Expected malformed END_BYTES line not found; refusing broad edit.'
}

$backup = "$TargetPath.parse-before"
Copy-Item -LiteralPath $TargetPath -Destination $backup -Force
$text = $text.Replace($old, $new.TrimEnd())
Set-Content -LiteralPath $TargetPath -Value $text -Encoding utf8NoBOM

$tokens = $null
$errors = $null
[void][System.Management.Automation.Language.Parser]::ParseFile(
    $TargetPath,
    [ref]$tokens,
    [ref]$errors)

if ($errors.Count -ne 0) {
    Copy-Item -LiteralPath $backup -Destination $TargetPath -Force
    $msg = ($errors | ForEach-Object { $_.Message }) -join '; '
    throw "Parse validation failed; restored backup: $msg"
}

Write-Host 'STATUS=PASS_PATCHED_PARSE'
Write-Host "FILE=$TargetPath"
Write-Host "BACKUP=$backup"
Write-Host 'SCAN_SCOPE_CHANGED=NO'
Write-Host 'MEMORY_WRITE_CHANGED=NO'
