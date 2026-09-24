param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs'
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$RootGlobalRva = 0x012BCEE8L
$RootVtableRva = 0x00EDE2F8L
$GridVtableRva = 0x00EDDE38L
$InvWinVtableRva = 0x00EDE180L
$RootGridOffset = 0x15CL
$RootInvWinOffset = 0x168L
$GridParentOffset = 0xECL
$GatePath = Join-Path $OutputDir '850_inventory_root_graph_restart_gate_v6b.txt'
$OutPath = Join-Path $OutputDir '850_inventory_invwin_child_objects_v13.txt'
$ChildOffsets = [ordered]@{
    CHILD_148 = 0x148L
    CHILD_14C = 0x14CL
    CHILD_150 = 0x150L
    CHILD_154 = 0x154L
}
$VtableEntryCount = 16

function Read-KvFile([string]$Path) {
    $m = @{}
    foreach ($line in Get-Content -LiteralPath $Path -ErrorAction Stop) {
        $i = $line.IndexOf('=')
        if ($i -le 0) { continue }
        $m[$line.Substring(0,$i).Trim()] = $line.Substring($i+1).Trim()
    }
    return $m
}
function Hex32([uint32]$Value) { return ('0x{0:X8}' -f [uint64]$Value) }

if (-not (Test-Path -LiteralPath $GatePath)) { throw "V6b gate missing: $GatePath" }
$gate = Read-KvFile $GatePath
if ($gate['CLIENT_SHA256'] -ne $ExpectedSha256 -or $gate['CLIENT_AUTHORITY'] -ne '1' -or $gate['STATUS'] -ne 'PASS_ROOT_OWNER_RESTART_STABLE' -or $gate['OWNER_ANCHOR'] -ne 'PASS_RESTART_STABLE') {
    throw 'V13 blocked: V6b restart-stable owner gate has not passed.'
}

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$full = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$proc = $null
foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
    try {
        if ($p.HasExited) { continue }
        if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)) { $proc=$p; break }
    } catch { }
}
if ($null -eq $proc) { throw 'Running authoritative Lin.bin2 process not found.' }
$module = $proc.MainModule
if (-not $module) { throw 'MainModule unavailable; run PowerShell elevated if required.' }
$base = [long]$module.BaseAddress
$size = [long]$module.ModuleMemorySize
$limit = $base + $size
$processStartUtc = $proc.StartTime.ToUniversalTime().ToString('o')

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class V13Read850 {
    const uint PROCESS_VM_READ = 0x0010;
    const uint PROCESS_QUERY_INFORMATION = 0x0400;
    [DllImport("kernel32.dll", SetLastError=true)] static extern IntPtr OpenProcess(uint a, bool i, int p);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h, IntPtr a, byte[] b, int s, out IntPtr r);
    public static uint ReadU32(int pid, long address) {
        IntPtr h = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
        if (h == IntPtr.Zero) throw new Exception("OpenProcess failed Win32=" + Marshal.GetLastWin32Error());
        try {
            byte[] b = new byte[4]; IntPtr r;
            bool ok = ReadProcessMemory(h, new IntPtr(address), b, 4, out r);
            if (!ok || r.ToInt64() != 4) throw new Exception("ReadProcessMemory failed/short at 0x" + address.ToString("X"));
            return BitConverter.ToUInt32(b,0);
        } finally { CloseHandle(h); }
    }
}
"@
function Read-U32([long]$Address) { return [uint32][V13Read850]::ReadU32($proc.Id,$Address) }

# Frozen graph revalidation.
$root = Read-U32 ($base + $RootGlobalRva)
if ($root -eq 0) { throw 'ROOT_GLOBAL is zero.' }
$rootVt = Read-U32 ([long]$root)
$grid = Read-U32 ([long]$root + $RootGridOffset)
$invwin = Read-U32 ([long]$root + $RootInvWinOffset)
if ($grid -eq 0 -or $invwin -eq 0) { throw 'ROOT graph child pointer is zero.' }
$gridVt = Read-U32 ([long]$grid)
$gridParent = Read-U32 ([long]$grid + $GridParentOffset)
$invVt = Read-U32 ([long]$invwin)
if ($rootVt -ne [uint32]($base+$RootVtableRva) -or $gridVt -ne [uint32]($base+$GridVtableRva) -or $invVt -ne [uint32]($base+$InvWinVtableRva) -or $gridParent -ne $root) {
    throw 'V13 live ROOT/GRID/INVWIN graph revalidation failed.'
}

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_INVWIN_CHILD_OBJECTS_V13')
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$processStartUtc")
$lines.Add(('MODULE_BASE=0x{0:X8}' -f $base))
$lines.Add(('MODULE_SIZE=0x{0:X}' -f $size))
$lines.Add("ROOT_OBJECT=$(Hex32 $root)")
$lines.Add("GRID_OBJECT=$(Hex32 $grid)")
$lines.Add("INVWIN_OBJECT=$(Hex32 $invwin)")
$lines.Add('ROOT_GRAPH_REVALIDATED=PASS')
$lines.Add('TARGET_OFFSETS=0x148,0x14C,0x150,0x154')
$lines.Add("VTABLE_ENTRY_LIMIT=$VtableEntryCount")
$lines.Add('EXACT_TARGET_DEREFERENCE=YES')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_WIDE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')

foreach ($kv in $ChildOffsets.GetEnumerator()) {
    $name = [string]$kv.Key
    $off = [long]$kv.Value
    $lines.Add('')
    $lines.Add("[$name]")
    $ptr = Read-U32 ([long]$invwin + $off)
    $lines.Add(('OFFSET=0x{0:X}' -f $off))
    $lines.Add("OBJECT=$(Hex32 $ptr)")
    if ($ptr -eq 0) {
        $lines.Add('OBJECT_CLASS=NULL')
        continue
    }

    try {
        $vt = Read-U32 ([long]$ptr)
        $lines.Add("VTABLE=$(Hex32 $vt)")
        if ([long][uint64]$vt -ge $base -and [long][uint64]$vt -lt $limit) {
            $lines.Add('VTABLE_CLASS=MODULE_IMAGE_ADDRESS')
            for ($i=0; $i -lt $VtableEntryCount; $i++) {
                try {
                    $fn = Read-U32 ([long][uint64]$vt + (4L*$i))
                    $fnClass = if ([long][uint64]$fn -ge $base -and [long][uint64]$fn -lt $limit) { 'MODULE' } else { 'OUTSIDE_MODULE' }
                    $rva = if ($fnClass -eq 'MODULE') { [long][uint64]$fn - $base } else { -1 }
                    if ($rva -ge 0) {
                        $lines.Add(('VTABLE_SLOT[{0:D2}]=VA:{1} RVA:0x{2:X8} CLASS:{3}' -f $i,(Hex32 $fn),$rva,$fnClass))
                    } else {
                        $lines.Add(('VTABLE_SLOT[{0:D2}]=VA:{1} CLASS:{2}' -f $i,(Hex32 $fn),$fnClass))
                    }
                } catch {
                    $lines.Add(('VTABLE_SLOT[{0:D2}]=READ_FAILED' -f $i))
                }
            }
        } else {
            $lines.Add('VTABLE_CLASS=OUTSIDE_MODULE')
        }
    } catch {
        $lines.Add('VTABLE=READ_FAILED')
        $lines.Add(('READ_ERROR=' + $_.Exception.Message))
    }
}

$lines.Add('')
$lines.Add('[DECISION]')
$lines.Add('STATUS=PASS_BOUNDED_CHILD_OBJECT_CAPTURE')
$lines.Add('FORMAL_WP5=NOT_YET')
$lines.Add('FORMAL_WP6=NOT_YET')
$lines.Add('NEXT=Classify only captured child vtables/method RVAs and their immediate helper semantics; do not widen scanning.')

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
[IO.File]::WriteAllText($OutPath,($lines -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))
$lines | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutPath"
