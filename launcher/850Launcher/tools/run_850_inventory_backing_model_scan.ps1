param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$CatalogPath = "",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_backing_model_scan.txt"
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
$InventoryGridVtableRva = 0x00EDDE38
$InventoryRootVtableRva = 0x00EDE2F8
$InvWinVtableRva = 0x00EDE180

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$sha = (Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

if ([string]::IsNullOrWhiteSpace($CatalogPath)) {
    $CatalogPath = [IO.Path]::GetFullPath((Join-Path $PSScriptRoot "..\item-names.csv"))
}
if (-not (Test-Path -LiteralPath $CatalogPath)) { throw "Item catalog not found: $CatalogPath" }

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }

function Resolve-AuthoritativeProcess {
    param([string]$ExpectedPath)
    $full = [IO.Path]::GetFullPath($ExpectedPath)
    foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
        try {
            if ($p.HasExited) { continue }
            if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)) {
                return $p
            }
        } catch { }
    }
    throw "Running authoritative Lin.bin2 process not found."
}

$proc = Resolve-AuthoritativeProcess -ExpectedPath $ClientPath
$module = $proc.MainModule
if (-not $module) { throw "MainModule unavailable; run elevated if required." }
$base = [long]$module.BaseAddress
$size = [int]$module.ModuleMemorySize
$gridVt = [uint32]($base + $InventoryGridVtableRva)
$rootVt = [uint32]($base + $InventoryRootVtableRva)
$invWinVt = [uint32]($base + $InvWinVtableRva)

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

public sealed class InvBackingDwordHit850 {
    public long Address;
    public uint Value;
}

public static class InvBackingMem850 {
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
        if(address<=0 || size<=0) return new byte[0];
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

    public static bool IsReadableAddress(int pid,long address) {
        if(address<0x10000 || address>=0x7FFF0000) return false;
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) return false;
        try {
            MBI m; uint ms=(uint)Marshal.SizeOf(typeof(MBI));
            if(VirtualQueryEx(h,new IntPtr(address),out m,ms)==0) return false;
            long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64());
            return m.State==MEM_COMMIT && Readable(m.Protect) && address>=rb && address<rb+rs;
        } finally { CloseHandle(h); }
    }

    public static List<InvBackingDwordHit850> ScanPrivateDword(int pid,uint value,int maxHits) {
        var outp=new List<InvBackingDwordHit850>();
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            long address=0x10000; const long maxAddress=0x7FFF0000;
            uint ms=(uint)Marshal.SizeOf(typeof(MBI));
            while(address<maxAddress && outp.Count<maxHits) {
                MBI m; if(VirtualQueryEx(h,new IntPtr(address),out m,ms)==0) break;
                long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64());
                if(rs<=0) break;
                bool okRegion=m.State==MEM_COMMIT && m.Type==MEM_PRIVATE && Readable(m.Protect);
                if(okRegion) {
                    long off=0;
                    while(off<rs && outp.Count<maxHits) {
                        int want=(int)Math.Min((long)Chunk,rs-off); if(want<=0) break;
                        byte[] b=new byte[want]; IntPtr rp;
                        bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,want,out rp);
                        int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;
                        for(int i=0;i+3<got && outp.Count<maxHits;i+=4) {
                            if(BitConverter.ToUInt32(b,i)==value) outp.Add(new InvBackingDwordHit850{Address=rb+off+i,Value=value});
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

$catalog = New-Object 'System.Collections.Generic.HashSet[int]'
$nameById = @{}
foreach ($row in (Import-Csv -LiteralPath $CatalogPath)) {
    $id = 0
    if ([int]::TryParse([string]$row.item_id,[ref]$id)) {
        $null = $catalog.Add($id)
        if (-not $nameById.ContainsKey($id)) { $nameById[$id] = [string]$row.name }
    }
}
if ($catalog.Count -lt 100) { throw "Item catalog unexpectedly small: $($catalog.Count)" }

function Read-U32([byte[]]$b,[int]$o) {
    if ($null -eq $b -or $o -lt 0 -or $o+4 -gt $b.Length) { return [uint32]0 }
    return [BitConverter]::ToUInt32($b,$o)
}
function Read-RemoteU32([long]$addr) {
    $b=[InvBackingMem850]::Read($proc.Id,$addr,4)
    if($b.Length -lt 4){return [uint32]0}
    return [BitConverter]::ToUInt32($b,0)
}
function Is-PlausiblePtr([uint32]$v) {
    if($v -lt 0x10000 -or $v -ge 0x7FFF0000){return $false}
    return [InvBackingMem850]::IsReadableAddress($proc.Id,[long]$v)
}

function Test-InventoryGridObject([long]$addr) {
    $b=[InvBackingMem850]::Read($proc.Id,$addr,0x220)
    if($b.Length -lt 0x16C){return $null}
    if((Read-U32 $b 0) -ne $gridVt){return $null}
    $root=[uint32](Read-U32 $b 0xEC)
    if(-not (Is-PlausiblePtr $root)){return $null}
    $rb=[InvBackingMem850]::Read($proc.Id,$root,0x180)
    if($rb.Length -lt 0x16C){return $null}
    if((Read-U32 $rb 0) -ne $rootVt){return $null}
    if((Read-U32 $rb 0x15C) -ne [uint32]$addr){return $null}
    $inv=[uint32](Read-U32 $rb 0x168)
    $invOk=$false
    if(Is-PlausiblePtr $inv){$invOk=((Read-RemoteU32 $inv) -eq $invWinVt)}
    return [pscustomobject]@{Grid=[uint32]$addr;Root=$root;InvWin=$inv;InvWinValid=$invOk}
}

function Get-VectorTriples([string]$owner,[uint32]$ownerAddr,[int]$maxOffset) {
    $b=[InvBackingMem850]::Read($proc.Id,$ownerAddr,$maxOffset+16)
    $out=New-Object System.Collections.Generic.List[object]
    if($b.Length -lt 16){return $out}
    for($o=0x20;$o+12 -le [Math]::Min($b.Length,$maxOffset);$o+=4){
        $a=[uint32](Read-U32 $b $o);$e=[uint32](Read-U32 $b ($o+4));$c=[uint32](Read-U32 $b ($o+8))
        if(-not (Is-PlausiblePtr $a)){continue}
        if($e -lt $a -or $c -lt $e){continue}
        $used=[long]$e-[long]$a;$cap=[long]$c-[long]$a
        if($used -lt 4 -or $used -gt 0x20000 -or $cap -gt 0x40000){continue}
        $strideHits=New-Object System.Collections.Generic.List[int]
        foreach($s in @(4,8,12,16,20,24,28,32,36,40,48,56,64,80,96)){
            if(($used % $s)-eq 0){$n=[int]($used/$s);if($n-ge 1 -and $n-le 512){$strideHits.Add($s)}}
        }
        if($strideHits.Count -gt 0){
            $out.Add([pscustomobject]@{Owner=$owner;OwnerAddr=$ownerAddr;Offset=$o;Begin=$a;End=$e;Capacity=$c;Used=$used;Strides=($strideHits.ToArray())})
        }
    }
    return $out
}

function Analyze-RecordBuffer([string]$source,[uint32]$address,[int]$bytes,[int[]]$forcedStrides=$null) {
    $b=[InvBackingMem850]::Read($proc.Id,$address,$bytes)
    $out=New-Object System.Collections.Generic.List[object]
    if($b.Length -lt 16){return $out}
    $strides = if($forcedStrides -and $forcedStrides.Count -gt 0){$forcedStrides}else{@(8,12,16,20,24,28,32,36,40,48,56,64,80,96)}
    foreach($stride in $strides){
        if($stride -lt 4){continue}
        $records=[Math]::Min(64,[int]([Math]::Floor($b.Length/[double]$stride)))
        if($records -lt 2){continue}
        $maxField=[Math]::Min($stride-4,32)
        for($fo=0;$fo -le $maxField;$fo+=4){
            $matches=0;$nonzero=0;$zeros=0
            $unique=New-Object 'System.Collections.Generic.HashSet[int]'
            $samples=New-Object System.Collections.Generic.List[string]
            for($i=0;$i -lt $records;$i++){
                $p=($i*$stride)+$fo;if($p+4 -gt $b.Length){break}
                $raw=[uint32](Read-U32 $b $p)
                if($raw -eq 0){$zeros++;continue}
                $nonzero++
                if($raw -gt [uint32][int]::MaxValue){continue}
                $v=[int]$raw
                if($catalog.Contains($v)){
                    $matches++;$null=$unique.Add($v)
                    if($samples.Count -lt 8){
                        $prev=if($p-ge 4){[uint32](Read-U32 $b ($p-4))}else{0}
                        $next=if($p+8 -le $b.Length){[uint32](Read-U32 $b ($p+4))}else{0}
                        $nm=if($nameById.ContainsKey($v)){$nameById[$v]}else{""}
                        $samples.Add(("REC={0} ITEM={1} NAME={2} PREV=0x{3:X8} NEXT=0x{4:X8}" -f $i,$v,$nm,$prev,$next))
                    }
                }
            }
            if($matches -lt 2){continue}
            $pct=if($nonzero -gt 0){[int][Math]::Round(($matches*100.0)/$nonzero)}else{0}
            $score=($matches*10)+($unique.Count*6)+$pct
            $out.Add([pscustomobject]@{Source=$source;Address=$address;Stride=$stride;FieldOffset=$fo;Records=$records;Matches=$matches;Unique=$unique.Count;NonZero=$nonzero;Zero=$zeros;Pct=$pct;Score=$score;Samples=$samples.ToArray()})
        }
    }
    return $out
}

$gridHits=[InvBackingMem850]::ScanPrivateDword($proc.Id,$gridVt,32)
$verified=New-Object System.Collections.Generic.List[object]
foreach($h in $gridHits){$v=Test-InventoryGridObject $h.Address;if($null-ne$v){$verified.Add($v)}}
if($verified.Count -eq 0){throw "No verified InventoryItemGrid object found."}
$live=$verified[0]

$vectorTriples=New-Object System.Collections.Generic.List[object]
foreach($x in (Get-VectorTriples 'GRID' $live.Grid 0x170)){$vectorTriples.Add($x)}
foreach($x in (Get-VectorTriples 'ROOT' $live.Root 0x240)){$vectorTriples.Add($x)}
if($live.InvWinValid){foreach($x in (Get-VectorTriples 'INVWIN' $live.InvWin 0x240)){$vectorTriples.Add($x)}}

$candidates=New-Object System.Collections.Generic.List[object]
foreach($v in $vectorTriples){
    foreach($x in (Analyze-RecordBuffer ("VECTOR {0}+0x{1:X}" -f $v.Owner,$v.Offset) $v.Begin ([int][Math]::Min([long]$v.Used,0x6000)) $v.Strides)){$candidates.Add($x)}
}

$owners=@(
    [pscustomobject]@{Name='GRID';Address=$live.Grid;Max=0x170},
    [pscustomobject]@{Name='ROOT';Address=$live.Root;Max=0x240}
)
if($live.InvWinValid){$owners += [pscustomobject]@{Name='INVWIN';Address=$live.InvWin;Max=0x240}}
$seenPtrs=New-Object 'System.Collections.Generic.HashSet[uint32]'
foreach($o in $owners){
    $b=[InvBackingMem850]::Read($proc.Id,$o.Address,$o.Max)
    for($fo=0x20;$fo+4 -le $b.Length;$fo+=4){
        $p=[uint32](Read-U32 $b $fo)
        if(-not (Is-PlausiblePtr $p)){continue}
        if(-not $seenPtrs.Add($p)){continue}
        foreach($x in (Analyze-RecordBuffer ("PTR {0}+0x{1:X}" -f $o.Name,$fo) $p 0x1800 $null)){$candidates.Add($x)}
        if($seenPtrs.Count -ge 96){break}
    }
}

$ranked=@($candidates | Sort-Object Score,Matches,Unique -Descending | Select-Object -First 30)
$strong=@($ranked | Where-Object { $_.Matches -ge 3 -and $_.Unique -ge 2 -and $_.Pct -ge 30 })

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_INVENTORY_BACKING_MODEL_SCAN")
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $base))
$lines.Add("CATALOG=$CatalogPath")
$lines.Add("CATALOG_IDS=$($catalog.Count)")
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("")
$lines.Add("[LIVE_INVENTORY_UI_GRAPH]")
$lines.Add(("GRID_VTABLE_RVA=0x{0:X8}" -f $InventoryGridVtableRva))
$lines.Add(("GRID_VTABLE_VA=0x{0:X8}" -f $gridVt))
$lines.Add("GRID_VTABLE_RAW_HITS=$($gridHits.Count)")
$lines.Add("VERIFIED_GRID_OBJECTS=$($verified.Count)")
$lines.Add(("GRID_OBJECT=0x{0:X8}" -f $live.Grid))
$lines.Add(("ROOT_OBJECT=0x{0:X8}" -f $live.Root))
$lines.Add(("INVWIN_OBJECT=0x{0:X8}" -f $live.InvWin))
$lines.Add("INVWIN_VALID=$([int]$live.InvWinValid)")
$lines.Add("PARENT_OFFSET=0xEC")
$lines.Add("ROOT_GRID_OFFSET=0x15C")
$lines.Add("ROOT_INVWIN_OFFSET=0x168")
$lines.Add("")
$lines.Add("[VECTOR_TRIPLES]")
$lines.Add("COUNT=$($vectorTriples.Count)")
foreach($v in $vectorTriples){
    $lines.Add(("VECTOR OWNER={0} OWNER_ADDR=0x{1:X8} OFF=0x{2:X3} BEGIN=0x{3:X8} END=0x{4:X8} CAP=0x{5:X8} USED={6} STRIDES={7}" -f $v.Owner,$v.OwnerAddr,$v.Offset,$v.Begin,$v.End,$v.Capacity,$v.Used,($v.Strides -join ',')))
}
$lines.Add("")
$lines.Add("[ITEM_RECORD_CANDIDATES]")
$lines.Add("TOTAL_CANDIDATES=$($candidates.Count)")
$lines.Add("STRONG_CANDIDATES=$($strong.Count)")
foreach($c in $ranked){
    $lines.Add(("CAND SOURCE={0} ADDR=0x{1:X8} STRIDE={2} ITEM_OFF=0x{3:X} RECORDS={4} MATCHES={5} UNIQUE={6} NONZERO={7} ZERO={8} MATCH_PCT={9} SCORE={10}" -f $c.Source,$c.Address,$c.Stride,$c.FieldOffset,$c.Records,$c.Matches,$c.Unique,$c.NonZero,$c.Zero,$c.Pct,$c.Score))
    foreach($s in $c.Samples){$lines.Add("  SAMPLE $s")}
}
$lines.Add("")
$lines.Add("[SUMMARY]")
$lines.Add("BACKING_MODEL_CANDIDATES=$($strong.Count)")
if($strong.Count -gt 0){
    $lines.Add("STATUS=PASS_CANDIDATE_BACKING_MODEL")
    $lines.Add("NEXT=Run controlled pick/drop/stack/equip behavior correlation only against the top backing-model candidates; do not rescan whole process.")
}else{
    $lines.Add("STATUS=NO_STRONG_BACKING_MODEL_YET")
    $lines.Add("NEXT=Use InventoryItemGrid vtable methods/xrefs to locate the model pointer rather than broad item-id process scans.")
}
$lines.Add("FORMAL_WP5=NOT_YET")
$lines.Add("MEMORY_WRITE=NO")

[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host "STATUS=$($lines | Where-Object { $_ -like 'STATUS=*' } | Select-Object -Last 1)"
Write-Host "PID=$($proc.Id)"
Write-Host "VERIFIED_GRID_OBJECTS=$($verified.Count)"
Write-Host "VECTOR_TRIPLES=$($vectorTriples.Count)"
Write-Host "BACKING_MODEL_CANDIDATES=$($strong.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
