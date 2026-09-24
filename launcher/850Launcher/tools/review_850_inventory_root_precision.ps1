param(
    [string]$WatchPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [string]$DecoderPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch_decoded.txt',
    [string]$V4bPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_precision_review.txt'
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

foreach ($p in @($WatchPath,$DecoderPath,$V4bPath)) {
    if (-not (Test-Path -LiteralPath $p)) { throw "Missing precision evidence: $p" }
}
$watch = @(Get-Content -LiteralPath $WatchPath)
$decoder = @(Get-Content -LiteralPath $DecoderPath)
$v4b = @(Get-Content -LiteralPath $V4bPath)

function First-Value([string[]]$Source,[string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -First 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length).Trim()
}
function Last-Value([string[]]$Source,[string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -Last 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length).Trim()
}
function Hex64([string]$Text) {
    if ([string]::IsNullOrWhiteSpace($Text) -or -not $Text.StartsWith('0x',[StringComparison]::OrdinalIgnoreCase)) { return $null }
    try { return [Convert]::ToUInt64($Text.Substring(2),16) } catch { return $null }
}

$watchSha = First-Value $watch 'CLIENT_SHA256='
$watchPid = First-Value $watch 'PID='
$watchStart = First-Value $watch 'PROCESS_START_UTC='
$watchAuth = First-Value $watch 'CLIENT_AUTHORITY='
$watchMw = First-Value $watch 'TARGET_MEMORY_WRITE='

$decSha = First-Value $decoder 'CLIENT_SHA256='
$decPid = First-Value $decoder 'PID='
$decStart = First-Value $decoder 'PROCESS_START_UTC='
$decAuth = First-Value $decoder 'CLIENT_AUTHORITY='
$decMw = First-Value $decoder 'TARGET_MEMORY_WRITE='
$decSummary = Last-Value $decoder 'STATUS='

$v4bSha = First-Value $v4b 'CLIENT_SHA256='
$v4bPid = First-Value $v4b 'PID='
$v4bStart = Last-Value $v4b 'PROCESS_START_UTC='
$v4bAuth = Last-Value $v4b 'CLIENT_AUTHORITY='
$v4bMw = Last-Value $v4b 'MEMORY_WRITE='

$identityPass =
    $watchSha -eq $ExpectedSha256 -and $decSha -eq $ExpectedSha256 -and $v4bSha -eq $ExpectedSha256 -and
    $watchAuth -eq '1' -and $decAuth -eq '1' -and $v4bAuth -eq '1' -and
    -not [string]::IsNullOrWhiteSpace($watchPid) -and $watchPid -eq $decPid -and $watchPid -eq $v4bPid -and
    -not [string]::IsNullOrWhiteSpace($watchStart) -and $watchStart -eq $decStart -and $watchStart -eq $v4bStart -and
    $watchMw -eq 'NO' -and $decMw -eq 'NO' -and $v4bMw -eq 'NO'

$stores = New-Object System.Collections.Generic.List[object]
foreach ($line in $v4b) {
    $m = [regex]::Match($line,'^REF RVA=(0x[0-9A-Fa-f]+) KIND=(\S+) IMM32=(\S+)')
    if (-not $m.Success) { continue }
    $kind = $m.Groups[2].Value
    if ($kind -ne 'STORE_A3' -and $kind -ne 'STORE_IMM_C705' -and -not $kind.StartsWith('STORE_89_')) { continue }
    $lit = Hex64 $m.Groups[1].Value
    if ($null -eq $lit) { continue }
    [uint64]$start = if ($kind -eq 'STORE_A3') { $lit - 1 } else { $lit - 2 }
    [uint64]$post = if ($kind -eq 'STORE_IMM_C705') { $lit + 8 } else { $lit + 4 }
    $stores.Add([pscustomobject]@{
        Kind=$kind; LiteralRva=[uint64]$lit; StartRva=$start; PostRva=$post; Imm=$m.Groups[3].Value.ToUpperInvariant()
    })
}

$decodedHits = New-Object System.Collections.Generic.List[object]
$current = $null
foreach ($line in $decoder) {
    $hm = [regex]::Match($line,'^HIT N=(\d+) TID=(\d+) WATCH=(\S+) VALUE=(\S+) POST_EIP=(\S+) POST_EIP_RVA=(\S+) STATUS=(\S+)')
    if ($hm.Success) {
        $current = [pscustomobject]@{
            N=[int]$hm.Groups[1].Value; Tid=[int]$hm.Groups[2].Value; Value=$hm.Groups[4].Value.ToUpperInvariant();
            PostRva=$hm.Groups[6].Value.ToUpperInvariant(); Status=$hm.Groups[7].Value; Writers=New-Object System.Collections.Generic.List[object]
        }
        $decodedHits.Add($current)
        continue
    }
    if ($null -eq $current) { continue }
    $im = [regex]::Match($line,'^\s+INSN VA=(\S+) RVA=(\S+) SIZE=(\d+) END=(\S+) TARGET_WRITE=(YES|NO) SRC_KIND=(\S+) SRC=(\S+) MNEMONIC=(\S+) OPS=(.*)$')
    if (-not $im.Success -or $im.Groups[5].Value -ne 'YES') { continue }
    $rva = Hex64 $im.Groups[2].Value
    if ($null -eq $rva) { continue }
    $current.Writers.Add([pscustomobject]@{
        Rva=[uint64]$rva; Size=[int]$im.Groups[3].Value; SourceKind=$im.Groups[6].Value; Source=$im.Groups[7].Value.ToUpperInvariant();
        Mnemonic=$im.Groups[8].Value; Ops=$im.Groups[9].Value
    })
}

$matches = New-Object System.Collections.Generic.List[object]
foreach ($hit in $decodedHits) {
    if ($hit.Status -ne 'PROVEN_TARGET_WRITER' -or $hit.Writers.Count -ne 1) { continue }
    $writer = $hit.Writers[0]
    $storeMatches = @($stores | Where-Object { $_.StartRva -eq $writer.Rva })
    foreach ($store in $storeMatches) {
        $sem = 'AMBIGUOUS'
        if ($hit.Value -eq '0X00000000' -and $store.Kind -eq 'STORE_IMM_C705' -and $store.Imm -eq '0X00000000' -and $writer.SourceKind -eq 'IMM' -and $writer.Source -eq '0X00000000') {
            $sem = 'TEARDOWN_ZERO_CLEAR'
        }
        elseif ($hit.Value -match '^0X[0-9A-F]{8}$' -and $hit.Value -ne '0X00000000' -and ($store.Kind -eq 'STORE_A3' -or $store.Kind.StartsWith('STORE_89_')) -and $writer.SourceKind -eq 'REG') {
            $sem = 'NONZERO_REGISTER_ASSIGNMENT'
        }
        $matches.Add([pscustomobject]@{Hit=$hit;Writer=$writer;Store=$store;Semantics=$sem})
    }
}

$teardown = @($matches | Where-Object { $_.Semantics -eq 'TEARDOWN_ZERO_CLEAR' })
$assign = @($matches | Where-Object { $_.Semantics -eq 'NONZERO_REGISTER_ASSIGNMENT' })
$status = 'OWNER_PROMOTION_NOT_YET'
$next = 'Capture decoder-aligned assignment and teardown writes, then repeat in a fresh process.'
if (-not $identityPass) {
    $status = 'REJECT_IDENTITY_OR_SAFETY_GATE'
    $next = 'Rerun all precision gates in one unchanged authoritative Lin.bin2 process.'
}
elseif ($decSummary -eq 'DECODER_AMBIGUOUS') {
    $status = 'REJECT_DECODER_AMBIGUOUS'
    $next = 'Inspect only the ambiguous decoder window; do not promote writer evidence.'
}
elseif ($teardown.Count -gt 0 -and $assign.Count -gt 0) {
    $status = 'OWNER_ANCHOR_STRONG_DECODER_BACKED_CANDIDATE'
    $next = 'Repeat the same precision gate in a fresh Lin.bin2 process and require stable writer RVAs before owner promotion.'
}
elseif ($teardown.Count -gt 0) {
    $status = 'PARTIAL_DECODER_BACKED_TEARDOWN'
    $next = 'Start the watch before entering the game world to capture a decoder-backed nonzero assignment.'
}
elseif ($assign.Count -gt 0) {
    $status = 'PARTIAL_DECODER_BACKED_ASSIGNMENT'
    $next = 'Perform a normal logout/relogin under the same watch to capture the decoder-backed zero clear.'
}
elseif (@($decodedHits | Where-Object { $_.Status -eq 'PROVEN_TARGET_WRITER' }).Count -gt 0) {
    $status = 'DECODER_WRITER_NOT_MATCHED_TO_V4B_STORE'
    $next = 'Use the decoder instruction itself as the next narrow analysis target; do not widen memory scanning.'
}

$out = New-Object System.Collections.Generic.List[string]
$out.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$out.Add('MODE=850_INVENTORY_ROOT_PRECISION_REVIEW')
$out.Add("IDENTITY_SAFETY_GATE=$(if($identityPass){'PASS'}else{'FAIL'})")
$out.Add("PID=$watchPid")
$out.Add("PROCESS_START_UTC=$watchStart")
$out.Add("DECODER_STATUS=$decSummary")
$out.Add("V4B_STORE_COUNT=$($stores.Count)")
$out.Add("DECODER_HITS=$($decodedHits.Count)")
$out.Add("CORRELATED_MATCHES=$($matches.Count)")
$out.Add("CORRELATED_ASSIGNMENTS=$($assign.Count)")
$out.Add("CORRELATED_TEARDOWNS=$($teardown.Count)")
$out.Add('')
$out.Add('[MATCHES]')
foreach ($m in $matches) {
    $out.Add(('HIT={0} VALUE={1} SEM={2} WRITER_RVA=0x{3:X8} KIND={4} SRC={5}:{6} OPS={7}' -f $m.Hit.N,$m.Hit.Value,$m.Semantics,$m.Writer.Rva,$m.Store.Kind,$m.Writer.SourceKind,$m.Writer.Source,$m.Writer.Ops))
}
$out.Add('')
$out.Add('[DECISION]')
$out.Add("STATUS=$status")
$out.Add('OWNER_PROMOTION=NOT_YET')
$out.Add('FORMAL_WP5=NOT_YET')
$out.Add('FORMAL_WP6=NOT_YET')
$out.Add("NEXT=$next")
$out.Add('RAW_E8_USED=NO')
$out.Add('DECODER_BACKED=YES')
$out.Add('HEAP_SCAN=NO')
$out.Add('MEM_PRIVATE_SCAN=NO')
$out.Add('MEMORY_WRITE=NO')

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }
[IO.File]::WriteAllLines($OutputPath,$out,[Text.UTF8Encoding]::new($false))
$out | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
