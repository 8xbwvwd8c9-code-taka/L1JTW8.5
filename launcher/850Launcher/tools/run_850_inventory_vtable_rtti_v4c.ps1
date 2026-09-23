param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_vtable_rtti_v4c.txt",
    [int]$VtableEntries = 32,
    [int]$MaxBases = 24
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$Vtables = [ordered]@{
    GRID   = 0x00EDDE38L
    ROOT   = 0x00EDE2F8L
    INVWIN = 0x00EDE180L
}

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$full = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$proc = $null
foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
    try {
        if ($p.HasExited) { continue }
        if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName), $full, [StringComparison]::OrdinalIgnoreCase)) {
            $proc = $p
            break
        }
    } catch { }
}
if ($null -eq $proc) { throw 'Running authoritative Lin.bin2 process not found.' }

$module = $proc.MainModule
$base = [long]$module.BaseAddress
$size = [long]$module.ModuleMemorySize
$limit = $base + $size

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
public sealed class InvRttiRegion850 { public long Address; public long Size; public uint Protect; public uint Type; public byte[] Bytes; }
public static class InvRttiMem850 {
    const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400, MEM_COMMIT=0x1000, MEM_IMAGE=0x1000000, PAGE_GUARD=0x100, PAGE_NOACCESS=0x01;
    [StructLayout(LayoutKind.Sequential)] struct MBI { public IntPtr BaseAddress,AllocationBase; public uint AllocationProtect; public UIntPtr RegionSize; public uint State,Protect,Type; }
    [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
    [DllImport("kernel32.dll",SetLastError=true)] static extern int VirtualQueryEx(IntPtr h,IntPtr a,out MBI m,uint l);
    static bool Readable(uint p){ if((p&PAGE_GUARD)!=0 || (p&PAGE_NOACCESS)!=0) return false; uint x=p&0xFF; return x==0x02||x==0x04||x==0x08||x==0x10||x==0x20||x==0x40||x==0x80; }
    public static List<InvRttiRegion850> ReadImage(int pid,long start,long end){
        var o=new List<InvRttiRegion850>(); IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid); if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try { uint ms=(uint)Marshal.SizeOf(typeof(MBI)); long cur=start; while(cur<end){ MBI m; if(VirtualQueryEx(h,new IntPtr(cur),out m,ms)==0) break; long rb=m.BaseAddress.ToInt64(), rs=unchecked((long)m.RegionSize.ToUInt64()); if(rs<=0) break; long a=Math.Max(start,rb), z=Math.Min(end,rb+rs); if(z>a && m.State==MEM_COMMIT && m.Type==MEM_IMAGE && Readable(m.Protect)){ long rem=z-a,off=0; while(off<rem){ int want=(int)Math.Min(262144L,rem-off); byte[] b=new byte[want]; IntPtr rp; bool ok=ReadProcessMemory(h,new IntPtr(a+off),b,want,out rp); int got=ok?(int)Math.Min((long)want,rp.ToInt64()):0; if(got>0){ if(got!=b.Length){byte[] t=new byte[got];Array.Copy(b,t,got);b=t;} o.Add(new InvRttiRegion850{Address=a+off,Size=got,Protect=m.Protect,Type=m.Type,Bytes=b}); } off+=want; } } long n=rb+rs; if(n<=cur) break; cur=n; } return o; } finally { CloseHandle(h); }
    }
}
"@

$regions = [InvRttiMem850]::ReadImage($proc.Id, $base, $limit)
function Region-For([long]$Address) { foreach ($r in $regions) { if ($Address -ge [long]$r.Address -and $Address -lt ([long]$r.Address + [long]$r.Size)) { return $r } }; return $null }
function U32([long]$Address) { $r=Region-For $Address; if($null -eq $r){return $null}; $o=[int]($Address-[long]$r.Address); if($o+4 -gt $r.Bytes.Length){return $null}; return [BitConverter]::ToUInt32($r.Bytes,$o) }
function In-Module([uint64]$Value) { return ($Value -ge [uint64]$base -and $Value -lt [uint64]$limit) }
function Read-AsciiZ([long]$Address,[int]$Max=192) { $r=Region-For $Address; if($null -eq $r){return ''}; $o=[int]($Address-[long]$r.Address); $bytes=New-Object System.Collections.Generic.List[byte]; for($i=0;$i -lt $Max -and ($o+$i) -lt $r.Bytes.Length;$i++){ $x=[byte]$r.Bytes[$o+$i]; if($x -eq 0){break}; if($x -lt 0x20 -or $x -gt 0x7E){break}; $bytes.Add($x) }; if($bytes.Count -eq 0){return ''}; return [Text.Encoding]::ASCII.GetString($bytes.ToArray()) }
function RvaText([uint64]$Value) { if(In-Module $Value){ return ('0x{0:X8}' -f ([long]$Value-$base)) }; return 'OUTSIDE' }

function Read-TypeName([uint32]$TypeDescriptorVa) {
    if (-not (In-Module $TypeDescriptorVa)) { return '' }
    # MSVC x86 TypeDescriptor: vfptr, spare, decorated-name[]
    return Read-AsciiZ ([long]$TypeDescriptorVa + 8) 192
}

$rows = New-Object System.Collections.Generic.List[object]
$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_INVENTORY_VTABLE_RTTI_V4C')
$lines.Add("CLIENT=$full")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $base))
$lines.Add(("MODULE_SIZE=0x{0:X}" -f $size))
$lines.Add('RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE')
$lines.Add('HEAP_DEREFERENCE=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')

foreach ($kv in $Vtables.GetEnumerator()) {
    $owner=$kv.Key; $vtRva=[long]$kv.Value; $vtVa=$base+$vtRva
    $colPtr=U32 ($vtVa-4)
    $typeName=''; $colSig=$null; $colOffset=$null; $colCd=$null; $typeDesc=$null; $classDesc=$null
    if($null -ne $colPtr -and (In-Module $colPtr)) {
        $colSig=U32 $colPtr; $colOffset=U32 ([long]$colPtr+4); $colCd=U32 ([long]$colPtr+8); $typeDesc=U32 ([long]$colPtr+12); $classDesc=U32 ([long]$colPtr+16)
        if($null -ne $typeDesc){$typeName=Read-TypeName $typeDesc}
    }
    $entryList=New-Object System.Collections.Generic.List[uint32]
    for($i=0;$i -lt $VtableEntries;$i++){ $v=U32 ($vtVa+($i*4)); if($null -eq $v){break}; $entryList.Add([uint32]$v) }
    $rows.Add([pscustomobject]@{Owner=$owner;VtRva=$vtRva;VtVa=$vtVa;Col=$colPtr;TypeName=$typeName;Entries=$entryList.ToArray();TypeDesc=$typeDesc;ClassDesc=$classDesc})

    $lines.Add('')
    $lines.Add("[VTABLE_$owner]")
    $lines.Add(("VTABLE_RVA=0x{0:X8}" -f $vtRva))
    $lines.Add(("VTABLE_VA=0x{0:X8}" -f $vtVa))
    $lines.Add(("COL_PTR={0}" -f ($(if($null -ne $colPtr){'0x'+([uint32]$colPtr).ToString('X8')}else{'UNREADABLE'}))))
    $lines.Add(("COL_RVA={0}" -f ($(if($null -ne $colPtr -and (In-Module $colPtr)){'0x'+(([long]$colPtr-$base).ToString('X8'))}else{'OUTSIDE_OR_NONE'}))))
    $lines.Add("TYPE_NAME=$typeName")
    if($null -ne $colSig){$lines.Add("COL_SIGNATURE=$colSig");$lines.Add("COL_OFFSET=$colOffset");$lines.Add("COL_CDOFFSET=$colCd")}
    if($null -ne $typeDesc){$lines.Add(("TYPE_DESCRIPTOR_VA=0x{0:X8} RVA={1}" -f $typeDesc,(RvaText $typeDesc)))}
    if($null -ne $classDesc){$lines.Add(("CLASS_DESCRIPTOR_VA=0x{0:X8} RVA={1}" -f $classDesc,(RvaText $classDesc)))}
    for($i=0;$i -lt $entryList.Count;$i++){ $v=$entryList[$i]; $lines.Add(("ENTRY[{0:D2}]=0x{1:X8} RVA={2}" -f $i,$v,(RvaText $v))) }

    if($null -ne $classDesc -and (In-Module $classDesc)) {
        # MSVC x86 ClassHierarchyDescriptor: signature, attributes, numBaseClasses, pBaseClassArray
        $chSig=U32 $classDesc; $chAttr=U32 ([long]$classDesc+4); $baseCount=U32 ([long]$classDesc+8); $baseArray=U32 ([long]$classDesc+12)
        $lines.Add("CHD_SIGNATURE=$chSig")
        $lines.Add(("CHD_ATTRIBUTES=0x{0:X8}" -f $chAttr))
        $lines.Add("CHD_BASE_COUNT=$baseCount")
        $lines.Add(("CHD_BASE_ARRAY={0}" -f ($(if($null -ne $baseArray){'0x'+([uint32]$baseArray).ToString('X8')}else{'UNREADABLE'}))))
        if($null -ne $baseCount -and $null -ne $baseArray -and (In-Module $baseArray)) {
            $n=[Math]::Min([int]$baseCount,$MaxBases)
            for($bi=0;$bi -lt $n;$bi++){
                $bcd=U32 ([long]$baseArray+($bi*4)); if($null -eq $bcd){break}
                $bType=if(In-Module $bcd){U32 $bcd}else{$null}
                $bName=if($null -ne $bType){Read-TypeName $bType}else{''}
                $numContained=if(In-Module $bcd){U32 ([long]$bcd+4)}else{$null}
                $mdisp=if(In-Module $bcd){U32 ([long]$bcd+8)}else{$null}
                $pdisp=if(In-Module $bcd){U32 ([long]$bcd+12)}else{$null}
                $vdisp=if(In-Module $bcd){U32 ([long]$bcd+16)}else{$null}
                $attr=if(In-Module $bcd){U32 ([long]$bcd+20)}else{$null}
                $lines.Add(("BASE[{0:D2}] BCD=0x{1:X8} TYPE={2} NAME={3} CONTAINED={4} MDISP={5} PDISP={6} VDISP={7} ATTR={8}" -f $bi,$bcd,$(if($null-ne$bType){'0x'+([uint32]$bType).ToString('X8')}else{'NONE'}),$bName,$numContained,$mdisp,$pdisp,$vdisp,$attr))
            }
        }
    }
}

$lines.Add('')
$lines.Add('[VTABLE_COMPARISON]')
for($i=0;$i -lt $rows.Count;$i++){
    for($j=$i+1;$j -lt $rows.Count;$j++){
        $a=$rows[$i];$b=$rows[$j];$same=0;$prefix=0;$limitEntries=[Math]::Min($a.Entries.Length,$b.Entries.Length)
        for($k=0;$k -lt $limitEntries;$k++){if($a.Entries[$k]-eq$b.Entries[$k]){$same++;if($k-eq$prefix){$prefix++}}}
        $lines.Add(("PAIR={0}:{1} SAME_INDEX_ENTRIES={2}/{3} SHARED_PREFIX={4} TYPE_A={5} TYPE_B={6}" -f $a.Owner,$b.Owner,$same,$limitEntries,$prefix,$a.TypeName,$b.TypeName))
    }
}

$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add('STATUS=PASS_VTABLE_RTTI_PREPARED')
$lines.Add('RTTI_PROMOTION_RULE=Decorated type names and class hierarchy are accepted only when pointers remain inside authoritative Lin.bin2 MEM_IMAGE and structures are internally readable.')
$lines.Add('NO_CLASS_RELATION_INFERENCE_FROM_VTABLE_SPACING_ONLY=YES')
$lines.Add('HEAP_DEREFERENCE=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')

$parent=Split-Path -Parent $OutputPath
if($parent -and -not(Test-Path -LiteralPath $parent)){New-Item -ItemType Directory -Force -Path $parent|Out-Null}
[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host 'STATUS=PASS_VTABLE_RTTI_PREPARED'
Write-Host "OUTPUT=$OutputPath"
Write-Host 'HEAP_DEREFERENCE=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
