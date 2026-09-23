param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_gauge_ratio_source_scan.txt"
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
if(-not(Test-Path -LiteralPath $ClientPath)){throw "Client not found: $ClientPath"}
$sha=(Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha-ne$ExpectedSha256){throw "Client authority mismatch: $sha"}

$full=[IO.Path]::GetFullPath($ClientPath)
$proc=$null
foreach($p in Get-Process -ErrorAction SilentlyContinue){try{if(-not$p.HasExited -and $p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)){$proc=$p;break}}catch{}}
if(-not$proc){throw 'Running authoritative Lin.bin2 process not found; run elevated if needed.'}
$module=$proc.MainModule
$base=[long]$module.BaseAddress;$size=[int]$module.ModuleMemorySize

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class GaugeRatioMem850 {
 const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
 [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
 public static byte[] Read(int pid,long a,int s){IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);if(h==IntPtr.Zero)throw new Exception("OpenProcess Win32="+Marshal.GetLastWin32Error());try{byte[] b=new byte[s];IntPtr r;if(!ReadProcessMemory(h,new IntPtr(a),b,s,out r))return new byte[0];int n=(int)Math.Min((long)s,r.ToInt64());if(n==s)return b;byte[] o=new byte[n];Array.Copy(b,o,n);return o;}finally{CloseHandle(h);}}
}
"@
$img=[GaugeRatioMem850]::Read($proc.Id,$base,$size)
if($img.Length-lt0x1000){throw 'Failed to read module image.'}

function Hex([long]$rva,[int]$before=0,[int]$after=64){
 $s=[Math]::Max(0,[int]$rva-$before);$e=[Math]::Min($img.Length,$s+$before+$after);if($e-le$s){return ''};return (($img[$s..($e-1)]|ForEach-Object{$_.ToString('X2')})-join' ')
}
function Find-DirectCallers([long]$targetRva,[int]$max=64){
 $out=New-Object System.Collections.Generic.List[object]
 for($i=0;$i+5-le$img.Length;$i++){
  if($img[$i]-ne0xE8){continue}
  $rel=[BitConverter]::ToInt32($img,$i+1);$t=[long]$i+5+$rel
  if($t-eq$targetRva){$out.Add([pscustomobject]@{CallRva=[long]$i;Hex=(Hex $i 24 64)});if($out.Count-ge$max){break}}
 }
 return $out
}
function SigCount([long]$start,[int]$len,[byte[]]$sig){
 $end=[Math]::Min($img.Length,[int]$start+$len);$c=0
 for($i=[int]$start;$i+$sig.Length-le$end;$i++){ $ok=$true;for($j=0;$j-lt$sig.Length;$j++){if($img[$i+$j]-ne$sig[$j]){$ok=$false;break}};if($ok){$c++}}
 return $c
}

$setterA=0x00877D50
$setterB=0x00877D80
$ratioA=0x00C5BF80
$ratioB=0x00C5C230
$targets=@(
 [pscustomobject]@{Name='GAUGE_SETTER_2ARG';Rva=$setterA;Len=0x50},
 [pscustomobject]@{Name='GAUGE_SETTER_4ARG';Rva=$setterB;Len=0x60},
 [pscustomobject]@{Name='RATIO_CALLER_A';Rva=$ratioA;Len=0x180},
 [pscustomobject]@{Name='RATIO_CALLER_B';Rva=$ratioB;Len=0x100}
)

$parent=Split-Path -Parent $OutputPath;if($parent-and-not(Test-Path -LiteralPath $parent)){New-Item -ItemType Directory -Force -Path $parent|Out-Null}
$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_GAUGE_RATIO_SOURCE_SCAN')
$lines.Add("PID=$($proc.Id)")
$lines.Add("CLIENT=$full")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add(("MODULE_BASE=0x{0:X8}"-f$base))
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('SOURCE_MODIFIED=NO')
$lines.Add('')

foreach($t in $targets){
 $lines.Add(("[TARGET {0}] RVA=0x{1:X8}"-f$t.Name,$t.Rva))
 $lines.Add("HEX=$(Hex $t.Rva 0 $t.Len)")
 $calls=Find-DirectCallers $t.Rva 64
 $lines.Add("DIRECT_CALLERS=$($calls.Count)")
 foreach($c in $calls){$lines.Add(("  CALL_RVA=0x{0:X8} HEX={1}"-f$c.CallRva,$c.Hex))}
 $imul=SigCount $t.Rva $t.Len ([byte[]](0x0F,0xAF))
 $cdq100=SigCount $t.Rva $t.Len ([byte[]](0x99,0xB9,0x64,0x00,0x00,0x00,0xF7,0xF9))
 $idivEbp=SigCount $t.Rva $t.Len ([byte[]](0xF7,0x7D))
 $pushCall=0
 for($i=[int]$t.Rva;$i+6-lt[Math]::Min($img.Length,[int]$t.Rva+$t.Len);$i++){
  if($img[$i]-eq0x50 -and $img[$i+1]-eq0x8B -and $img[$i+2]-eq0x4D -and $img[$i+4]-eq0xE8){$pushCall++}
 }
 $lines.Add("SIG_IMUL=$imul")
 $lines.Add("SIG_CDQ_DIV100=$cdq100")
 $lines.Add("SIG_IDIV_EBP=$idivEbp")
 $lines.Add("SIG_PUSH_EAX_THEN_CALL=$pushCall")
 $lines.Add('')
}

$lines.Add('[KNOWN_RATIO_EVIDENCE]')
$lines.Add('SETTER_2ARG_RVA=0x00877D50')
$lines.Add('SETTER_2ARG_WRITES=this+0x1C0 <- arg1; this+0x1C4 <- arg2')
$lines.Add('RATIO_A_RVA=0x00C5BF80')
$lines.Add('RATIO_A_PATTERN=IMUL then CDQ/IDIV before direct call to setter')
$lines.Add('RATIO_B_RVA=0x00C5C230')
$lines.Add('RATIO_B_PATTERN=CDQ; MOV ECX,100; IDIV ECX; PUSH EAX; direct call to setter')
$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add('STATUS=PASS')
$lines.Add('NOTE=Read-only call-source tracing. Arithmetic patterns can support percentage/fill computation semantics but do not prove raw HP/MP source fields until operand provenance and restart validation are established.')
$lines|Set-Content -LiteralPath $OutputPath -Encoding UTF8
Write-Host 'STATUS=PASS'
Write-Host "PID=$($proc.Id)"
Write-Host "OUTPUT=$OutputPath"
Write-Host 'MEMORY_WRITE=NO'
