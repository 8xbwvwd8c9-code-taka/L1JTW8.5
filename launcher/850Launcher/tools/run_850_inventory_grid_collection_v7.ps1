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
$Offsets = @(0x1D8L,0x1DCL,0x1E0L,0x1E4L,0x1E8L,0x1ECL,0x1F0L,0x1F4L,0x1F8L)

$gatePath = Join-Path $OutputDir '850_inventory_root_graph_restart_gate_v6b.txt'
$outPath = Join-Path $OutputDir '850_inventory_grid_collection_v7.txt'

function Read-KvFile([string]$Path) {
    $m = @{}
    foreach ($line in Get-Content -LiteralPath $Path -ErrorAction Stop) {
        $i = $line.IndexOf('=')
        if ($i -le 0) { continue }
        $m[$line.Substring(0,$i).Trim()] = $line.Substring($i+1).Trim()
    }
    return $m
}

if (-not (Test-Path -LiteralPath $gatePath)) { throw "V6b gate missing: $gatePath" }
$gate = Read-KvFile $gatePath
if ($gate['CLIENT_SHA256'] -ne $ExpectedSha256 -or $gate['CLIENT_AUTHORITY'] -ne '1' -or $gate['STATUS'] -ne 'PASS_ROOT_OWNER_RESTART_STABLE' -or $gate['OWNER_ANCHOR'] -ne 'PASS_RESTART_STABLE') {
    throw 'V7 blocked: V6b restart-stable owner gate has not passed.'
}

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$full = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$proc = $null
foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
    try {
        if ($p.HasExited) { continue }
        if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName), $full, [StringComparison]::OrdinalIgnoreCase)) { $proc = $p; break }
    } catch { }
}
if ($null -eq $proc) { throw 'Running authoritative Lin.bin2 process not found.' }
$module = $proc.MainModule
if (-not $module) { throw 'MainModule unavailable; run PowerShell elevated if required.' }
$base = [long]$module.BaseAddress
$processStartUtc = $proc.StartTime.ToUniversalTime().ToString('o')

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class V7Read850 {
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
            return BitConverter.ToUInt32(b, 0);
        } finally { CloseHandle(h); }
    }
}
"@

function Read-U32([long]$Address) { return [uint32][V7Read850]::ReadU32($proc.Id,$Address) }
function H([uint32]$v) { return ('0x{0:X8}' -f [uint64]$v) }

$rootGlobalVa = $base + $RootGlobalRva
$root = Read-U32 $rootGlobalVa
if ($root -eq 0) { throw 'ROOT_GLOBAL is zero.' }
$rootVt = Read-U32 ([long]$root)
$grid = Read-U32 ([long]$root + $RootGridOffset)
$invwin = Read-U32 ([long]$root + $RootInvWinOffset)
if ($grid -eq 0 -or $invwin -eq 0) { throw 'ROOT graph child pointer is zero.' }
$gridVt = Read-U32 ([long]$grid)
$gridParent = Read-U32 ([long]$grid + $GridParentOffset)
$invVt = Read-U32 ([long]$invwin)

if ($rootVt -ne [uint32]($base + $RootVtableRva) -or $gridVt -ne [uint32]($base + $GridVtableRva) -or $invVt -ne [uint32]($base + $InvWinVtableRva) -or $gridParent -ne $root) {
    throw 'V7 live ROOT/GRID/INVWIN graph revalidation failed.'
}

$vals = @{}
foreach ($o in $Offsets) { $vals[$o] = Read-U32 ([long]$grid + $o) }

function Triple-Class([uint32]$a,[uint32]$b,[uint32]$c) {
    if ($a -eq 0 -and $b -eq 0 -and $c -eq 0) { return 'ZERO_TRIPLE' }
    if ($a -ne 0 -and $b -ne 0 -and $c -ne 0 -and [uint64]$a -le [uint64]$b -and [uint64]$b -le [uint64]$c) {
        $used = [uint64]$b - [uint64]$a
        $cap = [uint64]$c - [uint64]$a
        if (($used % 4) -eq 0 -and ($cap % 4) -eq 0) { return 'MONOTONIC_ALIGNED_TRIPLE' }
        return 'MONOTONIC_TRIPLE'
    }
    return 'NONVECTOR_SHAPE'
}

$groups = @(
    [pscustomobject]@{Name='A'; A=0x1D8L; B=0x1DCL; C=0x1E0L},
    [pscustomobject]@{Name='B'; A=0x1E4L; B=0x1E8L; C=0x1ECL},
    [pscustomobject]@{Name='C'; A=0x1F0L; B=0x1F4L; C=0x1F8L}
)

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_GRID_COLLECTION_V7')
$lines.Add("CLIENT=$full")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$processStartUtc")
$lines.Add(('MODULE_BASE=0x{0:X8}' -f $base))
$lines.Add('V6B_GATE=PASS_RESTART_STABLE')
$lines.Add("ROOT_OBJECT=$(H $root)")
$lines.Add("GRID_OBJECT=$(H $grid)")
$lines.Add("INVWIN_OBJECT=$(H $invwin)")
$lines.Add('ROOT_GRAPH_REVALIDATED=PASS')
$lines.Add('READ_DWORD_COUNT_COLLECTION=9')
$lines.Add('EXACT_TARGET_DEREFERENCE=YES')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_WIDE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[GRID_FIXED_OFFSETS]')
foreach ($o in $Offsets) { $lines.Add(('OFFSET=0x{0:X3} VALUE={1}' -f $o,(H $vals[$o]))) }
$lines.Add('')
$lines.Add('[TRIPLES]')
foreach ($g in $groups) {
    $a=[uint32]$vals[$g.A]; $b=[uint32]$vals[$g.B]; $c=[uint32]$vals[$g.C]
    $class = Triple-Class $a $b $c
    $used = if ([uint64]$b -ge [uint64]$a) { [uint64]$b - [uint64]$a } else { 0 }
    $cap = if ([uint64]$c -ge [uint64]$a) { [uint64]$c - [uint64]$a } else { 0 }
    $lines.Add("GROUP=$($g.Name) OFFSETS=0x$('{0:X3}' -f $g.A),0x$('{0:X3}' -f $g.B),0x$('{0:X3}' -f $g.C) BEGIN=$(H $a) END=$(H $b) CAP=$(H $c) USED_BYTES=$used CAP_BYTES=$cap CLASS=$class")
}
$lines.Add('')
$lines.Add('[DECISION]')
$lines.Add('STATUS=PASS_BOUNDED_GRID_COLLECTION_READ')
$lines.Add('COLLECTION_LAYOUT_PROVEN=NO')
$lines.Add('FORMAL_WP5=NOT_YET')
$lines.Add('FORMAL_WP6=NOT_YET')
$lines.Add('NEXT=Compare the same nine fixed offsets across controlled inventory changes and fresh sessions; promote only a restart-stable collection-shaped triple with semantic item correlation.')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_WIDE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
[IO.File]::WriteAllText($outPath, ($lines -join [Environment]::NewLine) + [Environment]::NewLine, [Text.UTF8Encoding]::new($false))
$lines | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$outPath"
