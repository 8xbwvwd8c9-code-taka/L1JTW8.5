param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2",
    [string]$OwnerReport = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_ui_owner_scan.txt",
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_hpmp_ui_behavior_correlation.txt",
    [int]$SampleMs = 250,
    [int]$BaselineSeconds = 8,
    [int]$HpSeconds = 20,
    [int]$MpSeconds = 20,
    [int]$FinalIdleSeconds = 8
)

$ErrorActionPreference = "Stop"
$ExpectedSha256 = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
if (-not (Test-Path -LiteralPath $OwnerReport)) { throw "Owner report not found: $OwnerReport" }
if ($SampleMs -lt 100) { throw "SampleMs must be >= 100." }

$sha = (Get-FileHash -LiteralPath $ClientPath -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

$report = Get-Content -LiteralPath $OwnerReport -ErrorAction Stop
function Get-ReportHex([string]$pattern) {
    $line = $report | Where-Object { $_ -match $pattern } | Select-Object -First 1
    if (-not $line) { return $null }
    $m = [regex]::Match($line,'0x([0-9A-Fa-f]+)')
    if (-not $m.Success) { return $null }
    return [long][Convert]::ToUInt64($m.Groups[1].Value,16)
}
function Get-ReportValue([string]$prefix) {
    $line = $report | Where-Object { $_ -like "$prefix*" } | Select-Object -First 1
    if (-not $line) { return $null }
    return ($line -split '=',2)[1].Trim()
}

$reportPid = [int](Get-ReportValue 'PID=')
$reportStart = Get-ReportValue 'PROCESS_START_UTC='
$reportHash = (Get-ReportValue 'CLIENT_SHA256=').ToUpperInvariant()
$reportAuthority = Get-ReportValue 'CLIENT_AUTHORITY='
if ($reportHash -ne $ExpectedSha256 -or $reportAuthority -ne '1') { throw "Owner report is not authoritative for this client." }

$hpLine = $report | Where-Object { $_ -match '^OBJECT NAME=HpGauge_Image ADDR=0x' } | Select-Object -First 1
$mpLine = $report | Where-Object { $_ -match '^OBJECT NAME=MpGauge_Image ADDR=0x' } | Select-Object -First 1
$statusLine = $report | Where-Object { $_ -match '^\s+FIELD \+0xE8=0x' } | Select-Object -First 1
$renewalLine = $report | Where-Object { $_ -match '^\s+FIELD \+0xEC=0x' } | Select-Object -First 1
if (-not $hpLine -or -not $mpLine -or -not $statusLine -or -not $renewalLine) { throw "Required HP/MP object evidence is missing from owner report." }

$hpObj = [long][Convert]::ToUInt64(([regex]::Match($hpLine,'ADDR=0x([0-9A-Fa-f]+)').Groups[1].Value),16)
$mpObj = [long][Convert]::ToUInt64(([regex]::Match($mpLine,'ADDR=0x([0-9A-Fa-f]+)').Groups[1].Value),16)
$statusWin = [long][Convert]::ToUInt64(([regex]::Match($statusLine,'FIELD \+0xE8=0x([0-9A-Fa-f]+)').Groups[1].Value),16)
$renewal = [long][Convert]::ToUInt64(([regex]::Match($renewalLine,'FIELD \+0xEC=0x([0-9A-Fa-f]+)').Groups[1].Value),16)

$p = Get-Process -Id $reportPid -ErrorAction Stop
if ($p.HasExited) { throw "Owner report PID is no longer running." }
$module = $p.MainModule
if (-not $module) { throw "MainModule unavailable; run this PowerShell elevated." }
$fullClient = [IO.Path]::GetFullPath($ClientPath)
if (-not [string]::Equals([IO.Path]::GetFullPath($module.FileName),$fullClient,[StringComparison]::OrdinalIgnoreCase)) { throw "PID no longer belongs to authoritative Lin.bin2." }
$currentStart = $p.StartTime.ToUniversalTime().ToString('o')
if ($reportStart -and $currentStart -ne $reportStart) { throw "Owner report is stale: process start time changed. Re-run run_850_hpmp_ui_owner_scan.ps1 first." }

$parent = Split-Path -Parent $OutputPath
if ($parent -and -not (Test-Path -LiteralPath $parent)) { New-Item -ItemType Directory -Force -Path $parent | Out-Null }

Add-Type -TypeDefinition @"
using System;
using System.Runtime.InteropServices;
public static class HpmpBehaviorMem850 {
    const uint PROCESS_VM_READ=0x0010, PROCESS_QUERY_INFORMATION=0x0400;
    [DllImport("kernel32.dll",SetLastError=true)] static extern IntPtr OpenProcess(uint a,bool i,int p);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool CloseHandle(IntPtr h);
    [DllImport("kernel32.dll",SetLastError=true)] static extern bool ReadProcessMemory(IntPtr h,IntPtr a,byte[] b,int s,out IntPtr r);
    public static byte[] Read(int pid,long address,int size) {
        IntPtr h=OpenProcess(PROCESS_QUERY_INFORMATION|PROCESS_VM_READ,false,pid);
        if(h==IntPtr.Zero) throw new Exception("OpenProcess failed Win32="+Marshal.GetLastWin32Error());
        try {
            byte[] b=new byte[size]; IntPtr rp;
            if(!ReadProcessMemory(h,new IntPtr(address),b,size,out rp)) return new byte[0];
            int n=(int)Math.Min((long)size,rp.ToInt64());
            if(n==size) return b;
            byte[] o=new byte[n]; Array.Copy(b,o,n); return o;
        } finally { CloseHandle(h); }
    }
}
"@

function Read-U32At([long]$addr) {
    $b=[HpmpBehaviorMem850]::Read($p.Id,$addr,4)
    if($b.Length -ne 4){ return $null }
    return [BitConverter]::ToUInt32($b,0)
}

# Revalidate the object graph before capture.
$hpStatus=Read-U32At ($hpObj+0xE8)
$mpStatus=Read-U32At ($mpObj+0xE8)
$hpRenewal=Read-U32At ($hpObj+0xEC)
$mpRenewal=Read-U32At ($mpObj+0xEC)
if ($hpStatus -ne $statusWin -or $mpStatus -ne $statusWin -or $hpRenewal -ne $renewal -or $mpRenewal -ne $renewal) {
    throw "HP/MP shared-owner graph changed. Re-run owner scan first."
}

$targets=@(
    [pscustomobject]@{Name='HP_GAUGE';Address=$hpObj;Size=0x300},
    [pscustomobject]@{Name='MP_GAUGE';Address=$mpObj;Size=0x300},
    [pscustomobject]@{Name='STATUS_WINDOW';Address=$statusWin;Size=0x400},
    [pscustomobject]@{Name='RENEWAL_STATUS_UI';Address=$renewal;Size=0x600}
)

$phases=@(
    [pscustomobject]@{Name='BASELINE_IDLE';Seconds=$BaselineSeconds;Instruction='Stand still. Prefer full HP/MP. Do not cast or take damage.'},
    [pscustomobject]@{Name='HP_ACTIVITY';Seconds=$HpSeconds;Instruction='Change HP several times: take a little damage and heal. Avoid MP-consuming skills.'},
    [pscustomobject]@{Name='MP_ACTIVITY';Seconds=$MpSeconds;Instruction='Change MP several times: use MP-consuming skills and allow recovery. Avoid taking damage.'},
    [pscustomobject]@{Name='FINAL_IDLE';Seconds=$FinalIdleSeconds;Instruction='Stand still again. No deliberate HP/MP activity.'}
)

$capture=@{}
foreach($phase in $phases){
    $capture[$phase.Name]=@{}
    foreach($t in $targets){ $capture[$phase.Name][$t.Name]=New-Object System.Collections.ArrayList }
}

function Invoke-CapturePhase($phase) {
    Write-Host ""
    Write-Host ("PHASE={0} SECONDS={1}" -f $phase.Name,$phase.Seconds)
    Write-Host ("ACTION={0}" -f $phase.Instruction)
    try { [console]::Beep(900,120) } catch { }
    $steps=[Math]::Max(1,[int][Math]::Ceiling(($phase.Seconds*1000.0)/$SampleMs))
    for($i=0;$i -lt $steps;$i++){
        foreach($t in $targets){
            $b=[HpmpBehaviorMem850]::Read($p.Id,[long]$t.Address,[int]$t.Size)
            if($b.Length -eq $t.Size){ [void]$capture[$phase.Name][$t.Name].Add($b) }
        }
        Start-Sleep -Milliseconds $SampleMs
    }
}

foreach($phase in $phases){ Invoke-CapturePhase $phase }
try { [console]::Beep(1200,180) } catch { }

function Get-Scalar([byte[]]$b,[int]$off,[string]$type) {
    if($type -eq 'U16'){
        if($off+2 -gt $b.Length){return $null}
        return [double][BitConverter]::ToUInt16($b,$off)
    }
    if($type -eq 'U32'){
        if($off+4 -gt $b.Length){return $null}
        return [double][BitConverter]::ToUInt32($b,$off)
    }
    if($type -eq 'F32'){
        if($off+4 -gt $b.Length){return $null}
        $v=[BitConverter]::ToSingle($b,$off)
        if([Single]::IsNaN($v) -or [Single]::IsInfinity($v)){return $null}
        return [double][Math]::Round([double]$v,6)
    }
    return $null
}

function Get-Stats($list,[int]$off,[string]$type) {
    if($null -eq $list -or $list.Count -lt 2){ return $null }
    $first=$null; $last=$null; $min=[double]::PositiveInfinity; $max=[double]::NegativeInfinity; $changes=0
    $set=New-Object 'System.Collections.Generic.HashSet[string]'
    foreach($b in $list){
        $v=Get-Scalar $b $off $type
        if($null -eq $v){continue}
        if($null -eq $first){$first=$v}
        if($null -ne $last -and $v -ne $last){$changes++}
        $last=$v
        if($v -lt $min){$min=$v}; if($v -gt $max){$max=$v}
        [void]$set.Add(([string]::Format([Globalization.CultureInfo]::InvariantCulture,'{0:R}',$v)))
    }
    if($null -eq $first){return $null}
    return [pscustomobject]@{Changes=$changes;Distinct=$set.Count;Min=$min;Max=$max;First=$first;Last=$last}
}

function Test-Plausible($stats,[string]$type) {
    if($null -eq $stats){return $false}
    if($type -eq 'U32' -and $stats.Max -gt 10000000){return $false}
    if($type -eq 'F32'){
        if([Math]::Abs($stats.Min) -gt 100000 -or [Math]::Abs($stats.Max) -gt 100000){return $false}
    }
    return $true
}

$candidates=New-Object System.Collections.Generic.List[object]
foreach($t in $targets){
    foreach($type in @('U16','U32','F32')){
        $step=if($type -eq 'U16'){2}else{4}
        $width=if($type -eq 'U16'){2}else{4}
        for($off=0;$off -le ($t.Size-$width);$off+=$step){
            $s0=Get-Stats $capture['BASELINE_IDLE'][$t.Name] $off $type
            $sh=Get-Stats $capture['HP_ACTIVITY'][$t.Name] $off $type
            $sm=Get-Stats $capture['MP_ACTIVITY'][$t.Name] $off $type
            $se=Get-Stats $capture['FINAL_IDLE'][$t.Name] $off $type
            if($null -eq $sh -or $null -eq $sm){continue}
            if(-not(Test-Plausible $sh $type) -or -not(Test-Plausible $sm $type)){continue}
            $baseCh=if($s0){$s0.Changes}else{0}; $endCh=if($se){$se.Changes}else{0}
            $class=$null
            if($sh.Changes -ge 2 -and $sm.Changes -le 1 -and $baseCh -le 1 -and $endCh -le 1){$class='HP_ONLY'}
            elseif($sm.Changes -ge 2 -and $sh.Changes -le 1 -and $baseCh -le 1 -and $endCh -le 1){$class='MP_ONLY'}
            elseif($sh.Changes -ge 2 -and $sm.Changes -ge 2 -and $baseCh -le 1 -and $endCh -le 1){$class='BOTH_ACTIVITY'}
            if($class){
                $candidates.Add([pscustomobject]@{
                    Target=$t.Name;Type=$type;Offset=$off;Class=$class;
                    BaseChanges=$baseCh;HpChanges=$sh.Changes;MpChanges=$sm.Changes;EndChanges=$endCh;
                    HpMin=$sh.Min;HpMax=$sh.Max;MpMin=$sm.Min;MpMax=$sm.Max
                })
            }
        }
    }
}

$symmetric=New-Object System.Collections.Generic.List[object]
$hpOnly=@($candidates | Where-Object {$_.Target -eq 'HP_GAUGE' -and $_.Class -eq 'HP_ONLY'})
$mpOnly=@($candidates | Where-Object {$_.Target -eq 'MP_GAUGE' -and $_.Class -eq 'MP_ONLY'})
foreach($h in $hpOnly){
    foreach($m in $mpOnly){
        if($h.Type -eq $m.Type -and $h.Offset -eq $m.Offset){
            $symmetric.Add([pscustomobject]@{Type=$h.Type;Offset=$h.Offset;HpChanges=$h.HpChanges;MpChanges=$m.MpChanges;HpMin=$h.HpMin;HpMax=$h.HpMax;MpMin=$m.MpMin;MpMax=$m.MpMax})
        }
    }
}

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add("MODE=850_HPMP_UI_BEHAVIOR_CORRELATION")
$lines.Add("PID=$($p.Id)")
$lines.Add("PROCESS_START_UTC=$currentStart")
$lines.Add("CLIENT=$fullClient")
$lines.Add("CLIENT_SHA256=$sha")
$lines.Add("CLIENT_AUTHORITY=1")
$lines.Add("MEMORY_WRITE=NO")
$lines.Add("SOURCE_MODIFIED=NO")
$lines.Add("SAMPLE_MS=$SampleMs")
$lines.Add("")
$lines.Add("[OBJECT_GRAPH]")
$lines.Add(("HP_GAUGE=0x{0:X8}" -f $hpObj))
$lines.Add(("MP_GAUGE=0x{0:X8}" -f $mpObj))
$lines.Add(("STATUS_WINDOW=0x{0:X8}" -f $statusWin))
$lines.Add(("RENEWAL_STATUS_UI=0x{0:X8}" -f $renewal))
$lines.Add("SHARED_STATUS_WINDOW=1")
$lines.Add("SHARED_RENEWAL_STATUS_UI=1")
$lines.Add("")
$lines.Add("[PHASES]")
foreach($phase in $phases){
    $count=$capture[$phase.Name]['HP_GAUGE'].Count
    $lines.Add("PHASE=$($phase.Name) SECONDS=$($phase.Seconds) SAMPLES=$count")
}
$lines.Add("")
$lines.Add("[SYMMETRIC_GAUGE_OFFSETS]")
$lines.Add("COUNT=$($symmetric.Count)")
foreach($s in ($symmetric | Sort-Object -Property @{Expression='HpChanges';Descending=$true},@{Expression='MpChanges';Descending=$true} | Select-Object -First 64)){
    $lines.Add(("CAND TYPE={0} OFFSET=0x{1:X} HP_CHANGES={2} MP_CHANGES={3} HP_RANGE={4}..{5} MP_RANGE={6}..{7}" -f $s.Type,$s.Offset,$s.HpChanges,$s.MpChanges,$s.HpMin,$s.HpMax,$s.MpMin,$s.MpMax))
}
$lines.Add("")
$lines.Add("[PHASE_SPECIFIC_FIELDS]")
foreach($c in ($candidates | Sort-Object -Property @{Expression={($_.HpChanges+$_.MpChanges)};Descending=$true},Target,Offset | Select-Object -First 240)){
    $lines.Add(("FIELD TARGET={0} CLASS={1} TYPE={2} OFFSET=0x{3:X} BASE_CH={4} HP_CH={5} MP_CH={6} END_CH={7} HP_RANGE={8}..{9} MP_RANGE={10}..{11}" -f $c.Target,$c.Class,$c.Type,$c.Offset,$c.BaseChanges,$c.HpChanges,$c.MpChanges,$c.EndChanges,$c.HpMin,$c.HpMax,$c.MpMin,$c.MpMax))
}
$lines.Add("")
$lines.Add("[SUMMARY]")
$lines.Add("PHASE_SPECIFIC_FIELDS=$($candidates.Count)")
$lines.Add("SYMMETRIC_GAUGE_OFFSETS=$($symmetric.Count)")
$lines.Add("STATUS=PASS_CAPTURED")
$lines.Add("NOTE=Read-only behavioral correlation. A symmetric gauge offset or phase-specific parent field is a candidate only; formal HP/MP mapping still requires numeric semantic proof and restart validation.")

$lines | Set-Content -LiteralPath $OutputPath -Encoding UTF8
Write-Host "STATUS=PASS_CAPTURED"
Write-Host "PID=$($p.Id)"
Write-Host "SYMMETRIC_GAUGE_OFFSETS=$($symmetric.Count)"
Write-Host "PHASE_SPECIFIC_FIELDS=$($candidates.Count)"
Write-Host "OUTPUT=$OutputPath"
Write-Host "MEMORY_WRITE=NO"
