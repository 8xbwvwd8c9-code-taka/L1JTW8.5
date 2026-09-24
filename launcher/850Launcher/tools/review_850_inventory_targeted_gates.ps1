param(
    [string]$OutputRoot = "I:\L共通工具\LineageAIResourceToolkit\outputs",
    [string]$ReviewPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_targeted_gate_review.txt"
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

$paths = [ordered]@{
    V4C = Join-Path $OutputRoot '850_inventory_vtable_rtti_v4c.txt'
    V4B = Join-Path $OutputRoot '850_inventory_root_global_xref_v4b.txt'
    V4  = Join-Path $OutputRoot '850_inventory_ctor_owner_trace_v4.txt'
}

foreach ($kv in $paths.GetEnumerator()) {
    if (-not (Test-Path -LiteralPath $kv.Value)) {
        throw "Missing targeted gate output: $($kv.Key) $($kv.Value)"
    }
}

function Read-Lines([string]$Path) {
    return [IO.File]::ReadAllLines($Path)
}

function Last-Key([string[]]$Lines, [string]$Key) {
    $prefix = $Key + '='
    for ($i = $Lines.Length - 1; $i -ge 0; $i--) {
        if ($Lines[$i].StartsWith($prefix, [StringComparison]::Ordinal)) {
            return $Lines[$i].Substring($prefix.Length).Trim()
        }
    }
    return ''
}

function Int-Key([string[]]$Lines, [string]$Key) {
    $text = Last-Key $Lines $Key
    $value = 0
    if ([int]::TryParse($text, [ref]$value)) {
        return $value
    }
    return 0
}

function Collect-VtableTypes([string[]]$Lines) {
    $output = New-Object System.Collections.Generic.List[object]
    $section = ''
    foreach ($line in $Lines) {
        if ($line -match '^\[(VTABLE_(GRID|ROOT|INVWIN))\]$') {
            $section = $Matches[2]
            continue
        }
        if ($line.StartsWith('[', [StringComparison]::Ordinal)) {
            $section = ''
            continue
        }
        if ($section -and $line.StartsWith('TYPE_NAME=', [StringComparison]::Ordinal)) {
            $output.Add([pscustomobject]@{
                Owner = $section
                TypeName = $line.Substring('TYPE_NAME='.Length).Trim()
            })
            $section = ''
        }
    }
    return $output
}

function Count-QualifiedExpectedXrefs([string[]]$Lines) {
    $count = 0
    foreach ($line in $Lines) {
        if ($line -match '^NAME=(INVWIN_A|GRID_A|ROOT_A|INVWIN_B|GRID_B|ROOT_B)\s+OWNER=\S+\s+RVA=0x[0-9A-Fa-f]+\s+COUNT=1\s+QUALIFIED=1$') {
            $count++
        }
    }
    return $count
}

$v4c = Read-Lines $paths.V4C
$v4b = Read-Lines $paths.V4B
$v4 = Read-Lines $paths.V4

$sets = @(
    [pscustomobject]@{ Name = 'V4C'; Lines = $v4c },
    [pscustomobject]@{ Name = 'V4B'; Lines = $v4b },
    [pscustomobject]@{ Name = 'V4'; Lines = $v4 }
)

$identityRows = New-Object System.Collections.Generic.List[object]
foreach ($s in $sets) {
    $identityRows.Add([pscustomobject]@{
        Name = $s.Name
        Sha = Last-Key $s.Lines 'CLIENT_SHA256'
        Pid = Last-Key $s.Lines 'PID'
        ProcessStartUtc = Last-Key $s.Lines 'PROCESS_START_UTC'
        Authority = Last-Key $s.Lines 'CLIENT_AUTHORITY'
    })
}

$identityOk = $true
$first = $identityRows[0]
foreach ($r in $identityRows) {
    if ($r.Sha -ne $ExpectedSha256 -or
        $r.Authority -ne '1' -or
        [string]::IsNullOrWhiteSpace($r.Pid) -or
        [string]::IsNullOrWhiteSpace($r.ProcessStartUtc) -or
        $r.Sha -ne $first.Sha -or
        $r.Pid -ne $first.Pid -or
        $r.ProcessStartUtc -ne $first.ProcessStartUtc) {
        $identityOk = $false
    }
}

$types = @(Collect-VtableTypes $v4c)
$namedTypes = @($types | Where-Object { -not [string]::IsNullOrWhiteSpace($_.TypeName) })

$globalClass = Last-Key $v4b 'SEED_GLOBAL_CLASS'
$globalXrefs = Int-Key $v4b 'GLOBAL_XREF_COUNT'
$ctorStores = Int-Key $v4b 'CTOR_STORE_NEAR_ROOT'
$teardownC705 = Int-Key $v4b 'TEARDOWN_C705_STORE_NEAR_ROOT'
$teardownZero = Int-Key $v4b 'TEARDOWN_ZERO_STORE_NEAR_ROOT'
$v4bPromotion = Last-Key $v4b 'OWNER_PROMOTION'

$qualifiedXrefs = Int-Key $v4 'QUALIFIED_EXPECTED_XREFS'
if ($qualifiedXrefs -eq 0) {
    $qualifiedXrefs = Count-QualifiedExpectedXrefs $v4
}
$functionGroups = Int-Key $v4 'FUNCTION_GROUPS'
$callAlignment = Last-Key $v4 'CALL_ALIGNMENT_PROOF'
$rawE8Promotable = Last-Key $v4 'RAW_E8_CALLERS_PROMOTABLE'

$review = New-Object System.Collections.Generic.List[string]
$review.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$review.Add('MODE=850_INVENTORY_TARGETED_GATE_OFFLINE_REVIEW')
$review.Add('MEMORY_ACCESS=NONE')
$review.Add('MEMORY_WRITE=NO')
$review.Add('HEAP_SCAN=NO')
$review.Add('MEM_PRIVATE_SCAN=NO')
$review.Add('')

$review.Add('[IDENTITY]')
foreach ($r in $identityRows) {
    $review.Add("$($r.Name) SHA=$($r.Sha) PID=$($r.Pid) PROCESS_START_UTC=$($r.ProcessStartUtc) AUTHORITY=$($r.Authority)")
}
$review.Add("IDENTITY_MATCH=$(if ($identityOk) { 'YES' } else { 'NO' })")

$review.Add('')
$review.Add('[V4C_RTTI]')
$review.Add("TYPE_NAME_COUNT=$($namedTypes.Count)")
foreach ($t in $types) {
    $review.Add("$($t.Owner)_TYPE_NAME=$($t.TypeName)")
}
$review.Add("RTTI_STATUS=$(Last-Key $v4c 'STATUS')")

$review.Add('')
$review.Add('[V4B_ROOT_GLOBAL]')
$review.Add("SEED_GLOBAL_CLASS=$globalClass")
$review.Add("GLOBAL_XREF_COUNT=$globalXrefs")
$review.Add("CTOR_STORE_NEAR_ROOT=$ctorStores")
$review.Add("TEARDOWN_C705_STORE_NEAR_ROOT=$teardownC705")
$review.Add("TEARDOWN_ZERO_STORE_NEAR_ROOT=$teardownZero")
$review.Add("SCRIPT_OWNER_PROMOTION=$v4bPromotion")
$review.Add("ZERO_CLEAR_PROVEN=$(if ($teardownZero -gt 0) { 'YES' } else { 'NO' })")

$review.Add('')
$review.Add('[V4_SIX_XREF]')
$review.Add("QUALIFIED_EXPECTED_XREFS=$qualifiedXrefs")
$review.Add("FUNCTION_GROUPS=$functionGroups")
$review.Add("CALL_ALIGNMENT_PROOF=$callAlignment")
$review.Add("RAW_E8_CALLERS_PROMOTABLE=$rawE8Promotable")
$review.Add("SIX_XREF_GATE=$(if ($qualifiedXrefs -eq 6) { 'PASS' } else { 'FAIL' })")

$review.Add('')
$review.Add('[ADJUDICATION]')
if (-not $identityOk) {
    $review.Add('STATUS=REJECT_IDENTITY_MISMATCH')
    $review.Add('OWNER_PROMOTION=NO')
    $review.Add('NEXT=Rerun run_850_inventory_next_gate.ps1 once; do not combine outputs from different client processes.')
} elseif ($qualifiedXrefs -ne 6) {
    $review.Add('STATUS=REJECT_SIX_XREF_GATE')
    $review.Add('OWNER_PROMOTION=NO')
    $review.Add('NEXT=Inspect only the missing known xref; do not widen scan scope.')
} elseif ($callAlignment -ne 'NO' -or $rawE8Promotable -ne 'NO') {
    $review.Add('STATUS=REJECT_CALLER_PROOF_STATE')
    $review.Add('OWNER_PROMOTION=NO')
    $review.Add('NEXT=Raw E8 candidates must remain non-promotable until instruction-boundary proof exists.')
} elseif ($ctorStores -gt 0 -and $teardownZero -gt 0) {
    $review.Add('STATUS=OWNER_ANCHOR_CANDIDATE_FOR_MANUAL_REVIEW')
    $review.Add('OWNER_PROMOTION=NOT_AUTOMATIC')
    $review.Add('NEXT=Inspect exact V4b construction store kind/destination and V4 six-xref function context. If they prove the same ROOT owner slot, prepare one narrow fixed-offset read-only probe.')
} else {
    $review.Add('STATUS=OWNER_PROMOTION_NOT_YET')
    $review.Add('OWNER_PROMOTION=NO')
    if ($namedTypes.Count -gt 0) {
        $review.Add('NEXT=Review V4c RTTI names/hierarchy first; no heap/vector scan.')
    } else {
        $review.Add('NEXT=Need stronger construction-side ROOT global evidence inside existing targeted scope; no heap/vector scan.')
    }
}

$review.Add('RAW_E8_CALLER_EVIDENCE=DIAGNOSTIC_ONLY')
$review.Add('FORMAL_WP5=NOT_YET')
$review.Add('FORMAL_WP6=NOT_YET')
$review.Add('MEMORY_WRITE=NO')

$parent = Split-Path -Parent $ReviewPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}

[IO.File]::WriteAllLines($ReviewPath, $review, [Text.UTF8Encoding]::new($false))

foreach ($line in $review) {
    Write-Host $line
}
Write-Host "OUTPUT=$ReviewPath"
