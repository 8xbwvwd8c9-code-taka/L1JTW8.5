param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$AuditReport = "I:\8.50c客服端\auto_runtime_audit_report.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_resource_pair_code_scan.txt"
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

function Resolve-AuthoritativeProcess {
    param([string]$ExpectedPath,[string]$ReportPath)

    $full = [IO.Path]::GetFullPath($ExpectedPath)
    foreach ($p in Get-Process -ErrorAction SilentlyContinue) {
        try {
            if ($p.HasExited) { continue }
            if ($p.MainModule -and [string]::Equals([IO.Path]::GetFullPath($p.MainModule.FileName),$full,[StringComparison]::OrdinalIgnoreCase)) {
                return [pscustomobject]@{ Process=$p; Detection='MAINMODULE_PATH' }
            }
        } catch { }
    }

    if (Test-Path -LiteralPath $ReportPath) {
        $pidLine = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue | Where-Object { $_ -match '^PID=\d+$' } | Select-Object -First 1
        $authLine = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue | Where-Object { $_ -eq 'CLIENT_AUTHORITY=1' } | Select-Object -First 1
        $hashLine = Get-Content -LiteralPath $ReportPath -ErrorAction SilentlyContinue | Where-Object { $_ -match '^CLIENT_SHA256=' } | Select-Object -First 1
        if ($pidLine -and $authLine -and $hashLine -and (($hashLine -split '=',2)[1].Trim().ToUpperInvariant() -eq $ExpectedSha256)) {
            $pid = [int](($pidLine -split '=',2)[1])
            try {
                $p = Get-Process -Id $pid -ErrorAction Stop
                if (-not $p.HasExited) { return [pscustomobject]@{ Process=$p; Detection='AUTHORITATIVE_AUDIT_REPORT' } }
            } catch { }
        }
    }

    throw "Running authoritative Lin.bin2 process not found."
}

$resolved = Resolve-AuthoritativeProcess -ExpectedPath $ClientPath -ReportPath $AuditReport
$proc = $resolved.Process
$detect = $resolved.Detection

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class MemRead850Pair {
  const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
  [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
  [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
  [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
  public static byte[] Read(int pid,long address,int size) {
    IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
    if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
    try {
      byte[] b=new byte[size]; IntPtr rp;
      if(!ReadProcessMemory(h,new IntPtr(address),b,size,out rp)) throw new Exception("ReadProcessMemory failed Win32="+Marshal.GetLastWin32Error());
      int n=(int)Math.Min((long)size,rp.ToInt64()); if(n==size) return b;
      byte[] o=new byte[n]; Array.Copy(b,o,n); return o;
    } finally { CloseHandle(h); }
  }
}
"@

$module = $proc.MainModule
if (-not $module) { throw "MainModule unavailable; run this PowerShell elevated." }
$base = [long]$module.BaseAddress
$size = [int]$module.ModuleMemorySize
$mem = [MemRead850Pair]::Read($proc.Id,$base,$size)

function Find-DwordHits([byte[]]$data,[uint32]$value) {
    $p = [BitConverter]::GetBytes($value)
    $hits = New-Object System.Collections.Generic.List[int]
    for ($i=0; $i -le $data.Length-4; $i++) {
        if ($data[$i] -eq $p[0] -and $data[$i+1] -eq $p[1] -and $data[$i+2] -eq $p[2] -and $data[$i+3] -eq $p[3]) { $hits.Add($i) }
    }
    return $hits
}

function Find-FunctionStart([byte[]]$data,[int]$index,[int]$maxBack=1280) {
    $min = [Math]::Max(0,$index-$maxBack)
    for ($i=$index; $i -ge $min; $i--) {
        if ($i+2 -lt $data.Length -and $data[$i] -eq 0x55 -and $data[$i+1] -eq 0x8B -and $data[$i+2] -eq 0xEC) { return $i }
        if ($i+4 -lt $data.Length -and $data[$i] -eq 0x8B -and $data[$i+1] -eq 0xFF -and $data[$i+2] -eq 0x55 -and $data[$i+3] -eq 0x8B -and $data[$i+4] -eq 0xEC) { return $i }
    }
    for ($i=$index-1; $i -ge $min; $i--) {
        if ($data[$i] -eq 0xCC -or $data[$i] -eq 0xC3) { return $i+1 }
        if ($data[$i] -eq 0xC2 -and $i+2 -lt $data.Length) { return $i+3 }
    }
    return -1
}

function Hex-Bytes([byte[]]$data,[int]$start,[int]$count) {
    $end = [Math]::Min($data.Length,$start+$count)
    if ($start -lt 0 -or $start -ge $end) { return '' }
    $sb = New-Object Text.StringBuilder
    for ($i=$start; $i -lt $end; $i++) {
        if ($sb.Length -gt 0) { [void]$sb.Append(' ') }
        [void]$sb.Append($data[$i].ToString('X2'))
    }
    return $sb.ToString()
}

function Analyze-Function([byte[]]$data,[int]$func,[int]$span,[long]$moduleBase,[System.Collections.Generic.List[string]]$lines) {
    if ($func -lt 0) { return }
    $end = [Math]::Min($data.Length,$func+$span)
    $calls = 0
    $fields = 0
    for ($i=$func; $i -lt $end; $i++) {
        if ($data[$i] -eq 0xE8 -and $i+4 -lt $end) {
            $rel = [BitConverter]::ToInt32($data,$i+1)
            $target = $i + 5 + $rel
            if ($target -ge 0 -and $target -lt $data.Length) {
                $lines.Add(("CALL AT_RVA=0x{0:X8} TARGET_RVA=0x{1:X8}" -f $i,$target))
                $calls++
                if ($calls -ge 64) { break }
            }
        }
    }

    for ($i=$func; $i -lt $end-6; $i++) {
        $op = $data[$i]
        $m = $data[$i+1]
        if (($op -eq 0x8B -or $op -eq 0x89 -or $op -eq 0x8D) -and (($m -band 0xC7) -eq 0x81)) {
            $disp = [BitConverter]::ToInt32($data,$i+2)
            if ([Math]::Abs([long]$disp) -le 0x100000) {
                $lines.Add(("ECX_FIELD OP=0x{0:X2} MODRM=0x{1:X2} AT_RVA=0x{2:X8} DISP=0x{3:X8} ({4})" -f $op,$m,$i,[uint32]$disp,$disp))
                $fields++
                if ($fields -ge 96) { break }
            }
        }
    }

    $lines.Add(("HEX_HEAD={0}" -f (Hex-Bytes $data $func 192)))
}

$pairs = @(
    [pscustomobject]@{ Name='HPMP_GAUGE_PNG'; A=10899; B=10900; MaxDistance=0x400 },
    [pscustomobject]@{ Name='PET_HP_PNG'; A=3200; B=3201; MaxDistance=0x400 },
    [pscustomobject]@{ Name='INVENTORY_RESOURCE'; A=3212; B=3213; MaxDistance=0x400 },
    [pscustomobject]@{ Name='DELETE_RESOURCE'; A=2052; B=2053; MaxDistance=0x400 }
)

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_RUNTIME_RESOURCE_PAIR_CODE_SCAN")
$lines.Add("PID=$($proc.Id)")
$lines.Add("PROCESS_DETECTION=$detect")
$lines.Add("PROCESS_START_UTC=$($proc.StartTime.ToUniversalTime().ToString('o'))")
$lines.Add("CLIENT=$([IO.Path]::GetFullPath($ClientPath))")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add(("MODULE_BASE=0x{0:X8}" -f $base))
$lines.Add(("MODULE_SIZE=0x{0:X}" -f $size))
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("")

$totalPairs = 0
$seenFuncs = New-Object 'System.Collections.Generic.HashSet[int]'
foreach ($pair in $pairs) {
    $ha = Find-DwordHits $mem ([uint32]$pair.A)
    $hb = Find-DwordHits $mem ([uint32]$pair.B)
    $lines.Add("[PAIR $($pair.Name) A=$($pair.A) B=$($pair.B)]")
    $lines.Add("A_HITS=$($ha.Count)")
    $lines.Add("B_HITS=$($hb.Count)")
    $local = 0
    foreach ($a in $ha) {
        foreach ($b in $hb) {
            $d = [Math]::Abs($a-$b)
            if ($d -gt $pair.MaxDistance) { continue }
            $func = Find-FunctionStart $mem ([Math]::Min($a,$b))
            $lines.Add(("PAIR_HIT A_RVA=0x{0:X8} B_RVA=0x{1:X8} DIST=0x{2:X} FUNC_RVA={3}" -f $a,$b,$d,$(if($func -ge 0){'0x{0:X8}' -f $func}else{'NOT_FOUND'})))
            $local++; $totalPairs++
            if ($func -ge 0 -and $seenFuncs.Add($func)) {
                $lines.Add(("[FUNCTION RVA=0x{0:X8} VA=0x{1:X8} SOURCE_PAIR={2}]" -f $func,($base+$func),$pair.Name))
                Analyze-Function $mem $func 0x900 $base $lines
            }
            if ($local -ge 64) { break }
        }
        if ($local -ge 64) { break }
    }
    $lines.Add("PAIR_COHERENT_HITS=$local")
    $lines.Add("")
}

$focus = @(
    [pscustomobject]@{Name='INVENTORY_GRID_INIT';Rva=0x00707870},
    [pscustomobject]@{Name='INVENTORY_COUNT_LABEL';Rva=0x007022A0},
    [pscustomobject]@{Name='PET_ACTION';Rva=0x004BD8F0},
    [pscustomobject]@{Name='PET_HP_IMAGE';Rva=0x004BD270},
    [pscustomobject]@{Name='SUMMON_UI';Rva=0x0095BD40},
    [pscustomobject]@{Name='SPELL_GRID';Rva=0x00B9B770},
    [pscustomobject]@{Name='GRID_EVENT_A';Rva=0x00536A10},
    [pscustomobject]@{Name='GRID_EVENT_B';Rva=0x00B9A7E0},
    [pscustomobject]@{Name='INVWIN_ALT';Rva=0x00C81FE0}
)

$lines.Add("[FOCUSED_XREF_FUNCTIONS]")
foreach ($f in $focus) {
    if ($f.Rva -lt 0 -or $f.Rva -ge $mem.Length) { continue }
    $lines.Add(("[FUNCTION NAME={0} RVA=0x{1:X8} VA=0x{2:X8}]" -f $f.Name,$f.Rva,($base+$f.Rva)))
    Analyze-Function $mem ([int]$f.Rva) 0x900 $base $lines
    $lines.Add("")
}

$lines.Add("TOTAL_PAIR_COHERENT_HITS=$totalPairs")
$lines.Add("UNIQUE_PAIR_FUNCTIONS=$($seenFuncs.Count)")
$lines.Add("STATUS=PASS")
$lines.Add("NOTE=Resource-id proximity and lightweight x86 field/call decoding are heuristic candidate evidence only. No HP/MP or inventory map is accepted without behavioral correlation and restart validation.")
$lines | Out-File -LiteralPath $OutputPath -Encoding utf8

Write-Host "STATUS=PASS"
Write-Host "PID=$($proc.Id)"
Write-Host "PAIR_HITS=$totalPairs"
Write-Host "PAIR_FUNCTIONS=$($seenFuncs.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
