param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_ui_anchor_runtime_heap_scan.txt"
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
$sha = (Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) {
    New-Item -ItemType Directory -Force -Path $parent | Out-Null
}

$clientFull = [IO.Path]::GetFullPath($ClientPath)
$proc = $null
foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
    try {
        if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName), $clientFull, [StringComparison]::OrdinalIgnoreCase)) {
            $proc = $p
            break
        }
    } catch { }
}
if (-not $proc) { throw "Running authoritative Lin.bin2 process not found. Start the game first." }

Add-Type -TypeDefinition @"
using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

public sealed class UiAnchorHit850 {
    public string Name = "";
    public string Kind = "";
    public long Address;
    public long RegionBase;
    public long RegionSize;
    public uint Protect;
    public uint Type;
}

public sealed class UiAnchorScan850 {
    public long BytesScanned;
    public int RegionsScanned;
    public readonly List<UiAnchorHit850> Hits = new List<UiAnchorHit850>();
    public string Status = "";
}

public static class UiAnchorRuntimeScanner850 {
    const uint PROCESS_VM_READ = 0x0010;
    const uint PROCESS_QUERY_INFORMATION = 0x0400;
    const uint MEM_COMMIT = 0x1000;
    const uint PAGE_NOACCESS = 0x01;
    const uint PAGE_GUARD = 0x100;
    const int ChunkSize = 512 * 1024;
    const int MaxHitsPerPattern = 128;

    [StructLayout(LayoutKind.Sequential)]
    struct MEMORY_BASIC_INFORMATION {
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
    [DllImport("kernel32.dll", SetLastError=true)] static extern int VirtualQueryEx(IntPtr h, IntPtr address, out MEMORY_BASIC_INFORMATION mbi, uint len);
    [DllImport("kernel32.dll", SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h, IntPtr address, byte[] buffer, int size, out IntPtr read);

    sealed class Pat {
        public string Name;
        public string Kind;
        public byte[] Bytes;
        public int Hits;
    }

    public static UiAnchorScan850 Scan(int pid, string[] tokens, int[] dwords) {
        var result = new UiAnchorScan850();
        var pats = new List<Pat>();
        foreach (var token in tokens) {
            if (String.IsNullOrEmpty(token)) continue;
            pats.Add(new Pat { Name=token, Kind="ASCII", Bytes=Encoding.ASCII.GetBytes(token) });
            pats.Add(new Pat { Name=token, Kind="UTF16LE", Bytes=Encoding.Unicode.GetBytes(token) });
        }
        foreach (var v in dwords) {
            pats.Add(new Pat { Name="DWORD:" + v, Kind="DWORD32", Bytes=BitConverter.GetBytes(v) });
        }

        var h = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pid);
        if (h == IntPtr.Zero) { result.Status = "OpenProcess failed Win32=" + Marshal.GetLastWin32Error(); return result; }
        try {
            long address = 0x10000;
            const long maxAddress = 0x7FFF0000;
            uint mbiSize = (uint)Marshal.SizeOf(typeof(MEMORY_BASIC_INFORMATION));
            while (address < maxAddress) {
                MEMORY_BASIC_INFORMATION mbi;
                if (VirtualQueryEx(h, new IntPtr(address), out mbi, mbiSize) == 0) break;
                long regionBase = mbi.BaseAddress.ToInt64();
                long regionSize = unchecked((long)mbi.RegionSize.ToUInt64());
                if (regionSize <= 0) break;

                bool readable = mbi.State == MEM_COMMIT && (mbi.Protect & PAGE_GUARD) == 0 && (mbi.Protect & PAGE_NOACCESS) == 0;
                if (readable) {
                    result.RegionsScanned++;
                    long offset = 0;
                    int maxPat = 1;
                    foreach (var p in pats) if (p.Bytes.Length > maxPat) maxPat = p.Bytes.Length;
                    int overlap = maxPat - 1;
                    while (offset < regionSize) {
                        long remaining = regionSize - offset;
                        int wanted = (int)Math.Min((long)ChunkSize, remaining);
                        if (wanted <= 0) break;
                        var buffer = new byte[wanted];
                        IntPtr gotPtr;
                        bool ok = ReadProcessMemory(h, new IntPtr(regionBase + offset), buffer, wanted, out gotPtr);
                        int got = ok ? (int)Math.Min((long)wanted, gotPtr.ToInt64()) : 0;
                        if (got > 0) {
                            result.BytesScanned += got;
                            foreach (var p in pats) {
                                if (p.Hits >= MaxHitsPerPattern || p.Bytes.Length == 0 || got < p.Bytes.Length) continue;
                                int last = got - p.Bytes.Length;
                                for (int i=0; i<=last; i++) {
                                    if (buffer[i] != p.Bytes[0]) continue;
                                    bool match = true;
                                    for (int j=1; j<p.Bytes.Length; j++) { if (buffer[i+j] != p.Bytes[j]) { match=false; break; } }
                                    if (!match) continue;
                                    result.Hits.Add(new UiAnchorHit850 {
                                        Name=p.Name, Kind=p.Kind, Address=regionBase+offset+i,
                                        RegionBase=regionBase, RegionSize=regionSize, Protect=mbi.Protect, Type=mbi.Type
                                    });
                                    p.Hits++;
                                    if (p.Hits >= MaxHitsPerPattern) break;
                                }
                            }
                        }
                        if (wanted == remaining) break;
                        int advance = wanted - overlap;
                        if (advance <= 0) break;
                        offset += advance;
                    }
                }
                long next = regionBase + regionSize;
                if (next <= address) break;
                address = next;
            }
            result.Status = "PASS";
            return result;
        } finally { CloseHandle(h); }
    }
}
"@

$tokens = @(
    'HPGauge','MPGauge','HpGauge_Image','MpGauge_Image',
    'InventoryItemGrid','ItemCountLabel','InventoryScroll','DeleteItem','InvWin',
    'Action_PetWin','Click_PetWin','Pet_Button0','HP_Image',
    'SummonButton','SummonLevelButton','QuickSummonButton',
    'Spell_Grid','GridEvent'
)
$dwords = @(10899,10900,3200,3201,3212,3213,2052,2053)

$result = [UiAnchorRuntimeScanner850]::Scan($proc.Id, $tokens, $dwords)
$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_RESOURCE_GUIDED_UI_ANCHOR_RUNTIME_HEAP_SCAN")
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add("CLIENT=$clientFull")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add("REGIONS_SCANNED=$($result.RegionsScanned)")
$lines.Add("BYTES_SCANNED=$($result.BytesScanned)")
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("")

$grouped = $result.Hits | Group-Object Name,Kind | Sort-Object Name
foreach ($g in $grouped) {
    $parts = $g.Name -split ', ',2
    $lines.Add("[PATTERN $($parts[0]) KIND=$($parts[1])]" )
    $lines.Add("HITS=$($g.Count)")
    foreach ($h in ($g.Group | Select-Object -First 32)) {
        $lines.Add(("HIT ADDR=0x{0:X8} REGION=0x{1:X8} REGION_SIZE=0x{2:X} PROTECT=0x{3:X} TYPE=0x{4:X}" -f $h.Address,$h.RegionBase,$h.RegionSize,$h.Protect,$h.Type))
    }
    $lines.Add("")
}

$lines.Add("[REGION_COHERENCE]")
$regionGroups = $result.Hits | Group-Object RegionBase | Sort-Object Count -Descending
foreach ($rg in ($regionGroups | Select-Object -First 40)) {
    $names = $rg.Group | ForEach-Object { $_.Name + '/' + $_.Kind } | Sort-Object -Unique
    $lines.Add(("REGION=0x{0:X8} HIT_COUNT={1} PATTERNS={2}" -f [long]$rg.Name,$rg.Count,($names -join ',')))
}
$lines.Add("")
$lines.Add("TOTAL_HITS=$($result.Hits.Count)")
$lines.Add("STATUS=$($result.Status)")
$lines.Add("NOTE=Read-only runtime heap evidence only. String/resource-id hits do not establish HP/MP or inventory mappings without behavior correlation and restart validation.")
$lines | Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=$($result.Status)"
Write-Host "PID=$($proc.Id)"
Write-Host "HITS=$($result.Hits.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
