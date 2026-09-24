param(
    [Parameter(Mandatory=$true)]
    [ValidateSet('baseline','stack_count','record_add','record_remove')]
    [string]$Label,
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
$historyDir = Join-Path $OutputDir '850_inventory_grid_collection_v7b_history'
$outPath = Join-Path $OutputDir '850_inventory_grid_collection_change_v7b.txt'

function Read-KvFile([string]$Path) {
    $m = @{}
    foreach ($line in Get-Content -LiteralPath $Path -ErrorAction Stop) {
        $i = $line.IndexOf('=')
        if ($i -le 0) { continue }
        $m[$line.Substring(0,$i).Trim()] = $line.Substring($i+1).Trim()
    }
    return $m
}

function Parse-Hex32([string]$Text) {
    if ([string]::IsNullOrWhiteSpace($Text)) { throw 'Missing hex value.' }
    $t = $Text.Trim()
    if ($t.StartsWith('0x',[StringComparison]::OrdinalIgnoreCase)) { $t = $t.Substring(2) }
    return [uint32][Convert]::ToUInt32($t,16)
}

function Hex32([uint32]$Value) { return ('0x{0:X8}' -f [uint64]$Value) }

function Signed-Delta([uint64]$Current,[uint64]$Baseline) {
    return [int64]$Current - [int64]$Baseline
}

function Triple-Shape([uint32]$Begin,[uint32]$End,[uint32]$Cap) {
    if ($Begin -eq 0 -and $End -eq 0 -and $Cap -eq 0) { return 'ZERO_TRIPLE' }
    if ($Begin -ne 0 -and $End -ne 0 -and $Cap -ne 0 -and [uint64]$Begin -le [uint64]$End -and [uint64]$End -le [uint64]$Cap) {
        $used = [uint64]$End - [uint64]$Begin
        $capacity = [uint64]$Cap - [uint64]$Begin
        if (($used % 4) -eq 0 -and ($capacity % 4) -eq 0) { return 'MONOTONIC_ALIGNED_TRIPLE' }
        return 'MONOTONIC_TRIPLE'
    }
    return 'NONVECTOR_SHAPE'
}

if (-not (Test-Path -LiteralPath $gatePath)) { throw "V6b gate missing: $gatePath" }
$gate = Read-KvFile $gatePath
if ($gate['CLIENT_SHA256'] -ne $ExpectedSha256 -or $gate['CLIENT_AUTHORITY'] -ne '1' -or $gate['STATUS'] -ne 'PASS_ROOT_OWNER_RESTART_STABLE' -or $gate['OWNER_ANCHOR'] -ne 'PASS_RESTART_STABLE') {
    throw 'V7b blocked: V6b restart-stable owner gate has not passed.'
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
$processStartUtc = $proc.StartTime.ToUniversalTime().ToString('o')

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class V7bRead850 {
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

function Read-U32([long]$Address) { return [uint32][V7bRead850]::ReadU32($proc.Id,$Address) }

# Revalidate the frozen owner graph before touching the nine fixed GRID fields.
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
    throw 'V7b live ROOT/GRID/INVWIN graph revalidation failed.'
}

$vals = @{}
foreach ($o in $Offsets) { $vals[$o] = Read-U32 ([long]$grid + $o) }
$bBegin=[uint32]$vals[0x1E4L]; $bEnd=[uint32]$vals[0x1E8L]; $bCap=[uint32]$vals[0x1ECL]
$bShape = Triple-Shape $bBegin $bEnd $bCap
$bUsed = if ([uint64]$bEnd -ge [uint64]$bBegin) { [uint64]$bEnd - [uint64]$bBegin } else { 0 }
$bCapacity = if ([uint64]$bCap -ge [uint64]$bBegin) { [uint64]$bCap - [uint64]$bBegin } else { 0 }

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
New-Item -ItemType Directory -Force -Path $historyDir | Out-Null
$stamp = (Get-Date).ToUniversalTime().ToString('yyyyMMddTHHmmssfffZ')
$safeStart = ($processStartUtc -replace '[^0-9A-Za-z]','')
$snapshotPath = Join-Path $historyDir ("v7b_{0}_{1}_pid{2}_{3}.txt" -f $safeStart,$Label,$proc.Id,$stamp)

$snapshot = New-Object System.Collections.Generic.List[string]
$snapshot.Add('MODE=850_INVENTORY_GRID_COLLECTION_V7B_SNAPSHOT')
$snapshot.Add("LABEL=$Label")
$snapshot.Add("CLIENT_SHA256=$sha")
$snapshot.Add('CLIENT_AUTHORITY=1')
$snapshot.Add("PID=$($proc.Id)")
$snapshot.Add("PROCESS_START_UTC=$processStartUtc")
$snapshot.Add(('MODULE_BASE=0x{0:X8}' -f $base))
$snapshot.Add("ROOT_OBJECT=$(Hex32 $root)")
$snapshot.Add("GRID_OBJECT=$(Hex32 $grid)")
$snapshot.Add("INVWIN_OBJECT=$(Hex32 $invwin)")
$snapshot.Add('ROOT_GRAPH_REVALIDATED=PASS')
foreach ($o in $Offsets) { $snapshot.Add(('OFFSET_0x{0:X3}={1}' -f $o,(Hex32 ([uint32]$vals[$o])))) }
$snapshot.Add("B_BEGIN=$(Hex32 $bBegin)")
$snapshot.Add("B_END=$(Hex32 $bEnd)")
$snapshot.Add("B_CAP=$(Hex32 $bCap)")
$snapshot.Add("B_USED_BYTES=$bUsed")
$snapshot.Add("B_CAPACITY_BYTES=$bCapacity")
$snapshot.Add("B_CLASS=$bShape")
$snapshot.Add('READ_DWORD_COUNT_COLLECTION=9')
$snapshot.Add('EXACT_TARGET_DEREFERENCE=YES')
$snapshot.Add('HEAP_SCAN=NO')
$snapshot.Add('MEM_PRIVATE_SCAN=NO')
$snapshot.Add('VECTOR_WIDE_SCAN=NO')
$snapshot.Add('MEMORY_WRITE=NO')
[IO.File]::WriteAllText($snapshotPath,($snapshot -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))

# Compare only against the newest baseline from this exact process instance.
$baseline = $null
$baselinePath = $null
foreach ($f in Get-ChildItem -LiteralPath $historyDir -Filter '*.txt' -File -ErrorAction SilentlyContinue | Sort-Object LastWriteTimeUtc -Descending) {
    $m = Read-KvFile $f.FullName
    if ($m['LABEL'] -ne 'baseline') { continue }
    if ($m['PID'] -ne [string]$proc.Id -or $m['PROCESS_START_UTC'] -ne $processStartUtc) { continue }
    $baseline=$m; $baselinePath=$f.FullName; break
}

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_GRID_COLLECTION_CHANGE_V7B')
$lines.Add("LABEL=$Label")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$processStartUtc")
$lines.Add('V6B_GATE=PASS_RESTART_STABLE')
$lines.Add('ROOT_GRAPH_REVALIDATED=PASS')
$lines.Add("SNAPSHOT=$snapshotPath")
$lines.Add("B_BEGIN=$(Hex32 $bBegin)")
$lines.Add("B_END=$(Hex32 $bEnd)")
$lines.Add("B_CAP=$(Hex32 $bCap)")
$lines.Add("B_USED_BYTES=$bUsed")
$lines.Add("B_CAPACITY_BYTES=$bCapacity")
$lines.Add("B_CLASS=$bShape")
$lines.Add('READ_DWORD_COUNT_COLLECTION=9')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_WIDE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[COMPARISON]')

if ($Label -eq 'baseline') {
    $lines.Add('BASELINE=CAPTURED')
    $lines.Add('STATUS=PASS_BASELINE_CAPTURED')
    $lines.Add('NEXT=Perform exactly one controlled action, then rerun with -Label stack_count, record_add, or record_remove.')
} elseif ($null -eq $baseline) {
    $lines.Add('BASELINE=NOT_FOUND_FOR_CURRENT_PROCESS')
    $lines.Add('STATUS=BLOCKED_NO_BASELINE')
    $lines.Add('NEXT=Capture -Label baseline in this same process before performing another controlled inventory action.')
} else {
    $baseBegin=Parse-Hex32 $baseline['B_BEGIN']; $baseEnd=Parse-Hex32 $baseline['B_END']; $baseCap=Parse-Hex32 $baseline['B_CAP']
    $baseUsed=[uint64]$baseline['B_USED_BYTES']; $baseCapacity=[uint64]$baseline['B_CAPACITY_BYTES']
    $beginDelta=Signed-Delta ([uint64]$bBegin) ([uint64]$baseBegin)
    $endDelta=Signed-Delta ([uint64]$bEnd) ([uint64]$baseEnd)
    $capDelta=Signed-Delta ([uint64]$bCap) ([uint64]$baseCap)
    $usedDelta=Signed-Delta $bUsed $baseUsed
    $capacityDelta=Signed-Delta $bCapacity $baseCapacity
    $unchanged = ($beginDelta -eq 0 -and $endDelta -eq 0 -and $capDelta -eq 0 -and $usedDelta -eq 0 -and $capacityDelta -eq 0)
    $lines.Add("BASELINE=$baselinePath")
    $lines.Add("BASE_B_BEGIN=$(Hex32 $baseBegin)")
    $lines.Add("BASE_B_END=$(Hex32 $baseEnd)")
    $lines.Add("BASE_B_CAP=$(Hex32 $baseCap)")
    $lines.Add("BASE_B_USED_BYTES=$baseUsed")
    $lines.Add("DELTA_BEGIN=$beginDelta")
    $lines.Add("DELTA_END=$endDelta")
    $lines.Add("DELTA_CAP=$capDelta")
    $lines.Add("DELTA_USED_BYTES=$usedDelta")
    $lines.Add("DELTA_CAPACITY_BYTES=$capacityDelta")

    $status='CONTROLLED_DELTA_NOT_MATCHED'
    $next='Keep B as candidate only; do not widen scan scope.'
    if ($Label -eq 'stack_count') {
        if ($unchanged) {
            $status='PASS_STACK_COUNT_NO_COLLECTION_SHAPE_CHANGE'
            $next='B behaves like record-cardinality storage rather than stack quantity; next perform exactly one distinct-record add/remove test.'
        } else {
            $status='STACK_COUNT_CHANGED_COLLECTION_SHAPE'
            $next='B may track UI/data rebuilding; capture a fresh baseline and test one distinct-record add/remove before promotion.'
        }
    } elseif ($Label -eq 'record_add') {
        if ($usedDelta -gt 0 -and ($usedDelta % 4) -eq 0 -and $usedDelta -le 512) {
            $status='PASS_RECORD_ADD_DELTA_CANDIDATE'
            $next='Repeat inverse record_remove from a fresh baseline; require equal-magnitude opposite used-byte delta before collection promotion.'
        }
    } elseif ($Label -eq 'record_remove') {
        if ($usedDelta -lt 0 -and ((-$usedDelta) % 4) -eq 0 -and (-$usedDelta) -le 512) {
            $status='PASS_RECORD_REMOVE_DELTA_CANDIDATE'
            $next='Repeat inverse record_add from a fresh baseline; require equal-magnitude opposite used-byte delta before collection promotion.'
        }
    }
    $lines.Add("STATUS=$status")
    $lines.Add('COLLECTION_LAYOUT_PROVEN=NO')
    $lines.Add('FORMAL_WP5=NOT_YET')
    $lines.Add('FORMAL_WP6=NOT_YET')
    $lines.Add("NEXT=$next")
}

[IO.File]::WriteAllText($outPath,($lines -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))
$lines | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$outPath"
