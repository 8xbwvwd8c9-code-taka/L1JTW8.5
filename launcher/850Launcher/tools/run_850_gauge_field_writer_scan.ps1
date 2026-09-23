param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OwnerReport = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_ui_owner_scan.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_gauge_field_writer_scan.txt"
)

$ErrorActionPreference='Stop'
$ExpectedSha256='FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
if(-not(Test-Path -LiteralPath $ClientPath)){throw "Client not found: $ClientPath"}
if(-not(Test-Path -LiteralPath $OwnerReport)){throw "Owner report not found: $OwnerReport"}
$sha=(Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha-ne$ExpectedSha256){throw "Client authority mismatch: $sha"}
$report=Get-Content -LiteralPath $OwnerReport
function Get-RV([string]$prefix){$l=$report|Where-Object{$_-like "$prefix*"}|Select-Object -First 1;if(-not$l){return $null};return ($l-split'=',2)[1].Trim()}
$targetPid=[int](Get-RV 'PID=');$start=Get-RV 'PROCESS_START_UTC=';$rh=(Get-RV 'CLIENT_SHA256=').ToUpperInvariant();$ra=Get-RV 'CLIENT_AUTHORITY='
if($rh-ne$ExpectedSha256 -or $ra-ne'1'){throw 'Owner report authority mismatch.'}
$p=Get-Process -Id $targetPid -ErrorAction Stop
if($p.HasExited){throw 'PID exited.'}
$module=$p.MainModule;if(-not$module){throw 'MainModule unavailable; run elevated.'}
if(-not[string]::Equals([IO.Path]::GetFullPath($module.FileName),[IO.Path]::GetFullPath($ClientPath),[StringComparison]::OrdinalIgnoreCase)){throw 'PID is not authoritative Lin.bin2.'}
if($start -and $p.StartTime.ToUniversalTime().ToString('o') -ne $start){throw 'Owner report stale; rerun owner scan.'}
$base=[long]$module.BaseAddress;$size=[int]$module.ModuleMemorySize

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class GaugeWriterMem850 {
 const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
 [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
 public static byte[] Read(int pid,long a,int s){IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);if(h==IntPtr.Zero)throw new Exception("OpenProcess Win32="+Marshal.GetLastWin32Error());try{byte[] b=new byte[s];IntPtr r;if(!ReadProcessMemory(h,new IntPtr(a),b,s,out r))return new byte[0];int n=(int)Math.Min((long)s,r.ToInt64());if(n==s)return b;byte[] o=new byte[n];Array.Copy(b,o,n);return o;}finally{CloseHandle(h);}}
}
"@

$moduleBytes=[GaugeWriterMem850]::Read($targetPid,$base,$size)
if($moduleBytes.Length-lt0x1000){throw 'Failed to read runtime module image.'}
function Get-ExecRanges([byte[]]$d){$r=New-Object System.Collections.Generic.List[object];$pe=[BitConverter]::ToInt32($d,0x3C);$n=[BitConverter]::ToUInt16($d,$pe+6);$os=[BitConverter]::ToUInt16($d,$pe+20);$s=$pe+24+$os;for($i=0;$i-lt$n;$i++){$o=$s+40*$i;if($o+40-gt$d.Length){break};$vs=[BitConverter]::ToUInt32($d,$o+8);$va=[BitConverter]::ToUInt32($d,$o+12);$ch=[BitConverter]::ToUInt32($d,$o+36);if(($ch-band0x20000000)-ne0){$r.Add([pscustomobject]@{S=[long]$va;E=[long]([Math]::Min([long]$d.Length,[long]$va+[Math]::Max([long]$vs,1)))})}};return $r}
$exec=Get-ExecRanges $moduleBytes
function Is-Exec([long]$rva){foreach($x in $exec){if($rva-ge$x.S-and$rva-lt$x.E){return $true}};return $false}
function Find-FnStart([int]$rva){$lo=[Math]::Max(0,$rva-0x180);for($i=$rva;$i-ge$lo;$i--){if($i+2-lt$moduleBytes.Length-and$moduleBytes[$i]-eq0x55-and$moduleBytes[$i+1]-eq0x8B-and$moduleBytes[$i+2]-eq0xEC){return $i};if($i+1-lt$moduleBytes.Length-and$moduleBytes[$i]-eq0xCC-and$moduleBytes[$i+1]-eq0xCC){for($j=$i+2;$j-lt[Math]::Min($rva,$i+0x30);$j++){if($moduleBytes[$j]-eq0x55-and$moduleBytes[$j+1]-eq0x8B-and$moduleBytes[$j+2]-eq0xEC){return $j}}}};return $rva}
function HexCtx([int]$rva,[int]$pre=16,[int]$post=28){$s=[Math]::Max(0,$rva-$pre);$e=[Math]::Min($moduleBytes.Length-1,$rva+$post);return (($moduleBytes[$s..$e]|ForEach-Object{$_.ToString('X2')})-join' ')}
function RegName([int]$n){@('EAX','ECX','EDX','EBX','ESP','EBP','ESI','EDI')[$n-band7]}

$hits=New-Object System.Collections.Generic.List[object]
foreach($x in $exec){for($i=[int]$x.S+4;$i-lt[int]$x.E-8;$i++){
    if($moduleBytes[$i]-ne0xC0-or$moduleBytes[$i+1]-ne0x01-or$moduleBytes[$i+2]-ne0x00-or$moduleBytes[$i+3]-ne0x00){continue}
    $role=$null;$op='';$baseReg='';$srcReg='';$instr=$i
    $mr=$moduleBytes[$i-1];$mod=($mr-shr6)-band3;$rm=$mr-band7;$reg=($mr-shr3)-band7
    if($mod-ne2){continue}
    $opc=$moduleBytes[$i-2]
    switch($opc){
      0x89 {$role='WRITE';$op='MOV_R32_TO_MEM';$baseReg=RegName $rm;$srcReg=RegName $reg;$instr=$i-2}
      0x88 {$role='WRITE';$op='MOV_R8_TO_MEM';$baseReg=RegName $rm;$srcReg=RegName $reg;$instr=$i-2}
      0xC7 {if($reg-eq0){$role='WRITE';$op='MOV_IMM32_TO_MEM';$baseReg=RegName $rm;$instr=$i-2}}
      0xC6 {if($reg-eq0){$role='WRITE';$op='MOV_IMM8_TO_MEM';$baseReg=RegName $rm;$instr=$i-2}}
      0x01 {$role='RMW';$op='ADD_R32_TO_MEM';$baseReg=RegName $rm;$srcReg=RegName $reg;$instr=$i-2}
      0x29 {$role='RMW';$op='SUB_R32_FROM_MEM';$baseReg=RegName $rm;$srcReg=RegName $reg;$instr=$i-2}
      0x31 {$role='RMW';$op='XOR_R32_WITH_MEM';$baseReg=RegName $rm;$srcReg=RegName $reg;$instr=$i-2}
      0x8B {$role='READ';$op='MOV_MEM_TO_R32';$baseReg=RegName $rm;$srcReg=RegName $reg;$instr=$i-2}
      0x83 {$role='READ_OR_RMW';$op=('OP83_GRP'+$reg);$baseReg=RegName $rm;$instr=$i-2}
    }
    if(-not$role-and$i-ge4-and$moduleBytes[$i-4]-eq0xF3-and$moduleBytes[$i-3]-eq0x0F-and$moduleBytes[$i-2]-eq0x11){$role='WRITE';$op='MOVSS_XMM_TO_MEM';$baseReg=RegName $rm;$srcReg=('XMM'+$reg);$instr=$i-4}
    if(-not$role-and$i-ge3-and$moduleBytes[$i-3]-eq0x66-and$moduleBytes[$i-2]-eq0x89){$role='WRITE';$op='MOV_R16_TO_MEM';$baseReg=RegName $rm;$srcReg=RegName $reg;$instr=$i-3}
    if(-not$role){continue}
    $fn=Find-FnStart $instr
    $hits.Add([pscustomobject]@{Role=$role;Op=$op;Rva=$instr;Fn=$fn;Base=$baseReg;Src=$srcReg;Hex=(HexCtx $instr)})
}}

$writers=@($hits|Where-Object{$_.Role-eq'WRITE'-or$_.Role-eq'RMW'})
$reads=@($hits|Where-Object{$_.Role-like'READ*'})
$uniqFns=@($writers|Select-Object -ExpandProperty Fn -Unique|Sort-Object)

function Find-DirectCallers([int]$targetRva){$out=New-Object System.Collections.Generic.List[object];foreach($x in $exec){for($i=[int]$x.S;$i-lt[int]$x.E-5;$i++){if($moduleBytes[$i]-ne0xE8){continue};$rel=[BitConverter]::ToInt32($moduleBytes,$i+1);$dst=[long]$i+5+$rel;if($dst-eq$targetRva){$out.Add([pscustomobject]@{Rva=$i;Fn=(Find-FnStart $i);Hex=(HexCtx $i 12 24)})}}};return $out}

$parent=Split-Path -Parent $OutputPath;if($parent-and-not(Test-Path -LiteralPath $parent)){New-Item -ItemType Directory -Force -Path $parent|Out-Null}
$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_GAUGE_FIELD_WRITER_SCAN')
$lines.Add("PID=$targetPid")
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add(("MODULE_BASE=0x{0:X8}"-f$base))
$lines.Add('TARGET_OFFSET=0x1C0')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('SOURCE_MODIFIED=NO')
$lines.Add('')
$lines.Add('[ALL_DISP_1C0_ACCESS]')
$lines.Add("TOTAL=$($hits.Count)")
$lines.Add("WRITES_OR_RMW=$($writers.Count)")
$lines.Add("READS_OR_TESTS=$($reads.Count)")
foreach($h in $hits){$lines.Add(("ACCESS ROLE={0} OP={1} RVA=0x{2:X8} FN_RVA=0x{3:X8} BASE={4} SRC={5} HEX={6}"-f$h.Role,$h.Op,$h.Rva,$h.Fn,$h.Base,$h.Src,$h.Hex))}
$lines.Add('')
$lines.Add('[WRITER_FUNCTIONS]')
$lines.Add("COUNT=$($uniqFns.Count)")
foreach($fn in $uniqFns){$w=@($writers|Where-Object{$_.Fn-eq$fn});$lines.Add(("WRITER_FN RVA=0x{0:X8} ACCESS_HITS={1}"-f$fn,$w.Count));foreach($z in $w){$lines.Add(("  WRITE RVA=0x{0:X8} OP={1} BASE={2} SRC={3} HEX={4}"-f$z.Rva,$z.Op,$z.Base,$z.Src,$z.Hex))};$c=Find-DirectCallers $fn;$lines.Add("  DIRECT_CALLERS=$($c.Count)");foreach($cc in $c|Select-Object -First 24){$lines.Add(("    CALL_RVA=0x{0:X8} CALLER_RVA=0x{1:X8} HEX={2}"-f$cc.Rva,$cc.Fn,$cc.Hex))}}
$lines.Add('')
$lines.Add('[KNOWN_SLOT9_RECHECK]')
$slot9=0x00874850
$slotHits=@($hits|Where-Object{$_.Fn-eq$slot9})
$lines.Add("SLOT9_FN_RVA=0x00874850")
$lines.Add("SLOT9_ACCESS_HITS=$($slotHits.Count)")
foreach($h in $slotHits){$lines.Add(("  ROLE={0} OP={1} RVA=0x{2:X8} BASE={3} SRC={4}"-f$h.Role,$h.Op,$h.Rva,$h.Base,$h.Src))}
$slotWrites=@($slotHits|Where-Object{$_.Role-eq'WRITE'-or$_.Role-eq'RMW'}).Count
$lines.Add("SLOT9_WRITES=$slotWrites")
$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add("WRITER_ACCESS_HITS=$($writers.Count)")
$lines.Add("WRITER_FUNCTIONS=$($uniqFns.Count)")
$lines.Add("SLOT9_IS_WRITER=$([int]($slotWrites-gt0))")
$lines.Add('STATUS=PASS')
$lines.Add('NOTE=Read-only x86 code scan. Only explicit stores/RMW using displacement 0x1C0 are writer candidates; read/test references are not setters. Candidates still require call-site semantics and behavior/restart validation.')
$lines|Set-Content -LiteralPath $OutputPath -Encoding UTF8
Write-Host 'STATUS=PASS';Write-Host "PID=$targetPid";Write-Host "WRITER_ACCESS_HITS=$($writers.Count)";Write-Host "WRITER_FUNCTIONS=$($uniqFns.Count)";Write-Host "SLOT9_IS_WRITER=$([int]($slotWrites-gt0))";Write-Host "OUTPUT=$OutputPath";Write-Host 'MEMORY_WRITE=NO'
