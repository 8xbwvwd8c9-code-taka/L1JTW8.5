param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$RawPath = Join-Path $OutputDir '850_inventory_invwin_16c_helpers_v18_raw.txt'
$OutPath = Join-Path $OutputDir '850_inventory_invwin_16c_helpers_v18.txt'
$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$DecoderPath = Join-Path $DecoderDir 'decode_850_inventory_invwin_16c_helpers_v18.py'
$SetupPath = Join-Path $DecoderDir 'setup_capstone_decoder.ps1'
$PythonPath = Join-Path $DecoderDir '.venv\Scripts\python.exe'
$CaptureBytes = 384

$TargetRvas = @(
    0x0087E750L,
    0x0087F5C0L,
    0x0087F6E0L,
    0x0084EFD0L,
    0x00854A30L,
    0x00854A40L
)

if (-not (Test-Path -LiteralPath $DecoderPath)) { throw "Missing V18 decoder: $DecoderPath" }
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

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public sealed class InvWinV18Region850 {
    public long Base; public long Size; public uint State; public uint Protect; public uint Type;
}
public static class InvWinV18Mem850 {
    const uint PROCESS_VM_READ = 0x0010;
    const uint PROCESS_QUERY_INFORMATION = 0x0400;
    const uint MEM_COMMIT = 0x1000;
    const uint MEM_IMAGE = 0x1000000;
    const uint PAGE_GUARD = 0x100;
    const uint PAGE_NOACCESS = 0x01;
    [StructLayout(LayoutKind.Sequential)] struct MBI {
        public IntPtr BaseAddress; public IntPtr AllocationBase; public uint AllocationProtect;
        public UIntPtr RegionSize; public uint State; public uint Protect; public uint Type;
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
    public static InvWinV18Region850 Query(int pid, long address) {
        IntPtr h = Open(pid); try {
            MBI m; uint sz=(uint)Marshal.SizeOf(typeof(MBI));
            if (VirtualQueryEx(h,new IntPtr(address),out m,sz)==0) return null;
            return new InvWinV18Region850{Base=m.BaseAddress.ToInt64(),Size=unchecked((long)m.RegionSize.ToUInt64()),State=m.State,Protect=m.Protect,Type=m.Type};
        } finally { CloseHandle(h); }
    }
    public static bool IsExecutableImage(InvWinV18Region850 r) {
        if (r==null || r.State!=MEM_COMMIT || r.Type!=MEM_IMAGE) return false;
        if ((r.Protect&PAGE_GUARD)!=0 || (r.Protect&PAGE_NOACCESS)!=0) return false;
        uint p=r.Protect&0xFF; return p==0x10 || p==0x20 || p==0x40 || p==0x80;
    }
    public static byte[] ReadBytes(int pid,long address,int want) {
        IntPtr h=Open(pid); try {
            byte[] b=new byte[want]; IntPtr read;
            bool ok=ReadProcessMemory(h,new IntPtr(address),b,want,out read);
            int got=ok?(int)Math.Min((long)want,read.ToInt64()):0;
            if(got<=0) return new byte[0];
            if(got==want) return b;
            byte[] t=new byte[got]; Array.Copy(b,t,got); return t;
        } finally { CloseHandle(h); }
    }
}
"@

function Hex-Bytes([byte[]]$Bytes) {
    if ($null -eq $Bytes -or $Bytes.Length -eq 0) { return '' }
    return (($Bytes | ForEach-Object { $_.ToString('X2') }) -join ' ')
}

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_INVWIN_16C_HELPERS_V18')
$lines.Add("CLIENT=$full")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add(('MODULE_BASE=0x{0:X8}' -f $base))
$lines.Add(('MODULE_SIZE=0x{0:X}' -f $size))
$lines.Add("TARGET_COUNT=$($TargetRvas.Count)")
$lines.Add("CAPTURE_BYTES_PER_TARGET=$CaptureBytes")
$lines.Add('SOURCE=RUNTIME_MEM_IMAGE_EXACT_TARGETS')
$lines.Add('EXACT_TARGET_ONLY=YES')
$lines.Add('RUNTIME_MEM_IMAGE_ONLY=YES')
$lines.Add('FOCUS=INVWIN_PLUS_0x16C_BEGIN_END_HELPERS')
$lines.Add('HELPER_DEPTH=1')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('REMOTE_CALL=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[TARGETS]')

foreach ($rva in $TargetRvas) {
    $va = $base + [long]$rva
    if ($va -lt $base -or $va -ge $limit) { throw ('Target RVA outside main module: 0x{0:X8}' -f $rva) }
    $region = [InvWinV18Mem850]::Query($proc.Id,$va)
    if (-not [InvWinV18Mem850]::IsExecutableImage($region)) { throw ('Target not executable MEM_IMAGE: RVA=0x{0:X8}' -f $rva) }
    $available = ([long]$region.Base + [long]$region.Size) - $va
    $want = [int][Math]::Min([long]$CaptureBytes,$available)
    $bytes = [InvWinV18Mem850]::ReadBytes($proc.Id,$va,$want)
    if ($bytes.Length -lt 16) { throw ('Short runtime helper capture: RVA=0x{0:X8} bytes={1}' -f $rva,$bytes.Length) }
    $lines.Add(('TARGET_RVA=0x{0:X8} BYTES={1}' -f $rva,(Hex-Bytes $bytes)))
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
[IO.File]::WriteAllText($RawPath,($lines -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $PythonPath)) {
    if (-not (Test-Path -LiteralPath $SetupPath)) { throw "Missing decoder setup: $SetupPath" }
    & $SetupPath
}
if (-not (Test-Path -LiteralPath $PythonPath)) { throw "Decoder Python missing after setup: $PythonPath" }
$version = (& $PythonPath -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($version -ne '5.0.9') { throw "Unexpected Capstone package version: $version" }

& $PythonPath $DecoderPath --input $RawPath --output $OutPath
if ($LASTEXITCODE -ne 0) { throw "V18 decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutPath)) { throw "V18 output missing: $OutPath" }

Write-Host 'STATUS=PASS_INVWIN_16C_HELPERS_V18_EXECUTED'
Write-Host "RAW=$RawPath"
Write-Host "OUTPUT=$OutPath"
Write-Host 'EXACT_TARGET_ONLY=YES'
Write-Host 'RUNTIME_MEM_IMAGE_ONLY=YES'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'REMOTE_CALL=NO'
Write-Host 'MEMORY_WRITE=NO'
