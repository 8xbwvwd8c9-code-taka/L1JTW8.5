param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_static_xref_v2.txt",
    [int]$OwnerWindow = 0x500,
    [int]$MaxGlobalHitsPerOffset = 256
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$ImageBase = 0x00400000L
$Vtables = [ordered]@{
    GRID   = 0x00EDDE38
    ROOT   = 0x00EDE2F8
    INVWIN = 0x00EDE180
}

# Proven UI graph offsets first, then candidate model/vector neighborhood offsets.
$InterestingOffsets = @(
    0x0EC, 0x0F4, 0x15C, 0x168,
    0x1E0,0x1E4,0x1E8,0x1EC,0x1F0,0x1F4,0x1F8,0x1FC,
    0x200,0x204,0x208,0x20C,0x210,0x214,0x218,0x21C,
    0x220,0x224,0x228,0x22C,0x230
)
$InterestingSet = New-Object 'System.Collections.Generic.HashSet[int]'
foreach($x in $InterestingOffsets){ $null = $InterestingSet.Add([int]$x) }

if(-not (Test-Path -LiteralPath $ClientPath)){ throw "Client not found: $ClientPath" }
$sha=(Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha -ne $ExpectedSha256){ throw "Client authority mismatch: $sha" }
$bytes=[IO.File]::ReadAllBytes($ClientPath)

function U16([int]$o){
    if($o -lt 0 -or $o+2 -gt $bytes.Length){ throw "U16 out of range: $o" }
    [BitConverter]::ToUInt16($bytes,$o)
}
function U32([int]$o){
    if($o -lt 0 -or $o+4 -gt $bytes.Length){ throw "U32 out of range: $o" }
    [BitConverter]::ToUInt32($bytes,$o)
}
function I32([int]$o){
    if($o -lt 0 -or $o+4 -gt $bytes.Length){ throw "I32 out of range: $o" }
    [BitConverter]::ToInt32($bytes,$o)
}
function Hex([byte[]]$b,[int]$start,[int]$count){
    if($start -lt 0){$start=0}
    if($start+$count -gt $b.Length){$count=$b.Length-$start}
    if($count -le 0){return ''}
    return (($b[$start..($start+$count-1)] | ForEach-Object { $_.ToString('X2') }) -join ' ')
}

$pe=[int](U32 0x3C)
if([Text.Encoding]::ASCII.GetString($bytes,$pe,4) -ne "PE`0`0"){ throw 'Invalid PE signature' }
$sectionCount=[int](U16 ($pe+6))
$optSize=[int](U16 ($pe+20))
$opt=$pe+24
$magic=[int](U16 $opt)
if($magic -ne 0x10B){ throw ("Expected PE32/x86 optional header, magic=0x{0:X}" -f $magic) }
$sec=$opt+$optSize
$sections=New-Object System.Collections.Generic.List[object]
for($i=0;$i -lt $sectionCount;$i++){
    $o=$sec+($i*40)
    $name=([Text.Encoding]::ASCII.GetString($bytes,$o,8)).Trim([char]0)
    $vsize=[uint32](U32 ($o+8))
    $rva=[uint32](U32 ($o+12))
    $rawSize=[uint32](U32 ($o+16))
    $raw=[uint32](U32 ($o+20))
    $ch=[uint32](U32 ($o+36))
    $sections.Add([pscustomobject]@{
        Name=$name; Rva=$rva; VSize=$vsize; RawSize=$rawSize; Raw=$raw; Chars=$ch;
        Exec=(($ch -band 0x20000000)-ne 0)
    })
}
$exec=@($sections | Where-Object {$_.Exec -and $_.RawSize -gt 0})
if($exec.Count -eq 0){ throw 'No executable PE sections found.' }

function File-ToRva([int]$fo){
    foreach($s in $sections){
        if($fo -ge [int]$s.Raw -and $fo -lt ([int]$s.Raw+[int]$s.RawSize)){
            return [uint32]([long]$s.Rva+($fo-[int]$s.Raw))
        }
    }
    return [uint32]0
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
function Get-SectionByFile([int]$fo){
    foreach($s in $sections){
        if($fo -ge [int]$s.Raw -and $fo -lt ([int]$s.Raw+[int]$s.RawSize)){ return $s }
    }
    return $null
}
function Test-ExecRva([long]$rva){
    foreach($s in $exec){
        $span=[Math]::Max([long]$s.VSize,[long]$s.RawSize)
        if($rva -ge [long]$s.Rva -and $rva -lt ([long]$s.Rva+$span)){ return $true }
    }
    return $false
}

function Find-All4([uint32]$value,[object[]]$scanSections){
    $needle=[BitConverter]::GetBytes($value)
    $hits=New-Object System.Collections.Generic.List[object]
    foreach($s in $scanSections){
        $start=[int]$s.Raw
        $end=[Math]::Min($bytes.Length,$start+[int]$s.RawSize)
        for($i=$start;$i+4 -le $end;$i++){
            if($bytes[$i]-eq$needle[0] -and $bytes[$i+1]-eq$needle[1] -and $bytes[$i+2]-eq$needle[2] -and $bytes[$i+3]-eq$needle[3]){
                $hits.Add([pscustomobject]@{File=$i;Rva=(File-ToRva $i);Section=$s.Name})
            }
        }
    }
    return $hits
}

function Find-ProbableFunctionStart([int]$fo,[int]$maxBack=0x200){
    $s=Get-SectionByFile $fo
    if($null -eq $s){ return -1 }
    $lo=[Math]::Max([int]$s.Raw,[int]($fo-$maxBack))
    for($i=$fo;$i-ge$lo;$i--){
        if($i+3 -le $bytes.Length -and $bytes[$i]-eq0x55 -and $bytes[$i+1]-eq0x8B -and $bytes[$i+2]-eq0xEC){ return $i }
        if($i+5 -le $bytes.Length -and $bytes[$i]-eq0x8B -and $bytes[$i+1]-eq0xFF -and $bytes[$i+2]-eq0x55 -and $bytes[$i+3]-eq0x8B -and $bytes[$i+4]-eq0xEC){ return $i }
        if($i+2 -lt $bytes.Length -and ($bytes[$i]-eq0xCC -or $bytes[$i]-eq0x90) -and $bytes[$i+1]-ne$bytes[$i]){ return $i+1 }
    }
    return -1
}

function Get-ModRmDispRef([int]$instructionFile,[int]$opcodeLen,[string]$opcodeName){
    $mpos=$instructionFile+$opcodeLen
    if($mpos -lt 0 -or $mpos -ge $bytes.Length){ return $null }
    $modrm=[int]$bytes[$mpos]
    $mod=($modrm -shr 6) -band 3
    $rm=$modrm -band 7
    if($mod -eq 3){ return $null }

    $cursor=$mpos+1
    $hasSib=$false
    if($rm -eq 4){
        if($cursor -ge $bytes.Length){ return $null }
        $hasSib=$true
        $cursor++
    }

    $disp=$null
    $dispBytes=0
    if($mod -eq 1){
        if($cursor -ge $bytes.Length){ return $null }
        $rawDisp=[int]$bytes[$cursor]
        $disp=if($rawDisp -ge 0x80){$rawDisp-0x100}else{$rawDisp}
        $dispBytes=1
    } elseif($mod -eq 2){
        if($cursor+4 -gt $bytes.Length){ return $null }
        $disp=[int](I32 $cursor)
        $dispBytes=4
    } elseif($mod -eq 0 -and $rm -eq 5){
        return $null
    } elseif($mod -eq 0 -and $hasSib){
        $sib=[int]$bytes[$mpos+1]
        if(($sib -band 7) -eq 5){ return $null }
    }

    if($null -eq $disp){ return $null }
    if($disp -lt 0){ return $null }
    if(-not $InterestingSet.Contains([int]$disp)){ return $null }

    $rva=File-ToRva $instructionFile
    return [pscustomobject]@{
        File=$instructionFile
        Rva=$rva
        Opcode=$opcodeName
        ModRm=('0x{0:X2}' -f $modrm)
        Disp=[int]$disp
        DispBytes=$dispBytes
        HasSib=$hasSib
        Bytes=(Hex $bytes $instructionFile 16)
    }
}

function Find-MemberRefsInRange([int]$start,[int]$end){
    $out=New-Object System.Collections.Generic.List[object]
    $start=[Math]::Max(0,$start)
    $end=[Math]::Min($bytes.Length,$end)
    for($i=$start;$i-lt$end;$i++){
        $op=[int]$bytes[$i]
        $ref=$null
        switch($op){
            0x8B { $ref=Get-ModRmDispRef $i 1 'MOV_R_RM' }
            0x89 { $ref=Get-ModRmDispRef $i 1 'MOV_RM_R' }
            0x8D { $ref=Get-ModRmDispRef $i 1 'LEA' }
            0x3B { $ref=Get-ModRmDispRef $i 1 'CMP_R_RM' }
            0x39 { $ref=Get-ModRmDispRef $i 1 'CMP_RM_R' }
            0x85 { $ref=Get-ModRmDispRef $i 1 'TEST_RM_R' }
            0x81 { $ref=Get-ModRmDispRef $i 1 'GRP1_IMM32' }
            0x83 { $ref=Get-ModRmDispRef $i 1 'GRP1_IMM8' }
            0xC6 { $ref=Get-ModRmDispRef $i 1 'MOV_RM_IMM8' }
            0xC7 { $ref=Get-ModRmDispRef $i 1 'MOV_RM_IMM32' }
            0xF6 { $ref=Get-ModRmDispRef $i 1 'GRP3_BYTE' }
            0xF7 { $ref=Get-ModRmDispRef $i 1 'GRP3_DWORD' }
            0xFF { $ref=Get-ModRmDispRef $i 1 'GRP5' }
            0x0F {
                if($i+1 -lt $end){
                    switch([int]$bytes[$i+1]){
                        0xB6 { $ref=Get-ModRmDispRef $i 2 'MOVZX_BYTE' }
                        0xB7 { $ref=Get-ModRmDispRef $i 2 'MOVZX_WORD' }
                        0xBE { $ref=Get-ModRmDispRef $i 2 'MOVSX_BYTE' }
                        0xBF { $ref=Get-ModRmDispRef $i 2 'MOVSX_WORD' }
                    }
                }
            }
        }
        if($null -ne $ref){ $out.Add($ref) }
    }
    return $out
}

function Find-CallsInRange([int]$start,[int]$end){
    $out=New-Object System.Collections.Generic.List[object]
    $start=[Math]::Max(0,$start)
    $end=[Math]::Min($bytes.Length,$end)
    for($i=$start;$i+5 -le $end;$i++){
        if($bytes[$i] -ne 0xE8){ continue }
        $srcRva=[long](File-ToRva $i)
        if($srcRva -eq 0){ continue }
        $rel=[long](I32 ($i+1))
        $target=$srcRva+5+$rel
        if($target -lt 0 -or $target -gt [uint32]::MaxValue){ continue }
        if(-not (Test-ExecRva $target)){ continue }
        $out.Add([pscustomobject]@{
            File=$i; Rva=[uint32]$srcRva; TargetRva=[uint32]$target;
            TargetFile=(Rva-ToFile ([uint32]$target)); Bytes=(Hex $bytes $i 8)
        })
    }
    return $out
}

function Classify-VtableLiteralContext([int]$fo){
    $start=[Math]::Max(0,$fo-8)
    $prefix=Hex $bytes $start ([Math]::Min(16,$bytes.Length-$start))
    $kind='RAW_LITERAL'
    if($fo-ge2 -and $bytes[$fo-2]-eq0xC7){ $kind='C7_IMMEDIATE_CANDIDATE' }
    elseif($fo-ge1 -and ($bytes[$fo-1]-ge0xB8 -and $bytes[$fo-1]-le0xBF)){ $kind='MOV_REG_IMMEDIATE' }
    elseif($fo-ge1 -and $bytes[$fo-1]-eq0x68){ $kind='PUSH_IMMEDIATE' }
    elseif($fo-ge1 -and $bytes[$fo-1]-eq0xA1){ $kind='MOFFS_CANDIDATE' }
    return [pscustomobject]@{Kind=$kind;Context=$prefix}
}

$parent=Split-Path -Parent $OutputPath
if($parent -and -not (Test-Path -LiteralPath $parent)){ New-Item -ItemType Directory -Force -Path $parent | Out-Null }

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_INVENTORY_STATIC_XREF_V2')
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add('RUNTIME_ATTACH=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('SOURCE_MODIFIED=NO')
$lines.Add(("OWNER_WINDOW=0x{0:X}" -f $OwnerWindow))
$lines.Add('')
$lines.Add('[SECTIONS]')
foreach($s in $sections){
    $lines.Add(("SEC NAME={0} RVA=0x{1:X8} VSIZE=0x{2:X} RAW=0x{3:X} RAWSIZE=0x{4:X} EXEC={5}" -f $s.Name,$s.Rva,$s.VSize,$s.Raw,$s.RawSize,[int]$s.Exec))
}

$ownerXrefs=New-Object System.Collections.Generic.List[object]
$lines.Add('')
$lines.Add('[VTABLE_LITERAL_XREFS]')
foreach($kv in $Vtables.GetEnumerator()){
    $name=[string]$kv.Key
    $vrva=[uint32]$kv.Value
    $vva=[uint32]([long]$ImageBase+[long]$vrva)
    $hits=@(Find-All4 $vva $exec)
    $lines.Add(("OWNER={0} VTABLE_RVA=0x{1:X8} VTABLE_VA=0x{2:X8} EXEC_LITERAL_HITS={3}" -f $name,$vrva,$vva,$hits.Count))
    foreach($h in $hits){
        $ctx=Classify-VtableLiteralContext $h.File
        $fnfo=Find-ProbableFunctionStart $h.File
        $fnrva=if($fnfo-ge0){File-ToRva $fnfo}else{[uint32]0}
        $lines.Add(("  XREF RVA=0x{0:X8} SEC={1} KIND={2} FN_RVA={3} CONTEXT={4}" -f $h.Rva,$h.Section,$ctx.Kind,$(if($fnrva-ne0){'0x'+$fnrva.ToString('X8')}else{'UNKNOWN'}),$ctx.Context))
        $ownerXrefs.Add([pscustomobject]@{Owner=$name;File=$h.File;Rva=$h.Rva;FnFile=$fnfo;FnRva=$fnrva;Kind=$ctx.Kind})
    }
}

$lines.Add('')
$lines.Add('[OWNER_WINDOW_MEMBER_REFS]')
foreach($x in $ownerXrefs){
    $lo=[Math]::Max(0,$x.File-$OwnerWindow)
    $hi=[Math]::Min($bytes.Length,$x.File+$OwnerWindow)
    $refs=@(Find-MemberRefsInRange $lo $hi)
    $lines.Add(("OWNER={0} XREF_RVA=0x{1:X8} FN_RVA={2} MEMBER_REFS={3}" -f $x.Owner,$x.Rva,$(if($x.FnRva-ne0){'0x'+$x.FnRva.ToString('X8')}else{'UNKNOWN'}),$refs.Count))
    foreach($r in $refs){
        $dist=[long]$r.File-[long]$x.File
        $lines.Add(("  REF RVA=0x{0:X8} DIST={1} OP={2} MODRM={3} DISP=0x{4:X3} DISP_BYTES={5} SIB={6} BYTES={7}" -f $r.Rva,$dist,$r.Opcode,$r.ModRm,$r.Disp,$r.DispBytes,[int]$r.HasSib,$r.Bytes))
    }
}

$lines.Add('')
$lines.Add('[OWNER_WINDOW_CALLS]')
foreach($x in $ownerXrefs){
    $lo=[Math]::Max(0,$x.File-$OwnerWindow)
    $hi=[Math]::Min($bytes.Length,$x.File+$OwnerWindow)
    $calls=@(Find-CallsInRange $lo $hi)
    $lines.Add(("OWNER={0} XREF_RVA=0x{1:X8} CALLS={2}" -f $x.Owner,$x.Rva,$calls.Count))
    foreach($c in $calls){
        $lines.Add(("  CALL RVA=0x{0:X8} TARGET_RVA=0x{1:X8} TARGET_FILE={2} BYTES={3}" -f $c.Rva,$c.TargetRva,$(if($c.TargetFile-ge0){'0x'+$c.TargetFile.ToString('X')}else{'UNMAPPED'}),$c.Bytes))
    }
}

$lines.Add('')
$lines.Add('[GLOBAL_MEMBER_REFS]')
$global=New-Object System.Collections.Generic.List[object]
foreach($s in $exec){
    foreach($r in (Find-MemberRefsInRange ([int]$s.Raw) ([int]$s.Raw+[int]$s.RawSize))){ $global.Add($r) }
}
foreach($off in $InterestingOffsets){
    $hits=@($global | Where-Object {$_.Disp -eq $off})
    $lines.Add(("DISP=0x{0:X3} HITS={1}" -f $off,$hits.Count))
    foreach($r in ($hits | Select-Object -First $MaxGlobalHitsPerOffset)){
        $fnfo=Find-ProbableFunctionStart $r.File
        $fnrva=if($fnfo-ge0){File-ToRva $fnfo}else{[uint32]0}
        $lines.Add(("  REF RVA=0x{0:X8} FN_RVA={1} OP={2} MODRM={3} BYTES={4}" -f $r.Rva,$(if($fnrva-ne0){'0x'+$fnrva.ToString('X8')}else{'UNKNOWN'}),$r.Opcode,$r.ModRm,$r.Bytes))
    }
}

$lines.Add('')
$lines.Add('[CORRELATION_HINTS]')
$lines.Add('PROVEN_GRAPH=GRID+0xEC->ROOT; ROOT+0x15C->GRID; ROOT+0x168->INVWIN')
$lines.Add('RANK_HIGH=Same probable function/window contains a vtable literal xref plus two or more proven graph offsets.')
$lines.Add('RANK_MED=Same probable function/window contains one proven graph offset plus repeated 0x1E0..0x230 model/vector offsets.')
$lines.Add('DO_NOT_PROMOTE=Any single literal/offset/call hit without owner correlation and restart-stable narrow live proof.')
$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add('STATUS=PASS_STATIC_ONLY_V2')
$lines.Add('STATIC_XREF_VERSION=2')
$lines.Add(("VTABLE_LITERAL_XREF_TOTAL={0}" -f $ownerXrefs.Count))
$lines.Add(("GLOBAL_MEMBER_REF_TOTAL={0}" -f $global.Count))
$lines.Add('NEXT=Run report; rank owner windows containing proven +0xEC/+0x15C/+0x168 accesses; then inspect only the strongest stable owner/model pointer with one narrow read-only live check.')
$lines.Add('RUNTIME_ATTACH=NO')
$lines.Add('MEMORY_WRITE=NO')

[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host 'STATUS=PASS_STATIC_ONLY_V2'
Write-Host "OUTPUT=$OutputPath"
Write-Host "VTABLE_LITERAL_XREF_TOTAL=$($ownerXrefs.Count)"
Write-Host "GLOBAL_MEMBER_REF_TOTAL=$($global.Count)"
Write-Host 'RUNTIME_ATTACH=NO'
Write-Host 'MEMORY_WRITE=NO'
