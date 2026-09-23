param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$HeapReportPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_ui_anchor_runtime_heap_scan.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_runtime_ui_xref_scan.txt",
    [int]$TargetPid = 0
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
if (-not (Test-Path -LiteralPath $HeapReportPath)) { throw "Heap report not found: $HeapReportPath" }
$sha = (Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$clientFull = [IO.Path]::GetFullPath($ClientPath)
$proc = $null
$detect = ""
if ($TargetPid -gt 0) {
    try { $proc = Get-Process -Id $TargetPid -ErrorAction Stop; $detect = "TARGET_PID" } catch { }
}
if (-not $proc) {
    foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
        try {
            if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName), $clientFull, [StringComparison]::OrdinalIgnoreCase)) {
                $proc = $p; $detect = "MAINMODULE_PATH"; break
            }
        } catch { }
    }
}
if (-not $proc) {
    $pidLine = Get-Content -LiteralPath $HeapReportPath | Where-Object { $_ -match '^PID=\d+$' } | Select-Object -First 1
    if ($pidLine) {
        $candidatePid = [int]($pidLine.Substring(4))
        try { $proc = Get-Process -Id $candidatePid -ErrorAction Stop; $detect = "HEAP_REPORT_PID" } catch { }
    }
}
if (-not $proc) { throw "Authoritative Lin.bin2 process not found." }

try {
    $module = $proc.MainModule
    $moduleBase = [long]$module.BaseAddress
    $moduleSize = [long]$module.ModuleMemorySize
} catch {
    throw "Cannot read Lin.bin2 module base/size. Run this PowerShell at the same elevation as the game."
}
$moduleEnd = $moduleBase + $moduleSize

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }

# Parse only resource-guided anchors from the previous read-only heap report.
$wanted = @(
    'Action_PetWin','Click_PetWin','HP_Image','Pet_Button0',
    'InventoryItemGrid','InventoryScroll','InvWin','ItemCountLabel','DeleteItem',
    'QuickSummonButton','SummonButton','SummonLevelButton','Spell_Grid','GridEvent',
    'HPGauge','MPGauge','HpGauge_Image','MpGauge_Image'
)
$wantedSet = @{}
foreach ($w in $wanted) { $wantedSet[$w] = $true }
$anchors = @()
$currentName = $null
foreach ($line in Get-Content -LiteralPath $HeapReportPath) {
    if ($line -match '^\[PATTERN (.+) KIND=(ASCII|UTF16LE)\]$') {
        $currentName = $Matches[1]
        if (-not $wantedSet.ContainsKey($currentName)) { $currentName = $null }
        continue
    }
    if ($currentName -and $line -match '^HIT ADDR=0x([0-9A-Fa-f]+) REGION=0x([0-9A-Fa-f]+).*PROTECT=0x([0-9A-Fa-f]+) TYPE=0x([0-9A-Fa-f]+)$') {
        $addr = [Convert]::ToInt64($Matches[1],16)
        $region = [Convert]::ToInt64($Matches[2],16)
        $protect = [Convert]::ToUInt32($Matches[3],16)
        $type = [Convert]::ToUInt32($Matches[4],16)
        $anchors += [pscustomobject]@{ Name=$currentName; Address=$addr; Region=$region; Protect=$protect; Type=$type }
    }
}

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

public sealed class UiRefHit850 {
    public uint Target;
    public long RefAddress;
    public long RegionBase;
    public uint Protect;
    public uint Type;
    public long FunctionAddress;
}

public static class RuntimeUiRefScanner850 {
    const uint PROCESS_VM_READ = 0x0010;
    const uint PROCESS_QUERY_INFORMATION = 0x0400;
    const uint MEM_COMMIT = 0x1000;
    const uint MEM_PRIVATE = 0x20000;
    const uint PAGE_NOACCESS = 0x01;
    const uint PAGE_GUARD = 0x100;
    const uint PAGE_READWRITE = 0x04;
    const uint PAGE_WRITECOPY = 0x08;
    const uint PAGE_EXECUTE = 0x10;
    const uint PAGE_EXECUTE_READ = 0x20;
    const uint PAGE_EXECUTE_READWRITE = 0x40;
    const uint PAGE_EXECUTE_WRITECOPY = 0x80;
    const int Chunk = 512 * 1024;

    [StructLayout(LayoutKind.Sequential)]
    struct MBI { public IntPtr BaseAddress; public IntPtr AllocationBase; public uint AllocationProtect; public UIntPtr RegionSize; public uint State; public uint Protect; public uint Type; }
    [DllImport("kernel32.dll", SetLastError=true)] static extern IntPtr OpenProcess(uint a, bool i, int p);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll", SetLastError=true)] static extern int VirtualQueryEx(IntPtr h, IntPtr a, out MBI m, uint l);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h, IntPtr a, byte[] b, int s, out IntPtr r);

    static bool Readable(MBI m) { return m.State==MEM_COMMIT && (m.Protect&PAGE_GUARD)==0 && (m.Protect&PAGE_NOACCESS)==0; }
    static bool Executable(uint p) { return (p&(PAGE_EXECUTE|PAGE_EXECUTE_READ|PAGE_EXECUTE_READWRITE|PAGE_EXECUTE_WRITECOPY))!=0; }
    static bool Writable(uint p) { return (p&(PAGE_READWRITE|PAGE_WRITECOPY|PAGE_EXECUTE_READWRITE|PAGE_EXECUTE_WRITECOPY))!=0; }

    static long FindFunction(IntPtr h, long refAddress, long regionBase) {
        long start = Math.Max(regionBase, refAddress - 0x700);
        int size = (int)(refAddress - start + 1);
        if (size <= 0) return -1;
        var b = new byte[size]; IntPtr gotPtr;
        if (!ReadProcessMemory(h,new IntPtr(start),b,size,out gotPtr)) return -1;
        int got=(int)Math.Min((long)size,gotPtr.ToInt64());
        for (int i=got-3;i>=0;i--) {
            if (b[i]==0x55 && b[i+1]==0x8B && b[i+2]==0xEC) return start+i;
            if (i+4<got && b[i]==0x8B && b[i+1]==0xFF && b[i+2]==0x55 && b[i+3]==0x8B && b[i+4]==0xEC) return start+i;
        }
        for (int i=got-2;i>=0;i--) {
            if (b[i]==0xCC || b[i]==0xC3) return start+i+1;
            if (i+2<got && b[i]==0xC2) return start+i+3;
        }
        return -1;
    }

    static void ScanRegion(IntPtr h, MBI m, HashSet<uint> targets, bool alignedOnly, bool wantFunction, List<UiRefHit850> output, Dictionary<uint,int> counts) {
        long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64());
        long off=0;
        while(off<rs) {
            long remain=rs-off; int wanted=(int)Math.Min((long)Chunk,remain); if(wanted<=0) break;
            var buf=new byte[wanted]; IntPtr gotPtr;
            bool ok=ReadProcessMemory(h,new IntPtr(rb+off),buf,wanted,out gotPtr);
            int got=ok?(int)Math.Min((long)wanted,gotPtr.ToInt64()):0;
            if(got>=4) {
                int step=alignedOnly?4:1;
                int start=0;
                if(alignedOnly) { long mod=(rb+off)&3; if(mod!=0) start=(int)(4-mod); }
                for(int i=start;i<=got-4;i+=step) {
                    uint v=BitConverter.ToUInt32(buf,i); if(!targets.Contains(v)) continue;
                    int c; counts.TryGetValue(v,out c); if(c>=128) continue;
                    long ra=rb+off+i;
                    output.Add(new UiRefHit850 { Target=v, RefAddress=ra, RegionBase=rb, Protect=m.Protect, Type=m.Type, FunctionAddress=wantFunction?FindFunction(h,ra,rb):-1 });
                    counts[v]=c+1;
                }
            }
            if(wanted==remain) break;
            off += Math.Max(1,wanted-3);
        }
    }

    public static List<UiRefHit850> ScanModuleCode(int pid, long moduleBase, long moduleSize, uint[] targetValues) {
        var result=new List<UiRefHit850>(); var set=new HashSet<uint>(targetValues); var counts=new Dictionary<uint,int>();
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid); if(h==IntPtr.Zero) return result;
        try {
            long end=moduleBase+moduleSize; long a=moduleBase; uint sz=(uint)Marshal.SizeOf(typeof(MBI));
            while(a<end) { MBI m; if(VirtualQueryEx(h,new IntPtr(a),out m,sz)==0) break; long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64()); if(rs<=0) break;
                if(Readable(m) && Executable(m.Protect) && rb<end && rb+rs>moduleBase) ScanRegion(h,m,set,false,true,result,counts);
                long n=rb+rs; if(n<=a) break; a=n;
            }
        } finally { CloseHandle(h); }
        return result;
    }

    public static List<UiRefHit850> ScanWritablePrivateBackrefs(int pid, uint[] targetValues) {
        var result=new List<UiRefHit850>(); var set=new HashSet<uint>(targetValues); var counts=new Dictionary<uint,int>();
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid); if(h==IntPtr.Zero) return result;
        try {
            long a=0x10000; const long end=0x7FFF0000; uint sz=(uint)Marshal.SizeOf(typeof(MBI));
            while(a<end) { MBI m; if(VirtualQueryEx(h,new IntPtr(a),out m,sz)==0) break; long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64()); if(rs<=0) break;
                if(Readable(m) && Writable(m.Protect) && m.Type==MEM_PRIVATE) ScanRegion(h,m,set,true,false,result,counts);
                long n=rb+rs; if(n<=a) break; a=n;
            }
        } finally { CloseHandle(h); }
        return result;
    }
}
"@

# Module-resident strings are the strongest bridge: static file had zero hits, runtime image now contains them.
$moduleAnchors = $anchors | Where-Object { $_.Address -ge $moduleBase -and $_.Address -lt $moduleEnd } | Sort-Object Name,Address -Unique
$moduleValues = @($moduleAnchors | ForEach-Object { [uint32]$_.Address })
$codeRefs = if ($moduleValues.Count -gt 0) { [RuntimeUiRefScanner850]::ScanModuleCode($proc.Id,$moduleBase,$moduleSize,[uint32[]]$moduleValues) } else { @() }

# HP/MP control strings exist only in decoded heap/resource buffers in this run. Search writable-private pointer backrefs to those exact string addresses.
$hpNames = @('HPGauge','MPGauge','HpGauge_Image','MpGauge_Image')
$hpHeapAnchors = $anchors | Where-Object { $hpNames -contains $_.Name -and ($_.Address -lt $moduleBase -or $_.Address -ge $moduleEnd) } | Sort-Object Name,Address -Unique
$hpValues = @($hpHeapAnchors | ForEach-Object { [uint32]$_.Address })
$hpBackrefs = if ($hpValues.Count -gt 0) { [RuntimeUiRefScanner850]::ScanWritablePrivateBackrefs($proc.Id,[uint32[]]$hpValues) } else { @() }

$nameByAddress = @{}
foreach ($a in $anchors) { $nameByAddress[[uint32]$a.Address] = $a.Name }

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_RUNTIME_UI_XREF_AND_HPMP_BACKREF_SCAN")
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_DETECTION=$detect")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add("CLIENT=$clientFull")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $moduleBase))
$lines.Add(("MODULE_SIZE=0x{0:X}" -f $moduleSize))
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("")

$lines.Add("[RUNTIME_MODULE_ANCHORS]")
$lines.Add("ANCHORS=$($moduleAnchors.Count)")
foreach ($a in $moduleAnchors) { $lines.Add(("ANCHOR NAME={0} VA=0x{1:X8} RVA=0x{2:X8}" -f $a.Name,$a.Address,($a.Address-$moduleBase))) }
$lines.Add("")

$lines.Add("[MODULE_CODE_XREFS]")
$lines.Add("XREFS=$($codeRefs.Count)")
foreach ($r in $codeRefs | Sort-Object Target,RefAddress) {
    $nm = $nameByAddress[[uint32]$r.Target]
    $func = if ($r.FunctionAddress -ge 0) { '0x{0:X8}' -f $r.FunctionAddress } else { 'NOT_FOUND' }
    $funcRva = if ($r.FunctionAddress -ge $moduleBase -and $r.FunctionAddress -lt $moduleEnd) { '0x{0:X8}' -f ($r.FunctionAddress-$moduleBase) } else { 'NA' }
    $lines.Add(("XREF NAME={0} TARGET=0x{1:X8} REF=0x{2:X8} REF_RVA=0x{3:X8} FUNC={4} FUNC_RVA={5}" -f $nm,$r.Target,$r.RefAddress,($r.RefAddress-$moduleBase),$func,$funcRva))
}
$lines.Add("")

$lines.Add("[HPMP_HEAP_ANCHORS]")
$lines.Add("ANCHORS=$($hpHeapAnchors.Count)")
foreach ($a in $hpHeapAnchors) { $lines.Add(("ANCHOR NAME={0} VA=0x{1:X8} REGION=0x{2:X8}" -f $a.Name,$a.Address,$a.Region)) }
$lines.Add("")
$lines.Add("[HPMP_WRITABLE_PRIVATE_BACKREFS]")
$lines.Add("BACKREFS=$($hpBackrefs.Count)")
foreach ($r in $hpBackrefs | Sort-Object Target,RefAddress) {
    $nm = $nameByAddress[[uint32]$r.Target]
    $lines.Add(("BACKREF NAME={0} TARGET=0x{1:X8} REF=0x{2:X8} REGION=0x{3:X8} PROTECT=0x{4:X} TYPE=0x{5:X}" -f $nm,$r.Target,$r.RefAddress,$r.RegionBase,$r.Protect,$r.Type))
}
$lines.Add("")
$lines.Add("STATUS=PASS")
$lines.Add("NOTE=Runtime-unpacked string xrefs and heap pointer backrefs are candidate evidence only; no runtime map is accepted without behavior correlation and restart validation.")
$lines | Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "MODULE_ANCHORS=$($moduleAnchors.Count)"
Write-Host "MODULE_XREFS=$($codeRefs.Count)"
Write-Host "HPMP_BACKREFS=$($hpBackrefs.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
