param(
  [string]$A = ".\Lin.bin",
  [string]$B = ".\Lin.bin2",
  [string]$OutDir = ".\850_COMPARE"
)

$ErrorActionPreference = "Stop"

$APath = (Resolve-Path -LiteralPath $A).Path
$BPath = (Resolve-Path -LiteralPath $B).Path

if (-not (Test-Path -LiteralPath $OutDir)) {
  New-Item -ItemType Directory -Force -Path $OutDir | Out-Null
}
$OutPath = (Resolve-Path -LiteralPath $OutDir).Path

function Read-PEInfo([string]$FilePath) {
  $bytes = [IO.File]::ReadAllBytes($FilePath)
  if ($bytes.Length -lt 0x100 -or $bytes[0] -ne 0x4D -or $bytes[1] -ne 0x5A) {
    throw "Not MZ: $FilePath"
  }

  $lf = [BitConverter]::ToInt32($bytes,0x3C)
  if ($lf -lt 0 -or ($lf + 24) -ge $bytes.Length) { throw "Invalid PE offset: $FilePath" }
  if ($bytes[$lf] -ne 0x50 -or $bytes[$lf+1] -ne 0x45) { throw "Not PE: $FilePath" }

  $machine=[BitConverter]::ToUInt16($bytes,$lf+4)
  $nsec=[BitConverter]::ToUInt16($bytes,$lf+6)
  $opt=[BitConverter]::ToUInt16($bytes,$lf+20)
  $magic=[BitConverter]::ToUInt16($bytes,$lf+24)
  $entry=[BitConverter]::ToUInt32($bytes,$lf+40)
  if($magic -eq 0x10b) {
    $imageBase=[uint64][BitConverter]::ToUInt32($bytes,$lf+52)
  } elseif($magic -eq 0x20b) {
    $imageBase=[BitConverter]::ToUInt64($bytes,$lf+48)
  } else {
    throw ('Unsupported optional header magic 0x{0:X4}' -f $magic)
  }

  $secOff=$lf+24+$opt
  $secs=New-Object System.Collections.Generic.List[object]
  for($i=0;$i -lt $nsec;$i++){
    $o=$secOff+40*$i
    $nameBytes=$bytes[$o..($o+7)]
    $name=([Text.Encoding]::ASCII.GetString($nameBytes)).Trim([char]0)
    $vsize=[BitConverter]::ToUInt32($bytes,$o+8)
    $vaddr=[BitConverter]::ToUInt32($bytes,$o+12)
    $rawsize=[BitConverter]::ToUInt32($bytes,$o+16)
    $rawptr=[BitConverter]::ToUInt32($bytes,$o+20)
    $chars=[BitConverter]::ToUInt32($bytes,$o+36)
    $secs.Add([pscustomobject]@{
      Name=$name
      VSize=$vsize
      RVA=('0x{0:X8}' -f $vaddr)
      RawSize=$rawsize
      RawPtr=('0x{0:X8}' -f $rawptr)
      Characteristics=('0x{0:X8}' -f $chars)
    })
  }

  return [pscustomobject]@{
    FilePath=$FilePath
    FileSize=[int64]$bytes.LongLength
    Machine=('0x{0:X4}' -f $machine)
    SectionCount=[int]$nsec
    Magic=('0x{0:X4}' -f $magic)
    ImageBase=[uint64]$imageBase
    EntryRVA=[uint32]$entry
    SectionTable=$secs.ToArray()
    FileBytes=$bytes
  }
}

$peA = Read-PEInfo $APath
$peB = Read-PEInfo $BPath

$peA.SectionTable | Format-Table -AutoSize | Out-String |
  Set-Content -Encoding utf8 (Join-Path $OutPath "A_SECTIONS.txt")
$peB.SectionTable | Format-Table -AutoSize | Out-String |
  Set-Content -Encoding utf8 (Join-Path $OutPath "B_SECTIONS.txt")

$min=[Math]::Min([int64]$peA.FileBytes.LongLength,[int64]$peB.FileBytes.LongLength)
$first=[int64]-1
$last=[int64]-1
[long]$diff=0
$chunks=New-Object System.Collections.Generic.List[object]
$inRun=$false
[long]$runStart=0
[long]$runDiff=0

for([long]$i=0;$i -lt $min;$i++){
  $same = ($peA.FileBytes[$i] -eq $peB.FileBytes[$i])
  if(-not $same){
    if($first -lt 0){$first=$i}
    $last=$i
    $diff++
    if(-not $inRun){
      $inRun=$true
      $runStart=$i
      $runDiff=0
    }
    $runDiff++
  } elseif($inRun){
    $chunks.Add([pscustomobject]@{
      Start=('0x{0:X8}' -f $runStart)
      End=('0x{0:X8}' -f ($i-1))
      Length=($i-$runStart)
      DiffBytes=$runDiff
    })
    $inRun=$false
  }
}
if($inRun){
  $chunks.Add([pscustomobject]@{
    Start=('0x{0:X8}' -f $runStart)
    End=('0x{0:X8}' -f ($min-1))
    Length=($min-$runStart)
    DiffBytes=$runDiff
  })
}

$tail=[Math]::Abs([int64]$peA.FileBytes.LongLength-[int64]$peB.FileBytes.LongLength)
$totalDiff=$diff+$tail

$chunks | Sort-Object Length -Descending | Select-Object -First 200 |
  Export-Csv -NoTypeInformation -Encoding utf8 (Join-Path $OutPath "DIFF_RUNS_TOP200.csv")

$summary=@(
 "STATUS=PASS",
 "A=$APath",
 "B=$BPath",
 "A_SIZE=$($peA.FileSize)",
 "B_SIZE=$($peB.FileSize)",
 ('A_IMAGEBASE=0x{0:X}' -f $peA.ImageBase),
 ('B_IMAGEBASE=0x{0:X}' -f $peB.ImageBase),
 ('A_ENTRY_RVA=0x{0:X8}' -f $peA.EntryRVA),
 ('B_ENTRY_RVA=0x{0:X8}' -f $peB.EntryRVA),
 "A_SECTIONS=$($peA.SectionCount)",
 "B_SECTIONS=$($peB.SectionCount)",
 "COMMON_BYTES=$min",
 "DIFF_BYTES_COMMON=$diff",
 "TAIL_BYTES=$tail",
 "TOTAL_DIFF_BYTES=$totalDiff",
 ("FIRST_DIFF=" + $(if($first -ge 0){'0x{0:X8}' -f $first}else{'NONE'})),
 ("LAST_DIFF_COMMON=" + $(if($last -ge 0){'0x{0:X8}' -f $last}else{'NONE'})),
 "DIFF_RUN_COUNT=$($chunks.Count)",
 "NEXT=Use section layout + diff concentration to decide whether Lin.bin2 is a near build, patched variant, or substantially different client."
)
$summary | Set-Content -Encoding utf8 (Join-Path $OutPath "SUMMARY.txt")
$summary | ForEach-Object { Write-Host $_ }
