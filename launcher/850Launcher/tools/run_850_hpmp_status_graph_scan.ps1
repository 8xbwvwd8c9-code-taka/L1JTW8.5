param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OwnerReport = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_ui_owner_scan.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_status_graph_scan.txt"
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
if (-not (Test-Path -LiteralPath $OwnerReport)) { throw "Owner report not found: $OwnerReport" }
$sha=(Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha-ne$ExpectedSha256){throw "Client authority mismatch: $sha"}

$report=Get-Content -LiteralPath $OwnerReport -ErrorAction Stop
function Get-ReportValue([string]$prefix){
    $l=$report|Where-Object{$_-like "$prefix*"}|Select-Object -First 1
    if(-not$l){return $null}
    return ($l-split'=',2)[1].Trim()
}
function HexFrom([string]$line,[string]$pat){$m=[regex]::Match($line,$pat);if(-not$m.Success){return $null};return [long][Convert]::ToUInt64($m.Groups[1].Value,16)}
$targetPid=[int](Get-ReportValue 'PID=')
$start=Get-ReportValue 'PROCESS_START_UTC='
$rh=(Get-ReportValue 'CLIENT_SHA256=').ToUpperInvariant()
$ra=Get-ReportValue 'CLIENT_AUTHORITY='
if($rh-ne$ExpectedSha256 -or $ra-ne'1'){throw 'Owner report authority mismatch.'}
$hpLine=$report|Where-Object{$_-match'^OBJECT NAME=HpGauge_Image ADDR=0x'}|Select-Object -First 1
$mpLine=$report|Where-Object{$_-match'^OBJECT NAME=MpGauge_Image ADDR=0x'}|Select-Object -First 1
$stLine=$report|Where-Object{$_-match'^\s+FIELD \+0xE8=0x'}|Select-Object -First 1
$rnLine=$report|Where-Object{$_-match'^\s+FIELD \+0xEC=0x'}|Select-Object -First 1
if(-not$hpLine-or-not$mpLine-or-not$stLine-or-not$rnLine){throw 'Required owner evidence missing.'}
$hp=HexFrom $hpLine 'ADDR=0x([0-9A-Fa-f]+)';$mp=HexFrom $mpLine 'ADDR=0x([0-9A-Fa-f]+)';$status=HexFrom $stLine 'FIELD \+0xE8=0x([0-9A-Fa-f]+)';$renew=HexFrom $rnLine 'FIELD \+0xEC=0x([0-9A-Fa-f]+)'
$p=Get-Process -Id $targetPid -ErrorAction Stop
if($p.HasExited){throw 'PID exited.'}
$module=$p.MainModule;if(-not$module){throw 'MainModule unavailable; run elevated.'}
if(-not[string]::Equals([IO.Path]::GetFullPath($module.FileName),[IO.Path]::GetFullPath($ClientPath),[StringComparison]::OrdinalIgnoreCase)){throw 'PID is not authoritative Lin.bin2.'}
if($start -and $p.StartTime.ToUniversalTime().ToString('o') -ne $start){throw 'Owner report is stale; rerun owner scan.'}
$base=[long]$module.BaseAddress;$size=[int]$module.ModuleMemorySize

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class HpmpGraphMem850 {
 const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
 [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
 public static byte[] Read(int pid,long a,int s){IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);if(h==IntPtr.Zero)throw new Exception("OpenProcess Win32="+Marshal.GetLastWin32Error());try{byte[] b=new byte[s];IntPtr r;if(!ReadProcessMemory(h,new IntPtr(a),b,s,out r))return new byte[0];int n=(int)Math.Min((long)s,r.ToInt64());if(n==s)return b;byte[] o=new byte[n];Array.Copy(b,o,n);return o;}finally{CloseHandle(h);}}
}
"@

$moduleBytes=[HpmpGraphMem850]::Read($targetPid,$base,$size)
function ExecRanges([byte[]]$d){$r=New-Object System.Collections.Generic.List[object];$pe=[BitConverter]::ToInt32($d,0x3C);$n=[BitConverter]::ToUInt16($d,$pe+6);$os=[BitConverter]::ToUInt16($d,$pe+20);$s=$pe+24+$os;for($i=0;$i-lt$n;$i++){$o=$s+40*$i;if($o+40-gt$d.Length){break};$vs=[BitConverter]::ToUInt32($d,$o+8);$va=[BitConverter]::ToUInt32($d,$o+12);$ch=[BitConverter]::ToUInt32($d,$o+36);if(($ch-band0x20000000)-ne0){$r.Add([pscustomobject]@{S=[long]$va;E=[long]([Math]::Min([long]$d.Length,[long]$va+[Math]::Max([long]$vs,1)))})}};return $r}
$exec=ExecRanges $moduleBytes
function IsExec([long]$rva){foreach($x in $exec){if($rva-ge$x.S-and$rva-lt$x.E){return $true}};return $false}
function VtCount([uint32]$va,[int]$max=16){if($va-lt$base-or$va-ge($base+$size)){return 0};$r=[long]$va-$base;$c=0;for($i=0;$i-lt$max;$i++){$o=$r+4*$i;if($o+4-gt$moduleBytes.Length){break};$fn=[BitConverter]::ToUInt32($moduleBytes,[int]$o);if($fn-lt$base-or$fn-ge($base+$size)){break};if(-not(IsExec ([long]$fn-$base))){break};$c++};return $c}
function Ascii([byte[]]$b,[int]$max=16){$out=New-Object System.Collections.Generic.List[string];$s=-1;for($i=0;$i-le$b.Length;$i++){$ok=($i-lt$b.Length-and$b[$i]-ge0x20-and$b[$i]-le0x7E);if($ok){if($s-lt0){$s=$i};continue};if($s-ge0){$n=$i-$s;if($n-ge4){$out.Add(("+0x{0:X3}:{1}"-f$s,[Text.Encoding]::ASCII.GetString($b,$s,$n)));if($out.Count-ge$max){break}};$s=-1}};return ($out-join'|')}
function ReadObj([long]$a,[int]$n=0x320){$b=[HpmpGraphMem850]::Read($targetPid,$a,$n);if($b.Length-lt4){return $null};$vt=[BitConverter]::ToUInt32($b,0);$vc=VtCount $vt 16;return [pscustomobject]@{A=$a;B=$b;VT=$vt;VC=$vc;ASCII=(Ascii $b 16)}}

$parent=Split-Path -Parent $OutputPath;if($parent-and-not(Test-Path -LiteralPath $parent)){New-Item -ItemType Directory -Force -Path $parent|Out-Null}
$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))");$lines.Add('MODE=850_HPMP_STATUS_GRAPH_SCAN');$lines.Add("PID=$targetPid");$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))");$lines.Add("CLIENT_SHA256=$sha");$lines.Add('CLIENT_AUTHORITY=1');$lines.Add('MEMORY_WRITE=NO');$lines.Add('SOURCE_MODIFIED=NO');$lines.Add('')
$lines.Add('[ROOTS]');$lines.Add(("HP_GAUGE=0x{0:X8}"-f$hp));$lines.Add(("MP_GAUGE=0x{0:X8}"-f$mp));$lines.Add(("STATUS_WINDOW=0x{0:X8}"-f$status));$lines.Add(("RENEWAL_STATUS_UI=0x{0:X8}"-f$renew));$lines.Add('')

foreach($pair in @(@('HP_GAUGE',$hp),@('MP_GAUGE',$mp))){$o=ReadObj $pair[1] 0x240;$lines.Add("[GAUGE $($pair[0])] ADDR=0x$('{0:X8}'-f$pair[1]) VTABLE=0x$('{0:X8}'-f$o.VT) ASCII=$($o.ASCII)");foreach($off in 0x180..0x1D0|Where-Object{($_-0x180)%2-eq0}){if($off+2-le$o.B.Length){$u=[BitConverter]::ToUInt16($o.B,$off);if($u-le2048){$lines.Add(("  U16 +0x{0:X3}={1}"-f$off,$u))}}};$lines.Add('')}

$roots=@([pscustomobject]@{N='STATUS_WINDOW';A=$status;Size=0x500},[pscustomobject]@{N='RENEWAL_STATUS_UI';A=$renew;Size=0x900})
$direct=0;$semantic=0
foreach($r in $roots){$ro=ReadObj $r.A $r.Size;$lines.Add("[ROOT $($r.N)] ADDR=0x$('{0:X8}'-f$r.A) VTABLE=0x$('{0:X8}'-f$ro.VT) VT_ENTRIES=$($ro.VC) ASCII=$($ro.ASCII)");for($off=4;$off+4-le$ro.B.Length;$off+=4){$ptr=[BitConverter]::ToUInt32($ro.B,$off);if($ptr-lt0x10000){continue};$co=ReadObj $ptr 0x280;if($null-eq$co-or$co.VC-lt8){continue};$direct++;$tag='';if($ptr-eq$hp){$tag=' EXACT_HP'}elseif($ptr-eq$mp){$tag=' EXACT_MP'};if($co.ASCII-match'(?i)HP|MP|Gauge|Label|Status|Defence|AC|MR|Health|Mana'){$semantic++;$tag+=' SEMANTIC'};$lines.Add(("  CHILD +0x{0:X3}=0x{1:X8} VTABLE=0x{2:X8} VT_ENTRIES={3}{4} ASCII={5}"-f$off,$ptr,$co.VT,$co.VC,$tag,$co.ASCII))};$lines.Add('')}
$lines.Add('[SUMMARY]');$lines.Add("DIRECT_CHILD_OBJECTS=$direct");$lines.Add("SEMANTIC_CHILD_OBJECTS=$semantic");$lines.Add('STATUS=PASS');$lines.Add('NOTE=Read-only status UI graph inspection. Gauge +0x1C0 behavioral value is treated as UI fill-state evidence only, not raw HP/MP, until numeric semantics and restart validation prove otherwise.')
$lines|Set-Content -LiteralPath $OutputPath -Encoding UTF8
Write-Host 'STATUS=PASS';Write-Host "PID=$targetPid";Write-Host "DIRECT_CHILD_OBJECTS=$direct";Write-Host "SEMANTIC_CHILD_OBJECTS=$semantic";Write-Host "OUTPUT=$OutputPath";Write-Host 'MEMORY_WRITE=NO'