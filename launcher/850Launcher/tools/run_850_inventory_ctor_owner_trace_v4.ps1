param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_ctor_owner_trace_v4.txt",
    [int]$BackSearch = 0x240,
    [int]$ForwardSearch = 0x900,
    [int]$MaxInboundCallers = 256
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$Vtables = [ordered]@{
    GRID   = 0x00EDDE38
    ROOT   = 0x00EDE2F8
    INVWIN = 0x00EDE180
}
$ExpectedXrefs = [ordered]@{
    'INVWIN_A' = 0x0070135A
    'GRID_A'   = 0x007013DA
    'ROOT_A'   = 0x0070184E
    'INVWIN_B' = 0x00701C2B
    'GRID_B'   = 0x00701C8B
    'ROOT_B'   = 0x00701E6B
}

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
    throw 'Running authoritative Lin.bin2 process not found. This V4 is prepared for the next runtime session.'
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

public sealed class InvTraceRegion850 {
    public long Address;
    public long Size;
    public uint Protect;
    public uint Type;
    public byte[] Bytes;
}

public static class InvTraceMem850 {
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

    static bool Readable(uint p){
        if((p&PAGE_GUARD)!=0 || (p&PAGE_NOACCESS)!=0) return false;
        uint low=p&0xFF;
        return low==0x02 || low==0x04 || low==0x08 || low==0x10 || low==0x20 || low==0x40 || low==0x80;
    }
    static bool Executable(uint p){
        uint low=p&0xFF;
        return low==0x10 || low==0x20 || low==0x40 || low==0x80;
    }

    public static List<InvTraceRegion850> ReadImageRegions(int pid,long start,long end,bool execOnly){
        var outp=new List<InvTraceRegion850>();
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
                bool okRegion=z>a && m.State==MEM_COMMIT && m.Type==MEM_IMAGE && Readable(m.Protect) && (!execOnly || Executable(m.Protect));
                if(okRegion){
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
                            outp.Add(new InvTraceRegion850{Address=a+off,Size=got,Protect=m.Protect,Type=m.Type,Bytes=b});
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

$allImage=[InvTraceMem850]::ReadImageRegions($proc.Id,$base,$limit,$false)
$execImage=@($allImage | Where-Object { ($_.Protect -band 0xFF) -in @(0x10,0x20,0x40,0x80) })
if($execImage.Count -eq 0){ throw 'No executable MEM_IMAGE regions readable.' }

function Region-For([long]$addr,[object[]]$regions){
    foreach($r in $regions){ if($addr -ge [long]$r.Address -and $addr -lt ([long]$r.Address+[long]$r.Size)){ return $r } }
    return $null
}
function Byte-At([long]$addr){
    $r=Region-For $addr $allImage
    if($null -eq $r){ return $null }
    return [byte]$r.Bytes[[int]($addr-[long]$r.Address)]
}
function U32-At([long]$addr){
    $r=Region-For $addr $allImage
    if($null -eq $r){ return $null }
    $o=[int]($addr-[long]$r.Address)
    if($o+4 -gt $r.Bytes.Length){ return $null }
    return [BitConverter]::ToUInt32($r.Bytes,$o)
}
function I32-At([long]$addr){
    $r=Region-For $addr $allImage
    if($null -eq $r){ return $null }
    $o=[int]($addr-[long]$r.Address)
    if($o+4 -gt $r.Bytes.Length){ return $null }
    return [BitConverter]::ToInt32($r.Bytes,$o)
}
function Hex-At([long]$addr,[int]$count){
    $r=Region-For $addr $allImage
    if($null -eq $r){ return 'UNMAPPED' }
    $o=[int]($addr-[long]$r.Address)
    $n=[Math]::Min($count,$r.Bytes.Length-$o)
    if($n -le 0){ return '' }
    return (($r.Bytes[$o..($o+$n-1)] | ForEach-Object { $_.ToString('X2') }) -join ' ')
}
function Match-Bytes([long]$addr,[byte[]]$pattern){
    for($i=0;$i -lt $pattern.Length;$i++){
        $b=Byte-At ($addr+$i)
        if($null -eq $b -or $b -ne $pattern[$i]){ return $false }
    }
    return $true
}

function Find-Prologue([long]$center){
    $lo=[Math]::Max($base,$center-$BackSearch)
    $patterns=@(
        [byte[]](0x55,0x8B,0xEC),
        [byte[]](0x8B,0xFF,0x55,0x8B,0xEC)
    )
    for($a=$center;$a -ge $lo;$a--){
        foreach($p in $patterns){ if(Match-Bytes $a $p){ return $a } }
    }
    return 0L
}
function Find-Epilogue([long]$start,[long]$after){
    $hi=[Math]::Min($limit,$start+$ForwardSearch)
    for($a=[Math]::Max($start,$after);$a -lt $hi;$a++){
        $b=Byte-At $a
        if($null -eq $b){ continue }
        if($b -eq 0xC3){ return $a }
        if($b -eq 0xC2){
            $x=Byte-At ($a+1);$y=Byte-At ($a+2)
            if($null-ne$x -and $null-ne$y){ return ($a+2) }
        }
    }
    return 0L
}

function Find-VtableHits([string]$owner,[long]$rva){
    $va=[uint32]($base+$rva)
    $needle=[BitConverter]::GetBytes($va)
    $hits=New-Object System.Collections.Generic.List[long]
    foreach($r in $execImage){
        $b=$r.Bytes
        for($i=0;$i -le $b.Length-4;$i++){
            if($b[$i]-eq$needle[0] -and $b[$i+1]-eq$needle[1] -and $b[$i+2]-eq$needle[2] -and $b[$i+3]-eq$needle[3]){
                $hits.Add([long]$r.Address+$i)
            }
        }
    }
    return $hits
}

function Find-RawE8Candidates([long]$target){
    $hits=New-Object System.Collections.Generic.List[object]
    foreach($r in $execImage){
        $b=$r.Bytes
        for($i=0;$i+5 -le $b.Length;$i++){
            if($b[$i] -ne 0xE8){ continue }
            $at=[long]$r.Address+$i
            $rel=[BitConverter]::ToInt32($b,$i+1)
            $dest=$at+5+$rel
            if($dest -eq $target){
                $hits.Add([pscustomobject]@{At=$at;AtRva=$at-$base;Target=$target;TargetRva=$target-$base})
                if($hits.Count -ge $MaxInboundCallers){ return $hits }
            }
        }
    }
    return $hits
}

function Find-GlobalStores([long]$start,[long]$end){
    $hits=New-Object System.Collections.Generic.List[object]
    if($start -le 0 -or $end -le $start){ return $hits }
    for($a=$start;$a+10 -le $end;$a++){
        $b0=Byte-At $a
        if($null -eq $b0){ continue }
        # A3 abs32 : mov [abs],eax
        if($b0 -eq 0xA3){
            $dst=U32-At ($a+1)
            if($null-ne$dst -and $dst -ge $base -and $dst -lt $limit){
                $hits.Add([pscustomobject]@{At=$a;AtRva=$a-$base;Kind='A3';Dest=[long]$dst;DestRva=[long]$dst-$base})
            }
        }
        # 89 /r with modrm absolute forms 05/0D/15/1D/25/2D/35/3D
        if($b0 -eq 0x89){
            $m=Byte-At ($a+1)
            if($null-ne$m -and (($m -band 0xC7) -eq 0x05)){
                $dst=U32-At ($a+2)
                if($null-ne$dst -and $dst -ge $base -and $dst -lt $limit){
                    $hits.Add([pscustomobject]@{At=$a;AtRva=$a-$base;Kind=('89/{0:X2}' -f $m);Dest=[long]$dst;DestRva=[long]$dst-$base})
                }
            }
        }
        # C7 05 abs32 imm32 : mov dword ptr [abs],imm32
        if($b0 -eq 0xC7 -and (Byte-At ($a+1)) -eq 0x05){
            $dst=U32-At ($a+2)
            if($null-ne$dst -and $dst -ge $base -and $dst -lt $limit){
                $imm=U32-At ($a+6)
                $hits.Add([pscustomobject]@{At=$a;AtRva=$a-$base;Kind='C7/05';Dest=[long]$dst;DestRva=[long]$dst-$base;Imm=$imm})
            }
        }
    }
    return $hits
}

function Find-MemberWrites([long]$start,[long]$end){
    $hits=New-Object System.Collections.Generic.List[object]
    if($start -le 0 -or $end -le $start){ return $hits }
    for($a=$start;$a+10 -le $end;$a++){
        $op=Byte-At $a
        if($op -eq 0xC7){
            $m=Byte-At ($a+1)
            if($null-eq$m){ continue }
            $mod=($m -band 0xC0)
            $rm=($m -band 0x07)
            if($mod -eq 0x80 -and $rm -ne 0x04){
                $disp=U32-At ($a+2)
                $imm=U32-At ($a+6)
                if($null-ne$disp -and $disp -le 0x600){
                    $hits.Add([pscustomobject]@{At=$a;AtRva=$a-$base;Op='C7';ModRM=$m;Disp=$disp;Imm=$imm})
                }
            }
        }
        if($op -eq 0x89 -or $op -eq 0x8B){
            $m=Byte-At ($a+1)
            if($null-eq$m){ continue }
            $mod=($m -band 0xC0)
            $rm=($m -band 0x07)
            if($mod -eq 0x80 -and $rm -ne 0x04){
                $disp=U32-At ($a+2)
                if($null-ne$disp -and $disp -le 0x600){
                    $hits.Add([pscustomobject]@{At=$a;AtRva=$a-$base;Op=('0x{0:X2}' -f $op);ModRM=$m;Disp=$disp;Imm=$null})
                }
            }
        }
    }
    return $hits
}

$observed=New-Object System.Collections.Generic.List[object]
foreach($kv in $Vtables.GetEnumerator()){
    foreach($hit in (Find-VtableHits $kv.Key ([long]$kv.Value))){
        $observed.Add([pscustomobject]@{Owner=$kv.Key;Address=$hit;Rva=$hit-$base})
    }
}

$qualified=New-Object System.Collections.Generic.List[object]
$expectedCheck=New-Object System.Collections.Generic.List[object]
foreach($kv in $ExpectedXrefs.GetEnumerator()){
    $owner=($kv.Key -split '_')[0]
    $rva=[long]$kv.Value
    $found=@($observed | Where-Object { $_.Owner -eq $owner -and $_.Rva -eq $rva })
    $expectedCheck.Add([pscustomobject]@{Name=$kv.Key;Owner=$owner;Rva=$rva;Count=$found.Count})
    if($found.Count -ne 1){
        throw "Expected xref $($kv.Key) at RVA 0x$($rva.ToString('X8')) exactly once; found $($found.Count)."
    }
    $qualified.Add($found[0])
}

$analyses=New-Object System.Collections.Generic.List[object]
foreach($x in ($qualified | Sort-Object Rva)){
    $start=Find-Prologue $x.Address
    $end=if($start -gt 0){Find-Epilogue $start $x.Address}else{0L}
    $rawCallCandidates=if($start -gt 0){@(Find-RawE8Candidates $start)}else{@()}
    $stores=if($start -gt 0 -and $end -gt $start){@(Find-GlobalStores $start $end)}else{@()}
    $members=if($start -gt 0 -and $end -gt $start){@(Find-MemberWrites $start $end)}else{@()}
    $analyses.Add([pscustomobject]@{Xref=$x;Start=$start;End=$end;RawCallCandidates=$rawCallCandidates;Stores=$stores;Members=$members})
}

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_INVENTORY_CTOR_OWNER_TRACE_V4')
$lines.Add("CLIENT=$fullClient")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $base))
$lines.Add(("MODULE_SIZE=0x{0:X}" -f $size))
$lines.Add('RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE')
$lines.Add('SCAN_SCOPE=SIX_KNOWN_VTABLE_XREF_FUNCTION_CONTEXTS')
$lines.Add('VTABLE_LITERAL_EXTRA_HITS_ANALYZED=NO')
$lines.Add('CALL_ALIGNMENT_PROOF=NO')
$lines.Add('RAW_E8_CALLERS_PROMOTABLE=NO')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')

$lines.Add('[EXPECTED_XREF_CHECK]')
foreach($x in $expectedCheck){
    $lines.Add(("NAME={0} OWNER={1} RVA=0x{2:X8} COUNT={3} QUALIFIED={4}" -f $x.Name,$x.Owner,$x.Rva,$x.Count,[int]($x.Count -eq 1)))
}
$lines.Add("RAW_VTABLE_LITERAL_HITS=$($observed.Count)")
$lines.Add("QUALIFIED_EXPECTED_XREFS=$($qualified.Count)")

$lines.Add('')
$lines.Add('[FUNCTION_BOUNDARY_CANDIDATES]')
foreach($a in $analyses){
    $lines.Add(("OWNER={0} XREF_RVA=0x{1:X8} START_RVA={2} END_RVA={3} RAW_E8_CANDIDATES={4} GLOBAL_STORES={5} MEMBER_REFS={6}" -f
        $a.Xref.Owner,$a.Xref.Rva,
        ($(if($a.Start-gt0){'0x'+($a.Start-$base).ToString('X8')}else{'NONE'})),
        ($(if($a.End-gt0){'0x'+($a.End-$base).ToString('X8')}else{'NONE'})),
        $a.RawCallCandidates.Count,$a.Stores.Count,$a.Members.Count))
    if($a.Start -gt 0){ $lines.Add(("  START_BYTES={0}" -f (Hex-At $a.Start 48))) }
    if($a.End -gt 0){ $lines.Add(("  END_BYTES={0}" -f (Hex-At ([Math]::Max($a.Start,$a.End-24)) ([int]([Math]::Min(48,$a.End-[Math]::Max($a.Start,$a.End-24)+1))))) }
    foreach($m in ($a.Members | Sort-Object At | Select-Object -First 96)){
        $iv=if($null-ne$m.Imm){'0x'+([uint32]$m.Imm).ToString('X8')}else{'-'}
        $lines.Add(("  MEMBER RVA=0x{0:X8} OP={1} MODRM=0x{2:X2} DISP=0x{3:X3} IMM={4}" -f $m.AtRva,$m.Op,$m.ModRM,$m.Disp,$iv))
    }
    foreach($s in ($a.Stores | Select-Object -First 32)){
        $immText=if($s.PSObject.Properties['Imm'] -and $null-ne$s.Imm){'0x'+([uint32]$s.Imm).ToString('X8')}else{'-'}
        $lines.Add(("  GLOBAL_STORE RVA=0x{0:X8} KIND={1} DEST_RVA=0x{2:X8} IMM={3}" -f $s.AtRva,$s.Kind,$s.DestRva,$immText))
    }
    foreach($c in ($a.RawCallCandidates | Select-Object -First 32)){
        $lines.Add(("  RAW_E8_CANDIDATE RVA=0x{0:X8} -> 0x{1:X8} ALIGNMENT_PROOF=NO" -f $c.AtRva,$c.TargetRva))
    }
}

$lines.Add('')
$lines.Add('[FUNCTION_GROUPS]')
$groups=$analyses | Group-Object Start
foreach($g in $groups){
    if([long]$g.Name -le 0){ continue }
    $members=@($g.Group | Sort-Object {$_.Xref.Rva})
    $owners=($members | ForEach-Object {$_.Xref.Owner}) -join ','
    $xrefs=($members | ForEach-Object {'0x'+$_.Xref.Rva.ToString('X8')}) -join ','
    $start=[long]$members[0].Start
    $end=[long]$members[0].End
    $lines.Add(("START_RVA=0x{0:X8} END_RVA={1} OWNERS={2} XREFS={3} XREF_COUNT={4}" -f
        ($start-$base),($(if($end-gt0){'0x'+($end-$base).ToString('X8')}else{'NONE'})),$owners,$xrefs,$members.Count))
}

$lines.Add('')
$lines.Add('[UNVERIFIED_OWNER_STORE_CANDIDATES_FROM_RAW_E8]')
$lines.Add('PROMOTABLE=NO')
$seenStores=New-Object 'System.Collections.Generic.HashSet[string]'
foreach($a in $analyses){
    foreach($c in $a.RawCallCandidates){
        $callerStart=Find-Prologue $c.At
        if($callerStart -le 0){ continue }
        $callerEnd=Find-Epilogue $callerStart $c.At
        if($callerEnd -le $callerStart){ continue }
        foreach($s in (Find-GlobalStores $callerStart $callerEnd)){
            $key=('{0:X8}:{1:X8}:{2}' -f ($callerStart-$base),$s.DestRva,$a.Xref.Owner)
            if($seenStores.Add($key)){
                $lines.Add(("UNVERIFIED OWNER={0} CALLEE_START_RVA=0x{1:X8} RAW_E8_RVA=0x{2:X8} CALLER_START_RVA=0x{3:X8} STORE_RVA=0x{4:X8} DEST_RVA=0x{5:X8} KIND={6}" -f
                    $a.Xref.Owner,($a.Start-$base),$c.AtRva,($callerStart-$base),$s.AtRva,$s.DestRva,$s.Kind))
            }
        }
    }
}

$lines.Add('')
$lines.Add('[INFERENCE_GATES]')
$lines.Add('GATE_A=Analyze only the six previously established owner/xref pairs. Extra same-value literal hits are diagnostic only.')
$lines.Add('GATE_B=If INVWIN_A and GRID_A share START_RVA, treat them as same function context; do not call them independent constructors.')
$lines.Add('GATE_C=Raw byte E8 matches are not direct-call proof. Never promote caller-derived global stores until instruction-boundary alignment is independently proven.')
$lines.Add('GATE_D=Prefer exact absolute global xrefs from V4b and global stores inside the six qualified function contexts over raw-E8 caller candidates.')
$lines.Add('GATE_E=Do not promote GRID+1D8..1F8 vector semantics from zero initialization alone.')
$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add('STATUS=PASS_TARGETED_TRACE_PREPARED')
$lines.Add("RAW_VTABLE_LITERAL_HITS=$($observed.Count)")
$lines.Add("QUALIFIED_EXPECTED_XREFS=$($qualified.Count)")
$lines.Add("FUNCTION_GROUPS=$(@($groups | Where-Object {[long]$_.Name -gt 0}).Count)")
$lines.Add('CALL_ALIGNMENT_PROOF=NO')
$lines.Add('RAW_E8_CALLERS_PROMOTABLE=NO')
$lines.Add('WP5=NOT_YET')
$lines.Add('WP6=NOT_YET')
$lines.Add('NEXT=Use six-xref function groups plus V4b exact global refs to choose the next narrow owner hypothesis. Do not use raw E8 callers for promotion.')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')

$parent=Split-Path -Parent $OutputPath
if($parent -and -not (Test-Path -LiteralPath $parent)){ New-Item -ItemType Directory -Force -Path $parent | Out-Null }
[IO.File]::WriteAllLines($OutputPath,$lines,[Text.UTF8Encoding]::new($false))
Write-Host 'STATUS=PASS_TARGETED_TRACE_PREPARED'
Write-Host "OUTPUT=$OutputPath"
Write-Host 'CALL_ALIGNMENT_PROOF=NO'
Write-Host 'RAW_E8_CALLERS_PROMOTABLE=NO'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'VECTOR_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
