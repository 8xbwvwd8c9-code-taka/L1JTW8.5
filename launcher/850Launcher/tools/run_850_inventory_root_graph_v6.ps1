param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputPath = 'I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_root_graph_v6.txt'
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
if (-not $module) { throw 'MainModule unavailable; run PowerShell elevated if required.' }
$base = [long]$module.BaseAddress
$processStartUtc = $proc.StartTime.ToUniversalTime().ToString('o')

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;

public static class ExactRootRead850 {
    const uint PROCESS_VM_READ = 0x0010;
    const uint PROCESS_QUERY_INFORMATION = 0x0400;

    [DllImport("kernel32.dll", SetLastError=true)] static extern IntPtr OpenProcess(uint access, bool inherit, int pid);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h, IntPtr address, byte[] buffer, int size, out IntPtr read);

    public static uint ReadU32(int pid, long address) {
        IntPtr h = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
        if (h == IntPtr.Zero) throw new Exception("OpenProcess failed Win32=" + Marshal.GetLastWin32Error());
        try {
            byte[] b = new byte[4];
            IntPtr read;
            bool ok = ReadProcessMemory(h, new IntPtr(address), b, 4, out read);
            int got = ok ? (int)read.ToInt64() : 0;
            if (!ok || got != 4) throw new Exception("ReadProcessMemory failed/short at 0x" + address.ToString("X") + " got=" + got + " Win32=" + Marshal.GetLastWin32Error());
            return BitConverter.ToUInt32(b, 0);
        } finally {
            CloseHandle(h);
        }
    }
}
"@

function Read-U32([long]$Address) {
    return [uint32][ExactRootRead850]::ReadU32($proc.Id, $Address)
}

function Hex32([uint32]$Value) {
    return ('0x{0:X8}' -f [uint64]$Value)
}

$rootGlobalVa = $base + $RootGlobalRva
$expectedRootVt = [uint32]($base + $RootVtableRva)
$expectedGridVt = [uint32]($base + $GridVtableRva)
$expectedInvWinVt = [uint32]($base + $InvWinVtableRva)

$root = Read-U32 $rootGlobalVa
$rootVt = 0
$grid = 0
$invwin = 0
$gridVt = 0
$invwinVt = 0
$gridParent = 0

if ($root -ne 0) {
    $rootVt = Read-U32 ([long]$root)
    $grid = Read-U32 ([long]$root + $RootGridOffset)
    $invwin = Read-U32 ([long]$root + $RootInvWinOffset)
}
if ($grid -ne 0) {
    $gridVt = Read-U32 ([long]$grid)
    $gridParent = Read-U32 ([long]$grid + $GridParentOffset)
}
if ($invwin -ne 0) {
    $invwinVt = Read-U32 ([long]$invwin)
}

$rootVtPass = ($root -ne 0 -and $rootVt -eq $expectedRootVt)
$gridPass = ($grid -ne 0 -and $gridVt -eq $expectedGridVt -and $gridParent -eq $root)
$invwinPass = ($invwin -ne 0 -and $invwinVt -eq $expectedInvWinVt)
$graphPass = ($rootVtPass -and $gridPass -and $invwinPass)

$status = if ($graphPass) { 'PASS_ROOT_GRAPH_EXACT_OFFSETS' } elseif ($root -eq 0) { 'ROOT_GLOBAL_ZERO' } else { 'PARTIAL_ROOT_GRAPH' }

$lines = @(
    'MODE=850_INVENTORY_ROOT_GRAPH_V6',
    "CLIENT=$full",
    "CLIENT_SHA256=$sha",
    'CLIENT_AUTHORITY=1',
    "PID=$($proc.Id)",
    "PROCESS_START_UTC=$processStartUtc",
    ('MODULE_BASE=0x{0:X8}' -f $base),
    ('ROOT_GLOBAL_RVA=0x{0:X8}' -f $RootGlobalRva),
    ('ROOT_GLOBAL_VA=0x{0:X8}' -f $rootGlobalVa),
    "ROOT_OBJECT=$(Hex32 $root)",
    "ROOT_VTABLE=$(Hex32 $rootVt)",
    "EXPECTED_ROOT_VTABLE=$(Hex32 $expectedRootVt)",
    ('ROOT_GRID_OFFSET=0x{0:X}' -f $RootGridOffset),
    "GRID_OBJECT=$(Hex32 $grid)",
    "GRID_VTABLE=$(Hex32 $gridVt)",
    "EXPECTED_GRID_VTABLE=$(Hex32 $expectedGridVt)",
    ('GRID_PARENT_OFFSET=0x{0:X}' -f $GridParentOffset),
    "GRID_PARENT=$(Hex32 $gridParent)",
    ('ROOT_INVWIN_OFFSET=0x{0:X}' -f $RootInvWinOffset),
    "INVWIN_OBJECT=$(Hex32 $invwin)",
    "INVWIN_VTABLE=$(Hex32 $invwinVt)",
    "EXPECTED_INVWIN_VTABLE=$(Hex32 $expectedInvWinVt)",
    "ROOT_VTABLE_GATE=$(if ($rootVtPass) {'PASS'} else {'FAIL'})",
    "GRID_ROUNDTRIP_GATE=$(if ($gridPass) {'PASS'} else {'FAIL'})",
    "INVWIN_VTABLE_GATE=$(if ($invwinPass) {'PASS'} else {'FAIL'})",
    "STATUS=$status",
    "OWNER_ANCHOR_RUNTIME=$(if ($graphPass) {'PASS_CURRENT_PROCESS'} else {'NOT_YET'})",
    'FORMAL_WP5=NOT_YET',
    'FORMAL_WP6=NOT_YET',
    'READ_DWORD_COUNT=7',
    'EXACT_TARGET_DEREFERENCE=YES',
    'HEAP_SCAN=NO',
    'MEM_PRIVATE_SCAN=NO',
    'VECTOR_SCAN=NO',
    'MEMORY_WRITE=NO',
    'NEXT=If PASS, repeat once after a full Lin.bin2 restart; then freeze ROOT_GLOBAL as the stable owner anchor and proceed only to narrow collection-offset reads.'
)

$dir = Split-Path -Parent $OutputPath
if ($dir) { New-Item -ItemType Directory -Force -Path $dir | Out-Null }
[IO.File]::WriteAllText($OutputPath, ($lines -join [Environment]::NewLine) + [Environment]::NewLine, [Text.UTF8Encoding]::new($false))
$lines | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutputPath"
