param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$CatalogPath = "",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_vector64_profile.txt"
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
$InventoryGridVtableRva = 0x00EDDE38
$InventoryRootVtableRva = 0x00EDE2F8
$InvWinVtableRva = 0x00EDE180
$PrimaryVectorOffset = 0x220
$RecordStride = 64
$ItemIdOffset = 4

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

public sealed class InvVecHit850 { public long Address; public uint Value; }
public static class InvVecMem850 {
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

    public static List<InvVecHit850> ScanPrivateDword(int pid,uint value,int maxHits) {
        var outp=new List<InvVecHit850>();
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            long address=0x10000; const long maxAddress=0x7FFF0000; uint ms=(uint)Marshal.SizeOf(typeof(MBI));
            while(address<maxAddress && outp.Count<maxHits) {
                MBI m; if(VirtualQueryEx(h,new IntPtr(address),out m,ms)==0) break;
                long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64()); if(rs<=0) break;
                bool okRegion=m.State==MEM_COMMIT && m.Type==MEM_PRIVATE && Readable(m.Protect);
                if(okRegion) {
                    long off=0;
                    while(off<rs && outp.Count<maxHits) {
                        int want=(int)Math.Min((long)Chunk,rs-off); if(want<=0) break;
                        byte[] b=new byte[want]; IntPtr rp;
                        bool ok=ReadProcessMemory(h,new IntPtr(rb+off),b,want,out rp);
                        int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0;
                        for(int i=0;i+3<got && outp.Count<maxHits;i+=4) {
                            if(BitConverter.ToUInt32(b,i)==value) outp.Add(new InvVecHit850{Address=rb+off+i,Value=value});
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

function Read-U32([byte[]]$b,[int]$o) {
    if ($null -eq $b -or $o -lt 0 -or $o+4 -gt $b.Length) { return [uint32]0 }
    return [BitConverter]::ToUInt32($b,$o)
}
function Read-RemoteU32([long]$addr) {
    $b=[InvVecMem850]::Read($proc.Id,$addr,4)
    if($b.Length -lt 4){return [uint32]0}
    return [BitConverter]::ToUInt32($b,0)
}
function Is-ReadablePtr([uint32]$v) {
    if($v -lt 0x10000 -or $v -ge 0x7FFF0000){return $false}
    return [InvVecMem850]::IsReadableAddress($proc.Id,[long]$v)
}
function To-ItemId([uint32]$v) {
    if([uint64]$v -gt [uint64][int]::MaxValue){return $null}
    return [int]$v
}

function Test-InventoryGridObject([long]$addr) {
    $b=[InvVecMem850]::Read($proc.Id,$addr,0x220)
    if($b.Length -lt 0x16C){return $null}
    if((Read-U32 $b 0) -ne $gridVt){return $null}
    $root=[uint32](Read-U32 $b 0xEC)
    if(-not (Is-ReadablePtr $root)){return $null}
    $rb=[InvVecMem850]::Read($proc.Id,$root,0x180)
    if($rb.Length -lt 0x16C){return $null}
    if((Read-U32 $rb 0) -ne $rootVt){return $null}
    if((Read-U32 $rb 0x15C) -ne [uint32]$addr){return $null}
    $inv=[uint32](Read-U32 $rb 0x168)
    if(-not (Is-ReadablePtr $inv)){return $null}
    if((Read-RemoteU32 $inv) -ne $invWinVt){return $null}
    return [pscustomobject]@{Grid=[uint32]$addr;Root=$root;InvWin=$inv}
}

$gridHits=[InvVecMem850]::ScanPrivateDword($proc.Id,$gridVt,32)
$verified=New-Object System.Collections.Generic.List[object]
foreach($h in $gridHits){$x=Test-InventoryGridObject $h.Address;if($null-ne$x){$verified.Add($x)}}
if($verified.Count -ne 1){throw "Expected exactly one verified InventoryItemGrid object, got $($verified.Count)."}
$live=$verified[0]

$invBytes=[InvVecMem850]::Read($proc.Id,$live.InvWin,$PrimaryVectorOffset+16)
if($invBytes.Length -lt ($PrimaryVectorOffset+12)){throw "InvWin object read too short."}
$begin=[uint32](Read-U32 $invBytes $PrimaryVectorOffset)
$end=[uint32](Read-U32 $invBytes ($PrimaryVectorOffset+4))
$cap=[uint32](Read-U32 $invBytes ($PrimaryVectorOffset+8))
if(-not (Is-ReadablePtr $begin)){throw "Inventory vector begin is not readable: 0x$($begin.ToString('X8'))"}
if($end -lt $begin -or $cap -lt $end){throw "Inventory vector ordering invalid."}
$used=[long]$end-[long]$begin
$capacity=[long]$cap-[long]$begin
if($used -le 0 -or ($used % $RecordStride) -ne 0){throw "Inventory vector used bytes $used not divisible by stride $RecordStride."}
$count=[int]($used/$RecordStride)
if($count -lt 1 -or $count -gt 256){throw "Inventory vector record count implausible: $count"}

$buf=[InvVecMem850]::Read($proc.Id,$begin,[int]$used)
if($buf.Length -ne $used){throw "Inventory vector read incomplete: got $($buf.Length), expected $used"}

$records=New-Object System.Collections.Generic.List[object]
for($i=0;$i -lt $count;$i++){
    $ro=$i*$RecordStride
    $rawItem=[uint32](Read-U32 $buf ($ro+$ItemIdOffset))
    $itemId=To-ItemId $rawItem
    $isCatalog=($null-ne$itemId -and $itemId -gt 0 -and $catalog.Contains($itemId))
    $name=if($isCatalog -and $nameById.ContainsKey($itemId)){$nameById[$itemId]}else{""}
    $records.Add([pscustomobject]@{Index=$i;Offset=$ro;ItemId=$itemId;Catalog=$isCatalog;Name=$name})
}
$occupied=@($records | Where-Object {$_.Catalog})
$catalogPct=if($count -gt 0){[int][Math]::Round(($occupied.Count*100.0)/$count)}else{0}

$fieldStats=New-Object System.Collections.Generic.List[object]
for($fo=0;$fo -lt $RecordStride;$fo+=4){
    $nonzero=0;$catalogHits=0;$ptrHits=0;$smallHits=0;$boolHits=0
    $uniq=New-Object 'System.Collections.Generic.HashSet[uint32]'
    foreach($r in $occupied){
        $v=[uint32](Read-U32 $buf ($r.Offset+$fo))
        if($v -ne 0){$nonzero++;$null=$uniq.Add($v)}
        $iv=To-ItemId $v
        if($null-ne$iv -and $catalog.Contains($iv)){$catalogHits++}
        if(Is-ReadablePtr $v){$ptrHits++}
        if([uint64]$v -le 1000000){$smallHits++}
        if($v -le 1){$boolHits++}
    }
    $fieldStats.Add([pscustomobject]@{Offset=$fo;NonZero=$nonzero;Unique=$uniq.Count;Catalog=$catalogHits;Pointers=$ptrHits;Small=$smallHits;Bool=$boolHits})
}

$linkMap=@{}
foreach($fo in 0..15){
    $fieldOff=$fo*4
    foreach($r in $occupied){
        $ptr=[uint32](Read-U32 $buf ($r.Offset+$fieldOff))
        if(-not (Is-ReadablePtr $ptr)){continue}
        $tb=[InvVecMem850]::Read($proc.Id,$ptr,0x100)
        if($tb.Length -lt 8){continue}
        for($to=0;$to+4 -le $tb.Length;$to+=4){
            $tv=[uint32](Read-U32 $tb $to)
            $iv=To-ItemId $tv
            if($null-eq$iv -or $iv -ne $r.ItemId){continue}
            $key=("{0:X2}:{1:X2}" -f $fieldOff,$to)
            if(-not $linkMap.ContainsKey($key)){$linkMap[$key]=0}
            $linkMap[$key]++
        }
    }
}
$links=New-Object System.Collections.Generic.List[object]
foreach($k in $linkMap.Keys){
    $parts=$k.Split(':');$rf=[Convert]::ToInt32($parts[0],16);$to=[Convert]::ToInt32($parts[1],16);$m=[int]$linkMap[$k]
    $pct=if($occupied.Count -gt 0){[int][Math]::Round(($m*100.0)/$occupied.Count)}else{0}
    $links.Add([pscustomobject]@{RecordField=$rf;TargetItemOffset=$to;Matches=$m;Pct=$pct})
}
$rankLinks=@($links | Sort-Object Matches,Pct -Descending | Select-Object -First 12)

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_INVENTORY_VECTOR64_PROFILE")
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $base))
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("")
$lines.Add("[LIVE_GRAPH]")
$lines.Add(("GRID=0x{0:X8}" -f $live.Grid))
$lines.Add(("ROOT=0x{0:X8}" -f $live.Root))
$lines.Add(("INVWIN=0x{0:X8}" -f $live.InvWin))
$lines.Add("")
$lines.Add("[PRIMARY_VECTOR]")
$lines.Add(("INVWIN_VECTOR_OFFSET=0x{0:X}" -f $PrimaryVectorOffset))
$lines.Add(("BEGIN=0x{0:X8}" -f $begin))
$lines.Add(("END=0x{0:X8}" -f $end))
$lines.Add(("CAP=0x{0:X8}" -f $cap))
$lines.Add("USED_BYTES=$used")
$lines.Add("CAPACITY_BYTES=$capacity")
$lines.Add("RECORD_STRIDE=$RecordStride")
$lines.Add("RECORD_COUNT=$count")
$lines.Add(("ITEM_ID_OFFSET=0x{0:X}" -f $ItemIdOffset))
$lines.Add("CATALOG_RECORDS=$($occupied.Count)")
$lines.Add("CATALOG_RECORD_PCT=$catalogPct")
$lines.Add("")
$lines.Add("[RECORDS]")
foreach($r in $records){
    $ro=$r.Offset
    $d=@();for($fo=0;$fo -lt $RecordStride;$fo+=4){$d += ("{0:X8}" -f [uint32](Read-U32 $buf ($ro+$fo)))}
    $lines.Add(("REC={0} ITEM={1} CATALOG={2} NAME={3} DWORDS={4}" -f $r.Index,($(if($null-ne$r.ItemId){$r.ItemId}else{-1})),([int]$r.Catalog),$r.Name,($d -join ',')))
}
$lines.Add("")
$lines.Add("[FIELD_STATS_OCCUPIED_RECORDS]")
foreach($f in $fieldStats){
    $lines.Add(("FIELD=0x{0:X2} NONZERO={1} UNIQUE={2} CATALOG={3} READABLE_PTR={4} SMALL_LE_1M={5} BOOL01={6}" -f $f.Offset,$f.NonZero,$f.Unique,$f.Catalog,$f.Pointers,$f.Small,$f.Bool))
}
$lines.Add("")
$lines.Add("[POINTER_ITEMID_LINKS]")
foreach($l in $rankLinks){
    $lines.Add(("LINK RECORD_FIELD=0x{0:X2} TARGET_ITEMID_OFF=0x{1:X2} MATCHES={2} PCT={3}" -f $l.RecordField,$l.TargetItemOffset,$l.Matches,$l.Pct))
}
$lines.Add("")
$lines.Add("[SUMMARY]")
$vectorStrong=($count -ge 2 -and $count -le 128 -and $catalogPct -ge 80)
$topLink=if($rankLinks.Count -gt 0){$rankLinks[0]}else{$null}
$linkStrong=($null-ne$topLink -and $topLink.Matches -ge 3 -and $topLink.Pct -ge 50)
$lines.Add("VECTOR64_ITEMID_STRONG=$([int]$vectorStrong)")
$lines.Add("BACKING_OBJECT_POINTER_STRONG=$([int]$linkStrong)")
if($null-ne$topLink){
    $lines.Add(("TOP_LINK_RECORD_FIELD=0x{0:X2}" -f $topLink.RecordField))
    $lines.Add(("TOP_LINK_TARGET_ITEMID_OFF=0x{0:X2}" -f $topLink.TargetItemOffset))
    $lines.Add("TOP_LINK_MATCHES=$($topLink.Matches)")
    $lines.Add("TOP_LINK_PCT=$($topLink.Pct)")
}
$lines.Add("FORMAL_WP5=NOT_YET")
$lines.Add("NEXT=Profile candidate objectId/count/enchant/equipped fields only inside this vector/backing-object relation; then run controlled inventory behavior correlation.")
$lines.Add("MEMORY_WRITE=NO")

[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "RECORD_COUNT=$count"
Write-Host "CATALOG_RECORDS=$($occupied.Count)"
Write-Host "CATALOG_RECORD_PCT=$catalogPct"
Write-Host "VECTOR64_ITEMID_STRONG=$([int]$vectorStrong)"
Write-Host "BACKING_OBJECT_POINTER_STRONG=$([int]$linkStrong)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
