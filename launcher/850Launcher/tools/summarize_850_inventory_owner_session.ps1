param(
    [string]$WatchPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [string]$CorrelationPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_watch_decoder_correlation_v2.txt',
    [Parameter(Mandatory=$true)][string]$OutputPath
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

if (-not (Test-Path -LiteralPath $WatchPath)) { throw "Missing watch report: $WatchPath" }
if (-not (Test-Path -LiteralPath $CorrelationPath)) { throw "Missing correlation report: $CorrelationPath" }

$watchLines = @(Get-Content -LiteralPath $WatchPath)
$correlationLines = @(Get-Content -LiteralPath $CorrelationPath)

function First-Value([string[]]$Source, [string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -First 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length)
}

$sha = First-Value $watchLines 'CLIENT_SHA256='
$authority = First-Value $watchLines 'CLIENT_AUTHORITY='
$pidText = First-Value $watchLines 'PID='
$startUtc = First-Value $watchLines 'PROCESS_START_UTC='
$rootRva = First-Value $watchLines 'ROOT_GLOBAL_RVA='
$memoryWrite = First-Value $watchLines 'TARGET_MEMORY_WRITE='
$correlationIdentity = First-Value $correlationLines 'IDENTITY_GATE='
$correlationStatus = First-Value $correlationLines 'STATUS='
$capstoneVersion = First-Value $correlationLines 'CAPSTONE_VERSION='

$assignmentRvas = New-Object System.Collections.Generic.List[string]
$teardownRvas = New-Object System.Collections.Generic.List[string]

foreach ($line in $correlationLines) {
    $m = [regex]::Match($line, '^HIT N=\d+ VALUE=(\S+) EIP_RVA=(\S+) DECODER=(\S+) MATCH=(\S+) LITERAL_RVA=(\S+) IMM32=(\S+)')
    if (-not $m.Success) { continue }
    $value = $m.Groups[1].Value.ToUpperInvariant()
    $eipRva = $m.Groups[2].Value.ToUpperInvariant()
    $decoder = $m.Groups[3].Value
    $kind = $m.Groups[4].Value
    $imm32 = $m.Groups[6].Value.ToUpperInvariant()
    if ($decoder -ne 'PASS_UNIQUE_TARGET_WRITER') { continue }

    if ($value -match '^0X[0-9A-F]{8}$' -and $value -ne '0X00000000' -and ($kind -eq 'STORE_A3' -or $kind.StartsWith('STORE_89_'))) {
        if (-not $assignmentRvas.Contains($eipRva)) { $assignmentRvas.Add($eipRva) }
    }
    if ($value -eq '0X00000000' -and $kind -eq 'STORE_IMM_C705' -and $imm32 -eq '0X00000000') {
        if (-not $teardownRvas.Contains($eipRva)) { $teardownRvas.Add($eipRva) }
    }
}

$assignmentSet = @($assignmentRvas | Sort-Object) -join ','
$teardownSet = @($teardownRvas | Sort-Object) -join ','

$identityPass =
    $sha -eq $ExpectedSha256 -and
    $authority -eq '1' -and
    -not [string]::IsNullOrWhiteSpace($pidText) -and
    -not [string]::IsNullOrWhiteSpace($startUtc) -and
    $rootRva.ToUpperInvariant() -eq '0X012BCEE8' -and
    $memoryWrite -eq 'NO' -and
    $correlationIdentity -eq 'PASS' -and
    $capstoneVersion -eq '5.0.9'

$status = 'REJECT_OWNER_SESSION_EVIDENCE'
if ($identityPass -and
    $correlationStatus -eq 'OWNER_ANCHOR_STRONG_DECODER_RUNTIME_CANDIDATE' -and
    $assignmentRvas.Count -gt 0 -and
    $teardownRvas.Count -gt 0) {
    $status = 'PASS_OWNER_SESSION_EVIDENCE'
}

$out = New-Object System.Collections.Generic.List[string]
$out.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$out.Add('MODE=850_OWNER_SESSION_EVIDENCE_V1')
$out.Add("CLIENT_SHA256=$sha")
$out.Add("CLIENT_AUTHORITY=$authority")
$out.Add("PID=$pidText")
$out.Add("PROCESS_START_UTC=$startUtc")
$out.Add("ROOT_GLOBAL_RVA=$rootRva")
$out.Add("CAPSTONE_VERSION=$capstoneVersion")
$out.Add("CORRELATION_IDENTITY_GATE=$correlationIdentity")
$out.Add("CORRELATION_STATUS=$correlationStatus")
$out.Add("ASSIGNMENT_WRITER_RVAS=$assignmentSet")
$out.Add("TEARDOWN_WRITER_RVAS=$teardownSet")
$out.Add("ASSIGNMENT_WRITER_COUNT=$($assignmentRvas.Count)")
$out.Add("TEARDOWN_WRITER_COUNT=$($teardownRvas.Count)")
$out.Add("IDENTITY_GATE=$(if ($identityPass) { 'PASS' } else { 'FAIL' })")
$out.Add("STATUS=$status")
$out.Add('OWNER_PROMOTION=NOT_YET')
$out.Add('MEMORY_WRITE=NO')

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}
[IO.File]::WriteAllLines($OutputPath, $out, [Text.UTF8Encoding]::new($false))
$out | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
