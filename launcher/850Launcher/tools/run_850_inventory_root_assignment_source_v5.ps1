param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$TargetAssignRva = 0x00C9A1E3L
$WindowBefore = 0x4000L
$WindowAfter = 0x200L
$WindowStartRva = $TargetAssignRva - $WindowBefore
$WindowEndRva = $TargetAssignRva + $WindowAfter
$WindowLength = [int]($WindowEndRva - $WindowStartRva)

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
$moduleSize = [long]$module.ModuleMemorySize
$windowStartVa = $base + $WindowStartRva
$windowEndVa = $base + $WindowEndRva
if ($WindowStartRva -lt 0 -or $WindowEndRva -gt $moduleSize) { throw 'Requested V5 window falls outside Lin.bin2 main module.' }
$processStartUtc = $proc.StartTime.ToUniversalTime().ToString('o')

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;

public static class InventorySourceWindow850 {
    const uint PROCESS_VM_READ = 0x0010;
    const uint PROCESS_QUERY_INFORMATION = 0x0400;
    const uint MEM_COMMIT = 0x1000;
    const uint MEM_IMAGE = 0x1000000;
    const uint PAGE_GUARD = 0x100;
    const uint PAGE_NOACCESS = 0x01;

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

    [DllImport("kernel32.dll", SetLastError=true)] static extern IntPtr OpenProcess(uint access, bool inherit, int pid);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h, IntPtr address, byte[] buffer, int size, out IntPtr read);
    [DllImport("kernel32.dll", SetLastError=true)] static extern int VirtualQueryEx(IntPtr h, IntPtr address, out MBI mbi, uint length);

    static bool Readable(uint p) {
        if ((p & PAGE_GUARD) != 0 || (p & PAGE_NOACCESS) != 0) return false;
        uint x = p & 0xFF;
        return x == 0x02 || x == 0x04 || x == 0x08 || x == 0x10 || x == 0x20 || x == 0x40 || x == 0x80;
    }

    static bool Executable(uint p) {
        uint x = p & 0xFF;
        return x == 0x10 || x == 0x20 || x == 0x40 || x == 0x80;
    }

    public static byte[] ReadExactExecutableImage(int pid, long start, int length) {
        if (length <= 0) throw new ArgumentOutOfRangeException("length");
        byte[] output = new byte[length];
        long end = start + length;
        long cur = start;
        int dst = 0;
        IntPtr h = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
        if (h == IntPtr.Zero) throw new Exception("OpenProcess failed Win32=" + Marshal.GetLastWin32Error());
        try {
            uint mbiSize = (uint)Marshal.SizeOf(typeof(MBI));
            while (cur < end) {
                MBI m;
                if (VirtualQueryEx(h, new IntPtr(cur), out m, mbiSize) == 0)
                    throw new Exception("VirtualQueryEx failed at 0x" + cur.ToString("X"));
                long rb = m.BaseAddress.ToInt64();
                long rs = unchecked((long)m.RegionSize.ToUInt64());
                long rz = rb + rs;
                if (rs <= 0 || cur < rb || cur >= rz)
                    throw new Exception("Invalid memory region around 0x" + cur.ToString("X"));
                if (m.State != MEM_COMMIT || m.Type != MEM_IMAGE || !Readable(m.Protect) || !Executable(m.Protect))
                    throw new Exception("Window crossed non-executable MEM_IMAGE at 0x" + cur.ToString("X"));
                long takeLong = Math.Min(end, rz) - cur;
                int take = checked((int)takeLong);
                byte[] temp = new byte[take];
                IntPtr readPtr;
                bool ok = ReadProcessMemory(h, new IntPtr(cur), temp, take, out readPtr);
                int got = ok ? checked((int)readPtr.ToInt64()) : 0;
                if (!ok || got != take)
                    throw new Exception("ReadProcessMemory failed/short at 0x" + cur.ToString("X") + " got=" + got + " Win32=" + Marshal.GetLastWin32Error());
                Buffer.BlockCopy(temp, 0, output, dst, take);
                cur += take;
                dst += take;
            }
            return output;
        } finally {
            CloseHandle(h);
        }
    }
}
"@

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
$binPath = Join-Path $OutputDir '850_inventory_root_assignment_window_v5.bin'
$metaPath = Join-Path $OutputDir '850_inventory_root_assignment_window_v5_meta.txt'
$outPath = Join-Path $OutputDir '850_inventory_root_assignment_source_v5.txt'

$bytes = [InventorySourceWindow850]::ReadExactExecutableImage($proc.Id, $windowStartVa, $WindowLength)
[IO.File]::WriteAllBytes($binPath, $bytes)
$windowSha = (Get-FileHash -LiteralPath $binPath -Algorithm SHA256).Hash.ToUpperInvariant()

$meta = @(
    'MODE=850_INVENTORY_ROOT_ASSIGNMENT_WINDOW_V5',
    "CLIENT=$full",
    "CLIENT_SHA256=$sha",
    'CLIENT_AUTHORITY=1',
    "PID=$($proc.Id)",
    "PROCESS_START_UTC=$processStartUtc",
    ('MODULE_BASE=0x{0:X8}' -f $base),
    ('MODULE_SIZE=0x{0:X}' -f $moduleSize),
    ('WINDOW_START_RVA=0x{0:X8}' -f $WindowStartRva),
    ('WINDOW_END_RVA=0x{0:X8}' -f $WindowEndRva),
    ('WINDOW_START_VA=0x{0:X8}' -f $windowStartVa),
    ('WINDOW_END_VA=0x{0:X8}' -f $windowEndVa),
    "WINDOW_LENGTH=$WindowLength",
    "WINDOW_SHA256=$windowSha",
    'RUNTIME_TARGET_ATTACH=READ_ONLY_MODULE_IMAGE_WINDOW',
    'HEAP_SCAN=NO',
    'MEM_PRIVATE_SCAN=NO',
    'MEMORY_WRITE=NO'
)
[IO.File]::WriteAllText($metaPath, ($meta -join [Environment]::NewLine) + [Environment]::NewLine, [Text.UTF8Encoding]::new($false))

$decoderDir = Join-Path $PSScriptRoot 'decoder'
$setup = Join-Path $decoderDir 'setup_capstone_decoder.ps1'
$decoder = Join-Path $decoderDir 'trace_850_inventory_root_assignment_source.py'
$venvPython = Join-Path $decoderDir '.venv\Scripts\python.exe'
if (-not (Test-Path -LiteralPath $decoder)) { throw "Missing V5 decoder: $decoder" }
if (-not $SkipSetup -or -not (Test-Path -LiteralPath $venvPython)) {
    & $setup
}
if (-not (Test-Path -LiteralPath $venvPython)) { throw "Decoder Python missing: $venvPython" }
$packageVersion = (& $venvPython -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($packageVersion -ne '5.0.9') { throw "Unexpected Capstone package version: $packageVersion" }

& $venvPython $decoder --bin $binPath --meta $metaPath --output $outPath
if ($LASTEXITCODE -ne 0) { throw "V5 provenance decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $outPath)) { throw "V5 output missing: $outPath" }

Write-Host 'STATUS=PASS_ROOT_ASSIGNMENT_SOURCE_V5_EXECUTED'
Write-Host "OUTPUT=$outPath"
Write-Host "WINDOW_BIN=$binPath"
Write-Host "WINDOW_META=$metaPath"
Write-Host 'RUNTIME_TARGET_ATTACH=READ_ONLY_MODULE_IMAGE_WINDOW'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
