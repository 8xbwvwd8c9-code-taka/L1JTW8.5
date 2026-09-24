param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [int]$VtableEntries = 64,
    [int]$FunctionBytes = 576,
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$InvWinVtableRva = 0x00EDE180L
$gatePath = Join-Path $OutputDir '850_inventory_root_graph_restart_gate_v6b.txt'
$rawPath = Join-Path $OutputDir '850_inventory_invwin_methods_v8_raw.txt'
$outPath = Join-Path $OutputDir '850_inventory_invwin_methods_v8.txt'
$decoderDir = Join-Path $PSScriptRoot 'decoder'
$setup = Join-Path $decoderDir 'setup_capstone_decoder.ps1'
$decoder = Join-Path $decoderDir 'decode_850_inventory_invwin_methods_v8.py'
$venvPython = Join-Path $decoderDir '.venv\Scripts\python.exe'

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
    throw 'V8 blocked: V6b restart-stable owner gate has not passed.'
}

if ($VtableEntries -lt 1 -or $VtableEntries -gt 64) { throw 'VtableEntries must be 1..64.' }
if ($FunctionBytes -lt 128 -or $FunctionBytes -gt 1024) { throw 'FunctionBytes must be 128..1024.' }
if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$full = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$proc = $null
foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
    try {
        if ($p.HasExited) { continue }
        if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)) {
            $proc = $p
            break
        }
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

public sealed class InvWinV8Region850 {
    public long Base;
    public long Size;
    public uint State;
    public uint Protect;
    public uint Type;
}

public static class InvWinV8Mem850 {
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

    static IntPtr Open(int pid) {
        IntPtr h = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
        if (h == IntPtr.Zero) throw new Exception("OpenProcess failed Win32=" + Marshal.GetLastWin32Error());
        return h;
    }

    public static uint ReadU32(int pid, long address) {
        IntPtr h = Open(pid);
        try {
            byte[] b = new byte[4]; IntPtr read;
            bool ok = ReadProcessMemory(h, new IntPtr(address), b, 4, out read);
            if (!ok || read.ToInt64() != 4) throw new Exception("ReadProcessMemory failed/short at 0x" + address.ToString("X"));
            return BitConverter.ToUInt32(b,0);
        } finally { CloseHandle(h); }
    }

    public static InvWinV8Region850 Query(int pid, long address) {
        IntPtr h = Open(pid);
        try {
            MBI m;
            uint sz = (uint)Marshal.SizeOf(typeof(MBI));
            if (VirtualQueryEx(h, new IntPtr(address), out m, sz) == 0) return null;
            return new InvWinV8Region850 {
                Base = m.BaseAddress.ToInt64(),
                Size = unchecked((long)m.RegionSize.ToUInt64()),
                State = m.State,
                Protect = m.Protect,
                Type = m.Type
            };
        } finally { CloseHandle(h); }
    }

    public static bool IsExecutableImage(InvWinV8Region850 r) {
        if (r == null || r.State != MEM_COMMIT || r.Type != MEM_IMAGE) return false;
        if ((r.Protect & PAGE_GUARD) != 0 || (r.Protect & PAGE_NOACCESS) != 0) return false;
        uint p = r.Protect & 0xFF;
        return p == 0x10 || p == 0x20 || p == 0x40 || p == 0x80;
    }

    public static byte[] ReadBytes(int pid, long address, int want) {
        if (want <= 0) return new byte[0];
        IntPtr h = Open(pid);
        try {
            byte[] b = new byte[want]; IntPtr read;
            bool ok = ReadProcessMemory(h, new IntPtr(address), b, want, out read);
            int got = ok ? (int)Math.Min((long)want, read.ToInt64()) : 0;
            if (got <= 0) return new byte[0];
            if (got == b.Length) return b;
            byte[] t = new byte[got]; Array.Copy(b,t,got); return t;
        } finally { CloseHandle(h); }
    }
}
"@

function Hex-Bytes([byte[]]$Bytes) {
    if ($null -eq $Bytes -or $Bytes.Length -eq 0) { return '' }
    return (($Bytes | ForEach-Object { $_.ToString('X2') }) -join ' ')
}

$vtableVa = $base + $InvWinVtableRva
$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_INVWIN_METHOD_CAPTURE_V8')
$lines.Add("CLIENT=$full")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$processStartUtc")
$lines.Add(('MODULE_BASE=0x{0:X8}' -f $base))
$lines.Add(('MODULE_SIZE=0x{0:X}' -f $size))
$lines.Add(('INVWIN_VTABLE_RVA=0x{0:X8}' -f $InvWinVtableRva))
$lines.Add(('INVWIN_VTABLE_VA=0x{0:X8}' -f $vtableVa))
$lines.Add("VTABLE_ENTRY_LIMIT=$VtableEntries")
$lines.Add("FUNCTION_BYTE_LIMIT=$FunctionBytes")
$lines.Add('RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[METHODS]')

$captured = 0
for ($slot=0; $slot -lt $VtableEntries; $slot++) {
    $fn = [uint32][InvWinV8Mem850]::ReadU32($proc.Id, $vtableVa + ($slot * 4L))
    if ([uint64]$fn -lt [uint64]$base -or [uint64]$fn -ge [uint64]$limit) {
        $lines.Add(('ENTRY SLOT={0:D2} VA=0x{1:X8} RVA=OUTSIDE' -f $slot,$fn))
        continue
    }
    $region = [InvWinV8Mem850]::Query($proc.Id,[long]$fn)
    if (-not [InvWinV8Mem850]::IsExecutableImage($region)) {
        $lines.Add(('ENTRY SLOT={0:D2} VA=0x{1:X8} RVA=0x{2:X8} EXEC_IMAGE=NO' -f $slot,$fn,([long]$fn-$base)))
        continue
    }
    $available = ([long]$region.Base + [long]$region.Size) - [long]$fn
    $want = [int][Math]::Min([long]$FunctionBytes,$available)
    $bytes = [InvWinV8Mem850]::ReadBytes($proc.Id,[long]$fn,$want)
    if ($bytes.Length -eq 0) {
        $lines.Add(('ENTRY SLOT={0:D2} VA=0x{1:X8} RVA=0x{2:X8} READ=FAIL' -f $slot,$fn,([long]$fn-$base)))
        continue
    }
    $captured++
    $lines.Add(('METHOD SLOT={0:D2} VA=0x{1:X8} RVA=0x{2:X8} PROTECT=0x{3:X8} BYTES={4}' -f $slot,$fn,([long]$fn-$base),$region.Protect,(Hex-Bytes $bytes)))
}

$lines.Add('')
$lines.Add('[SUMMARY]')
$lines.Add("METHOD_SLOTS_CAPTURED=$captured")
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
[IO.File]::WriteAllText($rawPath,($lines -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))

if (-not (Test-Path -LiteralPath $decoder)) { throw "Missing V8 decoder: $decoder" }
if (-not $SkipSetup -or -not (Test-Path -LiteralPath $venvPython)) {
    if (-not (Test-Path -LiteralPath $setup)) { throw "Missing decoder setup: $setup" }
    & $setup
}
if (-not (Test-Path -LiteralPath $venvPython)) { throw "Decoder Python missing after setup: $venvPython" }
$packageVersion = (& $venvPython -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($packageVersion -ne '5.0.9') { throw "Unexpected Capstone package version: $packageVersion" }

& $venvPython $decoder --input $rawPath --output $outPath
if ($LASTEXITCODE -ne 0) { throw "V8 decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $outPath)) { throw "V8 output missing: $outPath" }

Write-Host 'STATUS=PASS_INVWIN_V8_EXECUTED'
Write-Host "RAW=$rawPath"
Write-Host "OUTPUT=$outPath"
Write-Host 'RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
