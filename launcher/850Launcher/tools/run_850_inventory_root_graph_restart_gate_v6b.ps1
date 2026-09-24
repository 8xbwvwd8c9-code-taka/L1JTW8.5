param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$UseExistingV6
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$ExpectedRootGlobalRva = 0x012BCEE8L
$ExpectedRootVtableRva = 0x00EDE2F8L
$ExpectedGridVtableRva = 0x00EDDE38L
$ExpectedInvWinVtableRva = 0x00EDE180L
$ExpectedRootGridOffset = 0x15CL
$ExpectedRootInvWinOffset = 0x168L
$ExpectedGridParentOffset = 0xECL

$v6Script = Join-Path $PSScriptRoot 'run_850_inventory_root_graph_v6.ps1'
$v6Path = Join-Path $OutputDir '850_inventory_root_graph_v6.txt'
$historyDir = Join-Path $OutputDir '850_inventory_root_graph_v6_history'
$gatePath = Join-Path $OutputDir '850_inventory_root_graph_restart_gate_v6b.txt'

function Read-KvFile([string]$Path) {
    $map = @{}
    foreach ($line in Get-Content -LiteralPath $Path -ErrorAction Stop) {
        $idx = $line.IndexOf('=')
        if ($idx -le 0) { continue }
        $k = $line.Substring(0, $idx).Trim()
        $v = $line.Substring($idx + 1).Trim()
        $map[$k] = $v
    }
    return $map
}

function Hex-ToInt64([string]$Text) {
    if ([string]::IsNullOrWhiteSpace($Text)) { throw 'Missing hex value.' }
    $t = $Text.Trim()
    if ($t.StartsWith('0x', [StringComparison]::OrdinalIgnoreCase)) { $t = $t.Substring(2) }
    return [Convert]::ToInt64($t, 16)
}

function Is-ValidSession($m) {
    try {
        if ($m['CLIENT_SHA256'] -ne $ExpectedSha256) { return $false }
        if ($m['CLIENT_AUTHORITY'] -ne '1') { return $false }
        if ($m['STATUS'] -ne 'PASS_ROOT_GRAPH_EXACT_OFFSETS') { return $false }
        if ($m['OWNER_ANCHOR_RUNTIME'] -ne 'PASS_CURRENT_PROCESS') { return $false }
        if ($m['HEAP_SCAN'] -ne 'NO' -or $m['MEM_PRIVATE_SCAN'] -ne 'NO' -or $m['VECTOR_SCAN'] -ne 'NO' -or $m['MEMORY_WRITE'] -ne 'NO') { return $false }
        if ($m['READ_DWORD_COUNT'] -ne '7' -or $m['EXACT_TARGET_DEREFERENCE'] -ne 'YES') { return $false }
        if ($m['ROOT_VTABLE_GATE'] -ne 'PASS' -or $m['GRID_ROUNDTRIP_GATE'] -ne 'PASS' -or $m['INVWIN_VTABLE_GATE'] -ne 'PASS') { return $false }
        if ([string]::IsNullOrWhiteSpace($m['PID']) -or [string]::IsNullOrWhiteSpace($m['PROCESS_START_UTC'])) { return $false }

        $base = Hex-ToInt64 $m['MODULE_BASE']
        $rootGlobalVa = Hex-ToInt64 $m['ROOT_GLOBAL_VA']
        $rootVt = Hex-ToInt64 $m['ROOT_VTABLE']
        $gridVt = Hex-ToInt64 $m['GRID_VTABLE']
        $invVt = Hex-ToInt64 $m['INVWIN_VTABLE']
        $rootObj = Hex-ToInt64 $m['ROOT_OBJECT']
        $gridObj = Hex-ToInt64 $m['GRID_OBJECT']
        $invObj = Hex-ToInt64 $m['INVWIN_OBJECT']
        $gridParent = Hex-ToInt64 $m['GRID_PARENT']

        if ($rootObj -eq 0 -or $gridObj -eq 0 -or $invObj -eq 0) { return $false }
        if ($gridParent -ne $rootObj) { return $false }
        if (($rootGlobalVa - $base) -ne $ExpectedRootGlobalRva) { return $false }
        if (($rootVt - $base) -ne $ExpectedRootVtableRva) { return $false }
        if (($gridVt - $base) -ne $ExpectedGridVtableRva) { return $false }
        if (($invVt - $base) -ne $ExpectedInvWinVtableRva) { return $false }
        if ((Hex-ToInt64 $m['ROOT_GRID_OFFSET']) -ne $ExpectedRootGridOffset) { return $false }
        if ((Hex-ToInt64 $m['ROOT_INVWIN_OFFSET']) -ne $ExpectedRootInvWinOffset) { return $false }
        if ((Hex-ToInt64 $m['GRID_PARENT_OFFSET']) -ne $ExpectedGridParentOffset) { return $false }
        return $true
    } catch {
        return $false
    }
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
New-Item -ItemType Directory -Force -Path $historyDir | Out-Null

if ($UseExistingV6) {
    if (-not (Test-Path -LiteralPath $v6Path)) { throw "Existing V6 report not found: $v6Path" }
} else {
    if (-not (Test-Path -LiteralPath $v6Script)) { throw "V6 script not found: $v6Script" }
    & $v6Script -ClientPath $ClientPath -OutputPath $v6Path
    if ($LASTEXITCODE -ne 0) { throw "V6 execution failed: exit=$LASTEXITCODE" }
    if (-not (Test-Path -LiteralPath $v6Path)) { throw "V6 report missing after execution: $v6Path" }
}

$current = Read-KvFile $v6Path
if (-not (Is-ValidSession $current)) {
    throw 'Current V6 report did not pass the strict V6b session gate.'
}

$pidText = $current['PID']
$startText = $current['PROCESS_START_UTC']
$safeStart = ($startText -replace '[^0-9A-Za-z]', '')
$archivePath = Join-Path $historyDir ("root_graph_{0}_pid{1}.txt" -f $safeStart, $pidText)
Copy-Item -LiteralPath $v6Path -Destination $archivePath -Force

$all = New-Object System.Collections.Generic.List[object]
foreach ($f in Get-ChildItem -LiteralPath $historyDir -Filter '*.txt' -File -ErrorAction SilentlyContinue) {
    $m = Read-KvFile $f.FullName
    if (-not (Is-ValidSession $m)) { continue }
    $all.Add([pscustomobject]@{
        Path = $f.FullName
        PID = $m['PID']
        Start = $m['PROCESS_START_UTC']
        Base = $m['MODULE_BASE']
        Root = $m['ROOT_OBJECT']
        Grid = $m['GRID_OBJECT']
        InvWin = $m['INVWIN_OBJECT']
    })
}

$distinct = @($all | Group-Object { $_.PID + '|' + $_.Start })
$distinctStarts = @($all | Select-Object -ExpandProperty Start -Unique)
$restartPass = ($all.Count -ge 2 -and $distinct.Count -ge 2 -and $distinctStarts.Count -ge 2)
$status = if ($restartPass) { 'PASS_ROOT_OWNER_RESTART_STABLE' } else { 'NEED_FRESH_PROCESS_REPEAT' }
$owner = if ($restartPass) { 'PASS_RESTART_STABLE' } else { 'PASS_CURRENT_PROCESS_ONLY' }

$lines = New-Object System.Collections.Generic.List[string]
$lines.Add('MODE=850_INVENTORY_ROOT_GRAPH_RESTART_GATE_V6B')
$lines.Add("CLIENT_SHA256=$ExpectedSha256")
$lines.Add('CLIENT_AUTHORITY=1')
$lines.Add(("SESSION_COUNT={0}" -f $all.Count))
$lines.Add(("DISTINCT_PROCESS_IDENTITIES={0}" -f $distinct.Count))
$lines.Add(("DISTINCT_PROCESS_STARTS={0}" -f $distinctStarts.Count))
$lines.Add('ROOT_GLOBAL_RVA=0x012BCEE8')
$lines.Add('ROOT_VTABLE_RVA=0x00EDE2F8')
$lines.Add('GRID_VTABLE_RVA=0x00EDDE38')
$lines.Add('INVWIN_VTABLE_RVA=0x00EDE180')
$lines.Add('ROOT_GRID_OFFSET=0x15C')
$lines.Add('ROOT_INVWIN_OFFSET=0x168')
$lines.Add('GRID_PARENT_OFFSET=0xEC')
$lines.Add('')
$lines.Add('[SESSIONS]')
foreach ($s in $all | Sort-Object Start) {
    $lines.Add(("PID={0} PROCESS_START_UTC={1} MODULE_BASE={2} ROOT={3} GRID={4} INVWIN={5} REPORT={6}" -f $s.PID, $s.Start, $s.Base, $s.Root, $s.Grid, $s.InvWin, $s.Path))
}
$lines.Add('')
$lines.Add('[DECISION]')
$lines.Add("STATUS=$status")
$lines.Add("OWNER_ANCHOR=$owner")
$lines.Add('FORMAL_WP5=NOT_YET')
$lines.Add('FORMAL_WP6=NOT_YET')
$lines.Add('READ_DWORD_COUNT_PER_SESSION=7')
$lines.Add('EXACT_TARGET_DEREFERENCE=YES')
$lines.Add('HEAP_SCAN=NO')
$lines.Add('MEM_PRIVATE_SCAN=NO')
$lines.Add('VECTOR_SCAN=NO')
$lines.Add('MEMORY_WRITE=NO')
if ($restartPass) {
    $lines.Add('NEXT=Freeze ROOT_GLOBAL/module-relative owner anchor and proceed only to bounded collection-offset investigation from ROOT/GRID/INVWIN; do not resume broad scans.')
} else {
    $lines.Add('NEXT=Fully close Lin.bin2, launch a new client process, enter the world, then rerun this V6b script without -UseExistingV6.')
}

[IO.File]::WriteAllText($gatePath, ($lines -join [Environment]::NewLine) + [Environment]::NewLine, [Text.UTF8Encoding]::new($false))
$lines | ForEach-Object { Write-Host $_ }
Write-Host "ARCHIVE=$archivePath"
Write-Host "OUTPUT=$gatePath"
