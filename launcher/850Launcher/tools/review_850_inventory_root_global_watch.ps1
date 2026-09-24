param(
    [string]$InputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch_review.txt'
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$ExpectedRootGlobalRva = '0X012BCEE8'
$ExpectedRootBPostWriteEipRva = '0X00701E80'

if (-not (Test-Path -LiteralPath $InputPath)) { throw "Missing watch report: $InputPath" }
$lines = @(Get-Content -LiteralPath $InputPath)

function First-Value([string]$Prefix) {
    $line = $lines | Where-Object { $_ -like "$Prefix*" } | Select-Object -First 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length)
}

$sha = First-Value 'CLIENT_SHA256='
$authority = First-Value 'CLIENT_AUTHORITY='
$pidText = First-Value 'PID='
$startUtc = First-Value 'PROCESS_START_UTC='
$rootRva = First-Value 'ROOT_GLOBAL_RVA='
$targetWrite = First-Value 'TARGET_MEMORY_WRITE='

$hits = New-Object System.Collections.Generic.List[object]
foreach ($line in $lines) {
    if (-not $line.StartsWith('HIT NAME=')) { continue }
    $m = [regex]::Match($line, '^HIT NAME=(\S+)\s+N=(\d+)\s+TID=(\d+)\s+WATCH=(\S+)\s+POST_WRITE_VALUE=(\S+)\s+EIP=(\S+)\s+EIP_RVA=(\S+)')
    if (-not $m.Success) { continue }
    $hits.Add([pscustomobject]@{
        Name = $m.Groups[1].Value
        Number = [int]$m.Groups[2].Value
        Tid = [int]$m.Groups[3].Value
        Watch = $m.Groups[4].Value.ToUpperInvariant()
        Value = $m.Groups[5].Value.ToUpperInvariant()
        Eip = $m.Groups[6].Value.ToUpperInvariant()
        EipRva = $m.Groups[7].Value.ToUpperInvariant()
    })
}

$identityPass =
    $sha -eq $ExpectedSha256 -and
    $authority -eq '1' -and
    -not [string]::IsNullOrWhiteSpace($pidText) -and
    -not [string]::IsNullOrWhiteSpace($startUtc) -and
    $rootRva.ToUpperInvariant() -eq $ExpectedRootGlobalRva -and
    $targetWrite -eq 'NO'

$zeroHits = @($hits | Where-Object { $_.Value -eq '0X00000000' })
$nonzeroHits = @($hits | Where-Object { $_.Value -match '^0X[0-9A-F]{8}$' -and $_.Value -ne '0X00000000' })
$unreadableHits = @($hits | Where-Object { $_.Value -eq 'UNREADABLE' })
$expectedTeardown = @($zeroHits | Where-Object { $_.EipRva -eq $ExpectedRootBPostWriteEipRva })

$allWriterRvas = @($hits | Where-Object { $_.EipRva -match '^0X[0-9A-F]{8}$' } | Select-Object -ExpandProperty EipRva -Unique | Sort-Object)
$nonzeroWriterRvas = @($nonzeroHits | Where-Object { $_.EipRva -match '^0X[0-9A-F]{8}$' } | Select-Object -ExpandProperty EipRva -Unique | Sort-Object)
$zeroWriterRvas = @($zeroHits | Where-Object { $_.EipRva -match '^0X[0-9A-F]{8}$' } | Select-Object -ExpandProperty EipRva -Unique | Sort-Object)

$decision = 'OWNER_PROMOTION_NOT_YET'
$next = 'Capture a session that includes both a nonzero construction-side write and the known ROOT_B zero clear.'
if (-not $identityPass) {
    $decision = 'REJECT_IDENTITY_OR_SAFETY_GATE'
    $next = 'Rerun the authoritative ROOT-global watcher; do not combine this report with other evidence.'
}
elseif ($hits.Count -eq 0) {
    $decision = 'NO_WRITE_OBSERVED'
    $next = 'Rerun while entering/re-entering the world and performing a normal logout/relogin; do not widen memory scan scope.'
}
elseif ($expectedTeardown.Count -gt 0 -and $nonzeroHits.Count -gt 0) {
    $decision = 'OWNER_ANCHOR_STRONG_RUNTIME_CANDIDATE'
    $next = 'Repeat across a fresh client process; require the same teardown writer and a stable construction-side writer before owner promotion.'
}
elseif ($expectedTeardown.Count -gt 0) {
    $decision = 'PARTIAL_TEARDOWN_ONLY'
    $next = 'Capture the construction phase by starting the watch before entering the game world.'
}
elseif ($nonzeroHits.Count -gt 0) {
    $decision = 'PARTIAL_NONZERO_WRITE_ONLY'
    $next = 'Capture a normal logout/relogin to test for the known ROOT_B zero clear.'
}
elseif ($zeroHits.Count -gt 0) {
    $decision = 'ZERO_WRITE_AMBIGUOUS'
    $next = 'Inspect only the observed zero-writer RVA; do not promote it to ROOT teardown unless it matches decoded ROOT_B context.'
}

$out = New-Object System.Collections.Generic.List[string]
$out.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$out.Add('MODE=850_INVENTORY_ROOT_GLOBAL_WATCH_REVIEW')
$out.Add("INPUT=$InputPath")
$out.Add("CLIENT_SHA256=$sha")
$out.Add("CLIENT_AUTHORITY=$authority")
$out.Add("PID=$pidText")
$out.Add("PROCESS_START_UTC=$startUtc")
$out.Add("ROOT_GLOBAL_RVA=$rootRva")
$out.Add("TARGET_MEMORY_WRITE=$targetWrite")
$out.Add("IDENTITY_SAFETY_GATE=$(if($identityPass){'PASS'}else{'FAIL'})")
$out.Add('')
$out.Add('[HITS]')
$out.Add("TOTAL=$($hits.Count)")
$out.Add("ZERO=$($zeroHits.Count)")
$out.Add("NONZERO=$($nonzeroHits.Count)")
$out.Add("UNREADABLE=$($unreadableHits.Count)")
$out.Add("EXPECTED_ROOT_B_ZERO_CLEAR=$($expectedTeardown.Count)")
$out.Add("EXPECTED_ROOT_B_POST_WRITE_EIP_RVA=$ExpectedRootBPostWriteEipRva")
$out.Add("ALL_WRITER_RVAS=$($allWriterRvas -join ',')")
$out.Add("NONZERO_WRITER_RVAS=$($nonzeroWriterRvas -join ',')")
$out.Add("ZERO_WRITER_RVAS=$($zeroWriterRvas -join ',')")
foreach ($h in $hits) {
    $out.Add("HIT N=$($h.Number) TID=$($h.Tid) VALUE=$($h.Value) EIP_RVA=$($h.EipRva)")
}
$out.Add('')
$out.Add('[DECISION]')
$out.Add("STATUS=$decision")
$out.Add('OWNER_PROMOTION=NOT_YET')
$out.Add('FORMAL_WP5=NOT_YET')
$out.Add('FORMAL_WP6=NOT_YET')
$out.Add("NEXT=$next")
$out.Add('HEAP_SCAN=NO')
$out.Add('MEM_PRIVATE_SCAN=NO')
$out.Add('MEMORY_WRITE=NO')

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }
[IO.File]::WriteAllLines($OutputPath, $out, [Text.UTF8Encoding]::new($false))
$out | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
