param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$AuditReport = "I:\8.50c客服端\auto_runtime_audit_report.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_ui_vtable_object_scan.txt"
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
        $pidLine = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue | Where-Object { $_ -match '^PID=\d+$' } | Select-Object -First 1
        $authLine = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue | Where-Object { $_ -eq 'CLIENT_AUTHORITY=1' } | Select-Object -First 1
        $hashLine = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue | Where-Object { $_ -match '^CLIENT_SHA256=' } | Select-Object -First 1
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

public sealed class VtHit850 {
    public long Address;
    public long RegionBase;
    public long RegionSize;
    public uint Value;
}

public static class VtMem850 {
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
            int n=(int)Math.Min((long)size,rp.ToInt64()); if(n==size) return b;
            byte[] o=new byte[n]; Array.Copy(b,o,n); return o;
        } finally { CloseHandle(h); }
    }

    public static List<VtHit850> ScanPrivateDwords(int pid,uint[] values,int maxHitsPerValue) {
        var outp=new List<VtHit850>();
        var counts=new Dictionary<uint,int>();
        var wanted=new HashSet<uint>(values);
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
                            outp.Add(new VtHit850{Address=rb+off+i,RegionBase=rb,RegionSize=rs,Value=v});
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

$mem = [VtMem850]::Read($proc.Id,$base,$size)
if ($mem.Length -lt 0x1000) { throw "Failed to read runtime module image." }

function Get-ExecRanges([byte[]]$data) {
    $ranges = New-Object System.Collections.Generic.List[object]
    if ($data.Length -lt 0x100) { return $ranges }
    $pe = [BitConverter]::ToInt32($data,0x3C)
    if ($pe -lt 0 -or $pe+0x18 -ge $data.Length) { return $ranges }
    $sections = [BitConverter]::ToUInt16($data,$pe+6)
    $optSize = [BitConverter]::ToUInt16($data,$pe+20)
    $sec = $pe + 24 + $optSize
    for ($i=0; $i -lt $sections; $i++) {
        $o = $sec + ($i*40)
        if ($o+40 -gt $data.Length) { break }
        $virtualSize = [BitConverter]::ToUInt32($data,$o+8)
        $virtualAddress = [BitConverter]::ToUInt32($data,$o+12)
        $chars = [BitConverter]::ToUInt32($data,$o+36)
        if (($chars -band 0x20000000) -ne 0) {
            $ranges.Add([pscustomobject]@{ Start=[int]$virtualAddress; End=[int]([Math]::Min([long]$data.Length,[long]$virtualAddress+[Math]::Max([long]$virtualSize,1))); Chars=$chars })
        }
    }
    return $ranges
}

$execRanges = Get-ExecRanges $mem
function Test-ExecRva([uint32]$rva) {
    foreach ($r in $execRanges) { if ($rva -ge $r.Start -and $rva -lt $r.End) { return $true } }
    return $false
}
function Find-DwordHits([byte[]]$data,[uint32]$value) {
    $p=[BitConverter]::GetBytes($value)
    $hits=New-Object System.Collections.Generic.List[int]
    for($i=0;$i -le $data.Length-4;$i++) {
        if($data[$i]-eq$p[0] -and $data[$i+1]-eq$p[1] -and $data[$i+2]-eq$p[2] -and $data[$i+3]-eq$p[3]) { $hits.Add($i) }
    }
    return $hits
}
function Get-VtableCandidate([byte[]]$data,[int]$slot,[long]$moduleBase) {
    $start = $slot - ($slot % 4)
    $cur = $start
    $back = 0
    while ($cur-4 -ge 0 -and $back -lt 32) {
        $v=[BitConverter]::ToUInt32($data,$cur-4)
        if ($v -lt $moduleBase -or $v -ge ($moduleBase+$data.Length)) { break }
        $r=[uint32]($v-$moduleBase)
        if (-not (Test-ExecRva $r)) { break }
        $cur -= 4; $back++
    }
    $count=0
    $p=$cur
    while($p+4 -le $data.Length -and $count -lt 96) {
        $v=[BitConverter]::ToUInt32($data,$p)
        if ($v -lt $moduleBase -or $v -ge ($moduleBase+$data.Length)) { break }
        $r=[uint32]($v-$moduleBase)
        if (-not (Test-ExecRva $r)) { break }
        $count++; $p+=4
    }
    [pscustomobject]@{ StartRva=$cur; Entries=$count; ContainsSlot=(($slot-$cur)/4) }
}
function Read-U32([byte[]]$b,[int]$o) {
    if($o -lt 0 -or $o+4 -gt $b.Length){ return $null }
    return [BitConverter]::ToUInt32($b,$o)
}

$targets=@(
    [pscustomobject]@{Name='INVENTORY_GRID_INIT';Rva=0x00707870},
    [pscustomobject]@{Name='INVENTORY_COUNT_LABEL';Rva=0x007022A0},
    [pscustomobject]@{Name='PET_ACTION';Rva=0x004BD8F0},
    [pscustomobject]@{Name='PET_HP_IMAGE';Rva=0x004BD270},
    [pscustomobject]@{Name='SUMMON_UI';Rva=0x0095BD40},
    [pscustomobject]@{Name='SPELL_GRID';Rva=0x00B9B770},
    [pscustomobject]@{Name='DELETE_UI_RESOURCE';Rva=0x00703FF0}
)

$anchorVAs = [ordered]@{
    InventoryItemGrid = [uint32]0x012DDBA8
    InvWin            = [uint32]0x012DDBBC
    ItemCountLabel    = [uint32]0x012DDAF4
    Action_PetWin     = [uint32]0x012CA73C
    Click_PetWin      = [uint32]0x012CA72C
    HP_Image          = [uint32]0x012CA7CC
    SummonLevelButton = [uint32]0x013007FC
    SummonButton      = [uint32]0x01300810
    QuickSummonButton = [uint32]0x01300820
    Spell_Grid        = [uint32]0x01329D70
}

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_UI_VTABLE_OBJECT_SCAN")
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

$lines.Add("[EXEC_RANGES]")
foreach($r in $execRanges){ $lines.Add(("RVA=0x{0:X8}-0x{1:X8}" -f $r.Start,$r.End)) }
$lines.Add("")

$candidates = New-Object System.Collections.Generic.List[object]
$seen = New-Object 'System.Collections.Generic.HashSet[uint32]'
$lines.Add("[FUNCTION_POINTER_TABLE_CANDIDATES]")
foreach($t in $targets){
    $funcVa=[uint32]($base+$t.Rva)
    $hits=Find-DwordHits $mem $funcVa
    $lines.Add("FUNCTION NAME=$($t.Name) RVA=0x$('{0:X8}' -f $t.Rva) POINTER_SLOTS=$($hits.Count)")
    foreach($slot in ($hits | Select-Object -First 32)){
        $vt=Get-VtableCandidate $mem $slot $base
        $tableVa=[uint32]($base+$vt.StartRva)
        $lines.Add(("  SLOT_RVA=0x{0:X8} TABLE_RVA=0x{1:X8} TABLE_VA=0x{2:X8} ENTRIES={3} INDEX={4}" -f $slot,$vt.StartRva,$tableVa,$vt.Entries,$vt.ContainsSlot))
        if($vt.Entries -ge 3 -and $seen.Add($tableVa)){
            $candidates.Add([pscustomobject]@{ Name=$t.Name; TableRva=[uint32]$vt.StartRva; TableVa=$tableVa; Entries=$vt.Entries; Index=$vt.ContainsSlot })
        }
    }
}
# Strong constructor-derived seed seen in PET_ACTION caller: C7 00 E8 A8 2C 01.
$petSeed=[uint32]0x012CA8E8
if($petSeed -ge $base -and $petSeed -lt ($base+$size) -and $seen.Add($petSeed)){
    $candidates.Add([pscustomobject]@{Name='PET_CALLER_VTABLE_SEED';TableRva=[uint32]($petSeed-$base);TableVa=$petSeed;Entries=0;Index=0})
    $lines.Add(("SEED NAME=PET_CALLER_VTABLE_SEED TABLE_VA=0x{0:X8} TABLE_RVA=0x{1:X8}" -f $petSeed,($petSeed-$base)))
}
$lines.Add("")

$vtValues = @($candidates | ForEach-Object { [uint32]$_.TableVa } | Sort-Object -Unique)
$heapHits = if($vtValues.Count -gt 0){ [VtMem850]::ScanPrivateDwords($proc.Id,[uint32[]]$vtValues,64) } else { @() }
$lines.Add("[WRITABLE_PRIVATE_VTABLE_OBJECTS]")
$lines.Add("VTABLE_CANDIDATES=$($vtValues.Count)")
$lines.Add("OBJECT_VPTR_HITS=$($heapHits.Count)")

$interestingOffsets = @(0x15C,0x160,0x164,0x168,0x170,0x178,0x180,0x188,0x190,0x198,0x1D8,0x1DC,0x1E0,0x1E4,0x1E8,0x1EC,0x1F0,0x1F4,0x204,0x22C)
$objectCount=0
foreach($h in ($heapHits | Select-Object -First 160)){
    $cand=$candidates | Where-Object { $_.TableVa -eq [uint32]$h.Value } | Select-Object -First 1
    $buf=[VtMem850]::Read($proc.Id,$h.Address,0x280)
    $lines.Add(("OBJECT ADDR=0x{0:X8} REGION=0x{1:X8} VTABLE=0x{2:X8} SOURCE={3}" -f $h.Address,$h.RegionBase,$h.Value,$cand.Name))
    foreach($off in $interestingOffsets){
        $v=Read-U32 $buf $off
        if($null -ne $v){ $lines.Add(("  FIELD +0x{0:X3}=0x{1:X8} ({2})" -f $off,$v,$v)) }
    }
    foreach($kv in $anchorVAs.GetEnumerator()){
        $p=[BitConverter]::GetBytes([uint32]$kv.Value)
        for($i=0;$i -le $buf.Length-4;$i+=4){
            if($buf[$i]-eq$p[0] -and $buf[$i+1]-eq$p[1] -and $buf[$i+2]-eq$p[2] -and $buf[$i+3]-eq$p[3]){
                $lines.Add(("  ANCHOR_PTR +0x{0:X3} NAME={1} VA=0x{2:X8}" -f $i,$kv.Key,$kv.Value))
            }
        }
    }
    $objectCount++
    if($objectCount -ge 160){ break }
}
$lines.Add("")

$lines.Add("STATUS=PASS")
$lines.Add("NOTE=Read-only vtable/object evidence only. A vtable match is not a gameplay mapping; no HP/MP, inventory, delete, pet, summon, skill, item-use or packet bridge is enabled by this report alone.")
$lines | Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "VTABLE_CANDIDATES=$($vtValues.Count)"
Write-Host "OBJECT_VPTR_HITS=$($heapHits.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
