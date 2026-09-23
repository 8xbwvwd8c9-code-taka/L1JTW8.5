param(
    [string]$ToolkitRoot = "I:\L共通工具\LineageAIResourceToolkit",
    [string]$ClientRoot = "I:\8.50c客服端",
    [string]$OutputRoot = ""
)

$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($OutputRoot)) {
    $OutputRoot = Join-Path $ToolkitRoot "outputs\850_launcher_static_audit"
}

$wrapper = Join-Path $ToolkitRoot "scripts\lineage-tool.ps1"
if (-not (Test-Path -LiteralPath $wrapper)) {
    throw "LineageAIResourceToolkit wrapper not found: $wrapper"
}
if (-not (Test-Path -LiteralPath $ClientRoot)) {
    throw "Client root not found: $ClientRoot"
}

New-Item -ItemType Directory -Path $OutputRoot -Force | Out-Null

function SafeName([string]$value) {
    if ([string]::IsNullOrWhiteSpace($value)) { return "empty" }
    return ($value -replace '[^0-9A-Za-z._-]+','_').Trim('_')
}

function Invoke-ToolkitReadOnly {
    param(
        [Parameter(Mandatory=$true)][string[]]$ToolArgs,
        [Parameter(Mandatory=$true)][string]$OutputFile
    )

    $dest = Join-Path $OutputRoot $OutputFile
    $allArgs = @('-NoProfile','-ExecutionPolicy','Bypass','-File',$wrapper) + $ToolArgs

    "COMMAND=powershell.exe $($allArgs -join ' ')" | Out-File -LiteralPath $dest -Encoding utf8
    & powershell.exe @allArgs 2>&1 | Out-File -LiteralPath $dest -Encoding utf8 -Append
    $code = $LASTEXITCODE
    "EXIT_CODE=$code" | Out-File -LiteralPath $dest -Encoding utf8 -Append
    return $code
}

$started = Get-Date
$summary = Join-Path $OutputRoot "_SUMMARY.txt"
@(
    "MODE=850_TOOLKIT_STATIC_RESOURCE_AUDIT",
    "TIME=$($started.ToString('yyyy-MM-dd HH:mm:ss'))",
    "TOOLKIT_ROOT=$ToolkitRoot",
    "CLIENT_ROOT=$ClientRoot",
    "OUTPUT_ROOT=$OutputRoot",
    "SOURCE_MODIFIED=NO",
    "CLIENT_WRITE=NO",
    "TOOLKIT_WRITE_SCOPE=outputs_only"
) | Out-File -LiteralPath $summary -Encoding utf8

# The toolkit documentation requires checking the unified wrapper help first.
Invoke-ToolkitReadOnly -ToolArgs @('--help') -OutputFile '00_wrapper_help.txt' | Out-Null
Invoke-ToolkitReadOnly -ToolArgs @('version') -OutputFile '01_version.txt' | Out-Null
Invoke-ToolkitReadOnly -ToolArgs @('pak','--help') -OutputFile '02_pak_help.txt' | Out-Null

# Read-only client resource manifest. No client file is changed.
$idxFiles = @(Get-ChildItem -LiteralPath $ClientRoot -Filter '*.idx' -File -Recurse -ErrorAction SilentlyContinue)
$idxFiles |
    Sort-Object FullName |
    Select-Object FullName,Length,LastWriteTime |
    Format-Table -AutoSize |
    Out-String -Width 4096 |
    Out-File -LiteralPath (Join-Path $OutputRoot '10_client_idx_manifest.txt') -Encoding utf8

$textIdx = $idxFiles | Where-Object { $_.Name -ieq 'Text.idx' } | Select-Object -First 1
if ($textIdx) {
    Add-Content -LiteralPath $summary -Encoding utf8 -Value "TEXT_IDX=$($textIdx.FullName)"
    Invoke-ToolkitReadOnly -ToolArgs @('pak','info',$textIdx.FullName) -OutputFile '20_text_idx_info.txt' | Out-Null
    Invoke-ToolkitReadOnly -ToolArgs @('pak','list',$textIdx.FullName) -OutputFile '21_text_idx_list.txt' | Out-Null

    # Filename/resource-name searches only. These are intentionally broad; exact
    # extraction happens in a later step after the actual 8.50 resource names are known.
    $terms = @(
        'string-c.tbl',
        'MainCharInfoUI',
        'ClassSlotData',
        'effectlist',
        'inventory',
        'item',
        'buff',
        'skill',
        'status',
        'charinfo',
        'pet',
        'summon',
        'doll',
        'hp',
        'mp',
        'shortcut',
        'quick'
    )

    foreach ($term in $terms) {
        $file = 'search_' + (SafeName $term) + '.txt'
        Invoke-ToolkitReadOnly -ToolArgs @('pak','search',$textIdx.FullName,$term) -OutputFile $file | Out-Null
    }
} else {
    Add-Content -LiteralPath $summary -Encoding utf8 -Value 'TEXT_IDX=NOT_FOUND'
}

# Record metadata for other known index families when present. Avoid full list/export
# here because this first pass is only for static structure discovery.
foreach ($name in @('Sprite.idx','Sprite00.idx','Tile.idx')) {
    $match = $idxFiles | Where-Object { $_.Name -ieq $name } | Select-Object -First 1
    if (-not $match) { continue }
    $safe = SafeName $name
    Add-Content -LiteralPath $summary -Encoding utf8 -Value "$($name.ToUpperInvariant().Replace('.','_'))=$($match.FullName)"
    Invoke-ToolkitReadOnly -ToolArgs @('pak','info',$match.FullName) -OutputFile ("30_info_" + $safe + '.txt') | Out-Null
}

# Finalize the summary before hashing reports so its recorded digest reflects the
# finished report. The manifest itself is intentionally excluded to avoid hashing
# a file while Export-Csv has it open for writing.
Add-Content -LiteralPath $summary -Encoding utf8 -Value "FINISHED=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))"
Add-Content -LiteralPath $summary -Encoding utf8 -Value 'STATUS=PASS_REPORTS_GENERATED'

$manifestPath = Join-Path $OutputRoot '_REPORT_MANIFEST.csv'
Get-ChildItem -LiteralPath $OutputRoot -File |
    Where-Object { $_.FullName -ne $manifestPath } |
    Sort-Object Name |
    ForEach-Object {
        $h = Get-FileHash -LiteralPath $_.FullName -Algorithm SHA256
        [PSCustomObject]@{ Name=$_.Name; Length=$_.Length; SHA256=$h.Hash }
    } |
    Export-Csv -LiteralPath $manifestPath -NoTypeInformation -Encoding UTF8

Write-Host "STATUS=PASS_REPORTS_GENERATED"
Write-Host "OUTPUT=$OutputRoot"
Write-Host "SOURCE_MODIFIED=NO"
