param(
    [string]$HistoryDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_owner_session_history',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_owner_restart_comparison.txt'
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

function First-Value([string[]]$Source, [string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -First 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length)
}

$sessions = New-Object System.Collections.Generic.List[object]
if (Test-Path -LiteralPath $HistoryDir) {
    foreach ($file in Get-ChildItem -LiteralPath $HistoryDir -Filter '*.txt' -File | Sort-Object LastWriteTimeUtc) {
        $lines = @(Get-Content -LiteralPath $file.FullName)
        $status = First-Value $lines 'STATUS='
        $sha = First-Value $lines 'CLIENT_SHA256='
        $authority = First-Value $lines 'CLIENT_AUTHORITY='
        $pidText = First-Value $lines 'PID='
        $startUtc = First-Value $lines 'PROCESS_START_UTC='
        $rootRva = First-Value $lines 'ROOT_GLOBAL_RVA='
        $capstone = First-Value $lines 'CAPSTONE_VERSION='
        $assignment = First-Value $lines 'ASSIGNMENT_WRITER_RVAS='
        $teardown = First-Value $lines 'TEARDOWN_WRITER_RVAS='
        $memoryWrite = First-Value $lines 'MEMORY_WRITE='

        $authoritative =
            $status -eq 'PASS_OWNER_SESSION_EVIDENCE' -and
            $sha -eq $ExpectedSha256 -and
            $authority -eq '1' -and
            -not [string]::IsNullOrWhiteSpace($pidText) -and
            -not [string]::IsNullOrWhiteSpace($startUtc) -and
            $rootRva.ToUpperInvariant() -eq '0X012BCEE8' -and
            $capstone -eq '5.0.9' -and
            -not [string]::IsNullOrWhiteSpace($assignment) -and
            -not [string]::IsNullOrWhiteSpace($teardown) -and
            $memoryWrite -eq 'NO'

        $sessions.Add([pscustomobject]@{
            File = $file.FullName
            Authoritative = $authoritative
            Status = $status
            Sha = $sha
            Pid = $pidText
            StartUtc = $startUtc
            Assignment = $assignment.ToUpperInvariant()
            Teardown = $teardown.ToUpperInvariant()
        })
    }
}

$accepted = @($sessions | Where-Object { $_.Authoritative })
$processStarts = @($accepted | Select-Object -ExpandProperty StartUtc -Unique)
$assignmentSets = @($accepted | Select-Object -ExpandProperty Assignment -Unique)
$teardownSets = @($accepted | Select-Object -ExpandProperty Teardown -Unique)
$distinctProcesses = $processStarts.Count

$status = 'OWNER_PROMOTION_NOT_YET'
$next = 'Need at least two authoritative strong sessions from different Lin.bin2 process starts with identical writer RVA sets.'
$ownerPromotion = 'NOT_YET'

if ($accepted.Count -ge 2 -and
    $distinctProcesses -ge 2 -and
    $assignmentSets.Count -eq 1 -and
    $teardownSets.Count -eq 1) {
    $status = 'PASS_ROOT_OWNER_RESTART_STABILITY'
    $ownerPromotion = 'PASS_STABLE_ROOT_OWNER_ANCHOR'
    $next = 'ROOT owner anchor may be promoted. Proceed only to narrow fixed-offset owner/grid reads; do not resume broad scans.'
}
elseif ($accepted.Count -ge 2 -and $distinctProcesses -ge 2 -and ($assignmentSets.Count -gt 1 -or $teardownSets.Count -gt 1)) {
    $status = 'FAIL_WRITER_RVA_RESTART_STABILITY'
    $next = 'Writer RVA sets differ across fresh processes. Do not promote ROOT owner; inspect only decoder-proven differing writer contexts.'
}
elseif ($accepted.Count -eq 0) {
    $status = 'NO_AUTHORITATIVE_OWNER_SESSIONS'
}
elseif ($distinctProcesses -lt 2) {
    $status = 'NEED_FRESH_PROCESS_REPEAT'
}

$out = New-Object System.Collections.Generic.List[string]
$out.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$out.Add('MODE=850_OWNER_RESTART_COMPARISON_V1')
$out.Add("HISTORY_DIR=$HistoryDir")
$out.Add("FILES_SEEN=$($sessions.Count)")
$out.Add("AUTHORITATIVE_SESSIONS=$($accepted.Count)")
$out.Add("DISTINCT_PROCESS_STARTS=$distinctProcesses")
$out.Add("DISTINCT_ASSIGNMENT_SETS=$($assignmentSets.Count)")
$out.Add("DISTINCT_TEARDOWN_SETS=$($teardownSets.Count)")
$out.Add('')
$out.Add('[AUTHORITATIVE_SESSIONS]')
foreach ($s in $accepted) {
    $out.Add("SESSION PID=$($s.Pid) START=$($s.StartUtc) ASSIGNMENT=$($s.Assignment) TEARDOWN=$($s.Teardown) FILE=$($s.File)")
}
$out.Add('')
$out.Add('[REJECTED_FILES]')
foreach ($s in @($sessions | Where-Object { -not $_.Authoritative })) {
    $out.Add("REJECT FILE=$($s.File) STATUS=$($s.Status) START=$($s.StartUtc)")
}
$out.Add('')
$out.Add('[DECISION]')
$out.Add("STATUS=$status")
$out.Add("OWNER_PROMOTION=$ownerPromotion")
$out.Add('FORMAL_WP5=NOT_YET')
$out.Add('FORMAL_WP6=NOT_YET')
$out.Add("NEXT=$next")
$out.Add('HEAP_SCAN=NO')
$out.Add('MEM_PRIVATE_SCAN=NO')
$out.Add('MEMORY_WRITE=NO')

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}
[IO.File]::WriteAllLines($OutputPath, $out, [Text.UTF8Encoding]::new($false))
$out | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
