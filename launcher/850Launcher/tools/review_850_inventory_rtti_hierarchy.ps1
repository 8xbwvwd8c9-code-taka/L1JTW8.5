param(
    [string]$InputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_vtable_rtti_v4c.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_rtti_hierarchy_review.txt"
)

$ErrorActionPreference = 'Stop'

if (-not (Test-Path -LiteralPath $InputPath)) {
    throw "Missing V4C RTTI output: $InputPath"
}

$lines = [IO.File]::ReadAllLines($InputPath)

function Last-Key([string[]]$Lines, [string]$Key) {
    $prefix = $Key + '='
    for ($i = $Lines.Length - 1; $i -ge 0; $i--) {
        if ($Lines[$i].StartsWith($prefix, [StringComparison]::Ordinal)) {
            return $Lines[$i].Substring($prefix.Length).Trim()
        }
    }
    return ''
}

$sections = [ordered]@{}
$current = ''
foreach ($line in $lines) {
    if ($line -match '^\[VTABLE_(GRID|ROOT|INVWIN)\]$') {
        $current = $Matches[1]
        $sections[$current] = [ordered]@{
            TypeName = ''
            BaseCount = ''
            Bases = New-Object System.Collections.Generic.List[object]
        }
        continue
    }

    if ($line.StartsWith('[', [StringComparison]::Ordinal)) {
        $current = ''
        continue
    }

    if (-not $current) { continue }

    if ($line.StartsWith('TYPE_NAME=', [StringComparison]::Ordinal)) {
        $sections[$current].TypeName = $line.Substring('TYPE_NAME='.Length).Trim()
        continue
    }

    if ($line.StartsWith('CHD_BASE_COUNT=', [StringComparison]::Ordinal)) {
        $sections[$current].BaseCount = $line.Substring('CHD_BASE_COUNT='.Length).Trim()
        continue
    }

    if ($line -match '^BASE\[(\d+)\]\s+BCD=0x([0-9A-Fa-f]+)\s+TYPE=(\S+)\s+NAME=(.*?)\s+CONTAINED=(\S+)\s+MDISP=(\S+)\s+PDISP=(\S+)\s+VDISP=(\S+)\s+ATTR=(\S+)$') {
        $sections[$current].Bases.Add([pscustomobject]@{
            Index = [int]$Matches[1]
            Name = $Matches[4].Trim()
            Contained = $Matches[5]
            Mdisp = $Matches[6]
            Pdisp = $Matches[7]
            Vdisp = $Matches[8]
            Attr = $Matches[9]
        })
    }
}

$required = @('GRID','ROOT','INVWIN')
foreach ($name in $required) {
    if (-not $sections.Contains($name)) {
        throw "Missing VTABLE_$name section in $InputPath"
    }
}

$out = New-Object System.Collections.Generic.List[string]
$out.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$out.Add('MODE=850_INVENTORY_RTTI_HIERARCHY_OFFLINE_REVIEW')
$out.Add("SOURCE=$InputPath")
$out.Add("CLIENT_SHA256=$(Last-Key $lines 'CLIENT_SHA256')")
$out.Add("PID=$(Last-Key $lines 'PID')")
$out.Add("PROCESS_START_UTC=$(Last-Key $lines 'PROCESS_START_UTC')")
$out.Add('MEMORY_ACCESS=NONE')
$out.Add('MEMORY_WRITE=NO')
$out.Add('HEAP_SCAN=NO')
$out.Add('MEM_PRIVATE_SCAN=NO')
$out.Add('')

foreach ($name in $required) {
    $s = $sections[$name]
    $out.Add("[$name]")
    $out.Add("TYPE_NAME=$($s.TypeName)")
    $out.Add("CHD_BASE_COUNT=$($s.BaseCount)")
    $out.Add("PARSED_BASES=$($s.Bases.Count)")
    foreach ($b in $s.Bases) {
        $out.Add(("BASE[{0:D2}] NAME={1} CONTAINED={2} MDISP={3} PDISP={4} VDISP={5} ATTR={6}" -f
            $b.Index,$b.Name,$b.Contained,$b.Mdisp,$b.Pdisp,$b.Vdisp,$b.Attr))
    }
    $out.Add('')
}

function Base-Names($section) {
    return @($section.Bases | ForEach-Object { $_.Name } | Where-Object { -not [string]::IsNullOrWhiteSpace($_) })
}

$gridBases = Base-Names $sections.GRID
$rootBases = Base-Names $sections.ROOT
$invBases = Base-Names $sections.INVWIN

$rootMentionsGrid = @($rootBases | Where-Object { $_ -match 'InventoryItemGrid' }).Count -gt 0
$rootMentionsInv = @($rootBases | Where-Object { $_ -match 'InvWindow' }).Count -gt 0
$gridMentionsRoot = @($gridBases | Where-Object { $_ -match 'RenewalInventoryUI' }).Count -gt 0
$invMentionsRoot = @($invBases | Where-Object { $_ -match 'RenewalInventoryUI' }).Count -gt 0

$out.Add('[RELATIONSHIP_CHECK]')
$out.Add("ROOT_BASE_MENTIONS_GRID=$([int]$rootMentionsGrid)")
$out.Add("ROOT_BASE_MENTIONS_INVWIN=$([int]$rootMentionsInv)")
$out.Add("GRID_BASE_MENTIONS_ROOT=$([int]$gridMentionsRoot)")
$out.Add("INVWIN_BASE_MENTIONS_ROOT=$([int]$invMentionsRoot)")
$out.Add('')

# RTTI inheritance is not the same thing as object ownership/member composition.
# This reviewer only decides whether hierarchy can add construction-side owner proof.
$hierarchyAddsOwnerProof = $rootMentionsGrid -or $rootMentionsInv -or $gridMentionsRoot -or $invMentionsRoot

$out.Add('[ADJUDICATION]')
if ($hierarchyAddsOwnerProof) {
    $out.Add('STATUS=RTTI_RELATIONSHIP_CANDIDATE')
    $out.Add('OWNER_PROMOTION=NOT_AUTOMATIC')
    $out.Add('NEXT=Inspect only the exact matching RTTI base relation against the frozen ROOT/GRID/INVWIN member offsets; do not widen scan.')
} else {
    $out.Add('STATUS=RTTI_HIERARCHY_DOES_NOT_PROVE_OWNER')
    $out.Add('OWNER_PROMOTION=NO')
    $out.Add('NEXT=RTTI names establish class identity only. Move to a narrow construction-side store/load proof for ROOT_GLOBAL_RVA=0x012BCEE8; no heap/vector scan.')
}
$out.Add('RTTI_INHERITANCE_IS_NOT_MEMBER_OWNERSHIP=YES')
$out.Add('FORMAL_WP5=NOT_YET')
$out.Add('FORMAL_WP6=NOT_YET')
$out.Add('MEMORY_WRITE=NO')

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}
[IO.File]::WriteAllLines($OutputPath, $out, [Text.UTF8Encoding]::new($false))
$out | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
