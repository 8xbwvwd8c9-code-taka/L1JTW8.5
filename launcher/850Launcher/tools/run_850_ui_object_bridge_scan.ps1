param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$AuditReport = "I:\8.50c客服端\auto_runtime_audit_report.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_ui_object_bridge_scan.txt"
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

public sealed class BridgePtrHit850 {
    public string Name = "";
    public long Address;
    public long RegionBase;
    public long RegionSize;
}

public static class BridgeMem850 {
    const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
    const uint MEM_COMMIT=0x1000, MEM_PRIVATE=0x20000;
    const uint PAGE_NOACCESS=0x01, PAGE_GUARD=0x100;
    const uint PAGE_READWRITE=0x04, PAGE_WRITECOPY=0x08, PAGE_EXECUTE_READWRITE=0x40, PAGE_EXECUTE_WRITECOPY=0x80;
    const int ChunkSize=512*1024;
    const int MaxHitsPerTarget=128;

    [StructLayout(LayoutKind.Sequential)]
    struct MEMORY_BASIC_INFORMATION {
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
    [DllImport("kernel32.dll",SetLastError=true)] static extern int VirtualQueryEx(IntPtr h,IntPtr a,out MEMORY_BASIC_INFORMATION m,uint l);

    static bool Writable(uint p) {
        return (p & PAGE_READWRITE)!=0 || (p & PAGE_WRITECOPY)!=0 || (p & PAGE_EXECUTE_READWRITE)!=0 || (p & PAGE_EXECUTE_WRITECOPY)!=0;
    }

    public static byte[] Read(int pid,long address,int size) {
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            byte[] b=new byte[size]; IntPtr rp;
            if(!ReadProcessMemory(h,new IntPtr(address),b,size,out rp)) throw new Exception("ReadProcessMemory failed Win32="+Marshal.GetLastWin32Error());
            int n=(int)Math.Min((long)size,rp.ToInt64()); if(n==size) return b;
            byte[] o=new byte[n]; Array.Copy(b,o,n); return o;
        } finally { CloseHandle(h); }
    }

    public static List<BridgePtrHit850> ScanWritablePrivatePointers(int pid,string[] names,uint[] values) {
        var output=new List<BridgePtrHit850>();
        var counts=new int[values.Length];
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            long address=0x10000; const long maxAddress=0x7FFF0000;
            uint mbiSize=(uint)Marshal.SizeOf(typeof(MEMORY_BASIC_INFORMATION));
            while(address<maxAddress) {
                MEMORY_BASIC_INFORMATION mbi;
                if(VirtualQueryEx(h,new IntPtr(address),out mbi,mbiSize)==0) break;
                long rb=mbi.BaseAddress.ToInt64(); long rs=unchecked((long)mbi.RegionSize.ToUInt64());
                if(rs<=0) break;
                bool okRegion=mbi.State==MEM_COMMIT && mbi.Type==MEM_PRIVATE && (mbi.Protect&PAGE_GUARD)==0 && (mbi.Protect&PAGE_NOACCESS)==0 && Writable(mbi.Protect);
                if(okRegion) {
                    long off=0;
                    while(off<rs) {
                        long rem=rs-off; int wanted=(int)Math.Min((long)ChunkSize,rem); if(wanted<=0) break;
                        byte[] b=new byte[wanted]; IntPtr rp;
                        bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,wanted,out rp);
                        int got=ok?(int)Math.Min((long)wanted,rp.ToInt64()):0;
                        if(got>=4) {
                            for(int i=0;i<=got-4;i++) {
                                uint v=BitConverter.ToUInt32(b,i);
                                for(int t=0;t<values.Length;t++) {
                                    if(counts[t]>=MaxHitsPerTarget || v!=values[t]) continue;
                                    output.Add(new BridgePtrHit850 { Name=names[t], Address=rb+off+i, RegionBase=rb, RegionSize=rs });
                                    counts[t]++;
                                }
                            }
                        }
                        if(wanted==rem) break;
                        off += wanted-3;
                    }
                }
                long next=rb+rs; if(next<=address) break; address=next;
            }
            return output;
        } finally { CloseHandle(h); }
    }
}
"@

$mem = [BridgeMem850]::Read($proc.Id,$base,$size)

function Find-DwordHits([byte[]]$data,[uint32]$value) {
    $p=[BitConverter]::GetBytes($value)
    $hits=New-Object System.Collections.Generic.List[int]
    for($i=0;$i -le $data.Length-4;$i++) {
        if($data[$i]-eq $p[0] -and $data[$i+1]-eq $p[1] -and $data[$i+2]-eq $p[2] -and $data[$i+3]-eq $p[3]) { $hits.Add($i) }
    }
    return $hits
}

function Find-FunctionStart([byte[]]$data,[int]$index,[int]$maxBack=1280) {
    $min=[Math]::Max(0,$index-$maxBack)
    for($i=$index;$i -ge $min;$i--) {
        if($i+2 -lt $data.Length -and $data[$i]-eq 0x55 -and $data[$i+1]-eq 0x8B -and $data[$i+2]-eq 0xEC) { return $i }
        if($i+4 -lt $data.Length -and $data[$i]-eq 0x8B -and $data[$i+1]-eq 0xFF -and $data[$i+2]-eq 0x55 -and $data[$i+3]-eq 0x8B -and $data[$i+4]-eq 0xEC) { return $i }
    }
    for($i=$index-1;$i -ge $min;$i--) {
        if($data[$i]-eq 0xCC -or $data[$i]-eq 0xC3) { return $i+1 }
        if($data[$i]-eq 0xC2 -and $i+2 -lt $data.Length) { return $i+3 }
    }
    return -1
}

function Find-InboundCalls([byte[]]$data,[int]$target) {
    $rows=New-Object System.Collections.Generic.List[object]
    for($i=0;$i -le $data.Length-5;$i++) {
        if($data[$i]-ne 0xE8) { continue }
        $rel=[BitConverter]::ToInt32($data,$i+1)
        $dest=$i+5+$rel
        if($dest -ne $target) { continue }
        $caller=Find-FunctionStart $data $i
        $rows.Add([pscustomobject]@{CallRva=$i;CallerRva=$caller})
        if($rows.Count -ge 64) { break }
    }
    return $rows
}

function Hex-Bytes([byte[]]$data,[int]$start,[int]$count) {
    $end=[Math]::Min($data.Length,$start+$count)
    if($start -lt 0 -or $start -ge $end) { return '' }
    $sb=New-Object Text.StringBuilder
    for($i=$start;$i -lt $end;$i++) { if($sb.Length -gt 0){[void]$sb.Append(' ')}; [void]$sb.Append($data[$i].ToString('X2')) }
    return $sb.ToString()
}

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_UI_OBJECT_BRIDGE_AND_RESOURCE_CALLER_SCAN")
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

# First, follow resource/control strings that are definitely inside the runtime module.
$stringTargets=@(
    [pscustomobject]@{Name='InventoryItemGrid';Rva=0x00EDDBA8},
    [pscustomobject]@{Name='InvWin';Rva=0x00EDDBBC},
    [pscustomobject]@{Name='ItemCountLabel';Rva=0x00EDDAF4},
    [pscustomobject]@{Name='Action_PetWin';Rva=0x00ECA73C},
    [pscustomobject]@{Name='Click_PetWin';Rva=0x00ECA72C},
    [pscustomobject]@{Name='HP_Image';Rva=0x00ECA7CC},
    [pscustomobject]@{Name='SummonLevelButton';Rva=0x00F007FC},
    [pscustomobject]@{Name='SummonButton';Rva=0x00F00810},
    [pscustomobject]@{Name='QuickSummonButton';Rva=0x00F00820},
    [pscustomobject]@{Name='Spell_Grid';Rva=0x00F29D70}
)
$names=@(); $values=@()
foreach($t in $stringTargets){ $names += $t.Name; $values += [uint32]($base + [long]$t.Rva) }
$ptrHits=[BridgeMem850]::ScanWritablePrivatePointers($proc.Id,[string[]]$names,[uint32[]]$values)
$lines.Add("[MODULE_STRING_WRITABLE_PRIVATE_BACKREFS]")
$lines.Add("BACKREFS=$($ptrHits.Count)")
foreach($g in ($ptrHits | Group-Object Name | Sort-Object Name)) {
    $lines.Add("TARGET=$($g.Name) HITS=$($g.Count)")
    foreach($h in ($g.Group | Select-Object -First 32)) {
        $lines.Add(("PTR NAME={0} ADDR=0x{1:X8} REGION=0x{2:X8} REGION_SIZE=0x{3:X}" -f $h.Name,$h.Address,$h.RegionBase,$h.RegionSize))
    }
}
$lines.Add("")
$lines.Add("[BACKREF_PAGE_COHERENCE]")
$pageRows=$ptrHits | ForEach-Object { [pscustomobject]@{Page=($_.Address -band (-bnot 0xFFF));Name=$_.Name;Address=$_.Address} }
foreach($pg in ($pageRows | Group-Object Page | Sort-Object Count -Descending | Select-Object -First 40)) {
    $unique=@($pg.Group | ForEach-Object {$_.Name} | Sort-Object -Unique)
    if($unique.Count -lt 2) { continue }
    $lines.Add(("PAGE=0x{0:X8} HITS={1} TARGETS={2}" -f [long]$pg.Name,$pg.Count,($unique -join ',')))
}
$lines.Add("")

# Then analyze individual resource IDs. Pair proximity was too strict for HP/MP/Pet/Inventory,
# so preserve each reference and look for common parent callers instead.
$resourcePairs=@(
    [pscustomobject]@{Name='HPMP_GAUGE_PNG';A=10899;B=10900},
    [pscustomobject]@{Name='PET_HP_PNG';A=3200;B=3201},
    [pscustomobject]@{Name='INVENTORY_RESOURCE';A=3212;B=3213}
)
foreach($pair in $resourcePairs) {
    $lines.Add("[RESOURCE_PAIR_CALLERS $($pair.Name) A=$($pair.A) B=$($pair.B)]")
    $sideFunctions=@{}
    foreach($side in @('A','B')) {
        $value=if($side -eq 'A'){$pair.A}else{$pair.B}
        $hits=Find-DwordHits $mem ([uint32]$value)
        $funcSet=New-Object 'System.Collections.Generic.HashSet[int]'
        foreach($h in $hits) { $f=Find-FunctionStart $mem $h; if($f -ge 0){[void]$funcSet.Add($f)} }
        $sideFunctions[$side]=@($funcSet)
        $lines.Add("SIDE=$side VALUE=$value RAW_HITS=$($hits.Count) UNIQUE_FUNCTIONS=$($funcSet.Count)")
        foreach($f in (@($funcSet) | Sort-Object | Select-Object -First 32)) {
            $callers=Find-InboundCalls $mem $f
            $lines.Add(("RESOURCE_FUNC SIDE={0} RVA=0x{1:X8} INBOUND_CALLS={2} HEX={3}" -f $side,$f,$callers.Count,(Hex-Bytes $mem $f 96)))
            foreach($c in ($callers | Select-Object -First 12)) {
                $lines.Add(("  INBOUND CALL_RVA=0x{0:X8} CALLER_RVA={1}" -f $c.CallRva,$(if($c.CallerRva -ge 0){'0x{0:X8}' -f $c.CallerRva}else{'NOT_FOUND'})))
            }
        }
    }

    $aCallers=New-Object 'System.Collections.Generic.HashSet[int]'
    foreach($f in $sideFunctions['A']) { foreach($c in (Find-InboundCalls $mem $f)) { if($c.CallerRva -ge 0){[void]$aCallers.Add($c.CallerRva)} } }
    $bCallers=New-Object 'System.Collections.Generic.HashSet[int]'
    foreach($f in $sideFunctions['B']) { foreach($c in (Find-InboundCalls $mem $f)) { if($c.CallerRva -ge 0){[void]$bCallers.Add($c.CallerRva)} } }
    $common=@()
    foreach($c in $aCallers) { if($bCallers.Contains($c)) { $common += $c } }
    $lines.Add("COMMON_PARENT_CALLERS=$($common.Count)")
    foreach($c in ($common | Sort-Object | Select-Object -First 32)) {
        $lines.Add(("COMMON_CALLER_RVA=0x{0:X8} HEX={1}" -f $c,(Hex-Bytes $mem $c 128)))
    }
    $lines.Add("")
}

# Finally find inbound callers of already-proven UI anchor functions.
$focus=@(
    [pscustomobject]@{Name='INVENTORY_GRID_INIT';Rva=0x00707870},
    [pscustomobject]@{Name='INVENTORY_COUNT_LABEL';Rva=0x007022A0},
    [pscustomobject]@{Name='PET_ACTION';Rva=0x004BD8F0},
    [pscustomobject]@{Name='PET_HP_IMAGE';Rva=0x004BD270},
    [pscustomobject]@{Name='SUMMON_UI';Rva=0x0095BD40},
    [pscustomobject]@{Name='SPELL_GRID';Rva=0x00B9B770},
    [pscustomobject]@{Name='DELETE_UI_RESOURCE';Rva=0x00703FF0}
)
$lines.Add("[FOCUSED_FUNCTION_INBOUND_CALLERS]")
foreach($f in $focus) {
    $callers=Find-InboundCalls $mem ([int]$f.Rva)
    $lines.Add(("FUNCTION NAME={0} RVA=0x{1:X8} INBOUND_CALLS={2}" -f $f.Name,$f.Rva,$callers.Count))
    foreach($c in ($callers | Select-Object -First 32)) {
        $lines.Add(("CALL CALL_RVA=0x{0:X8} CALLER_RVA={1}" -f $c.CallRva,$(if($c.CallerRva -ge 0){'0x{0:X8}' -f $c.CallerRva}else{'NOT_FOUND'})))
        if($c.CallerRva -ge 0) { $lines.Add(("  CALLER_HEX={0}" -f (Hex-Bytes $mem $c.CallerRva 128))) }
    }
}
$lines.Add("")
$lines.Add("STATUS=PASS")
$lines.Add("NOTE=Read-only pointer/caller correlation only. Backrefs and common callers are candidates; no HP/MP, inventory, delete, item-use, pet, summon or skill bridge is enabled by this report alone.")
$lines | Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "STRING_BACKREFS=$($ptrHits.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
