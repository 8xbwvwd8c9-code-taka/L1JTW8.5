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
$CollectionOffset = 0xF4L
$BeginFieldOffset = 0x0L
$EndFieldOffset = 0x4L
$ElementStride = 4L
$GatePath = Join-Path $OutputDir '850_inventory_root_graph_restart_gate_v6b.txt'
$HistoryDir = Join-Path $OutputDir '850_inventory_invwin_f4_v12_history'
$OutPath = Join-Path $OutputDir '850_inventory_invwin_f4_collection_change_v12.txt'

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
function Signed-Delta([int64]$Current,[int64]$Baseline) { return $Current - $Baseline }

if (-not (Test-Path -LiteralPath $GatePath)) { throw "V6b gate missing: $GatePath" }
$gate = Read-KvFile $GatePath
if ($gate['CLIENT_SHA256'] -ne $ExpectedSha256 -or $gate['CLIENT_AUTHORITY'] -ne '1' -or $gate['STATUS'] -ne 'PASS_ROOT_OWNER_RESTART_STABLE' -or $gate['OWNER_ANCHOR'] -ne 'PASS_RESTART_STABLE') {
    throw 'V12 blocked: V6b restart-stable owner gate has not passed.'
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
public static class V12F4Read850 {
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

function Read-U32([long]$Address) { return [uint32][V12F4Read850]::ReadU32($proc.Id,$Address) }

# Revalidate the frozen owner graph first.
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
    throw 'V12 live ROOT/GRID/INVWIN graph revalidation failed.'
}

$collectionBase = [long]$invwin + $CollectionOffset
$begin = Read-U32 ($collectionBase + $BeginFieldOffset)
$end = Read-U32 ($collectionBase + $EndFieldOffset)

$shape = 'INVALID'
$usedBytes = [int64]-1
$count = [int64]-1
if ($begin -eq 0 -and $end -eq 0) {
    $shape = 'EMPTY_ZERO_PAIR'
    $usedBytes = 0
    $count = 0
} elseif ($begin -ne 0 -and $end -ne 0 -and [uint64]$end -ge [uint64]$begin) {
    $usedBytes = [int64]([uint64]$end - [uint64]$begin)
    if (($usedBytes % $ElementStride) -eq 0) {
        $shape = 'MONOTONIC_STRIDE4_PAIR'
        $count = [int64]($usedBytes / $ElementStride)
    } else {
        $shape = 'MONOTONIC_NONSTRIDE_PAIR'
    }
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
New-Item -ItemType Directory -Force -Path $HistoryDir | Out-Null
$stamp = (Get-Date).ToUniversalTime().ToString('yyyyMMddTHHmmssfffZ')
$safeStart = ($processStartUtc -replace '[^0-9A-Za-z]','')
$snapshotPath = Join-Path $HistoryDir ("v12_{0}_{1}_pid{2}_{3}.txt" -f $safeStart,$Label,$proc.Id,$stamp)

$snapshot = New-Object System.Collections.Generic.List[string]
$snapshot.Add('MODE=850_INVENTORY_INVWIN_F4_V12_SNAPSHOT')
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
$snapshot.Add('COLLECTION_OWNER=INVWIN+0xF4')
$snapshot.Add('BEGIN_FIELD_OFFSET=0x0')
$snapshot.Add('END_FIELD_OFFSET=0x4')
$snapshot.Add('ELEMENT_STRIDE=4')
$snapshot.Add("BEGIN=$(Hex32 $begin)")
$snapshot.Add("END=$(Hex32 $end)")
$snapshot.Add("USED_BYTES=$usedBytes")
$snapshot.Add("ELEMENT_COUNT=$count")
$snapshot.Add("PAIR_CLASS=$shape")
$snapshot.Add('READ_DWORD_COUNT_COLLECTION=2')
$snapshot.Add('COLLECTION_BUFFER_DEREFERENCE=NO')
$snapshot.Add('HEAP_SCAN=NO')
$snapshot.Add('MEM_PRIVATE_SCAN=NO')
$snapshot.Add('MEMORY_WRITE=NO')
[IO.File]::WriteAllText($snapshotPath,($snapshot -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))

$baseline = $null
$baselinePath = $null
foreach ($f in Get-ChildItem -LiteralPath $HistoryDir -Filter '*.txt' -File -ErrorAction SilentlyContinue | Sort-Object LastWriteTimeUtc -Descending) {
    $m = Read-KvFile $f.FullName
    if ($m['LABEL'] -ne 'baseline') { continue }
    if ($m['PID'] -ne [string]$proc.Id -or $m['PROCESS_START_UTC'] -ne $processStartUtc) { continue }
    $baseline=$m; $baselinePath=$f.FullName; break
}

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_INVWIN_F4_COLLECTION_CHANGE_V12')
$lines.Add("LABEL=$Label")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_START_UTC=$processStartUtc")
$lines.Add('V6B_GATE=PASS_RESTART_STABLE')
$lines.Add('ROOT_GRAPH_REVALIDATED=PASS')
$lines.Add('STATIC_LAYOUT_GATE=PASS_BEGIN_0_END_4_STRIDE_4')
$lines.Add("SNAPSHOT=$snapshotPath")
$lines.Add("BEGIN=$(Hex32 $begin)")
$lines.Add("END=$(Hex32 $end)")
$lines.Add("USED_BYTES=$usedBytes")
$lines.Add("ELEMENT_COUNT=$count")
$lines.Add("PAIR_CLASS=$shape")
$lines.Add('READ_DWORD_COUNT_COLLECTION=2')
$lines.Add('COLLECTION_BUFFER_DEREFERENCE=NO')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('')
$lines.Add('[COMPARISON]')

if ($shape -eq 'INVALID' -or $shape -eq 'MONOTONIC_NONSTRIDE_PAIR') {
    $lines.Add('STATUS=BLOCKED_INVALID_F4_PAIR')
    $lines.Add('COLLECTION_RUNTIME_DELTA_PROVEN=NO')
    $lines.Add('NEXT=Do not dereference the buffer or widen scanning; inspect only the static F4 accessor chain.')
} elseif ($Label -eq 'baseline') {
    $lines.Add('BASELINE=CAPTURED')
    $lines.Add('STATUS=PASS_BASELINE_CAPTURED')
    $lines.Add('COLLECTION_RUNTIME_DELTA_PROVEN=NO')
    $lines.Add('NEXT=Perform exactly one controlled stack-count or distinct-record action, then rerun with the matching label in this same process.')
} elseif ($null -eq $baseline) {
    $lines.Add('BASELINE=NOT_FOUND_FOR_CURRENT_PROCESS')
    $lines.Add('STATUS=BLOCKED_NO_BASELINE')
    $lines.Add('COLLECTION_RUNTIME_DELTA_PROVEN=NO')
    $lines.Add('NEXT=Capture -Label baseline in this same process before another controlled inventory action.')
} else {
    $baseBegin=Parse-Hex32 $baseline['BEGIN']
    $baseEnd=Parse-Hex32 $baseline['END']
    $baseUsed=[int64]$baseline['USED_BYTES']
    $baseCount=[int64]$baseline['ELEMENT_COUNT']
    $beginDelta=Signed-Delta ([int64][uint64]$begin) ([int64][uint64]$baseBegin)
    $endDelta=Signed-Delta ([int64][uint64]$end) ([int64][uint64]$baseEnd)
    $usedDelta=Signed-Delta $usedBytes $baseUsed
    $countDelta=Signed-Delta $count $baseCount
    $allocationMoved = ($begin -ne $baseBegin)
    $lines.Add("BASELINE=$baselinePath")
    $lines.Add("BASE_BEGIN=$(Hex32 $baseBegin)")
    $lines.Add("BASE_END=$(Hex32 $baseEnd)")
    $lines.Add("BASE_USED_BYTES=$baseUsed")
    $lines.Add("BASE_ELEMENT_COUNT=$baseCount")
    $lines.Add("DELTA_BEGIN=$beginDelta")
    $lines.Add("DELTA_END=$endDelta")
    $lines.Add("DELTA_USED_BYTES=$usedDelta")
    $lines.Add("DELTA_ELEMENT_COUNT=$countDelta")
    $lines.Add("ALLOCATION_MOVED=$(if($allocationMoved){'YES'}else{'NO'})")

    $status='CONTROLLED_DELTA_NOT_MATCHED'
    $runtimeProof='NO'
    $next='Keep F4 as a static collection layout only; do not dereference elements yet.'
    if ($Label -eq 'stack_count') {
        if ($usedDelta -eq 0 -and $countDelta -eq 0) {
            $status='PASS_STACK_COUNT_NO_RECORD_CARDINALITY_CHANGE'
            $next='Capture a fresh baseline, then add or remove exactly one distinct inventory record.'
        } else {
            $status='STACK_COUNT_CHANGED_RECORD_CARDINALITY'
            $next='Do not promote; repeat from a clean baseline with a known existing stack only.'
        }
    } elseif ($Label -eq 'record_add') {
        if ($usedDelta -eq 4 -and $countDelta -eq 1) {
            $status='PASS_RECORD_ADD_PLUS4'
            $next='Capture a fresh baseline and remove exactly one distinct record; require -4 bytes / -1 element.'
        }
    } elseif ($Label -eq 'record_remove') {
        if ($usedDelta -eq -4 -and $countDelta -eq -1) {
            $status='PASS_RECORD_REMOVE_MINUS4'
            $next='If an opposite +4 record_add has also been observed in this process family, promote F4 to runtime-controlled collection layout and proceed to bounded element-pointer identity validation.'
        }
    }
    $lines.Add("STATUS=$status")
    $lines.Add("COLLECTION_RUNTIME_DELTA_PROVEN=$runtimeProof")
    $lines.Add('FORMAL_WP5=NOT_YET')
    $lines.Add('FORMAL_WP6=NOT_YET')
    $lines.Add("NEXT=$next")
}

[IO.File]::WriteAllText($OutPath,($lines -join [Environment]::NewLine)+[Environment]::NewLine,[Text.UTF8Encoding]::new($false))
$lines | ForEach-Object { Write-Host $_ }
Write-Host "OUTPUT=$OutPath"
