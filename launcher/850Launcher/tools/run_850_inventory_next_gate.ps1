param(
    [string]$ClientPath = "I:\8.50c客服端\Lin.bin2"
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$OutputRoot = 'I:\L共通工具\LineageAIResourceToolkit\outputs'

if (-not (Test-Path -LiteralPath $ClientPath)) {
    Write-Host 'STATUS=CLIENT_NOT_FOUND'
    Write-Host "CLIENT=$ClientPath"
    exit 0
}

$full = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) {
    throw "Client authority mismatch: $sha"
}

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

if ($null -eq $proc) {
    Write-Host 'STATUS=CLIENT_RUNNING_NO'
    Write-Host "CLIENT=$full"
    Write-Host "CLIENT_SHA256=$sha"
    Write-Host 'NEXT=Start/login authoritative Lin.bin2, then rerun this script.'
    Write-Host 'MEMORY_WRITE=NO'
    exit 0
}

try {
    $processStartUtc = $proc.StartTime.ToUniversalTime()
} catch {
    throw "PROCESS_START_UTC unavailable for PID=$($proc.Id); restart evidence identity cannot be trusted."
}

$tools = @(
    [pscustomobject]@{
        Name = 'V4C_RTTI'
        Path = Join-Path $PSScriptRoot 'run_850_inventory_vtable_rtti_v4c.ps1'
        OutputPath = Join-Path $OutputRoot '850_inventory_vtable_rtti_v4c.txt'
    },
    [pscustomobject]@{
        Name = 'V4B_ROOT_GLOBAL'
        Path = Join-Path $PSScriptRoot 'run_850_inventory_root_global_xref_v4b.ps1'
        OutputPath = Join-Path $OutputRoot '850_inventory_root_global_xref_v4b.txt'
    },
    [pscustomobject]@{
        Name = 'V4_CTOR_OWNER'
        Path = Join-Path $PSScriptRoot 'run_850_inventory_ctor_owner_trace_v4.ps1'
        OutputPath = Join-Path $OutputRoot '850_inventory_ctor_owner_trace_v4.txt'
    }
)

foreach ($tool in $tools) {
    if (-not (Test-Path -LiteralPath $tool.Path)) {
        throw "Missing gate tool: $($tool.Path)"
    }
}

if (-not (Test-Path -LiteralPath $OutputRoot)) {
    New-Item -ItemType Directory -Force -Path $OutputRoot | Out-Null
}

Write-Host 'STATUS=RUNNING_TARGETED_INVENTORY_GATES'
Write-Host "CLIENT=$full"
Write-Host "CLIENT_SHA256=$sha"
Write-Host "PID=$($proc.Id)"
Write-Host "PROCESS_START_UTC=$($processStartUtc.ToString('o'))"
Write-Host 'ORDER=V4C_RTTI,V4B_ROOT_GLOBAL,V4_CTOR_OWNER'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'

$results = New-Object System.Collections.Generic.List[object]
foreach ($tool in $tools) {
    Write-Host "BEGIN=$($tool.Name)"
    try {
        & $tool.Path -ClientPath $full -OutputPath $tool.OutputPath

        if (-not (Test-Path -LiteralPath $tool.OutputPath)) {
            throw "Targeted tool returned without output file: $($tool.OutputPath)"
        }

        $identityLines = @(
            '',
            '[RUN_IDENTITY]',
            "GATE=$($tool.Name)",
            "PID=$($proc.Id)",
            "PROCESS_START_UTC=$($processStartUtc.ToString('o'))",
            "CLIENT_SHA256=$sha",
            'CLIENT_AUTHORITY=1',
            'MEMORY_WRITE=NO'
        )

        [IO.File]::AppendAllLines(
            $tool.OutputPath,
            $identityLines,
            [Text.UTF8Encoding]::new($false))

        $results.Add([pscustomobject]@{
            Name = $tool.Name
            Status = 'PASS_EXECUTED'
            Error = ''
            OutputPath = $tool.OutputPath
        })

        Write-Host "END=$($tool.Name) STATUS=PASS_EXECUTED OUTPUT=$($tool.OutputPath)"
    } catch {
        $msg = $_.Exception.Message
        $results.Add([pscustomobject]@{
            Name = $tool.Name
            Status = 'FAIL'
            Error = $msg
            OutputPath = $tool.OutputPath
        })
        Write-Host "END=$($tool.Name) STATUS=FAIL ERROR=$msg"
    }
}

$fail = @($results | Where-Object { $_.Status -ne 'PASS_EXECUTED' })
Write-Host ''
Write-Host '[GATE_SUMMARY]'
foreach ($r in $results) {
    if ([string]::IsNullOrWhiteSpace($r.Error)) {
        Write-Host "$($r.Name)=$($r.Status) OUTPUT=$($r.OutputPath)"
    } else {
        Write-Host "$($r.Name)=$($r.Status) ERROR=$($r.Error)"
    }
}

if ($fail.Count -eq 0) {
    Write-Host 'STATUS=PASS_ALL_TARGETED_GATES_EXECUTED'
    Write-Host 'IDENTITY_STAMP=PID+PROCESS_START_UTC+CLIENT_SHA256'
    Write-Host 'NEXT=Review V4c/V4b/V4 outputs; do not run broad heap/vector scans.'
} else {
    Write-Host 'STATUS=PARTIAL_GATE_FAILURE'
    Write-Host 'NEXT=Fix only the failed targeted tool; do not widen scan scope.'
}
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'MEMORY_WRITE=NO'
