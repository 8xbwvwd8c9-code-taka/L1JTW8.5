param(
  [string]$A = "I:\8.50c客服端\Lin.bin",
  [string]$B = "I:\8.50c客服端\Lin.bin2",
  [string]$OutDir = "I:\8.50c客服端\850_COMPARE"
)

$ErrorActionPreference = "Stop"
New-Item -ItemType Directory -Force -Path $OutDir | Out-Null

function Read-PE([string]$Path) {
  $bytes = [IO.File]::ReadAllBytes($Path)
  if ($bytes[0] -ne 0x4D -or $bytes[1] -ne 0x5A) { throw "Not MZ: $Path" }
  $lf = [BitConverter]::ToInt32($bytes,0x3C)
  if ($bytes[$lf] -ne 0x50 -or $bytes[$lf+1] -ne 0x45) { throw "Not PE: $Path" }
  $machine=[BitConverter]::ToUInt16($bytes,$lf+4)
  $nsec=[BitConverter]::ToUInt16($bytes,$lf+6)
  $opt=[BitConverter]::ToUInt16($bytes,$lf+20)
  $magic=[BitConverter]::ToUInt16($bytes,$lf+24)
  $imageBase = if($magic -eq 0x10b){ [BitConverter]::ToUInt32($bytes,$lf+52) } else { [BitConverter]::ToUInt64($bytes,$lf+48) }
  $entry=[BitConverter]::ToUInt32($bytes,$lf+40)
  $secOff=$lf+24+$opt
  $secs=@()
  for($i=0;$i -lt $nsec;$i++){
    $o=$secOff+40*$i
    $nameBytes=$bytes[$o..($o+7)]
    $name=([Text.Encoding]::ASCII.GetString($nameBytes)).Trim([char]0)
    $vsize=[BitConverter]::ToUInt32($bytes,$o+8)
    $vaddr=[BitConverter]::ToUInt32($bytes,$o+12)
    $rawsize=[BitConverter]::ToUInt32($bytes,$o+16)
    $rawptr=[BitConverter]::ToUInt32($bytes,$o+20)
    $chars=[BitConverter]::ToUInt32($bytes,$o+36)
    $secs += [pscustomobject]@{Name=$name;VSize=$vsize;RVA=('0x{0:X8}' -f $vaddr);RawSize=$rawsize;RawPtr=('0x{0:X8}' -f $rawptr);Characteristics=('0x{0:X8}' -f $chars)}
  }
  [pscustomobject]@{
    Path=$Path; Length=$bytes.Length; Machine=('0x{0:X4}' -f $machine); Sections=$nsec;
    Magic=('0x{0:X4}' -f $magic); ImageBase=('0x{0:X}' -f $imageBase); EntryRVA=('0x{0:X8}' -f $entry);
    SectionTable=$secs; Bytes=$bytes
  }
}

$a=Read-PE $A
$b=Read-PE $B

$a.SectionTable | Format-Table -AutoSize | Out-String | Set-Content -Encoding utf8 (Join-Path $OutDir "A_SECTIONS.txt")
$b.SectionTable | Format-Table -AutoSize | Out-String | Set-Content -Encoding utf8 (Join-Path $OutDir "B_SECTIONS.txt")

$min=[Math]::Min($a.Bytes.Length,$b.Bytes.Length)
$first=-1; $last=-1; [long]$diff=0
$chunks = New-Object System.Collections.Generic.List[object]
$inRun=$false; $runStart=0; $runDiff=0
for($i=0;$i -lt $min;$i++){
  $same = ($a.Bytes[$i] -eq $b.Bytes[$i])
  if(-not $same){
    if($first -lt 0){$first=$i}
    $last=$i; $diff++
    if(-not $inRun){$inRun=$true; $runStart=$i; $runDiff=0}
    $runDiff++
  } elseif($inRun){
    $chunks.Add([pscustomobject]@{Start=('0x{0:X8}' -f $runStart);End=('0x{0:X8}' -f ($i-1));Length=($i-$runStart);DiffBytes=$runDiff})
    $inRun=$false
  }
}
if($inRun){$chunks.Add([pscustomobject]@{Start=('0x{0:X8}' -f $runStart);End=('0x{0:X8}' -f ($min-1));Length=($min-$runStart);DiffBytes=$runDiff})}
$tail=[Math]::Abs($a.Bytes.Length-$b.Bytes.Length)
$diff += $tail

$chunks | Sort-Object Length -Descending | Select-Object -First 200 |
  Export-Csv -NoTypeInformation -Encoding utf8 (Join-Path $OutDir "DIFF_RUNS_TOP200.csv")

$summary=@(
 "STATUS=PASS",
 "A=$A",
 "B=$B",
 "A_SIZE=$($a.Length)",
 "B_SIZE=$($b.Length)",
 "A_IMAGEBASE=$($a.ImageBase)",
 "B_IMAGEBASE=$($b.ImageBase)",
 "A_ENTRY_RVA=$($a.EntryRVA)",
 "B_ENTRY_RVA=$($b.EntryRVA)",
 "A_SECTIONS=$($a.Sections)",
 "B_SECTIONS=$($b.Sections)",
 "COMMON_BYTES=$min",
 "DIFF_BYTES_COMMON=$($diff-$tail)",
 "TAIL_BYTES=$tail",
 "TOTAL_DIFF_BYTES=$diff",
 "FIRST_DIFF=" + ($(if($first -ge 0){'0x{0:X8}' -f $first}else{'NONE'})),
 "LAST_DIFF_COMMON=" + ($(if($last -ge 0){'0x{0:X8}' -f $last}else{'NONE'})),
 "DIFF_RUN_COUNT=$($chunks.Count)",
 "NEXT=Use section layout + diff concentration to decide whether Lin.bin2 is a near build, patched variant, or substantially different client."
)
$summary | Set-Content -Encoding utf8 (Join-Path $OutDir "SUMMARY.txt")
$summary | ForEach-Object { Write-Host $_ }
