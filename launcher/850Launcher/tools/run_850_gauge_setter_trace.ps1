param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OwnerReport = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_ui_owner_scan.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_gauge_setter_trace.txt"
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
if (-not (Test-Path -LiteralPath $OwnerReport)) { throw "Owner report not found: $OwnerReport" }
$sha=(Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha-ne$ExpectedSha256){throw "Client authority mismatch: $sha"}

$report=Get-Content -LiteralPath $OwnerReport -ErrorAction Stop
function Get-ReportValue([string]$prefix){
    $line=$report|Where-Object{$_-like "$prefix*"}|Select-Object -First 1
    if(-not $line){return $null}
    return ($line-split'=',2)[1].Trim()
}
$targetPid=[int](Get-ReportValue 'PID=')
$start=Get-ReportValue 'PROCESS_START_UTC='
$rh=(Get-ReportValue 'CLIENT_SHA256=').ToUpperInvariant()
$ra=Get-ReportValue 'CLIENT_AUTHORITY='
if($rh-ne$ExpectedSha256 -or $ra-ne'1'){throw 'Owner report authority mismatch.'}

$hpLine=$report|Where-Object{$_-match'^OBJECT NAME=HpGauge_Image ADDR=0x'}|Select-Object -First 1
$mpLine=$report|Where-Object{$_-match'^OBJECT NAME=MpGauge_Image ADDR=0x'}|Select-Object -First 1
if(-not $hpLine -or -not $mpLine){throw 'Gauge object evidence missing.'}
$hp=[long][Convert]::ToUInt64(([regex]::Match($hpLine,'ADDR=0x([0-9A-Fa-f]+)').Groups[1].Value),16)
$mp=[long][Convert]::ToUInt64(([regex]::Match($mpLine,'ADDR=0x([0-9A-Fa-f]+)').Groups[1].Value),16)
$gaugeVt=[uint32][Convert]::ToUInt32(([regex]::Match($hpLine,'VTABLE=0x([0-9A-Fa-f]+)').Groups[1].Value),16)

$p=Get-Process -Id $targetPid -ErrorAction Stop
if($p.HasExited){throw 'PID exited.'}
$module=$p.MainModule
if(-not $module){throw 'MainModule unavailable; run elevated.'}
if(-not [string]::Equals([IO.Path]::GetFullPath($module.FileName),[IO.Path]::GetFullPath($ClientPath),[StringComparison]::OrdinalIgnoreCase)){throw 'PID is not authoritative Lin.bin2.'}
if($start -and $p.StartTime.ToUniversalTime().ToString('o') -ne $start){throw 'Owner report is stale; rerun owner scan.'}
$base=[long]$module.BaseAddress
$size=[int]$module.ModuleMemorySize

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class GaugeTraceMem850 {
 const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
 [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
 public static byte[] Read(int pid,long a,int s){
  IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
  if(h==IntPtr.Zero)throw new Exception("OpenProcess Win32="+Marshal.GetLastWin32Error());
  try{byte[] b=new byte[s];IntPtr r;if(!ReadProcessMemory(h,new IntPtr(a),b,s,out r))return new byte[0];int n=(int)Math.Min((long)s,r.ToInt64());if(n==s)return b;byte[] o=new byte[n];Array.Copy(b,o,n);return o;}finally{CloseHandle(h);}
 }
}
"@

$moduleBytes=[GaugeTraceMem850]::Read($targetPid,$base,$size)
if($moduleBytes.Length-lt0x1000){throw 'Failed to read runtime module image.'}

function Get-ExecRanges([byte[]]$d){
    $out=New-Object System.Collections.Generic.List[object]
    $pe=[BitConverter]::ToInt32($d,0x3C)
    $n=[BitConverter]::ToUInt16($d,$pe+6)
    $os=[BitConverter]::ToUInt16($d,$pe+20)
    $sec=$pe+24+$os
    for($i=0;$i-lt$n;$i++){
        $o=$sec+40*$i
        if($o+40-gt$d.Length){break}
        $vs=[BitConverter]::ToUInt32($d,$o+8)
        $va=[BitConverter]::ToUInt32($d,$o+12)
        $ch=[BitConverter]::ToUInt32($d,$o+36)
        if(($ch-band0x20000000)-ne0){
            $end=[long]([Math]::Min([long]$d.Length,[long]$va+[Math]::Max([long]$vs,1)))
            $out.Add([pscustomobject]@{Start=[long]$va;End=$end})
        }
    }
    return $out
}
$exec=Get-ExecRanges $moduleBytes
function Test-ExecRva([long]$rva){foreach($r in $exec){if($rva-ge$r.Start -and $rva-lt$r.End){return $true}};return $false}
function HexWindow([byte[]]$d,[int]$center,[int]$before=12,[int]$after=20){
    $s=[Math]::Max(0,$center-$before);$e=[Math]::Min($d.Length,$center+$after);$a=New-Object System.Collections.Generic.List[string]
    for($i=$s;$i-lt$e;$i++){$a.Add(('{0:X2}'-f$d[$i]))}
    return ($a-join' ')
}
function Find-ApproxFunctionStart([int]$rva){
    $lo=[Math]::Max(0,$rva-0x180)
    for($i=$rva;$i-ge$lo;$i--){
        if($i+3-lt$moduleBytes.Length -and $moduleBytes[$i]-eq0x55 -and $moduleBytes[$i+1]-eq0x8B -and $moduleBytes[$i+2]-eq0xEC){return $i}
        if($i+2-lt$moduleBytes.Length -and $moduleBytes[$i]-eq0x53 -and $moduleBytes[$i+1]-eq0x56 -and $moduleBytes[$i+2]-eq0x57){return $i}
    }
    return [Math]::Max(0,$rva-0x40)
}

# Read Gauge vtable entries.
$vtRva=[long]$gaugeVt-$base
if($vtRva-lt0 -or $vtRva+64-gt$moduleBytes.Length){throw 'Gauge vtable is outside runtime module.'}
$slots=New-Object System.Collections.Generic.List[object]
for($i=0;$i-lt16;$i++){
    $fn=[BitConverter]::ToUInt32($moduleBytes,[int]($vtRva+4*$i))
    if($fn-lt$base -or $fn-ge($base+$size)){break}
    $rva=[long]$fn-$base
    if(-not(Test-ExecRva $rva)){break}
    $slots.Add([pscustomobject]@{Slot=$i;SlotOff=4*$i;VA=[long]$fn;RVA=$rva})
}

# Candidate vtable functions that reference displacement 0x1C0 in their first 0x300 bytes.
$cands=New-Object System.Collections.Generic.List[object]
foreach($s in $slots){
    $start=[int]$s.RVA;$len=[Math]::Min(0x300,$moduleBytes.Length-$start)
    if($len-le0){continue}
    $hits=New-Object System.Collections.Generic.List[int]
    for($i=0;$i+3-lt$len;$i++){
        $o=$start+$i
        if($moduleBytes[$o]-eq0xC0 -and $moduleBytes[$o+1]-eq0x01 -and $moduleBytes[$o+2]-eq0x00 -and $moduleBytes[$o+3]-eq0x00){$hits.Add($i)}
    }
    if($hits.Count-gt0){$cands.Add([pscustomobject]@{Slot=$s.Slot;SlotOff=$s.SlotOff;VA=$s.VA;RVA=$s.RVA;Hits=$hits})}
}

function Find-DirectCallers([long]$targetVa){
    $out=New-Object System.Collections.Generic.List[object]
    foreach($r in $exec){
        for($i=[int]$r.Start;$i+5-le[int]$r.End;$i++){
            if($moduleBytes[$i]-ne0xE8){continue}
            $rel=[BitConverter]::ToInt32($moduleBytes,$i+1)
            $dest=$base+$i+5+$rel
            if($dest-ne$targetVa){continue}
            $fs=Find-ApproxFunctionStart $i
            $out.Add([pscustomobject]@{CallRva=$i;CallerRva=$fs;Hex=(HexWindow $moduleBytes $i 16 20)})
        }
    }
    return $out
}

function Find-IndirectSlotCalls([int]$slotOff){
    $out=New-Object System.Collections.Generic.List[object]
    foreach($r in $exec){
        for($i=[int]$r.Start;$i+6-le[int]$r.End;$i++){
            if($moduleBytes[$i]-ne0xFF){continue}
            $m=$moduleBytes[$i+1]
            $match=$false;$len=0
            if($slotOff-le0x7F -and $m-ge0x50 -and $m-le0x57 -and $moduleBytes[$i+2]-eq[byte]$slotOff){$match=$true;$len=3}
            elseif($m-ge0x90 -and $m-le0x97 -and [BitConverter]::ToInt32($moduleBytes,$i+2)-eq$slotOff){$match=$true;$len=6}
            if(-not$match){continue}
            $fs=Find-ApproxFunctionStart $i
            $out.Add([pscustomobject]@{CallRva=$i;CallerRva=$fs;Len=$len;Hex=(HexWindow $moduleBytes $i 20 24)})
        }
    }
    return $out
}

$parent=Split-Path -Parent $OutputPath
if($parent -and -not(Test-Path -LiteralPath $parent)){New-Item -ItemType Directory -Force -Path $parent|Out-Null}
$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_GAUGE_SETTER_TRACE')
$lines.Add("PID=$targetPid")
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add(("MODULE_BASE=0x{0:X8}"-f$base))
$lines.Add(("HP_GAUGE=0x{0:X8}"-f$hp))
$lines.Add(("MP_GAUGE=0x{0:X8}"-f$mp))
$lines.Add(("GAUGE_VTABLE=0x{0:X8}"-f$gaugeVt))
$lines.Add('TARGET_FIELD_OFFSET=0x1C0')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('SOURCE_MODIFIED=NO')
$lines.Add('')
$lines.Add('[VTABLE_SLOTS]')
foreach($s in $slots){$lines.Add(("SLOT={0} SLOT_OFF=0x{1:X} FN_VA=0x{2:X8} FN_RVA=0x{3:X8}"-f$s.Slot,$s.SlotOff,$s.VA,$s.RVA))}
$lines.Add('')
$lines.Add('[FIELD_1C0_FUNCTION_CANDIDATES]')
$lines.Add("COUNT=$($cands.Count)")
foreach($c in $cands){
    $lines.Add(("CAND SLOT={0} SLOT_OFF=0x{1:X} FN_VA=0x{2:X8} FN_RVA=0x{3:X8} DISP_HITS={4}"-f$c.Slot,$c.SlotOff,$c.VA,$c.RVA,$c.Hits.Count))
    foreach($h in $c.Hits){$abs=[int]$c.RVA+$h;$lines.Add(("  DISP_RVA=0x{0:X8} HEX={1}"-f$abs,(HexWindow $moduleBytes $abs 12 20)))}
    $dc=Find-DirectCallers $c.VA
    $lines.Add("  DIRECT_CALLERS=$($dc.Count)")
    foreach($x in $dc|Select-Object -First 40){$lines.Add(("    CALL_RVA=0x{0:X8} CALLER_RVA=0x{1:X8} HEX={2}"-f$x.CallRva,$x.CallerRva,$x.Hex))}
    $ic=Find-IndirectSlotCalls $c.SlotOff
    $lines.Add("  INDIRECT_SLOT_CALLS=$($ic.Count)")
    foreach($x in $ic|Select-Object -First 80){$lines.Add(("    CALL_RVA=0x{0:X8} CALLER_RVA=0x{1:X8} HEX={2}"-f$x.CallRva,$x.CallerRva,$x.Hex))}
}
$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add("VTABLE_SLOTS=$($slots.Count)")
$lines.Add("FIELD_1C0_CANDIDATES=$($cands.Count)")
$lines.Add('STATUS=PASS')
$lines.Add('NOTE=Read-only code-path tracing. A function/slot that references Gauge+0x1C0 is only a setter/update candidate until call-site semantics are proven.')
$lines|Set-Content -LiteralPath $OutputPath -Encoding UTF8
Write-Host 'STATUS=PASS'
Write-Host "PID=$targetPid"
Write-Host "VTABLE_SLOTS=$($slots.Count)"
Write-Host "FIELD_1C0_CANDIDATES=$($cands.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host 'MEMORY_WRITE=NO'
