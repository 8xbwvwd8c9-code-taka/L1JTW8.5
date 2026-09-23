param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_ui_anchor_static_scan.txt"
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path -LiteralPath $ClientPath)) {
    throw "Client not found: $ClientPath"
}

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
public static class ByteFinder850 {
    public static int[] Find(byte[] data, byte[] pattern, int start, int length, int maxHits) {
        var hits = new List<int>();
        if (data == null || pattern == null || pattern.Length == 0 || length <= 0) return hits.ToArray();
        int end = Math.Min(data.Length, start + length) - pattern.Length;
        if (start < 0) start = 0;
        for (int i = start; i <= end; i++) {
            if (data[i] != pattern[0]) continue;
            bool ok = true;
            for (int j = 1; j < pattern.Length; j++) {
                if (data[i + j] != pattern[j]) { ok = false; break; }
            }
            if (!ok) continue;
            hits.Add(i);
            if (hits.Count >= maxHits) break;
        }
        return hits.ToArray();
    }
}
"@

function U16([byte[]]$b, [int]$o) { [BitConverter]::ToUInt16($b, $o) }
function U32([byte[]]$b, [int]$o) { [BitConverter]::ToUInt32($b, $o) }

$bytes = [IO.File]::ReadAllBytes($ClientPath)
if ($bytes.Length -lt 0x100 -or (U16 $bytes 0) -ne 0x5A4D) { throw "Not a valid MZ file" }
$pe = [int](U32 $bytes 0x3C)
if ((U32 $bytes $pe) -ne 0x00004550) { throw "Invalid PE signature" }
$fileHeader = $pe + 4
$sectionCount = [int](U16 $bytes ($fileHeader + 2))
$optionalSize = [int](U16 $bytes ($fileHeader + 16))
$optional = $fileHeader + 20
if ((U16 $bytes $optional) -ne 0x10B) { throw "Only PE32/x86 is supported" }
$imageBase = [uint32](U32 $bytes ($optional + 28))
$sizeOfImage = [uint32](U32 $bytes ($optional + 56))
$sectionTable = $optional + $optionalSize
$sections = @()
for ($i=0; $i -lt $sectionCount; $i++) {
    $off = $sectionTable + ($i * 40)
    if ($off + 40 -gt $bytes.Length) { break }
    $nameBytes = $bytes[$off..($off+7)]
    $zero = [Array]::IndexOf($nameBytes, [byte]0)
    if ($zero -lt 0) { $zero = 8 }
    $name = [Text.Encoding]::ASCII.GetString($nameBytes,0,$zero)
    $vs = [uint32](U32 $bytes ($off+8))
    $va = [uint32](U32 $bytes ($off+12))
    $rawSize = [uint32](U32 $bytes ($off+16))
    $raw = [uint32](U32 $bytes ($off+20))
    $chars = [uint32](U32 $bytes ($off+36))
    $sections += [pscustomobject]@{
        Name=$name; VA=$va; VS=$vs; Raw=$raw; RawSize=$rawSize; Chars=$chars;
        Executable=(($chars -band 0x20000000) -ne 0)
    }
}

function Raw-ToRva([int]$rawOffset) {
    foreach ($s in $sections) {
        $span = [Math]::Max([long]$s.VS,[long]$s.RawSize)
        if ($rawOffset -ge [long]$s.Raw -and $rawOffset -lt ([long]$s.Raw + [long]$s.RawSize)) {
            return [long]$s.VA + ($rawOffset - [long]$s.Raw)
        }
    }
    if ($rawOffset -ge 0 -and $rawOffset -lt $bytes.Length) { return [long]$rawOffset }
    return -1L
}

function Find-FunctionRva([int]$xrefRaw, $section) {
    $min = [Math]::Max([int]$section.Raw, $xrefRaw - 0x500)
    for ($i=$xrefRaw; $i -ge $min; $i--) {
        if ($i + 2 -lt $bytes.Length -and $bytes[$i] -eq 0x55 -and $bytes[$i+1] -eq 0x8B -and $bytes[$i+2] -eq 0xEC) {
            return [long]$section.VA + ($i - [long]$section.Raw)
        }
        if ($i + 4 -lt $bytes.Length -and $bytes[$i] -eq 0x8B -and $bytes[$i+1] -eq 0xFF -and $bytes[$i+2] -eq 0x55 -and $bytes[$i+3] -eq 0x8B -and $bytes[$i+4] -eq 0xEC) {
            return [long]$section.VA + ($i - [long]$section.Raw)
        }
    }
    for ($i=$xrefRaw-1; $i -ge $min; $i--) {
        if ($bytes[$i] -eq 0xCC -or $bytes[$i] -eq 0xC3) {
            return [long]$section.VA + (($i + 1) - [long]$section.Raw)
        }
        if ($bytes[$i] -eq 0xC2 -and $i + 2 -lt $bytes.Length) {
            return [long]$section.VA + (($i + 3) - [long]$section.Raw)
        }
    }
    return -1L
}

$targets = @(
    [pscustomobject]@{Area='HPMP'; Token='HPGauge'},
    [pscustomobject]@{Area='HPMP'; Token='MPGauge'},
    [pscustomobject]@{Area='HPMP'; Token='HpGauge_Image'},
    [pscustomobject]@{Area='HPMP'; Token='MpGauge_Image'},
    [pscustomobject]@{Area='HPMP'; Token='EquipNStatusUI.xml'},
    [pscustomobject]@{Area='HPMP'; Token='ReNStatusUIEx-c.xml'},
    [pscustomobject]@{Area='INVENTORY'; Token='ReInventory.xml'},
    [pscustomobject]@{Area='INVENTORY'; Token='InventoryItemGrid'},
    [pscustomobject]@{Area='INVENTORY'; Token='ItemCountLabel'},
    [pscustomobject]@{Area='INVENTORY'; Token='InventoryScroll'},
    [pscustomobject]@{Area='INVENTORY'; Token='DeleteItem'},
    [pscustomobject]@{Area='INVENTORY'; Token='InvWin'},
    [pscustomobject]@{Area='PET'; Token='PetSummonUI.xml'},
    [pscustomobject]@{Area='PET'; Token='Action_PetWin'},
    [pscustomobject]@{Area='PET'; Token='Click_PetWin'},
    [pscustomobject]@{Area='PET'; Token='Pet_Button0'},
    [pscustomobject]@{Area='PET'; Token='HP_Image'},
    [pscustomobject]@{Area='SUMMON'; Token='SummonUI.xml'},
    [pscustomobject]@{Area='SUMMON'; Token='SummonButton'},
    [pscustomobject]@{Area='SUMMON'; Token='SummonLevelButton'},
    [pscustomobject]@{Area='BUFF'; Token='RankBuffMatching.xml'},
    [pscustomobject]@{Area='BUFF'; Token='effectlist2-c.xml'},
    [pscustomobject]@{Area='SKILL'; Token='SpellUI.xml'},
    [pscustomobject]@{Area='SKILL'; Token='SpellList.xml'},
    [pscustomobject]@{Area='DOLL'; Token='MagicDollDesc.xml'}
)

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_RESOURCE_GUIDED_UI_ANCHOR_STATIC_SCAN")
$lines.Add("CLIENT=$ClientPath")
$lines.Add("CLIENT_SHA256=$((Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash)")
$lines.Add(("FILE_SIZE={0}" -f $bytes.Length))
$lines.Add(("IMAGE_BASE=0x{0:X8}" -f $imageBase))
$lines.Add(("SIZE_OF_IMAGE={0}" -f $sizeOfImage))
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("")

$totalStringHits = 0
$totalXrefs = 0
foreach ($target in $targets) {
    $ascii = [Text.Encoding]::ASCII.GetBytes($target.Token)
    $utf16 = [Text.Encoding]::Unicode.GetBytes($target.Token)
    $hits = @()
    foreach ($h in [ByteFinder850]::Find($bytes,$ascii,0,$bytes.Length,64)) {
        $hits += [pscustomobject]@{Raw=[int]$h; Encoding='ASCII'}
    }
    foreach ($h in [ByteFinder850]::Find($bytes,$utf16,0,$bytes.Length,64)) {
        $hits += [pscustomobject]@{Raw=[int]$h; Encoding='UTF16LE'}
    }

    $lines.Add("[TARGET $($target.Area):$($target.Token)]")
    $lines.Add("STRING_HITS=$($hits.Count)")
    $totalStringHits += $hits.Count
    $targetXrefs = 0

    foreach ($hit in $hits) {
        $rva = Raw-ToRva $hit.Raw
        if ($rva -lt 0) { continue }
        $va64 = [long]$imageBase + $rva
        if ($va64 -lt 0 -or $va64 -gt [uint32]::MaxValue) { continue }
        $va = [uint32]$va64
        $lines.Add(("STRING ENCODING={0} RAW=0x{1:X8} RVA=0x{2:X8} VA=0x{3:X8}" -f $hit.Encoding,$hit.Raw,$rva,$va))
        $needle = [BitConverter]::GetBytes($va)

        foreach ($s in $sections | Where-Object { $_.Executable -and $_.RawSize -gt 0 }) {
            $start = [int]$s.Raw
            $length = [int][Math]::Min([long]$s.RawSize, [long]$bytes.Length - $start)
            if ($start -lt 0 -or $length -le 0) { continue }
            foreach ($xr in [ByteFinder850]::Find($bytes,$needle,$start,$length,128)) {
                $xrefRva = [long]$s.VA + ([long]$xr - [long]$s.Raw)
                $funcRva = Find-FunctionRva ([int]$xr) $s
                $lines.Add(("XREF STR_RVA=0x{0:X8} XREF_RVA=0x{1:X8} FUNC_RVA={2}" -f $rva,$xrefRva,($(if($funcRva -ge 0){'0x{0:X8}' -f $funcRva}else{'NOT_FOUND'}))))
                $targetXrefs++
                $totalXrefs++
            }
        }
    }
    $lines.Add("XREFS=$targetXrefs")
    $lines.Add("")
}

$lines.Add("TOTAL_STRING_HITS=$totalStringHits")
$lines.Add("TOTAL_XREFS=$totalXrefs")
$lines.Add("STATUS=PASS_STATIC_SCAN")
$lines.Add("NOTE=Resource-derived anchors are static evidence only; runtime mappings still require semantic and restart validation.")
$lines | Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=PASS_STATIC_SCAN"
Write-Host "OUTPUT=$OutputPath"
Write-Host "SOURCE_MODIFIED=NO"
