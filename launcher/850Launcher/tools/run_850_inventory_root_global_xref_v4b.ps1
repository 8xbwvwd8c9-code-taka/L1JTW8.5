param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_global_xref_v4b.txt"
)

$ErrorActionPreference='Stop'
$ExpectedSha256='FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$SeedGlobalRva=0x012BCEE8L
$RootCtorXrefRva=0x0070184EL
$RootTeardownXrefRva=0x00701E6BL
$RootVtableRva=0x00EDE2F8L

if(-not(Test-Path -LiteralPath $ClientPath)){throw "Client not found: $ClientPath"}
$full=[IO.Path]::GetFullPath($ClientPath)
$sha=(Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha-ne$ExpectedSha256){throw "Client authority mismatch: $sha"}

$proc=$null
foreach($p in Get-Process -ErrorAction SilentlyContinue){
    try{if(-not$p.HasExited -and $p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)){$proc=$p;break}}catch{}
}
if($null-eq$proc){throw 'Running authoritative Lin.bin2 process not found.'}
$module=$proc.MainModule
$base=[long]$module.BaseAddress;$size=[long]$module.ModuleMemorySize;$limit=$base+$size
$globalVa=$base+$SeedGlobalRva

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
public sealed class RootGlobalRegion850 { public long Address; public long Size; public uint Protect; public uint Type; public byte[] Bytes; }
public static class RootGlobalMem850 {
 const uint PROCESS_VM_READ=0x10,PROCESS_QUERY_INFORMATION=0x400,MEM_COMMIT=0x1000,MEM_IMAGE=0x1000000,PAGE_GUARD=0x100,PAGE_NOACCESS=0x01;
 [StructLayout(LayoutKind.Sequential)] struct MBI { public IntPtr BaseAddress,AllocationBase; public uint AllocationProtect; public UIntPtr RegionSize; public uint State,Protect,Type; }
 [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
 [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
 [DllImport("kernel32.dll",SetLastError=true)] static extern int VirtualQueryEx(IntPtr h,IntPtr a,out MBI m,uint l);
 static bool Readable(uint p){if((p&PAGE_GUARD)!=0||(p&PAGE_NOACCESS)!=0)return false;uint x=p&0xFF;return x==0x02||x==0x04||x==0x08||x==0x10||x==0x20||x==0x40||x==0x80;}
 static bool Exec(uint p){uint x=p&0xFF;return x==0x10||x==0x20||x==0x40||x==0x80;}
 public static List<RootGlobalRegion850> ReadImage(int pid,long start,long end,bool execOnly){
  var o=new List<RootGlobalRegion850>();IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);if(h==IntPtr.Zero)throw new Exception("OpenProcess failed "+Marshal.GetLastWin32Error());
  try{uint ms=(uint)Marshal.SizeOf(typeof(MBI));long cur=start;while(cur<end){MBI m;if(VirtualQueryEx(h,new IntPtr(cur),out m,ms)==0)break;long rb=m.BaseAddress.ToInt64(),rs=unchecked((long)m.RegionSize.ToUInt64());if(rs<=0)break;long a=Math.Max(start,rb),z=Math.Min(end,rb+rs);if(z>a&&m.State==MEM_COMMIT&&m.Type==MEM_IMAGE&&Readable(m.Protect)&&(!execOnly||Exec(m.Protect))){long rem=z-a,off=0;while(off<rem){int want=(int)Math.Min(262144L,rem-off);byte[] b=new byte[want];IntPtr rp;bool ok=ReadProcessMemory(h,new IntPtr(a+off),b,want,out rp);int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;if(got>0){if(got!=b.Length){byte[] t=new byte[got];Array.Copy(b,t,got);b=t;}o.Add(new RootGlobalRegion850{Address=a+off,Size=got,Protect=m.Protect,Type=m.Type,Bytes=b});}off+=want;}}long n=rb+rs;if(n<=cur)break;cur=n;}return o;}finally{CloseHandle(h);}}
}
"@

$all=[RootGlobalMem850]::ReadImage($proc.Id,$base,$limit,$false)
$exec=@($all|Where-Object{($_.Protect-band 0xFF)-in@(0x10,0x20,0x40,0x80)})
function R([long]$a){foreach($r in $all){if($a-ge[long]$r.Address-and$a-lt([long]$r.Address+[long]$r.Size)){return$r}};return$null}
function B([long]$a){$r=R $a;if($null-eq$r){return$null};return[byte]$r.Bytes[[int]($a-[long]$r.Address)]}
function U32([long]$a){$r=R $a;if($null-eq$r){return$null};$o=[int]($a-[long]$r.Address);if($o+4-gt$r.Bytes.Length){return$null};return[BitConverter]::ToUInt32($r.Bytes,$o)}
function Hex([long]$a,[int]$n){$r=R $a;if($null-eq$r){return'UNMAPPED'};$o=[int]($a-[long]$r.Address);$n=[Math]::Min($n,$r.Bytes.Length-$o);if($n-le0){return''};return(($r.Bytes[$o..($o+$n-1)]|%{$_.ToString('X2')})-join' ')}
function Prologue([long]$a){$lo=[Math]::Max($base,$a-0x300);for($p=$a;$p-ge$lo;$p--){if((B $p)-eq0x55-and(B ($p+1))-eq0x8B-and(B ($p+2))-eq0xEC){return$p};if((B $p)-eq0x8B-and(B ($p+1))-eq0xFF-and(B ($p+2))-eq0x55-and(B ($p+3))-eq0x8B-and(B ($p+4))-eq0xEC){return$p}};return 0L}
function Kind([long]$literal){
 $m2=B ($literal-2);$m1=B ($literal-1)
 if($m1-eq0xA1){return'LOAD_A1'}
 if($m1-eq0xA3){return'STORE_A3'}
 if($m2-eq0xC7-and$m1-eq0x05){return'STORE_IMM_C705'}
 if($m2-eq0x89-and(($m1-band0xC7)-eq0x05)){return('STORE_89_{0:X2}'-f$m1)}
 if($m2-eq0x8B-and(($m1-band0xC7)-eq0x05)){return('LOAD_8B_{0:X2}'-f$m1)}
 if($m2-eq0xFF-and$m1-eq0x35){return'PUSH_FF35'}
 if($m2-eq0x83-and$m1-eq0x3D){return'CMP_833D'}
 if($m2-eq0x80-and$m1-eq0x3D){return'CMP_803D'}
 return'RAW_LITERAL'
}

$needle=[BitConverter]::GetBytes([uint32]$globalVa)
$refs=New-Object System.Collections.Generic.List[object]
foreach($r in $exec){$b=$r.Bytes;for($i=0;$i-le$b.Length-4;$i++){if($b[$i]-eq$needle[0]-and$b[$i+1]-eq$needle[1]-and$b[$i+2]-eq$needle[2]-and$b[$i+3]-eq$needle[3]){$a=[long]$r.Address+$i;$refs.Add([pscustomobject]@{Literal=$a;Rva=$a-$base;Kind=(Kind $a);Start=(Prologue $a)})}}}

$gv=U32 $globalVa
$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_INVENTORY_ROOT_GLOBAL_XREF_V4B')
$lines.Add("CLIENT=$full")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add(("MODULE_BASE=0x{0:X8}"-f$base))
$lines.Add(("SEED_GLOBAL_RVA=0x{0:X8}"-f$SeedGlobalRva))
$lines.Add(("SEED_GLOBAL_VA=0x{0:X8}"-f$globalVa))
$lines.Add(("SEED_GLOBAL_DWORD={0}"-f($(if($null-ne$gv){'0x'+([uint32]$gv).ToString('X8')}else{'UNREADABLE'}))))
$lines.Add(("ROOT_VTABLE_VA=0x{0:X8}"-f($base+$RootVtableRva)))
$lines.Add(("ROOT_CTOR_XREF_RVA=0x{0:X8}"-f$RootCtorXrefRva))
$lines.Add(("ROOT_TEARDOWN_XREF_RVA=0x{0:X8}"-f$RootTeardownXrefRva))
$lines.Add('RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE')
$lines.Add('HEAP_DEREFERENCE=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[GLOBAL_ABSOLUTE_XREFS]')
$lines.Add("COUNT=$($refs.Count)")
foreach($x in $refs){$sr=if($x.Start-gt0){'0x'+($x.Start-$base).ToString('X8')}else{'NONE'};$ctx=[Math]::Max($base,$x.Literal-16);$lines.Add(("REF RVA=0x{0:X8} KIND={1} FUNC_START_RVA={2} BYTES={3}"-f$x.Rva,$x.Kind,$sr,(Hex $ctx 48)))}
$lines.Add('')
$lines.Add('[ROOT_TEARDOWN_EXPECTATION]')
$expected=@($refs|Where-Object{$_.Rva-ge($RootTeardownXrefRva-0x40)-and$_.Rva-le($RootTeardownXrefRva+0x80)-and$_.Kind-eq'STORE_IMM_C705'})
$lines.Add("TEARDOWN_ZERO_STORE_NEAR_ROOT=$($expected.Count)")
$lines.Add('EXPECTED_SEMANTICS=Candidate global is cleared in ROOT_B path; construction-side assignment or repeated ROOT-context loads are required before owner promotion.')
$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add('STATUS=PASS_GLOBAL_XREF_TRACE_PREPARED')
$lines.Add("GLOBAL_XREF_COUNT=$($refs.Count)")
$lines.Add('OWNER_PROMOTION=NOT_YET')
$lines.Add('NEXT=If a construction-side store writes this/returned object to the same global, promote it to stable ROOT owner candidate; otherwise classify as flag/state global.')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('HEAP_DEREFERENCE=NO')
$lines.Add('MEMORY_WRITE=NO')
$parent=Split-Path -Parent $OutputPath;if($parent-and-not(Test-Path -LiteralPath $parent)){New-Item -ItemType Directory -Force -Path $parent|Out-Null}
[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host 'STATUS=PASS_GLOBAL_XREF_TRACE_PREPARED'
Write-Host "OUTPUT=$OutputPath"
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'HEAP_DEREFERENCE=NO'
Write-Host 'MEMORY_WRITE=NO'
