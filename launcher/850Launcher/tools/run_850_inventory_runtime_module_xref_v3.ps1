param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_runtime_module_xref_v3.txt",
    [int]$CorrelationRadius = 0x600,
    [int]$MaxHitsPerNeedle = 512
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$Vtables = [ordered]@{
    GRID   = 0x00EDDE38
    ROOT   = 0x00EDE2F8
    INVWIN = 0x00EDE180
}
$InterestingOffsets = @(
    0x0EC,0x0F4,0x15C,0x168,
    0x1E0,0x1E4,0x1E8,0x1EC,0x1F0,0x1F4,0x1F8,0x1FC,
    0x200,0x204,0x208,0x20C,0x210,0x214,0x218,0x21C,
    0x220,0x224,0x228,0x22C,0x230
)

if(-not (Test-Path -LiteralPath $ClientPath)){ throw "Client not found: $ClientPath" }
$fullClient=[IO.Path]::GetFullPath($ClientPath)
$sha=(Get-FileHash -LiteralPath $fullClient -Algorithm SHA256).Hash.ToUpperInvariant()
if($sha -ne $ExpectedSha256){ throw "Client authority mismatch: $sha" }

function Resolve-AuthoritativeProcess([string]$expected){
    foreach($p in Get-Process -ErrorAction SilentlyContinue){
        try{
            if($p.HasExited){ continue }
            if($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$expected,[StringComparison]::OrdinalIgnoreCase)){
                return $p
            }
        }catch{}
    }
    throw "Running authoritative Lin.bin2 process not found. Start/login client first."
}

$proc=Resolve-AuthoritativeProcess $fullClient
$module=$proc.MainModule
if(-not $module){ throw 'MainModule unavailable; run PowerShell elevated if required.' }
$base=[long]$module.BaseAddress
$size=[long]$module.ModuleMemorySize
$limit=$base+$size

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

public sealed class RuntimeExecRegion850 {
    public long Address;
    public long Size;
    public uint Protect;
    public uint Type;
    public byte[] Bytes;
}

public static class RuntimeModuleMem850 {
    const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
    const uint MEM_COMMIT=0x1000, MEM_IMAGE=0x1000000;
    const uint PAGE_GUARD=0x100, PAGE_NOACCESS=0x01;

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

    static bool IsExec(uint p){
        if((p&PAGE_GUARD)!=0 || (p&PAGE_NOACCESS)!=0) return false;
        uint low=p&0xFF;
        return low==0x10 || low==0x20 || low==0x40 || low==0x80;
    }

    public static List<RuntimeExecRegion850> ReadExecImageRegions(int pid,long start,long end){
        var outp=new List<RuntimeExecRegion850>();
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try{
            uint ms=(uint)Marshal.SizeOf(typeof(MBI));
            long cur=start;
            while(cur<end){
                MBI m;
                if(VirtualQueryEx(h,new IntPtr(cur),out m,ms)==0) break;
                long rb=m.BaseAddress.ToInt64();
                long rs=unchecked((long)m.RegionSize.ToUInt64());
                if(rs<=0) break;
                long a=Math.Max(start,rb), z=Math.Min(end,rb+rs);
                if(z>a && m.State==MEM_COMMIT && m.Type==MEM_IMAGE && IsExec(m.Protect)){
                    long remain=z-a;
                    const int Chunk=256*1024;
                    long off=0;
                    while(off<remain){
                        int want=(int)Math.Min((long)Chunk,remain-off);
                        byte[] b=new byte[want]; IntPtr gotp;
                        bool ok=ReadProcessMemory(h,new IntPtr(a+off),b,want,out gotp);
                        int got=ok?(int)Math.Min((long)want,gotp.ToInt64()):0;
                        if(got>0){
                            if(got!=b.Length){ byte[] t=new byte[got]; Array.Copy(b,t,got); b=t; }
                            outp.Add(new RuntimeExecRegion850{Address=a+off,Size=got,Protect=m.Protect,Type=m.Type,Bytes=b});
                        }
                        off+=want;
                    }
                }
                long next=rb+rs;
                if(next<=cur) break;
                cur=next;
            }
            return outp;
        }finally{ CloseHandle(h); }
    }
}
"@

function Hex([byte[]]$b,[int]$start,[int]$count){
    if($start -lt 0){$start=0}
    if($start+$count -gt $b.Length){$count=$b.Length-$start}
    if($count -le 0){return ''}
    return (($b[$start..($start+$count-1)] | ForEach-Object { $_.ToString('X2') }) -join ' ')
}

function Find-Needle([byte[]]$hay,[byte[]]$needle,[int]$maxHits){
    $hits=New-Object System.Collections.Generic.List[int]
    if($needle.Length -eq 0 -or $hay.Length -lt $needle.Length){ return $hits }
    for($i=0;$i -le $hay.Length-$needle.Length;$i++){
        $ok=$true
        for($j=0;$j -lt $needle.Length;$j++){
            if($hay[$i+$j] -ne $needle[$j]){ $ok=$false; break }
        }
        if($ok){ $hits.Add($i); if($hits.Count -ge $maxHits){ break } }
    }
    return $hits
}

function Find-NearbyCalls([object[]]$regions,[long]$center,[int]$radius){
    $out=New-Object System.Collections.Generic.List[object]
    $lo=$center-$radius; $hi=$center+$radius
    foreach($r in $regions){
        $ra=[long]$r.Address; $rz=$ra+[long]$r.Size
        if($rz -le $lo -or $ra -ge $hi){ continue }
        $b=$r.Bytes
        for($i=0;$i+5 -le $b.Length;$i++){
            $addr=$ra+$i
            if($addr -lt $lo -or $addr -gt $hi){ continue }
            if($b[$i] -eq 0xE8){
                $rel=[BitConverter]::ToInt32($b,$i+1)
                $target=$addr+5+$rel
                if($target -ge $base -and $target -lt $limit){
                    $out.Add([pscustomobject]@{At=$addr;Target=$target;AtRva=$addr-$base;TargetRva=$target-$base})
                }
            }
        }
    }
    return $out
}

$regions=[RuntimeModuleMem850]::ReadExecImageRegions($proc.Id,$base,$limit)
if($regions.Count -eq 0){ throw 'No executable MEM_IMAGE regions were readable inside Lin.bin2 module.' }

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_INVENTORY_RUNTIME_MODULE_XREF_V3')
$lines.Add("CLIENT=$fullClient")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $base))
$lines.Add(("MODULE_SIZE=0x{0:X}" -f $size))
$lines.Add('RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE')
$lines.Add('SCAN_SCOPE=LIN.BIN2_EXECUTABLE_MEM_IMAGE_ONLY')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[EXEC_IMAGE_REGIONS]')
foreach($r in $regions){
    $lines.Add(("REGION VA=0x{0:X8} RVA=0x{1:X8} SIZE=0x{2:X} PROTECT=0x{3:X}" -f $r.Address,($r.Address-$base),$r.Size,$r.Protect))
}

$vtableHits=New-Object System.Collections.Generic.List[object]
$lines.Add('')
$lines.Add('[VTABLE_RUNTIME_LITERAL_XREFS]')
foreach($kv in $Vtables.GetEnumerator()){
    $owner=$kv.Key; $rva=[long]$kv.Value; $va=[uint32]($base+$rva); $needle=[BitConverter]::GetBytes($va)
    $ownerHits=New-Object System.Collections.Generic.List[object]
    foreach($r in $regions){
        foreach($off in (Find-Needle $r.Bytes $needle $MaxHitsPerNeedle)){
            $addr=[long]$r.Address+$off
            $o=[pscustomobject]@{Owner=$owner;Address=$addr;Rva=$addr-$base;Region=$r;Offset=$off}
            $ownerHits.Add($o); $vtableHits.Add($o)
            if($ownerHits.Count -ge $MaxHitsPerNeedle){ break }
        }
        if($ownerHits.Count -ge $MaxHitsPerNeedle){ break }
    }
    $lines.Add(("OWNER={0} VTABLE_RVA=0x{1:X8} VTABLE_VA=0x{2:X8} EXEC_RUNTIME_HITS={3}" -f $owner,$rva,$va,$ownerHits.Count))
    foreach($h in $ownerHits){
        $lines.Add(("  HIT RVA=0x{0:X8} VA=0x{1:X8} BYTES={2}" -f $h.Rva,$h.Address,(Hex $h.Region.Bytes ([Math]::Max(0,$h.Offset-12)) 40)))
    }
}

$memberHits=New-Object System.Collections.Generic.List[object]
$lines.Add('')
$lines.Add('[GLOBAL_RUNTIME_MEMBER_REFS]')
foreach($disp in $InterestingOffsets){
    $needle=[BitConverter]::GetBytes([uint32]$disp)
    $hits=New-Object System.Collections.Generic.List[object]
    foreach($r in $regions){
        foreach($off in (Find-Needle $r.Bytes $needle $MaxHitsPerNeedle)){
            $addr=[long]$r.Address+$off
            $o=[pscustomobject]@{Disp=$disp;Address=$addr;Rva=$addr-$base;Region=$r;Offset=$off}
            $hits.Add($o); $memberHits.Add($o)
            if($hits.Count -ge $MaxHitsPerNeedle){ break }
        }
        if($hits.Count -ge $MaxHitsPerNeedle){ break }
    }
    $lines.Add(("DISP=0x{0:X3} HITS={1}" -f $disp,$hits.Count))
    foreach($h in ($hits | Select-Object -First 64)){
        $lines.Add(("  HIT RVA=0x{0:X8} VA=0x{1:X8} BYTES={2}" -f $h.Rva,$h.Address,(Hex $h.Region.Bytes ([Math]::Max(0,$h.Offset-10)) 36)))
    }
}

$lines.Add('')
$lines.Add('[CORRELATED_OWNER_WINDOWS]')
$corr=New-Object System.Collections.Generic.List[object]
foreach($vh in $vtableHits){
    $near=@($memberHits | Where-Object { [Math]::Abs([long]$_.Address-[long]$vh.Address) -le $CorrelationRadius })
    $proven=@($near | Where-Object { $_.Disp -in @(0x0EC,0x15C,0x168) })
    $model=@($near | Where-Object { $_.Disp -ge 0x1E0 -and $_.Disp -le 0x230 })
    $score=($proven.Count*100)+($model.Count*4)+1
    $calls=@(Find-NearbyCalls $regions $vh.Address $CorrelationRadius)
    $corr.Add([pscustomobject]@{Owner=$vh.Owner;Hit=$vh;Proven=$proven;Model=$model;Calls=$calls;Score=$score})
}
foreach($c in ($corr | Sort-Object Score -Descending)){
    $lines.Add(("OWNER={0} XREF_RVA=0x{1:X8} SCORE={2} PROVEN_REFS={3} MODEL_REFS={4} CALLS={5}" -f $c.Owner,$c.Hit.Rva,$c.Score,$c.Proven.Count,$c.Model.Count,$c.Calls.Count))
    foreach($x in ($c.Proven | Select-Object -First 16)){$lines.Add(("  PROVEN DISP=0x{0:X3} RVA=0x{1:X8}" -f $x.Disp,$x.Rva))}
    foreach($x in ($c.Model | Select-Object -First 16)){$lines.Add(("  MODEL DISP=0x{0:X3} RVA=0x{1:X8}" -f $x.Disp,$x.Rva))}
    foreach($x in ($c.Calls | Select-Object -First 24)){$lines.Add(("  CALL RVA=0x{0:X8} -> 0x{1:X8}" -f $x.AtRva,$x.TargetRva))}
}

$strong=@($corr | Where-Object { $_.Proven.Count -ge 2 } | Sort-Object Score -Descending)
$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add('STATUS=PASS_RUNTIME_MODULE_ONLY')
$lines.Add("VTABLE_RUNTIME_XREF_TOTAL=$($vtableHits.Count)")
$lines.Add("GLOBAL_RUNTIME_MEMBER_REF_TOTAL=$($memberHits.Count)")
$lines.Add("STRONG_OWNER_WINDOW_COUNT=$($strong.Count)")
if($strong.Count -gt 0){
    $best=$strong[0]
    $lines.Add(("BEST_OWNER={0}" -f $best.Owner))
    $lines.Add(("BEST_XREF_RVA=0x{0:X8}" -f $best.Hit.Rva))
    $lines.Add(("BEST_SCORE={0}" -f $best.Score))
    $lines.Add('NEXT=Inspect only BEST owner window and derive one stable owner/model pointer candidate; no heap/vector scan.')
}else{
    $lines.Add('BEST_OWNER=NONE')
    $lines.Add('NEXT=Do not widen to heap/vector scan. Use runtime module call/xref evidence to refine constructor/owner path first.')
}
$lines.Add('RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')

$parent=Split-Path -Parent $OutputPath
if($parent -and -not (Test-Path -LiteralPath $parent)){ New-Item -ItemType Directory -Force -Path $parent | Out-Null }
[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host 'STATUS=PASS_RUNTIME_MODULE_ONLY'
Write-Host "OUTPUT=$OutputPath"
Write-Host "VTABLE_RUNTIME_XREF_TOTAL=$($vtableHits.Count)"
Write-Host "STRONG_OWNER_WINDOW_COUNT=$($strong.Count)"
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
