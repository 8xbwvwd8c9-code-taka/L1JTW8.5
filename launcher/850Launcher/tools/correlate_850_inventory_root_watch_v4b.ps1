param(
    [string]$WatchPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [string]$V4bPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_watch_v4b_correlation.txt'
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

if (-not (Test-Path -LiteralPath $WatchPath)) { throw "Missing hardware-watch report: $WatchPath" }
if (-not (Test-Path -LiteralPath $V4bPath)) { throw "Missing V4b report: $V4bPath" }

$watchLines = @(Get-Content -LiteralPath $WatchPath)
$v4bLines = @(Get-Content -LiteralPath $V4bPath)

function First-Value([string[]]$Source, [string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -First 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length)
}

function Last-Value([string[]]$Source, [string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -Last 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length)
}

function Hex-ToUInt64([string]$Text) {
    if ([string]::IsNullOrWhiteSpace($Text) -or -not $Text.StartsWith('0x', [StringComparison]::OrdinalIgnoreCase)) { return $null }
    return [Convert]::ToUInt64($Text.Substring(2), 16)
}

$watchSha = First-Value $watchLines 'CLIENT_SHA256='
$watchPid = First-Value $watchLines 'PID='
$watchStart = First-Value $watchLines 'PROCESS_START_UTC='
$watchAuthority = First-Value $watchLines 'CLIENT_AUTHORITY='
$watchMemoryWrite = First-Value $watchLines 'TARGET_MEMORY_WRITE='

$v4bSha = First-Value $v4bLines 'CLIENT_SHA256='
$v4bPid = First-Value $v4bLines 'PID='
# V4b gets its authoritative process-start identity from the runner stamp.
$v4bStart = Last-Value $v4bLines 'PROCESS_START_UTC='
$v4bAuthority = Last-Value $v4bLines 'CLIENT_AUTHORITY='
$v4bMemoryWrite = Last-Value $v4bLines 'MEMORY_WRITE='

$identityPass =
    $watchSha -eq $ExpectedSha256 -and
    $v4bSha -eq $ExpectedSha256 -and
    $watchAuthority -eq '1' -and
    $v4bAuthority -eq '1' -and
    $watchPid -eq $v4bPid -and
    -not [string]::IsNullOrWhiteSpace($watchStart) -and
    $watchStart -eq $v4bStart -and
    $watchMemoryWrite -eq 'NO' -and
    $v4bMemoryWrite -eq 'NO'

$stores = New-Object System.Collections.Generic.List[object]
foreach ($line in $v4bLines) {
    $m = [regex]::Match($line, '^REF RVA=(0x[0-9A-Fa-f]+) KIND=(\S+) IMM32=(\S+)')
    if (-not $m.Success) { continue }
    $kind = $m.Groups[2].Value
    if ($kind -ne 'STORE_A3' -and $kind -ne 'STORE_IMM_C705' -and -not $kind.StartsWith('STORE_89_')) { continue }

    $literalRva = Hex-ToUInt64 $m.Groups[1].Value
    if ($null -eq $literalRva) { continue }
    [uint64]$postDelta = if ($kind -eq 'STORE_IMM_C705') { 8 } else { 4 }
    [uint64]$postRva = [uint64]$literalRva + $postDelta

    $stores.Add([pscustomobject]@{
        LiteralRva = [uint64]$literalRva
        PostEipRva = $postRva
        Kind = $kind
        Imm32 = $m.Groups[3].Value.ToUpperInvariant()
    })
}

$hits = New-Object System.Collections.Generic.List[object]
foreach ($line in $watchLines) {
    $m = [regex]::Match($line, '^HIT NAME=(\S+)\s+N=(\d+)\s+TID=(\d+)\s+WATCH=(\S+)\s+POST_WRITE_VALUE=(\S+)\s+EIP=(\S+)\s+EIP_RVA=(\S+)')
    if (-not $m.Success) { continue }
    $eipRva = Hex-ToUInt64 $m.Groups[7].Value
    $value = $m.Groups[5].Value.ToUpperInvariant()
    $matched = @()
    if ($null -ne $eipRva) {
        $matched = @($stores | Where-Object { $_.PostEipRva -eq [uint64]$eipRva })
    }
    $hits.Add([pscustomobject]@{
        Number = [int]$m.Groups[2].Value
        Tid = [int]$m.Groups[3].Value
        Value = $value
        EipRva = $eipRva
        EipRvaText = $m.Groups[7].Value.ToUpperInvariant()
        Stores = $matched
    })
}

$correlated = @($hits | Where-Object { $_.Stores.Count -gt 0 })
$teardownMatches = New-Object System.Collections.Generic.List[object]
$assignmentMatches = New-Object System.Collections.Generic.List[object]
foreach ($hit in $correlated) {
    foreach ($store in $hit.Stores) {
        if ($hit.Value -eq '0X00000000' -and $store.Kind -eq 'STORE_IMM_C705' -and $store.Imm32 -eq '0X00000000') {
            $teardownMatches.Add([pscustomobject]@{ Hit=$hit; Store=$store })
        }
        if ($hit.Value -match '^0X[0-9A-F]{8}$' -and $hit.Value -ne '0X00000000' -and ($store.Kind -eq 'STORE_A3' -or $store.Kind.StartsWith('STORE_89_'))) {
            $assignmentMatches.Add([pscustomobject]@{ Hit=$hit; Store=$store })
        }
    }
}

$status = 'OWNER_PROMOTION_NOT_YET'
$next = 'Obtain both a correlated nonzero register-store assignment and a correlated zero immediate teardown clear in one authoritative process.'
if (-not $identityPass) {
    $status = 'REJECT_IDENTITY_MISMATCH'
    $next = 'Rerun V4b and the hardware watcher in one unchanged Lin.bin2 process.'
}
elseif ($teardownMatches.Count -gt 0 -and $assignmentMatches.Count -gt 0) {
    $status = 'OWNER_ANCHOR_STRONG_RUNTIME_CANDIDATE'
    $next = 'Repeat across a fresh client process; require stable writer RVAs before owner promotion.'
}
elseif ($teardownMatches.Count -gt 0) {
    $status = 'PARTIAL_CORRELATED_TEARDOWN'
    $next = 'Start the hardware watch before entering the world to capture the nonzero construction-side register store.'
}
elseif ($assignmentMatches.Count -gt 0) {
    $status = 'PARTIAL_CORRELATED_ASSIGNMENT'
    $next = 'Perform a normal logout/relogin while the same watch remains active to capture the zero clear.'
}
elseif ($correlated.Count -gt 0) {
    $status = 'CORRELATED_STORE_BUT_SEMANTICS_AMBIGUOUS'
    $next = 'Inspect only the correlated exact store(s); do not widen scan scope.'
}
elseif ($hits.Count -gt 0) {
    $status = 'RUNTIME_WRITER_NOT_IN_V4B_STORE_SET'
    $next = 'Decode only the observed writer EIP window with a real x86 decoder; do not use raw-byte CALL heuristics.'
}

$out = New-Object System.Collections.Generic.List[string]
$out.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$out.Add('MODE=850_ROOT_WATCH_V4B_CORRELATION')
$out.Add("WATCH=$WatchPath")
$out.Add("V4B=$V4bPath")
$out.Add("WATCH_PID=$watchPid")
$out.Add("V4B_PID=$v4bPid")
$out.Add("WATCH_START_UTC=$watchStart")
$out.Add("V4B_START_UTC=$v4bStart")
$out.Add("IDENTITY_GATE=$(if($identityPass){'PASS'}else{'FAIL'})")
$out.Add("V4B_STORE_CANDIDATES=$($stores.Count)")
$out.Add("WATCH_HITS=$($hits.Count)")
$out.Add("CORRELATED_HITS=$($correlated.Count)")
$out.Add("CORRELATED_NONZERO_ASSIGNMENTS=$($assignmentMatches.Count)")
$out.Add("CORRELATED_ZERO_CLEARS=$($teardownMatches.Count)")
$out.Add('')
$out.Add('[STORE_CANDIDATES]')
foreach ($store in $stores) {
    $out.Add(('STORE KIND={0} LITERAL_RVA=0x{1:X8} POST_EIP_RVA=0x{2:X8} IMM32={3}' -f $store.Kind,$store.LiteralRva,$store.PostEipRva,$store.Imm32))
}
$out.Add('')
$out.Add('[CORRELATION]')
foreach ($hit in $hits) {
    if ($hit.Stores.Count -eq 0) {
        $out.Add("HIT N=$($hit.Number) VALUE=$($hit.Value) EIP_RVA=$($hit.EipRvaText) MATCH=NONE")
        continue
    }
    foreach ($store in $hit.Stores) {
        $out.Add(('HIT N={0} VALUE={1} EIP_RVA={2} MATCH={3} LITERAL_RVA=0x{4:X8} IMM32={5}' -f $hit.Number,$hit.Value,$hit.EipRvaText,$store.Kind,$store.LiteralRva,$store.Imm32))
    }
}
$out.Add('')
$out.Add('[DECISION]')
$out.Add("STATUS=$status")
$out.Add('OWNER_PROMOTION=NOT_YET')
$out.Add('FORMAL_WP5=NOT_YET')
$out.Add('FORMAL_WP6=NOT_YET')
$out.Add("NEXT=$next")
$out.Add('RAW_E8_USED=NO')
$out.Add('HEAP_SCAN=NO')
$out.Add('MEM_PRIVATE_SCAN=NO')
$out.Add('MEMORY_WRITE=NO')

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }
[IO.File]::WriteAllLines($OutputPath, $out, [Text.UTF8Encoding]::new($false))
$out | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
