param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_wp7_live_send_iat_v20.txt",
    [uint32]$SendIatRva = 0x00EA5898,
    [int]$MaxXrefs = 256,
    [int]$MaxExactTargets = 3
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'

if(-not (Test-Path -LiteralPath $ClientPath)){ throw "Client not found: $ClientPath" }
$fullClient = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $fullClient -Algorithm SHA256).Hash.ToUpperInvariant()
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
    throw 'Running authoritative Lin.bin2 process not found. Start/login client first.'
}

$proc = Resolve-AuthoritativeProcess $fullClient
$module = $proc.MainModule
if(-not $module){ throw 'MainModule unavailable; run PowerShell elevated if required.' }
$base = [long]$module.BaseAddress
$size = [long]$module.ModuleMemorySize
$limit = $base + $size
if([long]$SendIatRva -ge $size){ throw ('Send IAT RVA 0x{0:X8} exceeds module size 0x{1:X}.' -f $SendIatRva,$size) }

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;

public sealed class Wp7ExecRegion850 {
    public long Address;
    public long Size;
    public uint Protect;
    public byte[] Bytes;
}

public static class Wp7LiveIat850 {
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
    [DllImport("kernel32.dll",CharSet=CharSet.Ansi,SetLastError=true)] static extern IntPtr LoadLibrary(string n);
    [DllImport("kernel32.dll",CharSet=CharSet.Ansi,SetLastError=true)] static extern IntPtr GetProcAddress(IntPtr h,string n);

    static bool IsExec(uint p){
        if((p&PAGE_GUARD)!=0 || (p&PAGE_NOACCESS)!=0) return false;
        uint low=p&0xFF;
        return low==0x10 || low==0x20 || low==0x40 || low==0x80;
    }

    public static byte[] ReadExact(int pid,long address,int size){
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try{
            byte[] b=new byte[size]; IntPtr got;
            if(!ReadProcessMemory(h,new IntPtr(address),b,size,out got) || got.ToInt64()!=size)
                throw new Exception("ReadProcessMemory failed/short @0x"+address.ToString("X")+" Win32="+Marshal.GetLastWin32Error());
            return b;
        }finally{ CloseHandle(h); }
    }

    public static long ResolveLocalExportRva(string dll,string name){
        IntPtr h=LoadLibrary(dll);
        if(h==IntPtr.Zero) throw new Exception("LoadLibrary failed: "+dll);
        IntPtr p=GetProcAddress(h,name);
        if(p==IntPtr.Zero) throw new Exception("GetProcAddress failed: "+dll+"!"+name);
        return p.ToInt64()-h.ToInt64();
    }

    public static List<Wp7ExecRegion850> ReadExecImageRegions(int pid,long start,long end){
        var outp=new List<Wp7ExecRegion850>();
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
                            outp.Add(new Wp7ExecRegion850{Address=a+off,Size=got,Protect=m.Protect,Bytes=b});
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

function Find-ContainingRegion([object[]]$regions,[long]$address){
    foreach($r in $regions){
        if($address -ge [long]$r.Address -and $address -lt ([long]$r.Address+[long]$r.Size)){ return $r }
    }
    return $null
}

function Find-FunctionStart([object[]]$regions,[long]$instruction,[int]$maxBack=0x500){
    $r=Find-ContainingRegion $regions $instruction
    if(-not $r){ return $null }
    $idx=[int]($instruction-[long]$r.Address)
    $min=[Math]::Max(0,$idx-$maxBack)
    $b=$r.Bytes
    for($i=$idx;$i -ge $min;$i--){
        if($i+2 -lt $b.Length -and $b[$i] -eq 0x55 -and $b[$i+1] -eq 0x8B -and $b[$i+2] -eq 0xEC){ return [long]$r.Address+$i }
        if($i+4 -lt $b.Length -and $b[$i] -eq 0x8B -and $b[$i+1] -eq 0xFF -and $b[$i+2] -eq 0x55 -and $b[$i+3] -eq 0x8B -and $b[$i+4] -eq 0xEC){ return [long]$r.Address+$i }
    }
    for($i=$idx-1;$i -ge $min;$i--){
        if($b[$i] -eq 0xCC){
            $n=$i+1
            while($n -lt $idx -and $b[$n] -eq 0xCC){$n++}
            if($n -lt $idx){return [long]$r.Address+$n}
        }
        if($b[$i] -eq 0xC3){return [long]$r.Address+$i+1}
        if($b[$i] -eq 0xC2 -and $i+2 -lt $b.Length){return [long]$r.Address+$i+3}
    }
    return $null
}

function Test-Strong5E([object[]]$regions,[long]$function,[int]$window=0x800){
    $r=Find-ContainingRegion $regions $function
    if(-not $r){return $false}
    $start=[int]($function-[long]$r.Address)
    $end=[Math]::Min($r.Bytes.Length,$start+$window)
    for($i=$start;$i -lt $end-1;$i++){
        # Ranking hint only: common immediate forms push 0x5E / mov r32,0x5E / cmp imm8.
        if($r.Bytes[$i] -eq 0x6A -and $r.Bytes[$i+1] -eq 0x5E){return $true}
        if($r.Bytes[$i] -ge 0xB8 -and $r.Bytes[$i] -le 0xBF -and $i+4 -lt $end -and [BitConverter]::ToUInt32($r.Bytes,$i+1) -eq 0x5E){return $true}
        if($r.Bytes[$i] -eq 0x83 -and $i+2 -lt $end -and $r.Bytes[$i+2] -eq 0x5E){return $true}
    }
    return $false
}

function Find-DirectCallers([object[]]$regions,[long]$target,[int]$maxHits=256){
    $out=New-Object System.Collections.Generic.List[object]
    foreach($r in $regions){
        $b=$r.Bytes
        for($i=0;$i+5 -le $b.Length;$i++){
            if($b[$i] -ne 0xE8){continue}
            $at=[long]$r.Address+$i
            $rel=[BitConverter]::ToInt32($b,$i+1)
            if(($at+5+$rel) -ne $target){continue}
            $owner=Find-FunctionStart $regions $at
            $out.Add([pscustomobject]@{CallVa=$at;CallRva=$at-$base;OwnerVa=$owner;OwnerRva=if($owner){$owner-$base}else{$null}})
            if($out.Count -ge $maxHits){return $out}
        }
    }
    return $out
}

$iatVa=$base+[long]$SendIatRva
$slot=[Wp7LiveIat850]::ReadExact($proc.Id,$iatVa,4)
$sendTarget=[uint32][BitConverter]::ToUInt32($slot,0)
if($sendTarget -eq 0){throw 'Live send IAT slot is NULL.'}

$remoteWs=$null
try{$remoteWs=@($proc.Modules | Where-Object {$_.ModuleName -ieq 'ws2_32.dll'} | Select-Object -First 1)[0]}catch{}
$sendExportRva=[Wp7LiveIat850]::ResolveLocalExportRva('ws2_32.dll','send')
$expectedRemoteSend=$null
$sendTargetVerified=$false
if($remoteWs){
    $expectedRemoteSend=[long]$remoteWs.BaseAddress+$sendExportRva
    $sendTargetVerified=([uint32]$expectedRemoteSend -eq $sendTarget)
}

$regions=[Wp7LiveIat850]::ReadExecImageRegions($proc.Id,$base,$limit)
if($regions.Count -eq 0){throw 'No executable MEM_IMAGE regions readable inside Lin.bin2.'}

$iatBytes=[BitConverter]::GetBytes([uint32]$iatVa)
$xrefs=New-Object System.Collections.Generic.List[object]
foreach($r in $regions){
    $b=$r.Bytes
    for($i=0;$i+6 -le $b.Length;$i++){
        if($b[$i] -ne 0xFF -or ($b[$i+1] -ne 0x15 -and $b[$i+1] -ne 0x25)){continue}
        if($b[$i+2] -ne $iatBytes[0] -or $b[$i+3] -ne $iatBytes[1] -or $b[$i+4] -ne $iatBytes[2] -or $b[$i+5] -ne $iatBytes[3]){continue}
        $va=[long]$r.Address+$i
        $owner=Find-FunctionStart $regions $va
        $xrefs.Add([pscustomobject]@{
            Va=$va;Rva=$va-$base;Kind=if($b[$i+1]-eq 0x15){'CALL [IAT]'}else{'JMP [IAT]'};
            OwnerVa=$owner;OwnerRva=if($owner){$owner-$base}else{$null}
        })
        if($xrefs.Count -ge $MaxXrefs){break}
    }
    if($xrefs.Count -ge $MaxXrefs){break}
}

$candidates=New-Object System.Collections.Generic.List[object]
$seen=New-Object 'System.Collections.Generic.HashSet[string]'
foreach($x in $xrefs){
    if(-not $x.OwnerVa){continue}
    $key=('0:{0:X8}' -f [long]$x.OwnerRva)
    if($seen.Add($key)){
        $candidates.Add([pscustomobject]@{Depth=0;FunctionVa=[long]$x.OwnerVa;FunctionRva=[long]$x.OwnerRva;TriggerRva=[long]$x.Rva;Strong5E=(Test-Strong5E $regions ([long]$x.OwnerVa));Source=$x.Kind})
    }
    foreach($c in (Find-DirectCallers $regions ([long]$x.OwnerVa)){
        if(-not $c.OwnerVa){continue}
        $key=('1:{0:X8}' -f [long]$c.OwnerRva)
        if($seen.Add($key)){
            $candidates.Add([pscustomobject]@{Depth=1;FunctionVa=[long]$c.OwnerVa;FunctionRva=[long]$c.OwnerRva;TriggerRva=[long]$c.CallRva;Strong5E=(Test-Strong5E $regions ([long]$c.OwnerVa));Source=('CALL->0x{0:X8}' -f [long]$x.OwnerRva)})
        }
    }
}

$ranked=@($candidates | Sort-Object @{Expression='Strong5E';Descending=$true},@{Expression='Depth';Descending=$true},FunctionRva)
$targets=@($ranked | Select-Object -First $MaxExactTargets)

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_WP7_LIVE_SEND_IAT_V20')
$lines.Add("CLIENT=$fullClient")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add(('MODULE_BASE=0x{0:X8}' -f $base))
$lines.Add(('MODULE_SIZE=0x{0:X}' -f $size))
$lines.Add(('SEND_IAT_RVA=0x{0:X8}' -f $SendIatRva))
$lines.Add(('SEND_IAT_VA=0x{0:X8}' -f $iatVa))
$lines.Add(('SEND_TARGET_VA=0x{0:X8}' -f $sendTarget))
$lines.Add(('REMOTE_WS2_32_BASE={0}' -f $(if($remoteWs){'0x{0:X8}' -f [long]$remoteWs.BaseAddress}else{'UNKNOWN'})))
$lines.Add(('LOCAL_SEND_EXPORT_RVA=0x{0:X8}' -f $sendExportRva))
$lines.Add(('EXPECTED_REMOTE_SEND={0}' -f $(if($expectedRemoteSend){'0x{0:X8}' -f $expectedRemoteSend}else{'UNKNOWN'})))
$lines.Add(('SEND_TARGET_EXPORT_MATCH={0}' -f $(if($sendTargetVerified){1}else{0})))
$lines.Add('SCAN_SCOPE=LIN.BIN2_EXECUTABLE_MEM_IMAGE_ONLY')
$lines.Add('CALLER_DEPTH=1')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('PACKET_SEND=NO')
$lines.Add('ITEM_SPECIFIC_ACTION_PROVEN=NO')
$lines.Add('OBJECT_ID_PROVEN=NO')
$lines.Add('WP7_NATIVE_USEITEM_PASS=NO')
$lines.Add('')
$lines.Add('[IAT_XREFS]')
$lines.Add("XREF_COUNT=$($xrefs.Count)")
foreach($x in $xrefs){
    $lines.Add(('XREF RVA=0x{0:X8} VA=0x{1:X8} KIND={2} OWNER_RVA={3}' -f [long]$x.Rva,[long]$x.Va,$x.Kind,$(if($x.OwnerRva -ne $null){'0x{0:X8}' -f [long]$x.OwnerRva}else{'UNKNOWN'})))
}
$lines.Add('')
$lines.Add('[DEPTH1_CANDIDATES]')
$lines.Add("CANDIDATE_COUNT=$($candidates.Count)")
foreach($c in $ranked){
    $lines.Add(('CANDIDATE DEPTH={0} FUNCTION_RVA=0x{1:X8} TRIGGER_RVA=0x{2:X8} STRONG_0x5E={3} SOURCE={4}' -f $c.Depth,[long]$c.FunctionRva,[long]$c.TriggerRva,$(if($c.Strong5E){1}else{0}),$c.Source))
}
$lines.Add('')
$lines.Add('[EXACT_TARGETS]')
$i=0
foreach($t in $targets){
    $i++
    $lines.Add(('EXACT_TARGET={0} FUNCTION_RVA=0x{1:X8} TRIGGER_RVA=0x{2:X8} DEPTH={3} STRONG_0x5E={4} SOURCE={5}' -f $i,[long]$t.FunctionRva,[long]$t.TriggerRva,$t.Depth,$(if($t.Strong5E){1}else{0}),$t.Source))
}
$lines.Add('')
$lines.Add('[SUMMARY]')
if(-not $remoteWs){
    $lines.Add('STATUS=PARTIAL_WS2_32_MODULE_NOT_ENUMERATED')
}elseif(-not $sendTargetVerified){
    $lines.Add('STATUS=BLOCKED_SEND_IAT_TARGET_MISMATCH')
}elseif($xrefs.Count -eq 0){
    $lines.Add('STATUS=PARTIAL_SEND_IAT_PROVEN_NO_XREF')
}else{
    $lines.Add('STATUS=PASS_LIVE_SEND_IAT_XREF_CANDIDATES')
}
$lines.Add('LIVE_IAT_RESTART_STABLE=NO')
$lines.Add('CONTROLLED_MANUAL_POTION_CORRELATION=NO')
$lines.Add('NEXT=Restart-stability check first; then no-action -> one manual potion -> no-action correlation only at EXACT_TARGET RVAs.')

$outDir=Split-Path -Parent $OutputPath
if($outDir){New-Item -ItemType Directory -Force -Path $outDir | Out-Null}
$lines | Set-Content -LiteralPath $OutputPath -Encoding UTF8
$lines | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
