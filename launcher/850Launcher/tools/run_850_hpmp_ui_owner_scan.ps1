param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$AuditReport = "I:\8.50c客服端\auto_runtime_audit_report.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_ui_owner_scan.txt"
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$sha = (Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }

function Resolve-AuthoritativeProcess {
    param([string]$ExpectedPath,[string]$ReportPath)
    $full = [IO.Path]::GetFullPath($ExpectedPath)
    foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
        try {
            if ($p.HasExited) { continue }
            if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)) {
                return [pscustomobject]@{ Process=$p; Detection='MAINMODULE_PATH' }
            }
        } catch { }
    }
    if (Test-Path -LiteralPath $ReportPath) {
        $c = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue
        $pidLine = $c | Where-Object { $_ -match '^PID=\d+$' } | Select-Object -First 1
        $authLine = $c | Where-Object { $_ -eq 'CLIENT_AUTHORITY=1' } | Select-Object -First 1
        $hashLine = $c | Where-Object { $_ -match '^CLIENT_SHA256=' } | Select-Object -First 1
        if ($pidLine -and $authLine -and $hashLine -and (($hashLine -split '=',2)[1].Trim().ToUpperInvariant() -eq $ExpectedSha256)) {
            $pid = [int](($pidLine -split '=',2)[1])
            try {
                $p = Get-Process -Id $pid -ErrorAction Stop
                if (-not $p.HasExited) { return [pscustomobject]@{ Process=$p; Detection='AUTHORITATIVE_AUDIT_REPORT' } }
            } catch { }
        }
    }
    throw "Running authoritative Lin.bin2 process not found."
}

$resolved = Resolve-AuthoritativeProcess -ExpectedPath $ClientPath -ReportPath $AuditReport
$proc = $resolved.Process
$detect = $resolved.Detection
$module = $proc.MainModule
if (-not $module) { throw "MainModule unavailable; run this PowerShell elevated." }
$base = [long]$module.BaseAddress
$size = [int]$module.ModuleMemorySize

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

public sealed class HpAsciiHit850 { public string Name; public long Address; public long RegionBase; public long RegionSize; }
public sealed class HpDwordHit850 { public uint Value; public long Address; public long RegionBase; public long RegionSize; }

public static class HpOwnerMem850 {
    const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
    const uint MEM_COMMIT=0x1000, MEM_PRIVATE=0x20000;
    const uint PAGE_GUARD=0x100, PAGE_NOACCESS=0x01;
    const int Chunk=512*1024;
    [StructLayout(LayoutKind.Sequential)] struct MBI {
        public IntPtr BaseAddress; public IntPtr AllocationBase; public uint AllocationProtect;
        public UIntPtr RegionSize; public uint State; public uint Protect; public uint Type;
    }
    [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
    [DllImport("kernel32.dll",SetLastError=true)] static extern int VirtualQueryEx(IntPtr h,IntPtr a,out MBI m,uint l);
    static bool Readable(uint p) {
        if((p&PAGE_GUARD)!=0 || (p&PAGE_NOACCESS)!=0) return false;
        uint low=p&0xFF; return low==0x02 || low==0x04 || low==0x08 || low==0x20 || low==0x40 || low==0x80;
    }
    public static byte[] Read(int pid,long address,int size) {
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            byte[] b=new byte[size]; IntPtr rp;
            if(!ReadProcessMemory(h,new IntPtr(address),b,size,out rp)) return new byte[0];
            int n=(int)Math.Min((long)size,rp.ToInt64()); if(n==size) return b;
            byte[] o=new byte[n]; Array.Copy(b,o,n); return o;
        } finally { CloseHandle(h); }
    }
    public static List<HpAsciiHit850> ScanPrivateAscii(int pid,string[] names,int maxHitsPerName) {
        var outp=new List<HpAsciiHit850>(); var pats=new Dictionary<string,byte[]>(); var counts=new Dictionary<string,int>();
        foreach(var n in names){pats[n]=Encoding.ASCII.GetBytes(n);counts[n]=0;}
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            long address=0x10000; const long maxAddress=0x7FFF0000; uint mbiSize=(uint)Marshal.SizeOf(typeof(MBI));
            while(address<maxAddress){
                MBI m; if(VirtualQueryEx(h,new IntPtr(address),out m,mbiSize)==0) break;
                long rb=m.BaseAddress.ToInt64(), rs=unchecked((long)m.RegionSize.ToUInt64()); if(rs<=0) break;
                bool okRegion=m.State==MEM_COMMIT && m.Type==MEM_PRIVATE && Readable(m.Protect);
                if(okRegion){
                    long off=0;
                    while(off<rs){
                        int want=(int)Math.Min((long)Chunk,rs-off); if(want<=0) break;
                        byte[] b=new byte[want]; IntPtr rp; bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,want,out rp);
                        int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;
                        foreach(var kv in pats){
                            if(counts[kv.Key]>=maxHitsPerName) continue; var p=kv.Value;
                            for(int i=0;i+p.Length<=got;i++){
                                bool same=true; for(int j=0;j<p.Length;j++){if(b[i+j]!=p[j]){same=false;break;}}
                                if(!same) continue;
                                outp.Add(new HpAsciiHit850{Name=kv.Key,Address=rb+off+i,RegionBase=rb,RegionSize=rs}); counts[kv.Key]++;
                                if(counts[kv.Key]>=maxHitsPerName) break;
                            }
                        }
                        off+=Math.Max(1,want-128);
                    }
                }
                long next=rb+rs; if(next<=address) break; address=next;
            }
            return outp;
        } finally { CloseHandle(h); }
    }
    public static List<HpDwordHit850> ScanPrivateDwords(int pid,uint[] values,int maxHitsPerValue) {
        var outp=new List<HpDwordHit850>(); var wanted=new HashSet<uint>(values); var counts=new Dictionary<uint,int>();
        foreach(var v in values) counts[v]=0;
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            long address=0x10000; const long maxAddress=0x7FFF0000; uint mbiSize=(uint)Marshal.SizeOf(typeof(MBI));
            while(address<maxAddress){
                MBI m; if(VirtualQueryEx(h,new IntPtr(address),out m,mbiSize)==0) break;
                long rb=m.BaseAddress.ToInt64(), rs=unchecked((long)m.RegionSize.ToUInt64()); if(rs<=0) break;
                bool okRegion=m.State==MEM_COMMIT && m.Type==MEM_PRIVATE && Readable(m.Protect);
                if(okRegion){
                    long off=0;
                    while(off<rs){
                        int want=(int)Math.Min((long)Chunk,rs-off); if(want<=0) break;
                        byte[] b=new byte[want]; IntPtr rp; bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,want,out rp);
                        int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;
                        for(int i=0;i+3<got;i+=4){
                            uint v=BitConverter.ToUInt32(b,i); if(!wanted.Contains(v) || counts[v]>=maxHitsPerValue) continue;
                            outp.Add(new HpDwordHit850{Value=v,Address=rb+off+i,RegionBase=rb,RegionSize=rs}); counts[v]++;
                        }
                        off+=want;
                    }
                }
                long next=rb+rs; if(next<=address) break; address=next;
            }
            return outp;
        } finally { CloseHandle(h); }
    }
}
"@

$moduleBytes=[HpOwnerMem850]::Read($proc.Id,$base,$size)
if($moduleBytes.Length -lt 0x1000){throw "Failed to read runtime module image."}

function Get-ExecRanges([byte[]]$data){
    $ranges=New-Object System.Collections.Generic.List[object]
    $pe=[BitConverter]::ToInt32($data,0x3C); $sections=[BitConverter]::ToUInt16($data,$pe+6); $opt=[BitConverter]::ToUInt16($data,$pe+20); $sec=$pe+24+$opt
    for($i=0;$i-lt$sections;$i++){
        $o=$sec+($i*40); if($o+40-gt$data.Length){break}
        $vs=[BitConverter]::ToUInt32($data,$o+8); $va=[BitConverter]::ToUInt32($data,$o+12); $ch=[BitConverter]::ToUInt32($data,$o+36)
        if(($ch-band 0x20000000)-ne0){$ranges.Add([pscustomobject]@{Start=[long]$va;End=[long]([Math]::Min([long]$data.Length,[long]$va+[Math]::Max([long]$vs,1)))})}
    }
    return $ranges
}
$execRanges=Get-ExecRanges $moduleBytes
function Test-ExecRva([long]$rva){foreach($r in $execRanges){if($rva-ge$r.Start -and $rva-lt$r.End){return $true}};return $false}
function Get-VtableEntries([uint32]$va,[int]$max=16){
    if($va-lt$base -or $va-ge($base+$size)){return 0}; $r=[long]$va-$base; $c=0
    for($i=0;$i-lt$max;$i++){$o=$r+($i*4);if($o-lt0-or$o+4-gt$moduleBytes.Length){break};$fn=[BitConverter]::ToUInt32($moduleBytes,[int]$o);if($fn-lt$base-or$fn-ge($base+$size)){break};if(-not(Test-ExecRva ([long]$fn-$base))){break};$c++}
    return $c
}
function Extract-Ascii([byte[]]$b,[int]$max=20){
    $out=New-Object System.Collections.Generic.List[string];$s=-1
    for($i=0;$i-le$b.Length;$i++){$p=($i-lt$b.Length-and$b[$i]-ge0x20-and$b[$i]-le0x7E);if($p){if($s-lt0){$s=$i};continue};if($s-ge0){$n=$i-$s;if($n-ge4){$out.Add(("+0x{0:X3}:{1}"-f$s,[Text.Encoding]::ASCII.GetString($b,$s,$n)));if($out.Count-ge$max){break}};$s=-1}}
    return ($out-join'|')
}
function Find-NamedObjects([string[]]$names){
    $hits=[HpOwnerMem850]::ScanPrivateAscii($proc.Id,$names,32);$objs=New-Object System.Collections.Generic.List[object];$seen=New-Object 'System.Collections.Generic.HashSet[long]'
    $offs=@(0x4,0x8,0xC,0x10,0x14,0x18,0x1C,0x20,0x24,0x28,0x2C,0x30,0x34,0x38,0x3C,0x40)
    foreach($h in $hits){foreach($off in $offs){$obj=[long]$h.Address-$off;if($obj-lt0x10000-or(($obj-band3)-ne0)){continue};$b=[HpOwnerMem850]::Read($proc.Id,$obj,0x320);if($b.Length-lt0x40){continue};$vt=[BitConverter]::ToUInt32($b,0);$e=Get-VtableEntries $vt 16;if($e-lt8){continue};if($seen.Add($obj)){$objs.Add([pscustomobject]@{Name=$h.Name;Address=$obj;NameOff=$off;Vtable=$vt;Entries=$e;Bytes=$b;Region=$h.RegionBase})}}}
    return $objs
}
function Describe-Pointer([uint32]$ptr){
    if($ptr-lt0x10000){return 'INVALID'};$b=[HpOwnerMem850]::Read($proc.Id,$ptr,0x300);if($b.Length-lt4){return 'UNREADABLE'};$vt=[BitConverter]::ToUInt32($b,0);$e=Get-VtableEntries $vt 16;return ("PTR=0x{0:X8} VTABLE=0x{1:X8} VT_ENTRIES={2} ASCII={3}"-f$ptr,$vt,$e,(Extract-Ascii $b 12))
}

$objects=Find-NamedObjects @('HpGauge_Image','MpGauge_Image')
$hp=@($objects|Where-Object{$_.Name-eq'HpGauge_Image'})
$mp=@($objects|Where-Object{$_.Name-eq'MpGauge_Image'})

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_HPMP_UI_OWNER_SCAN")
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_DETECTION=$detect")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add(("MODULE_BASE=0x{0:X8}"-f$base))
$lines.Add(("MODULE_SIZE=0x{0:X}"-f$size))
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("")
$lines.Add("[NAMED_GAUGE_OBJECTS]")
$lines.Add("HP_OBJECTS=$($hp.Count)")
$lines.Add("MP_OBJECTS=$($mp.Count)")
foreach($o in $objects){
    $lines.Add(("OBJECT NAME={0} ADDR=0x{1:X8} VTABLE=0x{2:X8} VT_ENTRIES={3} NAME_OFF=0x{4:X}"-f$o.Name,$o.Address,$o.Vtable,$o.Entries,$o.NameOff))
    $lines.Add("  ASCII=$(Extract-Ascii $o.Bytes 16)")
    foreach($off in @(0xE8,0xEC,0xF0,0xF4,0x148,0x14C,0x150,0x154,0x15C,0x160,0x164,0x168,0x170,0x178,0x180,0x188,0x190,0x198,0x204,0x208,0x20C,0x210)){
        if($off+4-gt$o.Bytes.Length){continue};$v=[BitConverter]::ToUInt32($o.Bytes,$off)
        if($off-eq0xEC-or$v-ge0x10000){$lines.Add(("  FIELD +0x{0:X}=0x{1:X8} {2}"-f$off,$v,$(if($v-ge0x10000){Describe-Pointer $v}else{''})))}
    }
}
$lines.Add("")

$sharedParent=0
$lines.Add("[CHILD_PLUS_EC_PARENT_TEST]")
foreach($h in $hp){foreach($m in $mp){
    if($h.Bytes.Length-lt0xF0-or$m.Bytes.Length-lt0xF0){continue}
    $hpParent=[BitConverter]::ToUInt32($h.Bytes,0xEC);$mpParent=[BitConverter]::ToUInt32($m.Bytes,0xEC)
    $same=($hpParent-ne0-and$hpParent-eq$mpParent);if($same){$sharedParent=1}
    $lines.Add(("PAIR HP=0x{0:X8} MP=0x{1:X8} HP_PARENT_EC=0x{2:X8} MP_PARENT_EC=0x{3:X8} SHARED={4}"-f$h.Address,$m.Address,$hpParent,$mpParent,$(if($same){1}else{0})))
    if($hpParent-ne0){$lines.Add("  HP_PARENT $(Describe-Pointer $hpParent)")}
    if($mpParent-ne0-and$mpParent-ne$hpParent){$lines.Add("  MP_PARENT $(Describe-Pointer $mpParent)")}
}}
$lines.Add("")

$addrValues=@($objects|ForEach-Object{[uint32]$_.Address})
$refs=if($addrValues.Count-gt0){[HpOwnerMem850]::ScanPrivateDwords($proc.Id,$addrValues,256)}else{@()}
$lines.Add("[GAUGE_OBJECT_REFERENCES]")
$lines.Add("REFS=$($refs.Count)")
foreach($r in $refs){$lines.Add(("REF TARGET=0x{0:X8} REF_ADDR=0x{1:X8} REGION=0x{2:X8}"-f$r.Value,$r.Address,$r.RegionBase))}
$lines.Add("")

$pairContainers=0
$lines.Add("[PAIRED_REFERENCE_CONTAINERS]")
foreach($h in $hp){foreach($m in $mp){
    $hr=@($refs|Where-Object{$_.Value-eq[uint32]$h.Address});$mr=@($refs|Where-Object{$_.Value-eq[uint32]$m.Address})
    foreach($a in $hr){foreach($b in $mr){
        if($a.RegionBase-ne$b.RegionBase){continue};$d=[Math]::Abs($a.Address-$b.Address);if($d-gt0x100){continue}
        $pairContainers++;$lo=[Math]::Min($a.Address,$b.Address);$scanStart=[Math]::Max([long]0x10000,$lo-0x400);$scanLen=[int]([Math]::Min([long]0x700,[long]($lo-$scanStart+0x300)))
        $buf=[HpOwnerMem850]::Read($proc.Id,$scanStart,$scanLen)
        $lines.Add(("PAIR_REFS HP_REF=0x{0:X8} MP_REF=0x{1:X8} DIST=0x{2:X} REGION=0x{3:X8}"-f$a.Address,$b.Address,$d,$a.RegionBase))
        $owners=0
        for($i=0;$i+4-le$buf.Length;$i+=4){$cand=$scanStart+$i;if($cand-gt$lo){break};$vt=[BitConverter]::ToUInt32($buf,$i);$e=Get-VtableEntries $vt 16;if($e-lt8){continue};$oh=[long]$a.Address-$cand;$om=[long]$b.Address-$cand;if($oh-lt0-or$om-lt0-or$oh-gt0x400-or$om-gt0x400){continue};$ob=[HpOwnerMem850]::Read($proc.Id,$cand,0x300);$lines.Add(("  OWNER_CAND ADDR=0x{0:X8} VTABLE=0x{1:X8} VT_ENTRIES={2} HP_OFF=0x{3:X} MP_OFF=0x{4:X} ASCII={5}"-f$cand,$vt,$e,$oh,$om,(Extract-Ascii $ob 12)));$owners++;if($owners-ge16){break}}
        if($owners-eq0){$lines.Add("  OWNER_CAND=NONE_WITH_VALID_VTABLE_WITHIN_BACK_0x400")}
    }}
}}
$lines.Add("")
$lines.Add("[SUMMARY]")
$lines.Add("HP_NAMED_OBJECTS=$($hp.Count)")
$lines.Add("MP_NAMED_OBJECTS=$($mp.Count)")
$lines.Add("SHARED_PARENT_PLUS_EC=$sharedParent")
$lines.Add("PAIRED_REFERENCE_CONTAINERS=$pairContainers")
$lines.Add("STATUS=PASS")
$lines.Add("NOTE=Read-only HP/MP UI-object ownership correlation only. A shared UI owner is stronger structural evidence, but no HP/MP gameplay value mapping is accepted without behavior correlation and restart validation.")
$lines|Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "HP_OBJECTS=$($hp.Count)"
Write-Host "MP_OBJECTS=$($mp.Count)"
Write-Host "SHARED_PARENT_PLUS_EC=$sharedParent"
Write-Host "PAIR_CONTAINERS=$pairContainers"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
