param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_static_model_xref.txt"
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$ImageBase = 0x00400000L
$Vtables = [ordered]@{
    GRID   = 0x00EDDE38
    ROOT   = 0x00EDE2F8
    INVWIN = 0x00EDE180
}
$InterestingOffsets = @(0x0F4,0x15C,0x168,0x1E0,0x1E4,0x1E8,0x1EC,0x1F0,0x1F4,0x1F8,0x1FC,0x200,0x204,0x208,0x20C,0x210,0x214,0x218,0x21C,0x220,0x224,0x228,0x22C,0x230)

if(-not (Test-Path -LiteralPath $ClientPath)){ throw "Client not found: $ClientPath" }
$sha=(Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha -ne $ExpectedSha256){ throw "Client authority mismatch: $sha" }
$bytes=[IO.File]::ReadAllBytes($ClientPath)

function U16([int]$o){ [BitConverter]::ToUInt16($bytes,$o) }
function U32([int]$o){ [BitConverter]::ToUInt32($bytes,$o) }

$pe=[int](U32 0x3C)
if([Text.Encoding]::ASCII.GetString($bytes,$pe,4) -ne "PE`0`0"){ throw 'Invalid PE signature' }
$sectionCount=[int](U16 ($pe+6))
$optSize=[int](U16 ($pe+20))
$opt=$pe+24
$sec=$opt+$optSize
$sections=New-Object System.Collections.Generic.List[object]
for($i=0;$i -lt $sectionCount;$i++){
    $o=$sec+($i*40)
    $name=([Text.Encoding]::ASCII.GetString($bytes,$o,8)).Trim([char]0)
    $vsize=[uint32](U32 ($o+8)); $rva=[uint32](U32 ($o+12)); $rawSize=[uint32](U32 ($o+16)); $raw=[uint32](U32 ($o+20)); $ch=[uint32](U32 ($o+36))
    $sections.Add([pscustomobject]@{Name=$name;Rva=$rva;VSize=$vsize;RawSize=$rawSize;Raw=$raw;Chars=$ch;Exec=(($ch -band 0x20000000)-ne 0)})
}
function Rva-ToFile([uint32]$rva){
    foreach($s in $sections){
        $span=[Math]::Max([long]$s.VSize,[long]$s.RawSize)
        if([long]$rva -ge [long]$s.Rva -and [long]$rva -lt ([long]$s.Rva+$span)){
            $fo=[long]$s.Raw+([long]$rva-[long]$s.Rva)
            if($fo -ge 0 -and $fo -lt $bytes.Length){ return [int]$fo }
        }
    }
    return -1
}
function File-ToRva([int]$fo){
    foreach($s in $sections){
        if($fo -ge [int]$s.Raw -and $fo -lt ([int]$s.Raw+[int]$s.RawSize)){
            return [uint32]([long]$s.Rva+($fo-[int]$s.Raw))
        }
    }
    return [uint32]0
}
function Hex([byte[]]$b,[int]$start,[int]$count){
    if($start -lt 0){$start=0}; if($start+$count -gt $b.Length){$count=$b.Length-$start}; if($count -le 0){return ''}
    return (($b[$start..($start+$count-1)] | ForEach-Object { $_.ToString('X2') }) -join ' ')
}
function Find-All4([uint32]$value,[object[]]$scanSections){
    $needle=[BitConverter]::GetBytes($value)
    $hits=New-Object System.Collections.Generic.List[object]
    foreach($s in $scanSections){
        $start=[int]$s.Raw; $end=[Math]::Min($bytes.Length,$start+[int]$s.RawSize)
        for($i=$start;$i+4 -le $end;$i++){
            if($bytes[$i]-eq$needle[0] -and $bytes[$i+1]-eq$needle[1] -and $bytes[$i+2]-eq$needle[2] -and $bytes[$i+3]-eq$needle[3]){
                $hits.Add([pscustomobject]@{File=$i;Rva=(File-ToRva $i);Section=$s.Name})
            }
        }
    }
    return $hits
}

$exec=@($sections | Where-Object {$_.Exec})
$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_INVENTORY_STATIC_MODEL_XREF')
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add('RUNTIME_ATTACH=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('SOURCE_MODIFIED=NO')
$lines.Add('')
$lines.Add('[SECTIONS]')
foreach($s in $sections){$lines.Add(("SEC NAME={0} RVA=0x{1:X8} VSIZE=0x{2:X} RAW=0x{3:X} RAWSIZE=0x{4:X} EXEC={5}" -f $s.Name,$s.Rva,$s.VSize,$s.Raw,$s.RawSize,[int]$s.Exec))}

$vtFns=New-Object System.Collections.Generic.List[object]
$lines.Add('')
$lines.Add('[VTABLES]')
foreach($kv in $Vtables.GetEnumerator()){
    $name=$kv.Key; $rva=[uint32]$kv.Value; $fo=Rva-ToFile $rva
    if($fo -lt 0){$lines.Add("VTABLE $name RVA=0x$($rva.ToString('X8')) FILE=UNMAPPED");continue}
    $lines.Add(("VTABLE {0} RVA=0x{1:X8} FILE=0x{2:X}" -f $name,$rva,$fo))
    for($slot=0;$slot -lt 32;$slot++){
        $p=[uint32](U32 ($fo+($slot*4)))
        $frva=if($p -ge $ImageBase){[uint32]([long]$p-$ImageBase)}else{[uint32]0}
        $ffo=if($frva -ne 0){Rva-ToFile $frva}else{-1}
        $lines.Add(("  SLOT={0:D2} VA=0x{1:X8} RVA=0x{2:X8} FILE={3}" -f $slot,$p,$frva,($(if($ffo-ge0){'0x'+$ffo.ToString('X')}else{'UNMAPPED'}))))
        if($ffo -ge 0){$vtFns.Add([pscustomobject]@{Owner=$name;Slot=$slot;Rva=$frva;File=$ffo})}
    }
}

$lines.Add('')
$lines.Add('[VTABLE_FUNCTION_OFFSET_REFS]')
foreach($fn in $vtFns){
    $start=[Math]::Max(0,$fn.File-16); $end=[Math]::Min($bytes.Length,$fn.File+0x500)
    $found=0
    foreach($off in $InterestingOffsets){
        $needle=[BitConverter]::GetBytes([uint32]$off)
        for($i=$start;$i+4 -le $end;$i++){
            if($bytes[$i]-eq$needle[0] -and $bytes[$i+1]-eq$needle[1] -and $bytes[$i+2]-eq$needle[2] -and $bytes[$i+3]-eq$needle[3]){
                $hrva=File-ToRva $i
                $lines.Add(("REF OWNER={0} SLOT={1} FN_RVA=0x{2:X8} HIT_RVA=0x{3:X8} DISP=0x{4:X3} BYTES={5}" -f $fn.Owner,$fn.Slot,$fn.Rva,$hrva,$off,(Hex $bytes ([Math]::Max(0,$i-8)) 24)))
                $found++
            }
        }
    }
    if($found -eq 0){$lines.Add(("REF OWNER={0} SLOT={1} FN_RVA=0x{2:X8} NONE" -f $fn.Owner,$fn.Slot,$fn.Rva))}
}

$lines.Add('')
$lines.Add('[GLOBAL_EXEC_DISPLACEMENT_HITS]')
foreach($off in $InterestingOffsets){
    $hits=@(Find-All4 ([uint32]$off) $exec | Select-Object -First 64)
    $lines.Add(("DISP=0x{0:X3} HITS={1}" -f $off,$hits.Count))
    foreach($h in $hits){$lines.Add(("  HIT RVA=0x{0:X8} SEC={1} BYTES={2}" -f $h.Rva,$h.Section,(Hex $bytes ([Math]::Max(0,$h.File-8)) 24)))}
}

$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add('STATUS=PASS_STATIC_ONLY')
$lines.Add('NEXT=Correlate vtable methods that reference inventory offsets; prefer one narrowly targeted live read only after a stable owner/model pointer is identified.')
$lines.Add('RUNTIME_ATTACH=NO')
$lines.Add('MEMORY_WRITE=NO')
[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host 'STATUS=PASS_STATIC_ONLY'
Write-Host "OUTPUT=$OutputPath"
Write-Host 'RUNTIME_ATTACH=NO'
Write-Host 'MEMORY_WRITE=NO'
