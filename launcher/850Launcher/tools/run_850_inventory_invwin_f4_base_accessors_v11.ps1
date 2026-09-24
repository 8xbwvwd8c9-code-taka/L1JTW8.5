param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$GatePath = Join-Path $OutputDir '850_inventory_root_graph_restart_gate_v6b.txt'
$RawPath = Join-Path $OutputDir '850_inventory_invwin_f4_base_accessors_v11_raw.txt'
$OutPath = Join-Path $OutputDir '850_inventory_invwin_f4_base_accessors_v11.txt'
$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$Setup = Join-Path $DecoderDir 'setup_capstone_decoder.ps1'
$Decoder = Join-Path $DecoderDir 'decode_850_inventory_invwin_f4_base_accessors_v11.py'
$VenvPython = Join-Path $DecoderDir '.venv\Scripts\python.exe'
$Targets = [ordered]@{
    BASE_MUT   = 0x004CD670L
    BASE_CONST = 0x004CD690L
}
$ByteLimit = 96

function Read-KvFile([string]$Path) {
    $m = @{}
    foreach ($line in Get-Content -LiteralPath $Path -ErrorAction Stop) {
        $i = $line.IndexOf('=')
        if ($i -le 0) { continue }
        $m[$line.Substring(0,$i).Trim()] = $line.Substring($i+1).Trim()
    }
    return $m
}

if (-not (Test-Path -LiteralPath $GatePath)) { throw "V6b gate missing: $GatePath" }
$gate = Read-KvFile $GatePath
if ($gate['CLIENT_SHA256'] -ne $ExpectedSha256 -or $gate['STATUS'] -ne 'PASS_ROOT_OWNER_RESTART_STABLE' -or $gate['OWNER_ANCHOR'] -ne 'PASS_RESTART_STABLE') {
    throw 'V11 blocked: restart-stable ROOT owner gate has not passed.'
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
public static class V11ImageRead850 {
    const uint PROCESS_VM_READ = 0x0010;
    const uint PROCESS_QUERY_INFORMATION = 0x0400;
    const uint MEM_COMMIT = 0x1000;
    const uint MEM_IMAGE = 0x1000000;
    const uint PAGE_EXECUTE = 0x10;
    const uint PAGE_EXECUTE_READ = 0x20;
    const uint PAGE_EXECUTE_READWRITE = 0x40;
    const uint PAGE_EXECUTE_WRITECOPY = 0x80;
    [StructLayout(LayoutKind.Sequential)] struct MBI {
        public IntPtr BaseAddress; public IntPtr AllocationBase; public uint AllocationProtect;
        public UIntPtr RegionSize; public uint State; public uint Protect; public uint Type;
    }
    [DllImport("kernel32.dll", SetLastError=true)] static extern IntPtr OpenProcess(uint a, bool i, int p);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h, IntPtr a, byte[] b, int s, out IntPtr r);
    [DllImport("kernel32.dll", SetLastError=true)] static extern int VirtualQueryEx(IntPtr h, IntPtr a, out MBI m, uint l);
    static bool Exec(uint p) { uint x=p & 0xFF; return x==PAGE_EXECUTE || x==PAGE_EXECUTE_READ || x==PAGE_EXECUTE_READWRITE || x==PAGE_EXECUTE_WRITECOPY; }
    public static byte[] ReadExecImage(int pid, long address, int count) {
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            MBI m; uint s=(uint)Marshal.SizeOf(typeof(MBI));
            if(VirtualQueryEx(h,new IntPtr(address),out m,s)==0) throw new Exception("VirtualQueryEx failed");
            long rb=m.BaseAddress.ToInt64(); long rs=unchecked((long)m.RegionSize.ToUInt64());
            if(m.State!=MEM_COMMIT || m.Type!=MEM_IMAGE || !Exec(m.Protect)) throw new Exception("Target is not committed executable MEM_IMAGE");
            long avail=(rb+rs)-address; if(avail<=0) throw new Exception("Target outside region");
            int want=(int)Math.Min((long)count,avail); byte[] b=new byte[want]; IntPtr got;
            bool ok=ReadProcessMemory(h,new IntPtr(address),b,want,out got);
            if(!ok || got.ToInt64()<=0) throw new Exception("ReadProcessMemory failed Win32="+Marshal.GetLastWin32Error());
            int n=(int)got.ToInt64(); if(n==b.Length) return b;
            byte[] t=new byte[n]; Array.Copy(b,t,n); return t;
        } finally { CloseHandle(h); }
    }
}
"@

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_INVWIN_F4_BASE_ACCESSOR_CAPTURE_V11')
$lines.Add("CLIENT=$full")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$processStartUtc")
$lines.Add(('MODULE_BASE=0x{0:X8}' -f $base))
$lines.Add(('MODULE_SIZE=0x{0:X}' -f $size))
$lines.Add('SUBOBJECT_OWNER=INVWIN+0xF4')
$lines.Add("TARGET_BYTE_LIMIT=$ByteLimit")
$lines.Add('RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[TARGETS]')
foreach ($kv in $Targets.GetEnumerator()) {
    $rva=[long]$kv.Value; $va=$base+$rva
    if ($va -lt $base -or $va -ge $limit) { throw "Target outside module: $($kv.Key)" }
    $bytes=[V11ImageRead850]::ReadExecImage($proc.Id,$va,$ByteLimit)
    $hex=($bytes | ForEach-Object { $_.ToString('X2') }) -join ' '
    $lines.Add(("TARGET NAME={0} RVA=0x{1:X8} VA=0x{2:X8} BYTES={3}" -f $kv.Key,$rva,$va,$hex))
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
[IO.File]::WriteAllText($RawPath,($lines -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))

if (-not (Test-Path -LiteralPath $Decoder)) { throw "Decoder missing: $Decoder" }
if (-not $SkipSetup -or -not (Test-Path -LiteralPath $VenvPython)) {
    if (-not (Test-Path -LiteralPath $Setup)) { throw "Decoder setup missing: $Setup" }
    & $Setup
}
if (-not (Test-Path -LiteralPath $VenvPython)) { throw "Decoder Python missing: $VenvPython" }
$pkg = (& $VenvPython -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($pkg -ne '5.0.9') { throw "Unexpected Capstone package version: $pkg" }

& $VenvPython $Decoder --input $RawPath --output $OutPath
if ($LASTEXITCODE -ne 0) { throw "V11 decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutPath)) { throw "V11 output missing: $OutPath" }

Write-Host 'STATUS=PASS_V11_F4_BASE_ACCESSOR_CAPTURE_AND_DECODE'
Write-Host "OUTPUT=$OutPath"
Write-Host "RAW=$RawPath"
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
