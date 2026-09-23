param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$AuditReport = "I:\8.50c客服端\auto_runtime_audit_report.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_named_ui_object_scan.txt"
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$sha = (Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}

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
        $content = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue
        $pidLine = $content | Where-Object { $_ -match '^PID=\d+$' } | Select-Object -First 1
        $authLine = $content | Where-Object { $_ -eq 'CLIENT_AUTHORITY=1' } | Select-Object -First 1
        $hashLine = $content | Where-Object { $_ -match '^CLIENT_SHA256=' } | Select-Object -First 1
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

public sealed class UiAsciiHit850 {
    public string Name;
    public long Address;
    public long RegionBase;
    public long RegionSize;
}
public sealed class UiDwordHit850 {
    public uint Value;
    public long Address;
    public long RegionBase;
    public long RegionSize;
}

public static class UiNamedMem850 {
    const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
    const uint MEM_COMMIT=0x1000, MEM_PRIVATE=0x20000;
    const uint PAGE_GUARD=0x100, PAGE_NOACCESS=0x01;
    const int Chunk=512*1024;

    [StructLayout(LayoutKind.Sequential)]
    struct MBI {
        public IntPtr BaseAddress;
        public IntPtr AllocationBase;
        public uint AllocationProtect;
        public UIntPtr RegionSize;
        public uint State;
        public uint Protect;
        public uint Type;
    }

    [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
    [DllImport("kernel32.dll",SetLastError=true)] static extern int VirtualQueryEx(IntPtr h,IntPtr a,out MBI m,uint l);

    static bool Readable(uint p) {
        if((p&PAGE_GUARD)!=0 || (p&PAGE_NOACCESS)!=0) return false;
        uint low=p&0xFF;
        return low==0x02 || low==0x04 || low==0x08 || low==0x20 || low==0x40 || low==0x80;
    }

    public static byte[] Read(int pid,long address,int size) {
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            byte[] b=new byte[size]; IntPtr rp;
            if(!ReadProcessMemory(h,new IntPtr(address),b,size,out rp)) return new byte[0];
            int n=(int)Math.Min((long)size,rp.ToInt64());
            if(n==size) return b;
            byte[] o=new byte[n]; Array.Copy(b,o,n); return o;
        } finally { CloseHandle(h); }
    }

    public static List<UiAsciiHit850> ScanPrivateAscii(int pid,string[] names,int maxHitsPerName) {
        var outp=new List<UiAsciiHit850>();
        var pats=new Dictionary<string,byte[]>(StringComparer.Ordinal);
        var counts=new Dictionary<string,int>(StringComparer.Ordinal);
        foreach(var n in names){ pats[n]=Encoding.ASCII.GetBytes(n); counts[n]=0; }
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            long address=0x10000; const long maxAddress=0x7FFF0000;
            uint mbiSize=(uint)Marshal.SizeOf(typeof(MBI));
            while(address<maxAddress) {
                MBI m; if(VirtualQueryEx(h,new IntPtr(address),out m,mbiSize)==0) break;
                long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64());
                if(rs<=0) break;
                bool okRegion=m.State==MEM_COMMIT && m.Type==MEM_PRIVATE && Readable(m.Protect);
                if(okRegion) {
                    long off=0;
                    while(off<rs) {
                        int want=(int)Math.Min((long)Chunk,rs-off); if(want<=0) break;
                        byte[] b=new byte[want]; IntPtr rp;
                        bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,want,out rp);
                        int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;
                        if(got>0) {
                            foreach(var kv in pats) {
                                if(counts[kv.Key]>=maxHitsPerName) continue;
                                var p=kv.Value;
                                for(int i=0;i+p.Length<=got;i++) {
                                    bool same=true;
                                    for(int j=0;j<p.Length;j++){ if(b[i+j]!=p[j]){same=false;break;} }
                                    if(!same) continue;
                                    outp.Add(new UiAsciiHit850{Name=kv.Key,Address=rb+off+i,RegionBase=rb,RegionSize=rs});
                                    counts[kv.Key]++;
                                    if(counts[kv.Key]>=maxHitsPerName) break;
                                }
                            }
                        }
                        off+=Math.Max(1,want-256);
                    }
                }
                long next=rb+rs; if(next<=address) break; address=next;
            }
            return outp;
        } finally { CloseHandle(h); }
    }

    public static List<UiDwordHit850> ScanPrivateDwords(int pid,uint[] values,int maxHitsPerValue) {
        var outp=new List<UiDwordHit850>();
        var wanted=new HashSet<uint>(values);
        var counts=new Dictionary<uint,int>();
        foreach(var v in values) counts[v]=0;
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            long address=0x10000; const long maxAddress=0x7FFF0000;
            uint mbiSize=(uint)Marshal.SizeOf(typeof(MBI));
            while(address<maxAddress) {
                MBI m; if(VirtualQueryEx(h,new IntPtr(address),out m,mbiSize)==0) break;
                long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64());
                if(rs<=0) break;
                bool okRegion=m.State==MEM_COMMIT && m.Type==MEM_PRIVATE && Readable(m.Protect);
                if(okRegion) {
                    long off=0;
                    while(off<rs) {
                        int want=(int)Math.Min((long)Chunk,rs-off); if(want<=0) break;
                        byte[] b=new byte[want]; IntPtr rp;
                        bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,want,out rp);
                        int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;
                        for(int i=0;i+3<got;i+=4) {
                            uint v=BitConverter.ToUInt32(b,i);
                            if(!wanted.Contains(v) || counts[v]>=maxHitsPerValue) continue;
                            outp.Add(new UiDwordHit850{Value=v,Address=rb+off+i,RegionBase=rb,RegionSize=rs});
                            counts[v]++;
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

$moduleBytes = [UiNamedMem850]::Read($proc.Id,$base,$size)
if ($moduleBytes.Length -lt 0x1000) { throw "Failed to read runtime module image." }

function Get-ExecRanges([byte[]]$data) {
    $ranges = New-Object System.Collections.Generic.List[object]
    $pe = [BitConverter]::ToInt32($data,0x3C)
    $sections = [BitConverter]::ToUInt16($data,$pe+6)
    $optSize = [BitConverter]::ToUInt16($data,$pe+20)
    $sec = $pe + 24 + $optSize
    for ($i=0; $i -lt $sections; $i++) {
        $o=$sec+($i*40); if($o+40 -gt $data.Length){break}
        $vs=[BitConverter]::ToUInt32($data,$o+8)
        $va=[BitConverter]::ToUInt32($data,$o+12)
        $ch=[BitConverter]::ToUInt32($data,$o+36)
        if(($ch-band 0x20000000)-ne 0){
            $ranges.Add([pscustomobject]@{Start=[long]$va;End=[long]([Math]::Min([long]$data.Length,[long]$va+[Math]::Max([long]$vs,1)))})
        }
    }
    return $ranges
}
$execRanges=Get-ExecRanges $moduleBytes
function Test-ExecRva([long]$rva){ foreach($r in $execRanges){ if($rva-ge$r.Start -and $rva-lt$r.End){return $true} }; return $false }
function Get-VtableExecEntries([uint32]$va,[int]$max=16){
    if($va-lt$base -or $va-ge($base+$size)){return 0}
    $rva=[long]$va-$base; $count=0
    for($i=0;$i-lt$max;$i++){
        $o=$rva+($i*4); if($o-lt0 -or $o+4-gt$moduleBytes.Length){break}
        $fn=[BitConverter]::ToUInt32($moduleBytes,[int]$o)
        if($fn-lt$base -or $fn-ge($base+$size)){break}
        if(-not(Test-ExecRva ([long]$fn-$base))){break}
        $count++
    }
    return $count
}
function Extract-Ascii([byte[]]$b,[int]$max=24){
    $out=New-Object System.Collections.Generic.List[string]
    $start=-1
    for($i=0;$i-le$b.Length;$i++){
        $print=($i-lt$b.Length -and $b[$i]-ge0x20 -and $b[$i]-le0x7E)
        if($print){ if($start-lt0){$start=$i}; continue }
        if($start-ge0){
            $len=$i-$start
            if($len-ge4){
                $s=[Text.Encoding]::ASCII.GetString($b,$start,$len)
                $out.Add(("+0x{0:X3}:{1}" -f $start,$s))
                if($out.Count-ge$max){break}
            }
            $start=-1
        }
    }
    return ($out -join '|')
}

$names=@(
    'HPGauge','MPGauge','HpGauge_Image','MpGauge_Image',
    'Inventory','InventoryItemGrid','InvWin','ItemCountLabel','InventoryScroll','InventoryDeleteButton','Userinfo_Scrollbar',
    'PetSummonUI','PetMenu_Window','Pet_Window','PetList','PetName','HP_Image',
    'SummonUI','SummonButton','SummonLevelButton','QuickSummonButton','ManageMember_Window',
    'SpellUI','Spell_Grid','ScrollBar','ClassLevelTab_List','Tabs_List','PassiveSeparator_Image','Class_Button',
    'EquipNStatusUI','ReNStatusUIEx','ReNStatusWinUI'
)

$asciiHits=[UiNamedMem850]::ScanPrivateAscii($proc.Id,$names,64)
$nameOffsets=@(0x4,0x8,0xC,0x10,0x14,0x18,0x1C,0x20,0x24,0x28,0x2C,0x30,0x34,0x38,0x3C,0x40)
$objects=New-Object System.Collections.Generic.List[object]
$seenObj=New-Object 'System.Collections.Generic.HashSet[long]'

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_NAMED_UI_OBJECT_SCAN")
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_DETECTION=$detect")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $base))
$lines.Add(("MODULE_SIZE=0x{0:X}" -f $size))
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("")
$lines.Add("[ASCII_TARGET_HITS]")
$lines.Add("RAW_HITS=$($asciiHits.Count)")
foreach($h in $asciiHits){ $lines.Add(("HIT NAME={0} ADDR=0x{1:X8} REGION=0x{2:X8}" -f $h.Name,$h.Address,$h.RegionBase)) }
$lines.Add("")
$lines.Add("[VERIFIED_NAMED_OBJECTS]")

foreach($h in $asciiHits){
    foreach($off in $nameOffsets){
        $obj=[long]$h.Address-$off
        if($obj-lt0x10000 -or (($obj-band3)-ne0)){continue}
        $head=[UiNamedMem850]::Read($proc.Id,$obj,0x300)
        if($head.Length-lt0x20){continue}
        $vt=[BitConverter]::ToUInt32($head,0)
        $entries=Get-VtableExecEntries $vt 16
        if($entries-lt4){continue}
        if(-not $seenObj.Add($obj)){continue}
        $o=[pscustomobject]@{Name=$h.Name;Address=$obj;NameOffset=$off;Vtable=$vt;Entries=$entries;Bytes=$head}
        $objects.Add($o)
        $lines.Add(("OBJECT NAME={0} ADDR=0x{1:X8} NAME_OFF=0x{2:X} VTABLE=0x{3:X8} VT_ENTRIES={4}" -f $h.Name,$obj,$off,$vt,$entries))
        $asc=Extract-Ascii $head 20
        if($asc){$lines.Add("  ASCII=$asc")}
        for($fo=0x100;$fo-le0x220;$fo+=4){
            if($fo+4-gt$head.Length){break}
            $ptr=[BitConverter]::ToUInt32($head,$fo)
            if($ptr-lt0x10000 -or $ptr-gt0x7FFF0000){continue}
            $child=[UiNamedMem850]::Read($proc.Id,$ptr,0x180)
            if($child.Length-lt0x20){continue}
            $cvt=[BitConverter]::ToUInt32($child,0)
            $ce=Get-VtableExecEntries $cvt 12
            if($ce-lt4){continue}
            $ca=Extract-Ascii $child 8
            $lines.Add(("  CHILD FIELD=+0x{0:X3} PTR=0x{1:X8} VTABLE=0x{2:X8} VT_ENTRIES={3} ASCII={4}" -f $fo,$ptr,$cvt,$ce,$ca))
        }
    }
}

$lines.Add("")
$lines.Add("VERIFIED_OBJECTS=$($objects.Count)")

if($objects.Count-gt0){
    $vals=@($objects | ForEach-Object {[uint32]$_.Address})
    $refs=[UiNamedMem850]::ScanPrivateDwords($proc.Id,$vals,48)
    $lines.Add("")
    $lines.Add("[OBJECT_PARENT_BACKREFS]")
    $lines.Add("BACKREFS=$($refs.Count)")
    foreach($r in $refs){
        $lines.Add(("PARENT_REF TARGET=0x{0:X8} REF_ADDR=0x{1:X8} REGION=0x{2:X8}" -f $r.Value,$r.Address,$r.RegionBase))
    }
}

$hpmp=@($objects | Where-Object { $_.Name -in @('HPGauge','MPGauge','HpGauge_Image','MpGauge_Image') })
$inv=@($objects | Where-Object { $_.Name -in @('Inventory','InventoryItemGrid','InvWin','ItemCountLabel','InventoryScroll','InventoryDeleteButton') })
$pet=@($objects | Where-Object { $_.Name -like 'Pet*' -or $_.Name -eq 'HP_Image' })
$spell=@($objects | Where-Object { $_.Name -like 'Spell*' })

$lines.Add("")
$lines.Add("[SUMMARY]")
$lines.Add("HPMP_NAMED_OBJECTS=$($hpmp.Count)")
$lines.Add("INVENTORY_NAMED_OBJECTS=$($inv.Count)")
$lines.Add("PET_NAMED_OBJECTS=$($pet.Count)")
$lines.Add("SPELL_NAMED_OBJECTS=$($spell.Count)")
$lines.Add("STATUS=PASS")
$lines.Add("NOTE=Read-only exact-name to live-object correlation. Named objects are stronger UI evidence, but no gameplay state mapping or action bridge is accepted without behavior correlation and restart validation.")

$lines | Out-File -LiteralPath $OutputPath -Encoding utf8
Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "RAW_NAME_HITS=$($asciiHits.Count)"
Write-Host "VERIFIED_OBJECTS=$($objects.Count)"
Write-Host "HPMP_NAMED_OBJECTS=$($hpmp.Count)"
Write-Host "INVENTORY_NAMED_OBJECTS=$($inv.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
