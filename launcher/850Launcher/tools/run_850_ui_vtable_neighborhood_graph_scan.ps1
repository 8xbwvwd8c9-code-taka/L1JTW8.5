param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$AuditReport = "I:\8.50c客服端\auto_runtime_audit_report.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_ui_vtable_neighborhood_graph_scan.txt"
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
        $all = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue
        $pidLine = $all | Where-Object { $_ -match '^PID=\d+$' } | Select-Object -First 1
        $authLine = $all | Where-Object { $_ -eq 'CLIENT_AUTHORITY=1' } | Select-Object -First 1
        $hashLine = $all | Where-Object { $_ -match '^CLIENT_SHA256=' } | Select-Object -First 1
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

public sealed class UiGraphHit850 {
    public long Address;
    public long RegionBase;
    public long RegionSize;
    public uint Value;
}

public static class UiGraphMem850 {
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

    static bool Writable(uint p) {
        uint low=p & 0xFF;
        return low==0x04 || low==0x08 || low==0x40 || low==0x80;
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

    public static List<UiGraphHit850> ScanPrivateDwords(int pid,uint[] values,int maxHitsPerValue) {
        var outp=new List<UiGraphHit850>();
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
                bool readable=m.State==MEM_COMMIT && m.Type==MEM_PRIVATE && Writable(m.Protect) && (m.Protect&PAGE_GUARD)==0 && (m.Protect&PAGE_NOACCESS)==0;
                if(readable) {
                    long off=0;
                    while(off<rs) {
                        int want=(int)Math.Min((long)Chunk,rs-off); if(want<=0) break;
                        byte[] b=new byte[want]; IntPtr rp;
                        bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,want,out rp);
                        int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;
                        for(int i=0;i+3<got;i+=4) {
                            uint v=BitConverter.ToUInt32(b,i);
                            if(!wanted.Contains(v)) continue;
                            if(counts[v]>=maxHitsPerValue) continue;
                            outp.Add(new UiGraphHit850{Address=rb+off+i,RegionBase=rb,RegionSize=rs,Value=v});
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

$moduleBytes = [UiGraphMem850]::Read($proc.Id,$base,$size)
if ($moduleBytes.Length -lt 0x1000) { throw "Failed to read runtime module image." }

function Get-ExecRanges([byte[]]$data) {
    $ranges = New-Object System.Collections.Generic.List[object]
    $pe = [BitConverter]::ToInt32($data,0x3C)
    if ($pe -lt 0 -or $pe+0x18 -ge $data.Length) { return $ranges }
    $sections = [BitConverter]::ToUInt16($data,$pe+6)
    $optSize = [BitConverter]::ToUInt16($data,$pe+20)
    $sec = $pe + 24 + $optSize
    for ($i=0; $i -lt $sections; $i++) {
        $o=$sec+($i*40); if($o+40 -gt $data.Length){break}
        $vs=[BitConverter]::ToUInt32($data,$o+8); $va=[BitConverter]::ToUInt32($data,$o+12); $ch=[BitConverter]::ToUInt32($data,$o+36)
        if(($ch -band 0x20000000) -ne 0){ $ranges.Add([pscustomobject]@{Start=[uint32]$va;End=[uint32]([Math]::Min([long]$data.Length,[long]$va+[Math]::Max([long]$vs,1)))}) }
    }
    return $ranges
}
$execRanges=Get-ExecRanges $moduleBytes
function Test-ExecVa([uint32]$va){
    if($va -lt $base -or $va -ge ($base+$size)){return $false}
    $r=[uint32]($va-$base)
    foreach($x in $execRanges){ if($r -ge $x.Start -and $r -lt $x.End){return $true} }
    return $false
}
function Test-Vtable([uint32]$va){
    if($va -lt $base -or $va+12 -ge ($base+$size)){ return 0 }
    $r=[int]($va-$base); $n=0
    for($i=0;$i -lt 12;$i++){
        if($r+($i*4)+4 -gt $moduleBytes.Length){break}
        $f=[BitConverter]::ToUInt32($moduleBytes,$r+($i*4))
        if(Test-ExecVa $f){$n++}else{break}
    }
    return $n
}
function Get-AsciiRuns([byte[]]$b,[int]$minLen=4){
    $out=New-Object System.Collections.Generic.List[string]
    $start=-1
    for($i=0;$i -le $b.Length;$i++){
        $print=$false
        if($i -lt $b.Length){$v=$b[$i];$print=($v -ge 0x20 -and $v -le 0x7E)}
        if($print -and $start -lt 0){$start=$i}
        if((-not $print) -and $start -ge 0){
            $len=$i-$start
            if($len -ge $minLen){
                $s=[Text.Encoding]::ASCII.GetString($b,$start,$len)
                $out.Add(("+0x{0:X3}:{1}" -f $start,$s))
            }
            $start=-1
        }
    }
    return $out
}
function Read-U32([byte[]]$b,[int]$o){ if($o -lt 0 -or $o+4 -gt $b.Length){return $null}; return [BitConverter]::ToUInt32($b,$o) }

$seeds=@(
    [pscustomobject]@{Name='INVENTORY_GRID';Base=[uint32]0x012DE300},
    [pscustomobject]@{Name='INVENTORY_COUNT';Base=[uint32]0x012DE144},
    [pscustomobject]@{Name='PET_HP_TABLE';Base=[uint32]0x012CA8E4},
    [pscustomobject]@{Name='PET_STRONG_SEED';Base=[uint32]0x012CA8E8},
    [pscustomobject]@{Name='SUMMON_UI';Base=[uint32]0x01300850},
    [pscustomobject]@{Name='SPELL_GRID';Base=[uint32]0x01329E58},
    [pscustomobject]@{Name='DELETE_UI';Base=[uint32]0x012DDDC4}
)

$probeOffsets=@(-16,-12,-8,-4,0,4,8,12,16,20,24,28,32)
$probeMap=@{}
$probeValues=New-Object System.Collections.Generic.List[uint32]
foreach($s in $seeds){
    foreach($d in $probeOffsets){
        $v=[uint32]([int64]$s.Base+$d)
        $entries=Test-Vtable $v
        if($entries -ge 3){
            $key=$v.ToString('X8')
            if(-not $probeMap.ContainsKey($key)){
                $probeMap[$key]=[pscustomobject]@{Name=$s.Name;Value=$v;Delta=$d;Entries=$entries}
                $probeValues.Add($v)
            }
        }
    }
}

$hits=[UiGraphMem850]::ScanPrivateDwords($proc.Id,$probeValues.ToArray(),32)

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_UI_VTABLE_NEIGHBORHOOD_GRAPH_SCAN")
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

$lines.Add("[VTABLE_NEIGHBORHOODS]")
foreach($k in ($probeMap.Keys | Sort-Object)){
    $p=$probeMap[$k]
    $lines.Add(("VTABLE SOURCE={0} VA=0x{1:X8} DELTA={2} EXEC_ENTRIES={3}" -f $p.Name,$p.Value,$p.Delta,$p.Entries))
}
$lines.Add("VALID_VTABLE_PROBES=$($probeValues.Count)")
$lines.Add("")

$lines.Add("[OBJECT_HITS]")
$lines.Add("OBJECT_VPTR_HITS=$($hits.Count)")
$objectAddrs=New-Object System.Collections.Generic.List[uint32]
foreach($h in $hits){
    $key=([uint32]$h.Value).ToString('X8')
    $p=$probeMap[$key]
    $obj=[uint32]$h.Address
    $objectAddrs.Add($obj)
    $lines.Add(("OBJECT ADDR=0x{0:X8} REGION=0x{1:X8} REGION_SIZE=0x{2:X} VTABLE=0x{3:X8} SOURCE={4} DELTA={5} EXEC_ENTRIES={6}" -f $obj,$h.RegionBase,$h.RegionSize,$h.Value,$p.Name,$p.Delta,$p.Entries))
    $b=[UiGraphMem850]::Read($proc.Id,$h.Address,0x300)
    if($b.Length -lt 4){$lines.Add("  READ=FAILED");continue}
    foreach($s in (Get-AsciiRuns $b 5 | Select-Object -First 24)){ $lines.Add("  ASCII $s") }
    $fieldOffsets=@(0x15C,0x160,0x164,0x168,0x170,0x178,0x180,0x188,0x190,0x198,0x1D8,0x1DC,0x1E0,0x1E4,0x1E8,0x1EC,0x1F0,0x1F4,0x204,0x22C)
    foreach($o in $fieldOffsets){
        $v=Read-U32 $b $o; if($null -eq $v){continue}
        $childVt=0; $childAscii=''
        if($v -ge 0x10000 -and $v -lt 0x7FFF0000){
            $cb=[UiGraphMem850]::Read($proc.Id,$v,0x120)
            if($cb.Length -ge 4){
                $cv=[BitConverter]::ToUInt32($cb,0)
                $childVt=Test-Vtable $cv
                $ar=Get-AsciiRuns $cb 5 | Select-Object -First 3
                if($ar){$childAscii=($ar -join '|')}
                $lines.Add(("  CHILD FIELD=+0x{0:X3} PTR=0x{1:X8} CHILD_VTABLE=0x{2:X8} CHILD_VT_ENTRIES={3} ASCII={4}" -f $o,$v,$cv,$childVt,$childAscii))
                continue
            }
        }
        $lines.Add(("  FIELD +0x{0:X3}=0x{1:X8} ({2})" -f $o,$v,$v))
    }
}
$lines.Add("")

if($objectAddrs.Count -gt 0){
    $lines.Add("[OBJECT_PARENT_BACKREFS]")
    $parents=[UiGraphMem850]::ScanPrivateDwords($proc.Id,$objectAddrs.ToArray(),24)
    $lines.Add("PARENT_BACKREFS=$($parents.Count)")
    foreach($h in $parents){
        $lines.Add(("PARENT_REF TARGET_OBJECT=0x{0:X8} REF_ADDR=0x{1:X8} REGION=0x{2:X8}" -f $h.Value,$h.Address,$h.RegionBase))
    }
    $lines.Add("")
}

$lines.Add("STATUS=PASS")
$lines.Add("NOTE=Read-only expanded vtable neighborhood and object-graph evidence only. No gameplay mapping or action bridge is enabled without behavior correlation and restart validation.")
$lines | Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "VALID_VTABLE_PROBES=$($probeValues.Count)"
Write-Host "OBJECT_VPTR_HITS=$($hits.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
