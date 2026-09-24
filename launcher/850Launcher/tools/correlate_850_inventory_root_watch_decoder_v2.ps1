param(
    [string]$WatchPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch.txt',
    [string]$DecoderPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_watch_capstone.txt',
    [string]$V4bPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b.txt',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_watch_decoder_correlation_v2.txt'
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

foreach ($p in @($WatchPath,$DecoderPath,$V4bPath)) {
    if (-not (Test-Path -LiteralPath $p)) { throw "Missing evidence file: $p" }
}

$watchLines = @(Get-Content -LiteralPath $WatchPath)
$decoderLines = @(Get-Content -LiteralPath $DecoderPath)
$v4bLines = @(Get-Content -LiteralPath $V4bPath)

function First-Value([string[]]$Source,[string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -First 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length)
}
function Last-Value([string[]]$Source,[string]$Prefix) {
    $line = $Source | Where-Object { $_ -like "$Prefix*" } | Select-Object -Last 1
    if (-not $line) { return '' }
    return $line.Substring($Prefix.Length)
}
function Hex-ToUInt64([string]$Text) {
    if ([string]::IsNullOrWhiteSpace($Text) -or -not $Text.StartsWith('0x',[StringComparison]::OrdinalIgnoreCase)) { return $null }
    return [Convert]::ToUInt64($Text.Substring(2),16)
}

$watchSha = First-Value $watchLines 'CLIENT_SHA256='
$watchPid = First-Value $watchLines 'PID='
$watchStart = First-Value $watchLines 'PROCESS_START_UTC='
$watchAuthority = First-Value $watchLines 'CLIENT_AUTHORITY='
$watchMemoryWrite = First-Value $watchLines 'TARGET_MEMORY_WRITE='

$decoderSha = First-Value $decoderLines 'CLIENT_SHA256='
$decoderPid = First-Value $decoderLines 'PID='
$decoderStart = First-Value $decoderLines 'PROCESS_START_UTC='
$decoderAuthority = First-Value $decoderLines 'CLIENT_AUTHORITY='
$decoderMemoryWrite = First-Value $decoderLines 'TARGET_MEMORY_WRITE='
$decoderVersion = First-Value $decoderLines 'CAPSTONE_VERSION='

$v4bSha = First-Value $v4bLines 'CLIENT_SHA256='
$v4bPid = First-Value $v4bLines 'PID='
$v4bStart = Last-Value $v4bLines 'PROCESS_START_UTC='
$v4bAuthority = Last-Value $v4bLines 'CLIENT_AUTHORITY='
$v4bMemoryWrite = Last-Value $v4bLines 'MEMORY_WRITE='

$identityPass =
    $watchSha -eq $ExpectedSha256 -and
    $decoderSha -eq $ExpectedSha256 -and
    $v4bSha -eq $ExpectedSha256 -and
    $watchAuthority -eq '1' -and
    $decoderAuthority -eq '1' -and
    $v4bAuthority -eq '1' -and
    $watchPid -eq $decoderPid -and $watchPid -eq $v4bPid -and
    -not [string]::IsNullOrWhiteSpace($watchStart) -and
    $watchStart -eq $decoderStart -and $watchStart -eq $v4bStart -and
    $watchMemoryWrite -eq 'NO' -and
    $decoderMemoryWrite -eq 'NO' -and
    $v4bMemoryWrite -eq 'NO' -and
    $decoderVersion -eq '5.0.9'

$stores = New-Object System.Collections.Generic.List[object]
foreach ($line in $v4bLines) {
    $m = [regex]::Match($line,'^REF RVA=(0x[0-9A-Fa-f]+) KIND=(\S+) IMM32=(\S+)')
    if (-not $m.Success) { continue }
    $kind = $m.Groups[2].Value
    if ($kind -ne 'STORE_A3' -and $kind -ne 'STORE_IMM_C705' -and -not $kind.StartsWith('STORE_89_')) { continue }
    $literalRva = Hex-ToUInt64 $m.Groups[1].Value
    if ($null -eq $literalRva) { continue }
    [uint64]$postDelta = if ($kind -eq 'STORE_IMM_C705') { 8 } else { 4 }
    $stores.Add([pscustomobject]@{
        LiteralRva=[uint64]$literalRva
        PostEipRva=[uint64]$literalRva+$postDelta
        Kind=$kind
        Imm32=$m.Groups[3].Value.ToUpperInvariant()
    })
}

$watchHits = @{}
foreach ($line in $watchLines) {
    $m = [regex]::Match($line,'^HIT NAME=(\S+)\s+N=(\d+)\s+TID=(\d+)\s+WATCH=(\S+)\s+POST_WRITE_VALUE=(\S+)\s+EIP=(\S+)\s+EIP_RVA=(\S+)')
    if (-not $m.Success) { continue }
    $n=[int]$m.Groups[2].Value
    $watchHits[$n]=[pscustomobject]@{
        N=$n; Tid=[int]$m.Groups[3].Value; Value=$m.Groups[5].Value.ToUpperInvariant(); EipRva=$m.Groups[7].Value.ToUpperInvariant()
    }
}

$decoderHits = @{}
foreach ($line in $decoderLines) {
    $m = [regex]::Match($line,'^HIT N=(\d+) TID=(\d+) POST_WRITE_VALUE=(\S+) EIP=(\S+) EIP_RVA=(\S+) STATUS=(\S+)')
    if (-not $m.Success) { continue }
    $n=[int]$m.Groups[1].Value
    $decoderHits[$n]=[pscustomobject]@{
        N=$n; Tid=[int]$m.Groups[2].Value; Value=$m.Groups[3].Value.ToUpperInvariant(); EipRva=$m.Groups[5].Value.ToUpperInvariant(); Status=$m.Groups[6].Value
    }
}

$records=New-Object System.Collections.Generic.List[object]
foreach ($n in ($watchHits.Keys | Sort-Object)) {
    $w=$watchHits[$n]
    $d=$decoderHits[$n]
    $decoderPass=$null-ne$d -and $d.Status -eq 'PASS_UNIQUE_TARGET_WRITER' -and $d.EipRva -eq $w.EipRva -and $d.Tid -eq $w.Tid
    $eipRva=Hex-ToUInt64 $w.EipRva
    $matched=@()
    if ($decoderPass -and $null-ne$eipRva) { $matched=@($stores | Where-Object { $_.PostEipRva -eq [uint64]$eipRva }) }
    $records.Add([pscustomobject]@{ N=$n; Watch=$w; Decoder=$d; DecoderPass=$decoderPass; Stores=$matched })
}

$assignmentMatches=New-Object System.Collections.Generic.List[object]
$teardownMatches=New-Object System.Collections.Generic.List[object]
foreach ($r in $records) {
    if (-not $r.DecoderPass) { continue }
    foreach ($s in $r.Stores) {
        if ($r.Watch.Value -eq '0X00000000' -and $s.Kind -eq 'STORE_IMM_C705' -and $s.Imm32 -eq '0X00000000') {
            $teardownMatches.Add([pscustomobject]@{ Record=$r; Store=$s })
        }
        if ($r.Watch.Value -match '^0X[0-9A-F]{8}$' -and $r.Watch.Value -ne '0X00000000' -and ($s.Kind -eq 'STORE_A3' -or $s.Kind.StartsWith('STORE_89_'))) {
            $assignmentMatches.Add([pscustomobject]@{ Record=$r; Store=$s })
        }
    }
}

$status='OWNER_PROMOTION_NOT_YET'
$next='Capture both decoder-proven nonzero assignment and zero teardown clear in one authoritative process.'
if (-not $identityPass) {
    $status='REJECT_IDENTITY_DECODER_GATE'
    $next='Rerun the full precision gate; do not combine evidence across process identities or decoder versions.'
}
elseif ($assignmentMatches.Count -gt 0 -and $teardownMatches.Count -gt 0) {
    $status='OWNER_ANCHOR_STRONG_DECODER_RUNTIME_CANDIDATE'
    $next='Repeat the same precision gate across a fresh Lin.bin2 process; require stable writer RVAs before owner promotion.'
}
elseif ($teardownMatches.Count -gt 0) {
    $status='PARTIAL_DECODER_TEARDOWN'
    $next='Start watch before world entry to capture a decoder-proven nonzero construction assignment.'
}
elseif ($assignmentMatches.Count -gt 0) {
    $status='PARTIAL_DECODER_ASSIGNMENT'
    $next='Perform normal logout/relogin while watch remains active to capture decoder-proven zero teardown.'
}
elseif (@($records | Where-Object {$_.DecoderPass}).Count -gt 0) {
    $status='DECODER_WRITER_PROVEN_STORE_SEMANTICS_UNRESOLVED'
    $next='Inspect only decoder-proven writer RVAs and exact V4b store matches; do not widen memory scope.'
}
elseif ($records.Count -gt 0) {
    $status='WATCH_HITS_WITHOUT_UNIQUE_DECODER_WRITER'
    $next='Do not promote. Inspect decoder ambiguity only; no raw-byte fallback.'
}
else {
    $status='NO_WATCH_HITS'
    $next='Rerun during object lifecycle activity; do not widen scan scope.'
}

$out=New-Object System.Collections.Generic.List[string]
$out.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$out.Add('MODE=850_ROOT_WATCH_DECODER_CORRELATION_V2')
$out.Add("WATCH=$WatchPath")
$out.Add("DECODER=$DecoderPath")
$out.Add("V4B=$V4bPath")
$out.Add("CAPSTONE_VERSION=$decoderVersion")
$out.Add("IDENTITY_GATE=$(if($identityPass){'PASS'}else{'FAIL'})")
$out.Add("WATCH_HITS=$($records.Count)")
$out.Add("DECODER_UNIQUE_WRITERS=$(@($records | Where-Object {$_.DecoderPass}).Count)")
$out.Add("V4B_STORE_CANDIDATES=$($stores.Count)")
$out.Add("CORRELATED_NONZERO_ASSIGNMENTS=$($assignmentMatches.Count)")
$out.Add("CORRELATED_ZERO_CLEARS=$($teardownMatches.Count)")
$out.Add('')
$out.Add('[CORRELATION]')
foreach ($r in $records) {
    $ds=if($null-ne$r.Decoder){$r.Decoder.Status}else{'MISSING'}
    if ($r.Stores.Count -eq 0) {
        $out.Add("HIT N=$($r.N) VALUE=$($r.Watch.Value) EIP_RVA=$($r.Watch.EipRva) DECODER=$ds MATCH=NONE")
    } else {
        foreach ($s in $r.Stores) {
            $out.Add(('HIT N={0} VALUE={1} EIP_RVA={2} DECODER={3} MATCH={4} LITERAL_RVA=0x{5:X8} IMM32={6}' -f $r.N,$r.Watch.Value,$r.Watch.EipRva,$ds,$s.Kind,$s.LiteralRva,$s.Imm32))
        }
    }
}
$out.Add('')
$out.Add('[DECISION]')
$out.Add("STATUS=$status")
$out.Add('OWNER_PROMOTION=NOT_YET')
$out.Add('FORMAL_WP5=NOT_YET')
$out.Add('FORMAL_WP6=NOT_YET')
$out.Add("NEXT=$next")
$out.Add('RAW_BYTE_OPCODE_PROMOTION=NO')
$out.Add('DECODER_ALIGNED_PROMOTION=YES')
$out.Add('HEAP_SCAN=NO')
$out.Add('MEM_PRIVATE_SCAN=NO')
$out.Add('MEMORY_WRITE=NO')

$parent=Split-Path -Parent $OutputPath
if($parent-and-not(Test-Path -LiteralPath $parent)){New-Item -ItemType Directory -Force -Path $parent|Out-Null}
[IO.File]::WriteAllLines($OutputPath,$out,[Text.UTF8Encoding]::new($false))
$out|ForEach-Object{Write-Host $_}
Write-Host "OUTPUT=$OutputPath"
